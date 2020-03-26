<template>
  <div class="line-pie">
    <ve-line  :data="lineData" :settings="lineSettings" :extend="lineExtend" :grid="lineGrid"
      :height="lineHeight*0.5+'px'" :legend-visible="false" :colors="colors"></ve-line>
    <ve-pie  :data="pieData" :settings="pieSettings" :height="lineHeight*0.45+'px'" :grid="pieGrid" :extend="pieExtend"
      :legend-visible="false"></ve-pie>
  </div>
</template>
<script>
import VeLine from 'v-charts/lib/line.common'
import VePie from 'v-charts/lib/pie.common'
export default {
  components: {
    VeLine,
    VePie
  },
  props: {
    lineData: Object,
    pieData: Object,
    color: String,
    lineHeight: Number
  },
  data () {
    this.lineSettings = {
      labelMap: {
        month: '月份',
        count: '数量',
        fault: '故障',
        subhealth: '亚健康'
      }
    }
    this.lineExtend = {
      textStyle: {
        fontSize: 12
      },
      xAxis: {
        type: 'category',
        axisLine: {
          lineStyle: {
            color: 'white'
          }
        },
        boundaryGap: false,
        axisLabel: {
          formatter: function (value, idx) {
            return value + '月'
          }
        }
      },
      yAxis: {
        type: 'value',
        axisLabel: {
          lineStyle: {
            color: 'white'
          }
        },
        axisLine: {
          lineStyle: {
            color: 'white'
          }
        },
        splitLine: {
          show: true
        }
      },
      series: {
        label: {
          normal: {
            show: true
          }
        },
        smooth: false
      }
    }
    this.lineGrid = {
      borderColor: 'gray',
      show: true,
      right: 20,
      bottom: 20,
      top: 30
    }

    // 饼图
    this.pieSettings = {
      labelMap: {
        type: '类型',
        percent: '百分比'
      },
      offsetY: '47%'
    }
    this.pieExtend = {
      series: {
        radius: '70%'
      }
    }
    this.pieGrid = {
      left: 0,
      right: 0,
      top: 0,
      bottom: 0,
      width: '100%',
      height: '100%'
    }
    return {}
  },
  created () {
    this.colors = [this.color]
  }
}
</script>
