<template>
  <el-col :span="1">
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
    this.colors = ['#c23531', '#749f83',
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
      },
      tooltip: {
        formatter: '{a0}: {c0}℃'
      }
    }
    return {
      chartData: {
        columns: ['name', '内核温度'],
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
        内核温度: this.board.coreTemp
      }]
    }
  }
}

</script>
<style>
</style>
