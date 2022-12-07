<template>
  <div class="dashboard-editor-container">

    <panel-group @handleSetLineChartData="handleSetLineChartData"/>

    <el-row style="background:#ffffff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData"/>
    </el-row>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <raddar-chart/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <bar-chart/>
        </div>
      </el-col>
    </el-row>
    <!--仪表盘-->
    <el-row :gutter="32" v-if="flag">
      <el-col :xs="24" :sm="24" :lg="6">
        <div class="chart-wrapper">
          <simple-gauge seriesName="总数据量" :seriesValue="form.totalNum"/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="6">
        <div class="chart-wrapper">
          <simple-gauge seriesName="发布项目数" :seriesValue="form.projectNum"/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="6">
        <div class="chart-wrapper">
          <simple-gauge seriesName="结构物数量" :seriesValue="form.sNum"/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="6">
        <div class="chart-wrapper">
          <simple-gauge seriesName="设备总数" :seriesValue="form.eNum"/>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="32" v-if="flag">
      <div class="chart-wrapper" style="background-color: #97a8be">
        <CityMap :mapPointData="form.projectArr"/>
      </div>
    </el-row>

<!--    <el-row :gutter="32">-->
<!--      <div class="main_con">-->
<!--        <div style="text-align: left;">-->
<!--          <H2>日历</H2>-->
<!--          <el-divider></el-divider>-->
<!--        </div>-->
<!--        <el-calendar>-->
<!--          <template slot="dateCell" slot-scope="{date, data}">-->
<!--            <div @click="dataClickFun(data)">-->
<!--              {{ data.day.split('-').slice(1).join('-') }}-->
<!--              <div  style="width:100%;"  v-for="(item, index) in scheduleData"  :key="index" >-->
<!--                <el-tag type="danger" v-if="(item.workingDay).indexOf(data.day)!=-1">-->
<!--                  {{ item.content }}-->
<!--                </el-tag>-->
<!--                <el-tooltip content="Top center" placement="top"></el-tooltip>-->
<!--              </div>-->
<!--            </div>-->

<!--          </template>-->
<!--        </el-calendar>-->
<!--      </div>-->

<!--    </el-row>-->
  </div>
</template>

<script>
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'
import SimpleGauge from './business/SimpleGauge'
import SolutionForm from './business/solution/solutionForm'
import CityMap from './business/CityMap'

import {getStatisticsFun} from "@/api/business/statistics";


const lineChartData = {
  newVisitis: {
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
  },
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
}

export default {
  name: 'Index',
  components: {
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart,
    SimpleGauge,
    SolutionForm,
    CityMap
  },
  data() {
    return {
      lineChartData: lineChartData.newVisitis,
      form: {},
      flag: false,
      value: new Date(),
      scheduleData: [
        {
          workingDay: "2022-09-02",
          content: "您有一条备忘信息，点击查看",
        },
        {
          workingDay: "2022-09-03",
          content: "地基验坑",
        },
        {
          workingDay: "2022-09-04",
          content: "地基回填",
        },
        {
          workingDay: "2022-09-05",
          content: "基础垫层模",
        },
        {
          workingDay: "2022-09-06",
          content: "基础垫层混凝土浇筑",
        },
        {
          workingDay: "2022-09-07",
          content: "基础钢筋绑扎",
        },
      ],
    }
  },
  created() {
    this.getStatistics();
  },
  methods: {
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    },
    //获取统计信息
    getStatistics() {
      getStatisticsFun().then(response => {
        this.$set(this.form, "totalNum", response.data.totalNum);
        this.$set(this.form, "projectNum", response.data.projectNum);
        this.$set(this.form, "sNum", response.data.sNum);
        this.$set(this.form, "eNum", response.data.eNum);
        this.$set(this.form, "projectArr", response.data.projectArr);
        this.flag = true;
      });
    },
    dataClickFun(data){


    },
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
