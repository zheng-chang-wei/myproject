<template>
  <el-card class="algorithm-card" shadow="hover">
    <div class="algorithm-title">
      <div class="pull-right">
        <el-tag v-if="algorithm.data.status==='Run'" effect="dark" type="success">正常运行</el-tag>
        <el-tag v-else-if="algorithm.data.status==='Err'" effect="dark" type="danger">异常运行</el-tag>
        <el-tag v-else-if="algorithm.data.status==='Stop'" effect="dark" type="info">停止运行</el-tag>
        <el-tag v-else-if="algorithm.data.status==='Idle'" effect="dark" type="info">空闲</el-tag>
      </div>
      <h4>{{algorithm.data.name}}</h4>
    </div>
    <div class="algorithm-body">
      <el-row style="margin: 10px">
        <el-col :span="12">
          <div class="board-text">
            槽位号：{{algorithm.data.slotId}}
          </div>
        </el-col>
        <el-col :span="12">
          <div class="board-text">
            子系统：{{algorithm.data.subsystem}}
          </div>
        </el-col>
      </el-row>
      <el-row style="margin: 10px">
        输入变量：
        <span class="variable-text" v-if="algorithm.variable.mvb">
          MVB变量[{{algorithm.variable.mvb}}]
        </span>
        <span class="variable-text" v-if="algorithm.variable.ecn">
          ECN变量[{{algorithm.variable.ecn}}]
        </span>
        <span class="variable-text" v-if="algorithm.variable.ad">
          AD变量[{{algorithm.variable.ad}}]
        </span>
      </el-row>
    </div>
    <div class="board-foot">
    </div>
  </el-card>
</template>
<script>
  export default {
    props: {
      algorithm: Object
    },
    data() {
      return {}
    }
  }

</script>
<style scoped>
  .algorithm-card {
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

  .detail-text {
    margin: 10px;
  }

  .variable-text {
    margin-left: 5px;
  }

</style>
<style>
  .algorithm-card .el-card__body {
    padding-top: 10px;
    padding-right: 8px;
  }

</style>
