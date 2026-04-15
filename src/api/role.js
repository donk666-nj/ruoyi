import request from '@/utils/request'

export function listRole() {
  return request({ url: '/api/role/list', method: 'get' })
}

export function getRole(id) {
  return request({ url: '/api/role/' + id, method: 'get' })
}

export function addRole(data) {
  return request({ url: '/api/role', method: 'post', data })
}

export function updateRole(data) {
  return request({ url: '/api/role', method: 'put', data })
}

export function delRole(id) {
  return request({ url: '/api/role/' + id, method: 'delete' })
}
