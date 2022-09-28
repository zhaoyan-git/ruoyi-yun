import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listStructure(query) {
  return request({
    url: '/business/structure/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getStructure(id) {
  return request({
    url: '/business/structure/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addStructure(data) {
  return request({
    url: '/business/structure',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateStructure(data) {
  return request({
    url: '/business/structure',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delStructure(id) {
  return request({
    url: '/business/structure/' + id,
    method: 'delete'
  })
}
export function updateStructureForEquipmentFun(data) {
  return request({
    url: '/business/structure/updateStructureForEquipment',
    method: 'put',
    data: data
  })
}

// 新增【请填写功能名称】
export function insertMFFun(data) {
  return request({
    url: '/business/structure/insertMF',
    method: 'post',
    data: data
  })
}

export function getMFInfoFun(id) {
  return request({
    url: '/business/structure/getMFInfo' + id,
    method: 'get'
  })
}





