import request from '@/utils/request'

export function getapicasesdataList(params) {
  return request({
    url: '/api/casedata',
    method: 'get',
    params
  })
}

export function search(apicasesdataForm) {
  return request({
    url: '/api/casedata/search',
    method: 'post',
    data: apicasesdataForm
  })
}

export function updatepropertydata(apicasesdataForm) {
  return request({
    url: '/api/casedata/updatepropertydata',
    method: 'post',
    data: apicasesdataForm
  })
}

export function getparamvaluebycaseidandtype(params) {
  return request({
    url: '/api/casedata/casevalue',
    method: 'post',
    data: params
  })
}

export function casevalueforbody(params) {
  return request({
    url: '/api/casedata/casevalueforbody',
    method: 'post',
    data: params
  })
}
// export function deleparamvaluebycaseidandtype(params) {
//   return request({
//     url: '/api/casedata/delecasevalue',
//     method: 'delete',
//     data: params
//   })
// }

export function addapicasesdata(apicasesdataForm) {
  return request({
    url: '/api/casedata',
    method: 'post',
    data: apicasesdataForm
  })
}

export function updateapicasesdata(apicasesdataForm) {
  return request({
    url: '/api/casedata/detail',
    method: 'put',
    data: apicasesdataForm
  })
}

export function removeapicasesdata(apicasesdataId) {
  return request({
    url: '/api/casedata/' + apicasesdataId,
    method: 'delete'
  })
}
