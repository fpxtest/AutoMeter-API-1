import request from '@/utils/request'

export function getdepunitList(params) {
  return request({
    url: '/deployunit',
    method: 'get',
    params
  })
}

export function getdeploynum() {
  return request({
    url: '/deployunit/getdeploynum',
    method: 'get'
  })
}

export function getdepunitLists() {
  return request({
    url: '/deployunit/getdeplist',
    method: 'get'
  })
}

export function search(depunitForm) {
  return request({
    url: '/deployunit/search',
    method: 'post',
    data: depunitForm
  })
}

export function adddepunit(depunitForm) {
  return request({
    url: '/deployunit',
    method: 'post',
    data: depunitForm
  })
}

export function updatedepunit(depunitForm) {
  return request({
    url: '/deployunit/detail',
    method: 'put',
    data: depunitForm
  })
}

export function removedepunit(depunitId) {
  return request({
    url: '/deployunit/' + depunitId,
    method: 'delete'
  })
}
