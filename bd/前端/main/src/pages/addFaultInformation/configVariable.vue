<template>
  <section id="configVariable" class="app-container">
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;margin-top:20px">
      <el-form :inline="true">
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <el-table
      v-loading="listLoading"
      :border="true"
      :data="tableDatas"
      highlight-current-row
      style="width: 98%;"
    >
      <el-table-column prop="name" label="变量名称" align="center" />
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="handleEdit(scope.$index, scope.row)"
          >编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDel(scope.$index, scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--新增界面-->
    <el-dialog title="新增" :visible.sync="addDialogVisible" :close-on-click-modal="false">
      <el-form :model="addGroup" label-width="100px" style="width:80%;margin-left:5%">
        <el-form-item label="变量名称" prop="name" required>
          <el-input v-model="addGroup.name" auto-complete="off" placeholder="请输入变量名称" :maxlength="20" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="addLoading" style="width:90px;" @click.native="addSubmit">提交</el-button>
        <el-button type="cancel" style="width:90px;" @click.native="addDialogVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <!--编辑界面-->
    <el-dialog title="编辑" :visible.sync="editDialogVisible" :close-on-click-modal="false">
      <el-form :model="editGroup" label-width="100px" style="width:80%;margin-left:5%">
        <el-form-item label="变量名称" prop="name" required>
          <el-input v-model="editGroup.name" auto-complete="off" placeholder="请输入变量名称" :maxlength="20" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="editLoading" style="width:90px;" @click.native="editSubmit">提交</el-button>
        <el-button type="cancel" style="width:90px;" @click.native="editDialogVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import app from '@/common/js/app'
export default {
  data() {
    return {
      listLoading: false,
      tableDatas: [],
      // 新增界面数据
      addGroup: {
        name: ''
      },
      // 编辑界面数据
      editGroup: {
        id: '',
        name: ''
      },
      addDialogVisible: false, // 新增界面是否显示
      addLoading: false,
      editDialogVisible: false, // 编辑界面是否显示
      editLoading: false
    }
  },
  mounted() {
    this.getDatas()
  },
  methods: {
    // 获取自定义变量
    getDatas() {
      app.get('config_list_variable').then(response => {
        if (response.code === 0) {
          this.tableDatas = response.data
          this.listLoading = false
        }
      })
    },
    handleAdd() {
      if (this.tableDatas.length === 4) {
        this.$message({
          message: '最多添加4个变量',
          type: 'warning'
        })
        return
      }
      this.addDialogVisible = true
      this.addLoading = false
      this.addGroup = {
        name: null
      }
    },
    // 新增
    addSubmit() {
      this.$confirm('确认提交吗？', '提交', {}).then(() => {
        this.addLoading = true
        const parm = Object.assign({}, this.addGroup)
        app.post('config_save', parm).then(response => {
          if (response.code === 0) {
            this.addLoading = false
            this.$message({
              message: response.data,
              type: 'success'
            })
            this.addDialogVisible = false
            this.getDatas()
          }
        })
      }).catch(() => {})
    },
    handleEdit(index, row) {
      this.editDialogVisible = true
      this.editGroup = {
        id: row.id,
        name: row.name
      }
    },
    editSubmit() {
      const vm = this
      this.$confirm('确认提交吗？', '提交', {}).then(() => {
        this.editLoading = true
        const parm = Object.assign({}, vm.editGroup)
        app.post('config_update', parm).then(response => {
          if (response.code === 0) {
            vm.editLoading = false
            vm.$message({
              message: response.data,
              type: 'success'
            })
            vm.editDialogVisible = false
            this.getDatas()
          }
        }).catch(() => { vm.editLoading = false })
      }).catch(() => {})
    },
    handleDel(index, row) {
      const param = {
        ids: row.id
      }
      this.$confirm('确认删除吗？', '删除', {
        type: 'warning'
      }).then(() => {
        app.post('config_delete', param).then(response => {
          if (response.code === 0) {
            this.$message({
              message: response.data,
              type: 'success'
            })
            this.getDatas()
          }
        })
      }).catch(() => {})
    }
  }
}

</script>

<style>

</style>
