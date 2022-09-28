import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listGateway(query) {
  return request({
    url: '/cmp/gateway/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getGateway(id) {
  return request({
    url: '/cmp/gateway/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addGateway(data) {
  return request({
    url: '/cmp/gateway',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateGateway(data) {
  return request({
    url: '/cmp/gateway',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delGateway(id) {
  return request({
    url: '/cmp/gateway/' + id,
    method: 'delete'
  })
}
