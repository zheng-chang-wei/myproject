<template>
  <div id="lineChart" style="width: 100%;padding:10px 2px 0px 2px;">
    <div ref="chart" style="height: 500px;"></div>
  </div>
</template>

<script>
  import echarts from 'echarts'
  let elementResizeDetector = require("element-resize-detector")

  export default {
    name: "lineChart",
    data() {
      return {
        data: [],
      }
    },
    mounted() {
      let _this = this
      this.draw(this.data)
      let erd = new elementResizeDetector()
      erd.listenTo(document.getElementById("lineChart"), function (element) {
          echarts.getInstanceByDom(_this.$refs.chart).resize()
      })
    },
    methods: {
      setData: function (data) {
        this.draw(data)
      },
      draw: function (data) {
        let _this = this
        let myChart = echarts.init(this.$refs.chart);
        let option = {
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            y: 'bottom',
            x: 'center',
            bottom: '0px',
            data: ['邮件营销', '联盟广告', '视频广告', '直接访问', '搜索引擎']
          },
          grid: {
            left: '5%',
            right: '5%',
            bottom: '60px',
            containLabel: true
          },
          toolbox: {
            feature: {
              saveAsImage: {}
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
          },
          yAxis: [{
            type: 'value'
          }, {
            type: 'value'
          }],
          series: [
            {
              name: '邮件营销',
              type: 'line',
              stack: '总量',
              data: [120, 132, 101, 134, 90, 230, 210]
            },
            {
              name: '联盟广告',
              type: 'line',
              stack: '总量',
              data: [220, 182, 191, 234, 290, 330, 310]
            },
            {
              name: '视频广告',
              type: 'line',
              stack: '总量',
              data: [150, 232, 201, 154, 190, 330, 410]
            },
            {
              name: '直接访问',
              type: 'line',
              stack: '总量',
              data: [320, 332, 301, 334, 390, 330, 320]
            },
            {
              name: '搜索引擎',
              type: 'line',
              stack: '总量',
              data: [820, 932, 901, 934, 1290, 1330, 1320]
            }
          ]
        };
        myChart.setOption(option);
      }
    }
  }
</script>

<style scoped>

</style>
