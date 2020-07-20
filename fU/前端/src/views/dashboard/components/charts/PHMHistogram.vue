<template>
  <el-col :span="2">
    <ve-histogram
      :data="chartData"
      :settings="chartSettings"
      :legend-visible="false"
      :extend="chartExtend"
      :height="height"
      :colors="colors"
    />
  </el-col>
</template>
<script>
import VeHistogram from 'v-charts/lib/histogram.common'
export default {
  components: {
    VeHistogram
  },
  props: {
    board: {
      type: Object,
      default: null
    },
    height: {
      type: String,
      default: null
    }
  },
  data() {
    this.colors = ['#c23531', '#2f4554', 'green',
      '#5793f3', 'violet', '#749f83',
      '#ca8622', '#bda29a', '#6e7074',
      '#546570', '#c4ccd3']
    this.chartSettings = {
      yAxisType: ['value'],
      max: [100]
    }
    this.chartExtend = {
      yAxis: {
        show: false
      },
      xAxis: {
        axisLabel: { // 调整x轴的lable
          textStyle: {
            fontSize: 10.5
          }
        }
      },
      grid: {
        left: -40,
        right: 0,
        bottom: 5,
        top: 8
      },
      tooltip: {
        formatter: '{a0}: {c0}℃<br />{a1}: {c1}%' + '<br/>{a2}: {c2}%' + '<br/>{a3}: {c3}%' + '<br/>{a4}: {c4}%'
      }
    }
    return {
      chartData: {
        columns: ['name', '内核温度', '存储空间', '内存空间', 'CPU', 'GPU'],
        rows: []
      }
    }
  },
  created() {
  },
  mounted() {
    this.refresh()
  },
  methods: {
    refresh() {
      this.chartData.rows = [{
        name: this.board.boardType + '[' + this.board.slotID + ']',
        内核温度: this.board.coreTemp,
        存储空间: this.board.romUsed,
        内存空间: this.board.ramUsed,
        CPU: this.board.cpuUsed,
        GPU: this.board.gpuUsed
      }]
    }
  }
}

</script>
<style>
</style>
