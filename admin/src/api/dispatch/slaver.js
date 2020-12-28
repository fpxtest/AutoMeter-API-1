import request from '@/utils/request'

export function getslaverList(params) {
  return request({
    url: '/slaver',
    method: 'get',
    params
  })
}

export function getslavernum() {
  return request({
    url: '/slaver/getslavernum',
    method: 'get'
  })
}

export function search(slaverForm) {
  return request({
    url: '/slaver/search',
    method: 'post',
    data: slaverForm
  })
}

export function addslaver(slaverForm) {
  return request({
    url: '/slaver',
    method: 'post',
    data: slaverForm
  })
}

export function updateslaver(slaverForm) {
  return request({
    url: '/slaver/detail',
    method: 'put',
    data: slaverForm
  })
}

export function removeslaver(slaverId) {
  return request({
    url: '/slaver/' + slaverId,
    method: 'delete'
  })
}
