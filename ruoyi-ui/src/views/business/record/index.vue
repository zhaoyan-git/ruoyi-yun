<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="结构物" prop="sid">
        <el-select clearable v-model="queryParams.sid"
                   placeholder="请选择所属结构物"
        >
          <el-option
            v-for="item in formProjectStructureData"
            v-bind:key="item.id"
            :label="item.name"
            :value="parseInt(item.id)"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="关键词" prop="keyWork">
        <el-input
          v-model="queryParams.keyWork"
          placeholder="请输入告警源,告警信息"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item >
        <el-checkbox-group v-model="checkedSources" @change="handleCheckedCitiesChange">
          <el-checkbox v-for="city in cities" :label="city" :key="city">{{city}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange"  :default-sort = "{prop: 'lev', order: 'descending'}">
      <el-table-column label="结构物" align="center" prop="sname" />
      <el-table-column label="告警源" align="center" prop="alarmSource" />
      <el-table-column label="等级" align="center" prop="lev"  sortable />
      <el-table-column label="告警信息" align="center" prop="alarmInfo" width="150" />
      <el-table-column label="产生次数" align="center" prop="prodNum" sortable />
      <el-table-column label="产生时间" align="center" prop="prodDate" sortable >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.prodDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateDate" sortable>
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
<!--          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['cmp:record:edit']"
          >修改</el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cmp:record:remove']"
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
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="所属项目" prop="projectId">
          <el-input v-model="form.projectId" placeholder="请输入所属项目" />
        </el-form-item>
        <el-form-item label="是否已读" prop="readFlag">
          <el-select v-model="form.readFlag" placeholder="是否已读" clearable>
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">63</div>
    </el-dialog>
  </div>
</template>

<script>
import {listStructure} from "@/api/business/structure";

/*
const cityOptions = ['数据异常', '异物入侵', 'DTU异常', '传感器异常','网关异常','节点异常'];
*/
const cityOptions = ['数据异常'];
import { listRecord, getRecord, delRecord, addRecord, updateRecord } from "@/api/business/record";

export default {
  name: "Record",
  dicts: ['sys_yes_no'],
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
      recordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sid: null,
        keyWork:null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      checkAll: false,
     /* checkedSources: ['数据异常', '异物入侵', 'DTU异常', '传感器异常','网关异常','节点异常'],*/
      checkedSources: ['数据异常'],
      cities: cityOptions,
      isIndeterminate: true,
      formProjectStructureData: [],
    };
  },
  created() {
    this.getList()
    this.getStrctureListFun()
  },
  methods: {
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      this.queryParams.checkedSources = this.checkedSources;
      listRecord(this.queryParams).then(response => {
        this.recordList = response.rows;
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
        title: null,
        content: null,
        projectId: null,
        readFlag: null,
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
      getRecord(id).then(response => {
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
            updateRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRecord(this.form).then(response => {
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
        return delRecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('cmp/record/export', {
        ...this.queryParams
      }, `record_${new Date().getTime()}.xlsx`)
    },
    handleCheckAllChange(val) {
      this.checkedSources = val ? cityOptions : [];
      this.isIndeterminate = false;
    },
    handleCheckedCitiesChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.cities.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length;
    },
    //获取结构物信息
    getStrctureListFun(){
      listStructure({}).then(res => {
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
  }
};
</script>
