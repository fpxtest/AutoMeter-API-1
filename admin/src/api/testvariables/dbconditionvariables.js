import request from '@/utils/request'

export function getdbconditionvariablesList(params) {
  return request({
    url: '/dbcondition/variables',
    method: 'get',
    params
  })
}

export function getdbconditionvariablesnum() {
  return request({
    url: '/dbcondition/variables/getdbconditionvariablesnum',
    method: 'get'
  })
}

export function getdbconditionvariablesallList() {
  return request({
    url: '/dbcondition/variables/getvariableslist',
    method: 'get'
  })
}

export function search(dbconditionvariablesForm) {
  return request({
    url: '/dbcondition/variables/search',
    method: 'post',
    data: dbconditionvariablesForm
  })
}

export function adddbconditionvariables(dbconditionvariablesForm) {
  return request({
    url: '/dbcondition/variables',
    method: 'post',
    data: dbconditionvariablesForm
  })
}

export function updatedbconditionvariables(dbconditionvariablesForm) {
  return request({
    url: '/dbcondition/variables/detail',
    method: 'put',
    data: dbconditionvariablesForm
  })
}

export function getbyvariablesid(DBconditionVariablesForm) {
  return request({
    url: '/dbcondition/variables/getbyvariablesid',
    method: 'post',
    data: DBconditionVariablesForm
  })
}

export function removedbconditionvariables(dbconditionvariablesId) {
  return request({
    url: '/dbcondition/variables/' + dbconditionvariablesId,
    method: 'delete'
  })
}
