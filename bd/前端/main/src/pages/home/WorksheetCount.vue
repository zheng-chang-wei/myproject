<template>
  <div class="card-panel" :style="'height:'+sheetCountHeight">
    <div class="card-title">工单指标</div>
    <ve-histogram
      :data="sheetCount"
      :tooltip-visible="false"
      :legend-visible="false"
      :settings="settings"
      :extend="extend"
      height="95%"
      :colors="colors"
    />
  </div>
</template>
<script>
import VeHistogram from 'v-charts/lib/histogram.common'
export default {
  components: {
    VeHistogram
  },
  props: {
    sheetCount: {
      type: Object,
      default: null
    },
    sheetCountHeight: {
      type: String,
      default: null
    }
  },
  data() {
    this.colors = [
      '#FD5DA7', '#009999', '#F7F494', '#6666FF'
    ]
    this.settings = {
      labelMap: {
        newSheet: '今日新增',
        handled: '今日处理',
        unhandled: '今日剩余',
        unhandledBeforeMonth: '30天未处理'
      },
      stack: {
        'count': ['newSheet', 'handled', 'unhandled', 'unhandledBeforeMonth']
      }
    }
    this.extend = {
      xAxis: {
        type: 'category',
        axisLine: {
          lineStyle: {
            color: 'white'
          }
        },
        axisLabel: {
          interval: 0,
          textStyle: {
            color: 'white',
            fontSize: 10
          }
        }
      },
      yAxis: {
        type: 'value',
        axisLine: {
          lineStyle: {
            color: 'white'
          }
        },
        splitLine: {
          show: false
        },
        axisLabel: {
          show: false
        }
      },
      grid: {
        top: '25px',
        bottom: '0px',
        right: '6px',
        left: '0px'
      },
      series: {
        barWidth: 30,
        itemStyle: {
          normal: {
            label: {
              show: true,
              position: 'top'
            }
          }
        }
      }
    }
    return {}
  }
}

</script>
<style lang="scss" scoped>

    .card-panel {
      border-top: 3px solid rgb(253, 93, 167);
    }

    .card-title {
      color: #fff;
      text-align: right;
      width: 100%;
      font-weight: bold;
    }

</style>
