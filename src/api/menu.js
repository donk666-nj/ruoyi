import request from '@/utils/request'

export function listMenu() {
  return request({ url: '/api/menu/list', method: 'get' })
}

export function getMenuTree() {
  return request({ url: '/api/menu/tree', method: 'get' })
}

export function getMenu(id) {
  return request({ url: '/api/menu/' + id, method: 'get' })
}

export function addMenu(data) {
  return request({ url: '/api/menu', method: 'post', data })
}

export function updateMenu(data) {
  return request({ url: '/api/menu', method: 'put', data })
}

export function delMenu(id) {
  return request({ url: '/api/menu/' + id, method: 'delete' })
}
