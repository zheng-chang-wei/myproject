<template>
  <div class="ecn-board">
    <div class="board-text">
      数据状态:
      <el-tag :type="dataStatusType(board.dataStatus)">{{status}}</el-tag>
    </div>
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
    computed: {
      status: function () {
        return this.board.dataStatus === 'NO_DATA' ? '无数据' : '有数据'
      }
    },
    methods: {
      dataStatusType(dataStatus) {
        return dataStatus === 'NO_DATA' ? 'info' : 'success';
      }
    }
  }

</script>
<style scoped>
  .ecn-board {
    /* margin-bottom: 10px; */
  }

</style>
