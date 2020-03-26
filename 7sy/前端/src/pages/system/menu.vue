<template>
	<div>
		<section id="menuManager">
			<nav-bar :titles="['系统管理', '菜单管理']"></nav-bar>
			<!--工具条-->
			<el-col :span="24" class="toolbar" style="padding-bottom: 0px;padding-top:16px">
				<el-form :inline="true">
					<el-form-item>
						<el-button type="primary" @click="handleAdd('addForm')" v-show="btnArr[0]=='1'">
							<i class="iconfont icon-add"></i>新增</el-button>
						<el-button type="primary" @click="handleEdit" v-show="btnArr[1]=='1'">
							<i class="iconfont icon-edit"></i>编辑</el-button>
						<el-button type="danger" @click="batchRemove" v-show="btnArr[2]=='1'">
							<i class="iconfont icon-delete"></i>删除</el-button>
					</el-form-item>
				</el-form>
			</el-col>

			<!--列表 树-->
			<!-- <tree-grid style="width: 98%;margin-left:15px;overflow:auto;border-bottom: 1px solid #dfe6ec;" :table-max-height="tableMaxHeight" @selsList="checkedChange" ref='tree' :columns="columns" :tree-structure="true" :data-source="dataSource"></tree-grid> -->

			<el-table :data="dataSource" style="width: 98%;margin-bottom: 20px;margin-left:15px;" border row-key="id"
				@selection-change="checkedChange">
				<el-table-column type="selection" width="55"> </el-table-column>
				<el-table-column prop="name" label="菜单名称" sortable>
				</el-table-column>
				<el-table-column prop="perms" label="菜单标识" sortable>
				</el-table-column>
				<el-table-column prop="url" label="菜单url" sortable>
				</el-table-column>
				<el-table-column prop="parentId" label="上级菜单" sortable>
				</el-table-column>
			</el-table>

			<!--新增界面-->
			<el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
				<el-form :rules="addRules" :model="addForm" label-width="100px" ref="addForm"
					style="width:80%;margin-left:5%">
					<el-form-item label="菜单名称" prop="name" required>
						<el-input v-model="addForm.name" auto-complete="off" placeholder="请输入菜单名称" :maxlength="20">
						</el-input>
					</el-form-item>
					<el-form-item label="菜单标识" prop="perms">
						<el-input v-model="addForm.perms" auto-complete="off" placeholder="请输入菜单标识" :maxlength="20">
						</el-input>
					</el-form-item>
					<el-form-item label="菜单url" prop="url">
						<el-input v-model="addForm.url" auto-complete="off" placeholder="请输入菜单url" :maxlength="30">
						</el-input>
					</el-form-item>
					<el-form-item label="上级菜单" prop="parentId">
						<el-select v-model="addForm.parentId" placeholder="请选择">
							<el-option v-for="(item,index) in parentMenuOptions" :key="item.value" :label="item.label"
								:value="item.value" v-bind:style="optionMarginLeft(item,index)">
							</el-option>
						</el-select>
					</el-form-item>

					<el-form-item label="菜单类型" prop="type">
						<el-select v-model="addForm.type" placeholder="请选择">
							<el-option v-for="item in menuOptions" :key="item.value" :label="item.label"
								:value="item.value">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="图标" prop="icon">
						<el-select v-model="addForm.icon" placeholder="请选择图标">
							<el-option v-for="item in iconOptions" :key="item.value" :label="item.label"
								:value="item.value">
							</el-option>
						</el-select>
					</el-form-item>

				</el-form>
				<div slot="footer" class="dialog-footer">
					<el-button type="primary" @click.native="addSubmit" :loading="addLoading" style="width:90px;">提交
					</el-button>
					<el-button type="cancel" @click.native="addFormVisible = false" style="width:90px;">取消</el-button>
				</div>
			</el-dialog>

			<!--编辑界面-->
			<el-dialog title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false">
				<el-form :rules="editRules" :model="editForm" label-width="100px" ref="editForm"
					style="width:80%;margin-left:5%">
					<el-form-item label="菜单名称" prop="name" required>
						<el-input v-model="editForm.name" auto-complete="off" placeholder="请输入菜单名称" :maxlength="20">
						</el-input>
					</el-form-item>
					<el-form-item label="菜单标识" prop="perms">
						<el-input v-model="editForm.perms" auto-complete="off" placeholder="请输入菜单标识" :maxlength="20">
						</el-input>
					</el-form-item>
					<el-form-item label="菜单url" prop="url">
						<el-input v-model="editForm.url" auto-complete="off" placeholder="请输入菜单url" :maxlength="30">
						</el-input>
					</el-form-item>
					<el-form-item label="上级菜单" prop="parentId">
						<el-select v-model="editForm.parentId" placeholder="请选择">
							<el-option v-for="(item,index) in parentMenuOptions" :key="item.value" :label="item.label"
								:value="item.value" v-bind:style="optionMarginLeft(item,index)">
							</el-option>
						</el-select>
					</el-form-item>

					<el-form-item label="菜单类型" prop="type">
						<el-select v-model="editForm.type" placeholder="请选择">
							<el-option v-for="item in menuOptions" :key="item.value" :label="item.label"
								:value="item.value">
							</el-option>
						</el-select>
					</el-form-item>

					<el-form-item label="图标" prop="icon">
						<el-select v-model="editForm.icon" placeholder="请选择图标">
							<el-option v-for="item in iconOptions" :key="item.value" :label="item.label"
								:value="item.value">
							</el-option>
						</el-select>
					</el-form-item>

				</el-form>
				<div slot="footer" class="dialog-footer">
					<el-button type="primary" @click.native="editSubmit" :loading="editLoading" style="width:90px;">提交
					</el-button>
					<el-button type="cancel" @click.native="editFormVisible = false" style="width:90px;">取消</el-button>
				</div>
			</el-dialog>

		</section>
	</div>
