<template>
  <div class="wireless-board">
    <el-row>
      <el-col :span="12">
        <div class="board-text">4G信号：{{board.sigQuality4G}}</div>
      </el-col>
      <el-col :span="12">
        <div class="board-text">WIFI信号：{{board.sigQualityWifi}}</div>
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
