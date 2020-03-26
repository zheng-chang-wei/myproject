<template>
  <el-row :style="'height:'+tableHeight*0.5+'px'">
    <div class="card-title">今日亚健康预警</div>
    <el-table
      :data="subhealthList"
      :height="(tableHeight-18)+'px'"
      :header-cell-style="{background:'#0f1741',color:'#fff',padding:'3px'}"
      :row-style="{background:'#394761',color:'#fff'}"
      :cell-style="{padding:'2px',borderColor:'#0f1741'}"
      class="fault-table"
      @row-click="rowClick"
    >
      <el-table-column prop="project" label="项目" align="center" width="80px" />
      <el-table-column prop="train" label="车号" align="center" width="80px" />
      <el-table-column prop="faultContent" label="预警描述" align="center" />
      <el-table-column prop="faultTime" label="发生时间" align="center" width="100px" />
    </el-table>
    <el-dialog class="subhealth-suggestion" :visible.sync="detailVisible">
      <template slot="title">
        <span class="dialog-title">处理建议</span>
      </template>
      <suggestion-table :select="select" :item="'亚健康预警'" />
    </el-dialog>
  </el-row>
</template>
<script>
import SuggestionTable from './components/SuggestionTable'
export default {
  components: {
    SuggestionTable
  },
  props: {
    subhealthList: {
      type: Array,
      default: null
    },
    tableHeight: {
      type: Number,
      default: null
    }
  },
  data() {
    return {
      detailVisible: false,
      select: {}
    }
  },
  methods: {
    rowClick(val) {
      this.select = val
      this.detailVisible = true
    }
  }
}

</script>
<style scoped>
.card-title {
    color: #fff;
    text-align: center;
    background-color: #FF9900;
    font-weight: bold;
  }

  .dialog-title {
    color: #FF9900;
    font-size: 18px;
    font-weight: bold;
  }

</style>
<style>
  .subhealth-suggestion .el-dialog .el-dialog__header {
    background-color: #394761 !important;
    text-align: center;
  }

  .subhealth-suggestion .el-dialog .el-dialog__title {
    color: #fff !important;
    font-size: 18px;

  }

  .subhealth-suggestion .el-dialog .el-dialog__body {
    background-color: #394761 !important;
    color: #fff !important;
  }

</style>
