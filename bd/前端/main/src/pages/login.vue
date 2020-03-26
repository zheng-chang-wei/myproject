<template>
  <div id="home_page" @keyup.enter="login">
    <header>
      <div class="imagelogo" style="height: 50px" />
    </header>
    <div class="mid">
      <!-- .img-wrapper -->
      <!-- img(src='../assets/logo-bg.png', alt='')  -->
      <el-form ref="ruleForm" class="form-wrapper" :model="ruleForm" :rules="loginRules">
        <h2 class="title">欢迎登录</h2>
        <i v-if="ruleForm.username" class="iconfont icon-Signin-Deleteall" @click="deleteAllUsername" />
        <el-form-item class="form-group" prop="username">
          <img src="../assets/Icon-User name.png" alt="">
          <el-input v-model="ruleForm.username" class="form-input" type="text" auto-complete="off" placeholder="用户名" />
        </el-form-item>
        <el-form-item class="form-group" prop="password">
          <img src="../assets/Icon-Password.png" alt="">
          <el-input v-model="ruleForm.password" type="password" show-password="show-password" auto-complete="off" placeholder="密码" />
        </el-form-item>
        <div class="za">
          <el-checkbox v-model="remeber_Flag" name="rememberMe">记住密码</el-checkbox>
        </div>
        <div class="form-group">
          <input type="button" value="登录" @click="login">
        </div>
      </el-form>
    </div>
    <footer />
  </div>
</template>
<script>
import app from '@/common/js/app'
const Base64 = require('js-base64').Base64
import { setToken } from '@/utils/auth'
export default {
  data() {
    const checkUserName = (rule, value, callback) => {
      if (value.length === 20) {
        callback(new Error('用户名长度限制20字符'))
      } else {
        callback()
      }
    }

    const checkPassWord = (rule, value, callback) => {
      if (value.length === 20) {
        callback(new Error('密码长度限制20字符'))
      } else {
        callback()
      }
    }
    return {
      user_name: '',
      password: '',
      remeber_Flag: false, // 是否记住密码
      logining: false,
      ruleForm: {
        username: '',
        password: ''
      },
      loginAlertInfo: '', // 登陆警示信息
      loginRules: {
        username: [{
          required: true,
          message: '必填',
          trigger: 'submit'
        },
        {
          validator: checkUserName,
          trigger: 'change'
        }
        ],
        password: [{
          required: true,
          message: '必填',
          trigger: 'submit'
        },
        {
          validator: checkPassWord,
          trigger: 'change'
        }
        ]
      }
    }
  },
  mounted() {
    // this.inputPadding()
    this.ifRememberPassword()
  },
  methods: {
    // inputPadding() {
    //   $('.el-input__inner').css({
    //     'padding-left': '50px',
    //     'height': '48px',
    //     'font-size': '16px'
    //   })
    //   $('.el-checkbox__label').css({
    //     'padding-left': '12px',
    //     'color': ' #585858',
    //     'font-size': '16px'
    //   })
    // },
    login() {
      var vm = this
      // 按钮按下后,颜色会改变
      // $('input[type=button]').css('background-color', '#0090d1')
      // setTimeout(function() {
      //   $('input[type=button]').css('background-color', '#00a1e9')
      // }, 500)
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          this.logining = true
          const para = {
            accountname: this.ruleForm.username,
            password: this.ruleForm.password
          }
          para.password = Base64.encode(para.password)
          app.post('logIn', para).then(data => {
            vm.initMenus()
          }).catch(e => {
            vm.logining = false
          })
        }
      })
    },
    initMenus() {
      app.get('findLeftTree_menu').then(data => {
        if (data.code === 0) {
          const msg = data.msg
          const urls = []
          for (var i = 0; i < msg.length; i++) {
            urls.push(msg[i].url)
          }
          localStorage.setItem('urls', urls)
          this.logining = false
          setToken(this.ruleForm.username)
          this.getRoleName()
          localStorage.setItem('username', this.ruleForm.username)
          this.rememberPassword()
        }
      })
    },
    rememberPassword() {
      const vm = this
      // 点提交时,设置了记住密码
      if (vm.remeber_Flag) {
        localStorage.setItem('password', Base64.encode(vm.ruleForm.password))
      } else {
        localStorage.removeItem('password')
      }
    },
    ifRememberPassword() {
      const vm = this
      // 进入页面,判断是否存在账户密码,有就直接填充
      if (localStorage.getItem('username') && localStorage.getItem('password')) {
        vm.ruleForm.username = localStorage.getItem('username')
        vm.ruleForm.password = Base64.decode(localStorage.getItem('password'))
        vm.remeber_Flag = true
      }
    },
    forgetPassword() {},
    showLoginAlertBox(text) {
      const vm = this
      vm.loginAlertInfo = text
    },
    deleteAllUsername() {
      this.ruleForm.username = ''
    },
    getRoleName() {
      app.get('find_role_by_userId').then(d => {
        localStorage.setItem('roleName', d.msg)
        this.$router.push({
          path: './dashboard'
        })
      })
    }
  }
}

