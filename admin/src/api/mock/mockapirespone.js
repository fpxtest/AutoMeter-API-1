import request from '@/utils/request'

export function getmockapiresponeList(params) {
  return request({
    url: '/mockapirespone',
    method: 'get',
    params
  })
}

export function getmockapiresponenum() {
  return request({
    url: '/mockapirespone/getmockapiresponenum',
    method: 'get'
  })
}

export function getmockapiresponeallList() {
  return request({
    url: '/mockapirespone',
    method: 'get'
  })
}

export function search(mockapiresponeForm) {
  return request({
    url: '/mockapirespone/search',
    method: 'post',
    data: mockapiresponeForm
  })
}

export function addmockapirespone(mockapiresponeForm) {
  return request({
    url: '/mockapirespone',
    method: 'post',
    data: mockapiresponeForm
  })
}

export function enablerespone(mockapiresponeForm) {
  return request({
    url: '/mockapirespone/enablerespone',
    method: 'post',
    data: mockapiresponeForm
  })
}

export function updatemockapirespone(mockapiresponeForm) {
  return request({
    url: '/mockapirespone/detail',
    method: 'put',
    data: mockapiresponeForm
  })
}

export function removemockapirespone(mockapiresponeId) {
  return request({
    url: '/mockapirespone/' + mockapiresponeId,
    method: 'delete'
  })
}
