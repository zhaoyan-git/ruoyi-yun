<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="厂商" prop="manufacturer">
        <el-input
          v-model="queryParams.manufacturer"
          placeholder="请输入厂商"
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
      <el-form-item label="所属设备ID" prop="businessId">
        <el-input
          v-model="queryParams.businessId"
          placeholder="请输入所属设备ID"
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
      <el-form-item label="接口ID" prop="dtuNo">
        <el-input
          v-model="queryParams.dtuNo"
          placeholder="请输入接口ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="CRC" prop="crc">
        <el-input
          v-model="queryParams.crc"
          placeholder="请输入CRC"
          clearable
          @keyup.enter.native="handleQuery"
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
      <el-form-item label="单包大小(Byte)" prop="pageSize">
        <el-input
          v-model="queryParams.pageSize"
          placeholder="请输入单包大小(Byte)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发送延时(ms)" prop="transmissionDelay">
        <el-input
          v-model="queryParams.transmissionDelay"
          placeholder="请输入发送延时(ms)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="同步接收包组时间" prop="receivedPacket">
        <el-input
          v-model="queryParams.receivedPacket"
          placeholder="请输入同步接收包组时间"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="码流日志" prop="streamLog">
        <el-input
          v-model="queryParams.streamLog"
          placeholder="请输入码流日志"
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
          v-hasPermi="['cmp:dtu:add']"
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
          v-hasPermi="['cmp:dtu:edit']"
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
          v-hasPermi="['cmp:dtu:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['cmp:dtu:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dtuList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="${comment}" align="center" prop="id" />
      <el-table-column label="厂商" align="center" prop="manufacturer" />
      <el-table-column label="产品" align="center" prop="product" />
      <el-table-column label="型号" align="center" prop="model" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="所属设备ID" align="center" prop="businessId" />
      <el-table-column label="SIM卡号" align="center" prop="sim" />
      <el-table-column label="接口ID" align="center" prop="dtuNo" />
      <el-table-column label="CRC" align="center" prop="crc" />
      <el-table-column label="设备ID号" align="center" prop="equipmentId" />
      <el-table-column label="单包大小(Byte)" align="center" prop="pageSize" />
      <el-table-column label="发送延时(ms)" align="center" prop="transmissionDelay" />
      <el-table-column label="同步接收包组时间" align="center" prop="receivedPacket" />
      <el-table-column label="码流日志" align="center" prop="streamLog" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['cmp:dtu:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cmp:dtu:remove']"
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
        <el-form-item label="厂商" prop="manufacturer">
          <el-input v-model="form.manufacturer" placeholder="请输入厂商" />
        </el-form-item>
        <el-form-item label="产品" prop="product">
          <el-input v-model="form.product" placeholder="请输入产品" />
        </el-form-item>
        <el-form-item label="型号" prop="model">
          <el-input v-model="form.model" placeholder="请输入型号" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="所属设备ID" prop="businessId">
          <el-input v-model="form.businessId" placeholder="请输入所属设备ID" />
        </el-form-item>
        <el-form-item label="SIM卡号" prop="sim">
          <el-input v-model="form.sim" placeholder="请输入SIM卡号" />
        </el-form-item>
        <el-form-item label="接口ID" prop="dtuNo">
          <el-input v-model="form.dtuNo" placeholder="请输入接口ID" />
        </el-form-item>
        <el-form-item label="CRC" prop="crc">
          <el-input v-model="form.crc" placeholder="请输入CRC" />
        </el-form-item>
        <el-form-item label="设备ID号" prop="equipmentId">
          <el-input v-model="form.equipmentId" placeholder="请输入设备ID号" />
        </el-form-item>
        <el-form-item label="单包大小(Byte)" prop="pageSize">
          <el-input v-model="form.pageSize" placeholder="请输入单包大小(Byte)" />
        </el-form-item>
        <el-form-item label="发送延时(ms)" prop="transmissionDelay">
          <el-input v-model="form.transmissionDelay" placeholder="请输入发送延时(ms)" />
        </el-form-item>
        <el-form-item label="同步接收包组时间" prop="receivedPacket">
          <el-input v-model="form.receivedPacket" placeholder="请输入同步接收包组时间" />
        </el-form-item>
        <el-form-item label="码流日志" prop="streamLog">
          <el-input v-model="form.streamLog" placeholder="请输入码流日志" />
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
import { listDtu, getDtu, delDtu, addDtu, updateDtu } from "@/api/business/dtu";

export default {
  name: "Dtu",
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
        pageSize: null,
        transmissionDelay: null,
        receivedPacket: null,
        streamLog: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      listDtu(this.queryParams).then(response => {
        this.dtuList = response.rows;
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
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除【请填写功能名称】编号为"' + ids + '"的数据项？').then(function() {
        return delDtu(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('cmp/dtu/export', {
        ...this.queryParams
      }, `dtu_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
