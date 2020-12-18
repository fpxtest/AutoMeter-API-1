import request from '@/utils/request'

export function getapiperformancestatisticsList(params) {
  return request({
    url: '/apicases/performancestatistics',
    method: 'get',
    params
  })
}

export function search(apiperformancestatisticsForm) {
  return request({
    url: '/apicases/performancestatistics/search',
    method: 'post',
    data: apiperformancestatisticsForm
  })
}

export function addapiperformancestatistics(apiperformancestatisticsForm) {
  return request({
    url: '/apicases/performancestatistics',
    method: 'post',
    data: apiperformancestatisticsForm
  })
}

export function updateapiperformancestatistics(apiperformancestatisticsForm) {
  return request({
    url: '/apicases/performancestatistics',
    method: 'put',
    data: apiperformancestatisticsForm
  })
}

export function removeapiperformancestatistics(apiperformancestatisticsId) {
  return request({
    url: '/apicases/performancestatistics/' + apiperformancestatisticsId,
    method: 'delete'
  })
}
