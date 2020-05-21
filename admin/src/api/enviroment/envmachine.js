import request from '@/utils/request'

export function getenvmachineList(params) {
  return request({
    url: '/envmachine',
    method: 'get',
    params
  })
}

export function search(envmachineForm) {
  return request({
    url: '/envmachine/search',
    method: 'post',
    data: envmachineForm
  })
}

export function addenvmachine(envmachineForm) {
  return request({
    url: '/envmachine',
    method: 'post',
    data: envmachineForm
  })
}

export function updateenvmachine(envmachineForm) {
  return request({
    url: '/envmachine/detail',
    method: 'put',
    data: envmachineForm
  })
}

export function removeenvmachine(envmachineId) {
  return request({
    url: '/envmachine/' + envmachineId,
    method: 'delete'
  })
}
