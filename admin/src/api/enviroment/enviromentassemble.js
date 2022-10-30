import request from '@/utils/request'

export function getenviroment_assembleList(params) {
  return request({
    url: '/enviroment_assemble',
    method: 'get',
    params
  })
}

export function getenviroment_assembleallList() {
  return request({
    url: '/enviroment_assemble/ens',
    method: 'get'
  })
}

export function getassembleallnameList(params) {
  return request({
    url: '/enviroment_assemble/getassemblename',
    method: 'get',
    params
  })
}

export function searchenviroment_assemble(enviroment_assembleForm) {
  return request({
    url: '/enviroment_assemble/search',
    method: 'post',
    data: enviroment_assembleForm
  })
}

export function runtest(enviroment_assembleForm) {
  return request({
    url: '/enviroment_assemble/runtest',
    method: 'post',
    data: enviroment_assembleForm
  })
}

export function addenviroment_assemble(enviroment_assembleForm) {
  return request({
    url: '/enviroment_assemble',
    method: 'post',
    data: enviroment_assembleForm
  })
}

export function updateenviroment_assemble(enviroment_assembleForm) {
  return request({
    url: '/enviroment_assemble/detail',
    method: 'put',
    data: enviroment_assembleForm
  })
}

export function removeenviroment_assemble(enviroment_assembleId) {
  return request({
    url: '/enviroment_assemble/' + enviroment_assembleId,
    method: 'delete'
  })
}
