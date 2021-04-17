import request from '@/utils/request'

export function searchcases(executeplanForm) {
  return request({
    url: '/executeplan/testcase/getcasebydeployandapi',
    method: 'post',
    data: executeplanForm
  })
}

export function getstaticsplancases() {
  return request({
    url: '/executeplan/testcase/getstaticsplancases',
    method: 'get'
  })
}

export function addexecuteplantestcase(executeplanForm) {
  return request({
    url: '/executeplan/testcase/addcases',
    method: 'post',
    data: executeplanForm
  })
}

export function executeplantestcase(executeplanForm) {
  return request({
    url: '/executeplan/testcase/execcases',
    method: 'post',
    data: executeplanForm
  })
}

export function removeexecuteplantestcase(executeplanForm) {
  return request({
    url: '/executeplan/testcase/removecases',
    method: 'post',
    data: executeplanForm
  })
}
