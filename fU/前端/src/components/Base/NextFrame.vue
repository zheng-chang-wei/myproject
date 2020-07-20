<template>
  <div style="line-height: 40px;height: 40px;text-align: center;">
    <i class="el-icon-d-arrow-left"
       style="height: 40px;width: 40px;cursor: pointer;"
       @click="clickNext(1)"></i>
    <span>{{index}}</span>
    <i class="el-icon-d-arrow-right"
       style="height: 40px;width: 40px;cursor: pointer;"
       @click="clickNext(2)"></i>
  </div>
</template>

<script>
  export default {
    name: "NextFrame",
    data() {
      return {
        index: 1
      }
    },
    methods: {
      reduceIndex: function () {
        if(this.index > 1){
          this.index--
        }
      },
      clickNext: function (flag) {
        if (flag == 1) {
          if (this.index > 1) {
            this.index--
          }else{
            return false
          }
        }
        if (flag == 2) {
          this.index++
        }
        this.$emit("comChanged", this.index)
      }
    }
  }
</script>

<style scoped>

</style>
