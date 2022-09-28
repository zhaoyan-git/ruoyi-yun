<template>
  <div id="china-map"  :class="className" :style="{height:height,width:width}" />
</template>

<script>
// 5.0以后
import * as echarts from 'echarts';
import JSON from '../business/json/china.json';



export default {
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '650px'
    },
    mapPointData : {
      type: Array,
      default: () =>[]
    },
  },
  data() {
    return {
      chart: null
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {

      this.mapOption= {
        color: ['#5470c6'],
        tooltip: {
          trigger: 'item',
          renderMode: 'html',
          // 触发方式
          triggerOn: 'mousemove',
          enterable: true,
          backgroundColor: '#fff',
          padding: 0,
          textStyle: {
            color: '#000',
            fontSize: '12'
          },
          extraCssText: 'box-shadow: 0 0 3px rgba(0, 0, 0, 0.3);'
        },
        dispatchAction: {
          type: 'downplay'
        },
        roam: false,
        roamController: {
          show: true,
          x: 'right',
          mapTypeControl: {
            china: true
          }
        },
        series: [
          {
            name: '',
            type: 'scatter',
            coordinateSystem: 'geo',
            color: ['#FF6347'],
            tooltip: {
              position: "right",
              color: "#000",
              formatter(d) {
                return `<div style="padding: 5px 10px;">${d.data.name}</div>`;
              },
            },
            data: this.mapPointData,
          }
        ],
        geo: {
          show: true,
          map: 'china',
          type: 'map',
          mapType: 'china',
          roam: false,
          label: {
            normal: {
              // 显示省份标签
              show: true,
              textStyle: {
                color: '#444444',
                fontSize: 13
              }
            },
            emphasis: {
              // 对应的鼠标悬浮效果
              show: true,
              // 选中后的字体样式
              textStyle: {
                color: '#F7760D',
                fontSize: 18
              }
            }
          },
          itemStyle: {
            color: '#ddb926',
            normal: {
              areaColor: '#8abcd1',
              borderColor: '#fff',
              borderWidth: 1
            },
            emphasis: {
              borderWidth: 0.5,
              borderColor: '#8abcd1',
              areaColor: '#99BBFF'
            }
          },
          emphasis: {
            label: {
              show: false
            }
          }
        }
      }

      echarts.registerMap('china', JSON, {});
      const myChart = echarts.init(
        document.getElementById('china-map'),
      );
      myChart.setOption(this.mapOption);
      window.addEventListener('resize', () => {
        myChart.resize();
      });
    }
  }
}
</script>
