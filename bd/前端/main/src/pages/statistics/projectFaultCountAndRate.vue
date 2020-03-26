<template>
  <section id="projectFaultCount" class="app-container">
    <!--查询-->
    <query-criteria @submit="submit" />
    <el-row>
      <el-row class="center" style="padding:10px">
        项目故障数统计
      </el-row>
      <ve-histogram :data="faultCountChartData" :height="histogramHeight" :extend="extend" :settings="chartSettings1" :legend-visible="false" />
      <el-row class="center" style="padding:10px">
        项目故障率统计
      </el-row>
      <ve-histogram :data="faultRateChartData" :height="histogramHeight" :extend="extend" :settings="chartSettings2" :legend-visible="false" />
    </el-row>
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
      yAxisName: ['故障数']
    }
    this.chartSettings2 = {
      yAxisType: ['percent'],
      yAxisName: ['故障率']
    }
    return {
      histogramHeight: '400px',
      faultCountChartData: {
        columns: ['名称', '故障数'],
        rows: []
      },
      faultRateChartData: {
        columns: ['名称', '故障率'],
        rows: []
      },
      extend: {
        legend: {
          textStyle: {
            color: '#ffffff',
            fontSize: 10
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
              color: '#FFFFFF',
              fontSize: 10
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
          },
          axisLabel: { // 调整x轴的lable
            textStyle: {
              color: '#FFFFFF',
              fontSize: 10
            }
          }
        },
        color: ['#c23531', '#2f4554', '#61a0a8', '#d48265', '#91c7ae', '#749f83', '#ca8622', '#bda29a', '#6e7074', '#546570', '#c4ccd3'],
        grid: {
          left: 5,
          right: 5,
          bottom: 10,
          top: 30
        }
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
    // 开始查询
    submit(retrieveForm) {
      app.postData('count_project_fault_by_parms', retrieveForm).then(data => {
        if (data.code === 0) {
          this.faultCountChartData.rows = []
          this.faultRateChartData.rows = []
          const msg = data.msg
          for (let i = 0; i < msg[0].length; i++) {
            this.faultCountChartData.rows.push({
              '名称': msg[0][i].x,
              '故障数': msg[0][i].y
            })
          }
          for (let i = 0; i < msg[1].length; i++) {
            this.faultRateChartData.rows.push({
              '名称': msg[1][i].x,
              '故障率': msg[1][i].y
            })
          }
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
#projectFaultCount {
  color: #fff;
}
</style>
