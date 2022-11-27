<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="厂商" prop="deptId" formColumnClassForColl>
        <treeselect
          v-model="form.deptId"
          style="width: 250px;"
          :options="deptOptions"
          placeholder="请选择企业"
          :show-count="true"
        />
      </el-form-item>
      <el-form-item label="产品" prop="product" formColumnClassForColl>
        <el-input maxlength="200" show-word-limit v-model="form.product" placeholder="请输入产品"/>
      </el-form-item>
      <el-form-item label="型号" prop="model" formColumnClassForColl>
        <el-input maxlength="100" show-word-limit v-model="form.model" placeholder="请输入型号"/>
      </el-form-item>
      <el-form-item label="模块号" prop="moduleNo" formColumnClassForColl>
        <el-input maxlength="100" show-word-limit v-model="form.moduleNo" placeholder="请输入模块号"/>
      </el-form-item>
      <el-form-item label="编号" prop="no" formColumnClassForColl>
        <el-input  maxlength="100" show-word-limit v-model="form.no" placeholder="请输入编号"/>
      </el-form-item>
      <el-form-item label="采集策略" prop="csId" formColumnClassForColl>
        <el-select
          v-model="form.csId"
          clearable
          placeholder="请选择采集策略"
        >
          <el-option
            v-for="dict in strategyList"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remark" formColumnClassForColl>
        <el-input maxlength="200" show-word-limit v-model="form.remark" type="textarea" placeholder="请输入内容"/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import {listCollector, getCollector, delCollector, addCollector, updateCollector,getCollectorByBusinessIdFun} from "@/api/business/collector";
import {getTreeselectList} from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

import {getStrategyByBusinessIdFun, listStrategy} from "@/api/business/strategy";
import {listProjectForNotPage} from "@/api/business/project";

export default {
  name: "CollectorForm",
  components: {Treeselect},
  props: ['sid','equipmentId'],
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
        businessId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      //部门树选项
      deptOptions: undefined,
      strategyList: [],
    };
  },
  created() {
    this.getTreeselect();
    //根据设备ID获取采集策略信息
    this.getStrategyByBusinessId();
    //根据设备ID获取采集仪信息
    this.getCollectorByBusinessId();
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
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCollector(id).then(response => {
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
            updateCollector(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCollector(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }``
      });
      this.$emit('ok', '');
    },
    getTreeselect() {
      var queryForm = {};
      queryForm.type = '2';
      getTreeselectList(queryForm).then(response => {
        this.deptOptions = response.data;
      });
    },
    //获取设备的采集策略
    getStrategyByBusinessId(){
      var qform = {};
      var _this = this;
      this.strategyList = [];
      qform.businessId = this.sid;
      listStrategy(qform).then(res => {
        if (res.code == 200) {
          for (let index in res.rows) {
            _this.strategyList.push({
              label: res.rows[index].name,
              value: res.rows[index].id,
            })
          }
        }
      });
    },
    //根据设备id获取采集仪信息
    getCollectorByBusinessId(){
      this.queryParams.businessId = this.sid;
      this.queryParams.equipmentId = this.equipmentId;
      listCollector(this.queryParams).then(response => {
        if(response.rows && response.rows.length >0){
          this.form = response.rows[0];
        }
      });
    },
  }
};
</script>
<style>
  .formColumnClassForColl{
    width: 60%;
  }
</style>
