<template>
  <div id="cardDetail">
    <h4 class="title">板卡详情</h4>
    <el-row class="center" style="padding:5px">
      <template v-for="(text,index) in legends">
        <Legend :key="index" :color="colors[index]" :text="text" />
      </template>
    </el-row>
    <el-card shadow="hover">
      <template v-for="(board,index) in boards">
        <SSDHistogram v-if="board.boardType==='SSD'" :key="index" ref="ssd" :board="board" :height="height" />
        <CPUHistogram v-else-if="board.type==='CPU'" :key="index" ref="cpu" :board="board" :height="height" />
        <PHMHistogram v-else-if="board.type!==null&&board.type.indexOf('PHM')!==-1" :key="index" ref="phm" :board="board" :height="height" />
        <WIRELESSHistogram v-else-if="board.type==='无线'" :key="index" ref="wireless" :board="board" :height="height" />
        <MVBHistogram v-else-if="board.type==='MVB'||board.type==='ECN'" :key="index" ref="mvb" :board="board" :height="height" />
      </template>
      <el-col :span="4" :offset="1">
        <template v-for="(board,index) in boards">
          <el-row v-if="board.type==='ECN'" :key="index" class="statusStye">ECN板卡数据状态：{{ board.dataStatus==='BE_DATA'?'有数据':'无数据' }}</el-row>
          <el-row v-if="board.type==='MVB'" :key="index+1" class="statusStye">MVB板卡A通道：{{ board.dataStatusA==='BE_DATA'?'有数据':'无数据' }}</el-row>
          <el-row v-if="board.type==='MVB'" :key="index" class="statusStye">MVB板卡B通道：{{ board.dataStatusB==='BE_DATA'?'有数据':'无数据' }}</el-row>
        </template>

      </el-col>
    </el-card>
  </div>
</template>
<script>
import SSDHistogram from './charts/SSDHistogram'
import CPUHistogram from './charts/CPUHistogram'
import PHMHistogram from './charts/PHMHistogram'
import WIRELESSHistogram from './charts/WIRELESSHistogram'
import MVBHistogram from './charts/MVBHistogram'
import Legend from './legend/index'
import app from '@/common/js/app'
export default {
  components: {
    SSDHistogram,
    CPUHistogram,
    WIRELESSHistogram,
    MVBHistogram,
    PHMHistogram,
    Legend
  },
  data() {
    this.colors = ['#c23531', '#2f4554', 'green',
      '#5793f3', 'violet', '#c4ccd3',
      '#ca8622']
    this.legends = ['内核温度', '存储空间', '内存空间', 'CPU', 'GPU', '4G信号', 'WIFI信号']
    return {
      boards: [],
      height: ''
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
  },
  mounted() {
    this.listBoards()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeHeight)
  },
  methods: {
    changeHeight() {
      this.height = document.body.offsetHeight - 355 + 'px'
    },
    listBoards() {
      app.get('board_list').then(res => {
        if (res.code === 0) {
          this.boards = res.data
          if (this.$refs.ssd !== undefined) {
            this.$refs.ssd[0].refresh()
          }
          if (this.$refs.cpu !== undefined) {
            this.$refs.cpu[0].refresh()
          }
          if (this.$refs.wireless !== undefined) {
            this.$refs.wireless[0].refresh()
          }
          if (this.$refs.mvb !== undefined) {
            this.$refs.mvb.forEach(element => {
              element.refresh()
            })
          }
          if (this.$refs.phm !== undefined) {
            this.$refs.phm.forEach(element => {
              element.refresh()
            })
          }
        }
      })
    }
  }
}

</script>
<style scoped>
.statusStye{
    padding: 5px;
    font-size: 10pt;
}

</style>
