<template>
  <div class="analog-line">
    <div>
      <el-checkbox v-model="advance" @change="changeAdvance">高级模式</el-checkbox>
    </div>
    <div id="dataChart" :style="{width:'90%',height:'500px'}" />
  </div>
</template>
<script>
const color = ['red', 'yellow', '#00FF00', 'orange', 'lightskyblue']
export default {
  components: {
  },
  props: {
    data: {
      type: Object,
      default: null
    }
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
        toolbox: {
          show: true,
          feature: {
            dataZoom: {
              yAxisIndex: 'none'
            },
            restore: {}
          },
          top: '-5'
        },
        legend: {
          left: 'center',
          data: ['电机电压', '电机电流', '编码器值'],
          textStyle: {
            color: '#ffffff',
            fontSize: 10
          }
        },
        grid: {
          top: '12%',
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
          position: 'right',
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
          offset: 50,
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
          yAxisIndex: 0
        }, {
          name: '电机电流',
          type: 'line',
          data: [],
          yAxisIndex: 1
        }, {
          name: '编码器值',
          type: 'line',
          data: [],
          yAxisIndex: 2
        }]
      },
      dataChart: null
    }
  },
  watch: {
    data: function(newValue, oldValue) {
      this.drawLine(this.data)
    }
  },
  mounted() {
    this.drawLine(this.data)
  },
  destroyed() {
  },
  methods: {
    drawLine(lineData) {
      this.dataChart = this.$echarts.init(document.getElementById('dataChart'))

      this.option.xAxis.data = lineData.times
      const keys = lineData.keys
      const baseValues = lineData.baseValues

      this.option.legend.data = keys
      this.option.series = []
      this.option.yAxis = []
      this.markLine = []
      if (keys != null) {
        for (let i = 0; i < keys.length; i++) {
          const series = {
            name: keys[i],
            type: 'line',
            data: lineData.data[i],
            yAxisIndex: i
          }
          const markLine = {
            silent: true,
            symbol: 'none',
            animation: false,
            data: []
          }
          let maxValue = 0
          for (let j = 0; j < baseValues[i].length; j++) {
            markLine.data.push({
              yAxis: baseValues[i][j]
            })
            maxValue = Math.max(maxValue, baseValues[i][j])
          }
          this.markLine.push(markLine)
          if (this.advance) {
            series['markLine'] = markLine
          }
          this.option.series.push(series)
          const obj = {
            type: 'value',
            name: keys[i],
            position: 'left',
            splitNumber: 10,
            axisLine: {
              lineStyle: {
                color: color[i]
              }
            },
            nameTextStyle: {
              fontSize: 10
            },
            splitLine: {
              show: false
            },
            max: function(value) {
              if (value.max > maxValue) {
                return value.max
              } else {
                return maxValue
              }
            },
            min: 0
          }
          switch (i) {
            case 1:
              obj.position = 'right'
              break
            case 2:
              obj.position = 'right'
              obj.offset = 40
              break
            case 3:
              obj.position = 'left'
              obj.offset = 40
              break
            case 4:
              obj.position = 'right'
              obj.offset = 80
              break
            default:
              break
          }
          this.option.yAxis.push(obj)
        }

        this.option.grid.left = '20'
        this.option.grid.right = '20'
        this.option.grid.containLabel = 'true'
      } else {
        this.option.series = [
          {
            name: '电机电压',
            type: 'line',
            data: [],
            yAxisIndex: 0
          }, {
            name: '电机电流',
            type: 'line',
            data: [],
            yAxisIndex: 1
          }, {
            name: '编码器值',
            type: 'line',
            data: [],
            yAxisIndex: 2
          }
        ]
      }

      this.dataChart.setOption(this.option)
    },
    changeAdvance() {
      if (this.advance) {
        for (let i = 0; i < this.option.series.length; i++) {
          this.option.series[i]['markLine'] = this.markLine[i]
        }
      } else {
        for (let i = 0; i < this.option.series.length; i++) {
          this.option.series[i]['markLine'] = null
        }
      }
      this.dataChart.setOption(this.option)
    }
  }
}
</script>
<style>
analog-line{
  width:100%;
}
</style>

