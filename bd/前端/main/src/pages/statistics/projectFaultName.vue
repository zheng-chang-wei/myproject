<template>
  <section id="projectFaultCount" class="app-container">
    <!--查询-->
    <query-criteria @submit="submit" />
    <el-row class="center" style="padding:10px">
      故障数统计
    </el-row>
    <ve-histogram :data="faultCountChartData" :height="histogramHeight" :extend="extend" />
    <el-row class="center" style="padding:10px">
      故障率统计
    </el-row>
    <ve-histogram :data="faultRateChartData" :height="histogramHeight" :extend="extend" :settings="chartSettings1" />
  </section>
</template>
<script>
import app from '@/common/js/app'
import VeHistogram from 'v-charts/lib/histogram.common'
import queryCriteria from './components/QueryCriteria.vue'
export default {
  components: {
    VeHistogram,
    queryCriteria
  },
  data() {
    this.chartSettings1 = {
      yAxisType: ['percent']
    }
    return {
      span: 6,
      histogramHeight: '400px',
      chartDatas: [],
      faultCountChartData: {
        columns: [],
        rows: []
      },
      faultRateChartData: {
        columns: [],
        rows: []
      },
      extend: {
        legend: {
          textStyle: {
            color: '#ffffff'
          }
        },
        xAxis: {
          axisLine: {
            show: 'true',
            lineStyle: {
              color: '#FFFFFF'
            }
          },
          axisLabel: { // 调整x轴的lable
            textStyle: {
              color: '#FFFFFF'
            }
          }
        },
        yAxis: {
          splitLine: {
            show: false
          },
          axisLine: {
            show: 'true',
            lineStyle: {
              color: '#ffffff'
            }
          }
        },
        grid: {
          left: 30,
          right: 30,
          bottom: 10
        },
        color: ['#c23531', '#2f4554', '#61a0a8', '#d48265', '#91c7ae', '#749f83', '#ca8622', '#bda29a', '#6e7074', '#546570', '#c4ccd3']
      }
    }
  },
  mounted() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeHeight)
  },
  methods: {
    submit(retrieveForm) {
      app.postData('count_fault_name_by_parms', retrieveForm).then(data => {
        if (data.code === 0) {
          this.faultCountChartData.rows = []
          this.faultRateChartData.rows = []
          const msg = data.msg
          const columns = ['name']
          for (let i = 0; i < msg.length; i++) {
            const countRow = {}
            const rateRow = {}
            countRow['name'] = msg[i].projectName
            rateRow['name'] = msg[i].projectName
            const commonStatisticsResultList = msg[i].commonStatisticsResultList
            for (let j = 0; j < commonStatisticsResultList.length; j++) {
              countRow[commonStatisticsResultList[j].x] = commonStatisticsResultList[j].y
              rateRow[commonStatisticsResultList[j].x] = commonStatisticsResultList[j].y / msg[i].doorCount
              if (columns.indexOf(commonStatisticsResultList[j].x) === -1) {
                columns.push(commonStatisticsResultList[j].x)
              }
            }
            this.faultCountChartData.rows.push(countRow)
            this.faultRateChartData.rows.push(rateRow)
          }
          this.faultCountChartData.columns = columns
          this.faultRateChartData.columns = columns
        }
      })
    },
    // 动态更改最大高度
    changeHeight() {
      this.histogramHeight = document.body.offsetHeight * 0.322 + 'px'
    }
  }
}

</script>
<style>

</style>
