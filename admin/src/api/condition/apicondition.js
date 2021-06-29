import request from '@/utils/request'

export function getapiconditionList(params) {
  return request({
    url: '/condition/api',
    method: 'get',
    params
  })
}

export function getapiconditionnum() {
  return request({
    url: '/condition/api/getapiconditionnum',
    method: 'get'
  })
}

export function getapiconditionallList() {
  return request({
    url: '/condition/api/ens',
    method: 'get'
  })
}

export function search(apiconditionForm) {
  return request({
    url: '/condition/api/search',
    method: 'post',
    data: apiconditionForm
  })
}

export function addapicondition(apiconditionForm) {
  return request({
    url: '/condition/api',
    method: 'post',
    data: apiconditionForm
  })
}

export function updateapicondition(apiconditionForm) {
  return request({
    url: '/condition/api/detail',
    method: 'put',
    data: apiconditionForm
  })
}

export function removeapicondition(apiconditionId) {
  return request({
    url: '/condition/api/' + apiconditionId,
    method: 'delete'
  })
}
