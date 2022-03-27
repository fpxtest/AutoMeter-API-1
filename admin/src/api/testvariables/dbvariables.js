import request from '@/utils/request'

export function getdbvariablesList(params) {
  return request({
    url: '/dbvariables',
    method: 'get',
    params
  })
}

export function getdbvariablesnum() {
  return request({
    url: '/dbvariables/getdbvariablesnum',
    method: 'get'
  })
}

export function getdbvariablesallList() {
  return request({
    url: '/dbvariables/getvariableslist',
    method: 'get'
  })
}

export function search(dbvariablesForm) {
  return request({
    url: '/dbvariables/search',
    method: 'post',
    data: dbvariablesForm
  })
}

export function adddbvariables(dbvariablesForm) {
  return request({
    url: '/dbvariables',
    method: 'post',
    data: dbvariablesForm
  })
}

export function updatedbvariables(dbvariablesForm) {
  return request({
    url: '/dbvariables/detail',
    method: 'put',
    data: dbvariablesForm
  })
}

export function removedbvariables(dbvariablesId) {
  return request({
    url: '/dbvariables/' + dbvariablesId,
    method: 'delete'
  })
}
