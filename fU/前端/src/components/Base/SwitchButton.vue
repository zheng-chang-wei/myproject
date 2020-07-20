<template>
  <div style="width: 100%;
              margin-top: 1px;
              padding: 1px;
              justify-content: space-around;
              display: flex;
              border: 1px solid #ddd;
              background-color: white;">
    <el-button :type="index==1?'primary':''"
               style="width: 47%;"
               size="mini"
               @click="select(1)">窗口1
    </el-button>
    <el-button :type="index==2?'primary':''"
               style="width: 47%;"
               size="mini"
               @click="select(2)">窗口2
    </el-button>
  </div>
</template>
<script>
  export default {
    name: "SwitchButton",
    data() {
      return {
        index: 1
      }
    },
    methods: {
      select: function (flag) {
        this.index = flag
        this.$emit("comChanged", flag)
      }
    }
  }
</script>

<style scoped>
</style>
