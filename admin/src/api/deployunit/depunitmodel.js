import request from '@/utils/request'

export function getdeployunitmodelList(params) {
  return request({
    url: '/deployunit/model',
    method: 'get',
    params
  })
}

export function getdeployunitmodel() {
  return request({
    url: '/deployunit/model/getdeployunitnum',
    method: 'get'
  })
}

export function getdeployunitmodelallList(params) {
  return request({
    url: '/deployunit/model/getvariableslist',
    method: 'get',
    params
  })
}

export function searchdeployunitmodel(deployunitForm) {
  return request({
    url: '/deployunit/model/search',
    method: 'post',
    data: deployunitForm
  })
}

export function adddeployunitmodel(deployunitForm) {
  return request({
    url: '/deployunit/model',
    method: 'post',
    data: deployunitForm
  })
}

export function updatedeployunitmodel(deployunitForm) {
  return request({
    url: '/deployunit/model/detail',
    method: 'put',
    data: deployunitForm
  })
}

export function removedeployunitmodel(deployunitId) {
  return request({
    url: '/deployunit/model/' + deployunitId,
    method: 'delete'
  })
}
