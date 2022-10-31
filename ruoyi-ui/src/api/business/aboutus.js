import request from '@/utils/request'

// 查询关于我们列表
export function listAboutus(query) {
  return request({
    url: '/cmp/aboutus/list',
    method: 'get',
    params: query
  })
}

// 查询关于我们详细
export function getAboutus(id) {
  return request({
    url: '/cmp/aboutus/' + id,
    method: 'get'
  })
}

// 新增关于我们
export function addAboutus(data) {
  return request({
    url: '/cmp/aboutus',
    method: 'post',
    data: data
  })
}

// 修改关于我们
export function updateAboutus(data) {
  return request({
    url: '/cmp/aboutus',
    method: 'put',
    data: data
  })
}

// 删除关于我们
export function delAboutus(id) {
  return request({
    url: '/cmp/aboutus/' + id,
    method: 'delete'
  })
}
