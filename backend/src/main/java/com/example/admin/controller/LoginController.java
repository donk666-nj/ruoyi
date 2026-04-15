package com.example.admin.controller;

import com.example.admin.service.UserService;
import com.example.admin.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private UserService userService;

    // 使用内存存储验证码，替代Redis
    private static final ConcurrentHashMap<String, CaptchaInfo> CAPTCHA_MAP = new ConcurrentHashMap<>();

    static {
        // 定期清理过期的验证码
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(60000); // 每分钟清理一次
                    long now = System.currentTimeMillis();
                    CAPTCHA_MAP.entrySet().removeIf(entry -> now - entry.getValue().getTimestamp() > 5 * 60 * 1000); // 5分钟过期
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    static class CaptchaInfo {
        private String code;
        private long timestamp;

        public CaptchaInfo(String code, long timestamp) {
            this.code = code;
            this.timestamp = timestamp;
        }

        public String getCode() {
            return code;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response) throws IOException {
        // 生成验证码
        String code = CaptchaUtil.generateCode();
        // 生成验证码图片
        byte[] image = CaptchaUtil.generateImage(code);
        
        // 将验证码存储到内存，有效期5分钟
        String key = UUID.randomUUID().toString();
        CAPTCHA_MAP.put(key, new CaptchaInfo(code, System.currentTimeMillis()));
        
        // 设置响应头
        response.setHeader("Content-Type", "image/jpeg");
        response.setHeader("captcha-key", key);
        response.getOutputStream().write(image);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        
        String username = params.get("username");
        String password = params.get("password");
        String captcha = params.get("captcha");
        String captchaKey = params.get("captchaKey");
        
        // 验证验证码
        if (captchaKey == null || captcha == null) {
            result.put("code", 400);
            result.put("message", "验证码不能为空");
            return result;
        }
        
        CaptchaInfo captchaInfo = CAPTCHA_MAP.get(captchaKey);
        if (captchaInfo == null || System.currentTimeMillis() - captchaInfo.getTimestamp() > 5 * 60 * 1000) {
            result.put("code", 400);
            result.put("message", "验证码已过期");
            return result;
        }
        
        if (!captchaInfo.getCode().equalsIgnoreCase(captcha)) {
            result.put("code", 400);
            result.put("message", "验证码错误");
            return result;
        }
        
        // 验证用户
        com.example.admin.entity.User user = userService.findByUsername(username);
        if (user == null) {
            result.put("code", 400);
            result.put("message", "用户名或密码错误");
            return result;
        }
        
        if (!user.getPassword().equals(password)) {
            result.put("code", 400);
            result.put("message", "用户名或密码错误");
            return result;
        }
        
        // 登录成功
        result.put("code", 200);
        result.put("message", "登录成功");
        result.put("user", user);
        
        return result;
    }
}
