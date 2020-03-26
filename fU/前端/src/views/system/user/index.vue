<template>
  <section id="userManager" class="app-container">
    <!--查询-->
    <el-col :span="24" class="query" style="padding-bottom: 0px;min-width:900px">
      <el-form ref="retrieveForm" :inline="true" :rules="retrieveRules" :model="retrieveForm">
        <el-form-item prop="userName">
          <el-input v-model="retrieveForm.userName" placeholder="用户名" :maxlength="20" />
        </el-form-item>
        <el-form-item prop="roleName">
          <el-select v-model="retrieveForm.roleName" placeholder="请选择">
            <el-option v-for="item in roleNamesOptions" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item prop="name">
          <el-input v-model="retrieveForm.name" placeholder="姓名" :maxlength="20" />
        </el-form-item>
        <el-form-item prop="email">
          <el-input v-model="retrieveForm.email" placeholder="邮箱" :maxlength="50" />
        </el-form-item>
        <el-form-item prop="mobile">
          <el-input v-model="retrieveForm.mobile" placeholder="电话" :maxlength="50" />
        </el-form-item>
        <el-form-item prop="wechat">
          <el-input v-model="retrieveForm.wechat" placeholder="微信" :maxlength="50" />
        </el-form-item>
        <el-form-item prop="deptName">
          <el-input v-model="retrieveForm.deptName" placeholder="部门" :maxlength="50" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="search" @click="retrieveUsers">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true">
        <el-form-item>
          <el-button type="primary" @click="handleAdd">
            <i class="iconfont icon-add" />新增</el-button>
          <el-button type="primary" @click="handleEdit">
            <i class="iconfont icon-edit" />编辑</el-button>
          <el-button type="danger" @click="batchRemove">
            <i class="iconfont icon-delete" />删除</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table
      v-loading="listLoading"
      :data="users"
      border
      :height="tableMaxHeight"
      highlight-current-row
      style="width: 98%;margin-left:15px"
      @selection-change="selsChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="userName" label="用户名" align="center" sortable width="120px" :resizable="false" />
      <el-table-column prop="groundRoleIds" label="角色" align="center" sortable :resizable="false" />
      <el-table-column prop="lineIds" label="线路" align="center" sortable width="130px" :resizable="false" />
      <el-table-column prop="name" label="姓名" align="center" sortable width="100px" :resizable="false" />
      <el-table-column prop="email" label="邮箱" align="center" sortable :resizable="false" />
      <el-table-column prop="mobile" label="电话" align="center" sortable :resizable="false" />
      <el-table-column prop="wechat" label="微信" align="center" sortable :resizable="false" />
      <el-table-column prop="deptName" label="部门" align="center" sortable width="100px" :resizable="false" />
      <el-table-column
        prop="createTime"
        label="创建时间"
        :formatter="formatCreatetime"
        align="center"
        sortable
        :resizable="false"
        width="110px"
      />
    </el-table>

    <!--分页  工具条-->
    <el-col :span="24" class="toolbar" style="position:absolute;bottom:10px;right:0">
      <el-pagination
        :current-page="currentPage"
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
    <el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
      <el-form ref="addForm" :rules="addRules" :model="addForm" label-width="80px" style="margin-left:5%;">
        <el-form-item label="用户名" prop="userName" size="small">
          <el-input v-model="addForm.userName" auto-complete="off" placeholder="请输入用户名" :maxlength="20" />
        </el-form-item>
        <el-form-item label="密码" prop="password" size="small">
          <el-input v-model="addForm.password" show-password auto-complete="off" placeholder="请输入密码" :maxlength="20" />
        </el-form-item>
        <el-form-item label="姓名" prop="name" size="small">
          <el-input v-model="addForm.name" auto-complete="off" placeholder="请输入姓名" :maxlength="20" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email" size="small">
          <el-input v-model="addForm.email" auto-complete="off" placeholder="请输入邮箱" :maxlength="20" />
        </el-form-item>
        <el-form-item label="电话" prop="mobile" size="small">
          <el-input v-model="addForm.mobile" auto-complete="off" placeholder="请输入电话" :maxlength="20" />
        </el-form-item>
        <el-form-item label="微信" prop="wechat" size="small">
          <el-input v-model="addForm.wechat" auto-complete="off" placeholder="请输入微信" :maxlength="20" />
        </el-form-item>
        <el-form-item label="部门" prop="deptName" size="small">
          <el-select v-model="addForm.deptName" placeholder="请选择">
            <el-option v-for="item in deptNameOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="角色" prop="groundRoleIds" size="small">
          <el-select v-model="addForm.groundRoleIds" placeholder="请选择">
            <el-option v-for="item in rolesOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="审批角色" prop="checkedApprovalRoles" size="small">
          <el-checkbox-group v-model="addForm.checkedApprovalRoles">
            <el-checkbox v-for="approvalRole in approvalRoles" :key="approvalRole.value" :label="approvalRole.label" :value="approvalRole.value">{{ approvalRole.label }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="线路" prop="line" style="margin-top:20px">
          <el-tree ref="tree" :data="treeData" show-checkbox node-key="id" :props="defaultProps" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="addLoading" style="width:90px;" @click.native="addSubmit">提交
        </el-button>
        <el-button type="cancel" style="width:90px;" @click.native="addFormVisible = false">取消</el-button>
      </div>
    </el-dialog>

    <!--编辑界面-->
    <el-dialog title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false">
      <el-form ref="editForm" :rules="editRules" :model="editForm" label-width="80px" style="margin-left:5%;">
        <el-form-item label="用户名" prop="userName" size="small">
          <el-input v-model="editForm.userName" auto-complete="off" placeholder="请输入用户名" :maxlength="20" />
        </el-form-item>
        <el-form-item label="密码" prop="password" size="small">
          <el-input v-model="editForm.password" show-password auto-complete="off" placeholder="请输入密码" :maxlength="20" type="password" />
        </el-form-item>
        <el-form-item label="姓名" prop="name" size="small">
          <el-input v-model="editForm.name" auto-complete="off" placeholder="请输入姓名" :maxlength="20" />
        </el-form-item>
        <el-form-item label="部门" prop="deptName" size="small">
          <el-select v-model="editForm.deptName" placeholder="请选择">
            <el-option v-for="item in deptNameOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="邮箱" prop="email" size="small">
          <el-input v-model="editForm.email" auto-complete="off" placeholder="请输入邮箱" :maxlength="20" />
        </el-form-item>
        <el-form-item label="电话" prop="mobile" size="small">
          <el-input v-model="editForm.mobile" auto-complete="off" placeholder="请输入电话" :maxlength="20" />
        </el-form-item>
        <el-form-item label="微信" prop="wechat" size="small">
          <el-input v-model="editForm.wechat" auto-complete="off" placeholder="请输入微信" :maxlength="20" />
        </el-form-item>
        <el-form-item label="角色" prop="groundRoleIds" size="small">
          <el-select v-model="editForm.groundRoleIds" placeholder="请选择" :disabled="editForm.roles== '1'">
            <el-option v-for="item in rolesOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="审批角色" prop="checkedApprovalRoles" size="small">
          <el-checkbox-group v-model="editForm.checkedApprovalRoles">
            <el-checkbox v-for="approvalRole in approvalRoles" :key="approvalRole.value" :label="approvalRole.label">{{ approvalRole.label }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="线路" prop="line" style="margin-top:20px">
          <el-tree ref="tree" :data="treeData" show-checkbox node-key="id" :props="defaultProps" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="editLoading" style="width:90px;" @click.native="editSubmit">提交
        </el-button>
        <el-button type="cancel" style="width:90px;" @click.native="editFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </section>
</template>
<script type="text/ecmascript-6">
  const Base64 = require('js-base64').Base64
  import app from '@/common/js/app'
  import util from '@/common/js/util'
  export default {
    components: {

    },
    data() {
      // 新增&编辑弹框里长度到达限制时提示
      const checkUserName = (rule, value, callback) => {
        if (value.length === 21) {
          callback(new Error('用户名长度限制20字符'))
        } else {
          callback()
        }
      }

      const checkPassWord = (rule, value, callback) => {
        if (value.length === 21) {
          callback(new Error('密码长度限制20字符'))
        } else {
          callback()
        }
      }
      const checkTime = (rule, value, callback) => {
        const sTime = new Date(this.retrieveForm.starttime).getTime()
        const eTime = new Date(value).getTime()
        if (eTime < sTime) {
          callback(new Error('结束时间比开始时间早,请重新选择查询时间段'))
        } else {
          callback()
        }
      }
      return {
        isAdd: false,
        treeData: [],
        defaultProps: {
          children: 'children',
          label: 'text'
        },
        users: [],
        total: 0,
        pageNum: 1,
        pageSize: 20,
        currentPage: 1,
        inputUsername: '',
        listLoading: false,
        sels: [], // 列表选中的选项
        rolesOptions: [],
        roleNamesOptions: [],
        deptNameOptions: [],
        approvalRoles: [],
        // 查询功能数据
        retrieveForm: {
          userName: '',
          roleName: '所有',
          email: '',
          deptName: '',
          name: '',
          mobile: '',
          wechat: ''
        },
        // 新增界面数据
        addForm: {
          userName: '',
          name: '',
          password: '',
          email: '',
          mobile: '',
          wechat: '',
          deptName: '',
          groundRoleIds: '',
          checkedApprovalRoles: []
        },
        addFormVisible: false, // 新增界面是否显示
        addLoading: false,
        // 编辑界面数据
        editForm: {
          userName: '',
          name: '',
          password: '',
          email: '',
          mobile: '',
          wechat: '',
          deptName: '',
          groundRoleIds: '',
          checkedApprovalRoles: []
        },
        editFormVisible: false, // 编辑界面是否显示
        editLoading: false,
        // 新增弹框表单验证
        addRules: {
          userName: [{
            required: true,
            message: '必填',
            trigger: 'blur'
          },
            {
              validator: checkUserName,
              trigger: 'blur'
            }
          ],
          password: [{
            required: true,
            message: '必填',
            trigger: 'blur'
          },
            {
              validator: checkPassWord,
              trigger: 'blur'
            }
          ]
        },
        // 编辑弹框表单验证
        editRules: {
          userName: [{
            required: true,
            message: '必填',
            trigger: 'blur'
          },
            {
              validator: checkUserName,
              trigger: 'blur'
            }
          ],
          password: [{
            required: true,
            message: '必填',
            trigger: 'blur'
          },
            {
              validator: checkPassWord,
              trigger: 'blur'
            }
          ],
          groundRoleIds: [{
            required: true,
            message: '必填',
            trigger: 'blur'
          }]
        },
        // 查询时验证
        retrieveRules: {
          userName: [{
            validator: checkUserName,
            trigger: 'blur'
          }],
          endtime: [{
            validator: checkTime,
            trigger: 'blur'
          }]
        },
        tableMaxHeight: document.body.offsetHeight - 230
      }
    },
    mounted() {
      this.getLineTree()
      this.getUsers()
      this.getDepts()
      this.getAllRoles()
      this.getAllApprovalRoles()
      // 页面改变时,更改尺寸
      window.addEventListener('resize', this.changeTableMaxHeight)
      this.changeTableMaxHeight()
    },
    methods: {

      getDepts() {
        const vm = this
        app.get('get_dept_list').then(data => {
          if (data) {
            if (data.msg && data.msg.length !== 0) {
              vm.deptNameOptions = []
              data.msg.forEach(item => {
                vm.deptNameOptions.push({
                  value: item.deptId,
                  label: item.deptName
                })
              })
            }
          }
        })
      },
      getLineTree() {
        const vm = this
        app.get('findLineTree').then(data => {
          if (data) {
            vm.treeData = data.msg
          }
        })
      },
      // 获取所有用户角色
      getAllRoles() {
        var vm = this
        app.get('get_all_role').then(data => {
          if (!data) return
          if (data.msg && data.msg.length !== 0) {
            vm.roleNamesOptions = ['所有']
            vm.rolesOptions = []
            data.msg.forEach(item => {
              vm.roleNamesOptions.push(item.roleName)
              vm.rolesOptions.push({
                value: item.groundRoleId,
                label: item.roleName
              })
            })
          }
        })
      },
      // 获取所有用户角色
      getAllApprovalRoles() {
        var vm = this
        app.get('get_all_approval_role').then(data => {
          if (!data) return
          if (data.msg && data.msg.length !== 0) {
            vm.approvalRoles = []
            data.msg.forEach(item => {
              vm.approvalRoles.push({
                value: item.repairRoleId,
                label: item.roleName
              })
            })
          }
        })
      },
      // 查询用户
      retrieveUsers() {
        this.$refs.retrieveForm.validate(valid => {
          if (valid) {
            this.getUsers()
          }
        })
      },
      // 获取用户
      getUsers() {
        this.listLoading = true
        const vm = this
        const param = {
          userName: vm.retrieveForm.userName,
          name: vm.retrieveForm.name,
          roleName: vm.retrieveForm.roleName,
          email: vm.retrieveForm.email,
          parentName: vm.retrieveForm.parentName,
          deptName: vm.retrieveForm.deptName,
          mobile: vm.retrieveForm.mobile,
          pageNum: vm.pageNum,
          pageSize: vm.pageSize
        }
        if (vm.retrieveForm.roleName === '所有') {
          param.roleName = ''
        }
        app.get('get_user', param).then(d => {
          if (d) {
            vm.users = d.msg.rows
            vm.total = d.msg.total
            vm.users.forEach(item => {
              if (item.groundRoleIds) {
                item.groundRoleIds = item.groundRoleIds
                  .map(itemI => itemI.roleName)
                  .join(',')
              }
              if (item.lineIds) {
                item.lineIds = item.lineIds
                  .map(itemI => itemI)
                  .join(',')
              }
            })
            this.listLoading = false
          }
        })
      },
      // 显示新增弹窗
      handleAdd() {
        const vm = this
        vm.isAdd = true
        this.addFormVisible = true
        this.addForm = {
          userName: '',
          name: '',
          password: '',
          email: '',
          mobile: '',
          wechat: '',
          deptName: '',
          groundRoleIds: '',
          checkedApprovalRoles: []
        }
      },
      // 新增
      addSubmit() {
        this.$refs.addForm.validate(valid => {
          var vm = this
          if (valid) {
            this.$confirm('确认提交吗？', '提交', {}).then(() => {
              vm.addLoading = true
              var ids = []
              // 将名称换成id
              vm.addForm.checkedApprovalRoles.forEach(item => {
                ids.push(vm.getIdByRoleName(item))
              })
              const param = {
                userName: vm.addForm.userName,
                name: vm.addForm.name,
                password: Base64.encode(vm.addForm.password),
                email: vm.addForm.email,
                mobile: vm.addForm.mobile,
                deptId: vm.addForm.deptName,
                wechat: vm.addForm.wechat,
                roleIds: vm.addForm.groundRoleIds,
                checkedApprovalRoleIds: ids.join(','),
                lineIds: vm.$refs.tree.getCheckedKeys(true).join(',')
              }
              app.post('add_user', param).then(data => {
                vm.addLoading = false
                if (data.code === '0001') {
                  vm.$message({
                    message: data.msg,
                    type: 'error'
                  })
                } else {
                  vm.$message({
                    message: data.msg,
                    type: 'success'
                  })
                  vm.$refs['addForm'].resetFields()
                  vm.addFormVisible = false

                  // vm.pageNum = 1; //返回第一页
                  // vm.currentPage = 1;
                  vm.getUsers()
                }
              })
            }).catch(() => {})
          }
        })
      },
      // 显示编辑弹窗
      handleEdit() {
        const vm = this
        // 进行判断,只能选择一个进行编辑
        if (this.sels.length === 0) {
          this.$message('请勾选一个用户')
          return
        } else if (this.sels.length > 1) {
          this.$message('只能选择一个用户')
          return
        } else {
          vm.isAdd = false
          vm.editFormVisible = true
          const param = {
            userId: vm.sels[0].id
          }
          app.get('getUserById', param).then(d => {
            if (d) {
              console.log(d.msg)
              vm.editForm = {
                userName: d.msg.userName,
                name: d.msg.name,
                password: Base64.decode(d.msg.password),
                email: d.msg.email,
                mobile: d.msg.mobile,
                wechat: d.msg.wechat,
                deptName: d.msg.deptId,
                groundRoleIds: d.msg.groundRoleIds[0].groundRoleId,
                checkedApprovalRoles: d.msg.approvalRoleNames
              }
              vm.$refs.tree.setCheckedKeys(d.msg.lineIds)
            }
          })
        }
      },
      // 编辑
      editSubmit() {
        this.$refs.editForm.validate(valid => {
          var vm = this
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.editLoading = true
              console.log(vm.editForm)
              var ids = []
              // 将名称换成id
              vm.editForm.checkedApprovalRoles.forEach(item => {
                ids.push(vm.getIdByRoleName(item))
              })
              const param = {
                userId: vm.sels[0].id,
                userName: vm.editForm.userName,
                name: vm.editForm.name,
                password: Base64.encode(vm.editForm.password),
                email: vm.editForm.email,
                mobile: vm.editForm.mobile,
                deptId: vm.editForm.deptName,
                wechat: vm.editForm.wechat,
                roleIds: vm.editForm.groundRoleIds,
                checkedApprovalRoleIds: ids.join(','),
                lineIds: vm.$refs.tree.getCheckedKeys(true).join(',')
              }
              app.post('update_user', param).then(data => {
                vm.editLoading = false
                if (data.code === '0001') {
                  vm.$message({
                    message: data.msg,
                    type: 'error'
                  })
                } else {
                  vm.$message({
                    message: data.msg,
                    type: 'success'
                  })
                  vm.$refs['editForm'].resetFields()
                  vm.editFormVisible = false

                  // vm.pageNum = 1; //返回第一页
                  // vm.currentPage = 1;
                  vm.getUsers()
                }
              })
            }).catch(() => {})
          }
        })
      },
      // 批量删除
      batchRemove() {
        var vm = this
        var ids = this.sels.map(item => item.id).toString()

        if (this.sels.length === 0) {
          this.$message({
            message: '请至少勾选一个用户删除',
            type: 'error'
          })
          return
        }
        this.$confirm('确认删除选中记录？', '提示', {
          type: 'warning'
        }).then(() => {
          this.listLoading = true
          const param = {
            userIds: ids
          }
          app.post('delete_user', param).then(data => {
            vm.listLoading = false
            if (data.code === '0003' || data.code === '0002') {
              vm.$message({
                message: data.msg,
                type: 'error'
              })
            } else {
              vm.$message({
                message: data.msg,
                type: 'success'
              })
              vm.getUsers()
            }
          })
        }).catch(() => {})
      },
      getIdByRoleName(roleName) {
        for (let index = 0; index < this.approvalRoles.length; index++) {
          const item = this.approvalRoles[index]
          if (item.label === roleName) {
            return item.value
          }
        }
      },
      // 分页触发
      handleCurrentChange(val) {
        this.pageNum = val
        this.getUsers()
      },
      // 改变页码
      handleSizeChange(val) {
        this.pageSize = val
        this.getUsers()
      },
      // 格式化创建时间
      formatCreatetime(row, column) {
        return (row.createTime = row.createTime
          ? util.formatDate(new Date(row.createTime), 'yyyy-MM-dd')
          : '')
      },
      // 格式化时间控件的开始时间
      formatStartTime(val) {
        this.retrieveForm.starttime = val
      },
      // 格式化时间控件的结束时间
      formatEndTime(val) {
        this.retrieveForm.endtime = val
      },
      // 列表选中的选项
      selsChange(sels) {
        this.sels = sels
      },
      // 动态更改表格最大高度
      changeTableMaxHeight() {
        this.tableMaxHeight = document.body.offsetHeight - 230
        // var sectionHeight = document.body.offsetHeight - 120 + 'px'
        // $('#userManager').css({
        //   height: sectionHeight
        // })
      }
    }
  }

</script>
<style>
  /* #userManager {
  background-color: white;
  margin-left: 12px;
  margin-right: 12px;
  border: 1px solid #e8e8e8;
} */

  #userManager .query {
    padding: 16px 15px 0px;
  }

  #userManager .query .el-input {
    width: 130px;
  }

  #userManager .toolbar {
    padding: 0px 15px;
    border-bottom: 1px solid transparent;
    border-top-right-radius: 3px;
    border-top-left-radius: 3px;
  }

  #userManager .el-form-item__label {
    text-align: right;
  }

  #userManager .el-dialog .el-input {
    width: 90%;
  }

  #userManager .el-dialog .el-select {
    width: 100%;
  }

</style>
