<template>
  <div class="board-detail">
    <div v-if="board.controlVer" class="board-text">控制程序版本：{{board.controlVer}}</div>
    <div v-if="board.fpgaVer" class="board-text">FPGA版本：{{board.fpgaVer}}</div>
    <div v-if="board.servVer" class="board-text">算法管理版本：{{board.servVer}}</div>
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
  .board-detail {
    padding: 10px;
  }

  .board-detail .board-text {
    margin-top: 5px;
  }

  .board-detail .board-text:first-of-type {
    margin-top: 0px;
  }

</style>
