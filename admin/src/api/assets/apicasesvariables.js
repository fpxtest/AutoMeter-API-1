import request from '@/utils/request'

export function getApicasesVariablesList(params) {
  return request({
    url: '/apicases/variables',
    method: 'get',
    params
  })
}

export function getApicasesVariablesnum() {
  return request({
    url: '/apicases/variables/getApicasesVariablesnum',
    method: 'get'
  })
}

export function getApicasesVariablesallList() {
  return request({
    url: '/apicases/variables/ens',
    method: 'get'
  })
}

export function search(ApicasesVariablesForm) {
  return request({
    url: '/apicases/variables/search',
    method: 'post',
    data: ApicasesVariablesForm
  })
}

export function getbyvariablesid(ApicasesVariablesForm) {
  return request({
    url: '/apicases/variables/getbyvariablesid',
    method: 'post',
    data: ApicasesVariablesForm
  })
}

export function addApicasesVariables(ApicasesVariablesForm) {
  return request({
    url: '/apicases/variables',
    method: 'post',
    data: ApicasesVariablesForm
  })
}

export function updateApicasesVariables(ApicasesVariablesForm) {
  return request({
    url: '/apicases/variables/detail',
    method: 'put',
    data: ApicasesVariablesForm
  })
}

export function removeApicasesVariables(ApicasesVariablesId) {
  return request({
    url: '/apicases/variables/' + ApicasesVariablesId,
    method: 'delete'
  })
}
