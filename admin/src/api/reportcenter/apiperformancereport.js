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
