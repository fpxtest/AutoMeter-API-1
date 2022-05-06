import request from '@/utils/request'

export function searchcases(processForm) {
  return request({
    url: '/process/testcase/getcasebydeployandapi',
    method: 'post',
    data: processForm
  })
}

export function search(processForm) {
  return request({
    url: '/process/testcase/search',
    method: 'post',
    data: processForm
  })
}

export function getstaticsplancases() {
  return request({
    url: '/process/testcase/getstaticsplancases',
    method: 'get'
  })
}

export function addprocesstestcase(processForm) {
  return request({
    url: '/process/testcase/addcases',
    method: 'post',
    data: processForm
  })
}

export function processtestcase(processForm) {
  return request({
    url: '/process/testcase/execcases',
    method: 'post',
    data: processForm
  })
}

export function removebatchprocesstestcase(processForm) {
  return request({
    url: '/process/testcase/removecases',
    method: 'post',
    data: processForm
  })
}

export function removeprocesstestcase(processtestcaseId) {
  return request({
    url: '/process/testcase/' + processtestcaseId,
    method: 'delete'
  })
}
