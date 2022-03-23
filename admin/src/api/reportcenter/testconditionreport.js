import request from '@/utils/request'

export function gettestconditionreportList(params) {
  return request({
    url: '/testcondition/report',
    method: 'get',
    params
  })
}

export function gettestconditionreportnum() {
  return request({
    url: '/testcondition/report/gettestconditionreportnum',
    method: 'get'
  })
}

export function gettestconditionreportallList() {
  return request({
    url: '/testcondition/report/getvariableslist',
    method: 'get'
  })
}

export function search(testconditionreportForm) {
  return request({
    url: '/testcondition/report/search',
    method: 'post',
    data: testconditionreportForm
  })
}

export function findconditionreport(testconditionreportForm) {
  return request({
    url: '/testcondition/report/findconditionreport',
    method: 'post',
    data: testconditionreportForm
  })
}

export function addtestconditionreport(testconditionreportForm) {
  return request({
    url: '/testcondition/report',
    method: 'post',
    data: testconditionreportForm
  })
}

export function updatetestconditionreport(testconditionreportForm) {
  return request({
    url: '/testcondition/report/detail',
    method: 'put',
    data: testconditionreportForm
  })
}

export function removetestconditionreport(testconditionreportId) {
  return request({
    url: '/testcondition/report/' + testconditionreportId,
    method: 'delete'
  })
}
