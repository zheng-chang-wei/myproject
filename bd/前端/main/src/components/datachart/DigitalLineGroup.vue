<template>
  <div class="line-group">
    <div class="pull-right">
      <el-select v-model="selected" style="width:80%" size="mini" multiple placeholder="请选择" clearable :multiple-limit="5" @clear="query">
        <el-option v-for="item in options" :key="item" :label="item" :value="item" />
      </el-select>
      <el-button type="primary" size="mini" @click="query">显示</el-button>
    </div>
    <div class="line">
      <ve-line
        :data="datas[0]"
        height="100px"
        :grid="grid"
        :extend="extend"
        :tooltip-visible="false"
      />
    </div>
    <div class="line">
      <ve-line
        :data="datas[1]"
        height="100px"
        :grid="grid"
        :extend="extend"
        :tooltip-visible="false"
      />
    </div>
    <div class="line">
      <ve-line
        :data="datas[2]"
        height="100px"
        :grid="grid"
        :extend="extend"
        :tooltip-visible="false"
      />
    </div>
    <div class="line">
      <ve-line
        :data="datas[3]"
        height="100px"
        :grid="grid"
        :extend="extend"
        :tooltip-visible="false"
      />
    </div>
    <div class="line">
      <ve-line
        :data="datas[4]"
        height="100px"
        :grid="grid"
        :extend="extend"
        :tooltip-visible="false"
      />
    </div>
  </div>
</template>
<script>
import VeLine from 'v-charts/lib/line.common'
const variables = [
  '门防挤压',
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
  '开关门指示灯输出',
  '蜂鸣器输出']
export default {
  components: {
    VeLine
  },
  props: {
    datas: {
      type: Array,
      default() {
        []
      }
    }
  },
  data() {
    this.grid = {
      show: true,
      top: 25,
      bottom: 5,
      left: 10,
      right: 10
    }
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
      }
    }
    return {
      options: variables,
      selected: [],
      lineDatas: []
    }
  },
  methods: {
    query() {
      const param = {
        'variables': this.selected
      }
      this.$emit('query', param)
    }
  }
}
</script>
<style>
.line-group{
  width: 100%
}
.pull-right {
    /* float: right; */
    width:100%
  }
</style>

