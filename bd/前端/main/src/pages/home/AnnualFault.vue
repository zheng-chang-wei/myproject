<template>
  <div id="annual-fault" :style="'height:'+chartHeight+'px'">
    <el-tabs v-model="activeName">
      <el-tab-pane label="MTBF" name="1">
        <div class="card-title">{{ title }}数量趋势</div>
        <ve-line :data="annualFault" :height="(chartHeight-80)+'px'" :settings="settings" :extend="extend" />
      </el-tab-pane>
      <el-tab-pane label="MTRR" name="2">
        <div class="card-title">{{ title }}数量趋势</div>
        <ve-line :data="annualFault" :height="(chartHeight-80)+'px'" :settings="settings" :extend="extend" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import VeLine from 'v-charts/lib/line.common'
export default {
  components: {
    VeLine
  },
  props: {
    annualFault: {
      type: Object,
      default: null
    },
    chartHeight: {
      type: Number,
      default: null
    },
    title: {
      type: String,
      default: null
    }
  },
  data() {
    this.colors = [
      '#FF9AB2',
      '#87F9D0'
    ]
    this.settings = {
      labelMap: {
        month: '月份',
        thisYear: '今年',
        lastYear: '去年'
      }
    }
    this.extend = {
      xAxis: {
        type: 'category',
        axisLine: {
          show: 'true',
          lineStyle: {
            color: 'white'
          }
        },
        boundaryGap: false,
        axisLabel: {
          formatter: function(value, idx) {
            return value + '月'
          }
        }
      },
      yAxis: {
        type: 'value',
        axisLine: {
          // show: 'true',
          lineStyle: {
            color: 'white'
          }
        }
      },
      series: {
        smooth: false
        // symbol: 'none',
      },
      legend: {
        textStyle: {
          color: '#ffffff'
        }
      },
      grid: {
        top: '20px',
        bottom: '0px',
        right: '30px'
      }
    }
    return {
      activeName: '2'
    }
  }
}

</script>
<style>
 .card-title {
    color: #fff;
    text-align: center;
    /* background-color: #00CCCC; */
    font-weight: bold;
  }
 #annual-fault .el-tabs__item{
color: #fff;
 }
 #annual-fault .el-tabs__item.is-active{
color: #409EFF;
 }

</style>
