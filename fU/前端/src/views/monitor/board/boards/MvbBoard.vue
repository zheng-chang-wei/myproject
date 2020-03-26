<template>
  <div class="mvb-board">
    <el-row>
      <el-col :span="12">
        <div class="board-text">
          A通道:
          <el-tag :type="dataStatusType(board.dataStatusA)">{{statusA}}</el-tag>
        </div>
      </el-col>
      <el-col :span="12">
        <div class="board-text">
          B通道:
          <el-tag :type="dataStatusType(board.dataStatusB)">{{statusB}}</el-tag>
        </div>
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
    computed: {
      statusA: function () {
        return this.board.dataStatusA === 'NO_DATA' ? '无数据' : '有数据'
      },
      statusB: function () {
        return this.board.dataStatusB === 'NO_DATA' ? '无数据' : '有数据'
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
  .mvb-board {
    /* margin-bottom: 10px; */
  }

</style>
