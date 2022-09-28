<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from '../dashboard/mixins/resize'

const animationDuration = 3000

export default {
  mixins: [resize],
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
      default: '300px'
    },
    seriesName: {
      type: String,
      default: ''
    },
    seriesValue: {
      type: String,
      default: "0"
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
      this.chart = echarts.init(this.$el, 'macarons')
      this.chart.setOption({
        tooltip: {
          formatter: '{b} : {c}'
        },
        series: [
          {
            type: 'gauge',
            max:500,
            // axisLine: {
            //   lineStyle: {
            //     width: 30,
            //     color: [
            //       [0.3, '#67e0e3'],
            //       [0.7, '#37a2da'],
            //       [1, '#fd666d']
            //     ]
            //   }
            // },
            // axisLabel: {
            //   color: 'auto',
            //   distance: 20,
            //   fontSize: 10
            // },
            progress: {
              show: true
            },
            detail: {
              valueAnimation: true,
              formatter: '{value}'
            },
            data: [
              {
                value: this.seriesValue,
                name: this.seriesName
              }
            ]
          }
        ]
      })
    }
  }
}
</script>
