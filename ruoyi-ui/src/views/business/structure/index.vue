<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="85px">
      <el-form-item label="结构物类型" prop="typeId">
        <el-select
          v-model="queryParams.typeId"
          clearable
          @keyup.enter.native="handleQuery"
          placeholder="请选择结构物类型"
        >
          <el-option
            v-for="dict in dict.type.structure_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所属项目" prop="projectId">
        <el-select
          v-model="queryParams.projectId"
          clearable
          @keyup.enter.native="handleQuery"
          placeholder="请选择所属项目"
        >
          <el-option
            v-for="dict in projectList"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="警报标志" prop="alarmFlag">
        <el-select
          v-model="queryParams.alarmFlag"
          clearable
          @keyup.enter.native="handleQuery"
          placeholder="请选择警报标志"
        >
          <el-option
            v-for="dict in dict.type.alarm_flag"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['business:structure:add']"
        >新增
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-row :gutter="32">
      <el-col :span="6" v-for="(item,index) in structureList">
        <div>
          <div class="image_div">
            <ImagePreview :src="item.photoFile" width="100%;" height="100%"></ImagePreview>
          </div>
          <div class="text_div">
            <div><p>{{ item.name }}</p></div>
            <div><p>所属项目:{{ item.projectName }}</p></div>
            <div>
              <el-button
                type="success"
                plain
                icon="el-icon-edit"
                size="mini"
                @click="handleUpdate(item)"
                v-hasPermi="['business:structure:edit']"
              >配置
              </el-button>
              <el-button
                type="danger"
                plain
                icon="el-icon-delete"
                size="mini"
                @click="deleteStructureFun(item)"
                v-hasPermi="['business:structure:remove']"
              >删除
              </el-button>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 添加或修改结构物对话框 -->
    <el-dialog :title="title" :visible.sync="open"   width="1100px" append-to-body  >
      <div >
        <el-tabs v-model="activeTab" type="border-card" @tab-click="switchTabFun" v-if="open">
          <el-tab-pane name="baseInfo" label="基础信息" v-if="baseInfo">
            <div >
              <el-form ref="form" :model="form" :rules="rules" label-width="90px">
                <el-row>
                  <el-col :span="12">
                    <el-form-item label="名称" prop="name" class="baseFormClass">
                      <el-input v-model="form.name" placeholder="请输入结构物名称"/>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="地址" prop="location" class="baseFormClass">
                      <el-input v-model="form.location" placeholder="请输入地址"/>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="12">
                    <el-form-item label="精度" prop="positionLat" class="baseFormClass">
                      <el-input v-model="form.positionLat" placeholder="请输入精度"/>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="纬度" prop="positionLng" class="baseFormClass">
                      <el-input v-model="form.positionLng" placeholder="请输入纬度"/>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="12">
                    <el-form-item label="监测目的" prop="monitoringPurpose" class="baseFormClass">
                      <el-input v-model="form.monitoringPurpose" type="textarea" placeholder="请输入监测目的"/>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="监测依据" prop="monitoringBasis" class="baseFormClass">
                      <el-input v-model="form.monitoringBasis" placeholder="请输入监测依据"/>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="示意图" class="baseFormClass">
                      <div style="text-align: left">
                        <image-upload v-model="form.photoFile" :value="fileList"/>
                      </div>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="描述" prop="dedc" class="baseFormClass">
                      <el-input v-model="form.dedc" type="textarea" placeholder="请输入描述"/>
                    </el-form-item>
                  </el-col>

                </el-row>
                <el-row>
                  <el-col :span="8">
                    <el-form-item label="施工单位" prop="constructionUnit" class="baseFormClass">
                      <el-input v-model="form.constructionUnit" placeholder="请输入施工单位"/>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="联系人" prop="constructionPserson" class="baseFormClass">
                      <el-input v-model="form.constructionPserson" placeholder="请输入联系人"/>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="联系电话" prop="constructionPhone" class="baseFormClass">
                      <el-input v-model="form.constructionPhone" placeholder="请输入联系电话"/>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="8">
                    <el-form-item label="监理单位" prop="manageUnit" class="baseFormClass">
                      <el-input v-model="form.manageUnit" placeholder="请输入监理单位"/>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="联系人" prop="managePerson" class="baseFormClass">
                      <el-input v-model="form.managePerson" placeholder="请输入联系人"/>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="联系电话" prop="managePhone" class="baseFormClass">
                      <el-input v-model="form.managePhone" placeholder="请输入联系电话"/>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="12">
                    <el-form-item label="所属项目" prop="projectId" class="baseFormClass">
                      <div style="text-align: left">
                        <el-select
                          v-model="form.projectId"
                          clearable
                          placeholder="请选择所属项目"
                        >
                          <el-option
                            v-for="dict in projectList"
                            :key="dict.value"
                            :label="dict.label"
                            :value="dict.value"
                          />
                        </el-select>
                      </div>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="监测模型" prop="typeId" class="baseFormClass">
                      <div style="text-align: left">
                        <el-select
                          v-model="form.typeId"
                          clearable
                          placeholder="请选择监测模型"
                        >
                          <el-option
                            v-for="dict in dict.type.structure_type"
                            :key="dict.value"
                            :label="dict.label"
                            :value="parseInt(dict.value)"
                          />
                        </el-select>
                      </div>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
              <div slot="footer" class="dialog-footer" >
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
              </div>
            </div>

          </el-tab-pane>
          <el-tab-pane name="sbInfo" label="设备组网" v-if="sbInfo">
            <div >
              <el-row :gutter="10" class="mb8">
                <el-col :span="1.5">
                  <el-button
                    type="primary"
                    plain
                    icon="el-icon-plus"
                    size="mini"
                    @click="addEquipmentFun"
                    v-hasPermi="['business:structure:add']"
                  >新增设备
                  </el-button>
                </el-col>
                <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
              </el-row>
              <el-table v-loading="loading" :data="equipment.equipmentList" @selection-change="handleSelectionChange">
                <el-table-column label="设备名称" align="center" prop="name"/>
                <el-table-column label="型号" align="center" prop="model"/>
                <el-table-column label="厂商" align="center" prop="deptName"/>
                <el-table-column label="设备ID号" align="center" prop="equipmentId"/>
                <el-table-column label="SIM卡号" align="center" prop="sim"/>
                <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                  <template slot-scope="scope" style="text-align: center">
                    <el-dropdown size="small">
                      <el-button type="primary">
                        更多<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>
                      </el-button>
                      <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item>
                          <el-button
                            size="mini"
                            type="text"
                            @click="collectionStrategy(scope.row)"
                            v-hasPermi="['business:structure:edit']"
                          >采集策略
                          </el-button>
                        </el-dropdown-item>
                        <el-dropdown-item>
                          <el-button
                            size="mini"
                            type="text"
                            @click="configureDTU(scope.row)"
                            v-hasPermi="['business:structure:edit']"
                          >配置DTU
                          </el-button>
                        </el-dropdown-item>
                        <el-dropdown-item>
                          <el-button
                            size="mini"
                            type="text"
                            @click="configureCollector(scope.row)"
                            v-hasPermi="['business:structure:edit']"
                          >配置采集仪
                          </el-button>
                        </el-dropdown-item>
                        <el-dropdown-item>
                          <el-button
                            size="mini"
                            type="text"
                            @click="configureSensor(scope.row)"
                            v-hasPermi="['business:structure:edit']"
                          >配置传感器
                          </el-button>
                        </el-dropdown-item>
                        <el-dropdown-item>
                          <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-delete"
                            @click="handleDeleteEqument(scope.row)"
                            v-hasPermi="['business:structure:remove']"
                          >删除
                          </el-button>
                        </el-dropdown-item>
                      </el-dropdown-menu>
                    </el-dropdown>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div slot="footer" class="dialog-footer" v-if="sbInfo">
              <el-button type="primary" @click="submitEqumentList">确 定</el-button>
              <el-button @click="cancel">取 消</el-button>
            </div>
          </el-tab-pane>
          <el-tab-pane name="cdInfo" label="测点布设" v-if="cdInfo">
            <el-tabs  tab-position="left"  v-model="activeCDTab" @tab-click="switchCDTabFun">
              <el-tab-pane label="监测因素" name="jcys">
                <el-form ref="cdForm" :model="cdForm" label-width="90px">
                  <el-checkbox-group v-model="cdForm.mf">
                    <el-checkbox v-for="dict in dict.type.monitoring_factors" :label="dict.value" :key="dict.value">
                      {{ dict.label }}
                    </el-checkbox>
                  </el-checkbox-group>
                  <div style="margin-top: 20%">
                    <el-button type="primary" @click="submitMFFun">确 定</el-button>
                    <el-button @click="cancel">取 消</el-button>
                  </div>
                </el-form>
              </el-tab-pane>
              <el-tab-pane label="测点" name="cd">
                <MeasuringPointForm ref="measuringPointForm" :sid="measuringPoint.id" :mf="cdForm.mf" v-if="isShowCD"
                                    @ok="measuringPointForOkFun"></MeasuringPointForm>
              </el-tab-pane>
            </el-tabs>
          </el-tab-pane>
        </el-tabs>
      </div>
    </el-dialog>

    <!--设备列表-->
    <el-dialog :title="equipment.title" :visible.sync="equipment.open"  v-if="equipment.open"  width="1000px" append-to-body>
      <EquipmentList ref="equipmentList" :selectIds="equipment.selectIds" :sid="equipment.sid" @ok="equipmentForOkFun"></EquipmentList>
    </el-dialog>
    <!--采集策略-->
    <el-dialog :title="strategy.title" :visible.sync="strategy.open" width="700px" append-to-body>
      <StrategyForm ref="strategyForm" :equipmentId="strategy.equipmentId" :sid="strategy.sid"  @ok="strategyForOkFun"></StrategyForm>
    </el-dialog>
    <!--采集仪-->
    <el-dialog :title="collector.title" :visible.sync="collector.open" width="700px" append-to-body>
      <CollectorForm ref="collectorForm" :sid="collector.sid" :equipmentId="collector.equipmentId"  @ok="collectorForOkFun"></CollectorForm>
    </el-dialog>
    <!--传感器-->
    <el-dialog :title="sensor.title" :visible.sync="sensor.open" width="700px" append-to-body>
      <SensorForm ref="sensorForm" :sid="sensor.sid" :equipmentId="sensor.equipmentId" @ok="sensorForOkFun"></SensorForm>
    </el-dialog>
    <!--DTU-->
    <el-dialog :title="dtu.title" :visible.sync="dtu.open" width="700px" append-to-body>
      <DtuForm ref="dtuForm" :sid="dtu.sid"  :equipmentId="dtu.equipmentId" @ok="dtuForOkFun"></DtuForm>
    </el-dialog>
  </div>
