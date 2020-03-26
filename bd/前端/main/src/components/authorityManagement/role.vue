<template>
  <section id="roleManager">
    <!--查询-->
    <el-col :span="24" class="query" style="padding-bottom: 0px;min-width:900px">
      <el-form :inline="true" :model="retrieveForm" size="mini">
        <el-form-item prop="roleName">
          <el-input v-model="retrieveForm.roleName" placeholder="角色名称" :maxlength="20" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="retrieveRoles(1)">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true">
        <el-form-item>
          <el-button type="primary" size="mini" @click="handleAdd">新增</el-button>
          <el-button type="primary" size="mini" @click="handleEdit">编辑</el-button>
          <el-button type="danger" size="mini" @click="handleDel">删除</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :border="true"
      :data="roleTableData"
      :max-height="tableMaxHeight"
      highlight-current-row
      style="width: 98%;margin-left:15px;"
      @selection-change="roleSelectChange"
    >
      <el-table-column type="selection" align="center" />
      <el-table-column prop="roleName" label="角色名" align="center" />
      <el-table-column prop="remark" label="描述" align="center" />
      <el-table-column prop="createTime" label="创建时间" align="center" />
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
    <el-dialog title="新增" :visible.sync="addRoleVisible" :close-on-click-modal="false">
      <el-form ref="addRole" :model="addRole" label-width="100px" :rules="rules" style="width:80%;margin-left:5%">
        <el-form-item label="角色名" prop="roleName" required>
          <el-input v-model="addRole.roleName" auto-complete="off" placeholder="请输入角色名称" :maxlength="20" />
        </el-form-item>
        <el-form-item label="描述" prop="remark" style="margin-top:20px">
          <el-input v-model="addRole.remark" auto-complete="off" />
        </el-form-item>
        <el-form-item label="权限" style="margin-top:20px">
          <el-tree ref="tree" :data="treeData" show-checkbox node-key="menuId" :props="defaultProps" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="addLoading" style="width:90px;" @click.native="addSubmit">提交</el-button>
        <el-button type="cancel" style="width:90px;" @click.native="addRoleVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <!--编辑界面-->
    <el-dialog title="编辑" :visible.sync="editRoleVisible" :close-on-click-modal="false">
      <el-form ref="editRole" :model="editRole" label-width="100px" :rules="rules" style="width:80%;margin-left:5%">
        <el-form-item label="角色名" prop="roleName" required>
          <el-input v-model="editRole.roleName" auto-complete="off" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="描述" prop="remark" style="margin-top:20px">
          <el-input v-model="editRole.remark" auto-complete="off" />
        </el-form-item>
        <el-form-item label="权限" style="margin-top:20px">
          <el-tree ref="tree" :data="treeData" show-checkbox node-key="menuId" :props="defaultProps" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="editLoading" style="width:90px;" @click.native="editSubmit">提交</el-button>
        <el-button type="cancel" style="width:90px;" @click.native="editRoleVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import app from '@/common/js/app'
