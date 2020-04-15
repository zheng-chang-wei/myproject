<template>
  <div>
    <template v-for="(a,index) in features">
      <div :id="index" :key="index" v-loading="dataloading" style="height:400px;margin-top:20px" />
    </template>
  </div>
</template>

<script>
import app from '@/common/js/app'
export default {
  props: {
    type: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      dataloading: false,
      option: {
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
        toolbox: {
          show: true,
          feature: {
            dataZoom: {
              yAxisIndex: 'none'
            },
            restore: {},
            saveAsImage: {}
          },
          right: 25,
          // left:50,
          top: 40
        },
        grid: {
          top: '17%',
          left: '2%',
          right: '2%',
          bottom: '1px',
          containLabel: true
        },
        dataZoom: [
          {
            type: 'inside',
            xAxisIndex: [0],
            start: 0,
            end: 100
          }
        ],
        legend: {
          left: 'center',
          top: 25,
          data: []
        },
        xAxis: {
          // type: 'time',
          data: []
        },
        yAxis: [{
          type: 'value',
          splitLine: {
            show: false
          }
        }],
        series: []
      },
      labels: [],
      features: []
    }
  },
  created() {
  },
  mounted() {

  },
  methods: {

    queryLineDatas(value) {
      const objs = value.objs
      this.features = value.features
      const parm = value.parm
      this.labels = []
      let url = ''
      if (this.type === 'CsPort') {
        url = 'getCsPortChartData'
        objs.forEach(element => {
          this.labels.push(element.comId + '-' + element.ip + '-' + element.port)
        })
      } else {
        url = 'getComIdChartData'
        objs.forEach(element => {
          this.labels.push(element.comId + '-' + element.ip)
        })
      }

      this.dataloading = true

      app.get(url, parm).then(response => {
        if (response.code === 0) {
          const allData = response.msg
          this.option.legend.data = this.labels
          // 循环属性
          let j = 0
          allData.forEach(element => {
            this.option.series = []
            this.option.xAxis.data = element.times.map(function(str) {
              return str.replace(' ', '\n')
            })
            for (let index = 0; index < element.values.length; index++) {
              const value = element.values[index]
              this.option.series.push({
                name: this.labels[index],
                type: 'line',
                // sampling: 'average',
                data: value
              })
            }

            this.option['title'] = {
              text: this.features[j]
            }
            const myChart = this.$echarts.init(document.getElementById('' + j))
            myChart.setOption(this.option)
            j++
          })
          this.dataloading = false
        }
      }).catch(response => {
        console.log(response)
        this.dataloading = false
      })
    }
  }
}
</script>

<style>
</style>
