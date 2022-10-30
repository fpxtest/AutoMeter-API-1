import request from '@/utils/request'

export function getconditionList(params) {
  return request({
    url: '/testcondition',
    method: 'get',
    params
  })
}

export function getconditionnum() {
  return request({
    url: '/testcondition/getconditionnum',
    method: 'get'
  })
}

export function getconditionallList(params) {
  return request({
    url: '/testcondition/getalltestcondition',
    method: 'get',
    params
  })
}

export function search(conditionForm) {
  return request({
    url: '/testcondition/search',
    method: 'post',
    data: conditionForm
  })
}

export function getalltestcondition(params) {
  return request({
    url: '/testcondition/getalltestcondition',
    method: 'get',
    params
  })
}

export function getalltestconditionbytype(params) {
  return request({
    url: '/testcondition/getalltestconditionbytype',
    method: 'get',
    params
  })
}

export function gettestconditionforscripyanddelay(params) {
  return request({
    url: '/testcondition/gettestconditionforscripyanddelay',
    method: 'get',
    params
  })
}

export function addcondition(conditionForm) {
  return request({
    url: '/testcondition',
    method: 'post',
    data: conditionForm
  })
}

export function updatecondition(conditionForm) {
  return request({
    url: '/testcondition/detail',
    method: 'put',
    data: conditionForm
  })
}

export function removecondition(conditionId) {
  return request({
    url: '/testcondition/' + conditionId,
    method: 'delete'
  })
}
