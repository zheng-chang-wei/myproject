<template>
  <section class="app-container user-manager">
    <el-row>
      <el-col :span="8">
        <el-button type="primary" @click="addUser">新增</el-button>
      </el-col>
    </el-row>
    <el-row style="margin-top:10px">
      <el-col :span="24">
        <el-table v-loading="loading" :data="users" border highlight-current-row>
          <el-table-column prop="username" label="用户名" align="center" />
          <el-table-column prop="role.cname" label="用户角色" align="center" />
          <el-table-column width="200">
            <template slot-scope="scope">
              <el-button v-if="scope.row.username!='admin'" type="primary" size="small" @click="update(scope.row)">修改</el-button>
              <el-button v-if="scope.row.username!='admin'" type="danger" size="small" @click="deleteUser(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-dialog title="新增用户" :visible.sync="dialog.add" :close-on-click-modal="false" destroy-on-close>
      <edit-dialog :user="user.add" :roles="roles" @submit="addSubmit" />
    </el-dialog>
    <el-dialog title="修改用户" :visible.sync="dialog.edit" :close-on-click-modal="false" destroy-on-close>
      <edit-dialog :user="user.edit" :roles="roles" @submit="editSubmit" />
    </el-dialog>
  </section>
</template>
<script>
import app from '@/common/js/app'
import EditDialog from './EditDialog'
export default {
  components: {
    EditDialog
  },
  data() {
    return {
      users: [],
      dialog: {
        add: false,
        edit: false
      },
      user: {
        add: {},
        edit: {}
      },
      roles: [],
      loading: false
    }
  },
  mounted() {
    this.getUsers()
    this.getRoles()
    console.log('mounted')
  },
  methods: {
    getUsers() {
      this.loading = true
      app.get('users').then(res => {
        if (res.code === 0) {
          this.users = res.data
        }
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    getRoles() {
      app.get('roles').then(res => {
        if (res.code === 0) {
          this.roles = res.data
        }
      })
    },
    addUser() {
      this.user.add.cname = '普通用户'
      this.dialog.add = true
    },
    update(row) {
      this.user.edit = row
      this.user.edit.cname = row.role.cname
      this.dialog.edit = true
    },
    deleteUser(row) {
      this.$confirm('确认删除吗？', '删除', {
        type: 'warning'
      }).then(() => {
        app.post('user_delete', { id: row.id }).then(res => {
          if (res.code === 0) {
            this.$message({
              message: '删除成功',
              type: 'success'
            })
            this.getUsers()
          } else {
            this.$message({
              message: '删除失败：' + res.msg,
              type: 'error'
            })
          }
        })
      })
    },
    addSubmit(user) {
      app.postJSON('user', user).then(res => {
        if (res.code === 0) {
          this.$message({
            message: '新增成功',
            type: 'success'
          })
          this.dialog.add = false
          this.user.add = {}
          this.getUsers()
        } else {
          this.$message({
            message: '新增失败：' + res.msg,
            type: 'error'
          })
        }
      })
    },
    editSubmit(user) {
      app.postJSON('user', user).then(res => {
        if (res.code === 0) {
          this.$message({
            message: '修改成功',
            type: 'success'
          })
          this.dialog.edit = false
          this.user.edit = {}
          this.getUsers()
        } else {
          this.$message({
            message: '修改失败：' + res.msg,
            type: 'error'
          })
        }
      })
    }
  }
}
</script>
