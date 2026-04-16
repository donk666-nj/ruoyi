import request from '@/utils/request'

export function listLocat(params) {
  return request({ url: '/api/locat/list', method: 'get', params })
}

export function addLocat(data) {
  return request({ url: '/api/locat', method: 'post', data })
}

export function updateLocat(data) {
  return request({ url: '/api/locat', method: 'put', data })
}

export function delLocat(id) {
  return request({ url: '/api/locat/' + id, method: 'delete' })
}

export function batchUpdateAreaNo(data) {
  return request({ url: '/api/locat/updateAreaNo', method: 'put', data })
}
