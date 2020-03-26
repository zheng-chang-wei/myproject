<template>
  <section id="logManager" class="app-container">
    <!--查询-->
    <el-col :span="24" class="query" style="padding-bottom: 0px;min-width:900px">
      <el-form ref="retrieveForm" :inline="true" :rules="retrieveRules" :model="retrieveForm" size="mini">
        <el-form-item prop="userName">
          <el-input v-model="retrieveForm.userName" placeholder="用户名" :maxlength="20" />
        </el-form-item>
        <el-form-item prop="operation">
          <el-input v-model="retrieveForm.operation" placeholder="用户操作" :maxlength="20" />
        </el-form-item>
        <el-form-item prop="starttime" label="时间范围">
          <el-date-picker
            v-model="retrieveForm.starttime"
            style="width:192px;"
            type="datetime"
            placeholder="选择日期时间"
            :editable="false"
            @change="formatStartTime"
          />
        </el-form-item>
        <el-form-item prop="endtime" label="-">
          <el-date-picker
            v-model="retrieveForm.endtime"
            style="width:192px;"
            type="datetime"
            placeholder="选择日期时间"
            :editable="false"
            @change="formatEndTime"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="search" @click="retrieveLogs">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table
      v-loading="listLoading"
      :data="logs"
      :border="true"
      :max-height="tableMaxHeight"
      highlight-current-row
      style="width: 98%;margin-left:15px"
      @selection-change="selsChange"
    >
      <el-table-column prop="userName" label="用户名" align="center" width="150" />
      <el-table-column prop="operation" label="行为描述" align="center" width="200" />
      <el-table-column prop="params" label="参数" align="center" />
      <el-table-column
        prop="operationTime"
        label="操作时间"
        align="center"
        width="200"
      />
    </el-table>

    <!--分页  工具条-->
    <el-col :span="24" class="toolbar" style="position:absolute;bottom:10px;right:0">
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
    </el-col>
  </section>
</template>

<script >
import app from '@/common/js/app'
import util from '@/common/js/util'
export default {
  components: {
  },
  data() {
    const checkTime = (rule, value, callback) => {
      const sTime = new Date(this.retrieveForm.starttime).getTime()
      const eTime = new Date(value).getTime()
      if (eTime < sTime) {
        callback(new Error('结束时间早于开始时间'))
      } else {
        callback()
      }
    }
    return {
      logs: [],
      total: 0,
      pageNum: 1,
      pageSize: 20,
      currentPage: 1,
      listLoading: false,
      sels: [], // 列表选中的选项
      // 查询功能数据
      retrieveForm: {
        userName: '',
        operation: '',
        ip: '',
        starttime: '', // 查询时开始时间
        endtime: '' // 查询时结束时间
      },
      // 查询时验证
      retrieveRules: {
        endtime: [{
          validator: checkTime,
          trigger: 'change'
        }]
      },
      tableMaxHeight: document.body.offsetHeight - 180
    }
  },
  mounted() {
    this.getLogs()
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
      this.tableMaxHeight = document.body.offsetHeight - 180
    },
    // 格式化时间控件的开始时间
    formatStartTime(val) {
      if (val) {
        this.retrieveForm.starttime = util.formatDate(new Date(val), 'yyyy-MM-dd hh:mm:ss')
      } else {
        this.retrieveForm.starttime = ''
      }
    },
    // 格式化时间控件的结束时间
    formatEndTime(val) {
      if (val) {
        this.retrieveForm.endtime = util.formatDate(new Date(val), 'yyyy-MM-dd hh:mm:ss')
      } else {
        this.retrieveForm.endtime = ''
      }
    },
    // 列表选中的选项
    selsChange(sels) {
      this.sels = sels
    },

    // 查询日志
    retrieveLogs() {
      this.$refs.retrieveForm.validate(valid => {
        if (valid) {
          this.handleQuery(1)
        }
      })
    },
    handleQuery(currentPage) {
      this.currentPage = currentPage
      this.handleCurrentChange(this.currentPage)
    },
    getLogs() {
      this.listLoading = true
      const vm = this
      const param = {
        userName: vm.retrieveForm.userName,
        operation: vm.retrieveForm.operation,
        startTime: vm.retrieveForm.starttime,
        endTime: vm.retrieveForm.endtime,
        pageNum: vm.pageNum,
        pageSize: vm.pageSize
      }
      app.get('get_log', param).then(d => {
        if (d) {
          vm.logs = d.msg.rows
          vm.total = d.msg.total
          this.listLoading = false
        }
      })
    },
    // 分页触发
    handleCurrentChange(val) {
      this.pageNum = val
      this.getLogs()
    },
    // 改变页码
    handleSizeChange(val) {
      this.pageSize = val
      this.getLogs()
    }
  }
}
</script>
<style>
#logManager .query {
  padding: 16px 15px 0px;
}

#logManager .query .el-input {
  width: 140px;
}

#logManager .toolbar {
  padding: 0px 15px;
  border-bottom: 1px solid transparent;
  border-top-right-radius: 3px;
  border-top-left-radius: 3px;
}

#logManager .el-form-item__label {
  text-align: right;
}
</style>
