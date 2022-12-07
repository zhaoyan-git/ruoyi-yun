import request from '@/utils/request'

// 查询告警策略配置列表
export function listPolicy(query) {
  return request({
    url: '/cmp/policy/list',
    method: 'get',
    params: query
  })
}

// 查询告警策略配置详细
export function getPolicy(id) {
  return request({
    url: '/cmp/policy/' + id,
    method: 'get'
  })
}

// 新增告警策略配置
export function addPolicy(data) {
  return request({
    url: '/cmp/policy',
    method: 'post',
    data: data
  })
}

export function changeStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/cmp/policy/changeStatus',
    method: 'put',
    data: data
  })
}

// 修改告警策略配置
export function updatePolicy(data) {
  return request({
    url: '/cmp/policy',
    method: 'put',
    data: data
  })
}

// 删除告警策略配置
export function delPolicy(id) {
  return request({
    url: '/cmp/policy/' + id,
    method: 'delete'
  })
}
