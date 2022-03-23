import request from '@/utils/request'

export function getdispatchList(params) {
  return request({
    url: '/dispatch',
    method: 'get',
    params
  })
}

export function search(dispatchForm) {
  return request({
    url: '/dispatch/search',
    method: 'post',
    data: dispatchForm
  })
}

export function getDispatchWithstatus(dispatchForm) {
  return request({
    url: '/dispatch/getDispatchWithstatus',
    method: 'post',
    data: dispatchForm
  })
}

export function adddispatch(dispatchForm) {
  return request({
    url: '/dispatch',
    method: 'post',
    data: dispatchForm
  })
}

export function updatedispatch(dispatchForm) {
  return request({
    url: '/dispatch/detail',
    method: 'put',
    data: dispatchForm
  })
}

export function removedispatch(dispatchId) {
  return request({
    url: '/dispatch/' + dispatchId,
    method: 'delete'
  })
}
