<template>
  <div id="annual-fault">
    <el-row class="fault-panel home-card">
      <div class="card-border card-border-top-left" />
      <div class="card-border card-border-top-right" />
      <div class="card-border card-border-bottom-left" />
      <div class="card-border card-border-bottom-right" />
      <el-col :span="24">
        <div class="card-panel">
          <div class="card-title">状态数据</div>
          <div class="card-state">{{state}}</div>
          <div id="myChart" :style="{width: '100%'}"></div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import VeLine from 'v-charts/lib/line.common'
const color = ['red', 'yellow', '#00FF00', 'green', 'blue ']
export default {
  components: {
    VeLine
  },
  props: {
    lineHeight: {
      type: Number,
      default: null
    }
  },
  data () {
    return {
      option: {
        color: color,
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            animation: true,
            label: {
              backgroundColor: '#0D4286'
            }
          }
        },
        legend: {
          left: 'center',
          // data: ['电机电压', '电机电流', '编码器值'],
          textStyle: {
            color: '#fff',
            fontSize: 10
          }
        },
        grid: {
          top: '27%',
          left: '2%',
          right: '8%',
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
        yAxis: [],
        series: []
      },
      state: ''
    }
  },
  mounted () {
    this.onEvenBus()
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
  },
  destroyed () {
    this.$bus.$off('drawSubhealthLine')
  },
  methods: {
    onEvenBus () {
      this.$bus.$on('drawSubhealthLine', (faultLineData) => {
        this.drawLine(faultLineData)
      })
    },
    changeHeight () {
      this.resizeLine()
    },
    drawLine (faultLineData) {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('myChart'))
      this.option.xAxis.data = faultLineData.times
      let keys = faultLineData.keys
      this.option.legend.data = keys
      this.option.series = []
      this.option.yAxis = []
      for (let i = 0; i < keys.length; i++) {
        this.option.series.push({
          name: keys[i],
          type: 'line',
          data: faultLineData.data[i],
          yAxisIndex: i
        })
        let obj = {
          type: 'value',
          name: keys[i],
          position: 'left',
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
          }
        }
        switch (i) {
          case 1:
            obj.position = 'right'
            break
          case 2:
            obj.position = 'right'
            obj.offset = 55
            break
          case 3:
            obj.position = 'left'
            obj.offset = 45
            break
          case 4:
            obj.position = 'right'
            obj.offset = 100
            break
          default:
            break
        }
        this.option.yAxis.push(obj)
      }
      if (keys.length > 3) {
        this.option.grid.left = '8%'
        this.option.grid.right = '13%'
      } else {
        this.option.grid.left = '2%'
        this.option.grid.right = '8%'
      }
      // 绘制图表
      myChart.setOption(this.option)
      this.state = faultLineData.state
    },
    resizeLine () {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('myChart'))
      myChart.getDom().style.height = this.lineHeight * 0.8 + 'px'
      myChart.resize()
    }
  }
}

</script>
<style scoped>
  #annual-fault {
    background-color: #0f1741;
  }

  .fault-panel {
    height: 100%;
    margin: 2px 2px 0px 0px
  }

  .fault-panel .card-panel {
    height: 100%;
    position: relative;
    overflow: hidden;
  }

  .fault-panel .card-title {
    color: #fff;
    text-align: center;
    background-color: #FF9900;
    font-weight: bold;
  }
  .fault-panel .card-state {
    color: #fff;
    font-weight: bold;
    text-align: right;
  }
</style>
