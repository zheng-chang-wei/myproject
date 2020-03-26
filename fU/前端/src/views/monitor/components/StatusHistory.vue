<template>
  <div class="status-history board-card">
    <h4>运行状况历史记录</h4>
    <div class="status-card" v-for="item in data">
      <el-row>
        <el-col :span="8">
          <div class="status-title">{{item.timestamp}}</div>
        </el-col>
        <el-col :span="16">
          <div class="status-title">{{item.message}}</div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
  export default {
    props: {
      data: Array,
    },
    data() {
      return {

      }
    }
  }

</script>
