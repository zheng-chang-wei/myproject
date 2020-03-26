<template>
  <!-- <el-card id="line"> -->
  <ve-line ref="lineChart" height="250px" :data="chartData" :settings="settings" :extend="extend" :colors="colors" />
  <!-- </el-card> -->
</template>
<script>
import VeLine from 'v-charts/lib/line.common'
export default {
  name: 'LinePart',
  components: {
    VeLine
  },
  props: {
    lineData: {
      type: Object,
      default: null
    },
    colors: {
      type: Array,
      default: null
    }
  },
  data() {
    return {
      chartData: {
        columns: ['day', 'value'],
        rows: this.lineData.values
      }
    }
  },
  computed: {},
  watch: {
    lineData() {
      this.chartData.rows = this.lineData.values
    }
  },
  created() {
    this.settings = {
      xAxisType: 'time',
      labelMap: {
        day: '天',
        value: this.lineData.param.name
      }
    }
    this.extend = {
      'xAxis.0.axisLabel.rotate': 45,
      grid: {
        top: 30,
        bottom: 15,
        right: 2,
        left: 2,
        height: '85%',
        width: '95%'
      },
      yAxis: {
        name: this.lineData.param.unit,
        axisLabel: {
          formatter: function(value, index) {
            return value.toFixed(5)
          }
        },
        splitLine: {
          show: false
        },
        axisLine: {
          show: 'true',
          lineStyle: {
            color: '#ffffff'
          }
        }
        // axisPointer: {
        //   show: true,
        //   snap: true
        // }
      },
      xAxis: {
        splitLine: {
          show: false
        },
        axisLine: {
          lineStyle: {
            color: '#FFFFFF'
          }
        },
        axisLabel: {
          textStyle: {
            color: '#FFFFFF'
          }
        },
        axisPointer: {
          show: true,
          snap: true
        }
      },
      legend: {
        textStyle: {
          color: '#ffffff'
        }
      },
      series: {
        type: 'line',
        smooth: false
      },
      tooltip: {
        formatter: (params) => {
          return params[0].value[0] + ': ' + params[0].value[1].toFixed(5) + ' ' + this.lineData.param.unit
        }
      }
    }
  },
  mounted() {
    console.log(this.colors)
  },
  methods: {}
}

</script>
<style>
	#line {
		width: 100%;
		height: 250px;
		box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
		margin-top: 10px;
    background-color: #242640;
	}

</style>
