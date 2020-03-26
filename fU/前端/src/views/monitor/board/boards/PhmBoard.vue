<template>
  <div class="phm-board">
    <el-row>
      <el-col :span="8">
        <div class="board-text">内存空间：{{board.ramSize}}
        </div>
      </el-col>
      <el-col :span="14">
        <el-progress class="store-progress" :percentage="board.ramUsed"></el-progress>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <div class="board-text">存储空间：{{board.romSize}}</div>
      </el-col>
      <el-col :span="14">
        <el-progress class="store-progress" :percentage="board.romUsed"></el-progress>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <div class="board-text">CPU：{{board.cpuUsed}}%</div>
      </el-col>
      <el-col :span="8">
        <div class="board-text">GPU：{{board.gpuUsed}}%</div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
  export default {
    props: {
      board: Object
    },
    data() {
      return {

      }
    },
    mounted() {
      console.log(this.board);
    }
  }

</script>
<style scoped>
  .phm-board .el-row {
    margin-bottom: 10px;
  }

</style>
