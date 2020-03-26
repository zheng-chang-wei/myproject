<template>
  <section id="groundDataManager" class="app-container">
    <el-col :span="5" class="query">
      <ve-pie :data="chartData" :settings="chartSettings" :extend="extend" />
      <el-row style="margin-top:-230px;OVERFLOW:auto;">
        <template v-for="item in projectSpace">
          <el-row :key="item.project" style="padding-bottom: 10px;font-size:12px">
            <el-col :span="11">{{ item.project }}</el-col>
            <el-col :span="13">
              <el-progress :key="item.project" :percentage="item.used" />
            </el-col>
          </el-row>
        </template>
      </el-row>
    </el-col>
    <el-col :span="19">
      <!--查询-->
      <trainSelect @query="query" />
      <!--工具条-->
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true">
          <el-form-item>
            <el-button type="primary" size="mini" @click="downloadTrainData">
              下载标记项</el-button>
            <el-button type="danger" size="mini" @click="deleteTrainData">
              删除标记项</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <!--列表-->

      <el-table
        v-loading="listLoading"
        :data="trains"
        border
        :max-height="tableMaxHeight"
        highlight-current-row
        style="width: 98%;margin-left:15px"
        @selection-change="selsChange"
      >
        <el-table-column type="selection" width="40" align="center" />
        <el-table-column prop="project" label="项目名称" align="center" />
        <el-table-column prop="train" label="车辆编号" align="center" />
        <el-table-column prop="startDay" label="起始时间" align="center" />
        <el-table-column prop="endDay" label="终止时间" align="center" />
      </el-table>
      <!--分页  工具条-->
      <el-row class="toolbar" style="position:absolute;bottom:10px;right:0;">
        <el-pagination
          :current-page.sync="currentPage"
          :page-sizes="[20, 50, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          style="float: right;color:white"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </el-row>
    </el-col>
    <el-dialog title="删除数据" :visible.sync="deleteFormVisible" :close-on-click-modal="false">
      <el-form :inline="true" class="query">
        <el-form-item label="删除的截止日期：">
          <el-date-picker v-model="deleteDate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" :editable="false" :picker-options="pickerOptions" />
        </el-form-item>
      </el-form>
      <div>
        <p v-if="deleteDate!=null" style="color:red">点击“提交”后，系统将删除{{ deleteDate }}之前的数据</p>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :disabled="deleteDate==null" @click.native="deleteGroundData">提交</el-button>
        <el-button type="cancel" @click.native="deleteFormVisible=false">取消</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import app from '@/common/js/app'
import VePie from 'v-charts/lib/pie.common'
import trainSelect from './trainSelect'
export default {
  components: {
    VePie,
    trainSelect
  },
  data() {
    this.chartSettings = {
      radius: 50,
      offsetY: 95
    }
    return {
      chartData: {
        columns: ['city', 'used'],
        rows: []
      },
      extend: {
        legend: {
          textStyle: {
            color: '#ffffff'
          },
          selectedMode: false
        }
      },
      project: null,
      train: null,
      projectSpace: [],
      sels: [], // 列表选中的选项
      trains: [],
      total: 0,
      pageNum: 1,
      pageSize: 20,
      currentPage: 1,
      listLoading: false,
      addLoading: false,
      // 查询时验证
      tableMaxHeight: document.body.offsetHeight - 230,
      deleteFormVisible: false,
      deleteDate: null,
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() >= Date.now()
        }
      }
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeSectionHeight)
    this.changeSectionHeight()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeSectionHeight)
  },
  mounted() {
    this.getDataSpace()
    this.getProjectSpace()
    this.getTrains()
  },
  methods: {
    // 动态更改表格最大高度
    changeSectionHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 230
    },
    // 列表选中的选项
    selsChange(sels) {
      this.sels = sels
    },
    downloadTrainData() { },
    deleteTrainData() {
      if (this.sels.length === 0) {
        this.$message({
          message: '请至少勾选一条数据',
          type: 'error'
        })
        return
      }
      this.deleteDate = null
      this.deleteFormVisible = true
    },
    deleteGroundData() {
      const param = {
        date: this.deleteDate,
        params: this.sels
      }
      this.deleteFormVisible = false
      this.listLoading = true
      app.postData('delete_groundDataManager_info', param).then(data => {
        this.listLoading = false
        this.$message({
          message: data.msg,
          type: 'success'
        })
        this.getTrains()
      })
    },

    getDataSpace() {
      const vm = this
      app.get('get_data_space').then(d => {
        if (d) {
          vm.$set(vm.chartData.rows, 0, {
            'city': '已用空间',
            'used': d.msg
          })
          vm.$set(vm.chartData.rows, 1, {
            'city': '可用空间',
            'used': 100 - d.msg
          })
        }
      })
    },
    getProjectSpace() {
      const vm = this
      app.get('get_project_space').then(d => {
        if (d.msg) {
          vm.projectSpace = d.msg
        }
      })
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
      const param = {
        project: this.project,
        train: this.train,
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }
      app.get('get_groundDataManager_info', param).then(d => {
        if (d) {
          this.trains = d.msg.rows
          this.total = d.msg.total
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
<style>
	#groundDataManager{
		color:white;
	}
	#groundDataManager .query {
		padding: 16px 0px 0px;
	}

	#groundDataManager .query .el-input {
		width: 150px;
	}

	#groundDataManager .toolbar {
		padding: 0px 15px;
		border-bottom: 1px solid transparent;
		border-top-right-radius: 3px;
		border-top-left-radius: 3px;
	}

	#groundDataManager .el-form-item__label {
		text-align: right;
	}
	#groundDataManager .el-progress__text {
		color:white;
	}
</style>
