<template>
  <div class="cpu-board">
    <el-row>
      <el-col :span="8">
        <div class="board-text">内存空间：{{board.ramSize}}
        </div>
      </el-col>
      <el-col :span="14">
        <el-progress class="store-progress" :percentage="board.ramUsed"></el-progress>
      </el-col>
    </el-row>
  </div>
</template>
<script>
  export default {
    props: {
      board: Object
    }
  }

</script>
<style scoped>
  .cpu-board {
    /* margin-bottom: 10px; */
  }

</style>
