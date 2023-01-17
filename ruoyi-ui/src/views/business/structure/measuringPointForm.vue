<template>
  <div class="app-container">
    <div>
      <div class="left_div_tree">
        <div class="gd-tree">
          <eleTree
            :data.sync="newData"
            :lev1Drop="lev1Drop"
            :lev3Drop="lev3Drop"
            :diectoryDrop="diectoryDrop"
            :delete="deleteBtn"
            :edit="editBtn"
            @ok="handleNodeSureFun"
            @delete="handleNodeDeleteFun"
            @handleNodeClickFun="handleNodeClickFun"
          ></eleTree>
        </div>
      </div>
      <div class="right_div_tree" v-if="isShowLev3">
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
          <el-form-item label="测点名称" class="formColumnClassForMPF" prop="name">
            <el-input v-model="form.name" placeholder="请输入测点名称" :disabled="true"/>
          </el-form-item>
          <el-form-item label="监测因素" class="formColumnClassForMPF" prop="mfId">
            <el-select
              v-model="form.mfId"
              clearable
              placeholder="请选择监测因素"
              :disabled="true"
            >
              <el-option
                v-for="dict in evenNumbers"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"

              />
            </el-select>
          </el-form-item>
          <el-form-item label="数据来源方式" class="formColumnClassForMPF" prop="dataSourceTypeId">
            <el-select
              v-model="form.dataSourceTypeId"
              clearable
              placeholder="请选择数据来源方式"
            >
              <el-option
                v-for="dict in dict.type.data_source_type"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="设备" class="formColumnClassForMPF" prop="equipmentId" v-if="isSelect">
            <el-select
              v-model="equipmentIdArr"
              clearable
              placeholder="请选择设备"
              multiple
            >
              <el-option
                v-for="dict in equipmentList"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="是否为基准点" class="formColumnClassForMPF" prop="isBenchmark">
            <el-select
              v-model="form.isBenchmark"
              clearable
              placeholder="请选择是否为基准点"
              @change="benchmarkChangeFun"
            >
              <el-option
                v-for="dict in dict.type.sys_yes_no"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="基准值" class="formColumnClassForMPF" prop="benchmarkValue" v-if="isShowBenchmark">
            <el-input type="number" v-model="form.benchmarkValue" placeholder="请输入基准值"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import eleTree from "@/views/business/structure/eleTree";
import {
  getMeasuringPointTreeFun,
  saveNodeInfoFun,
  deleteNodeInfoFun,
  getPoint,
  updatePoint
} from "@/api/business/point";
import {listEquipment} from "@/api/business/equipment";

