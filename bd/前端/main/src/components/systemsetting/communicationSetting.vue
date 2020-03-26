<template>
  <div>
    <section id="communicationSetting" class="app-container">
      <!--查询-->
      <trainSelect @query="query" />
      <!--工具条-->
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true">
          <el-form-item>
            <el-button type="primary" size="mini" @click="resumeReceive">
              恢复接收标记项</el-button>
            <el-button type="danger" size="mini" @click="stopReceive">
              停止接收标记项</el-button>
            <el-button type="danger" size="mini" @click="logoutTrain">
              注销标记车辆</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <!--列表-->
      <el-table
        v-loading="listLoading"
        :data="trainsTableData"
        :border="true"
        :max-height="tableMaxHeight"
        highlight-current-row
        style="width: 98%;margin-left:15px"
        @selection-change="selsChange"
      >
        <el-table-column type="selection" width="40" />
        <el-table-column prop="project" label="项目名称" align="center" />
        <el-table-column prop="trainNo" label="车辆" align="center" />
        <el-table-column prop="trainStatus" label="车辆状态" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.trainStatus===1" type="success">
              正常接收
            </el-tag>
            <el-tag v-else-if="scope.row.trainStatus===2" type="warning">
              停止接收
            </el-tag>
            <el-tag v-else-if="scope.row.trainStatus===3" type="info">
              车辆注销
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="trainOnline" label="车辆在线" align="center">
          <template slot-scope="scope">
            <el-tag :type="scope.row.trainOnline?'success':'info'">
              {{ scope.row.trainOnline?'车辆在线':'车辆下线' }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>

      <!--分页  工具条-->
      <el-row class="toolbar" style="position:absolute;bottom:10px;right:0">
        <el-pagination
          :current-page.sync="currentPage"
          :page-sizes="[20, 50, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          style="float: right;"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </el-row>
    </section>
  </div>
</template>

<!-- <script type="text/ecmascript-6"> -->
<script>
import app from '@/common/js/app'
import trainSelect from './trainSelect'
export default {
  components: {
    trainSelect
  },
  data() {
    return {
      project: null,
      train: null,
      sels: [], // 列表选中的选项
      trainsTableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 20,
      currentPage: 1,
      listLoading: false,
      addLoading: false,
      selectForm: {},
      // 查询时验证
      retrieveRules: {},
      tableMaxHeight: document.body.offsetHeight - 230
    }
  },
  mounted() {
    this.getTrains()
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeSectionHeight)
    this.changeSectionHeight()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeSectionHeight)
  },
  methods: {
    // 列表选中的选项
    selsChange(sels) {
      this.sels = sels
    },
    resumeReceive() {
      if (this.sels.length === 0) {
        this.$message({
          message: '请至少勾选一个',
          type: 'error'
        })
        return
      }
      this.$confirm('确认恢复接收选中车辆报文？', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true
        app.postJSON('resumeReceived', this.sels).then(d => {
          this.listLoading = false
          this.$message({
            message: '恢复接收成功',
            type: 'success'
          })
          this.getTrains()
        })
      }).catch(() => { })
    },
    stopReceive() {
      if (this.sels.length === 0) {
        this.$message({
          message: '请至少勾选一个',
          type: 'error'
        })
        return
      }
      this.$confirm('确认停止接收选中车辆报文？', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true
        app.postJSON('stopReceived', this.sels).then(d => {
          this.listLoading = false
          this.$message({
            message: '停止接收成功',
            type: 'success'
          })
          this.getTrains()
        })
      }).catch(() => { })
    },
    logoutTrain() {
      if (this.sels.length === 0) {
        this.$message({
          message: '请至少勾选一个',
          type: 'error'
        })
        return
      }
      this.$confirm('确认注销选中车辆？', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true
        app.postJSON('logOffTrains', this.sels).then(d => {
          this.listLoading = false
          this.$message({
            message: '注销成功',
            type: 'success'
          })
          this.getTrains()
        })
      }).catch(() => { })
    },
    // 动态更改表格最大高度
    changeSectionHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 230
    },
    query(retrieveForm) {
      this.project = retrieveForm.projectName
      this.train = retrieveForm.trainNo
      this.handleQuery(1)
    },
    handleQuery(currentPage) {
      this.currentPage = currentPage
      this.handleCurrentChange(this.currentPage)
    },
    getTrains() {
      this.listLoading = true
      const vm = this
      const param = {
        project: this.project,
        trainNo: this.train,
        pageNum: vm.pageNum,
        pageSize: vm.pageSize
      }
      app.get('get_communicationsetting_info', param).then(d => {
        console.log(d)
        if (d) {
          vm.trainsTableData = d.msg.rows
          vm.total = d.msg.total
          this.listLoading = false
        }
      })
    },
    // 分页触发
    handleCurrentChange(val) {
      this.pageNum = val
      this.getTrains()
    },
    // 改变页码
    handleSizeChange(val) {
      this.pageSize = val
      this.getTrains()
    }
  }
}
</script>
<style scoped>
	.query {
		padding: 16px 0px 0px;
	}
	.query .el-input {
		width: 150px;
	}
	.toolbar {
		padding: 0px 15px;
		border-bottom: 1px solid transparent;
		border-top-right-radius: 3px;
		border-top-left-radius: 3px;
	}
	.el-form-item__label {
		text-align: right;
	}
</style>
