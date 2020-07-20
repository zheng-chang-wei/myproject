<template>
  <el-dialog id="groupManager" title="新增" :visible.sync="groupManagerDialogVisible" :close-on-click-modal="false" top="10px">
    <!--工具条-->
    <el-button type="primary" icon="el-icon-plus" style="margin-bottom:10px" size="mini" @click="handleAdd">新增</el-button>
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      border
      :data="tableDatas"
      :height="tableMaxHeight"
      highlight-current-row
      :row-style="{ cursor: 'pointer', fontSize: '12px' }"
      :header-cell-style="{padding:'3px',fontSize:'13px'}"
      :cell-style="{padding:'3px'}"
    >
      <el-table-column prop="name" label="分组名称" align="center">
        <template slot-scope="scope">
          <el-input v-model="scope.row.name" />
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述" align="center">
        <template slot-scope="scope">
          <el-input v-model="scope.row.description" />
        </template>
      </el-table-column>
      <el-table-column label="包含算法个数" align="center">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.isUsed ? 'danger' : 'success'"
          >{{ scope.row.count }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            :disabled="scope.row.isUsed"
            icon="el-icon-delete"
            @click="handleDel(scope.$index, scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="groupManagerDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import app from '@/common/js/app'
export default {
  data() {
    return {
      tableDatas: [],
      listLoading: true,
      groupManagerDialogVisible: false,
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

  },
  destroyed() {
    window.removeEventListener('resize', this.changeTableMaxHeight)
  },
  methods: {
    openDialog() {
      this.getDatas()
      this.groupManagerDialogVisible = true
    },
    // 动态更改表格最大高度
    changeTableMaxHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 190
    },
    getDatas() {
      this.listLoading = true
      app.get('subsystem_listSubsystem_by_parms').then(response => {
        if (response.code === 0) {
          this.tableDatas = response.data
          this.listLoading = false
        }
      })
    },
    handleAdd() {
      this.tableDatas.push({
        name: '',
        description: '',
        count: 0
      })
    },
    handleDel(index, row) {
      this.$confirm('确认删除吗？', '删除', {
        type: 'warning'
      }).then(() => {
        this.tableDatas.splice(index, 1)
      }).catch(() => {})
    },
    confirm() {
      if (this.isRepeat(this.tableDatas)) {
        this.$message({
          message: '算法名称重复',
          type: 'error'
        })
        return
      }
      for (let index = 0; index < this.tableDatas.length; index++) {
        const element = this.tableDatas[index]
        if (element.name === '') {
          this.$message({
            message: '算法名称不能为空',
            type: 'error'
          })
          return
        }
      }
      app.postData('subsystem_save', this.tableDatas).then(response => {
        if (response.code === 0) {
          this.groupManagerDialogVisible = false
          this.$message({
            message: response.data,
            type: 'success'
          })
          this.$emit('saveSuccess')
        }
      })
    },
    isRepeat(arr) {
      var hash = {}
      for (var i in arr) {
        if (hash[arr[i].name]) {
          return true
        }
        // 不存在该元素，则赋值为true，可以赋任意值，相应的修改if判断条件即可
        hash[arr[i].name] = true
      }
      return false
    }
  }
}
</script>
<style>

</style>
