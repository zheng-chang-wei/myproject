<template>
  <div id="lineChart">
    <div id="chart1" ref="chart1" style="height: 300px;" />
    <div id="chart2" ref="chart2" style="height: 300px;" />
  </div>
</template>

<script>
import echarts from 'echarts'

const Resize = require('element-resize-detector')

export default {
  name: 'LineChart',
  data() {
    return {
      data: []
    }
  },
  mounted() {
  },
  methods: {
    setData: function(data, flag) {
      if (flag === 1) {
        this.drawChart(data, 'chart1')
      }
      if (flag === 2) {
        this.drawChart(data, 'chart2')
      }
    },
    drawChart: function(data, chart) {
      const dimlength = data.length

      const vm = this
      const myChart = echarts.init(document.getElementById(chart))
      const erd = new Resize()
      erd.listenTo(document.getElementById('lineChart'), function(element) {
        try {
          echarts.getInstanceByDom(vm.$refs.chart1).resize()
        } catch (e) {
          console.log(e)
        }
        try {
          echarts.getInstanceByDom(vm.$refs.chart2).resize()
        } catch (e) {
          console.log(e)
        }
      })

      const showData = data[0].values
      for (let i = 1; i < data.length; i++) {
        const tmp = data[i].values
        for (let j = 0; j < tmp.length; j++) {
          showData[j]['value' + i] = tmp[j].value
        }
      }
      const option = {
        tooltip: {
          trigger: 'axis'
        },
        toolbox: {
          feature: {
            dataZoom: { show: true },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        grid: {
          left: 150
        },
        legend: {
          data: [data[0].variable.signalName + '_' + data[0].variable.name],
          bottom: 2
        },
        dataset: {
          source: showData
        },
        xAxis: {
          type: 'category', axisLabel: {
            formatter: function(val) {
              return val.replace('T', '\n')
            }
          },
          axisLine: {
            lineStyle: {
              color: '#999'
            }
          }
        },
        yAxis: [{
          type: 'value',
          axisLine: {
            lineStyle: {
              color: '#999'
            }
          },
          splitLine: {
            show: true
          },
          name: data[0].variable.signalName,
          position: 'left'
        }],
        series: [{
          type: 'line',
          name: data[0].variable.signalName + '_' + data[0].variable.name,
          encode: {
            x: 'timestamp',
            y: 'value'
          },
          yAxisIndex: 0
        }]
      }

      for (let i = 1; i < dimlength; i++) {
        option.legend.data.push(data[i].variable.signalName + '_' + data[i].variable.name)
        const yAxi = {
          type: 'value',
          axisLine: {
            lineStyle: {
              color: '#999'
            }
          },
          splitLine: {
            show: false
          },
          name: data[i].variable.signalName,
          position: i <= 2 ? 'left' : 'right',
          offset: i <= 2 ? 40 * i : 40 * (i - 3)
        }
        option.yAxis.push(yAxi)
        const serie = {
          type: 'line',
          name: data[i].variable.signalName + '_' + data[i].variable.name,
          encode: {
            x: 'timestamp',
            y: 'value' + i
          },
          yAxisIndex: i
        }
        option.series.push(serie)
      }
      myChart.setOption(option)
    },
    clearChart: function(flag) {
      if (flag === 1) {
        try {
          const vm = this
          const myChart1 = echarts.getInstanceByDom(vm.$refs.chart1)
          myChart1.clear()
        } catch (e) {
          console.log(e)
        }
      }
      if (flag === 2) {
        try {
          const vm = this
          const myChart2 = echarts.getInstanceByDom(vm.$refs.chart2)
          myChart2.clear()
        } catch (e) {
          console.log(e)
        }
      }
    }
  }
}
</script>

<style scoped>
</style>
