<template>
  <section id="home" class="app-container">
    <!--查询-->
    <el-form :inline="true" :model="retrieveForm" size="mini">
      <el-form-item prop="name">
        <el-input v-model="retrieveForm.name" placeholder="文件名称" :maxlength="20" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="retrieveDatas(1)">查询</el-button>
      </el-form-item>
    </el-form>
    <!--工具条-->
    <el-button type="danger" size="mini" icon="el-icon-delete" @click="handleDel">删除选中</el-button>
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :border="true"
      :data="tableDatas"
      :max-height="tableMaxHeight"
      highlight-current-row
      style="margin-top:15px;"
    >
      <el-table-column type="selection" align="center" />
      <el-table-column prop="ComId" label="ComId" align="center" />
      <el-table-column prop="IP" label="IP" align="center" />
      <el-table-column prop="timeRange" label="时间跨度" align="center" />

    </el-table>
    <!--分页  工具条-->
    <el-col :span="24" class="toolbar" style="position:absolute;bottom:20px;right:0">
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

<script>
import app from '@/common/js/app'
export default {
  data() {
    return {
      tableDatas: [{
        ComId: '1000',
        IP: '192.168.1.1',
        timeRange: '2020/01/01 12:00:00 - 2020/03/26 12:12:12'
      }, {
        ComId: '1000',
        IP: '192.168.1.1',
        timeRange: '2020/01/01 12:00:00 - 2020/03/26 12:12:12'
      }, {
        ComId: '1000',
        IP: '192.168.1.1',
        timeRange: '2020/01/01 12:00:00 - 2020/03/26 12:12:12'
      }, {
        ComId: '1000',
        IP: '192.168.1.1',
        timeRange: '2020/01/01 12:00:00 - 2020/03/26 12:12:12'
      }, {
        ComId: '1000',
        IP: '192.168.1.1',
        timeRange: '2020/01/01 12:00:00 - 2020/03/26 12:12:12'
      }, {
        ComId: '1000',
        IP: '192.168.1.1',
        timeRange: '2020/01/01 12:00:00 - 2020/03/26 12:12:12'
      }, {
        ComId: '1000',
        IP: '192.168.1.1',
        timeRange: '2020/01/01 12:00:00 - 2020/03/26 12:12:12'
      }, {
        ComId: '1000',
        IP: '192.168.1.1',
        timeRange: '2020/01/01 12:00:00 - 2020/03/26 12:12:12'
      }, {
        ComId: '1000',
        IP: '192.168.1.1',
        timeRange: '2020/01/01 12:00:00 - 2020/03/26 12:12:12'
      }],
      selecteds: [],
      listLoading: false,
      retrieveForm: {
        name: ''
      },
      total: 0,
      pageNum: 1,
      pageSize: 20,
      currentPage: 1,
      tableMaxHeight: 0
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeTableMaxHeight)
    this.changeTableMaxHeight()
  },
  mounted() {
    // this.getDatas()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeTableMaxHeight)
  },
  methods: {
    // 查询
    retrieveDatas(currentPage) {
      this.currentPage = currentPage
      this.handleCurrentChange(this.currentPage)
    },
    // 动态更改表格最大高度
    changeTableMaxHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 230
    },

    getDatas() {
      const param = {
        name: this.retrieveForm.name,
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }
      app.get('subsystem_listSubsystem_by_parms', param).then(response => {
        if (response.code === 0) {
          this.tableDatas = response.data
          this.total = response.total
          this.listLoading = false
        }
      })
    },
    handleDel(index, row) {
      const param = {
        ids: row.id
      }
      this.$confirm('确认删除吗？', '删除', {
        type: 'warning'
      }).then(() => {
        app.post('subsystem_delete_by_id', param).then(response => {
          if (response.code === 0) {
            this.$message({
              message: response.data,
              type: 'success'
            })
            this.retrieveDatas(this.currentPage)
          }
        })
      }).catch(() => {})
    },
    // 分页触发
    handleCurrentChange(val) {
      this.pageNum = val
      this.getDatas()
    },
    // 改变页码
    handleSizeChange(val) {
      this.pageSize = val
      this.getDatas()
    }
  }
}
</script>
<style scoped>

#home .toolbar {
  padding: 0px 15px;
  border-bottom: 1px solid transparent;
  border-top-right-radius: 3px;
  border-top-left-radius: 3px;
}

#home .el-form-item__label {
  text-align: left;
}

</style>
