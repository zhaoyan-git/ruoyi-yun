import request from '@/utils/request'

// 查询阈值配置列表
export function listConfiguration(query) {
  return request({
    url: '/cmp/configuration/list',
    method: 'get',
    params: query
  })
}

// 查询阈值配置详细
export function getConfiguration(id) {
  return request({
    url: '/cmp/configuration/' + id,
    method: 'get'
  })
}

// 新增阈值配置
export function addConfiguration(data) {
  return request({
    url: '/cmp/configuration',
    method: 'post',
    data: data
  })
}

// 修改阈值配置
export function updateConfiguration(data) {
  return request({
    url: '/cmp/configuration',
    method: 'put',
    data: data
  })
}

// 删除阈值配置
export function delConfiguration(id) {
  return request({
    url: '/cmp/configuration/' + id,
    method: 'delete'
  })
}
