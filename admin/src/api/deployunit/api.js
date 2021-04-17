import request from '@/utils/request'

export function getapiList(params) {
  return request({
    url: '/api',
    method: 'get',
    params
  })
}

export function getstaticsdeployapi() {
  return request({
    url: '/api/getstaticsdeployapi',
    method: 'get'
  })
}

export function getapinum() {
  return request({
    url: '/api/getapinum',
    method: 'get'
  })
}

export function getapiListbydeploy(params) {
  return request({
    url: 'api/apibydeploy',
    method: 'get',
    params
  })
}

export function getresponetypebydeployandapiname(params) {
  return request({
    url: 'api/getresponetypebydeployandapiname',
    method: 'get',
    params
  })
}

export function search(apiForm) {
  return request({
    url: '/api/search',
    method: 'post',
    data: apiForm
  })
}

export function addapi(apiForm) {
  return request({
    url: '/api',
    method: 'post',
    data: apiForm
  })
}

export function updateapi(apiForm) {
  return request({
    url: '/api/detail',
    method: 'put',
    data: apiForm
  })
}

export function removeapi(apiId) {
  return request({
    url: '/api/' + apiId,
    method: 'delete'
  })
}
