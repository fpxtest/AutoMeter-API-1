import request from '@/utils/request'

export function getmockapiList(params) {
  return request({
    url: '/mockapi',
    method: 'get',
    params
  })
}

export function getmockapinum() {
  return request({
    url: '/mockapi/getmockapinum',
    method: 'get'
  })
}

export function getmockapiallList() {
  return request({
    url: '/mockapi',
    method: 'get'
  })
}

export function search(mockapiForm) {
  return request({
    url: '/mockapi/search',
    method: 'post',
    data: mockapiForm
  })
}

export function addmockapi(mockapiForm) {
  return request({
    url: '/mockapi',
    method: 'post',
    data: mockapiForm
  })
}

export function updatemockapi(mockapiForm) {
  return request({
    url: '/mockapi/detail',
    method: 'put',
    data: mockapiForm
  })
}

export function removemockapi(mockapiId) {
  return request({
    url: '/mockapi/' + mockapiId,
    method: 'delete'
  })
}
