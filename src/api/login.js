import request from '@/utils/request'

// 获取验证码（返回图片 blob + header 里的 captcha-key）
export function getCaptcha() {
  return request({
    url: '/api/captcha',
    method: 'get',
    responseType: 'blob'
  })
}

// 登录
export function login(data) {
  return request({
    url: '/api/login',
    method: 'post',
    data
  })
}
