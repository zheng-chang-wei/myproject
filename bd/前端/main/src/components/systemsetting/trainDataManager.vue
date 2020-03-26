<template>
  <section id="trainDataManager" class="app-container">
    <!--查询-->
    <trainSelect @query="query" />
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true">
        <el-form-item>
          <el-button type="danger" size="mini" @click="deleteTrainData">
            删除</el-button>
          <span>（不在线车辆不可执行删除）</span>
        </el-form-item>
      </el-form>
    </el-col>
    <!--列表-->

    <el-table
      v-loading="listLoading"
      :data="trains"
      :border="true"
      :max-height="tableMaxHeight"
      highlight-current-row
      style="width: 98%;margin-left:15px"
      @selection-change="selsChange"
    >
      <el-table-column type="selection" width="40" align="center" :selectable="trainSelectable" />
      <el-table-column prop="project" label="项目名称" align="center" />
      <el-table-column prop="train" label="车辆编号" align="center" />
      <el-table-column prop="startDate" label="起始时间" align="center" />
      <el-table-column prop="endDate" label="最新时间" align="center" />
      <el-table-column label="存储状态" prop="storageRatio" align="center">
        <template slot-scope="scope">
          <el-progress :percentage="scope.row.storageRatio" />
        </template>
      </el-table-column>
      <el-table-column label="故障数据占比" prop="faultRatio" align="center">
        <template slot-scope="scope">
          <el-progress :percentage="scope.row.faultRatio" />
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

    <!--删除界面-->
    <el-dialog title="删除数据" :visible.sync="deleteFormVisible" :close-on-click-modal="false">
      <el-form :inline="true" class="query">
        <el-form-item prop="deleteForm.deadline" label="截止日期">
          <el-date-picker
            v-model="deleteForm.deadline"
            type="date"
            placeholder="请选择要删除的截止日期"
            value-format="yyyy-MM-dd"
            :editable="false"
            :picker-options="pickerOptions"
          />
        </el-form-item>
      </el-form>
      <div>
        <p v-if="deleteForm.deadline!=null" style="color:red">点击“提交”后，系统将删除{{ deleteForm.deadline }}之前的数据</p>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :disabled="deleteForm.deadline==null" :loading="deleteLoading" style="width:90px;" @click.native="deleteSubmit">
          提交
        </el-button>
        <el-button type="cancel" style="width:90px;" @click.native="deleteFormVisible = false">取消
        </el-button>
      </div>
    </el-dialog>
  </section>
</template>

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
      trains: [],
      total: 0,
      pageNum: 1,
      pageSize: 20,
      currentPage: 1,
      listLoading: false,
      deleteLoading: false,
      deleteForm: {
        dataType: 'Common',
        deadline: null
      },
      addLoading: false,
      deleteFormVisible: false,
      tableMaxHeight: document.body.offsetHeight - 230,
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() >= Date.now()
        }
      }
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
    // 动态更改表格最大高度
    changeSectionHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 230
    },
    // 列表选中的选项
    selsChange(sels) {
      this.sels = sels
    },
    deleteTrainData() {
      if (this.sels.length === 0) {
        this.$message({
          message: '请至少勾选一条数据删除',
          type: 'error'
        })
        return
      }
      this.deleteFormVisible = true
    },
    deleteSubmit() {
      const vm = this
      if (this.deleteForm.deadline === '') {
        this.$message({
          message: '截止日期不能为空',
          type: 'error'
        })
        return
      }
      this.$confirm('确认删除选中记录？', '提示', {
        type: 'warning'
      }).then(() => {
        const param = {
          date: this.deleteForm.deadline,
          trainParams: []
        }
        for (var i = 0; i < vm.sels.length; i++) {
          param.trainParams.push({
            project: vm.sels[i].project,
            trainNo: vm.sels[i].train
          })
        }
        this.deleteFormVisible = false
        app.postData('delete_trainDataManager_info', param).then(data => {
          vm.deleteLoading = false
          vm.deleteFormVisible = false
          vm.$message({
            message: data.msg,
            type: 'success'
          })
          vm.getTrains()
        })
      }).catch(() => { })
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
      app.get('get_trainDataManager_info', param).then(d => {
        if (d) {
          vm.trains = d.msg.rows
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
    },
    trainSelectable(row, index) {
      return row.online === true
    }
  }
}
</script>
<style>
	#trainDataManager {
		color: white;
	}
	#trainDataManager .el-progress__text{
		color: white;
	}
	#trainDataManager .query {
		padding: 16px 0px 0px;
	}

	#trainDataManager .query .el-input {
		width: 150px;
	}

	#trainDataManager .toolbar {
		padding: 0px 15px;
		border-bottom: 1px solid transparent;
		border-top-right-radius: 3px;
		border-top-left-radius: 3px;
	}

	#trainDataManager .el-form-item__label {
		text-align: right;
	}
</style>
