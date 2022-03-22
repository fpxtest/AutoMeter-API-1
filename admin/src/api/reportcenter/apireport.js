import request from '@/utils/request'

export function getapireportList(params) {
  return request({
    url: '/apicases/report',
    method: 'get',
    params
  })
}

export function search(apireportForm) {
  return request({
    url: '/apicases/report/search',
    method: 'post',
    data: apireportForm
  })
}

export function findApicasereportWithNameandStatus(apireportForm) {
  return request({
    url: '/apicases/report/findApicasereportWithNameandStatus',
    method: 'post',
    data: apireportForm
  })
}

export function getstaticsreport(apireportForm) {
  return request({
    url: '/apicases/report/getstaticsreport',
    method: 'post',
    data: apireportForm
  })
}

export function getfunctioncasestatics(apireportForm) {
  return request({
    url: '/apicases/report/getfunctioncasestatics',
    method: 'post',
    data: apireportForm
  })
}

export function getfunctionconditionstatics(apireportForm) {
  return request({
    url: '/apicases/report/getfunctionconditionstatics',
    method: 'post',
    data: apireportForm
  })
}

export function getfunctionCaseSandF(apireportForm) {
  return request({
    url: '/apicases/report/getfunctionCaseSandF',
    method: 'post',
    data: apireportForm
  })
}

export function addapireport(apireportForm) {
  return request({
    url: '/apicases/report',
    method: 'post',
    data: apireportForm
  })
}

export function updateapireport(apireportForm) {
  return request({
    url: '/apicases/report',
    method: 'put',
    data: apireportForm
  })
}

export function removeapireport(apireportId) {
  return request({
    url: '/apicases/report/' + apireportId,
    method: 'delete'
  })
}
