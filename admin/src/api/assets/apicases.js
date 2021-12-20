import request from '@/utils/request'

export function getapicasesList(params) {
  return request({
    url: '/apicases',
    method: 'get',
    params
  })
}

export function getstaticsdeployunitcases() {
  return request({
    url: '/apicases/getstaticsdeployunitcases',
    method: 'get'
  })
}

export function getcasenum(params) {
  return request({
    url: '/apicases/getcasenum',
    method: 'get',
    params
  })
}

export function getperformancecasenum(params) {
  return request({
    url: '/apicases/getperformancecasenum',
    method: 'get',
    params
  })
}

export function search(apicasesForm) {
  return request({
    url: '/apicases/search',
    method: 'post',
    data: apicasesForm
  })
}

export function searchleftcase(apicasesForm) {
  return request({
    url: '/apicases/searchleftcase',
    method: 'post',
    data: apicasesForm
  })
}

export function runtest(apicasesForm) {
  return request({
    url: '/apicases/runtest',
    method: 'post',
    data: apicasesForm
  })
}

export function getcasebydeployunitid(apicasesForm) {
  return request({
    url: '/apicases/getcasebydeployunitid',
    method: 'post',
    data: apicasesForm
  })
}

export function copycases(apicasesForm) {
  return request({
    url: '/apicases/copycases',
    method: 'post',
    data: apicasesForm
  })
}

export function findcasesbyname(apicasesForm) {
  return request({
    url: '/apicases/searchbyname',
    method: 'post',
    data: apicasesForm
  })
}

export function addapicases(apicasesForm) {
  return request({
    url: '/apicases',
    method: 'post',
    data: apicasesForm
  })
}

export function updateapicases(apicasesForm) {
  return request({
    url: '/apicases/detail',
    method: 'put',
    data: apicasesForm
  })
}

export function removeapicases(apicasesId) {
  return request({
    url: '/apicases/' + apicasesId,
    method: 'delete'
  })
}
