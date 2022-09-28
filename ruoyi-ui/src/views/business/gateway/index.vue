<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入设备名称"
          clearable
          size="small"
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
          v-hasPermi="['cmp:gateway:add']"
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
          v-hasPermi="['cmp:gateway:edit']"
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
          v-hasPermi="['cmp:gateway:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="gatewayList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="所属项目" align="center" prop="projectName" />
      <el-table-column label="设备名称" align="center" prop="name" />
      <el-table-column label="厂商" align="center" prop="manufacturerName" />
      <el-table-column label="型号" align="center" prop="modelNumber" />
      <el-table-column label="设备说明" align="center" prop="detail" />
      <el-table-column label="sim卡号" align="center" prop="simNumber" />
      <el-table-column label="DTU ID" align="center" prop="dtuId" />
      <el-table-column label="是否在线" align="center" prop="onlineFlag" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.onlineFlag"></dict-tag>
        </template>
      </el-table-column>
      <el-table-column label="ip" align="center" prop="ip" />
      <el-table-column label="设备在地图上的纬度" align="center" prop="positionLat" />
      <el-table-column label="设备在地图上的经度" align="center" prop="positionLng" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['cmp:gateway:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cmp:gateway:remove']"
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

    <!-- 添加或修改网关管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-form-item label="所属项目" prop="projectId" class="formColumnForGateWay">
          <el-select
            v-model="form.projectId"
            placeholder="请选择所属项目"
          >
            <el-option
              v-for="item in projectData"
              v-bind:key="item.id"
              :label="item.name"
              :value="parseInt(item.id)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备名称" prop="name" class="formColumnForGateWay">
          <el-input v-model="form.name" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="厂商" prop="manufacturer" class="formColumnForGateWay">
          <treeselect
            v-model="form.manufacturer"
            style="width: 250px;"
            :options="deptOptions"
            placeholder="请选择企业"
            :show-count="true"
          />
        </el-form-item>
        <el-form-item label="型号" prop="modelNumber" class="formColumnForGateWay">
          <el-input v-model="form.modelNumber"  placeholder="请输入型号" />
        </el-form-item>
        <el-form-item label="设备说明" prop="detail" class="formColumnForGateWay">
          <el-input v-model="form.detail"  placeholder="请输入设备说明" />
        </el-form-item>
        <el-form-item label="sim卡号" prop="simNumber" class="formColumnForGateWay">
          <el-input v-model="form.simNumber"  placeholder="请输入sim卡号" />
        </el-form-item>
        <el-form-item label="DTU ID" prop="dtuId" class="formColumnForGateWay">
          <el-input v-model="form.dtuId"  placeholder="请输入DTU ID" />
        </el-form-item>
        <el-form-item label="是否在线" prop="onlineFlag" class="formColumnForGateWay">
          <el-select v-model="form.onlineFlag" placeholder="请输入是否在线">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="ip" prop="ip" class="formColumnForGateWay">
          <el-input v-model="form.ip" placeholder="请输入ip" />
        </el-form-item>
        <el-form-item label="设备在地图上的纬度" prop="positionLat" class="formColumnForGateWay">
          <el-input v-model="form.positionLat"  placeholder="请输入设备在地图上的纬度" />
        </el-form-item>
        <el-form-item label="设备在地图上的经度" prop="positionLng" class="formColumnForGateWay">
          <el-input v-model="form.positionLng"  placeholder="请输入设备在地图上的经度" />
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
import { listGateway, getGateway, delGateway, addGateway, updateGateway } from "@/api/business/gateway";
import {listProject} from "@/api/business/project";
import {getTreeselectList} from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Gateway",
  components: {Treeselect},
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
      // 网关管理表格数据
      gatewayList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectId: null,
        name: null,
        manufacturer: null,
        modelNumber: null,
        detail: null,
        simNumber: null,
        dtuId: null,
        onlineFlag: null,
        ip: null,
        positionLat: null,
        positionLng: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      projectData:[],
      //部门树选项
      deptOptions: undefined,
    };
  },
  created() {
    this.getList();
    this.getProjectListFun();
    this.getTreeselect();
  },
  methods: {
    /** 查询网关管理列表 */
    getList() {
      this.loading = true;
      listGateway(this.queryParams).then(response => {
        this.gatewayList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /**获取项目列表*/
    getProjectListFun(){
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
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        projectId: null,
        name: null,
        manufacturer: null,
        modelNumber: null,
        detail: null,
        simNumber: null,
        dtuId: null,
        onlineFlag: null,
        ip: null,
        positionLat: null,
        positionLng: null,
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
      this.title = "添加网关管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getGateway(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改网关管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateGateway(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGateway(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除网关管理编号为"' + ids + '"的数据项？').then(function() {
        return delGateway(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('cmp/gateway/export', {
        ...this.queryParams
      }, `gateway_${new Date().getTime()}.xlsx`)
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
<style>
.formColumnForGateWay{
  width: 80%;
}
</style>
