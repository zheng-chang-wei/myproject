<template>
	<div>
		<section id="logManager">
			<!--查询-->
			<el-col :span="24" class="query" style="padding-bottom: 0px;min-width:900px">
				<el-form :inline="true" :rules="retrieveRules" ref="retrieveForm" :model="retrieveForm">
					<el-form-item prop="username">
						<el-input v-model="retrieveForm.username" placeholder="用户名" :maxlength="20"></el-input>
					</el-form-item>
					<el-form-item prop="operation">
						<el-select v-model="retrieveForm.operation" placeholder="请选择">
							<el-option v-for="item in operationOptions" :key="item" :label="item" :value="item">
							</el-option>
						</el-select>
					</el-form-item>
					<!-- <el-form-item prop="ip">
						<el-input v-model="retrieveForm.ip" placeholder="IP地址" :maxlength="50"></el-input>
					</el-form-item> -->
					<el-form-item prop="starttime" label="时间范围">
						<el-date-picker style="width:192px;" v-model="retrieveForm.starttime" type="datetime"
							placeholder="选择日期时间"  :editable="false">
						</el-date-picker>
					</el-form-item>
					<el-form-item prop="endtime" label="-">
						<el-date-picker style="width:192px;" v-model="retrieveForm.endtime" type="datetime"
							placeholder="选择日期时间"  :editable="false">
						</el-date-picker>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" icon="search" @click="retrieveLogs">查询</el-button>
					</el-form-item>
				</el-form>
			</el-col>
			<!--列表-->
			<el-table :data="logs" :border="true" v-loading="listLoading" :height="tableMaxHeight" highlight-current-row
				style="width: 98%;margin-left:15px" @selection-change="selsChange">
				<!-- <el-table-column type="selection" width="55"> </el-table-column> -->
				<el-table-column prop="username" label="用户名" align="center" sortable width="100"> </el-table-column>
				<el-table-column prop="operation" label="行为描述" align="center" sortable width="120"> </el-table-column>
				<el-table-column prop="params" label="参数" align="center" sortable> </el-table-column>
				<el-table-column prop="ip" label="IP地址" align="center" sortable width="120"> </el-table-column>
				<el-table-column prop="createTime" label="创建时间" :formatter="formatCreatetime" align="center" sortable
					width="200">
				</el-table-column>
			</el-table>

			<!--分页  工具条-->
			<el-col :span="24" class="toolbar" style="position:absolute;bottom:20px;right:0">
				<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
					:current-page.sync="currentPage" :page-sizes="[20, 50, 100]" :page-size="pageSize"
					layout="total, sizes, prev, pager, next, jumper" :total="total" style="float: right;">
				</el-pagination>
			</el-col>
		</section>
	</div>
</template>

