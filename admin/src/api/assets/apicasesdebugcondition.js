import request from '@/utils/request'

export function getapicasesdebugconditionList(params) {
  return request({
    url: '/apicases/debug/condition',
    method: 'get',
    params
  })
}

export function getapicasesdebugconditionnum() {
  return request({
    url: '/apicases/debug/condition/getapicasesdebugconditionnum',
    method: 'get'
  })
}

export function getapicasesdebugconditionallList() {
  return request({
    url: '/apicases/debug/condition/ens',
    method: 'get'
  })
}

export function searchnotexist(apicasesdebugconditionForm) {
  return request({
    url: '/apicases/debug/condition/searchnotexit',
    method: 'post',
    data: apicasesdebugconditionForm
  })
}

export function searchexist(apicasesdebugconditionForm) {
  return request({
    url: '/apicases/debug/condition/searchexit',
    method: 'post',
    data: apicasesdebugconditionForm
  })
}

export function addcasesdebugcondition(apicases) {
  return request({
    url: '/apicases/debug/condition/addcases',
    method: 'post',
    data: apicases
  })
}

export function delatedebugconditiontestcase(apicases) {
  return request({
    url: '/apicases/debug/condition/deletacases',
    method: 'post',
    data: apicases
  })
}

export function addapicasesdebugcondition(apicasesdebugconditionForm) {
  return request({
    url: '/apicases/debug/condition',
    method: 'post',
    data: apicasesdebugconditionForm
  })
}

export function updateapicasesdebugcondition(apicasesdebugconditionForm) {
  return request({
    url: '/apicases/debug/condition/detail',
    method: 'put',
    data: apicasesdebugconditionForm
  })
}

export function removeapicasesdebugcondition(apicasesdebugconditionId) {
  return request({
    url: '/apicases/debug/condition/' + apicasesdebugconditionId,
    method: 'delete'
  })
}
