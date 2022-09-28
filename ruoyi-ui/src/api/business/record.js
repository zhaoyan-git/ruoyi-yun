import request from '@/utils/request'

// 查询告警记录列表
export function listRecord(query) {
  return request({
    url: '/cmp/record/list',
    method: 'get',
    params: query
  })
}

// 查询告警记录详细
export function getRecord(id) {
  return request({
    url: '/cmp/record/' + id,
    method: 'get'
  })
}

// 新增告警记录
export function addRecord(data) {
  return request({
    url: '/cmp/record',
    method: 'post',
    data: data
  })
}

// 修改告警记录
export function updateRecord(data) {
  return request({
    url: '/cmp/record',
    method: 'put',
    data: data
  })
}

// 删除告警记录
export function delRecord(id) {
  return request({
    url: '/cmp/record/' + id,
    method: 'delete'
  })
}
