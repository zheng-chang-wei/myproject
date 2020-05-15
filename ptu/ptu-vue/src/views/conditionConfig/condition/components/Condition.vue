<template>
  <div>
    <el-form :inline="true" size="mini">
      <el-form-item>
        <el-button type="primary" icon="el-icon-plus" @click="openAddConditionDialog">添加条件</el-button>
        <el-button type="danger" icon="el-icon-delete" @click="handleDelCondition">删除条件</el-button>
      </el-form-item>
    </el-form>
    <el-table
      ref="table"
      v-loading="tableLoading"
      border
      :data="conditionTableDatas"
      highlight-current-row
      :max-height="tableMaxHeight"
      :header-cell-style="{padding:'3px',fontSize:'11px'}"
      :cell-style="{padding:'3px',fontSize:'10px'}"
    >
      <el-table-column type="selection" align="center" />
      <el-table-column type="index" label="序号" align="center" width="50px" />
      <el-table-column prop="conditionName" label="条件名称" align="center" width="150px" />
      <el-table-column prop="expression" label="逻辑运算" align="center" />
      <el-table-column label="操作" align="center" width="100px">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-edit"
            @click="openEditConditionDialog(scope.$index, scope.row)"
          >编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <ConditionDialog ref="conditionDialog" :type="type" @getCondition="getCondition" />
  </div>
</template>
<script>
import ConditionDialog from './ConditionDialog'
import app from '@/common/js/app'
export default {
  components: {
    ConditionDialog
  },
  props: {
    type: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      conditionTableDatas: [],
      tableMaxHeight: 0,
      tableLoading: false
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeTableMaxHeight)
    this.changeTableMaxHeight()
  },
  mounted() {
    this.getCondition()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeTableMaxHeight)
  },
  methods: {
    // 动态更改表格最大高度
    changeTableMaxHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 200
    },
    openAddConditionDialog() {
      this.$refs.conditionDialog.openDialog({
        title: '新增条件',
        row: null
      })
    },
    handleDelCondition() {
      this.$confirm('此操作将删除该条件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const ids = []
        this.$refs.table.selection.forEach(element => {
          ids.push(element.id)
        })
        const parm = {
          ids: ids.join(',')
        }
        app.post('deleteCondition', parm).then(response => {
          if (response.code === 0) {
            this.$message({
              type: 'success',
              message: '删除成功'
            })
            this.getCondition()
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    openEditConditionDialog(index, row) {
      this.$refs.conditionDialog.openDialog({
        title: '编辑条件',
        row: row
      })
    },
    getCondition() {
      this.tableLoading = true
      app.get('listCondition', { type: this.type }).then(response => {
        if (response.code === 0) {
          this.conditionTableDatas = response.msg
          this.tableLoading = false
        }
      })
    }

  }
}
</script>
