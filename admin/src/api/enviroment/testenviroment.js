import request from '@/utils/request'

export function getenviromentList(params) {
  return request({
    url: '/enviroment',
    method: 'get',
    params
  })
}

export function getenviromentnum(params) {
  return request({
    url: '/enviroment/getenviromentnum',
    method: 'get',
    params
  })
}

export function getenviromentallList(params) {
  return request({
    url: '/enviroment/ens',
    method: 'get',
    params
  })
}

export function search(enviromentForm) {
  return request({
    url: '/enviroment/search',
    method: 'post',
    data: enviromentForm
  })
}

export function addenviroment(enviromentForm) {
  return request({
    url: '/enviroment',
    method: 'post',
    data: enviromentForm
  })
}

export function updateenviroment(enviromentForm) {
  return request({
    url: '/enviroment/detail',
    method: 'put',
    data: enviromentForm
  })
}

export function removeenviroment(enviromentId) {
  return request({
    url: '/enviroment/' + enviromentId,
    method: 'delete'
  })
}
