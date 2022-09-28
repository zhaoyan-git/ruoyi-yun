<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="型号" prop="model">
        <el-input
          v-model="queryParams.model"
          placeholder="请输入型号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="厂商" prop="manufacturer">
        <treeselect
          v-model="queryParams.manufacturer"
          style="width: 250px;"
          :options="deptOptions"
          placeholder="请选择企业"
          :show-count="true"
        />
      </el-form-item>
      <el-form-item label="设备ID号" prop="equipmentId">
        <el-input
          v-model="queryParams.equipmentId"
          placeholder="请输入设备ID号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="SIM卡号" prop="sim">
        <el-input
          v-model="queryParams.sim"
          placeholder="请输入SIM卡号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button type="primary"  size="mini" @click="submitForm">确定</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="equipmentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="设备名称" align="center" prop="name"/>
      <el-table-column label="型号" align="center" prop="model"/>
      <el-table-column label="厂商" align="center" prop="deptName"/>
      <el-table-column label="设备ID号" align="center" prop="equipmentId"/>
      <el-table-column label="SIM卡号" align="center" prop="sim"/>
      <el-table-column label="备注" align="center" prop="remark"/>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {listEquipment, getEquipment, delEquipment, addEquipment, updateEquipment} from "@/api/business/equipment";
import {getTreeselectList} from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "EquipmentList",
  components: {Treeselect},
  props: ['selectIds'],
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
      equipmentList: [],
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
        model: null,
        manufacturer: null,
        equipmentId: null,
        sim: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      //部门树选项
      deptOptions: undefined,
      selectList: [],
    };
  },
  created() {
    this.getList();
    this.getTreeselect();
  },
  methods: {
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      this.queryParams.businessIdIsNull = '1';
      listEquipment(this.queryParams).then(response => {
        this.equipmentList = response.rows;
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
        businessId: null,
        model: null,
        manufacturer: null,
        equipmentId: null,
        sim: null,
        remark: null,
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
      this.selectList = selection.map(item => item)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 提交按钮 */
    submitForm() {
      if(!this.selectList || this.selectList.length ==0){
        this.$modal.msgError("请选择设备");
        return;
      }
      this.$emit('ok', this.selectList)
    },
    getTreeselect() {
      var queryForm = {};
      queryForm.type = '2';
      getTreeselectList(queryForm).then(response => {
        this.deptOptions = response.data;
      });
    },
  }
};
</script>
