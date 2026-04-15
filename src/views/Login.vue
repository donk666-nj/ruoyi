<template>
  <div class="login-wrapper">
    <div class="login-bg">
      <div class="login-left">
        <div class="brand">
          <h1>演示管理系统</h1>
          <p>演示用管理平台</p>
        </div>
      </div>
      <div class="login-right">
        <div class="login-box">
          <h2>欢迎登录</h2>
          <form @submit.prevent="handleLogin">
            <div class="form-item">
              <label>用户名</label>
              <input type="text" v-model="form.username" placeholder="请输入用户名" required />
            </div>
            <div class="form-item">
              <label>密码</label>
              <input type="password" v-model="form.password" placeholder="请输入密码" required />
            </div>
            <div class="form-item">
              <label>验证码</label>
              <div class="captcha-row">
                <input type="text" v-model="form.captcha" placeholder="请输入验证码" required />
                <img :src="captchaUrl" @click="refreshCaptcha" alt="验证码" class="captcha-img" />
              </div>
            </div>
            <button type="submit" class="login-btn">登 录</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { getCaptcha, login } from '@/api/login'

const store = useStore()
const router = useRouter()

const form = ref({ username: '', password: '', captcha: '' })
const captchaUrl = ref('')
const captchaKey = ref('')

const refreshCaptcha = async () => {
  try {
    const res = await getCaptcha()
    captchaUrl.value = URL.createObjectURL(new Blob([res.data], { type: 'image/jpeg' }))
    captchaKey.value = res.headers['captcha-key']
  } catch (e) {
    console.error('获取验证码失败:', e)
  }
}

const handleLogin = async () => {
  try {
    const res = await login({
      username: form.value.username,
      password: form.value.password,
      captcha: form.value.captcha,
      captchaKey: captchaKey.value
    })
    if (res.data.code === 200) {
      store.dispatch('login', res.data.user)
      router.push('/home')
    } else {
      alert(res.data.message || '登录失败')
      refreshCaptcha()
    }
  } catch (e) {
    alert('登录失败，请检查用户名、密码和验证码')
    refreshCaptcha()
  }
}

onMounted(() => { refreshCaptcha() })
</script>

<style scoped>
.login-wrapper { width: 100vw; height: 100vh; overflow: hidden; }
.login-bg { display: flex; width: 100%; height: 100%; }
.login-left {
  flex: 1; background: linear-gradient(135deg, #304156 0%, #1a2a3a 50%, #0d1b2a 100%);
  display: flex; align-items: center; justify-content: center; position: relative; overflow: hidden;
}
.login-left::before {
  content: ''; position: absolute; width: 400px; height: 400px; border-radius: 50%;
  background: rgba(64, 158, 255, 0.08); top: -100px; left: -100px;
}
.login-left::after {
  content: ''; position: absolute; width: 300px; height: 300px; border-radius: 50%;
  background: rgba(64, 158, 255, 0.06); bottom: -80px; right: -80px;
}
.brand { text-align: center; color: #fff; z-index: 1; }
.brand h1 { font-size: 36px; font-weight: 600; margin-bottom: 16px; letter-spacing: 4px; }
.brand p { font-size: 16px; color: rgba(255, 255, 255, 0.6); letter-spacing: 1px; }
.login-right {
  width: 480px; background: #fff; display: flex; align-items: center;
  justify-content: center; box-shadow: -4px 0 20px rgba(0, 0, 0, 0.08);
}
.login-box { width: 340px; }
.login-box h2 { font-size: 24px; color: #303133; margin-bottom: 36px; font-weight: 600; }
.form-item { margin-bottom: 22px; }
.form-item label { display: block; font-size: 14px; color: #606266; margin-bottom: 8px; }
.form-item input {
  width: 100%; height: 40px; padding: 0 12px; border: 1px solid #dcdfe6;
  border-radius: 6px; font-size: 14px; outline: none; transition: border-color 0.2s; box-sizing: border-box;
}
.form-item input:focus { border-color: #409eff; }
.captcha-row { display: flex; gap: 12px; }
.captcha-row input { flex: 1; }
.captcha-img { width: 120px; height: 40px; border-radius: 6px; cursor: pointer; border: 1px solid #dcdfe6; object-fit: cover; }
.login-btn {
  width: 100%; height: 44px; background: #409eff; color: #fff; border: none;
  border-radius: 6px; font-size: 16px; cursor: pointer; letter-spacing: 8px; margin-top: 8px; transition: background 0.2s;
}
.login-btn:hover { background: #66b1ff; }
@media (max-width: 900px) { .login-left { display: none; } .login-right { width: 100%; } }
</style>
