<template>
  <el-card class="basic-board" shadow="hover">
    <div class="board-title">
      <div class="pull-right">
        <el-tag v-if="board.cardStatus==='WORKING'" effect="dark" type="success">正常运行</el-tag>
        <el-tag v-else-if="board.cardStatus==='ERROR'" effect="dark" type="danger">异常运行</el-tag>
        <el-tag v-else-if="board.cardStatus==='STOPPED'" effect="dark" type="info">停止运行</el-tag>
        <el-tag v-else-if="board.cardStatus==='IDLE'" effect="dark" type="info">空闲</el-tag>
      </div>
      <h4>{{board.boardType}}板卡【{{board.slotID}}】</h4>
    </div>
    <div class="board-body">
      <el-row style="margin-bottom: 10px">
        <el-col>
          <div v-if="board.coreTemp" class="board-text">内核温度:{{board.coreTemp}}℃</div>
        </el-col>
      </el-row>
      <slot :board="board"></slot>
    </div>
    <div class="board-foot">
      <div class="pull-right">
        <el-popover v-if="board.controlVer" placement="left" width="200" trigger="click">
          <board-detail :board="board"></board-detail>
          <el-button class="btn-more" icon="el-icon-more" size="mini" circle slot="reference"></el-button>
        </el-popover>
      </div>
    </div>
  </el-card>
</template>
<script>
  import BoardDetail from './BoardDetail'
  export default {
    components: {
      BoardDetail
    },
    props: {
      board: Object
    },
    data() {
      return {}
    },
  }

</script>
<style scoped>
  .basic-board {
    padding-bottom: 10px;
    margin-bottom: 10px;
  }

  .pull-right {
    float: right;
  }

  .btn-more {
    border: 0;
    background-color: #f0f0f0;
  }


  h4 {
    font-size: 18px;
    font-weight: 400;
    line-height: 32px;
    margin-top: 0;
    margin-bottom: 5px;
  }

</style>
<style>
  .basic-board .el-card__body {
    padding-top: 10px;
    padding-right: 8px;
  }

</style>
