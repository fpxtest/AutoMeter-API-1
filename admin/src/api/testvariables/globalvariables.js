import request from '@/utils/request'

export function getglobalvariablesList(params) {
  return request({
    url: '/globalvariables',
    method: 'get',
    params
  })
}

export function getglobalvariablesnum() {
  return request({
    url: '/globalvariables/getglobalvariablesnum',
    method: 'get'
  })
}

export function getglobalvariablesallList() {
  return request({
    url: '/globalvariables/ens',
    method: 'get'
  })
}

export function search(globalvariablesForm) {
  return request({
    url: '/globalvariables/search',
    method: 'post',
    data: globalvariablesForm
  })
}

export function addglobalvariables(globalvariablesForm) {
  return request({
    url: '/globalvariables',
    method: 'post',
    data: globalvariablesForm
  })
}

export function updateglobalvariables(globalvariablesForm) {
  return request({
    url: '/globalvariables/detail',
    method: 'put',
    data: globalvariablesForm
  })
}

export function removeglobalvariables(globalvariablesId) {
  return request({
    url: '/globalvariables/' + globalvariablesId,
    method: 'delete'
  })
}
