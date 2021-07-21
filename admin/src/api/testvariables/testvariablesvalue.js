import request from '@/utils/request'

export function gettestvariablesvalueList(params) {
  return request({
    url: '/testvariables/value',
    method: 'get',
    params
  })
}

export function gettestvariablesvaluenum() {
  return request({
    url: '/testvariables/value/gettestvariablesvaluenum',
    method: 'get'
  })
}

export function gettestvariablesvalueallList() {
  return request({
    url: '/testvariables/value/getvariableslist',
    method: 'get'
  })
}

export function search(testvariablesvalueForm) {
  return request({
    url: '/testvariables/value/search',
    method: 'post',
    data: testvariablesvalueForm
  })
}

export function addtestvariablesvalue(testvariablesvalueForm) {
  return request({
    url: '/testvariables/value',
    method: 'post',
    data: testvariablesvalueForm
  })
}

export function updatetestvariablesvalue(testvariablesvalueForm) {
  return request({
    url: '/testvariables/value/detail',
    method: 'put',
    data: testvariablesvalueForm
  })
}

export function removetestvariablesvalue(testvariablesvalueId) {
  return request({
    url: '/testvariables/value/' + testvariablesvalueId,
    method: 'delete'
  })
}
