import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listCollector(query) {
  return request({
    url: '/business/collector/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getCollector(id) {
  return request({
    url: '/business/collector/' + id,
    method: 'get'
  })
}





// 新增【请填写功能名称】
export function addCollector(data) {
  return request({
    url: '/business/collector',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateCollector(data) {
  return request({
    url: '/business/collector',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delCollector(id) {
  return request({
    url: '/business/collector/' + id,
    method: 'delete'
  })
}
