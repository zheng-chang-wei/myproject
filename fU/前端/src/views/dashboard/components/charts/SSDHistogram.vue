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
    this.colors = ['#2f4554']
    this.chartExtend = {
      grid: {
        left: 0,
        right: 5,
        bottom: 5,
        top: 8
      },
      yAxis: {
        splitLine: {
          show: false
        }
      },
      xAxis: {
        axisLabel: { // 调整x轴的lable
          textStyle: {
            fontSize: 10.5
          }
        }
      },
      tooltip: {
        formatter: '{a0}: {c0}%'
      }
    }
    this.chartSettings = {
      yAxisType: ['value'],
      max: [100]
    }
    return {
      chartData: {
        columns: ['name', '存储空间'],
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
        存储空间: this.board.ssdUsed
      }]
    }
  }
}

</script>
<style>
</style>
