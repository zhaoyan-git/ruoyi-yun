import request from '@/utils/request'

// 查询测点列表
export function listPoint(query) {
  return request({
    url: '/cmp/point/list',
    method: 'get',
    params: query
  })
}

// 查询测点详细
export function getPoint(id) {
  return request({
    url: '/cmp/point/' + id,
    method: 'get'
  })
}

// 新增测点
export function addPoint(data) {
  return request({
    url: '/cmp/point',
    method: 'post',
    data: data
  })
}

// 修改测点
export function updatePoint(data) {
  return request({
    url: '/cmp/point',
    method: 'put',
    data: data
  })
}

// 删除测点
export function delPoint(id) {
  return request({
    url: '/cmp/point/' + id,
    method: 'delete'
  })
}


export function getMeasuringPointTreeFun(query){
  return request({
    url: '/cmp/point/getMeasuringPointTree',
    method: 'get',
    params: query
  })
}
/**保存节点信息*/
export function saveNodeInfoFun(data){
  return request({
    url: '/cmp/point/saveNodeInfo',
    method: 'post',
    data: data
  })
}

export function deleteNodeInfoFun(data){
  return request({
    url: '/cmp/point/deleteNodeInfo',
    method: 'post',
    data: data
  })
}

