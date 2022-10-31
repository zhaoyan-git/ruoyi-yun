import request from '@/utils/request'

// 查询新闻管理列表
export function listNewsmanagement(query) {
  return request({
    url: '/cmp/newsmanagement/list',
    method: 'get',
    params: query
  })
}

// 查询新闻管理详细
export function getNewsmanagement(id) {
  return request({
    url: '/cmp/newsmanagement/' + id,
    method: 'get'
  })
}

// 新增新闻管理
export function addNewsmanagement(data) {
  return request({
    url: '/cmp/newsmanagement',
    method: 'post',
    data: data
  })
}

// 修改新闻管理
export function updateNewsmanagement(data) {
  return request({
    url: '/cmp/newsmanagement',
    method: 'put',
    data: data
  })
}

// 删除新闻管理
export function delNewsmanagement(id) {
  return request({
    url: '/cmp/newsmanagement/' + id,
    method: 'delete'
  })
}
