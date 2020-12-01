import request from '@/utils/request'

export function getapicases_conditionList(params) {
  return request({
    url: '/apicases_condition',
    method: 'get',
    params
  })
}

export function search(apicases_conditionForm) {
  return request({
    url: '/apicases_condition/search',
    method: 'post',
    data: apicases_conditionForm
  })
}

export function addapicases_condition(apicases_conditionForm) {
  return request({
    url: '/apicases_condition',
    method: 'post',
    data: apicases_conditionForm
  })
}

export function updateapicases_condition(apicases_conditionForm) {
  return request({
    url: '/apicases_condition/detail',
    method: 'put',
    data: apicases_conditionForm
  })
}

export function removeapicases_condition(apicases_conditionId) {
  return request({
    url: '/apicases_condition/' + apicases_conditionId,
    method: 'delete'
  })
}
