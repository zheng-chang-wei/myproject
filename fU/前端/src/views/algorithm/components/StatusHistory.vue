<template>
  <div class="status-history board-card">
    <div class="title">运行历史</div>
    <div :style="{height:height+'px'}" class="status-card">
      <el-scrollbar style="height:100%">
        <div v-for="(item,index) in data" :key="index" class="status-card">
          <el-row>
            <el-col :span="12">
              <div class="status-title">{{ item.timestamp }}</div>
            </el-col>
            <el-col :span="12">
              <div class="status-title">{{ item.message }}</div>
            </el-col>
          </el-row>
        </div>
      </el-scrollbar>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    data: { type: Array, default: () => [] }
  },
  data() {
    return {
      height: 360
    }
  },
  created() {
  },
  mounted() {
    this.changeHeight()
    window.addEventListener('resize', this.changeHeight)
  },
  destroyed() {
    window.removeEventListener('resize', this.changeHeight)
  },
  methods: {
    changeHeight() {
      this.height = document.body.offsetHeight - 370
    }
  }
}

</script>
<style>
  .status-card{
    background-color:#ffffff;
  }
  .status-history .el-scrollbar__wrap{
    overflow-x: hidden;
  }
</style>
