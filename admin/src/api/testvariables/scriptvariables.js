import request from '@/utils/request'

export function getscriptvariablesList(params) {
  return request({
    url: '/scriptvariables',
    method: 'get',
    params
  })
}

export function getscriptvariablesnum() {
  return request({
    url: '/scriptvariables/getscriptvariablesnum',
    method: 'get'
  })
}

export function getscriptvariablesallList() {
  return request({
    url: '/scriptvariables/getvariableslist',
    method: 'get'
  })
}

export function search(scriptvariablesForm) {
  return request({
    url: '/scriptvariables/search',
    method: 'post',
    data: scriptvariablesForm
  })
}

export function addscriptvariables(scriptvariablesForm) {
  return request({
    url: '/scriptvariables',
    method: 'post',
    data: scriptvariablesForm
  })
}

export function updatescriptvariables(scriptvariablesForm) {
  return request({
    url: '/scriptvariables/detail',
    method: 'put',
    data: scriptvariablesForm
  })
}

export function removescriptvariables(scriptvariablesId) {
  return request({
    url: '/scriptvariables/' + scriptvariablesId,
    method: 'delete'
  })
}
