<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所属测点" prop="pointId">
        <el-input
          v-model="queryParams.pointId"
          placeholder="请输入所属测点"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报警等级" prop="alarmLevel">
        <el-select
          v-model="queryParams.alarmLevel"
          clearable
          placeholder="请选择报警等级"
        >
          <el-option
            v-for="dict in dict.type.alarm_level"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="阈值" prop="threshold">
        <el-input
          v-model="queryParams.threshold"
          placeholder="请输入阈值"
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
          v-hasPermi="['cmp:config:add']"
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
          v-hasPermi="['cmp:config:edit']"
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
          v-hasPermi="['cmp:config:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="configList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="所属测点" align="center" prop="pointName"/>
      <el-table-column label="报警等级" align="center" prop="alarmLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.alarm_level" :value="scope.row.alarmLevel"></dict-tag>
        </template>
      </el-table-column>
      <el-table-column label="阈值" align="center" prop="threshold"/>
      <el-table-column label="计算方式" align="center" prop="computeType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.iot_compute_type" :value="scope.row.computeType"></dict-tag>
        </template>
      </el-table-column>
      <el-table-column label="报警标题" align="center" prop="title"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['cmp:config:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cmp:config:remove']"
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
      @pagination="getList"
    />

    <!-- 添加或修改报警配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="所属项目" prop="projectId" class="formColumnClassForConfig">
              <el-select
                v-model="form.projectId"
                placeholder="请选择所属项目"
                @change="dialogFormProjectChange"
              >
                <el-option
                  v-for="item in projectData"
                  v-bind:key="item.id"
                  :label="item.name"
                  :value="parseInt(item.id)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属结构物" prop="structureId" class="formColumnClassForConfig">
              <el-select v-model="form.structureId"
                         placeholder="请选择所属结构物"
                         @change="dialogFormProjectStructureChange"
              >
                <el-option
                  v-for="item in formProjectStructureData"
                  v-bind:key="item.id"
                  :label="item.name"
                  :value="parseInt(item.id)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="所属测点" prop="pointId" class="formColumnClassForConfig">
              <el-select v-model="form.pointId"
                         placeholder="请选择测点"
              >
                <el-option
                  v-for="item in optionsPointOrDeviceData"
                  v-bind:key="item.id"
                  :label="item.name"
                  :value="parseInt(item.id)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报警等级" prop="alarmLevel" class="formColumnClassForConfig">
              <el-select
                v-model="form.alarmLevel"
                clearable
                placeholder="请选择报警等级"
              >
                <el-option
                  v-for="dict in dict.type.alarm_level"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="阈值" prop="threshold" class="formColumnClassForConfig">
              <el-input type="number" v-model="form.threshold" placeholder="请输入阈值"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计算方式" prop="computeType" class="formColumnClassForConfig">
              <el-select v-model="form.computeType" placeholder="请选择计算方式">
                <el-option
                  v-for="dict in dict.type.iot_compute_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item  label="报警标题" prop="title" class="formColumnClassForConfig">
              <el-input maxlength="50" show-word-limit  v-model="form.title" type="textarea" placeholder="请输入内容"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item  label="报警内容" class="formColumnClassForConfig">
              <editor  v-model="form.content" :min-height="192"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listConfig, getConfig, delConfig, addConfig, updateConfig} from "@/api/business/config";
import {listProject} from "@/api/business/project";
import {listStructure,} from "@/api/business/structure";
import {listPoint} from "@/api/business/point";

export default {
  name: "Config",
  dicts: ['alarm_level', 'iot_compute_type'],
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
      // 报警配置表格数据
      configList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pointId: null,
        alarmLevel: null,
        threshold: null,
        computeType: null,
        title: null,
        content: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        pointId: [
          {required: true, message: "所属测点不能为空", trigger: "blur"}
        ],
        threshold: [
          {required: true, message: "阈值不能为空", trigger: "blur"}
        ],
        computeType: [
          {required: true, message: "计算方式不能为空", trigger: "change"}
        ],
        title: [
          {required: true, message: "报警标题不能为空", trigger: "blur"}
        ],
        projectId: [
          {required: true, message: "所属项目不能为空", trigger: "blur"}
        ],
        structureId: [
          {required: true, message: "所属结构物不能为空", trigger: "blur"}
        ],
        alarmLevel: [
          {required: true, message: "告警等级不能为空", trigger: "blur"}
        ],
      },
      projectData: [],
      formProjectStructureData: [],
      optionsPointOrDeviceData: [],
    };
  },
  created() {
    this.getList();
    this.getProjectListFun();
  },
  methods: {
    /** 查询报警配置列表 */
    getList() {
      this.loading = true;
      listConfig(this.queryParams).then(response => {
        this.configList = response.rows;
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
        pointId: null,
        alarmLevel: null,
        threshold: null,
        computeType: null,
        title: null,
        content: null,
        createTime: null,
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
      this.reset();
      this.open = true;
      this.title = "添加报警配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getConfig(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改报警配置";
        this.dialogFormProjectChange();
        this.dialogFormProjectStructureChange();
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateConfig(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addConfig(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除报警配置编号为"' + ids + '"的数据项？').then(function () {
        return delConfig(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('cmp/config/export', {
        ...this.queryParams
      }, `config_${new Date().getTime()}.xlsx`)
    },
    /**项目chang事件,获取对应的结构物信息*/
    dialogFormProjectChange() {
      this.formProjectStructureData = [];
      this.optionsPointOrDeviceData = [];
      if (this.form.projectId) {
        var queryParam = {};
        queryParam.projectId = this.form.projectId;
        listStructure(queryParam).then(res => {
          if (res.code == 200) {
            for (let index in res.rows) {
              this.formProjectStructureData.push({
                name: res.rows[index].name,
                id: res.rows[index].id,
              })
            }
          }
        });
      }

    },
    /**结构物chang事件,通过结构物获取对应的测点信息*/
    dialogFormProjectStructureChange() {
      this.optionsPointOrDeviceData = [];
      var queryForm = {};
      queryForm.lev = '3';
      queryForm.structureId = this.form.structureId;
      if (this.form.structureId) {
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
    /**获取项目列表*/
    getProjectListFun() {
      this.projectData = [];
      listProject({}).then(res => {
        if (res.code == 200) {
          for (let index in res.rows) {
            this.projectData.push({
              name: res.rows[index].name,
              id: res.rows[index].id,
            })
          }
        }
      });
    },
  }
};
</script>
<style>
.formColumnClassForConfig {
  width: 80%;
}
</style>
