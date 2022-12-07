<template>
  <div class="app-container">
      <el-card >
        <div slot="header">
          <span>请在英文输入状态下填写阈值，阈值填写格式：开区间，多个区间以 “;” 分隔，且区间不能重叠。 其中，正无穷用 “+” 表示，负无穷用 “-” 表示。例如：</span>
        </div>
        <div class="el-table el-table--enable-row-hover el-table--medium">
          <table cellspacing="0" style="width: 100%;">
            <thead>
            <tr>
              <th class="el-table__cell el-table__cell is-leaf"><div class="cell">阈值等级</div></th>
              <th class="el-table__cell is-leaf"><div class="cell">测量值(v)</div></th>
              <th class="el-table__cell is-leaf"><div class="cell">阈值配置</div></th>
            </tr>
            </thead>
            <tbody >
            <tr>
              <td class="el-table__cell is-leaf"><div class="cell">一级阈值</div></td>
              <td class="el-table__cell is-leaf"><div class="cell">v <= -100 或 v > 100</div></td>
              <td class="el-table__cell is-leaf"><div class="cell">(-,-100);(100,+)</div></td>
            </tr>
            <tr>
              <td class="el-table__cell is-leaf"><div class="cell">二级阈值</div></td>
              <td class="el-table__cell is-leaf"><div class="cell">-100 < v <= -55.5</div></td>
              <td class="el-table__cell is-leaf"><div class="cell">(-100,-55.5)</div></td>
            </tr>
            <tr>
              <td class="el-table__cell is-leaf"><div class="cell">三级阈值</div></td>
              <td class="el-table__cell is-leaf"><div class="cell">-55.5 < v <= -20</div></td>
              <td class="el-table__cell is-leaf"><div class="cell">(-55.5,-20)</div></td>
            </tr>
            </tbody>
          </table>
        </div>
      </el-card>

  </div>
</template>

<script>


export default {
  name: "ThresholdFormat",

  data() {
    return {

    };
  },
  created() {

  },
  methods: {
  }
};
</script>
