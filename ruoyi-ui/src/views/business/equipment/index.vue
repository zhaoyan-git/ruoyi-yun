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
          v-hasPermi="['business:equipment:add']"
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
          v-hasPermi="['business:equipment:edit']"
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
          v-hasPermi="['business:equipment:remove']"
        >删除</el-button>
      </el-col>
<!--      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:equipment:export']"
        >导出</el-button>
      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="equipmentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="设备名称" align="center" prop="name" />
      <el-table-column label="型号" align="center" prop="model" />
      <el-table-column label="厂商" align="center" prop="deptName" />
      <el-table-column label="设备ID号" align="center" prop="equipmentId" />
      <el-table-column label="SIM卡号" align="center" prop="sim" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:equipment:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:equipment:remove']"
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

    <!-- 添加或修改设备对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备名称" prop="name">
          <el-input maxlength="100" show-word-limit v-model="form.name"  placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="型号" prop="model">
          <el-input maxlength="100" show-word-limit v-model="form.model" placeholder="请输入型号" />
        </el-form-item>
        <el-form-item label="厂商" prop="manufacturer">
          <treeselect
            v-model="form.manufacturer"
            :options="deptOptions"
            :show-count="true"
            placeholder="选择所属企业"
          />
        </el-form-item>
        <el-form-item label="设备ID号" prop="equipmentId">
          <el-input maxlength="100" show-word-limit v-model="form.equipmentId" placeholder="请输入设备ID号" />
        </el-form-item>
        <el-form-item label="SIM卡号" prop="sim">
          <el-input maxlength="100" show-word-limit v-model="form.sim" placeholder="请输入SIM卡号" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input maxlength="200" show-word-limit v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listEquipment, getEquipment, delEquipment, addEquipment, updateEquipment } from "@/api/business/equipment";
import { getTreeselectList } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "Equipment",
  components:{Treeselect},
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
      // 设备表格数据
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
      rules: {
      },
      //部门树选项
      deptOptions : undefined,
    };
  },
  created() {
    this.getList();
    this.getTreeselect();
  },
  methods: {
    /** 查询设备列表 */
    getList() {
      this.loading = true;
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加设备";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getEquipment(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改设备";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateEquipment(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEquipment(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除设备编号为"' + ids + '"的数据项？').then(function() {
        return delEquipment(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/equipment/export', {
        ...this.queryParams
      }, `equipment_${new Date().getTime()}.xlsx`)
    },
    getTreeselect(){
      var queryForm = {};
      queryForm.type = '2';
      getTreeselectList(queryForm).then(response =>{
        this.deptOptions = response.data;
      });
    },
  }
};
</script>