</script>
<style>
	#home_page {
		background-color: #fff;
		font-family: '微软雅黑';
		height: 100%;
		overflow: hidden;
	}

	@media screen and (max-width: 1000px) {
		#home_page {
			min-height: 950px;
		}
	}

	#home_page header {
		padding: 0 10%;
		background-color: #eef9fd;
	}

	#home_page header .logo-title {
		margin: 15px 0;
	}

	@media screen and (max-width: 414px) {
		#home_page header .logo-title {
			margin: 10px 0;
			width: 100%;
		}
	}

	#home_page .mid {
		height: calc(100% - 120px);
		/* background-color: #c0e6f8; */
		background-color: #eef9fd;
		background-size: 100% 100%;
		position: relative;
	}

	#home_page .mid .img-wrapper {
		width: 100%;
		height: 100%;
		background-color: rgba(26, 26, 26, 0.11);
	}

	#home_page .mid .img-wrapper img {
		width: 575px;
		height: 417px;
		position: absolute;
		left: calc(5% - 50px);
		top: calc(30% - 70px);
	}

	@media screen and (max-width: 1000px) {
		#home_page .mid .img-wrapper img {
			width: 383px;
			height: 278px;
			top: 700px;
			left: calc(50% - 240px);
		}
	}

	#home_page .mid .form-wrapper {
		position: absolute;
		top: calc(30% - 120px);
		right: 35%;
		border: 11px solid #d9f0f9;
		width: 414px;
		height: 442px;
		box-sizing: border-box;
		background-color: #fff;
		padding-bottom: 20px;
	}

	@media screen and (max-width: 1000px) {
		#home_page .mid .form-wrapper {
			right: 50%;
			transform: translateX(50%);
			top: 350px;
		}
	}

	@media screen and (max-width: 414px) {
		#home_page .mid .form-wrapper {
			width: 100%;
		}
	}

	#home_page .mid .form-wrapper .iconfont {
		font-size: 24px;
		color: #b5c4c9;
	}

	#home_page .mid .form-wrapper .iconfont.icon-Signin-Deleteall {
		position: absolute;
		right: 34px;
		top: auto;
		font-size: 43px;
		z-index: 2;
		cursor: pointer;
	}

	#home_page .mid .form-wrapper h2 {
		display: block;
		height: 68px;
		line-height: 68px;
		text-align: center;
		box-sizing: border-box;
		border-bottom: 2px solid #c0e6f8;
		padding-bottom: 10px;
		margin-top: 0;
		margin-bottom: 40px;
		font-size: 18px;
		color: #6c6c6c;
		font-weight: normal;
	}

	#home_page .mid .form-wrapper .za {
		padding: 0 34px;
		margin: 0 auto;
		margin-top: 32px;
		margin-bottom: 40px;
		text-align: left;
		line-height: 26px;
		font-size: 20px;
	}

	#home_page .mid .form-wrapper .za input {
		float: left;
	}

	#home_page .mid .form-wrapper .za span {
		/* float: right; */
		cursor: pointer;
	}

	#home_page .mid .form-wrapper .form-group {
		margin: 0 auto;
		border-radius: 3px;
		margin-bottom: 22px;
		position: relative;
		padding: 0 34px;
	}

	#home_page .mid .form-wrapper .form-group i {
		position: absolute;
		left: -29px;
		top: 50%;
		transform: translateY(-50%);
		z-index: 1;
	}

	#home_page .mid .form-wrapper .form-group input {
		box-sizing: border-box;
		/* border: 1px; */
		width: 100%;
		height: 48px;
		background: none;
		padding-left: 50px;
		padding-right: 10px;
		font-size: 16px;
		outline: 1px;
	}

	#home_page .mid .form-wrapper .form-group input[type=button] {
		padding: 0;
		cursor: pointer;
		height: 44px;
		font-size: 20px;
		text-align: center;
		background-color: #00a1e9;
		color: #fff;
		border-color: #20a0ff;
		border-radius: 4px;
		letter-spacing: 4px;
	}

	#home_page .mid .form-wrapper .form-group img {
		position: absolute;
		top: 12px;
		left: 12px;
		z-index: 1;
	}

	#home_page footer {
		display: block;
		height: 67px;
		line-height: 67px;
		text-align: center;
		background-color: #eef9fd;
		color: #5c5d5d;
		font-size: 16px;
	}
	.el-checkbox__label{
		padding-left: 12px;
		color: #585858;
		font-size: 16px
	}
</style>
