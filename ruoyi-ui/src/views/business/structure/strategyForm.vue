<template>
  <div class="app-container">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="采集策略名称" prop="name" class="formColumnClass">
          <el-input v-model="form.name" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="采集时间(分钟)" prop="svalue" class="formColumnClass">
          <el-input v-model="form.svalue" placeholder="请输入采集时间(分钟)" oninput="value=value.replace(/^\.+|[^\d.]/g,'')" />
        </el-form-item>
        <el-form-item label="备注" prop="remark" class="formColumnClass">
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
import { getStrategy,  addStrategy, updateStrategy,getStrategyByBusinessIdFun} from "@/api/business/strategy";

export default {
  name: "StrategyForm",
  props:['equipmentId','sid'],
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
      // 采集策略表格数据
      strategyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        businessId: null,
        svalue: null,
        equipmentId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
     this.getStrategyByBusinessId();
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
        name: null,
        businessId: null,
        svalue: null,
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
      getStrategy(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改采集策略";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.businessId = this.sid;
          this.form.equipmentId = this.equipmentId;
          if (this.form.id != null) {
            updateStrategy(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
            });
          } else {
            addStrategy(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
            });
          }
        }
      });
      this.$emit('ok', '');
    },
    getStrategyByBusinessId(){
      this.queryParams.businessId = this.sid;
      this.queryParams.equipmentId = this.equipmentId;
      getStrategyByBusinessIdFun(this.queryParams).then(response => {
        this.form = response.data;
      });
    },
  }
};
</script>
<style>
 .formColumnClass{
   width: 50%;
 }
</style>