export default {
  data() {
    // 新增&编辑弹框里长度到达限制时提示
    const checkRoleName = (rule, value, callback) => {
      if (value.length === 20) {
        callback(new Error('角色名称长度限制20字符'))
      } else {
        callback()
      }
    }
    return {
      roleTableData: [],
      roleSelect: [],
      listLoading: true,
      retrieveForm: {
        roleName: ''
      },
      // 新增界面数据
      addRole: {
        groundRoleId: '',
        roleName: '',
        remark: ''
      },
      // 编辑界面数据
      editRole: {
        groundRoleId: '',
        roleName: '',
        remark: ''
      },
      // 表单验证提示
      rules: {
        name: [{
          required: true,
          message: '必填',
          trigger: 'submit'
        },
        {
          validator: checkRoleName,
          trigger: 'change'
        }
        ]
        // state: [{ required: true, message: '请选择启用状态', trigger: 'submit' }]
      },
      authorityList: [],
      addRoleVisible: false, // 新增界面是否显示
      addLoading: false,
      editRoleVisible: false, // 编辑界面是否显示
      editLoading: false,
      total: 0,
      pageNum: 1,
      pageSize: 20,
      currentPage: 1,
      treeData: [], // 菜单树
      defaultProps: {
        children: 'children',
        label: 'menuName'
      },
      child: [], // 在树上获得的最子节点
      tArr: [], // 完整的父子id
      exp: [],
      tableMaxHeight: 0
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeTableMaxHeight)
    this.changeTableMaxHeight()
  },
  mounted() {
    this.getRoles()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeTableMaxHeight)
  },
  methods: {
    // 格式化启用状态
    isUse(row, column) {
      return row.state === 0 ? '启用' : '禁用'
    },
    roleSelectChange(val) {
      this.roleSelect = val
    },
    // 查询
    retrieveRoles(currentPage) {
      this.currentPage = currentPage
      this.handleCurrentChange(this.currentPage)
    },
    // 动态更改表格最大高度
    changeTableMaxHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 240
    },
    // 获取角色
    getRoles() {
      const param = {
        roleName: this.retrieveForm.roleName,
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }
      app.get('get_role', param).then(data => {
        if (data.msg) {
          this.roleTableData = data.msg.rows
          this.total = data.msg.total
          this.listLoading = false
        }
      })
    },
    handleAdd() {
      this.addRoleVisible = true
      this.addLoading = false
      setTimeout(function() {
        if (this.$refs.addRole) this.$refs['addRole'].resetFields()
      }, 50)
      this.addRole = {
        groundRoleId: '',
        roleName: '',
        remark: ''
      }
      this.getMenu()
    },
    // 新增
    addSubmit() {
      this.$refs.addRole.validate(valid => {
        if (valid) {
          this.$confirm('确认提交吗？', '提交', {}).then(() => {
            this.addLoading = true
            const parm = Object.assign({}, this.addRole)
            parm.menuIds = this.$refs.tree.getCheckedKeys().join(',')
            app.post('add_role', parm).then(data => {
              if (data.msg) {
                this.addLoading = false
                this.$message({
                  message: data.msg,
                  type: 'success'
                })
                this.$refs['addRole'].resetFields()
                this.addRoleVisible = false
                this.getRoles()
              }
            })
          }).catch(() => {})
        }
      })
    },
    handleEdit() {
      this.editLoading = false
      if (this.roleSelect.length === 1) {
        this.editRoleVisible = true
        this.editRole = {
          groundRoleId: this.roleSelect[0].groundRoleId,
          roleName: this.roleSelect[0].roleName,
          remark: this.roleSelect[0].remark
        }
        this.handleAuthority()
      } else if (this.roleSelect.length === 0) {
        this.$message({
          message: '至少选择一个角色编辑'
        })
      } else {
        this.$message({
          message: '只能选择一个角色编辑'
        })
      }
    },
    editSubmit() {
      this.$refs.editRole.validate(valid => {
        const vm = this
        if (valid) {
          this.$confirm('确认提交吗？', '提交', {}).then(() => {
            this.editLoading = true
            const parm = Object.assign({}, vm.editRole)
            parm.menuIds = this.$refs.tree.getCheckedKeys().join(',')
            app.post('update_role', parm).then(data => {
              if (data.msg) {
                vm.editLoading = false
                vm.$message({
                  message: data.msg,
                  type: 'success'
                })
                vm.$refs['editRole'].resetFields()
                vm.editRoleVisible = false
                this.getRoles()
              }
            })
          }).catch(() => {})
        }
      })
    },
    handleDel() {
      const vm = this
      let ids = ''
      if (vm.roleSelect.length !== 0) {
        for (let i = 0; i < vm.roleSelect.length; i++) {
          if (i === 0) {
            ids = vm.roleSelect[i].groundRoleId
          } else {
            ids = ids + ',' + vm.roleSelect[i].groundRoleId
          }
        }
        const param = {
          ids: ids
        }
        this.$confirm('确认删除吗？', '删除', {
          type: 'warning'
        }).then(() => {
          app.post('delete_role', param).then(data => {
            if (data.msg) {
              vm.$message({
                message: data.msg,
                type: 'success'
              })
              vm.getRoles()
            }
          })
        }).catch(() => {})
      } else {
        vm.$message({
          message: '请至少选择一个角色删除',
          type: 'error'
        })
      }
    },
    getMenu() {
      app.get('findLeftTree_menu').then(data => {
        if (data.msg) {
          this.treeData = data.msg
        }
      })
    },
    handleAuthority() {
      app.get('findLeftTree_menu').then(data => {
        if (data.msg) {
          this.treeData = data.msg
          const param = {
            id: this.roleSelect[0].groundRoleId
          }
          app.get('get_role_authority', param).then(data => {
            if (data.msg) {
              const getId = data.msg
              const idArr = []
              for (let z = 0; z < getId.length; z++) {
                idArr.push(getId[z].menuId)
              }
              this.$refs.tree.setCheckedKeys(idArr)
            }
          })
        }
      })
    },
    // 分页触发
    handleCurrentChange(val) {
      this.pageNum = val
      this.getRoles()
    },
    // 改变页码
    handleSizeChange(val) {
      this.pageSize = val
      this.getRoles()
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
