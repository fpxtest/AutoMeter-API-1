import request from '@/utils/request'

export function getdepunitList(params) {
  return request({
    url: '/deployunit',
    method: 'get',
    params
  })
}

export function getstaticsdeploynames(params) {
  return request({
    url: '/deployunit/getstaticsdeploynames',
    method: 'get',
    params
  })
}

export function findDeployNameValueWithCode(params) {
  return request({
    url: '/deployunit/findDeployNameValueWithCode',
    method: 'get',
    params
  })
}

export function getdeploynum(params) {
  return request({
    url: '/deployunit/getdeploynum',
    method: 'get',
    params
  })
}

export function getdepunitLists(params) {
  return request({
    url: '/deployunit/getdeplist',
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