export default {
  name: "MeasuringPointForm",
  components: {Treeselect, eleTree},
  dicts: ['alarm_flag', 'structure_type', 'monitoring_factors', 'add_type', 'monitoring_factors', 'data_source_type', 'sys_yes_no'],
  props: ['sid', 'mf'],
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
      // 【请填写功能名称】表格数据
      collectorList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: null,
        product: null,
        model: null,
        equipmentId: null,
        moduleNo: null,
        no: null,
        csId: null,
      },
      equipmentIdArr: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      //部门树选项
      deptOptions: undefined,
      strategyList: [],
      lev1Drop: [
        {text: "add", value: "新增"},
        {text: "rn", value: "修改"},
        {text: "rm", value: "删除"}
      ],
      lev3Drop: [
        {text: "rn", value: "修改"},
        {text: "rm", value: "删除"}
      ],
      diectoryDrop: [
        {text: "add", value: "新增"},
        {text: "rn", value: "修改"},
        {text: "rm", value: "删除"}
      ],
      data: [],
      newData: [],
      isShowLev3: false,
      equipmentList: [],
      isSelect: false,
      isShowBenchmark: false,
    };
  },
  //一般在初始化页面完成后，再对dom节点进行相关操作
  mounted() {
    setTimeout(() => {
      this.newData = this.data;
    }, 100);
  },
  //1.created => computed => mounted
  created() {
    //构建左面的树结构
    this.getMeasuringPointTreeFun();
  },
  //只有在它的相关依赖发生改变时才会进行更新
  computed: {
    //数据过滤
    evenNumbers() {
      var _this = this;
      if (_this.mf && _this.mf.length > 0) {
        return this.dict.type.monitoring_factors.filter((dictData) => {
          if (_this.mf.indexOf(dictData.value) > -1) {
            return true;
          } else {
            return false;
          }
        });
      }
    }
  },
  methods: {
    // 取消按钮
    cancel() {
      this.$emit('ok', '');
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        deptId: null,
        product: null,
        model: null,
        equipmentId: null,
        moduleNo: null,
        no: null,
        csId: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.structureId = this.sid;
          this.form.equipmentIdArr = this.equipmentIdArr;
          if (this.form.id != null) {
            updatePoint(this.form).then(response => {
              this.$modal.msgSuccess("保存成功");
            });
          }
        }
      });
      this.$emit('ok', '');
    },
    deleteBtn(id) {
      return new Promise((resolve, reject) => {
        resolve(id);
      });
    },
    editBtn(id) {
      return new Promise((resolve, reject) => {
        resolve(id);
      });
    },
    /**获取测点树信息*/
    getMeasuringPointTreeFun() {
      this.queryParams.structureId = this.sid;
      getMeasuringPointTreeFun(this.queryParams).then(response => {
        this.newData = response.data.treeSelect;
      });
    },
    //节点新增,确定事件
    handleNodeSureFun(node) {
      //1.保存节点信息
      this.saveNodeInfoFun(node);
      if (node.level == 3) {
        this.isShowLev3 = true;
        this.getNodeInfoByNodeIdFun(node);
      } else {
        this.isShowLev3 = false;
      }
    },
    /**节点删除方法*/
    handleNodeDeleteFun(nodeId) {
      this.form.nodeId = nodeId;
      deleteNodeInfoFun(this.form).then(response => {
        this.isShowLev3 = false;
        this.getMeasuringPointTreeFun();
      });
    },
    //保存节点信息
    saveNodeInfoFun(node) {
      var json = {};
      var parentJson = {};
      parentJson.id = node.parent.data.id;
      parentJson.name = node.parent.data.label;
      parentJson.lev = node.parent.level;
      json.id = node.data.id;
      json.name = node.data.label;
      json.lev = node.level;
      json.parentJson = parentJson;
      this.form.nodeInfo = json;
      this.form.structureId = this.sid;
      saveNodeInfoFun(this.form).then(response => {
        this.isShowLev3 = false;
        this.getMeasuringPointTreeFun();
      });
    },
    /**节点单击事件*/
    handleNodeClickFun(node) {
      //如果是三级的节点,展示右侧信息
      if (node.level == 3) {
        this.isShowLev3 = true;
        this.getNodeInfoByNodeIdFun(node);
        this.getEquimentListFun();
      } else {
        this.isShowLev3 = false;
      }
    },
    /**根据节点ID获取节点信息*/
    getNodeInfoByNodeIdFun(node) {
      if (node.data.label) {
        getPoint(node.data.id).then(response => {
          if (response.data) {
            this.form = response.data;
            if (response.data.isBenchmark && response.data.isBenchmark == 'Y') {
              this.isShowBenchmark = true;
            }
          }
          this.equipmentIdArr = JSON.parse(response.data.equipmentId);
        });
      }
    },
    /**获取设备列表*/
    getEquimentListFun() {
      var _this = this;
      _this.equipmentList = [];
      listEquipment(this.queryParams).then(res => {
        if (res.code == 200) {
          for (let index in res.rows) {
            _this.equipmentList.push({
              label: res.rows[index].name,
              value: res.rows[index].id,
            })
          }
          this.isSelect = true;
        }
      });
    },
    benchmarkChangeFun(value) {
      this.isShowBenchmark = false;
      if (value == "Y") {
        this.isShowBenchmark = true;
      }
    },
  }
};
</script>
<style>

.left_div_tree {
  width: 40%;
  float: left;
  margin-bottom: 40px;
}

.right_div_tree {
  width: 57%;
  float: left;
  text-align: center;
}

/* 修改dialog必须在非scoped环境下 */
.innerDialog .el-dialog__body {
  height: 1150px !important;
}

.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}

.formColumnClassForMPF {
  width: 80%;
}

</style>
