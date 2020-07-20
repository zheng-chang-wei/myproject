<template>
  <div class="board-monitor">
    <el-row class="center">
      <!-- 状态摘要 -->
      <el-col :md="5" :xs="24">
        <status-abstract ref="statusAbstract" @error="error" />
      </el-col>
      <!-- 机箱示意图 -->
      <el-col :md="14" :xs="24" class="center">
        <chassis ref="chassis" />
      </el-col>
      <!-- 运行历史 -->
      <el-col :md="5" :xs="24">
        <status-history ref="statusHistory" />
      </el-col>
    </el-row>
    <el-row style="margin-top:5px">
      <CardDetail ref="cardDetail" />
    </el-row>
  </div>
</template>
<script>
import chassis from '../monitor/board/diagram/index'
import StatusAbstract from './components/StatusAbstract'
import StatusHistory from './components/StatusHistory'
import CardDetail from './components/CardDetail'
export default {
  components: {
    chassis,
    StatusAbstract,
    StatusHistory,
    CardDetail
  },
  data() {
    return {
      timer: Function
    }
  },
  mounted() {
    this.startTimer()
    this.$bus.$on('launch', (event) => {
      this.refresh()
    })
  },
  beforeDestroy() {
    this.$bus.$off('launch')
    clearInterval(this.timer)
  },
  methods: {
    startTimer() {
      this.timer = setInterval(() => {
        this.refresh()
      }, 10000)
    },
    refresh() {
      this.$refs.statusAbstract.getAbstract()
      this.$refs.chassis.listBoards()
      this.$refs.statusHistory.getHistory()
      this.$refs.cardDetail.listBoards()
    },
    error() {
      clearInterval(this.timer)
    }
  }
}

</script>
<style>
  .board-monitor {
    padding: 5px;
  }
  .board-monitor h4 {
    font-size: 18px;
    font-weight: 400;
    line-height: 32px;
    margin-top: 0;
    margin-bottom: 0px;
  }
 .board-monitor  .title {
  color: white;
  font-size: 14px;
  background-color: #7eb2db;
  height: 30px;
  line-height: 30px;
  text-align: center;
}
  .board-monitor .el-card__body {
    padding: 10px 20px;
}
</style>
