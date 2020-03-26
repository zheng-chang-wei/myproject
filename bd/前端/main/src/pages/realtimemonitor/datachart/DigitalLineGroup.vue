<template>
  <el-row class="dataanalysis-panel home-card common">
    <div class="card-border card-border-top-left" />
    <div class="card-border card-border-top-right" />
    <div class="card-border card-border-bottom-left" />
    <div class="card-border card-border-bottom-right" />
    <div class="card-title">离散信号</div>
    <div class="line-group">
      <div>
        <el-select v-model="selected" style="width:85%" size="mini" multiple placeholder="请选择" clearable :multiple-limit="5" @clear="showDigitalSignal">
          <el-option v-for="(item,index) in options" :key="index" :label="item" :value="item" />
        </el-select>
        <el-button type="primary" size="mini" @click="showDigitalSignal">显示</el-button>
      </div>
      <template v-for="(chartData, index) in datas">
        <ve-line
          :key="index"
          :data="chartData"
          height="70px"
          :extend="extend"
          :tooltip-visible="false"
        />
      </template>
    </div>
  </el-row>
</template>
<script>
import VeLine from 'v-charts/lib/line.common'
const variables = ['门防挤压',
  '门隔离',
  '门紧急解锁',
  '门开好',
  '门关好',
  '门动作中',
  '门零速',
  '门使能',
  '门控制选择信号',
  '再开闭信号反馈',
  '检测到障碍物',
  '关门信号反馈',
  '开门信号反馈',
  '门存在故障',
  '服务按钮信号',
  '集控开门信号',
  '集控关门信号',
  '集控再开闭信号',
  '零速信号',
  '隔离信号',
  '紧急解锁信号',
  '闭锁开关信号',
  '左门板开关信号',
  '右门板开关信号',
  '开门过程',
  '关门过程',
  '防挤压过程中',
  '防挤压停',
  '门地址编码1',
  '门地址编码2',
  '门地址编码3',
  '门地址编码4',
  '开关门指示灯输出',
  '蜂鸣器输出']
export default {
  components: {
    VeLine
  },
  data() {
    this.extend = {
      xAxis: {
        type: 'category',
        boundaryGap: false,
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
        }
      },
      yAxis: {
        type: 'value',
        splitNumber: 1,
        splitLine: {
          show: false
        },
        axisLine: {
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
        show: true,
        top: 25,
        bottom: 5,
        left: 35,
        right: 35
      }
    }
    return {
      options: variables,
      selected: [],
      datas: []
    }
  },
  mounted() {
    const vm = this
    this.$bus.$on('drawdigitalSignalLine', function(data) {
      vm.datas = data
    })
  },
  destroyed() {
    this.$bus.$off('drawdigitalSignalLine')
  },
  methods: {
    showDigitalSignal() {
      console.log(this.selected)
      this.$emit('showDigitalSignal', this.selected)
    }
  }
}
</script>
<style>
.line-group{
  width: 100%
}
</style>

