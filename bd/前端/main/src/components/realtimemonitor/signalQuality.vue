<template>
  <section>
    <el-row class="alignBottomParent">
      <!-- <el-col class="item alignBottomItem" style="height:5px" />
      <el-col class="item alignBottomItem" style="height:10px" />
      <el-col class="item alignBottomItem" style="height:15px" />
      <el-col class="item alignBottomItem" style="height:20px" />
      <el-col class="item alignBottomItem" style="height:25px" /> -->
      <template v-for="index in itemCounts">
        <el-col :key="index" class="item alignBottomItem" :style="itemStyle(index)" />
      </template>
      <span class="alignBottomItem" style="margin-left:8px;width:30px" :underline="false">{{ signalQuality }}%</span>
    </el-row>
  </section>
</template>

<script>
export default {
  props: {
    signalQuality: {
      type: Number,
      default: null
    }
  },
  data() {
    return {
      exampleSpan: 0,
      itemCounts: 5
    }
  },
  methods: {
    itemStyle(index) {
      let style = 'background:#00FF00'
      if (this.signalQuality === 0) {
        style = 'background:#999999'
      } else if (this.signalQuality < 25) {
        if (index > 1) {
          style = 'background:#999999'
        }
      } else if (this.signalQuality < 50) {
        if (index > 2) {
          style = 'background:#999999'
        }
      } else if (this.signalQuality < 75) {
        if (index > 3) {
          style = 'background:#999999'
        }
      } else if (this.signalQuality < 95) {
        if (index > 4) {
          style = 'background:#999999'
        }
      }
      return 'height:' + index * 5 + 'px;' + style
    }
  }
}
</script>
<style scoped>
.item{
  background: #00FF00;
  width:3px;
  margin-left: 2px;
}
.alignBottomParent{
  display:flex;
  align-items:flex-end;
}
.alignBottomItem{
  display:table-cell;
  vertical-align:bottom;
}
</style>
