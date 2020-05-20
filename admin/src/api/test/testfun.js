import request from '@/utils/request'

export function mangerTable(params) {
  return request({
    url: '/deployunit',
    method: 'get',
    params
  })
}

export function search(depunitForm) {
  return request({
    url: '/deployunit/search',
    method: 'post',
    data: depunitForm
  })
}

export function addTable(depunitForm) {
  return request({
    url: '/deployunit',
    method: 'post',
    data: depunitForm
  })
}

export function editTable(depunitForm) {
  return request({
    url: '/deployunit/detail',
    method: 'put',
    data: depunitForm
  })
}

export function delTable(depunitId) {
  return request({
    url: '/deployunit/' + depunitId,
    method: 'delete'
  })
}
