import request from '@/utils/request'

export function getscriptconditionList(params) {
  return request({
    url: '/condition/script',
    method: 'get',
    params
  })
}

export function getscriptconditionnum() {
  return request({
    url: '/condition/script/getscriptconditionnum',
    method: 'get'
  })
}

export function getscriptconditionallList(params) {
  return request({
    url: '/condition/script/getscriptconditionallList',
    method: 'get',
    params
  })
}

export function search(scriptconditionForm) {
  return request({
    url: '/condition/script/search',
    method: 'post',
    data: scriptconditionForm
  })
}

export function addscriptcondition(scriptconditionForm) {
  return request({
    url: '/condition/script',
    method: 'post',
    data: scriptconditionForm
  })
}

export function updatescriptcondition(scriptconditionForm) {
  return request({
    url: '/condition/script/detail',
    method: 'put',
    data: scriptconditionForm
  })
}

export function removescriptcondition(scriptconditionId) {
  return request({
    url: '/condition/script/' + scriptconditionId,
    method: 'delete'
  })
}
