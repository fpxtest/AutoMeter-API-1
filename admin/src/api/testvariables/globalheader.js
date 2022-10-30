import request from '@/utils/request'

export function getglobalheaderList(params) {
  return request({
    url: '/globalheader',
    method: 'get',
    params
  })
}

export function getglobalheadernum() {
  return request({
    url: '/globalheader/getglobalheadernum',
    method: 'get'
  })
}

export function getglobalheaderallList(params) {
  return request({
    url: '/globalheader/getvariableslist',
    method: 'get',
    params
  })
}

export function search(globalheaderForm) {
  return request({
    url: '/globalheader/search',
    method: 'post',
    data: globalheaderForm
  })
}

export function addglobalheader(globalheaderForm) {
  return request({
    url: '/globalheader',
    method: 'post',
    data: globalheaderForm
  })
}

export function updateglobalheader(globalheaderForm) {
  return request({
    url: '/globalheader/detail',
    method: 'put',
    data: globalheaderForm
  })
}

export function removeglobalheader(globalheaderId) {
  return request({
    url: '/globalheader/' + globalheaderId,
    method: 'delete'
  })
}
