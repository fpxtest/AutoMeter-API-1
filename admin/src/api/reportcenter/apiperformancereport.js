import request from '@/utils/request'

export function getapiperformancereportList(params) {
  return request({
    url: '/apicases/report/performance',
    method: 'get',
    params
  })
}

export function search(apiperformancereportForm) {
  return request({
    url: '/apicases/report/performance/search',
    method: 'post',
    data: apiperformancereportForm
  })
}

export function findApicasereportWithNameandStatus(apiperformancereportForm) {
  return request({
    url: '/apicases/report/performance/findApicasereportWithNameandStatus',
    method: 'post',
    data: apiperformancereportForm
  })
}

export function getperformancecasestatics(apireportForm) {
  return request({
    url: '/apicases/report/performance/getperformancecasestatics',
    method: 'post',
    data: apireportForm
  })
}

export function getperformanceallstatics(apireportForm) {
  return request({
    url: '/apicases/report/performance/getperformanceallstatics',
    method: 'post',
    data: apireportForm
  })
}

export function getperformanceslaverstatics(apireportForm) {
  return request({
    url: '/apicases/report/performance/getperformanceslaverstatics',
    method: 'post',
    data: apireportForm
  })
}

export function getperformanceCaseSandF(apireportForm) {
  return request({
    url: '/apicases/report/performance/getperformanceCaseSandF',
    method: 'post',
    data: apireportForm
  })
}

export function addapiperformancereport(apiperformancereportForm) {
  return request({
    url: '/apicases/report/performance',
    method: 'post',
    data: apiperformancereportForm
  })
}

export function updateapiperformancereport(apiperformancereportForm) {
  return request({
    url: '/apicases/report/performance',
    method: 'put',
    data: apiperformancereportForm
  })
}

export function removeapiperformancereport(apiperformancereportId) {
  return request({
    url: '/apicases/report/performance/' + apiperformancereportId,
    method: 'delete'
  })
}
