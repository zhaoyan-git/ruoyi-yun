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
          v-hasPermi="['cmp:sensor:add']"
        >新增
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
          v-hasPermi="['cmp:sensor:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sensorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="厂商" align="center" prop="deptName"/>
      <el-table-column label="产品" align="center" prop="product"/>
      <el-table-column label="型号" align="center" prop="model"/>
      <el-table-column label="模块号" align="center" prop="moduleNo"/>
      <el-table-column label="通道号" align="center" prop="channelNo"/>
      <el-table-column label="编号" align="center" prop="no"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['cmp:sensor:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cmp:sensor:remove']"
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

    <!-- 添加或修改【请填写功能名称】对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="厂商" class="formColumnClassForSen" prop="deptId">
          <treeselect
            v-model="form.deptId"
            style="width: 250px;"
            :options="deptOptions"
            placeholder="请选择企业"
            :show-count="true"
          />
        </el-form-item>
        <el-form-item label="产品" class="formColumnClassForSen" prop="product">
          <el-select v-model="form.product" placeholder="请输入产品" clearable @change="getModeListFun">
            <el-option
              v-for="dict in dict.type.product_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"

            />
          </el-select>
        </el-form-item>
        <el-form-item label="型号" class="formColumnClassForSen" prop="model">
          <el-select
            v-model="form.model"
            clearable
            placeholder="请选择型号"
          >
            <el-option
              v-for="dict in modelList"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="模块号" class="formColumnClassForSen" prop="moduleNo">
          <el-input v-model="form.moduleNo" placeholder="请输入模块号"/>
        </el-form-item>
        <el-form-item label="通道号" class="formColumnClassForSen" prop="channelNo">
          <el-input v-model="form.channelNo" placeholder="请输入通道号"/>
        </el-form-item>
        <el-form-item label="编号" class="formColumnClassForSen"  prop="no">
          <el-input v-model="form.no" placeholder="请输入编号"/>
        </el-form-item>
        <el-form-item label="批量添加" class="formColumnClassForSen" prop="batchadd">
          <el-switch
            v-model="form.batchadd"
            @change="switchChange"
          >
          </el-switch>
          <el-radio-group v-model="form.batchaddvalue" v-show="form.batchadd" style="margin-left: 7%;">
            <el-radio
              v-for="dict in dict.type.add_type"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>

        </el-form-item>
        <el-form-item label="备注" class="formColumnClassForSen"  prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
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
import {listSensor, getSensor, delSensor, addSensor, updateSensor} from "@/api/business/sensor";
import {listEquipment} from "@/api/business/equipment";
import {getTreeselectList} from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "SensorForm",
  props: ['sid', 'dtuId'],
  components: {Treeselect},
  dicts: ['alarm_flag', 'structure_type', 'monitoring_factors', 'add_type','product_type'],
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
        dtuId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      deptOptions: undefined,
      modelList: [],
    };
  },
  created() {
    this.getTreeselect();
    this.sensorList = [];
    if (this.dtuId) {
      this.queryParams.dtuId = this.dtuId;
      this.getList();
    }

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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSensor(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.businessId = this.sid;
          this.form.dtuId = this.dtuId;
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
      this.$emit("ok",'');
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除编号为"' + ids + '"的数据项？').then(function () {
        return delSensor(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('cmp/sensor/export', {
        ...this.queryParams
      }, `sensor_${new Date().getTime()}.xlsx`)
    },
    getTreeselect() {
      var queryForm = {};
      queryForm.type = '2';
      getTreeselectList(queryForm).then(response => {
        this.deptOptions = response.data;
      });
    },
    //获取型号列表
    getModeListFun() {
      this.modelList = [];
      //通过厂商和产品获取型号列表信息
      var queryForm = {};
      queryForm.manufacturer = this.form.deptId;
      queryForm.product = this.form.product;
      listEquipment(queryForm).then(response => {
        for (let index in response.rows) {
          this.modelList.push({
            label: response.rows[index].model,
            value: response.rows[index].model,
          })
        }
      });
    },
    switchChange(){
      console.log(this.form.batchadd);
    },
  }
};
</script>
<style>
.formColumnClassForSen{
  width: 60%;
}
</style>
