import request from '@/utils/request'

export function getDicList(params) {
  return request({
    url: '/dictionary',
    method: 'get',
    params
  })
}

export function getdatabydiccodeList(params) {
  return request({
    url: '/dictionary/apisearch',
    method: 'get',
    params
  })
}

export function search(searchForm1) {
  return request({
    url: '/dictionary/search',
    method: 'post',
    data: searchForm1
  })
}

export function addDic(dicForm) {
  return request({
    url: '/dictionary',
    method: 'post',
    data: dicForm
  })
}

export function updateDic(dicForm) {
  return request({
    url: '/dictionary/detail',
    method: 'put',
    data: dicForm
  })
}

export function removeDic(DicId) {
  return request({
    url: '/dictionary/' + DicId,
    method: 'delete'
  })
}
