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
    this.colors = ['#c23531', '#c4ccd3', '#ca8622']
    this.chartSettings = {
      yAxisType: ['percent'],
      yAxisName: ['数值'],
      max: [1]
    }
    this.chartExtend = {
      yAxis: {
        show: false
      },
      grid: {
        left: -40,
        right: 0,
        bottom: 5,
        top: 8
      },
      xAxis: {
        axisLabel: { // 调整x轴的lable
          textStyle: {
            fontSize: 10.5
          }
        }
      }
    }
    return {
      chartData: {
        columns: ['name', '内核温度', '4G信号', 'WIFI信号'],
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
        name: this.board.boardType,
        内核温度: this.board.coreTemp / 100,
        '4G信号': this.board.sigQuality4G / 100,
        WIFI信号: this.board.sigQualityWifi / 100
      }]
    }
  }
}

</script>
<style>
</style>
