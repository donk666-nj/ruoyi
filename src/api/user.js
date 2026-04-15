import request from '@/utils/request'

export function listUser() {
  return request({ url: '/api/user/list', method: 'get' })
}

export function getUser(id) {
  return request({ url: '/api/user/' + id, method: 'get' })
}

export function addUser(data) {
  return request({ url: '/api/user', method: 'post', data })
}

export function updateUser(data) {
  return request({ url: '/api/user', method: 'put', data })
}

export function delUser(id) {
  return request({ url: '/api/user/' + id, method: 'delete' })
}
