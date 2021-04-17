import request from '@/utils/request'

export function getapireportstaticsList(params) {
  return request({
    url: '/apicases/reportstatics/getplanstatics',
    method: 'get',
    params
  })
}

export function search(apireportstaticsForm) {
  return request({
    url: '/apicases/reportstatics/search',
    method: 'post',
    data: apireportstaticsForm
  })
}

export function getstaticsreportstatics(apireportstaticsForm) {
  return request({
    url: '/apicases/reportstatics/getstaticsreportstatics',
    method: 'post',
    data: apireportstaticsForm
  })
}

export function addapireportstatics(apireportstaticsForm) {
  return request({
    url: '/apicases/reportstatics',
    method: 'post',
    data: apireportstaticsForm
  })
}

export function updateapireportstatics(apireportstaticsForm) {
  return request({
    url: '/apicases/reportstatics',
    method: 'put',
    data: apireportstaticsForm
  })
}

export function removeapireportstatics(apireportstaticsId) {
  return request({
    url: '/apicases/reportstatics/' + apireportstaticsId,
    method: 'delete'
  })
}
