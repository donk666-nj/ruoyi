import axios from 'axios'
import store from '@/store'
import router from '@/router'

const service = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 10000
})

// 响应拦截器
service.interceptors.response.use(
  response => response,
  error => {
    if (error.response && error.response.status === 401) {
      store.dispatch('logout')
      router.push('/login')
    }
    return Promise.reject(error)
  }
)

export default service
