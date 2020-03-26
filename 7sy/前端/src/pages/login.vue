<template lang='pug'>
#home_page(@keyup.enter='login')
	header
			.imagelogo
				img.logo-title(src='../assets/qsy-Corp-1.jpg')
			
	.mid
		.img-wrapper
		el-form.form-wrapper(:model='ruleForm', ref='ruleForm' :rules="loginRules")
			h2.title 欢迎登录
			i.iconfont.icon-Signin-Deleteall(v-if='ruleForm.username', @click='deleteAllUsername')
			el-form-item.form-group(prop='username' )
				img(src='../assets/Icon-User name.png', alt='')
				el-input.form-input(type='text', v-model='ruleForm.username', auto-complete='off', placeholder='用户名')
			el-form-item.form-group(prop='password' )
				img(src='../assets/Icon-Password.png', alt='')
				el-input(type='password', v-model='ruleForm.password', show-password auto-complete='off', placeholder='密码')
			.za
				el-checkbox(v-model='remeber_Flag', name='rememberMe') 记住密码
			.form-group
				input(type='button', value='登录', @click='login')
		.loginAlertBox(v-text='loginAlertInfo', v-if='showLoginAlertBoxFlg')
	footer 戚墅堰厂机车运用数据分析诊断软件V1.1.4