<script type="text/ecmascript-6">
import app from "common/js/app";
import util from "common/js/util";
export default {
	data() {
		const checkTime = (rule, value, callback) => {
			let sTime = new Date(this.retrieveForm.starttime).getTime(),
				eTime = new Date(value).getTime();
			if (eTime < sTime) {
				callback(new Error("结束时间早于开始时间"));
			} else {
				callback();
			}
		};
		return {
			logs: [],
			total: 0,
			pageNum: 1,
			pageSize: 20,
			currentPage: 1,
			listLoading: false,
			sels: [], //列表选中的选项
			//查询功能数据
			retrieveForm: {
				username: "",
				operation: "所有",
				ip: "",
				starttime: "", //查询时开始时间
				endtime: "" //查询时结束时间
			},
			//查询时验证
			retrieveRules: {
				endtime: [{
					validator: checkTime,
					trigger: "change"
				}]
			},
			operationOptions: ["所有", "登录", "上传文件", "历史数据查询", "删除平台数据", "新增用户", "修改当前用户信息", "修改用户", "删除用户"],
			tableMaxHeight: document.body.offsetHeight - 220,
		};
	},
	mounted() {
		this.getLogs();
		//页面改变时,更改尺寸
		$(window).on("resize", this.changeTableMaxHeight);
		this.changeTableMaxHeight();
	},
	components: {},
	methods: {
		//格式化创建时间
		formatCreatetime(row, column) {
			return (row.createTime = row.createTime ?
				util.formatDate(new Date(row.createTime), "yyyy-MM-dd hh:mm:ss") :
				"");
		},

		//列表选中的选项
		selsChange(sels) {
			this.sels = sels;
		},
		//动态更改表格最大高度
		changeTableMaxHeight() {
			this.tableMaxHeight = document.body.offsetHeight - 220;
			let sectionHeight = document.body.offsetHeight - 80 + "px";
			$("#logManager").css({
				height: sectionHeight
			});
		},
		queryStart(){
			this.currentPage=1
			this.handleCurrentChange(1)
		},
		//查询日志
		retrieveLogs() {
			this.$refs.retrieveForm.validate(valid => {
				if (valid) {
					this.queryStart();
				}
			});
		},
		getLogs() {
			this.listLoading = true;
			let vm = this;
			if (vm.retrieveForm.starttime!==null&&vm.retrieveForm.starttime.length!==0) {
				vm.retrieveForm.starttime = util.formatDate(new Date(vm.retrieveForm.starttime), "yyyy-MM-dd hh:mm:ss")
			}
			if (vm.retrieveForm.endtime!==null&&vm.retrieveForm.endtime.length!==0) {
				vm.retrieveForm.endtime = util.formatDate(new Date(vm.retrieveForm.endtime), "yyyy-MM-dd hh:mm:ss")
			}
			let param = {
				username: vm.retrieveForm.username,
				operation: vm.retrieveForm.operation,
				ip: vm.retrieveForm.ip,
				startTime: vm.retrieveForm.starttime,
				endTime: vm.retrieveForm.endtime,
				pageNum: vm.pageNum,
				pageSize: vm.pageSize
			};
			if (vm.retrieveForm.operation == "所有") {
				param.operation = '';
			}
			app.get("get_log", param).then(d => {
				if (d) {
					vm.logs = d.msg.rows;
					vm.total = d.msg.total;
					this.listLoading = false;
				}
			});
		},
		//批量删除
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
					app.post("delete_log", param).then(data => {
						vm.listLoading = false;
						if (data.code == "0") {
							vm.$message({
								message: data.msg,
								type: "success"
							});
							vm.queryStart();
						}
					});
				})
				.catch(() => {});
		},
		//分页触发
		handleCurrentChange(val) {
			this.pageNum = val;
			this.getLogs();
		},
		//改变页码
		handleSizeChange(val) {
			this.pageSize = val;
			this.getLogs();
		}
	}
};
</script>
<style>
section {
  background-color: white;
  margin-left: 12px;
  margin-right: 12px;
  border: 1px solid #e8e8e8;
}

section#logManager {
  background-color: white;
  margin-left: 12px;
}

#logManager .query {
  padding: 16px 15px 0px;
}

#logManager .query .el-input {
  width: 140px;
}

#logManager .el-dialog__wrapper .el-dialog {
  min-width: 700px;
}

#logManager .toolbar {
  padding: 0px 15px;
  border-bottom: 1px solid transparent;
  border-top-right-radius: 3px;
  border-top-left-radius: 3px;
}

#logManager .el-form-item__label {
  text-align: right;
}

#logManager .el-breadcrumb {
  height: 30px;
  line-height: 30px;
  padding-left: 20px;
}

#logManager .systemColor .el-breadcrumb__item__inner {
  color: #97a8be;
}

#logManager .el-dialog .el-input {
  width: 460px;
}

/* 更改穿梭框样式 */

#logManager .el-transfer .el-checkbox__inner {
  display: none;
}

#logManager .el-transfer .el-checkbox__label {
  padding-left: 0px;
}

#logManager .el-transfer .el-transfer-panel__item {
  padding-left: 0px;
  box-sizing: border-box;
  margin-left: 12px;
}

#logManager .el-checkbox__input.is-checked + .el-checkbox__label {
  background-color: #00a1e9;
  color: white;
  box-sizing: border-box;
  padding-left: 6px;
}

#logManager
  .el-transfer-panel__footer
  .el-checkbox__input.is-checked
  + .el-checkbox__label {
  background-color: white;
  color: #8391a5;
}
</style>