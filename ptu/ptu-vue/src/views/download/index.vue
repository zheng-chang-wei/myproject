<template>
  <div class="app-container">
    <!--查询-->
    <el-col :span="24">
      <el-form :inline="true" size="mini">
        <!-- <el-form-item prop="name">
          <el-tooltip class="item" effect="dark" content="下载所有未下载数据" placement="top">
            <el-button type="primary" icon="el-icon-download" :loading="downloadLoading" @click="handleDownload">数据下载</el-button>
          </el-tooltip>
        </el-form-item> -->
        <el-form-item>
          <el-tooltip class="item" effect="dark" content="上传本地文件到数据库" placement="top">
            <el-button type="primary" icon="el-icon-upload2" @click="uploadFiles">上传文件</el-button>
          </el-tooltip>
        </el-form-item>
        <el-form-item>
          <el-tooltip class="item" effect="dark" content="删除某个时间之前的所有数据" placement="top">
            <el-button type="danger" icon="el-icon-delete" @click="openDeleteDialog">数据删除</el-button>
          </el-tooltip>
        </el-form-item>
        <el-form-item>
          <el-tooltip class="item" effect="dark" content="当需要上传已删除数据时，必须清空已上传文件" placement="top">
            <el-button type="danger" icon="el-icon-delete" :loading="clearLoading" @click="clearDownloadedFiles">已上传文件清空</el-button>
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
    <deleteDataDialog ref="deleteDataDialog" :selecteds="selecteds" @deleteSuccess="getDatas" />
    <uploadFileDialog ref="uploadFileDialog" @uploadComplete="getDatas" />
  </div>
</template>

<script>
import app from '@/common/js/app'
import deleteDataDialog from '@/components/deleteDataDialog/index'
import uploadFileDialog from '@/components/uploadFileDialog/index'
export default {
  components: {
    deleteDataDialog,
    uploadFileDialog
  },
  data() {
    return {
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
      downloadCount: 0, // 要下载文件的个数
      downloadSuccessedCount: 0 // 已下载成功文件的个数
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeTableMaxHeight)
    this.changeTableMaxHeight()

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
    // 要下载文件的个数
    this.$bus.$on('downloadError', () => {
      this.downloadLoading = false
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
    openDeleteDialog() {
      this.$refs.deleteDataDialog.openDeleteDialog()
    },
    uploadFiles() {
      this.$refs.uploadFileDialog.openFileUploadDialog()
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
