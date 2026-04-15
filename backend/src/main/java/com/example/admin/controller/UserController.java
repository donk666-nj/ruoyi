package com.example.admin.controller;

import com.example.admin.entity.User;
import com.example.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> listUsers() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping
    public boolean saveUser(@RequestBody User user) {
        user.setCreateTime(System.currentTimeMillis() / 1000);
        user.setUpdateTime(System.currentTimeMillis() / 1000);
        return userService.save(user);
    }

    @PutMapping
    public boolean updateUser(@RequestBody User user) {
        user.setUpdateTime(System.currentTimeMillis() / 1000);
        return userService.updateById(user);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        return userService.removeById(id);
    }
}
