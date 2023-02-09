import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listDtu(query) {
  return request({
    url: '/business/dtu/list',
    method: 'get',
    params: query
  })
}

export function notPagelistDtu(query) {
  return request({
    url: '/business/dtu/notPageList',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getDtu(id) {
  return request({
    url: '/business/dtu/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addDtu(data) {
  return request({
    url: '/business/dtu',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateDtu(data) {
  return request({
    url: '/business/dtu',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delDtu(id) {
  return request({
    url: '/business/dtu/' + id,
    method: 'delete'
  })
}
