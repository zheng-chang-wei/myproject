<template>
  <ve-pie  :data="pieData" :settings="pieSettings" :height="lineHeight*0.45+'px'" :grid="pieGrid" :extend="pieExtend" :legend-visible="false"></ve-pie>
</template>
<script>
import VePie from 'v-charts/lib/pie.common'
export default {
  components: {
    VePie
  },
  props: {
    pieData: Object,
    lineHeight: Number
  },
  data () {
    this.colors = ['red', 'orange']
    this.pieSettings = {
      labelMap: {
        type: '类型',
        percent: '百分比'
      },
      offsetY: '47%'
    }
    this.pieExtend = {
      series: {
        radius: '70%'
      }
    }
    this.pieGrid = {
      left: 0,
      right: 0,
      top: 0,
      bottom: 0,
      width: '100%',
      height: '100%'
    }
    return {}
  }
}
</script>
