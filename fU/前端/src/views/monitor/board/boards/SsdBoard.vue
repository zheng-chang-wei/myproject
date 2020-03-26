<template>
  <div class="ssd-board">
    <el-row>
      <el-col :span="8">
        <div class="board-text">存储空间：{{board.ssdSize}}</div>
      </el-col>
      <el-col :span="14">
        <el-progress class="store-progress" :percentage="board.ssdUsed"></el-progress>
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
  .ssd-board {
    /* margin-bottom: 10px; */
  }

</style>
