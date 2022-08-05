import request from '@/utils/request'

export function getmockmodelList(params) {
  return request({
    url: '/mockmodel',
    method: 'get',
    params
  })
}

export function getmockmodelnum() {
  return request({
    url: '/mockmodel/getmockmodelnum',
    method: 'get'
  })
}

export function getmockmodelallList() {
  return request({
    url: '/mockmodel',
    method: 'get'
  })
}

export function search(mockmodelForm) {
  return request({
    url: '/mockmodel/search',
    method: 'post',
    data: mockmodelForm
  })
}

export function addmockmodel(mockmodelForm) {
  return request({
    url: '/mockmodel',
    method: 'post',
    data: mockmodelForm
  })
}

export function updatemockmodel(mockmodelForm) {
  return request({
    url: '/mockmodel/detail',
    method: 'put',
    data: mockmodelForm
  })
}

export function removemockmodel(mockmodelId) {
  return request({
    url: '/mockmodel/' + mockmodelId,
    method: 'delete'
  })
}
