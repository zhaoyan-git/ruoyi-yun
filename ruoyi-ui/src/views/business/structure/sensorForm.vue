<template>
  <div class="app-container">

    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="厂商" class="formColumnClassForSen" prop="deptId">
        <treeselect
          v-model="form.deptId"
          style="width: 250px;"
          :options="deptOptions"
          placeholder="请选择企业"
          :show-count="true"
        />
      </el-form-item>
      <el-form-item label="产品" class="formColumnClassForSen" prop="product">
        <el-input v-model="form.product" placeholder="请输入产品"/>
      </el-form-item>
      <el-form-item label="型号" class="formColumnClassForSen" prop="model">
        <el-input v-model="form.model" placeholder="请输入型号"/>
      </el-form-item>
      <el-form-item label="批量添加" class="formColumnClassForSen" prop="batchadd">
        <el-switch
          v-model="form.batchadd"
          @change="switchChange"
        >
        </el-switch>
        <el-radio-group v-model="form.batchaddvalue" v-show="form.batchadd" style="margin-left: 7%;">
          <el-radio
            v-for="dict in dict.type.add_type"
            :key="dict.value"
            :label="dict.value"
          >{{dict.label}}</el-radio>
        </el-radio-group>

      </el-form-item>
      <el-form-item label="模块号" class="formColumnClassForSen" prop="moduleNo">
        <el-input v-model="form.moduleNo" placeholder="请输入模块号"/>
      </el-form-item>
      <el-form-item label="通道号" class="formColumnClassForSen" prop="channelNo">
        <el-input v-model="form.channelNo" placeholder="请输入通道号"/>
      </el-form-item>
      <el-form-item label="编号" class="formColumnClassForSen" prop="no">
        <el-input v-model="form.no" placeholder="请输入编号"/>
      </el-form-item>
      <el-form-item label="公式" class="formColumnClassForSen" prop="formula">
        <el-input v-model="form.formula" placeholder="请输入公式"/>
      </el-form-item>
      <el-form-item label="表达式" class="formColumnClassForSen" prop="expression">
        <el-input v-model="form.expression" placeholder="请输入表达式"/>
      </el-form-item>
      <el-form-item label="备注" class="formColumnClassForSen" prop="remark">
        <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import {listSensor, getSensor, delSensor, addSensor, updateSensor} from "@/api/business/sensor";
import {getTreeselectList} from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {listCollector} from "@/api/business/collector";


export default {
  name: "SensorForm",
  components: {Treeselect},
  props:['sid','equipmentId'],
  dicts: ['alarm_flag', 'structure_type', 'monitoring_factors','add_type'],
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
      sensorList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: null,
        businessId: null,
        equipmentId: null,
        product: null,
        model: null,
        moduleNo: null,
        channelNo: null,
        no: null,
        formula: null,
        expression: null,
        batchadd: null,
        batchaddvalue: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      //部门树选项
      deptOptions: undefined,
    };
  },
  created() {
    this.getTreeselect();
    this.getCollectorByBusinessId();
  },
  methods: {
    /** 查询【请填写功能名称】列表 */
    // 取消按钮
    cancel() {
      this.$emit('ok', '');
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        deptId: null,
        businessId: null,
        product: null,
        model: null,
        moduleNo: null,
        channelNo: null,
        no: null,
        formula: null,
        expression: null,
        batchadd: null,
        batchaddvalue: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        equipmentId: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSensor(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改【请填写功能名称】";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.businessId = this.sid;
          this.form.equipmentId = this.equipmentId;
          if (this.form.id != null) {
            updateSensor(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSensor(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
      this.$emit("ok",'');
    },
    getTreeselect() {
      var queryForm = {};
      queryForm.type = '2';
      getTreeselectList(queryForm).then(response => {
        this.deptOptions = response.data;
      });
    },
    switchChange(){
      console.log(this.form.batchadd);
    },
    //根据设备id获取采集仪信息
    getCollectorByBusinessId(){
      this.queryParams.businessId = this.sid;
      this.queryParams.equipmentId = this.equipmentId;
      listSensor(this.queryParams).then(response => {
        if(response.rows && response.rows.length >0){
          this.form = response.rows[0];
        }
      });
    },

  }
};
</script>
<style>
  .formColumnClassForSen{
    width: 60%;
  }
</style>
