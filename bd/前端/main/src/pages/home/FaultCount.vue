<!-- 故障、亚健康数量统计页面 -->
<template>
  <div id="fault-count">
    <el-row class="bar-panel home-card">
      <div class="card-panel">
        <div class="card-title">数量统计</div>
        <ve-histogram height="420px" :data="faultCount" :settings="settings" :extend="extend" :colors="colors" :grid="grid" />
      </div>
    </el-row>
  </div>
</template>
<script>
import VeHistogram from 'v-charts/lib/histogram.common'
export default {
  components: {
    VeHistogram
  },
  props: {
    faultCount: {
      type: Object,
      default: null
    }
  },
  data() {
    this.colors = [
      '#87F9D0',
      '#FF9AB2'
    ]
    this.settings = {
      labelMap: {
        thisYear: '今年',
        lastYear: '去年'
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
            color: 'white'
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
        }
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
      },
      legend: {
        textStyle: {
          color: 'white'
        }
      }
    }
    this.grid = {
      top: '50px',
      bottom: '30px'
    }
    return {}
  }
}

</script>
<style scoped>
  #fault-count {
    margin: 2px;
    background-color: #0f1741;
    height: 458px;
  }

  .bar-panel {
    height: 100%;
  }

  .bar-panel .card-panel {
    height: 100%;
    position: relative;
    overflow: hidden;
    border-top: 3px solid rgb(253, 93, 167);
  }

  .bar-panel .card-title {
    color: #fff;
    text-align: right;
    width: 100%;
    font-weight: bold;
  }

</style>
