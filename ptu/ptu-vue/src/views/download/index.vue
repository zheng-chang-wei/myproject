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
            <el-button type="danger" icon="el-icon-delete" @click="deleteFormVisible=true">数据删除</el-button>
          </el-tooltip>
          <!-- <el-button type="primary" icon="el-icon-search" @click="retrieveDatas(1)">查询</el-button> -->
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
    >
      <el-table-column prop="comId" label="comId" align="center" width="200" />
      <el-table-column prop="ip" label="ip" align="center" width="200" />
      <el-table-column prop="port" label="port" align="center" width="200" />
      <el-table-column prop="timeRange" label="时间范围" align="center" />
    </el-table>
    <!--分页  工具条-->
    <!-- <el-col :span="24" class="toolbar" style="position:absolute;bottom:20px;right:0">
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
    </el-col> -->
    <!--删除界面-->
    <el-dialog title="删除数据【提交&删除操作无法撤销，删除前请确认】" :visible.sync="deleteFormVisible" :close-on-click-modal="false">
      <el-form
        ref="deleteForm"
        :model="deleteForm"
        label-width="80px"
        style="margin-left:5%;"
      >
        <el-form-item prop="deadline " label="截止日期">
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
    return {
      deleteFormVisible: false,
      deleteLoading: false,
      deleteForm: {
        deadline: ''
      },
      tableDatas: [],
      listLoading: false,
      downloadLoading: false,
      tableMaxHeight: 0,
      total: 0,
      pageNum: 1,
      pageSize: 20,
      currentPage: 1,
      deleteCount: 0,
      downloadCount: 0, // 要下载文件的个数
      downloadSuccessedCount: 0 // 已下载成功文件的个数
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeTableMaxHeight)
    this.changeTableMaxHeight()
    this.$bus.$on('deleteData', () => {
      this.deleteCount++
      if (this.deleteCount === 3) {
        this.deleteLoading = false
        this.deleteFormVisible = false
        this.getDatas()
      }
    })
    // 要下载文件的个数
    this.$bus.$on('downloadCount', (data) => {
      this.downloadCount = parseInt(data)
      if (this.downloadCount === 0) {
        this.downloadLoading = false
      }
    })
    // 一个文件下载成功
    this.$bus.$on('downloadSuccess', () => {
      this.downloadSuccessedCount++
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
          response.msg.forEach(element => {
            this.tableDatas.push({
              ip: element.ip,
              comId: element.comId,
              port: element.port === 0 ? '无' : element.port,
              timeRange: element.startTime + ' - ' + element.endTime
            })
          })
          this.listLoading = false
        }
      })
    },
    handleDownload() {
      this.downloadLoading = true
      app.get('download').then(response => {
      }).catch(response => {
        console.log(response)
        this.downloadLoading = false
      })
    },
    deleteBtnChecked() {
      this.deleteLoading = true
      this.deleteCount = 0
      app.post('delete', this.deleteForm).then(response => {
      }).catch(response => {
        console.log(response)
        this.deleteLoading = false
      })
    }
  }
}
</script>
