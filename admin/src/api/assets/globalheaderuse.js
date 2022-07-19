import request from '@/utils/request'

export function getapicasesdebugList(params) {
  return request({
    url: 'globalheaderuse/',
    method: 'get',
    params
  })
}

export function searchheaderbyepid(params) {
  return request({
    url: '/globalheaderuse/searchheaderbyepid',
    method: 'post',
    data: params
  })
}

export function getapicasesdebugnum() {
  return request({
    url: 'globalheaderuse//getapicasesdebugnum',
    method: 'get'
  })
}

export function getapicasesdebugallList() {
  return request({
    url: 'globalheaderuse//ens',
    method: 'get'
  })
}

export function searchheadernotexist(apicasesdebugForm) {
  return request({
    url: 'globalheaderuse/searchnotexit',
    method: 'post',
    data: apicasesdebugForm
  })
}

export function searchheaderexist(apicasesdebugForm) {
  return request({
    url: 'globalheaderuse/searchexit',
    method: 'post',
    data: apicasesdebugForm
  })
}

export function addheadercasesdebug(apicases) {
  return request({
    url: 'globalheaderuse/addcases',
    method: 'post',
    data: apicases
  })
}

export function deleteheadercase(apicases) {
  return request({
    url: 'globalheaderuse/deletacases',
    method: 'post',
    data: apicases
  })
}

export function addapicasesdebug(apicasesdebugForm) {
  return request({
    url: 'globalheaderuse/',
    method: 'post',
    data: apicasesdebugForm
  })
}

export function updateapicasesdebug(apicasesdebugForm) {
  return request({
    url: 'globalheaderuse/detail',
    method: 'put',
    data: apicasesdebugForm
  })
}

export function removeapicasesdebug(apicasesdebugId) {
  return request({
    url: 'globalheaderuse/' + apicasesdebugId,
    method: 'delete'
  })
}
