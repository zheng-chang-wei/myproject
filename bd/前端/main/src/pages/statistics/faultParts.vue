<template>
  <section id="projectFaultCount" class="app-container">
    <!--查询-->
    <query-criteria :contain-project="true" @submit="submit" />
    <el-row>
      <!-- <el-row class="center" style="padding:10px">
        种类故障数统计
      </el-row> -->
      <el-col :span="12">
        <div id="myChart" style="height:400px" />
      </el-col>
      <el-col :span="12">
        <ve-pie :data="pieChartData" :extend="extend" :settings="chartSettings" :height="pieHeight" />
      </el-col>
    </el-row>
  </section>
</template>
<script>
import app from '@/common/js/app'
import VePie from 'v-charts/lib/pie.common'
import queryCriteria from './components/QueryCriteria.vue'
export default {
  components: {
    VePie,
    queryCriteria
  },
  data() {
    this.chartSettings = {
      limitShowNum: 10,
      radius: 100,
      offsetY: '50%'
    }
    return {
      pieHeight: '400px',
      extend: {
        legend: {
          textStyle: {
            color: '#ffffff'
          }
        },
        color: ['#c23531', '#2f4554', '#61a0a8', '#d48265', '#91c7ae', '#749f83', '#ca8622', '#bda29a', '#6e7074', '#546570', '#c4ccd3']
      },
      pieChartData: {
        columns: ['name', 'data'],
        rows: []
      },
      option: {
        title: {
          text: '各部件故障数',
          textStyle: {
            color: '#ffffff'
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        // legend: {
        //   data: ['2011年']
        // },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01],
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
          type: 'category',
          splitLine: {
            show: false
          },
          axisLine: {
            show: 'true',
            lineStyle: {
              color: '#ffffff'
            }
          },
          data: []
        },
        series: [
          {
            type: 'bar',
            data: []
          }
        ]
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
      app.postData('count_parts_fault_by_parms', retrieveForm).then(data => {
        if (data.code === 0) {
          const msg = data.msg
          this.option.yAxis.data = []
          this.option.series[0].data = []
          this.pieChartData.rows = []
          for (let i = msg.length - 1; i >= 0; i--) {
            this.option.yAxis.data.push(msg[i].x)
            this.option.series[0].data.push(msg[i].y)
          }
          for (let i = 0; i < msg.length; i++) {
            this.pieChartData.rows.push({
              'name': msg[i].x,
              'data': parseInt(msg[i].y)
            })
          }
          const myChart = this.$echarts.init(document.getElementById('myChart'))
          myChart.setOption(this.option)
        }
      })
    },
    // 动态更改最大高度
    changeHeight() {
      const myChart = this.$echarts.init(document.getElementById('myChart'))
      myChart.getDom().style.height = document.body.offsetHeight * 0.77 + 'px'
      this.pieHeight = document.body.offsetHeight * 0.77 + 'px'
      myChart.resize()
    }
  }
}

</script>
<style>
	#projectFaultCount .query {
		padding: 16px 0px 0px;
	}

	#projectFaultCount .query .el-input {
		width: 130px;
	}

	#projectFaultCount .el-form-item__label {
		text-align: right;
	}
</style>
