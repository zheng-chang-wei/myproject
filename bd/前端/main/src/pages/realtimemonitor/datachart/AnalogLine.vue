<template>
  <el-row class="dataanalysis-panel home-card common">
    <div class="card-border card-border-top-left" />
    <div class="card-border card-border-top-right" />
    <div class="card-border card-border-bottom-left" />
    <div class="card-border card-border-bottom-right" />
    <div class="card-title">模拟信号</div>
    <div class="analog-line">
      <div id="dataChart" :style="{height:'380px'}" />
    </div>
  </el-row>
</template>
<script>
const color = ['red', 'yellow', '#00FF00', 'orange', 'lightskyblue']
export default {
  components: {
  },
  data() {
    return {
      markLine: [],
      advance: false,
      option: {
        color: color,
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            animation: true,
            label: {
              backgroundColor: '#0D4386'
            }
          }
        },
        legend: {
          left: 'center',
          data: ['电机电压', '电机电流', '编码器值', '开门时间', '关门时间'],
          textStyle: {
            color: '#ffffff',
            fontSize: 10
          }
        },
        grid: {
          top: '13%',
          left: '5%',
          right: '2%',
          bottom: '1px',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          axisLine: {
            lineStyle: {
              color: '#FFFFFF'
            }
          },
          axisLabel: { // 调整x轴的lable
            textStyle: {
              color: '#FFFFFF',
              fontSize: 10
            },
            formatter: function(value, index) {
              return value.split(' ')[1]
            }
          },
          data: []
        },
        yAxis: [{
          type: 'value',
          name: '电机电压',
          position: 'left',
          axisLine: {
            lineStyle: {
              color: color[0]
            }
          },
          splitNumber: 10,
          axisLabel: {
            formatter: '{value}'
          },
          splitLine: {
            show: false
          }
        }, {
          type: 'value',
          name: '电机电流',
          position: 'left',
          offset: 50,
          axisLabel: {
            formatter: '{value}'
          },
          axisLine: {
            lineStyle: {
              color: color[1]
            }
          },
          splitLine: {
            show: false
          }
        }, {
          type: 'value',
          name: '编码器值',
          position: 'right',
          axisLine: {
            lineStyle: {
              color: color[2]
            }
          },
          splitLine: {
            show: false
          }
        }],
        series: [{
          name: '电机电压',
          type: 'line',
          data: [],
          yAxisIndex: 0,
          showSymbol: false
        }, {
          name: '电机电流',
          type: 'line',
          data: [],
          yAxisIndex: 1,
          showSymbol: false
        }, {
          name: '编码器值',
          type: 'line',
          data: [],
          yAxisIndex: 2,
          showSymbol: false
        }]
      }
    }
  },
  mounted() {
    const vm = this
    this.$bus.$on('drawAnalogSignalLine', function(data) {
      vm.drawLine(data)
    })
  },
  destroyed() {
    this.$bus.$off('drawAnalogSignalLine')
  },
  methods: {
    drawLine(lineData) {
      // console.log(lineData)
      const dataChart = this.$echarts.init(document.getElementById('dataChart'))
      this.option.xAxis.data = lineData.times
      const series = this.option.series
      const values = lineData.values
      for (let i = 0; i < series.length; i++) {
        series[i].data = values[i]
      }
      dataChart.setOption(this.option)
    }
  }
}
</script>
<style>
analog-line{
  width:100%;
}
</style>

