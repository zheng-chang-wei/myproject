<template>
  <div class="edit-dialog">
    <el-form ref="user" :model="user" :rules="rules" label-width="100px">
      <el-form-item label="用户名" prop="username" required>
        <el-input v-model="user.username" auto-complete="off" placeholder="用户名" />
      </el-form-item>
      <el-form-item label="密码" prop="password" required>
        <el-input v-model="user.password" auto-complete="off" placeholder="密码" :type="passwordType" />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <el-form-item label="角色" prop="cname" required>
        <el-select v-model="user.cname" placeholder="请选择角色">
          <el-option v-for="(item) in roles" :key="item.id" :label="item.cname" :value="item.cname" />
        </el-select>
      </el-form-item>
    </el-form>
    <el-button type="primary" @click="submit">确定</el-button>
  </div>
</template>
<script>
export default {
  props: {
    user: {
      type: Object,
      default: null
    },
    roles: {
      type: Array,
      default() {
        return []
      }
    }
  },
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!value || value.length < 1) {
        callback(new Error('请填写用户名'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (!value || value.length < 1) {
        callback(new Error('请填写密码'))
      } else {
        callback()
      }
    }
    const vlidateRole = (rule, value, callback) => {
      console.log(value)
      if (!value) {
        callback(new Error('请选择角色'))
      } else {
        callback()
      }
    }
    return {
      passwordType: 'password',
      rules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }],
        cname: [{ required: true, trigger: 'blur', validator: vlidateRole }]
      }
    }
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    submit() {
      this.$refs.user.validate(valid => {
        if (valid) {
          console.log(this.roles)
          this.user.role = this.getRole(this.user.cname)
          console.log(this.user)
          this.$emit('submit', this.user)
        }
      })
    },
    getRole(cname) {
      for (let i = 0; i < this.roles.length; i++) {
        if (this.roles[i].cname === cname) {
          return this.roles[i]
        }
      }
      return null
    }
  }
}
</script>
<style>
.show-pwd {
    position: absolute;
    right: 10px;
    top: 0px;
    font-size: 16px;
    color: #889aa4;
    cursor: pointer;
    user-select: none;
  }
</style>

