import request from '@/utils/request'

export function getprojectList(params) {
  return request({
    url: '/project',
    method: 'get',
    params
  })
}

export function getprojectnum() {
  return request({
    url: '/project/getprojectnum',
    method: 'get'
  })
}

export function getprojectallList() {
  return request({
    url: '/project/ens',
    method: 'get'
  })
}

export function search(projectForm) {
  return request({
    url: '/project/search',
    method: 'post',
    data: projectForm
  })
}

export function addproject(projectForm) {
  return request({
    url: '/project',
    method: 'post',
    data: projectForm
  })
}

export function updateproject(projectForm) {
  return request({
    url: '/project/detail',
    method: 'put',
    data: projectForm
  })
}

export function removeproject(projectId) {
  return request({
    url: '/project/' + projectId,
    method: 'delete'
  })
}
