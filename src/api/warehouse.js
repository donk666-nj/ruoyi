import request from '@/utils/request'

export function listWarehouse(params) {
  return request({ url: '/api/warehouse/list', method: 'get', params })
}

export function addWarehouse(data) {
  return request({ url: '/api/warehouse', method: 'post', data })
}

export function updateWarehouse(data) {
  return request({ url: '/api/warehouse', method: 'put', data })
}

export function delWarehouse(id) {
  return request({ url: '/api/warehouse/' + id, method: 'delete' })
}
