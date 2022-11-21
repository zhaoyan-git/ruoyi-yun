<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" >
        <div class="card-panel-icon-wrapper">
          <div class="card-panel-text-left">
            激活
          </div>
        </div>
        <div class="card-panel-description">
          <count-to :start-val="0" :end-val="1" :duration="2600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" >
        <div class="card-panel-icon-wrapper">
          <div class="card-panel-text-left">
            当月在线
          </div>
        </div>
        <div class="card-panel-description">
          <count-to :start-val="0" :end-val="100" :duration="3000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="12" class="card-panel-col">
      <div class="card-panel" >
        <div class="card-panel-icon-wrapper icon-money">

        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">

          </div>

        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="12" class="card-panel-col">
      <div class="" >
        <el-row :gutter="32">
          <el-col :xs="24" :sm="24" :lg="24">
            <div class="card-panel" >
              <div class="card-panel-icon-wrapper">
                <div class="card-panel-text-left">
                  快捷进入
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="32">
          <el-col :xs="24" :sm="24" :lg="12">
            <div class="card-panel" >
              <div class="card-panel-icon-wrapper icon-message">
                <i class="el-icon-coordinate"></i>
                监控中心
              </div>
            </div>
          </el-col>
          <el-col :xs="24" :sm="24" :lg="12">
            <div class="card-panel" >
              <div class="card-panel-icon-wrapper icon-message">
                <i class="el-icon-data-line"></i>
                电子围栏
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="32">
          <el-col :xs="24" :sm="24" :lg="12">
            <div class="card-panel" >
              <div class="card-panel-icon-wrapper icon-message">
                <i class="el-icon-shopping-bag-1"></i>
               健康数据
              </div>
            </div>
          </el-col>
          <el-col :xs="24" :sm="24" :lg="12">
            <div class="card-panel" >
              <div class="card-panel-icon-wrapper icon-message">
                <i class="el-icon-setting"></i>
                设备管理
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="32">
          <el-col :xs="24" :sm="24" :lg="12">
            <div class="card-panel" >
              <div class="card-panel-icon-wrapper icon-message">
                <i class="el-icon-thumb"></i>
               添加设备
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="12" class="card-panel-col">
      <div class="" >
        <el-row :gutter="32">
          <el-col :xs="24" :sm="24" :lg="24">
            <div class="card-panel" >
              <div class="card-panel-icon-wrapper">
                <div class="card-panel-text-left">
                  报警
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="32">
          <el-col :xs="24" :sm="24" :lg="24">
            <div class="card-panel" >
              <div class="card-panel-icon-wrapper icon-message">
                <el-button type="danger" round>清除</el-button>
              </div>
              <div class="card-panel-icon-wrapper icon-message">
                是否开启声音
                <el-switch
                  v-model="value"
                  class="switch"
                  :active-value="1"
                  :inactive-value="0"
                  active-text="开"
                  inactive-text="关"
                />
              </div>
              <div class="card-panel-icon-wrapper icon-message">
                <el-button type="danger" round>设置</el-button>
              </div>
            </div>
          </el-col>

        </el-row>
        <el-row :gutter="32">
          <el-col :xs="24" :sm="24" :lg="24">
            <el-table
              :data="tableData"
              height="450"
              border
              style="width: 100%">
              <el-table-column
                prop="name"
                label="姓名"
                :show-overflow-tooltip="true"
                width="180">
              </el-table-column>
              <el-table-column
                prop="alarmType"
                label="报警类型"
                :show-overflow-tooltip="true"
                width="180">
              </el-table-column>
              <el-table-column
                prop="Imei"
                :show-overflow-tooltip="true"
                label="Imei">
              </el-table-column>
              <el-table-column
                prop="alarm"
                :show-overflow-tooltip="true"
                label="报警">
              </el-table-column>
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="danger"
                    icon="el-icon-delete"
                    @click="handleDelete(scope.row)"
                    v-hasPermi="['cmp:solution:remove']"
                  >清除</el-button>
                  <el-button
                    size="mini"
                    type="warning"
                    icon="el-icon-edit"
                    @click="handleUpdate(scope.row)"
                    v-hasPermi="['cmp:solution:edit']"
                  >报警记录</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </div>
    </el-col>
  </el-row>



</template>

<script>
import CountTo from 'vue-count-to'

export default {
  components: {
    CountTo
  },
  data() {
    return {
      value: false,
      tableData: [
        {name: 'HT220221121001HT220221121001HT220221121001',alarmType: '低电提醒',Imei: '19950220262023323223',alarm:'202211212120001'},
        {name: 'HT220221121002',alarmType: '低电提醒',Imei: '19950220262023323223',alarm:'202211212120001'},
        {name: 'HT220221121003',alarmType: '低电提醒',Imei: '19950220262023323223',alarm:'202211212120001'},
        {name: 'HT220221121004',alarmType: '心率异常提醒',Imei: '19950220262023323223',alarm:'202211212120001'},
        {name: 'HT220221121005',alarmType: '心率异常提醒',Imei: '19950220262023323223',alarm:'202211212120001'},
        {name: 'HT220221121006',alarmType: '摔倒提醒',Imei: '19950220262023323223',alarm:'202211212120001'},
        {name: 'HT220221121007',alarmType: '摔倒提醒',Imei: '19950220262023323223',alarm:'202211212120001'},
        {name: 'HT220221121008',alarmType: '摔倒提醒',Imei: '19950220262023323223',alarm:'202211212120001'},
        {name: 'HT220221121009',alarmType: '心率异常提醒',Imei: '19950220262023323223',alarm:'202211212120001'},
        {name: 'HT220221121010',alarmType: '心率异常提醒',Imei: '19950220262023323223',alarm:'202211212120001'},
        {name: 'HT220221121011',alarmType: '心率异常提醒',Imei: '19950220262023323223',alarm:'202211212120001'},
        {name: 'HT220221121012',alarmType: '心率异常提醒',Imei: '19950220262023323223',alarm:'202211212120001'},
        {name: 'HT220221121013',alarmType: '心率异常提醒',Imei: '19950220262023323223',alarm:'202211212120001'},
        {name: 'HT220221121014',alarmType: '心率异常提醒',Imei: '19950220262023323223',alarm:'202211212120001'},
        {name: 'HT220221121015',alarmType: '心率异常提醒',Imei: '19950220262023323223',alarm:'202211212120001'},
        ],
    }
  },
  methods: {

  }
}
</script>

<style lang="scss" scoped>
.panel-group {
  margin-top: 0px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 88px;
    cursor: pointer;
    font-size: 26px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fdb933;
      }
    }
    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-text-left {
        color: rgba(0, 0, 0, 0.45);
        font-size: 36px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width:550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

  }
}
</style>