</template>

<script>

/*引入其他vue相关*/
import EquipmentList from "@/views/business/structure/equipmentList"; /*设备列表*/
import StrategyForm from "@/views/business/structure/strategyForm";/*采集策略*/
import CollectorForm from "@/views/business/structure/collectorForm";/*采集仪*/
import SensorForm from "@/views/business/structure/sensorForm";/*传感器*/
import DtuForm from "@/views/business/structure/dtuForm";/*dtu*/
import MeasuringPointForm from "@/views/business/structure/measuringPointForm";/*测点*/

import ImagePreview from "@/components/ImagePreview/index"; /*图片展示*/
import Treeselect from "@riophae/vue-treeselect"; /*机构树*/
/*调用js方法*/
import {listEquipmentNotPage, deleteStructureForEquipmentFun} from "@/api/business/equipment";
import {
  listStructure,
  getStructure,
  delStructure,
  addStructure,
  updateStructure,
  updateStructureForEquipmentFun,
  insertMFFun,
  getMFInfoFun,
} from "@/api/business/structure";
import {listProjectForNotPage} from "@/api/business/project";
/*样式引入*/
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Structure",
  dicts: ['alarm_flag', 'structure_type', 'monitoring_factors', 'add_type', 'monitoring_factors'],
  components: {
    ImagePreview,
    Treeselect,
    EquipmentList,
    StrategyForm,
    CollectorForm,
    SensorForm,
    DtuForm,
    MeasuringPointForm
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 结构物表格数据
      structureList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        typeId: null,
        photoFile: null,
        projectId: null,
        alarmFlag: null,
      },
      // 表单参数
      form: {},
      cdForm: {
        mf: [],
      },
      // 表单校验
      rules: {},
      fileList: [],
      projectList: [],
      isUpdate: false,
      baseInfo: false,
      sbInfo: false,
      cdInfo: false,
      rowDOM: null,
      // 设备对象
      equipment: {
        open: false,
        title: '',
        equipmentList: [], //设备表格数据
        selectIds: [],
        sid:'',
      },
      //采集策略对象
      strategy: {
        open: false,
        title: '',
        sid: '',
        equipmentId: '',
      },
      //采集仪
      collector: {
        open: false,
        title: '',
        sid: '',
        equipmentId: '',
      },
      sensor: {
        open: false,
        title: '',
        sid: '',
        equipmentId: '',
      },
      dtu: {
        open: false,
        title: '',
        sid: '',
        equipmentId: '',
      },
      measuringPoint: {
        open: false,
        title: '',
        id: '',
      },
      activeTab :'baseInfo',
      activeCDTab :'jcys',
      isShowCD : false,
    };
  },
  created() {
    this.getProjectListFun();
    this.getList();
  },
  methods: {
    /** 查询结构物列表 */
    getList() {
      this.loading = true;
      listStructure(this.queryParams).then(response => {
        this.structureList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        typeId: null,
        photoFile: null,
        projectId: null,
        alarmFlag: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.isUpdate = false;
      this.baseInfo = true;
      this.sbInfo = false;
      this.cdInfo = false;
      this.reset();
      this.open = true;
      this.title = "添加结构物";
      this.activeTab = "baseInfo";
      this.activeCDTab = "jcys";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.isUpdate = true;
      this.rowDOM = row;
      this.open = true;
      this.title = "修改结构物";
      const id = this.rowDOM.id;
      this.baseInfo= true;
      this.sbInfo= true;
      this.cdInfo= true;
      this.activeTab = "baseInfo";
      this.activeCDTab = "jcys";
      getStructure(id).then(response => {
        this.form = response.data;
      });

    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStructure(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStructure(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.cdInfo = true;
              this.sbInfo = true;
            });
          }
        }
      });
    },
    /** 提交设备列表 */
    submitEqumentList() {
      this.form.equipmentList = this.equipment.equipmentList; //关联设备
      this.form.id = this.rowDOM.id;
      updateStructureForEquipmentFun(this.form).then(response => {
        this.$modal.msgSuccess("保存成功");
      });
    },
    /** 删除结构物 */
    deleteStructureFun(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除结构物编号为"' + ids + '"的数据项？').then(function () {
        return delStructure(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    //获取项目列表
    getProjectListFun() {
      var _this = this;
      listProjectForNotPage().then((res) => {
        if (res.code == 200) {
          for (let index in res.rows) {
            _this.projectList.push({
              label: res.rows[index].name,
              value: res.rows[index].id,
            })
          }
        }
      });
    },
    //Tab切换
    switchTabFun(tab,event) {
      const id = this.rowDOM.id;
      if (tab.name == 'baseInfo') {
        this.baseInfo = true;
        getStructure(id).then(response => {
          this.form = response.data;
        });
      } else if(tab.name == 'sbInfo') {
        this.sbInfo = true;
        this.getEquipmentListByStructureIdFun(id);
      }else if(tab.name == 'cdInfo') {
        this.cdInfo = true;
        this.getMFInfoFun();
      }
    },
    //配置采集策略
    collectionStrategy(row) {
      this.strategy.open = true;
      this.strategy.title = "新建采集策略";
      this.strategy.sid = row.businessId;
      this.strategy.equipmentId = row.id;
    },
    //采集策略回调方法
    strategyForOkFun() {
      this.strategy.open = false;
    },
    //配置DTU
    configureDTU(row) {
      this.dtu.open = true;
      this.dtu.title = "新建DTU";
      this.dtu.sid = row.businessId;
      this.dtu.equipmentId = row.id;
    },
    dtuForOkFun() {
      this.dtu.open = false;
    },
    //配置采集仪
    configureCollector(row) {
      this.collector.open = true;
      this.collector.title = "配置采集仪";
      this.collector.equipmentId = row.id;
      this.collector.sid = row.businessId;
    },
    //配置采集仪回调方法
    collectorForOkFun() {
      this.collector.open = false;
    },
    //配置传感器
    configureSensor(row) {
      this.sensor.open = true;
      this.sensor.title = "配置传感器";
      this.sensor.sid = row.businessId;
      this.sensor.equipmentId = row.id;
    },
    //采集仪回调方法
    sensorForOkFun() {
      this.sensor.open = false;
    },
    //删除设备
    handleDeleteEqument(row) {
      deleteStructureForEquipmentFun(row).then(response => {
        this.$modal.msgSuccess("删除成功");
        this.getEquipmentListByStructureIdFun();
      });
    },
    //获取结构物关联的设备列表
    getEquipmentListByStructureIdFun() {
      this.queryParams.businessId = this.rowDOM.id;
      listEquipmentNotPage(this.queryParams).then(response => {
        this.equipment.equipmentList = response.rows;
      });
    },
    //设备组网,新增设备
    addEquipmentFun() {
      this.equipment.title = "请选择设备";
      this.equipment.open = true;
    },
    //设备选择回调方法
    equipmentForOkFun(array) {
      this.equipment.equipmentList = array;
      this.equipment.open = false;
    },
    submitMFFun() {
      this.cdForm.id = this.rowDOM.id;
      insertMFFun(this.cdForm).then(response => {
        this.$modal.msgSuccess("保存成功");

      });
    },
    getMFInfoFun() {
      getMFInfoFun(this.rowDOM.id).then(response => {
        this.cdForm.mf = response.data.mf;
      });
    },
    measuringPointForOkFun() {


    },
    switchCDTabFun(tab,event){
      if (tab.name == 'jcys') {

      } else if(tab.name == 'cd') {
        this.isShowCD = true;
        this.measuringPoint.id = this.rowDOM.id;
        this.$refs.measuringPointForm.getMeasuringPointTreeFun();
      }
    },
  }
};
</script>
<style>
.image_div {
  height: 200px;
  width: 48%;
  float: left;
  background-color: #ECECEC;
  margin-bottom: 40px;
}

.text_div {
  height: 200px;
  width: 48%;
  float: left;
  background-color: #ECECEC;
  text-align: center;
}


.left_div {
  width: 20%;
  float: left;
  margin-bottom: 40px;
}

.right_div {
  width: 76%;
  float: left;
  text-align: center;
}

/* 修改dialog必须在非scoped环境下 */
.innerDialog .el-dialog__body {
  height: 1150px !important;
}

.baseFormClass {
  width: 80%;
}

</style>
