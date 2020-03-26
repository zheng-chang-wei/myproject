<template>
  <section id="roleManager">
    <!--查询-->
    <el-col :span="24" class="query" style="padding-bottom: 0px;min-width:900px">
      <el-form :inline="true" :model="retrieveForm">
        <el-form-item prop="name">
          <el-input v-model="retrieveForm.name" placeholder="分组名称" :maxlength="20" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="retrieveDatas(1)">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true">
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
          <!-- <el-button type="danger" @click="handleDel">删除</el-button> -->
        </el-form-item>
      </el-form>
    </el-col>
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :border="true"
      :data="tableDatas"
      :height="tableMaxHeight"
      highlight-current-row
      style="width: 98%;margin-left:15px;"
    >
      <!-- <el-table-column type="selection" align="center" /> -->
      <el-table-column prop="name" label="分组名称" align="center" />
      <el-table-column prop="description" label="描述" align="center" />
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
            type="primary"
            @click="handleEdit(scope.$index, scope.row)"
          >编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            :disabled="scope.row.isUsed"
            @click="handleDel(scope.$index, scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
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
    <!--新增界面-->
    <el-dialog title="新增" :visible.sync="addDialogVisible" :close-on-click-modal="false">
      <el-form ref="addGroup" :model="addGroup" label-width="100px" :rules="rules" style="width:80%;margin-left:5%">
        <el-form-item label="分组名称" prop="name" required>
          <el-input v-model="addGroup.name" auto-complete="off" placeholder="请输入名称" :maxlength="20" />
        </el-form-item>
        <el-form-item label="描述" prop="description" style="margin-top:20px">
          <el-input v-model="addGroup.description" auto-complete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="addLoading" style="width:90px;" @click.native="addSubmit">提交</el-button>
        <el-button type="cancel" style="width:90px;" @click.native="addDialogVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <!--编辑界面-->
    <el-dialog title="编辑" :visible.sync="editDialogVisible" :close-on-click-modal="false">
      <el-form ref="editGroup" :model="editGroup" label-width="100px" :rules="rules" style="width:80%;margin-left:5%">
        <el-form-item label="分组名称" prop="name" required>
          <el-input v-model="editGroup.name" auto-complete="off" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="描述" prop="description" style="margin-top:20px">
          <el-input v-model="editGroup.description" auto-complete="off" />
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
    // 新增&编辑弹框里长度到达限制时提示
    const checkName = (rule, value, callback) => {
      if (value.length === 20) {
        callback(new Error('名称长度限制20字符'))
      } else {
        callback()
      }
    }
    return {
      tableDatas: [],
      selecteds: [],
      listLoading: true,
      retrieveForm: {
        name: ''
      },
      // 新增界面数据
      addGroup: {
        name: '',
        description: ''
      },
      // 编辑界面数据
      editGroup: {
        id: '',
        name: '',
        description: ''
      },
      // 表单验证提示
      rules: {
        name: [{
          required: true,
          message: '必填',
          trigger: 'submit'
        },
        {
          validator: checkName,
          trigger: 'change'
        }
        ]
        // state: [{ required: true, message: '请选择启用状态', trigger: 'submit' }]
      },
      addDialogVisible: false, // 新增界面是否显示
      addLoading: false,
      editDialogVisible: false, // 编辑界面是否显示
      editLoading: false,
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
    this.getDatas()
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
      this.tableMaxHeight = document.body.offsetHeight - 260
    },
    // 获取角色
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
    handleAdd() {
      this.addDialogVisible = true
      this.addLoading = false
      this.addGroup = {
        nmae: '',
        description: ''
      }
    },
    // 新增
    addSubmit() {
      this.$refs.addGroup.validate(valid => {
        if (valid) {
          this.$confirm('确认提交吗？', '提交', {}).then(() => {
            this.addLoading = true
            const parm = Object.assign({}, this.addGroup)
            app.post('subsystem_save', parm).then(response => {
              if (response.code === 0) {
                this.addLoading = false
                this.$message({
                  message: response.data,
                  type: 'success'
                })
                this.addDialogVisible = false
                this.retrieveDatas(1)
              }
            })
          }).catch(() => {})
        }
      })
    },
    handleEdit(index, row) {
      this.editDialogVisible = true
      this.editGroup = {
        id: row.id,
        name: row.name,
        description: row.description
      }
    },
    editSubmit() {
      this.$refs.editGroup.validate(valid => {
        const vm = this
        if (valid) {
          this.$confirm('确认提交吗？', '提交', {}).then(() => {
            this.editLoading = true
            const parm = Object.assign({}, vm.editGroup)
            app.post('subsystem_update', parm).then(response => {
              if (response.code === 0) {
                vm.editLoading = false
                vm.$message({
                  message: response.data,
                  type: 'success'
                })
                vm.editDialogVisible = false
                this.retrieveDatas(1)
              }
            }).catch(() => { vm.editLoading = false })
          }).catch(() => {})
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
#roleManager section {
  background-color: white;
  margin-left: 12px;
  margin-right: 12px;
  border: 1px solid #e8e8e8;
  /* height: 82vh; */
}

#roleManager .query {
  padding: 16px 15px 0px;
}

#roleManager .toolbar {
  padding: 0px 15px;
  border-bottom: 1px solid transparent;
  border-top-right-radius: 3px;
  border-top-left-radius: 3px;
}

#roleManager .el-breadcrumb {
  height: 30px;
  line-height: 30px;
  padding-left: 20px;
}

#roleManager .el-form-item__label {
  text-align: left;
}

#roleManager .systemColor .el-breadcrumb__item__inner {
  color: #97a8be;
}
</style>
