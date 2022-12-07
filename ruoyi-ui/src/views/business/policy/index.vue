<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['cmp:policy:add']"
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
          v-hasPermi="['cmp:policy:edit']"
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
          v-hasPermi="['cmp:policy:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['cmp:policy:export']"
        >导出
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="policyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="项目名称" align="center" prop="pname"
                       :filters=filterProjectColumnArr
                       :filter-method="filterHandler"
                       filter-placement="bottom-end"
      >

      </el-table-column>
      <el-table-column label="结构物名称" align="center" prop="sname"/>
      <el-table-column label="告警接收人" align="center" prop="reserve1">
      </el-table-column>
      <el-table-column label="接收告警类型" align="center" prop="reserve2">
      </el-table-column>
      <el-table-column label="短信告警级别" align="center" prop="notealarmlev" sortable>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.alarm_lev" :value="scope.row.notealarmlev"></dict-tag>
        </template>
      </el-table-column>
      <el-table-column label="邮件告警级别" align="center" prop="emailalarmlev" sortable>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.alarm_lev" :value="scope.row.emailalarmlev"></dict-tag>
        </template>
      </el-table-column>
      <el-table-column label="微信告警级别" align="center" prop="wechatalarmlev" sortable>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.alarm_lev" :value="scope.row.wechatalarmlev"></dict-tag>
        </template>
      </el-table-column>
      <el-table-column label="启用状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['cmp:policy:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cmp:policy:remove']"
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

    <!-- 添加或修改告警策略配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="所属项目" prop="pid">
          <el-select
            v-model="form.pid"
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
        <el-form-item label="所属结构物" prop="sid">
          <el-select v-model="form.sid"
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
        <el-form-item label="告警接收人" prop="receiverperson">
          <el-select v-model="receiverpersonArr"
                     placeholder="请选择告警接收人"
                     multiple
          >
            <el-option
              v-for="item in userList"
              v-bind:key="item.id"
              :label="item.name"
              :value="parseInt(item.id)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="接收告警类型" prop="alarmtype" v-if="isSelect" >
          <el-select v-model="alarmTypeArr"
                     placeholder="请选择接收告警类型"
                     multiple clearable
          >
            <el-option
              v-for="dict in dict.type.revive_alarm_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="短信告警级别" prop="notealarmlev">
          <el-select v-model="form.notealarmlev"
                     placeholder="请选择短信告警级别"
                     clearable
          >
            <el-option
              v-for="dict in dict.type.alarm_lev"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="邮件告警级别" prop="emailalarmlev">
          <el-select v-model="form.emailalarmlev"
                     placeholder="请选择邮件告警级别"
                     clearable
          >
            <el-option
              v-for="dict in dict.type.alarm_lev"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="微信告警级别" prop="wechatalarmlev">
          <el-select v-model="form.wechatalarmlev"
                     placeholder="请选择微信告警级别" clearable
          >
            <el-option
              v-for="dict in dict.type.alarm_lev"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
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
import {listPolicy, getPolicy, delPolicy, addPolicy, updatePolicy, changeStatus} from "@/api/business/policy";
import {listStructure} from "@/api/business/structure";
import {listProject} from "@/api/business/project";
import {getUserProfile} from "@/api/system/user";

export default {
  name: "Policy",
  dicts: ['revive_alarm_type', 'alarm_lev'],
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
      // 告警策略配置表格数据
      policyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pid: null,
        pname: null,
        sid: null,
        sname: null,
        receiverperson: null,
        alarmtype: null,
        receivertype: null,
        notealarmlev: null,
        emailalarmlev: null,
        wechatalarmlev: null,
        status: null,
        reserve1: null,
        reserve2: null,
        reserve3: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        pid: [
          {required: true, message: "项目不能为空", trigger: "blur"}
        ],
        sid: [
          {required: true, message: "结构物不能为空", trigger: "blur"}
        ],

      },
      projectData: [],
      formProjectStructureData: [],
      user: {},
      userList: [],
      alarmTypeArr: [],
      receiverpersonArr: [],
      isSelect:false,
      filterProjectColumnArr:[],
    };
  },
  created() {
    this.getList();
    this.getProjectListFun()
  },
  methods: {
    /** 查询告警策略配置列表 */
    getList() {
      this.loading = true;
      listPolicy(this.queryParams).then(response => {
        this.policyList = response.rows;
        this.total = response.total;
        for (let index in response.rows) {
          this.filterProjectColumnArr.push({
            text: response.rows[index].pname,
            value: response.rows[index].pname,
          })
        }
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
        pid: null,
        pname: null,
        sid: null,
        sname: null,
        receiverperson: null,
        alarmtype: null,
        receivertype: null,
        notealarmlev: null,
        emailalarmlev: null,
        wechatalarmlev: null,
        status: "0",
        reserve1: null,
        reserve2: null,
        reserve3: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
      this.title = "添加告警策略配置";
      this.getUser();
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getUser();
      const id = row.id || this.ids
      getPolicy(id).then(response => {
        this.form = response.data;
        this.title = "修改告警策略配置";
        this.alarmTypeArr = JSON.parse(response.data.alarmtype);
        this.receiverpersonArr = JSON.parse(response.data.receiverperson);
        this.dialogFormProjectChange();
        this.isSelect = true;
        this.open = true;

      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.alarmTypeArr = this.alarmTypeArr;
          this.form.receiverpersonArr = this.receiverpersonArr;
          if (this.form.id != null) {
            updatePolicy(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPolicy(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除告警策略配置编号为"' + ids + '"的数据项？').then(function () {
        return delPolicy(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('cmp/policy/export', {
        ...this.queryParams
      }, `policy_${new Date().getTime()}.xlsx`)
    },
    /**项目chang事件,获取对应的结构物信息*/
    dialogFormProjectChange() {
      this.formProjectStructureData = [];
      if (this.form.pid) {
        var queryParam = {};
        queryParam.projectId = this.form.pid;
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
    getUser() {
      getUserProfile().then(response => {
        this.user = response.data;
        this.userList.push({
          name: response.data.nickName,
          id: response.data.userId,
        })
      });
    },
    // 用户状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '"吗？').then(function () {
        return changeStatus(row.id, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function () {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    filterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    }
  }
};
</script>