</template>
<script>
import app from 'common/js/app'
let Base64 = require('js-base64').Base64;
import appApi from '../common/js/allApi.js'
export default {
	data() {
		const checkUserName = (rule, value, callback) => {
			if (value.length === 20) {
				callback(new Error("用户名长度限制20字符"));
			} else {
				callback();
			}
		};

		const checkPassWord = (rule, value, callback) => {
			if (value.length === 20) {
				callback(new Error("密码长度限制20字符"));
			} else {
				callback();
			}
		};
		return {
			user_name: '',
			password: '',
			remeber_Flag: false, // 是否记住密码
			logining: false,
			ruleForm: {
				username: '',
				password: ''
			},
			loginAlertInfo: '', //登陆警示信息
			showLoginAlertBoxFlg: false, //显示登录错误信息
			loginRules: {
				username: [{
						required: true,
						message: "必填",
						trigger: "submit"
					},
					{
						validator: checkUserName,
						trigger: "change"
					}
				],
				password: [{
						required: true,
						message: "必填",
						trigger: "submit"
					},
					{
						validator: checkPassWord,
						trigger: "change"
					}
				]
			},
		}
	},
	mounted() {
		this.inputPadding();
		this.ifRememberPassword();
	},
	methods: {
	
		inputPadding() {
			$('.el-input__inner').css({
				"padding-left": "50px",
				"height": "48px",
				"font-size": "16px"
			});
			$('.el-checkbox__label').css({
				"font-size": "14px",
				"padding-left": "12px",
				"color": " #585858",
				"font-size": "16px"
			});
		},
		login() {
			let vm = this;
			//按钮按下后,颜色会改变
			$('input[type=button]').css("background-color", "#0090d1");
			setTimeout(function () {
				$('input[type=button]').css("background-color", "#00a1e9");
			}, 500);
			this.$refs.ruleForm.validate(valid => {
				if (valid) {
					this.logining = true;
					let para = {
						accountname: this.ruleForm.username,
						password: this.ruleForm.password
					};
					para.password = Base64.encode(para.password);
					app.post('logIn', para).then(data => {
						vm.logining = false;
						vm.$router.push({
							path: './main'
						});
						localStorage.setItem('menuActive', null);
						localStorage.setItem('username', vm.ruleForm.username)
						vm.rememberPassword();
					}).catch(e => {
						vm.logining = false;
						if (e === 'Locked') {
							// vm.showLoginAlertBox('该账号被禁用，请更换其他账号');
						} else {
							// vm.showLoginAlertBox('用户名或密码错误，请重新输入');
						}
					})
				}
			});
		},
		rememberPassword() {
			let vm = this;
			//点提交时,设置了记住密码
			if (vm.remeber_Flag) {
				localStorage.setItem('password', Base64.encode(vm.ruleForm.password))
			} else {
				localStorage.removeItem('password')
			}
		},
		ifRememberPassword() {
			let vm = this;
			//进入页面,判断是否存在账户密码,有就直接填充
			if (localStorage.getItem('username') && localStorage.getItem('password')) {
				vm.ruleForm.username = localStorage.getItem('username');
				vm.ruleForm.password = Base64.decode(localStorage.getItem('password'));
				vm.remeber_Flag = true;
			}
		},
		forgetPassword() {},
		showLoginAlertBox(text) {
			let vm = this;
			vm.showLoginAlertBoxFlg = true;
			vm.loginAlertInfo = text;
		},
		deleteAllUsername() {
			this.ruleForm.username = "";
		}
	}
}
</script>
<style scoped lang="stylus">
@import '../common/stylus/utils'
#home_page
	background-color white
	font-family: '微软雅黑';
	heights 100% 
	overflow hidden
	@media screen and (max-width: 1000px)
		min-height 950px;
	header
		padding 0 3%	
		.logochara
			font-size: 20px;
			font-weight:bold 
		.logo-title
			margin-left 15px
			margin-top 10px
			vertical-align middle
			@media screen and (max-width: 414px)
				margin: 10px 0;
				width 100%
	.mid
		height: calc(100% - 120px);
		background-color: #c0e6f8;
		background:url(../assets/Page-Login-5.jpg);
		background-size: 100% 100%; 
		position: relative;
		.img-wrapper
			width: 100%;
			height: 100%;
			background-color: rgba(26, 26, 26, 0.11);
			img
				width: 575px;
				height: 417px;
				absolute: left calc(5% - 50px) top calc(30% - 70px)
				@media screen and (max-width: 1000px)
					width: 383px;
					height: 278px;
					top 60px
					left calc(50% - 240px)
		.form-wrapper
			absolute: top calc(30% - 120px) right 8%
			@media screen and (max-width: 1000px)
				right 50%
				transform translateX(50%)
				top 350px
			border: 11px solid #d9f0f9;
			width: 414px;
			@media screen and (max-width: 414px)
				width 100%
			height: 442px;
			box-sizing: border-box;
			background-color: #fff;
			padding-bottom: 20px;
			.iconfont
				font-size: 24px;
				color: #b5c4c9;
			.iconfont.icon-Signin-Deleteall
				absolute right 34px top auto
				font-size: 43px;
				z-index: 2;
				cursor: pointer;
			h2
				block-line 68px
				box-sizing: border-box;
				border-bottom: 2px solid #c0e6f8;
				padding-bottom: 10px;
				margin-top: 0;
				margin-bottom: 40px;
				font-size: 18px;
				color: #6c6c6c;
				font-weight: normal;
			.za
				padding: 0 34px;
				margin: 0 auto;
				margin-top: 32px;
				margin-bottom: 40px;
				text-align: left;
				line-height: 26px;
				font-size: 20px;
				input
					float: left;
				span
					float: right;
					cursor: pointer;
			.form-group
				margin: 0 auto;
				border-radius: 3px;
				margin-bottom: 22px;
				position: relative;
				padding: 0 34px;
				i
					absolute left 10px top 50%
					transform: translateY(-50%);
					z-index: 1;
				input
					box-sizing: border-box;
					border: none;
					width: 100%;
					height: 100%;
					background: none;
					padding-left: 50px;
					padding-right: 10px;
					font-size: 16px;
					outline: none;
				input[type=button]
					padding: 0;
					cursor: pointer;
					height: 44px;
					font-size: 20px;
					text-align: center;
					colors #00a1e9 #fff
					border-color: #20a0ff;
					border-radius: 4px;
					letter-spacing: 4px;
				img
					absolute top 12px left 12px
					z-index: 1;
		.loginAlertBox
			width: 324px;
			height: 46px;
			color: #e44c4c;
			line-height: 46px;
			font-size: 14px;
			text-align: left;
			border: 1px solid #ebccd1;
			border-radius: 4px;
			background: #ffeaea 17px 15px url('../assets/login-alertbox.png') no-repeat;
			absolute top calc(30% - 54px) right 148px
			padding-left: 50px;
			box-sizing: border-box;
	footer
		block-line 67px
		colors #eef9fd #5c5d5d
		font-size: 16px;
</style>