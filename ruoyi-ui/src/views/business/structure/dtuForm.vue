<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="150px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="厂商" class="formColumnClassForDTU" prop="manufacturer">
            <treeselect
              v-model="form.manufacturer"
              style="width: 250px;"
              :options="deptOptions"
              placeholder="请选择企业"
              :show-count="true"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="产品" class="formColumnClassForDTU" prop="product">
            <el-select v-model="form.product" placeholder="请输入产品" clearable>
              <el-option
                v-for="dict in dict.type.product_type"
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
          <el-form-item label="型号" class="formColumnClassForDTU" prop="model">
            <el-input maxlength="100" show-word-limit v-model="form.model" placeholder="请输入型号"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="名称" class="formColumnClassForDTU" prop="name">
            <el-input maxlength="100" show-word-limit v-model="form.name" type="textarea" placeholder="请输入内容"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="SIM卡号" class="formColumnClassForDTU" prop="sim">
            <el-input maxlength="100" show-word-limit v-model="form.sim" placeholder="请输入SIM卡号"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="接口ID" class="formColumnClassForDTU" prop="dtuNo">
            <el-input maxlength="100" show-word-limit v-model="form.dtuNo" placeholder="请输入接口ID"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="CRC校验" class="formColumnClassForDTU" prop="crc">
            <el-switch
              v-model="form.crc"
            >
            </el-switch>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="单包大小(Byte)" class="formColumnClassForDTU" prop="pageSize">
            <el-input typr="number" v-model="form.pageSize" placeholder="请输入单包大小(Byte)"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="发送延时(ms)" class="formColumnClassForDTU" prop="transmissionDelay">
            <el-input type="number" v-model="form.transmissionDelay" placeholder="请输入发送延时(ms)"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="同步接收包组时间" class="formColumnClassForDTU" prop="receivedPacket">
            <el-input type="number" v-model="form.receivedPacket" placeholder="请输入同步接收包组时间"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="码流日志" class="formColumnClassForDTU" prop="streamLog">
            <el-switch
              v-model="form.streamLog"
            >
            </el-switch>
          </el-form-item>
        </el-col>

      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="备注" class="formColumnClassForDTU" prop="remark">
            <el-input maxlength="200" show-word-limit v-model="form.remark" type="textarea" placeholder="请输入内容"/>
          </el-form-item>
        </el-col>

      </el-row>


    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import {listDtu, getDtu, delDtu, addDtu, updateDtu} from "@/api/business/dtu";
import {getTreeselectList} from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {listSensor} from "@/api/business/sensor";

export default {
  name: "Dtu",
  components: {Treeselect},
  dicts: ['alarm_flag', 'structure_type', 'monitoring_factors', 'add_type', 'product_type'],
  props: ['sid', 'equipmentId'],
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
      dtuList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        manufacturer: null,
        product: null,
        model: null,
        name: null,
        businessId: null,
        sim: null,
        dtuNo: null,
        crc: null,
        equipmentId: null,
        sid: null,
        pageSize: null,
        transmissionDelay: null,
        receivedPacket: null,
        streamLog: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      //部门树选项
      deptOptions: undefined,
    };
  },
  created() {
    this.getTreeselect();
    this.getDtuByBusinessId();
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
        manufacturer: null,
        product: null,
        model: null,
        name: null,
        businessId: null,
        sim: null,
        dtuNo: null,
        crc: null,
        equipmentId: null,
        pageSize: null,
        transmissionDelay: null,
        receivedPacket: null,
        streamLog: null,
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
      getDtu(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改【请填写功能名称】";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.businessId = this.sid;
          this.form.equipmentId = this.equipmentId;
          if (this.form.id != null) {
            updateDtu(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDtu(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
      this.$emit("ok", '');
    },
    getTreeselect() {
      var queryForm = {};
      queryForm.type = '2';
      getTreeselectList(queryForm).then(response => {
        this.deptOptions = response.data;
      });
    },
    getDtuByBusinessId() {
      this.queryParams.businessId = this.sid;
      this.queryParams.equipmentId = this.equipmentId;
      listDtu(this.queryParams).then(response => {
        if (response.rows && response.rows.length > 0) {
          this.form = response.rows[0];
        }
      });
    },
  }
};
</script>
<style>
.formColumnClassForDTU {
  width: 100%
}
</style>
