import request from '@/utils/request'

export function getexecuteplanList(params) {
  return request({
    url: '/executeplan',
    method: 'get',
    params
  })
}

export function search(executeplanForm) {
  return request({
    url: '/executeplan/search',
    method: 'post',
    data: executeplanForm
  })
}

export function addexecuteplan(executeplanForm) {
  return request({
    url: '/executeplan',
    method: 'post',
    data: executeplanForm
  })
}

export function updateexecuteplan(executeplanForm) {
  return request({
    url: '/executeplan/detail',
    method: 'put',
    data: executeplanForm
  })
}

export function removeexecuteplan(executeplanId) {
  return request({
    url: '/executeplan/' + executeplanId,
    method: 'delete'
  })
}
