<template>
  <div class="life-warning">
    <el-row>
      <el-col :span="24">
        <el-row class="center" style="background-color:#00cccc;color:white;height:30px">寿命预警信息列表</el-row>
        <el-table :data="tableData" border :max-height="maxHeight" highlight-current-row>
          <el-table-column prop="warningTime" label="日期" width="250" align="center" />
          <el-table-column prop="lifeItemName" label="部件名称" align="center" />
          <el-table-column prop="remainderLife" label="剩余寿命" align="center" />
          <el-table-column prop="project" label="项目" align="center" />
          <el-table-column prop="trainNo" label="车辆" align="center" />
          <el-table-column prop="carNo" label="车厢号" align="center" />
          <el-table-column prop="doorAddr" label="门地址" align="center" />
        </el-table>
        <!--分页  工具条-->
        <el-col :span="24" class="toolbar" style="margin-top:20px;">
          <el-button type="primary" style="margin-left:30px">导出</el-button>
          <el-pagination
            :current-page.sync="pageInfo.currentPage"
            :page-sizes="[10, 20, 30, 50, 100]"
            :page-size="pageInfo.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="pageInfo.total"
            style="float: right;"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </el-col>
      </el-col>
    </el-row>
  </div>
</template>
<script>
export default {
  props: {
    tableData: {
      type: Array,
      default() {
        []
      }
    },
    pageInfo: {
      type: Object,
      default() {
        return {
          pageNum: 1,
          pageSize: 20,
          currentPage: 1,
          total: 0
        }
      }
    },
    maxHeight: {
      type: Number,
      default: 400
    }
  },
  data() {
    return {
    }
  },
  methods: {
    handleSizeChange(val) {
      this.$emit('sizeChange', val)
    },
    handleCurrentChange(val) {
      this.$emit('currentChange', val)
    }
  }
}
</script>

<style scoped>
.life-warning .center{
  display: flex;
	-webkit-align-items: center;
	align-items: center;
	-webkit-justify-content: center;
	justify-content: center;
	border-radius: 7px 7px 0 0;
}
</style>

