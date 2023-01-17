<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
             label-width="68px">
      <el-form-item label="结构物" prop="sid">
        <el-select clearable v-model="queryParams.sid"
                   placeholder="请选择所属结构物"
                   @change="getList()"
        >
          <el-option
            v-for="item in formProjectStructureData"
            v-bind:key="item.id"
            :label="item.name"
            :value="parseInt(item.id)"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="监测因素" prop="mfid">
        <el-select clearable v-model="queryParams.mfid"
                   placeholder="请选择监测因素"
        >
          <el-option
            v-for="dict in dict.type.mf_threshold_configuration"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
    </el-form>

    <div>
      <el-tabs v-model="activeTab" type="border-card" @tab-click="switchTabFun">
        <el-tab-pane name="measuredValue" label="测量值">
          <div>
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button
                  type="primary"
                  plain
                  icon="el-icon-plus"
                  size="mini"
                  @click="handleAdd('measuredValue')"
                  v-hasPermi="['cmp:configuration:add']"
                >新增
                </el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button
                  type="success"
                  plain
                  icon="el-icon-edit"
                  size="mini"
                  :disabled="single"
                  @click="handleUpdate"
                  v-hasPermi="['cmp:configuration:edit']"
                >修改
                </el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button
                  type="danger"
                  plain
                  icon="el-icon-delete"
                  size="mini"
                  :disabled="multiple"
                  @click="handleDelete"
                  v-hasPermi="['cmp:configuration:remove']"
                >删除
                </el-button>
              </el-col>
              <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
            </el-row>
            <el-table v-loading="loading" :data="measuredValue.list" @selection-change="handleSelectionChange">
              <el-table-column type="selection" width="55" align="center"/>
              <el-table-column label="结构物" align="center" prop="sname"/>
              <el-table-column label="测点" align="center" prop="mpname"/>
              <el-table-column label="配置阈值的监测项" align="center" prop="miname"/>
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="handleUpdate(scope.row)"
                    v-hasPermi="['cmp:configuration:edit']"
                  >修改
                  </el-button>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    @click="handleDelete(scope.row)"
                    v-hasPermi="['cmp:configuration:remove']"
                  >删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <pagination
              v-show="total>0"
              :total="total"
              :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize"
              @pagination="getList('1')"
            />
            <!-- 添加或修改阈值配置对话框 -->
            <el-dialog :title="measuredValue.title" :visible.sync="measuredValue.open" v-if="open" width="1200px"
                       append-to-body>
              <el-form ref="measuredValue" :model="measuredValue" label-width="180px">
                <el-form-item label="所属测点" prop="mpid">
                  <el-select v-model="measuredValue.mpid"
                             placeholder="请选择测点">
                    <el-option
                      v-for="item in optionsPointOrDeviceData"
                      v-bind:key="item.id"
                      :label="item.name"
                      :value="parseInt(item.id)"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="配置阈值的监测项" prop="mpid">
                  <el-checkbox-group v-model="measuredValue.checkList">
                    <el-checkbox v-for="city in measuredValue.jcArray" :label="city.name" :key="city.value">
                      {{ city.name }}
                    </el-checkbox>
                  </el-checkbox-group>
                </el-form-item>
                <el-form-item label="阈值格式">
                  <el-switch
                    v-model="value"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                    @change="switchChange">
                  </el-switch>
                </el-form-item>
                <el-col :span="24" class="card-box" v-if="value">
                  <ThresholdFormat></ThresholdFormat>
                </el-col>
                <el-row v-for="(item,inde) in measuredValue.jcxTableList">
                  <el-divider content-position="left">{{ item }}</el-divider>
                  <div class="app-container">
                    <el-form :model="measuredValue.dynamicValidateForm" ref="dynamicValidateForm" label-width="100px"
                             class="demo-dynamic">
                      <el-col :span="6" v-for="(domain, index) in measuredValue.dynamicValidateForm.domains">
                        <el-form-item
                          :label="(index+1)+'级阈值'"
                          :key="domain.value"
                          :prop="'domains.' + index + '.value'"
                          :rules="{required: true, message: '阈值不能为空', trigger: 'blur'}"
                        >
                          <el-input v-model="domain.value"  lev="1" ></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :span="1.5">
                        <el-button
                          type="danger"
                          plain
                          icon="el-icon-delete"
                          size="mini"
                          @click.prevent="removeDomain(domain,'1')"
                        >删除
                        </el-button>
                      </el-col>
                      <el-col :span="1.5">
                        <el-button
                          type="primary"
                          plain
                          icon="el-icon-plus"
                          size="mini"
                          @click="addMeasuredValueDomain(item,'1')"
                        >新增阈值等级
                        </el-button>
                      </el-col>
                    </el-form>
                  </div>
                </el-row>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm('1')">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
              </div>
            </el-dialog>
          </div>

        </el-tab-pane>

        <el-tab-pane name="rateChange" label="变化速率">
          <div>
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button
                  type="primary"
                  plain
                  icon="el-icon-plus"
                  size="mini"
                  @click="handleAdd('rateChange')"
                  v-hasPermi="['cmp:configuration:add']"
                >新增阈值
                </el-button>
              </el-col>
            </el-row>
            <el-table v-loading="loading" :data="rateChange.list" @selection-change="handleSelectionChange">
              <el-table-column label="类型" align="center" prop="sname"/>
              <el-table-column label="监测项" align="center" prop="miname"/>
              <el-table-column label="一级阈值" align="center" prop="lev1value"/>
              <el-table-column label="二级阈值" align="center" prop="lev2value"/>
              <el-table-column label="三级阈值" align="center" prop="lev3value"/>
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="handleUpdate(scope.row)"
                    v-hasPermi="['cmp:configuration:edit']"
                  >修改
                  </el-button>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    @click="handleDelete(scope.row)"
                    v-hasPermi="['cmp:configuration:remove']"
                  >删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <pagination
              v-show="total>0"
              :total="total"
              :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize"
              @pagination="getList('2')"
            />
            <!-- 添加或修改阈值配置对话框 -->
            <el-dialog :title="rateChange.title" :visible.sync="rateChange.open" v-if="open" width="1200px" append-to-body>
              <el-form ref="rateChange" :model="rateChange" label-width="180px">
                <el-form-item label="测项" prop="jcArray">
                  <el-select v-model="rateChange.checkList"
                             placeholder="请选择测项" multiple>
                    <el-option
                      v-for="item in rateChange.jcArray"
                      v-bind:key="item.id"
                      :label="item.name"
                      :value="parseInt(item.id)"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="类型" prop="rateTypeList">
                  <el-select clearable v-model="rateChange.rateTypeList"
                             placeholder="请选择监测因素" multiple
                  >
                    <el-option
                      v-for="dict in dict.type.rate_change_type"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item label="阈值格式">
                  <el-switch
                    v-model="value"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                    @change="switchChange">
                  </el-switch>
                </el-form-item>
                <el-row>
                  <el-col :span="24" class="card-box" v-if="value">
                    <ThresholdFormat></ThresholdFormat>
                  </el-col>
                </el-row>
                <el-row v-for="(item,inde) in rateChange.jcxTableList">
                  <el-divider content-position="left">{{ item }}</el-divider>
                  <div class="app-container">
                    <el-form :model="rateChange.dynamicValidateForm" ref="dynamicValidateForm" label-width="100px"
                             class="demo-dynamic">
                      <el-col :span="6" v-for="(domain, index) in rateChange.dynamicValidateForm.domains">
                        <el-form-item
                          :label="(index+1)+'级阈值'"
                          :key="domain.value"
                          :prop="'domains.' + index + '.value'"
                          :rules="{required: true, message: '阈值不能为空', trigger: 'blur'}"
                        >
                          <el-input v-model="domain.value" type="number"  lev="1" ></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :span="1.5">
                        <el-button
                          type="danger"
                          plain
                          icon="el-icon-delete"
                          size="mini"
                          @click.prevent="removeDomain(domain,'2')"
                        >删除
                        </el-button>
                      </el-col>
                      <el-col :span="1.5">
                        <el-button
                          type="primary"
                          plain
                          icon="el-icon-plus"
                          size="mini"
                          @click="addMeasuredValueDomain(item,'2')"
                        >新增阈值等级
                        </el-button>
                      </el-col>


                    </el-form>

                  </div>
                </el-row>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm('2')">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
              </div>
            </el-dialog>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import {
  listConfiguration,
  getConfiguration,
  delConfiguration,
  addConfiguration,
  updateConfiguration
} from "@/api/business/configuration";
import {getStructure, listStructure} from "@/api/business/structure";
import {listPoint} from "@/api/business/point";

