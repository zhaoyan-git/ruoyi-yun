import request from '@/utils/request'

// 查询解决方案列表
export function listSolution(query) {
  return request({
    url: '/cmp/solution/list',
    method: 'get',
    params: query
  })
}

// 查询解决方案详细
export function getSolution(id) {
  return request({
    url: '/cmp/solution/' + id,
    method: 'get'
  })
}

// 新增解决方案
export function addSolution(data) {
  return request({
    url: '/cmp/solution',
    method: 'post',
    data: data
  })
}

// 修改解决方案
export function updateSolution(data) {
  return request({
    url: '/cmp/solution',
    method: 'put',
    data: data
  })
}

// 删除解决方案
export function delSolution(id) {
  return request({
    url: '/cmp/solution/' + id,
    method: 'delete'
  })
}
