<template>
  <el-row class="center">
    <el-col class="label" :style="labelStyle" />
    <el-col class="text" :style="textStyle">
      {{ text }}
    </el-col>
  </el-row>
</template>
<script>

export default {
  props: {
    text: {
      type: String,
      default: ''
    },
    color: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      labelStyle: {
        backgroundColor: this.color
      },
      textStyle: {
        width: this.text.length * 20 + 'px'
      }
    }
  }
}

</script>
<style>
.label{
  width: 20px;
  height: 10px;
  margin-right: 10px;
}
.text{
   font-size: 9pt;
}
</style>
