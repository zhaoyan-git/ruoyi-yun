import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listStrategy(query) {
  return request({
    url: '/business/strategy/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getStrategy(id) {
  return request({
    url: '/business/strategy/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addStrategy(data) {
  return request({
    url: '/business/strategy',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateStrategy(data) {
  return request({
    url: '/business/strategy',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delStrategy(id) {
  return request({
    url: '/business/strategy/' + id,
    method: 'delete'
  })
}

export function getStrategyByBusinessIdFun(query) {
  return request({
    url: '/business/strategy/getStrategyByBusinessId',
    method: 'get',
    params: query
  })
}
