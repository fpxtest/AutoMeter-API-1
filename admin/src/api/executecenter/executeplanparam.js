import request from '@/utils/request'

export function getexecuteplanparamList(params) {
  return request({
    url: '/executeplan/params',
    method: 'get',
    params
  })
}

export function searchparamsbyepid(params) {
  return request({
    url: '/executeplan/params/searchparamsbyepid',
    method: 'post',
    data: params
  })
}

export function search(executeplanForm) {
  return request({
    url: '/executeplan/params/search',
    method: 'post',
    data: executeplanForm
  })
}

export function addexecuteplanparam(executeplanForm) {
  return request({
    url: '/executeplan/params',
    method: 'post',
    data: executeplanForm
  })
}

export function updateexecuteplanparams(executeplanForm) {
  return request({
    url: '/executeplan/params/detail',
    method: 'put',
    data: executeplanForm
  })
}

export function removeexecuteplanparam(executeplanId) {
  return request({
    url: '/executeplan/params/' + executeplanId,
    method: 'delete'
  })
}
