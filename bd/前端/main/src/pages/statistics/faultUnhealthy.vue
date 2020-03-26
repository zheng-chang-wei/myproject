<template>
  <section id="faultUnhealthy" class="app-container">
    <!--查询-->
    <el-row class="query">
      <el-form :inline="true" size="mini">
        <el-form-item prop="projectName">
          <el-select v-model="projectName" placeholder="项目名称">
            <el-option
              v-for="item in projectOptions"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="statistics" />
        </el-form-item>
      </el-form>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-row>
          <div class="title">故障数</div>
          <ve-line :data="faultCountChartData" :extend="extend" :height="lineHeight" />
        </el-row>
        <el-row>
          <div class="title">故障率</div>
          <ve-line :data="faultRateChartData" :extend="extend" :settings="chartSettings" :height="lineHeight" />
        </el-row>
      </el-col>
      <el-col :span="12">
        <el-row>
          <div class="title">亚健康数</div>
          <ve-line :data="subhealthCountChartData" :extend="extend" :height="lineHeight" />
        </el-row>
        <el-row>
          <div class="title">亚健康率</div>
          <ve-line :data="subhealthRateChartData" :extend="extend" :settings="chartSettings" :height="lineHeight" />
        </el-row>
      </el-col>
    </el-row>
  </section>
</template>
<script>
import app from '@/common/js/app'
import VeLine from 'v-charts/lib/line.common'
export default {
  components: {
    VeLine
  },
  data() {
    this.chartSettings = {
      yAxisType: ['percent']
    }
    return {
      projectName: '',
      projectOptions: [],
      doorCount: null,
      lineHeight: '400px',
      faultCountChartData: {
        columns: [],
        rows: []
      },
      subhealthCountChartData: {
        columns: [],
        rows: []
      },
      faultRateChartData: {
        columns: [],
        rows: []
      },
      subhealthRateChartData: {
        columns: [],
        rows: []
      },
      extend: {
        xAxis: {
          type: 'category',
          boundaryGap: false,
          axisLine: {
            show: 'true',
            lineStyle: {
              color: '#FFFFFF'
            }
          },
          axisLabel: { // 调整x轴的lable
            textStyle: {
              color: '#FFFFFF'
              // fontSize: 10
            }
          }
        },
        yAxis: {
          type: 'value',
          splitNumber: 1,
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
        series: {
          type: 'line',
          smooth: false,
          symbol: 'none',
          animation: false
        },
        legend: {
          textStyle: {
            color: '#ffffff'
          },
          selectedMode: false
        },
        grid: {
          left: 30,
          right: 30,
          bottom: 10
        },
        color: ['red', 'yellow']
      }
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeLineHeight)
    this.changeLineHeight()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeHeight)
  },
  mounted() {
    this.getProject()
  },
  methods: {
    // 获取项目名称
    getProject() {
      app.get('get_project').then(data => {
        if (data.msg) {
          data.msg.forEach(element => {
            this.projectOptions.push(element.name)
          })
          if (this.projectOptions.length > 0) {
            this.projectName = this.projectOptions[0]
            this.getTrainDoorCount()
          }
        }
      })
    },
    getTrainDoorCount() {
      const parms = {
        projectName: this.projectName
      }
      app.get('get_train_door_count', parms).then(data => {
        if (data.code === 0) {
          this.doorCount = data.msg
          this.statistics()
        }
      })
    },
    statistics() {
      const parms = {
        projectName: this.projectName
      }
      app.get('count_fault_by_projectName', parms).then(data => {
        if (data.code === 0) {
          const tYear = new Date().getFullYear()
          this.faultCountChartData.columns = ['日期', (tYear - 1) + '年', tYear + '年']
          this.faultCountChartData.rows = []
          this.faultRateChartData.columns = ['日期', (tYear - 1) + '年', tYear + '年']
          this.faultRateChartData.rows = []
          for (let i = 0; i < 12; i++) {
            const objCount = {}
            objCount['日期'] = (i + 1) + '月'
            objCount[(tYear - 1) + '年'] = this.getMonthData(i, data.msg[1])
            objCount[tYear + '年'] = this.getMonthData(i, data.msg[0])
            this.faultCountChartData.rows.push(objCount)
            const objRate = {}
            objRate['日期'] = (i + 1) + '月'
            objRate[(tYear - 1) + '年'] = parseInt(this.getMonthData(i, data.msg[1])) / this.doorCount
            objRate[tYear + '年'] = parseInt(this.getMonthData(i, data.msg[0])) / this.doorCount
            this.faultRateChartData.rows.push(objRate)
          }
        }
      })
      app.get('count_subhealth_by_projectName', parms).then(data => {
        if (data.code === 0) {
          const tYear = new Date().getFullYear()
          this.subhealthCountChartData.columns = ['日期', (tYear - 1) + '年', tYear + '年']
          this.subhealthCountChartData.rows = []
          this.subhealthRateChartData.columns = ['日期', (tYear - 1) + '年', tYear + '年']
          this.subhealthRateChartData.rows = []
          for (let i = 0; i < 12; i++) {
            const objCount = {}
            objCount['日期'] = (i + 1) + '月'
            objCount[(tYear - 1) + '年'] = this.getMonthData(i, data.msg[1])
            objCount[tYear + '年'] = this.getMonthData(i, data.msg[0])
            this.subhealthCountChartData.rows.push(objCount)
            const objRate = {}
            objRate['日期'] = (i + 1) + '月'
            objRate[(tYear - 1) + '年'] = parseInt(this.getMonthData(i, data.msg[1])) / this.doorCount
            objRate[tYear + '年'] = parseInt(this.getMonthData(i, data.msg[0])) / this.doorCount
            this.subhealthRateChartData.rows.push(objRate)
          }
        }
      })
    },
    getMonthData(month, data) {
      for (let i = 0; i < data.length; i++) {
        const element = data[i]
        if (element.x === month + 1 + '') {
          return element.y
        }
      }
      return 0
    },
    // 动态更改最大高度
    changeLineHeight() {
      this.lineHeight = document.body.offsetHeight * 0.33 + 'px'
    }
  }
}

</script>
<style>

	#faultUnhealthy .query {
		padding: 10px 0px 0px;
	}

.title {
    color: #fff;
    text-align: center;
    font-weight: bold;
    margin-bottom: 10px;
  }
</style>
