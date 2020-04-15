<template>
  <div class="app-container">
    <!--查询-->
    <el-col :span="24">
      <el-form :inline="true" size="mini">
        <el-form-item prop="name">
          <el-tooltip class="item" effect="dark" content="下载所有未下载数据" placement="top">
            <el-button type="primary" icon="el-icon-download" :loading="downloadLoading" @click="handleDownload">数据下载</el-button>
          </el-tooltip>
        </el-form-item>
        <el-form-item>
          <el-tooltip class="item" effect="dark" content="删除某个时间之前的所有数据" placement="top">
            <el-button type="danger" icon="el-icon-delete" @click="openDeleteDialog">数据删除</el-button>
          </el-tooltip>
        </el-form-item>
        <el-form-item>
          <el-tooltip class="item" effect="dark" content="当需要下载已删除数据时，需要清空已下载文件" placement="top">
            <el-button type="danger" icon="el-icon-delete" :loading="clearLoading" @click="clearDownloadedFiles">已下载文件清空</el-button>
          </el-tooltip>
        </el-form-item>
      </el-form>
    </el-col>
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :border="true"
      :data="tableDatas"
      :max-height="tableMaxHeight"
      highlight-current-row
      @selection-change="selectedChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="name" label="名称" align="center" />
      <el-table-column prop="startTime" label="起始时间" align="center" />
      <el-table-column prop="endTime" label="结束时间" align="center" />
    </el-table>
    <!--删除界面-->
    <el-dialog title="删除数据【提交&删除操作无法撤销，删除前请确认】" :visible.sync="deleteFormVisible" :close-on-click-modal="false">
      <el-form
        ref="deleteForm"
        :model="deleteForm"
        :rules="deleteFormRules"
        label-width="80px"
        style="margin-left:5%;"
      >
        <el-form-item prop="deadline" label="截止日期">
          <el-date-picker
            v-model="deleteForm.deadline"
            placeholder="请选择要删除的截止日期"
            value-format="yyyy-MM-dd"
            :editable="false"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="cancel" @click.native="deleteFormVisible = false">取消
        </el-button>
        <el-button type="primary" :loading="deleteLoading" @click.native="deleteBtnChecked">
          提交&删除
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import app from '@/common/js/app'
export default {
  data() {
    const checkDeadline = (rule, value, callback) => {
      let minTime = this.selecteds[0].startTime
      for (let i = 0; i < this.selecteds.length; i++) {
        if (minTime < this.selecteds[i].startTime) {
          minTime = this.selecteds[i].startTime
        }
      }
      if (value < minTime) {
        callback(new Error('请选择晚于所有起始时间的日期'))
      } else {
        callback()
      }
    }
    return {
      deleteFormVisible: false,
      deleteLoading: false,
      deleteForm: {
        deadline: ''
      },
      deleteFormRules: {
        deadline: [
          {
            required: true,
            message: '必填',
            trigger: 'change'
          },
          {
            validator: checkDeadline,
            trigger: 'change'
          }
        ]
      },
      tableDatas: [],
      selecteds: [], // 选中的数据
      listLoading: false,
      downloadLoading: false,
      clearLoading: false,
      tableMaxHeight: 0,
      total: 0,
      pageNum: 1,
      pageSize: 20,
      currentPage: 1,
      deleteCount: 0, // 要删除数据的个数
      deleteSuccessedCount: 0, // 已经删除数据的个数
      downloadCount: 0, // 要下载文件的个数
      downloadSuccessedCount: 0 // 已下载成功文件的个数
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeTableMaxHeight)
    this.changeTableMaxHeight()
    this.$bus.$on('deleteData', () => {
      this.deleteSuccessedCount++
      if (this.deleteSuccessedCount === this.deleteCount) {
        this.deleteLoading = false
        this.deleteFormVisible = false
        this.$message({
          message: '删除成功',
          type: 'success'
        })
        this.getDatas()
      }
    })
    // 要下载文件的个数
    this.$bus.$on('downloadCount', (data) => {
      this.downloadCount = parseInt(data)
      console.log(this.downloadCount, 'downloadCount')

      if (this.downloadCount === 0) {
        this.downloadLoading = false
        this.$message({
          message: '最新文件都已下载',
          type: 'info'
        })
      }
    })
    // 一个文件下载成功
    this.$bus.$on('downloadSuccess', (data) => {
      this.downloadSuccessedCount++
      console.log(this.downloadSuccessedCount, 'downloadSuccessedCount')
      this.$message({
        message: data + ' 下载完成',
        type: 'success',
        duration: 1000
      })
      if (this.downloadCount === this.downloadSuccessedCount) {
        this.downloadLoading = false
        this.getDatas()
      }
    })
  },
  mounted() {
    this.getDatas()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeTableMaxHeight)
    this.$bus.$off('deleteData')
    this.$bus.$off('downloadCount')
    this.$bus.$off('downloadSuccess')
  },
  methods: {
    // 动态更改表格最大高度
    changeTableMaxHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 190
    },
    getDatas() {
      this.listLoading = true
      this.tableDatas = []
      app.get('getDataOverview').then(response => {
        if (response.code === 0) {
          this.tableDatas = response.msg
          this.listLoading = false
        }
      })
    },
    handleDownload() {
      this.downloadSuccessedCount = 0
      this.downloadLoading = true
      app.get('download').then(response => {
      }).catch(response => {
        console.log(response)
        this.downloadLoading = false
      })
    },
    // 列表选中的选项
    selectedChange(selecteds) {
      this.selecteds = selecteds
    },
    openDeleteDialog(index, row) {
      if (this.selecteds.length === 0) {
        this.$message({
          message: '请至少勾选一条数据删除',
          type: 'error'
        })
        return
      }
      this.deleteFormVisible = true
    },
    deleteBtnChecked() {
      this.$refs.deleteForm.validate(valid => {
        if (valid) {
          this.deleteLoading = true
          this.deleteSuccessedCount = 0
          this.deleteCount = this.selecteds.length
          for (let i = 0; i < this.selecteds.length; i++) {
            if (this.deleteForm.deadline > this.selecteds[i].endTime) {
              const parm = {
                type: this.selecteds[i].type
              }
              this.handleDelete('dropTable', parm)
            } else {
              const parm = {
                type: this.selecteds[i].type,
                deadline: this.deleteForm.deadline
              }
              this.handleDelete('delete', parm)
            }
          }
        }
      })
    },
    handleDelete(url, parm) {
      app.post(url, parm).then(response => {
      }).catch(response => {
        console.log(response)
        this.deleteLoading = false
      })
    },
    clearDownloadedFiles() {
      this.$confirm('此操作将清空已下载文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.clearLoading = true
        app.post('clearDownloadedFiles').then(response => {
          if (response.code === 0) {
            this.$message({
              type: 'success',
              message: '清空成功'
            })
            this.clearLoading = false
          }
        }).catch(response => {
          console.log(response)
          this.clearLoading = false
        })
      }).catch(() => {

      })
    }
  }
}
</script>
