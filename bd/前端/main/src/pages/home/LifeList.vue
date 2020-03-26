<template>
  <el-row :style="'height:'+tableHeight*0.5+'px'">
    <div class="card-title">今日寿命预警</div>
    <el-table
      :data="lifeList"
      :height="(tableHeight-18)+'px'"
      :header-cell-style="{background:'#0f1741',color:'#fff',padding:'3px',borderColor:'#0f1741'}"
      :row-style="{background:'#394761',color:'#fff'}"
      :cell-style="{padding:'2px',borderColor:'#0f1741'}"
      class="fault-table"
    >
      <el-table-column prop="project" label="项目" align="center" width="80px" />
      <el-table-column prop="train" label="车号" align="center" width="80px" />
      <el-table-column prop="faultContent" label="预警描述" align="center" width="160px" />
      <el-table-column prop="faultTime" label="发生时间" align="center" />
    </el-table>
  </el-row>
</template>
<script>
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
  data() {
    return {}
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

</style>
