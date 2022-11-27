<template>
  <div class="dashboard-editor-container" >
    <panel-group />


  </div>
</template>

<script>
import PanelGroup from './PanelGroup'
export default {
  name: 'Index',
  components: {
    PanelGroup,

  },
  data() {
    return {
      form: {},
      flag: false,

    }
  },
  created() {

  },
  methods: {

  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 0px;
  background-color: #fffef9;
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