</template>

<script type="text/ecmascript-6">
import appApi from "../../common/js/allApi.js";
// import NavBar from "components/nav-bar";
import Vue from "vue";
import app from "common/js/app";
export default {
	data() {
		//新增&编辑弹框里长度到达限制时提示
		const checkMenuName = (rule, value, callback) => {
			if (value.length === 20) {
				callback(new Error("菜单名称长度限制20字符"));
			} else {
				callback();
			}
		};
		const checkMenuReskey = (rule, value, callback) => {
			if (value.length === 20) {
				callback(new Error("菜单标识限制20字符"));
			} else {
				callback();
			}
		};
		const checkMenuResurl = (rule, value, callback) => {
			if (value.length === 30) {
				callback(new Error("菜单url限制30字符"));
			} else {
				callback();
			}
		};
		return {
			colorRed: "red",
			menus: [],
			inputUsername: "",
			listLoading: false,
			sels: [], //列表选中的选项
			//树上的值
			columns: [{
					text: "菜单名称",
					dataIndex: "name"
				},
				{
					text: "菜单类型",
					dataIndex: "type"
				},
				{
					text: "唯一KEY",
					dataIndex: "perms"
				},
				{
					text: "URL地址",
					dataIndex: "url"
				}
			],
			dataSource: [],
			//上级菜单选项
			parentMenuOptions: [{
				value: "0",
				label: "------顶级目录------",
				level: "0"
			}],
			//菜单类型选项
			menuOptions: [{
					value: "0",
					label: "------  目录  ------"
				},
				{
					value: "1",
					label: "------  菜单  ------"
				}
			],
			//图标选项
			iconOptions: [{
					value: " ",
					label: "清除图标"
				},
				{
					value: "icon-home",
					label: "首页 icon-home"
				},
				{
					value: "icon-Enterprisemanagemen",
					label: "企业管理 icon-Enterprisemanagemen"
				},
				{
					value: "el-icon-s-data",
					label: "统计 el-icon-s-data"
				},
				{
					value: "icon-system",
					label: "系统管理 icon-system"
				}
			],
			//新增界面数据
			addForm: {
				name: "",
				perms: "",
				url: "",
				parentId: "0",
				type: "0",
				icon: ""
			},
			addFormVisible: false, //新增界面是否显示
			addLoading: false,
			//编辑界面数据
			editForm: {
				name: "",
				perms: "",
				url: "",
				parentId: "0",
				type: "0",
				icon: ""
			},
			editFormVisible: false, //编辑界面是否显示
			editLoading: false,

			statusCode: "111", //用于新增,编辑,删除按钮的显示隐藏,默认111全显示
			btnArr: ["1", "1", "1"], //把楼上的分割成数组
			//新增弹框表单验证
			addRules: {
				name: [{
						required: true,
						message: "必填",
						trigger: "blur"
					},
					{
						validator: checkMenuName,
						trigger: "change"
					}
				]
			},
			//编辑弹框表单验证
			editRules: {
				name: [{
						required: true,
						message: "必填",
						trigger: "blur"
					},
					{
						validator: checkMenuName,
						trigger: "change"
					}
				]
			},
			tableMaxHeight: null
		};
	},
	components: {
		// NavBar
	},
	mounted() {
		this.getMenus();
		//页面改变时,更改尺寸
		$(window).on("resize", this.changeTableMaxHeight);
		this.changeTableMaxHeight();
	},
	methods: {
		checkedChange(sels) {
			this.sels = sels;
		},
		//动态更改表格最大高度
		changeTableMaxHeight() {
			this.tableMaxHeight = {
				"max-height": document.body.offsetHeight - 300 + "px"
			};
			let sectionHeight = document.body.offsetHeight - 162 + "px";
			$("#menuManager").css({
				height: sectionHeight
			});
		},
		//查询菜单

		getMenus() {
			let vm = this;
			app.get("get_menu").then(data => {
				if (data) {
					vm.dataSource = data.msg;
					vm.getParentMenu();
					// vm.buttonShowOrHide();
				}
			});
		},
		//根据statusCode判断新增,显示,删除按钮的显示隐藏
		buttonShowOrHide() {
			let vm = this;
			vm.btnArr = [];
			vm.btnArr = Array.from(vm.statusCode);
		},
		//菜单选项空格
		optionMarginLeft(item, index) {
			return "margin-left:" + 14 * item.level + "px";
		},
		//显示新增弹窗
		handleAdd() {
			let vm = this;
			setTimeout(() => {
				if (vm.$refs.addForm) vm.$refs.addForm.resetFields();
			}, 50);
			// this.$refs[addForm].resetFields();
			this.addFormVisible = true;
			this.addForm = {
				name: "",
				perms: "",
				url: "",
				parentId: "0",
				type: "0",
				icon: ""
			};
		},
		//获取上级菜单选项
		getParentMenu() {
			let vm = this;
			//用于取出格式化后的数据
			function DataTransfer(data) {
				if (!(this instanceof DataTransfer)) {
					return new DataTransfer(data, null, null);
				}
			}

			DataTransfer.treeToArray = function (data, parent, level) {
				let tmp = [];
				// console.log(data)
				Array.from(data).forEach(function (record) {
					if (parent) {
						Vue.set(record, "_parent", parent);
					}
					let _level = 0;
					if (level !== undefined && level !== null) {
						_level = level + 1;
					}
					Vue.set(record, "_level", _level);
					tmp.push(record);
					if (record.children && record.children.length > 0) {
						let children = DataTransfer.treeToArray(
							record.children,
							record,
							_level
						);
						tmp = tmp.concat(children);
					}
				});
				return tmp;
			};
			let data = DataTransfer.treeToArray(vm.dataSource, null, null);
			vm.parentMenuOptions = [];
			//提取data,渲染selet选项
			data.forEach(item => {
				vm.parentMenuOptions.push({
					value: item.id,
					label: item.name,
					level: item._level
				});
			});
			vm.parentMenuOptions = [{
				value: "0",
				label: "------顶级目录------",
				level: "0"
			}].concat(vm.parentMenuOptions);
		},
		//新增
		addSubmit() {
			this.$refs.addForm.validate(valid => {
				let vm = this;
				if (valid) {
					this.$confirm("确认提交吗？", "提交", {
						type: "warning"
					}).then(
						() => {
							let para = Object.assign({}, vm.addForm);

							//判断菜单名称,菜单标识,菜单url不能为空
							if (para.name == "") {
								vm.$message({
									message: "菜单名称不能为空",
									type: "warning"
								});
								return;
							}
							//   if (para.perms == "") {
							//     vm.$message({
							//       message: "菜单标识不能为空",
							//       type: "warning"
							//     });
							//     return;
							//   }
							//   if (para.url == "") {
							//     vm.$message({
							//       message: "菜单url不能为空",
							//       type: "warning"
							//     });
							//     return;
							//   }
							vm.addLoading = true;
							app.post("add_menu", para).then(data => {
								if (data) {
									vm.addLoading = false;
									vm.$message({
										message: data.msg,
										type: "success"
									});
									vm.$refs["addForm"].resetFields();
									vm.addFormVisible = false;
									//菜单发生变化,触发main.vue中的事件
									vm.$root.Bus.$emit("refreshMenu", 123);
									vm.getMenus();
								}
							});
						}
					).catch(() => {});
				}
			});
		},
		//显示编辑界面
		handleEdit() {
			let vm = this;
			setTimeout(() => {
				if (vm.$refs.editForm) {
					// vm.$refs.editForm.resetFields();
				}
			}, 50);
			//进行判断,只能选择一个进行编辑
			if (this.sels.length == 0) {
				this.$message("请勾选一个用户");
				return;
			}
			if (this.sels.length > 1) {
				this.$message("只能选择一个用户");
				return;
			}
			this.editForm = {
				name: "",
				perms: "",
				url: "",
				parentId: "0",
				type: "0",
				icon: ""
			};

			vm.editFormVisible = true;
			vm.editForm.id = vm.sels[0].id;
			vm.editForm.name = vm.sels[0].name;
			vm.editForm.perms = vm.sels[0].perms;
			vm.editForm.url = vm.sels[0].url;
			vm.editForm.parentId =
				vm.sels[0].parentId !== 0 ? vm.sels[0].parentId : "0";
			vm.editForm.type = vm.sels[0].type;
			vm.editForm.icon = vm.sels[0].icon;
		},
		//编辑
		editSubmit() {
			let vm = this;
			this.$refs.editForm.validate(valid => {
				if (valid) {
					this.$confirm("确认提交吗？", "提示", {
						type: "warning"
					}).then(
						() => {
							this.editLoading = true;

							let param = Object.assign({}, vm.editForm);
							app.post("update_menu", param).then(data => {
								if (data) {
									vm.editLoading = false;

									vm.$message({
										message: data.msg,
										type: "success"
									});
									vm.$refs["editForm"].resetFields();
									vm.editFormVisible = false;
									//菜单发生变化,触发main.vue中的事件
									vm.$root.Bus.$emit("refreshMenu", 123);
									vm.getMenus();
								}
							});
						}
					).catch(() => {});
				}
			});
		},
		//删除
		batchRemove() {
			let vm = this;
			let ids = this.sels.map(item => item.id).toString();

			if (this.sels.length == 0) {
				this.$message({
					message: "请至少勾选一个用户删除",
					type: "error"
				});
				return;
			}

			this.$confirm("确认删除选中记录？", "提示", {
					type: "warning"
				})
				.then(() => {
					this.listLoading = true;

					let param = {
						ids: ids
					};
					app.post("delete_menu", param).then(data => {
						if (data) {
							vm.listLoading = false;

							vm.getMenus();
							vm.$message({
								message: "删除成功",
								type: "success"
							});
							//菜单发生变化,触发main.vue中的事件
							vm.$root.Bus.$emit("refreshMenu", 123);
						}
					});
				})
				.catch(() => {});
		},
		//选择按钮类型时,向描述中增加内容
		showDescrip(flg) {
			let vm = this;
			switch (flg) {
				case 1:
					vm.addForm.description = vm.$refs.addButton.innerHTML;
					break;
				case 2:
					vm.addForm.description = vm.$refs.editButton.innerHTML;
					break;
				default:
					vm.addForm.description = vm.$refs.deleteButton.innerHTML;
			}
		},
		//选择按钮类型时,向描述中修改内容
		updateDescrip(flg) {
			let vm = this;
			switch (flg) {
				case 1:
					vm.editForm.description = vm.$refs.addButton2.innerHTML;
					break;
				case 2:
					vm.editForm.description = vm.$refs.editButton2.innerHTML;
					break;
				default:
					vm.editForm.description = vm.$refs.deleteButton2.innerHTML;
			}
		}
	}
};
</script>

<style>
section#menuManager {
  background-color: white;
  margin-left: 12px;
  margin-right: 12px;
  height: 82vh;
  border: 1px solid #e8e8e8;
}

#menuManager .query {
  padding: 10px 15px 0px;
}

#menuManager .toolbar {
  padding: 0px 15px;
  border-bottom: 1px solid transparent;
  border-top-right-radius: 3px;
  border-top-left-radius: 3px;
}

#menuManager .el-form-item__label {
  text-align: right;
}

#menuManager .el-breadcrumb {
  height: 30px;
  line-height: 30px;
  padding-left: 20px;
}

#menuManager .systemColor .el-breadcrumb__item__inner {
  color: #97a8be;
}

#menuManager .el-select {
  width: 100%;
}

#menuManager .el-dialog__wrapper .el-dialog__body {
  max-height: 410px;
}
#menuManager .el-table::before {
  background-color: transparent;
}
</style>