import request from '@/utils/request'

export function getmachineList(params) {
  return request({
    url: '/machine',
    method: 'get',
    params
  })
}

export function getmachineLists(params) {
  return request({
    url: '/machine/getmachine',
    method: 'get',
    params
  })
}

export function getmachinenum(params) {
  return request({
    url: '/machine/getmachinenum',
    method: 'get',
    params
  })
}

export function search(machineForm) {
  return request({
    url: '/machine/search',
    method: 'post',
    data: machineForm
  })
}

export function addmachine(machineForm) {
  return request({
    url: '/machine',
    method: 'post',
    data: machineForm
  })
}

export function updatemachine(machineForm) {
  return request({
    url: '/machine/detail',
    method: 'put',
    data: machineForm
  })
}

export function removemachine(machineId) {
  return request({
    url: '/machine/' + machineId,
    method: 'delete'
  })
}
