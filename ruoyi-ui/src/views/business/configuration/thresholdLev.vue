<template>

</template>

<script>


export default {
  name: "ThresholdLev",

  data() {
    return {
      dynamicValidateForm: {
        domains: [{
          value: ''
        }],
      },
    };
  },
  created() {
      this.dynamicValidateForm.domains = [];
  },
  methods: {
    removeDomain(item) {
      if(this.dynamicValidateForm.domains && this.dynamicValidateForm.domains.length ==1){
        return ;
      }
      //删除等级最小的
      item = this.dynamicValidateForm.domains[this.dynamicValidateForm.domains.length-1];
      var index = this.dynamicValidateForm.domains.indexOf(item)
      if (index !== -1) {
        this.dynamicValidateForm.domains.splice(index, 1)
      }
    },
    addDomain() {
      if(this.dynamicValidateForm.domains && this.dynamicValidateForm.domains.length >=3){
        return ;
      }
      this.dynamicValidateForm.domains.push({
        value: '',
        key: Date.now(),
        lev: this.dynamicValidateForm.domains.length+1,
      });
    },
    switchChange(value){
      console.log(value);
    }
  }
};
</script>
