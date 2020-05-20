import request from '@/utils/request'

export function gettesterList(params) {
  return request({
    url: '/tester',
    method: 'get',
    params
  })
}

export function search(testerForm) {
  return request({
    url: '/tester/search',
    method: 'post',
    data: testerForm
  })
}

export function addtester(testerForm) {
  return request({
    url: '/tester',
    method: 'post',
    data: testerForm
  })
}

export function updatetester(testerForm) {
  return request({
    url: '/tester/detail',
    method: 'put',
    data: testerForm
  })
}

export function removetester(testerId) {
  return request({
    url: '/tester/' + testerId,
    method: 'delete'
  })
}
