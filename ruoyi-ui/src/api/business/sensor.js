import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listSensor(query) {
  return request({
    url: '/business/sensor/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getSensor(id) {
  return request({
    url: '/business/sensor/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addSensor(data) {
  return request({
    url: '/business/sensor',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateSensor(data) {
  return request({
    url: '/business/sensor',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delSensor(id) {
  return request({
    url: '/business/sensor/' + id,
    method: 'delete'
  })
}
