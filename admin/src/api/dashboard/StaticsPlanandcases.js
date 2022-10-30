import request from '@/utils/request'

export function getStaticsPlanCasesList(params) {
  return request({
    url: '/statics/planandcases/getplanstatics',
    method: 'get',
    params
  })
}

export function getStaticsDeployUnitCasesList(params) {
  return request({
    url: '/statics/deployunitandcases/getdeployunitstatics',
    method: 'get',
    params
  })
}

export function getStaticsgetlastdays() {
  return request({
    url: '/statics/planandcases/getlastdays',
    method: 'get'
  })
}

export function search(searchForm1) {
  return request({
    url: '/statics/planandcases/search',
    method: 'post',
    data: searchForm1
  })
}

export function addStaticsPlanCases(dicForm) {
  return request({
    url: '/statics/planandcases',
    method: 'post',
    data: dicForm
  })
}

export function updateStaticsPlanCases(dicForm) {
  return request({
    url: '/statics/planandcases/detail',
    method: 'put',
    data: dicForm
  })
}

export function removeStaticsPlanCases(StaticsPlanCasesId) {
  return request({
    url: '/statics/planandcases/' + StaticsPlanCasesId,
    method: 'delete'
  })
}
