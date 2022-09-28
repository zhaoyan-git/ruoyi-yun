<template>
  <div class="app-container">
    <!-- 添加或修改解决方案对话框 -->

      <el-form ref="form" :model="form"  label-width="80px">

          <editor v-model="form.content" :min-height="192"/>

      </el-form>

  </div>
</template>

<script>
import {  getSolution} from "@/api/business/solution";

export default {
  name: "SolutionForm",
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
      // 解决方案表格数据
      solutionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        content: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          { required: true, message: "标题不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.handleUpdate(12);
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
        title: null,
        content: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 修改按钮操作 */
    handleUpdate(id) {
      getSolution(id).then(response => {
        this.form = response.data;
        this.open = true;
      });
    },
  }
};
</script>
