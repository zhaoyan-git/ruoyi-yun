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
            <el-select v-model="form.product" placeholder="请输入产品" clearable @change="getModeListFun">
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
          <el-form-item label="接口ID(DTU ID)" class="formColumnClassForDTU" prop="dtuNo">
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
        <el-col :span="12">
          <el-form-item label="组包" class="formColumnClassForDTU" prop="groupPackage">
            <el-switch
              v-model="form.groupPackage"
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
import {listEquipment, getEquipment, delEquipment, addEquipment, updateEquipment} from "@/api/business/equipment";
import {getTreeselectList} from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {addDtu, getDtu, updateDtu} from "@/api/business/dtu";

export default {
  name: "EquipmentList",
  components: {Treeselect},
  props: ['sid','id'],
  dicts: ['alarm_flag', 'structure_type', 'monitoring_factors', 'add_type', 'product_type'],
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
      modelList: [],
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
      sid : null,
      id : null,
    };
  },
  created() {
    this.getTreeselect();
    debugger;
    if(this.id){
      this.handleUpdate(this.id);
    }
  },
  methods: {
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
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          debugger
          this.form.businessId = this.sid;
          if (this.form.id != null) {
            updateDtu(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");

              this.$emit('ok')
            });
          } else {
            addDtu(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.$emit('ok')
            });
          }
        }
      });

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
      queryForm.manufacturer = this.form.manufacturer;
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
    /** 修改按钮操作 */
    handleUpdate(id) {
      this.reset();
      getDtu(id).then(response => {
        this.form = response.data;
      });
    },
  }
};
</script>
