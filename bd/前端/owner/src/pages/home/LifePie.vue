<template>
  <div id="Component-list">
    <el-row class="Component-panel home-card">
      <div class="card-border card-border-top-left" />
      <div class="card-border card-border-top-right" />
      <div class="card-border card-border-bottom-left" />
      <div class="card-border card-border-bottom-right" />
      <el-col :span="24">
        <div class="card-panel">
          <div class="card-title">寿命数据</div>
          <div id="myPie" :style="{width: '100%'}"></div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import VePie from 'v-charts/lib/pie.common'
export default {
  components: {
    VePie
  },
  props: {
    lineHeight: {
      type: Number,
      default: null
    },
    pieData: {
      type: Object,
      default: null
    }
  },
  data () {
    return {
      option: {
        color: ['#999999', '#FFCC66'],
        tooltip: {
          trigger: 'item',
          formatter: '{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          x: 'left',
          data: ['剩余寿命值', '已用寿命值'],
          textStyle: {
            color: '#fff'
          }
        },
        series: [
          {
            type: 'pie',
            radius: ['32%', '50%'],
            label: {
              normal: {
                formatter: ' {b|{b}}: {c}次 \n 占比:{per|{d}%}',
                // backgroundColor: '#eee',
                // borderColor: '#aaa',
                // borderWidth: 1,
                // borderRadius: 4,
                // shadowBlur: 3,
                // shadowOffsetX: 2,
                // shadowOffsetY: 2,
                // shadowColor: '#999',
                // padding: [0, 7],
                rich: {
                  a: {
                    color: '#999',
                    lineHeight: 22,
                    align: 'center'
                  },
                  b: {
                    // fontSize: 10
                    // lineHeight: 33
                  },
                  per: {
                    padding: [2, 4],
                    borderRadius: 2
                  }
                }
              }
            },
            center: ['50%', '65%'],
            data: [
              {value: 1048, name: '已用寿命值'},
              {value: 251, name: '剩余寿命值'}
            ]
          }
        ]
      }
    }
  },
  mounted () {
    this.onEvenBus()
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
  },
  destroyed () {
    this.$bus.$off('drawPie')
  },
  methods: {
    onEvenBus () {
      this.$bus.$on('drawPie', (lifePieData) => {
        this.drawPie(lifePieData)
      })
    },
    changeHeight () {
      this.resizePie()
    },
    drawPie (lifePieData) {
      // 基于准备好的dom，初始化echarts实例
      let myPie = this.$echarts.init(document.getElementById('myPie'))
      this.option.series[0].data[0].value = lifePieData.usedLife
      this.option.series[0].data[1].value = lifePieData.totleLife - lifePieData.usedLife

      // 绘制图表
      myPie.setOption(this.option)
    },
    resizePie () {
      // 基于准备好的dom，初始化echarts实例
      let myPie = this.$echarts.init(document.getElementById('myPie'))
      myPie.getDom().style.height = this.lineHeight * 0.85 + 'px'
      myPie.resize()
    }
  }

}

</script>
<style scoped>
  #Component-list {
    background-color: #0f1741;
  }

  .Component-panel {
    height: 100%;
    margin: 2px 2px 0px 0px
  }

  .Component-panel .card-panel {
    height: 100%;
    position: relative;
    overflow: hidden;
  }

  .Component-panel .card-title {
    color: #fff;
    text-align: center;
    background-color: rgb(11, 234, 235);
    font-weight: bold;
  }

</style>
