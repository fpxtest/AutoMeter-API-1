import request from '@/utils/request'

export function getMyInfoList(params) {
  return request({
    url: '/MyInfo',
    method: 'get',
    params
  })
}

export function getMyInfonum() {
  return request({
    url: '/MyInfo/getMyInfonum',
    method: 'get'
  })
}

export function getMyInfoallList() {
  return request({
    url: '/MyInfo/ens',
    method: 'get'
  })
}

export function searchmycreateinfo(MyInfoForm) {
  return request({
    url: '/MyInfo/mycreateinfo',
    method: 'post',
    data: MyInfoForm
  })
}

export function searchmyruninfo(MyInfoForm) {
  return request({
    url: '/MyInfo/myruninfo',
    method: 'post',
    data: MyInfoForm
  })
}

export function searchmyrecentfunctioninfo(MyInfoForm) {
  return request({
    url: '/MyInfo/myrecentfunctioninfo',
    method: 'post',
    data: MyInfoForm
  })
}

export function searchmyrecentperfermanceinfo(MyInfoForm) {
  return request({
    url: '/MyInfo/myrecentperfermanceinfo',
    method: 'post',
    data: MyInfoForm
  })
}

export function addMyInfo(MyInfoForm) {
  return request({
    url: '/MyInfo',
    method: 'post',
    data: MyInfoForm
  })
}

export function updateMyInfo(MyInfoForm) {
  return request({
    url: '/MyInfo/detail',
    method: 'put',
    data: MyInfoForm
  })
}

export function removeMyInfo(MyInfoId) {
  return request({
    url: '/MyInfo/' + MyInfoId,
    method: 'delete'
  })
}
