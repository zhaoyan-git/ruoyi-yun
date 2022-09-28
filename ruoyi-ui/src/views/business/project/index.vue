<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="所属企业" prop="businessId">
        <treeselect
          v-model="queryParams.businessId"
          style="width: 250px;"
          :options="deptOptions"
          placeholder="请选择企业"
          :show-count="true"
        />
      </el-form-item>
      <el-form-item label="项目位置纬度" prop="positionLat">
        <el-input
          v-model="queryParams.positionLat"
          placeholder="请输入项目位置纬度"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目位置经度" prop="positionLng">
        <el-input
          v-model="queryParams.positionLng"
          placeholder="请输入项目位置经度"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['business:project:add']"
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
          v-hasPermi="['business:project:edit']"
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
          v-hasPermi="['business:project:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:project:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="projectList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="项目名称" align="center" prop="name" />
      <el-table-column label="所属企业" align="center" prop="deptName" />
      <el-table-column label="监控目的" align="center" prop="aim" />
<!--      <el-table-column label="项目图片" align="center" prop="photoFile" >-->
<!--        <template slot-scope="scope">-->
<!--          <image-upload v-model="scope.row.photoFile" :value="scope.row.photoFile"/>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="备注" align="center" prop="details" />
      <el-table-column label="项目位置纬度" align="center" prop="positionLat" />
      <el-table-column label="项目位置经度" align="center" prop="positionLng" />
      <el-table-column label="警报标志" align="center" prop="alarmFlag" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.alarm_flag" :value="scope.row.alarmFlag"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:project:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:project:remove']"
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

    <!-- 添加或修改项目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="项目名称" prop="name">
          <el-input v-model="form.name"  placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="所属企业" prop="businessId">
          <treeselect
            v-model="form.businessId"
            :options="deptOptions"
            :show-count="true"
            placeholder="选择所属企业"
          />
        </el-form-item>
        <el-form-item label="监控目的" prop="aim">
          <el-input v-model="form.aim" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="项目图片">
          <image-upload v-model="form.photoFile" :value="fileList" @ok="fileCallbackFun()"/>
        </el-form-item>
        <el-form-item label="项目位置纬度" prop="positionLat">
          <el-input v-model="form.positionLat" placeholder="请输入项目位置纬度" />
        </el-form-item>
        <el-form-item label="项目位置经度" prop="positionLng">
          <el-input v-model="form.positionLng" placeholder="请输入项目位置经度" />
        </el-form-item>
        <el-form-item label="警报标志" prop="alarmFlag">
          <el-select
            v-model="form.alarmFlag"
            clearable
          >
            <el-option
              v-for="dict in dict.type.alarm_flag"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="details">
          <el-input v-model="form.details" type="textarea" placeholder="请输入内容" />
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
import { listProject, getProject, delProject, addProject, updateProject } from "@/api/business/project";
import { getTreeselectList } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Project",
  components:{Treeselect},
  dicts :['alarm_flag'],
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
      // 项目表格数据
      projectList: [],
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
        aim: null,
        photoFile: null,
        details: null,
        positionLat: null,
        positionLng: null,
        alarmFlag: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      //部门树选项
      deptOptions : undefined,
      fileList:[],
    };
  },
  created() {
    this.getList();
    this.getTreeselect();
  },
  methods: {
    /** 查询项目列表 */
    getList() {
      this.loading = true;
      listProject(this.queryParams).then(response => {
        this.projectList = response.rows;
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
        aim: null,
        photoFile: null,
        details: null,
        positionLat: null,
        positionLng: null,
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加项目";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProject(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改项目";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProject(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProject(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除项目编号为"' + ids + '"的数据项？').then(function() {
        return delProject(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/project/export', {
        ...this.queryParams
      }, `project_${new Date().getTime()}.xlsx`)
    },
    getTreeselect(){
      var queryForm = {};
      queryForm.type = '1';
      getTreeselectList(queryForm).then(response =>{
        this.deptOptions = response.data;
      });
    },
    fileCallbackFun(param){
      this.fileList = param;
      console.log("回调参数"+param);
    },
  }
};
</script>
