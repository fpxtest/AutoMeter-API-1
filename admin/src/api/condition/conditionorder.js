import request from '@/utils/request'

export function getconditionorderList(params) {
  return request({
    url: '/condition/order',
    method: 'get',
    params
  })
}

export function getconditionordernum() {
  return request({
    url: '/condition/order/getconditionordernum',
    method: 'get'
  })
}

export function getconditionorderallList() {
  return request({
    url: '/condition/order/ens',
    method: 'get'
  })
}

export function searchconditionorder(conditionorderForm) {
  return request({
    url: '/condition/order/search',
    method: 'post',
    data: conditionorderForm
  })
}

export function addconditionorder(conditionorderForm) {
  return request({
    url: '/condition/order/addconditionorder',
    method: 'post',
    data: conditionorderForm
  })
}

export function updateconditionorder(conditionorderForm) {
  return request({
    url: '/condition/order/detail',
    method: 'put',
    data: conditionorderForm
  })
}

export function removeconditionorder(conditionorderId) {
  return request({
    url: '/condition/order/' + conditionorderId,
    method: 'delete'
  })
}
