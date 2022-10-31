import request from '@/utils/request'

// 查询客户案例列表
export function listCustomerstories(query) {
  return request({
    url: '/cmp/customerstories/list',
    method: 'get',
    params: query
  })
}

// 查询客户案例详细
export function getCustomerstories(id) {
  return request({
    url: '/cmp/customerstories/' + id,
    method: 'get'
  })
}

// 新增客户案例
export function addCustomerstories(data) {
  return request({
    url: '/cmp/customerstories',
    method: 'post',
    data: data
  })
}

// 修改客户案例
export function updateCustomerstories(data) {
  return request({
    url: '/cmp/customerstories',
    method: 'put',
    data: data
  })
}

// 删除客户案例
export function delCustomerstories(id) {
  return request({
    url: '/cmp/customerstories/' + id,
    method: 'delete'
  })
}
