import request from '@/utils/request'

export function getvariablesList(params) {
  return request({
    url: '/variables',
    method: 'get',
    params
  })
}

export function getvariablesnum() {
  return request({
    url: '/variables/getvariablesnum',
    method: 'get'
  })
}

export function getvariablesallList() {
  return request({
    url: '/variables/ens',
    method: 'get'
  })
}

export function search(variablesForm) {
  return request({
    url: '/variables/search',
    method: 'post',
    data: variablesForm
  })
}

export function addvariables(variablesForm) {
  return request({
    url: '/variables',
    method: 'post',
    data: variablesForm
  })
}

export function updatevariables(variablesForm) {
  return request({
    url: '/variables/detail',
    method: 'put',
    data: variablesForm
  })
}

export function removevariables(variablesId) {
  return request({
    url: '/variables/' + variablesId,
    method: 'delete'
  })
}
