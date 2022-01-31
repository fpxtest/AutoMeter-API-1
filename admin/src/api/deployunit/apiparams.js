import request from '@/utils/request'

export function getapiparamsList(params) {
  return request({
    url: '/api/params',
    method: 'get',
    params
  })
}

export function getcaseparatype(params) {
  return request({
    url: '/api/params/searchid',
    method: 'post',
    data: params
  })
}

export function searchparamsbyapiid(params) {
  return request({
    url: '/api/params/searchparamsbyapiid',
    method: 'post',
    data: params
  })
}

export function searchbyidandproperty(params) {
  return request({
    url: '/api/params/searchbyidandproperty',
    method: 'post',
    data: params
  })
}

export function getBodyNoFormbyapiid(params) {
  return request({
    url: '/api/params/getBodyNoFormbyapiid',
    method: 'post',
    data: params
  })
}

export function search(apiparamsForm) {
  return request({
    url: '/api/params/search',
    method: 'post',
    data: apiparamsForm
  })
}

export function addapiparams(apiparamsForm) {
  return request({
    url: '/api/params',
    method: 'post',
    data: apiparamsForm
  })
}

export function addapiallparams(apiparamsForm) {
  return request({
    url: '/api/params/addapiallparams',
    method: 'post',
    data: apiparamsForm
  })
}

export function updateapiparams(apiparamsForm) {
  return request({
    url: '/api/params/detail',
    method: 'put',
    data: apiparamsForm
  })
}

export function removeapiparams(apiparamsId) {
  return request({
    url: '/api/params/' + apiparamsId,
    method: 'delete'
  })
}