import ThresholdFormat from "@/views/business/configuration/thresholdFormat.vue";
import ThresholdLev from "@/views/business/configuration/thresholdLev.vue";

export default {
  name: "Configuration",
  dicts: ['mf_threshold_configuration', 'rate_change_type'],
  components: {ThresholdFormat, ThresholdLev},
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
      // 阈值配置表格数据
      configurationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sid: null,
        sname: null,
        mpid: null,
        mpname: null,
        mfid: 'settlement',
        mfname: null,
        miid: null,
        miname: null,
        lev1value: null,
        lev2value: null,
        lev3value: null,

      },
      // 表单参数
      form: {},
      // 表单校验
      formProjectStructureData: [],
      activeTab: 'measuredValue',
      equipment: {
        equipmentList: null,
      },
      measuredValue: {
        open: false,
        title: '',
        form: {},
        jcArray: [],
        list: [],
        switchValue: false,
        dynamicValidateForm: {
          domains: [{
            value: '',
            lev:'1',
          }],
        },
        jcxTableList: [],
        checkList: [],
      },
      rateChange: {
        open: false,
        title: '',
        form: {},
        jcArray: [],
        list: [],
        switchValue: false,
        dynamicValidateForm: {
          domains: [{
            value: '',
            lev:'1',
          }],
        },
        jcxTableList: [],
        checkList: [],
        rateTypeList: null,
      },
      optionsPointOrDeviceData: [],
      isOk: false,
      size: '',
      value: false,
      type: '1',

    };
  },
  created() {
    this.getStrctureListFun()
    this.getList("1")
  },
  methods: {
    /** 查询阈值配置列表 */
    getList(type) {
      if(!type){
        type = this.type;
      }
      this.loading = true;
      this.queryParams.type = type;
      listConfiguration(this.queryParams).then(response => {
        if(type == '1'){
          this.measuredValue.list = response.rows;
        }else{
          this.rateChange.list = response.rows;
        }
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.getList();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        sid: null,
        sname: null,
        mpid: null,
        mpname: null,
        mfid: null,
        mfname: null,
        miid: null,
        miname: null,
        lev1value: null,
        lev2value: null,
        lev3value: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
    baseFun() {
      this.measuredValue.jcxTableList = [];
      this.measuredValue.checkList = [];
      this.rateChange.jcxTableList = [];
      this.rateChange.checkList = [];
      //1.根据结构物获取测点
      this.dialogFormProjectStructureChange()
      //2.获取监测因素对应的监测项
      this.getJCXForJCYSFun()
    },
    handleAdd(key) {
      this.baseFun();
      if (key == 'measuredValue') {
        this.measuredValue.open = true;
        this.open = true;
        this.measuredValue.title = "添加阈值";
        this.type = '1';
      } else {
        this.rateChange.open = true;
        this.rateChange.title = "新增阈值";
        this.type = '2';
      }
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const id = row.id || this.ids
      getConfiguration(id).then(response => {
        this.type = response.data.type;
        if(response.data.type == '1'){
          this.measuredValue = response.data;
          this.measuredValue.open = true;
          this.open = true;
          this.measuredValue.title = "修改阈值";
          this.queryParams.sid = response.data.sid;
          this.queryParams.mfid = response.data.mfid;
          this.baseFun();
          this.measuredValue.dynamicValidateForm.domains = response.data.dynamicValidateForm.domains;
        }else{
          this.rateChange = response.data;
          this.rateChange.open = true;
          this.open = true;
          this.rateChange.title = "修改阈值";
          this.queryParams.sid = response.data.sid;
          this.queryParams.mfid = response.data.mfid;
          this.baseFun();
          this.rateChange.rateTypeList = response.data.rateTypeList
          this.rateChange.dynamicValidateForm.domains = response.data.dynamicValidateForm.domains;
        }

      });
    },
    /** 提交按钮 */
    submitForm(type) {
      if (type == '1') {
        this.$refs["measuredValue"].validate(valid => {
          if (valid) {
            this.measuredValue.sid = this.queryParams.sid;
            this.measuredValue.mfid = this.queryParams.mfid;
            this.measuredValue.type = type;
            if (this.measuredValue.id != null) {
              updateConfiguration(this.measuredValue).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.measuredValue.open = false;
                this.open = false;
                this.getList('1');
              });
            } else {
              addConfiguration(this.measuredValue).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.measuredValue.open = false;
                this.open = false;
                this.getList('1');
              });
            }
          }
        });
      } else if (type == '2') {
        this.$refs["rateChange"].validate(valid => {
          if (valid) {
            this.rateChange.sid = this.queryParams.sid;
            this.rateChange.mfid = this.queryParams.mfid;
            this.rateChange.type = type;
            if (this.rateChange.id != null) {
              updateConfiguration(this.rateChange).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.rateChange.open = false;
                this.open = false;
                this.getList('2');
              });
            } else {
              addConfiguration(this.rateChange).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.rateChange.open = false;
                this.open = false;
                this.getList('2');
              });
            }
          }
        });
      }

    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除阈值配置编号为"' + ids + '"的数据项？').then(function () {
        return delConfiguration(ids);
      }).then(() => {
        this.getList(row.type);
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('cmp/configuration/export', {
        ...this.queryParams
      }, `configuration_${new Date().getTime()}.xlsx`)
    },
    //获取结构物信息
    getStrctureListFun() {
      listStructure({}).then(res => {
        if (res.code == 200) {
          for (let index in res.rows) {
            this.formProjectStructureData.push({
              name: res.rows[index].name,
              id: res.rows[index].id,
            })
          }
          this.queryParams.sid = this.formProjectStructureData[0].id;
        }
      });
    },
    //Tab切换
    switchTabFun(tab, event) {
      this.measuredValue.jcxTableList = [];
      this.measuredValue.checkList = [];
      if (tab.name == 'measuredValue') {
          this.type='1';
          this.getList('1');
      } else if (tab.name == 'rateChange') {
          this.type = '2';
          this.getList('2');
      }
    },
    /**结构物chang事件,通过结构物获取对应的测点信息*/
    dialogFormProjectStructureChange() {
      this.optionsPointOrDeviceData = [];
      var queryForm = {};
      queryForm.lev = '3';
      queryForm.structureId = this.queryParams.sid;
      if (this.queryParams.sid) {
        listPoint(queryForm).then(res => {
          if (res.code == 200) {
            for (let index in res.rows) {
              this.optionsPointOrDeviceData.push({
                name: res.rows[index].name,
                id: res.rows[index].id,
              })
            }
          }
        });

      }
    },
    //获取监测因素对应的监测项
    getJCXForJCYSFun() {
      this.measuredValue.checkList = [];
      this.measuredValue.jcArray = [];
      this.rateChange.checkList = [];
      this.rateChange.jcArray = [];
      const dictType = this.queryParams.mfid
      var _this = this;
      this.getDicts(dictType).then(response => {
        for (let index in response.data) {
          if (index == 0) {
            this.measuredValue.checkList.push(response.data[0].dictLabel);
            this.rateChange.checkList.push(response.data[0].dictLabel);
          }
          _this.measuredValue.jcArray.push({
            name: response.data[index].dictLabel,
            value: response.data[index].dictValue,
          })
          _this.rateChange.jcArray.push({
            name: response.data[index].dictLabel,
            id: response.data[index].dictValue,
          })

        }
        //3.根据选中的配置阈值的检测项,构建 监测项 模块
        this.buildJCXTableListFun();
      });

    },
    //构建监测项表格列表
    buildJCXTableListFun() {
      if (this.measuredValue.checkList) {
        for (var i = 0; i < this.measuredValue.checkList.length; i++) {
          this.measuredValue.jcxTableList.push(this.measuredValue.checkList[i]);
        }
      }
      if (this.rateChange.checkList) {
        for (var i = 0; i < this.rateChange.checkList.length; i++) {
          this.rateChange.jcxTableList.push(this.rateChange.checkList[i]);
        }
      }
    },
    switchChange(value) {
      this.value = value;
    },
    removeDomain(item, type) {
      if (type == '1') {
        if (this.measuredValue.dynamicValidateForm.domains && this.measuredValue.dynamicValidateForm.domains.length == 1) {
          return;
        }
        //删除等级最小的
        item = this.measuredValue.dynamicValidateForm.domains[this.measuredValue.dynamicValidateForm.domains.length - 1];
        var index = this.measuredValue.dynamicValidateForm.domains.indexOf(item)
        if (index !== -1) {
          this.measuredValue.dynamicValidateForm.domains.splice(index, 1)
        }
      } else if (type == '2') {
        if (this.rateChange.dynamicValidateForm.domains && this.rateChange.dynamicValidateForm.domains.length == 1) {
          return;
        }
        //删除等级最小的
        item = this.rateChange.dynamicValidateForm.domains[this.rateChange.dynamicValidateForm.domains.length - 1];
        var index = this.rateChange.dynamicValidateForm.domains.indexOf(item)
        if (index !== -1) {
          this.rateChange.dynamicValidateForm.domains.splice(index, 1)
        }
      }

    },
    addMeasuredValueDomain(item, type) {
      if (type == '1') {
        if (this.measuredValue.dynamicValidateForm.domains && this.measuredValue.dynamicValidateForm.domains.length >= 3) {
          return;
        }
        this.measuredValue.dynamicValidateForm.domains.push({
          value: '',
          key: Date.now(),
          lev: this.measuredValue.dynamicValidateForm.domains.length + 1,
          sname: item,
        });
      } else if (type == '2') {
        if (this.rateChange.dynamicValidateForm.domains && this.rateChange.dynamicValidateForm.domains.length >= 3) {
          return;
        }
        this.rateChange.dynamicValidateForm.domains.push({
          value: '',
          key: Date.now(),
          lev: this.rateChange.dynamicValidateForm.domains.length + 1,
          sname: item,
        });
      }

    },


  }
};
</script>
