import request from '@/utils/request'

export function getprojectaccountList(params) {
  return request({
    url: '/project/account',
    method: 'get',
    params
  })
}

export function searchprojectaccount(projectaccountForm) {
  return request({
    url: '/project/account/search',
    method: 'post',
    data: projectaccountForm
  })
}

export function addprojectaccounts(params) {
  return request({
    url: '/project/account/addprojectaccounts',
    method: 'post',
    data: params
  })
}

export function findaccountbyprojectid(params) {
  return request({
    url: '/project/account/findaccountbyprojectid',
    method: 'post',
    data: params
  })
}

export function addprojectaccount(projectaccountForm) {
  return request({
    url: '/project/account',
    method: 'post',
    data: projectaccountForm
  })
}

export function updateprojectaccount(projectaccountForm) {
  return request({
    url: '/project/account/detail',
    method: 'put',
    data: projectaccountForm
  })
}

export function removeprojectaccount(projectaccountId) {
  return request({
    url: '/project/account/' + projectaccountId,
    method: 'delete'
  })
}
