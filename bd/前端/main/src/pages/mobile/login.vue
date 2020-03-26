<template>
  <div class="login">
    <h2>博得工单App</h2>
    <van-cell-group>
      <van-field v-model="username" left-icon="manager" placeholder="请输入用户名" right-icon="close" @click-right-icon="clearText" />
      <van-field
        v-model="password"
        :type="visible?'text':'password'"
        left-icon="lock"
        placeholder="请输入密码"
        :right-icon="visible?'eye-o':'closed-eye'"
        @click-right-icon="visible=!visible"
      />
    </van-cell-group>
    <van-button class="login-button" type="info" @click="login">登录</van-button>
  </div>
</template>
<script>
import app from '@/common/js/app'
const Base64 = require('js-base64').Base64
export default {
  name: 'Login',
  data() {
    return {
      username: '',
      password: '',
      visible: false
    }
  },
  created() {
    this.username = localStorage.getItem('username')
  },
  methods: {
    login() {
      var vm = this
      if (this.validate()) {
        const param = {
          accountname: this.username,
          password: this.password
        }
        param.password = Base64.encode(param.password)
        app.post('logIn', param).then(data => {
          localStorage.setItem('username', vm.username)
          vm.$router.push({
            path: '/mobile/main'
          })
        }).catch(() => {
          vm.$toast('登录失败')
        })
      }
    },
    validate() {
      if (this.username === null || this.username === '') {
        this.$toast('请填写用户名')
        return false
      } else if (this.password === null || this.password === '') {
        this.$toast('请填写密码')
        return false
      }
      return true
    },
    clearText() {
      this.username = ''
      this.password = ''
    }
  }
}

</script>
<style>
  .login-button {
    margin-top: 20px;
    width: 100px;
  }

  .login {
    position: relative;
    text-align: center;
  }

</style>
