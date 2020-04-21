import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/test',
    method: 'get',
    params
  })
}
