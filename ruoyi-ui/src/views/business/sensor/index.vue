<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="厂商" class="formColumnClassForDTU" prop="deptId">
        <treeselect
          v-model="form.deptId"
          style="width: 250px;"
          :options="deptOptions"
          placeholder="请选择企业"
          :show-count="true"
        />
      </el-form-item>
      <el-form-item label="设备id" prop="businessId">
        <el-input
          v-model="queryParams.businessId"
          placeholder="请输入设备id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品" prop="product">
        <el-input
          v-model="queryParams.product"
          placeholder="请输入产品"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="型号" prop="model">
        <el-input
          v-model="queryParams.model"
          placeholder="请输入型号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模块号" prop="moduleNo">
        <el-input
          v-model="queryParams.moduleNo"
          placeholder="请输入模块号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="通道号" prop="channelNo">
        <el-input
          v-model="queryParams.channelNo"
          placeholder="请输入通道号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="编号" prop="no">
        <el-input
          v-model="queryParams.no"
          placeholder="请输入编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="公式" prop="formula">
        <el-input
          v-model="queryParams.formula"
          placeholder="请输入公式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="表达式" prop="expression">
        <el-input
          v-model="queryParams.expression"
          placeholder="请输入表达式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="批量添加" prop="batchadd">
        <el-input
          v-model="queryParams.batchadd"
          placeholder="请输入批量添加"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模号号递增,通道号递增" prop="batchaddvalue">
        <el-input
          v-model="queryParams.batchaddvalue"
          placeholder="请输入模号号递增,通道号递增"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['cmp:sensor:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['cmp:sensor:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['cmp:sensor:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['cmp:sensor:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sensorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="${comment}" align="center" prop="id" />
      <el-table-column label="厂商" align="center" prop="deptId" />
      <el-table-column label="设备id" align="center" prop="businessId" />
      <el-table-column label="产品" align="center" prop="product" />
      <el-table-column label="型号" align="center" prop="model" />
      <el-table-column label="模块号" align="center" prop="moduleNo" />
      <el-table-column label="通道号" align="center" prop="channelNo" />
      <el-table-column label="编号" align="center" prop="no" />
      <el-table-column label="公式" align="center" prop="formula" />
      <el-table-column label="表达式" align="center" prop="expression" />
      <el-table-column label="批量添加" align="center" prop="batchadd" />
      <el-table-column label="模号号递增,通道号递增" align="center" prop="batchaddvalue" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['cmp:sensor:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cmp:sensor:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改【请填写功能名称】对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="厂商" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入厂商" />
        </el-form-item>
        <el-form-item label="设备id" prop="businessId">
          <el-input v-model="form.businessId" placeholder="请输入设备id" />
        </el-form-item>
        <el-form-item label="产品" prop="product">
          <el-input v-model="form.product" placeholder="请输入产品" />
        </el-form-item>
        <el-form-item label="型号" prop="model">
          <el-input v-model="form.model" placeholder="请输入型号" />
        </el-form-item>
        <el-form-item label="模块号" prop="moduleNo">
          <el-input v-model="form.moduleNo" placeholder="请输入模块号" />
        </el-form-item>
        <el-form-item label="通道号" prop="channelNo">
          <el-input v-model="form.channelNo" placeholder="请输入通道号" />
        </el-form-item>
        <el-form-item label="编号" prop="no">
          <el-input v-model="form.no" placeholder="请输入编号" />
        </el-form-item>
        <el-form-item label="公式" prop="formula">
          <el-input v-model="form.formula" placeholder="请输入公式" />
        </el-form-item>
        <el-form-item label="表达式" prop="expression">
          <el-input v-model="form.expression" placeholder="请输入表达式" />
        </el-form-item>
        <el-form-item label="批量添加" prop="batchadd">
          <el-input v-model="form.batchadd" placeholder="请输入批量添加" />
        </el-form-item>
        <el-form-item label="模号号递增,通道号递增" prop="batchaddvalue">
          <el-input v-model="form.batchaddvalue" placeholder="请输入模号号递增,通道号递增" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSensor, getSensor, delSensor, addSensor, updateSensor } from "@/api/business/sensor";

export default {
  name: "Sensor",
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
      rules: {
      },
      //部门树选项
      deptOptions: undefined,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      listSensor(this.queryParams).then(response => {
        this.sensorList = response.rows;
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加【请填写功能名称】";
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
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除【请填写功能名称】编号为"' + ids + '"的数据项？').then(function() {
        return delSensor(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('cmp/sensor/export', {
        ...this.queryParams
      }, `sensor_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
