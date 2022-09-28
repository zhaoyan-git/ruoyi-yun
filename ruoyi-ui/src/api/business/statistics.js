import request from '@/utils/request'

// 查询统计详细
export function getStatisticsFun() {
  return request({
    url: '/business/statistics/',
    method: 'get'
  })
}







