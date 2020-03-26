<template>
  <div id="life-list">
    <el-row class="life-panel home-card">
      <div class="card-border card-border-top-left" />
      <div class="card-border card-border-top-right" />
      <div class="card-border card-border-bottom-left" />
      <div class="card-border card-border-bottom-right" />
      <div class="card-panel" >
        <div class="card-title">寿命评估</div>
        <el-table ref="table" :data="lifeList" :header-cell-style="{background:'#0f1741',color:'#fff',padding:'1px',fontSize:'12px'}"
          :row-style="{background:'#394761',color:'#fff',fontSize:'10px'}" :cell-style="{padding:'1px',borderColor:'#0f1741',fontSize:'10px'}"
          width="100%" :height="(tableHeight-30)+ 'px'" highlight-current-row @row-click="rowClick">
          <el-table-column prop="itemName" label="评估部件" align="center" />
          <el-table-column prop="warningTime" label="评估时间" align="center" :formatter="formatTime"/>
          <el-table-column prop="usedLife" label="寿命预测" align="center" />
        </el-table>
      </div>
    </el-row>
  </div>
</template>
<script>
import util from '@/common/js/util'
export default {
  props: {
    lifeList: {
      type: Array,
      default: null
    },
    tableHeight: {
      type: Number,
      default: null
    }
  },
  data () {
    return {}
  },
  mounted () {
    this.$bus.$on('setLifeRowSelection', () => {
      this.$refs.table.setCurrentRow(this.lifeList[0])
      this.rowClick(this.lifeList[0])
    })
  },
  destroyed () {
    this.$bus.$off('setLifeRowSelection')
  },
  methods: {
    rowClick (row) {
      this.$bus.$emit('drawPie', row)
    },
    // 格式化时间
    formatTime (row, column) {
      return (row.warningTime = row.warningTime
        ? util.formatDate(new Date(row.warningTime), 'yyyy-MM-dd hh:mm:ss')
        : '')
    }
  }
}

</script>
<style scoped>
  #life-list {
    background-color: #0f1741;
  }

  .life-panel {
    height: 100%;
    margin: 2px 2px 0px 0px
  }

  .life-panel .card-panel {
    height: 100%;
    position: relative;
    overflow: hidden;
  }

  .life-panel .card-title {
    color: #fff;
    text-align: center;
    background-color: rgb(11, 234, 235);
    font-weight: bold;
  }

</style>
