import request from '@/utils/request'

export function getglobalheaderparamsList(params) {
  return request({
    url: '/globalheader/params',
    method: 'get',
    params
  })
}

export function getglobalheaderparamsnum() {
  return request({
    url: '/globalheader/params/getglobalheaderparamsnum',
    method: 'get'
  })
}

export function getglobalheaderparamsallList() {
  return request({
    url: '/globalheader/params/getvariableslist',
    method: 'get'
  })
}

export function search(globalheaderparamsForm) {
  return request({
    url: '/globalheader/params/search',
    method: 'post',
    data: globalheaderparamsForm
  })
}

export function addglobalheaderparams(globalheaderparamsForm) {
  return request({
    url: '/globalheader/params',
    method: 'post',
    data: globalheaderparamsForm
  })
}

export function updateglobalheaderparams(globalheaderparamsForm) {
  return request({
    url: '/globalheader/params/detail',
    method: 'put',
    data: globalheaderparamsForm
  })
}

export function removeglobalheaderparams(globalheaderparamsId) {
  return request({
    url: '/globalheader/params/' + globalheaderparamsId,
    method: 'delete'
  })
}
