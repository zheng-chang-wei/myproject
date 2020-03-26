<template lang="pug">
	#app
		#top(style="overflow:hidden")
			div.left
			img(src="../assets/qsy-Corp-2.jpg"  )
									
			ul.right
				li
					el-button(type='primary', @click='uploadFileBtnClicked') 上传本地文件
					el-button(type='primary' icon="el-icon-info" @click='checkUploadFileResponse')
				li(style="cursor: pointer")
					span(@click='modifyUserInfo()') 欢迎, {{username}} {{dataObj.roleName}}
				//- li {{dataObj.roleName}}
					//- i.iconfont.icon-admin(style="font-size:60px;color:rgb(217,181,93);margin-left:-27px;margin-top:4px")
				li(style="cursor: pointer")
					.log-out
					i.iconfont.icon-login-out(@click="logout")
		#bottom
			#left-menu
				el-menu(ref="menu" @select="handleSelect" @open="handleOpen" :default-active="menuActive")
					el-menu-item(index="/home" v-show="homePage" style="padding-left:20px !important;")
						i.iconfont(class="icon-home")
						span 首页
					el-menu-item(index="/history/historyData"  style="padding-left:20px !important;")
						i.iconfont(class="el-icon-data-line")
						span 历史数据查阅
					el-menu-item(index="/detection/detectionByCar"  style="padding-left:20px !important;")
						i.iconfont(class="el-icon-first-aid-kit")
						span 轴温异常检测
					template(v-for="(sub, index) in nav")
						el-menu-recur(:root="sub", :index="index+''")
			#router-view
				keep-alive
					router-view(v-if="$route.meta.keepAlive")
				router-view(v-if="!$route.meta.keepAlive")
		el-dialog(title='上传本地文件', width="40%" :visible.sync='dialogVisible', :before-close="handleClose" :close-on-click-modal='false' )
			el-upload(ref='upload', :action='uploadUrl()', :limit="2000" multiple, accept=".xls,.xlsx" :file-list='fileList',:auto-upload="false", :on-change="addFile", :http-request="uploadFile",:before-upload="beforeUpload",:on-exceed="onExceed")
				el-button(slot='trigger', size='small', type='primary') 选取文件
				el-button(style="margin-left: 10px;" size="small" type="success" @click="submitUpload" ) 上传到服务器
				.el-upload__tip(slot='tip') 只能上传excel文件
				.el-upload-list__item-name(slot='tip') {{fileName}}
			span.dialog-footer(slot='footer')
				el-button(type='primary', @click='close') 清空列表数据

		el-dialog(title='上传文件反馈结果', width="40%" :visible.sync='uploadFileResponseDialogVisible', :close-on-click-modal='false' )
			template(v-for="item in uploadFileResponseList")
				el-row(:style="rowStyle(item)") {{item.msg}}

		el-dialog(id="edit" width="47%" title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false" )
			el-tabs(v-model="activeName")
				el-tab-pane(label="修改基本信息" name="first")
					el-form(:rules="editRules2" :model="editForm2" label-width="80px" ref="editForm2" style="margin-left:5%;")
						el-form-item(label="用户名" prop="username"  size="small")
							el-input( v-model="editForm2.username" auto-complete="off" placeholder="请输入用户名" :maxlength="20")
						el-form-item(label="姓名" prop="name"  size="small")
							el-input( v-model="editForm2.name" auto-complete="off" placeholder="请输入姓名" :maxlength="20")
						el-form-item(label="部门名称" prop="deptName"  size="small")
							el-input( v-model="editForm2.deptName" auto-complete="off" placeholder="请输入部门名称" :maxlength="20")
						el-form-item(label="证件号码" prop="idNum"  size="small")
							el-input( v-model="editForm2.idNum" auto-complete="off" placeholder="请输入证件号码" :maxlength="20")
				el-tab-pane(label="修改密码" name="second") 
					el-form(:rules="editRules1" :model="editForm1" label-width="80px" ref="editForm1" style="margin-left:5%;min-width:550px")
						el-form-item(label="旧密码" prop="oldPassword"  size="small")
							el-input( v-model="editForm1.oldPassword" show-password auto-complete="off"  placeholder="请输入密码" :maxlength="20")
						el-form-item(label="新密码" prop="password"  size="small")
							el-input( v-model="editForm1.password" show-password auto-complete="off"  placeholder="请输入密码" :maxlength="20")
						el-form-item(label="确认密码" prop="passwordConfirm"  size="small")
							el-input( v-model="editForm1.passwordConfirm" show-password auto-complete="off"  placeholder="请输入确认密码" :maxlength="20")
			span.dialog-footer(slot='footer')
				el-button(type='primary'  @click='editSubmit' style="width:90px;") 提交
				el-button(type='cancel' @click='editFormVisible = false' style="width:90px;") 取消
</template>
<script>
import app from 'common/js/app'
import apiDomain from 'common/js/apiDomain'
import elMenuRecur from "components/el-menu-recur"
import util from "common/js/util";
let Base64 = require('js-base64').Base64;
export default {
	data: function () {
		//新增&编辑弹框里长度到达限制时提示
		const checkUserName = (rule, value, callback) => {
			if (value.length === 21) {
				callback(new Error("用户名长度限制20字符"));
			} else {
				callback();
			}
		};

		const checkPassWord = (rule, value, callback) => {
			if (value.length === 21) {
				callback(new Error("密码长度限制20字符"));
			} else if (this.editForm1.password != this.editForm1.passwordConfirm && this.editForm1.password != "" && this.editForm1.passwordConfirm != "") {
				callback(new Error("密码不同"));
			} else if (value.length < 6) {
				callback(new Error("密码长度至少为6字符"));
			} else {
				callback();
			}
		};
		return {
			onceUploadCount:5,	//一次上传文件的数量
			fileDataList:[],
			paramList:[],
			menuActive:"",
			uploadFileResponseDialogVisible:false,
			uploadFileResponseList:[],//上传文件反馈，成功或失败
			updateSuccessCount: 0,
			loading: null,
			activeName: "first",
			editLoading: false,
			url: '',
			appDomain: '',
			nav: [],
			dataObj: {},
			username: '',
			roleName: '',
			homePage: true,
			dialogVisible: false,
			fileList: [],
			fileName: '',
			editFormVisible: false, //编辑界面是否显示
			//编辑界面数据
			editForm1: {
				oldPassword: "",
				password: "",
				passwordConfirm: "",
			},
			editForm2: {
				username: "",
				name: "",
				idNum: "",
				deptName: "",
			},
			//编辑弹框表单验证
			editRules1: {
				oldPassword: [{
						required: true,
						message: "必填",
						trigger: "change"
					},

				],
				password: [{
						required: true,
						message: "必填",
						trigger: "change"
					},
					{
						validator: checkPassWord,
						trigger: ["blur", "change"],
					}
				],
				passwordConfirm: [{
						required: true,
						message: "必填",
						trigger: "change"
					},
					{
						validator: checkPassWord,
						trigger: ["blur", "change"],
					}
				],
			},
			editRules2: {
				username: [{
						required: true,
						message: "必填",
						trigger: "change"
					},
					{
						validator: checkUserName,
						trigger: "change"
					}
				],
				name: [{
						required: true,
						message: "必填",
						trigger: "change"
					},
					{
						validator: checkUserName,
						trigger: "change"
					}
				],
				idNum: [{
						required: true,
						message: "必填",
						trigger: "change"
					},
					{
						validator: checkUserName,
						trigger: "change"
					}
				],
				deptName: [{
						required: true,
						message: "必填",
						trigger: "change"
					},
					{
						validator: checkUserName,
						trigger: "change"
					}
				],
			},
		}
	},
	name: 'app',
	components: {
		elMenuRecur
	},

	mounted() {
		this.initMenuActive();
		this.getLeftMenus();
		//监听menu.vue上面的刷新菜单
		this.$root.Bus.$on('refreshMenu', () => {
			this.getLeftMenus();
		})
		this.$root.Bus.$on('updateUserInfo', () => {
			this.getRoleName();
		})
		this.$root.Bus.$on('menuActive', (index) => {
			this.changeMenuActive(index);
		})
		this.$root.Bus.$on('handleError', () => {
			this.closeLoading();
		})
		this.getRoleName();
		this.initWebSocket();
	},
	destroyed() {
		this.closeWebSocket();
		this.$root.Bus.$off('refreshMenu');
		this.$root.Bus.$off('updateUserInfo');
		this.$root.Bus.$off('menuActive');
		this.$root.Bus.$off('handleError');
	},
	
	watch: {
		menuActive: function (val, oldVal) {
			localStorage.setItem('menuActive', val);
		},
	},
	methods: {
		initMenuActive(){
			if (localStorage.getItem('menuActive')!=="null") {
				this.menuActive=localStorage.getItem('menuActive');
			}else{
				this.menuActive="/home"
			}
		},
		initWebSocket() {
			if ("WebSocket" in window) {
				this.websocket = new WebSocket("ws://" + apiDomain.ip + "/websocket");
				//连接错误
				this.websocket.onerror = this.setErrorMessage;
				// 连接成功
				this.websocket.onopen = this.setOnopenMessage;
				//收到消息的回调
				this.websocket.onmessage = this.setOnmessageMessage;
				//连接关闭的回调
				this.websocket.onclose = this.setOncloseMessage;
				//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
				window.onbeforeunload = this.onbeforeunload;
			} else {
				alert("当前浏览器 Not support websocket");
			}
		},
		setErrorMessage() {
		},
		setOnopenMessage() {
			this.send(this.username + "_" + util.uuid());
		},
		setOnmessageMessage(event) {
			const data=JSON.parse(event.data);
			if (data.msg == "已登录") {
				localStorage.setItem('username', '')
				this.closeWebSocket();
				this.closeLoading();
				this.$alert('当前用户从别处登录了', '强制下线', {
					confirmButtonText: '确定',
					callback: action => {
						this.$router.push({
							path: '/login'
						});
					}
				});
			} else if (data.msg == "用户被删除") {
				this.closeLoading();
				this.closeWebSocket();
				this.$alert('当前用户被删除了', '强制下线', {
					confirmButtonText: '确定',
					callback: action => {
						this.$router.push({
							path: '/login'
						});
					}
				});
			}else if (data.msg==='删除成功') {
				this.$root.Bus.$emit("deleteData", data);
			}else  {
				this.handleFileUploadResult(data);
			}
		},
		setOncloseMessage() {
		},
		onbeforeunload() {
			this.websocket.close();
		},
		//websocket发送消息
		send(msg) {
			this.websocket.send(msg);
		},
		closeWebSocket() {
			if (this.websocket) {
				this.websocket.close();
			}
		},

		//退出登录
		logout: function () {
			let vm = this;
			this.$confirm('确认退出登录吗?', '提示', {
				type: 'warning'
			}).then(() => {
				app.post('logOut').then(d => {

				})
			}).catch(() => {})
		},
		//查询左侧菜单
		getLeftMenus() {
			app.get('findLeftTree_menu').then(d => {
				this.nav = d.msg
			})
		},
		getRoleName() {
			this.username = localStorage.getItem('username');
			app.get('find_role_by_userId').then(d => {
				this.dataObj = {}; //真正实现数据更新的是这行代码
				this.dataObj['roleName'] = d.msg;
				localStorage.setItem('roleName', d.msg);
			})
		},
		changeMenuActive(index){
			this.menuActive=index
		},
		handleSelect(index) {
			this.menuActive=index
			this.$router.push(index);
		},
		handleOpen(key, keyPath) {
			if (key === '0') {
				this.$refs.menu.close('1');
			} else {
				this.$refs.menu.close('0');
			}
		},

		//上传文件按钮被点击后，显示上传文件的提示框
		uploadFileBtnClicked() {
			this.dialogVisible = true;
		},
		checkUploadFileResponse(){
			this.uploadFileResponseDialogVisible = true;
		},
		rowStyle(item){
    	return "margin-top:10px;color:" + item.color;
		},
		submitUpload() {
			this.uploadFileResponseList = [];
			this.paramList=[];
			this.fileDataList=[];
			this.updateSuccessCount = 0;
			if (this.fileList.length === 0) {
				this.$message({
					message: '请先选取文件',
					type: 'warning'
				})
			} else {
				this.$refs.upload.submit();
				let vm=this;
				if (this.paramList.length===0) {
					this.closeLoading();
					return;
				}
				this.appendFileAndSendFile(0)
			}
		},
		appendFileAndSendFile(updateSuccessCount) {
			let loopTimes = this.onceUploadCount + updateSuccessCount;
			if (loopTimes > this.fileDataList.length) {
				loopTimes = this.fileDataList.length
			}
			for (let i = updateSuccessCount; i < loopTimes; i++) {
				const fileData = new FormData();
				const element = this.fileDataList[i];
				fileData.append('files', element);
				fileData.append('count', updateSuccessCount)
				app.uploadFile('upload_initialdata_file', fileData).then(data => {}).catch(response => {});
			}
		},
				//上传文件
		uploadFile(param) {
			this.paramList.push(param);
			this.fileDataList.push(param.file);
			
		},
		handleFileUploadResult(data) {
			const msg = data.msg.split(" ");
			let param = null;
			this.paramList.forEach(element => {
				if (msg[0] === element.file.name) {
					param = element;
				}
			});
			if (data.code === 0) {
				this.$message({
					message: data.msg,
					type: "success"
				});
				this.uploadFileResponseList.push({
					color: 'green',
					msg: data.msg
				})
				param.onSuccess();
			} else {
				this.$message({
					message: data.msg,
					type: "error"
				});
				this.uploadFileResponseList.push({
					color: 'red',
					msg: data.msg
				})
				param.onError();
			}
			this.updateSuccessCount++;
			if (this.updateSuccessCount === this.paramList.length) {
				this.closeLoading();
				this.uploadFileResponseDialogVisible = true
			} else if (this.updateSuccessCount % this.onceUploadCount === 0) {
				this.appendFileAndSendFile(this.updateSuccessCount);
			}
		},

		beforeUpload(file) {
			this.loading = this.$loading({
				lock: true,
				text: '文件上传中',
				spinner: 'el-icon-loading',
				background: 'rgba(0, 0, 0, 0.7)'
			});
			const isLt10M = file.size / 1024 / 1024 < 10 //这里做文件大小限制
			if (!isLt10M) {
				this.$message({
					message: '上传文件须小于 10MB!',
					type: 'error'
				});
				// this.updateSuccessCount++;
				this.uploadFileResponseList.push({
					color: 'red',
					msg: file.name + ' 文件大于10M'
				})
			}
			const isGreaterThan110k = file.size / 1024 > 110 //这里做文件大小限制
			if (!isGreaterThan110k) {
				this.$message({
					message: file.name + '上传文件数据量过少!',
					type: 'error'
				});
				// this.updateSuccessCount++;
				this.uploadFileResponseList.push({
					color: 'red',
					msg: file.name + ' 文件数据量过少'
				})
			}
			return isLt10M && isGreaterThan110k
			// return isLt10M 
		},
		onExceed(){
				this.$message({
					message: '文件个数超过2000，请重新选择',
					type: 'error'
				});
		},
		//上传文件的路径
		uploadUrl() {
			return app.getUrl('upload_initialdata_file');
		},
		//显示上传的文件
		addFile(file, fileList) {
			this.fileList = fileList;
		},
		close() {
			this.fileList = [];
		},
		modifyUserInfo() {
			this.editFormVisible = true;
			this.handleEdit();
		},
		//显示编辑弹窗
		handleEdit() {
			let vm = this;
			app.get("get_current_user").then(data => {
				vm.editFormVisible = true;
				let editData = data.msg.tSysUser;
				if (data.msg) {
					vm.editForm1.id = editData.id;
					vm.editForm2.id = editData.id;
					vm.editForm2.username = editData.username;
					vm.editForm2.name = editData.name;
					vm.editForm2.deptName = editData.deptName;
					// vm.editForm1.password = Base64.decode(editData.password);
					// vm.editForm1.passwordConfirm = Base64.decode(editData.password);
					vm.editForm2.idNum = editData.idNum;
				}
			});
		},
		//编辑
		editSubmit() {
			if (this.activeName == "second") {
				this.$refs.editForm1.validate(valid => {
					let vm = this;
					if (valid) {
						this.$confirm("确认提交吗？", "提示", {}).then(() => {
							this.editLoading = true;
							let param = Object.assign({}, vm.editForm1);
							param.password = Base64.encode(param.password);
							param.oldPassword = Base64.encode(param.oldPassword);
							param.passwordConfirm = ''
							app.post("edit_user", param).then(data => {
								vm.editLoading = false;

								if (data.code == "0001") {
									vm.$message({
										message: data.msg,
										type: "error"
									});
								} else {
									vm.$message({
										message: data.msg,
										type: "success"
									});
									vm.$refs["editForm1"].resetFields();
									vm.editFormVisible = false;
									app.post('logOut').then(d => {

									})
								}
							});
						}).catch(() => {});
					}
				});
			} else {
				this.$refs.editForm2.validate(valid => {
					let vm = this;
					if (valid) {
						this.$confirm("确认提交吗？", "提示", {}).then(() => {
							this.editLoading = true;
							let param = Object.assign({}, vm.editForm2);
							app.post("edit_user", param).then(data => {
								vm.editLoading = false;
								if (data.code == "0001") {
									vm.$message({
										message: data.msg,
										type: "error"
									});
								} else {
									vm.$message({
										message: data.msg,
										type: "success"
									});
									vm.username=vm.editForm2.username;
									localStorage.setItem('username', vm.username);
									vm.$router.go(0);
									vm.$refs["editForm2"].resetFields();
									vm.editFormVisible = false;
								}
							});
						}).catch(() => {});
					}
				});
			}

		},
		handleClose(done) {
			this.close();
			done();
		},
		closeLoading(){
			if (this.loading!==null) {
				this.loading.close()
				this.loading=null
			}
		}
	}
}
</script>
<style lang="stylus">
	html, body, #app
		margin 0
		height 100%
		background: #f6f6f8
		font-size: 14px
  #edit .el-input
		width 85%
		
	#top
		flex-shrink 0
		height: 60px
		width: 100%
		line-height: 60px
		border-bottom:1px solid #e6e6e6
		margin-bottom: -1px
		background: #fff
		z-index: 30
		.left
			float: left
			padding-left: 20px
			margin-left: 2px
			color: #0f8ac6
			font-size: 24px
			letter-spacing: 4px
		.right
			float: right
			padding: 0
			margin: 0
			color: #969696
			li
				list-style: none
				float: left
				margin-right: 40px
				padding-left: 30px
				position: relative
			
				&:last-child
					margin-right: 15px
				&:nth-child(3)::before,&:nth-child(4)::before
					width: 1px
					top: 10px
					left: -30px
				&:nth-child(4)::before
					left: -30px
				&:not(:first-child)::before
					display: block
					content:""
					height: 44px
					position: absolute
					background: #e6e6e6
				&:nth-child(2)
					i
						font-size: 24px
						top: -4px
						left: -6px
				.iconfont
					position: absolute
					font-size: 24px
					left:-6px
				.log-out
					background-color:red
					
				
					
	#app
		display flex
		flex-direction column
		overflow hidden
		width 100%
		>#bottom
			display flex
			flex-grow 1
			align-items stretch
			height 100%
			width 100%
			>div
				overflow auto
			>#left-menu
				background linear-gradient(to right, #0a75bb, #14609e)
				flex-shrink 0
				margin-top: 2px
				width 210px
				position relative
				overflow hidden
			>#router-view
				flex-grow 1
				height 100%
				width 100%
				padding-top 10px
				box-sizing border-box
				>div
					height:100%
			//覆盖饿了么菜单树的样式
			>#left-menu>.el-menu
				position absolute
				overflow-y scroll
				top 0
				left 0
				bottom 0
				right -25px
				background transparent
			>#left-menu>.el-menu>.el-submenu>.el-submenu__title
				background-color rgba(255,255,255,0.1)
				margin-bottom 1px
			>#left-menu>.el-menu>.el-menu-item
				background-color rgba(255,255,255,0.1)
				margin-bottom 1px
				height 56px !important
				line-height 56px !important
			>#left-menu>.el-menu>.el-menu-item:hover
				background-color #26b2e7
			>#left-menu>.el-menu>.el-menu-item.is-active
				background-color #26b2e7
			.el-submenu .el-menu
				background transparent
			.el-submenu .el-menu .el-submenu__title
				height 44px !important
				line-height 44px !important
			.el-menu-item
				color #abdef1
				padding-left 60px !important;
				height 44px !important
				line-height 44px !important
				.iconfont
					font-size 20px;
					padding-right 10px;
					color #fff
				span
					color #fff
			.el-submenu__title
				color #fff
			.el-submenu .el-menu-item:hover, .el-submenu__title:hover, .el-submenu .el-menu-item.is-active
				background-color #26b2e7
				color #fff
		
</style>
