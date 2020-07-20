<template>
  <div>
    <h4 class="title">运行历史</h4>
    <el-card shadow="hover" style="height:190px;overflow-y: scroll;">
      <el-row v-for="(item,index) in history" :key="index" class="status-card">
        <el-col :span="8">
          <div class="status-title">{{ item.timestamp }}</div>
        </el-col>
        <el-col :span="16">
          <div class="status-title">{{ item.message }}</div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>
<script>
import app from '@/common/js/app'
export default {
  data() {
    return {
      history: []
    }
  },
  mounted() {
    this.getHistory()
  },
  methods: {
    getHistory() {
      app.get('board_history').then(res => {
        if (res.code === 0) {
          this.history = res.data
        }
      })
    }
  }
}

</script>
<style scoped>
.status-card {
  font-size: 8pt;
}

.status-title {
  margin: 5px;
}
</style>
