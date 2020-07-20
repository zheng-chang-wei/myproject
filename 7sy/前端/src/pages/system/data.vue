<template>
	<div>
		<section id="dataManager">
			<!--查询-->
			<el-col :span="24" class="query" style="padding-bottom: 0px;min-width:900px">
				<el-form :inline="true" :rules="retrieveRules" ref="retrieveForm" :model="retrieveForm">
					<el-form-item label="车型" prop="type">
						<el-select v-model="retrieveForm.type" style="width:150px;" placeholder="请选择"
							@change="selectedTypeChange">
							<el-option v-for="item in typeOptions" :key="item.value" :label="item.label"
								:value="item.value"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="车号" prop="num">
						<el-select v-model="retrieveForm.num" style="width:150px;" placeholder="请选择">
							<el-option v-for="item in numOptions" :key="item.value" :label="item.label"
								:value="item.value"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" icon="search" @click="queryStart(1)">查询</el-button>
					</el-form-item>
				</el-form>
			</el-col>
			<!--工具条-->
			<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
				<el-form :inline="true">
					<el-form-item>
						<el-button type="danger" @click="batchRemove">
							<i class="iconfont icon-delete"></i>批量删除</el-button>
					</el-form-item>
				</el-form>
			</el-col>

			<!--列表-->
			<el-table :data="datas" :border="true" v-loading="listLoading" :height="tableMaxHeight"
				highlight-current-row style="width: 98%;margin-left:15px" @selection-change="selsChange">
				<el-table-column type="selection" width="55" align="center"> </el-table-column>
				<el-table-column prop="trainType" label="车型" align="center" sortable> </el-table-column>
				<el-table-column prop="trainNum" label="车号" align="center" sortable> </el-table-column>
				<el-table-column prop="earliestTime" label="最早数据时间"  align="center"
					sortable>
				</el-table-column>
				<el-table-column prop="latestTime" label="最新数据时间"  align="center" sortable>
				</el-table-column>
				<el-table-column prop="details" label="操作" align="center">
					<template slot-scope="scope">
						<el-button type="danger" @click="singleRemove(scope.$index, scope.row)" size="small ">
							<i class="el-icon-delete el-icon--center"></i>删除
						</el-button>
					</template>
				</el-table-column>
			</el-table>

			<!--删除界面-->
			<el-dialog title="删除数据【提交&删除操作无法撤销，删除前请确认】" :visible.sync="deleteFormVisible" :close-on-click-modal="false">
				<el-form :rules="deleteRules" :model="deleteForm" label-width="80px" ref="deleteForm"
					style="margin-left:5%;">
					<el-form-item prop="deadline " label="截止日期">
						<el-date-picker v-model="deleteForm.deadline" type="datetime" placeholder="请选择要删除的截止日期"
							 :editable="false">
						</el-date-picker>
					</el-form-item>
				</el-form>
				<div slot="footer" class="dialog-footer">
					<el-button type="primary" @click.native="deleteBtnChecked" :loading="deleteLoading">
						提交&删除
					</el-button>
					<el-button type="cancel" @click.native="deleteFormVisible = false">取消
					</el-button>
				</div>
			</el-dialog>

			<!--分页  工具条-->
			<el-col :span="24" class="toolbar" style="position:absolute;bottom:20px;right:0">
				<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
					:current-page.sync="currentPage" :page-sizes="[10, 20, 50]" :page-size="pageSize"
					layout="total, sizes, prev, pager, next, jumper" :total="total" style="float: right;">
				</el-pagination>
			</el-col>
			<el-row class="toolbar" style="position:absolute;bottom:20px;left:1">
				服务器硬盘可用空间 :<el-progress :stroke-width="18" :percentage="percentage"></el-progress>
			</el-row>
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
			let total = (eTime - sTime) / 1000;
			let day = parseInt(total / (24 * 60 * 60)); //计算整数天数
			if (sTime != 0 && eTime != 0) {
				if (eTime < sTime) {
					callback(new Error("结束时间早于开始时间"));
				} else if (day > 31) {
					callback(new Error("时间间隔不能超过366天"));
				} else {
					callback();
				}
			} else {
				callback();
			}
		};

		return {
			//车号
			numOptions: [{
				value: "",
				label: "所有"
			}],
			//车型
			typeOptions: [{
				value: "",
				label: "所有"
			}],
			percentage: 0,
			deleteFormVisible: false,
			deleteForm: {
				deadline: '',
			},
			row: '',
			isBatchRemove: true,
			deleteLoading: false,
			datas: [],
			total: 0,
			pageNum: 1,
			pageSize: 10,
			currentPage: 1,
			listLoading: false,
			sels: [], //列表选中的选项
			deleteSuccessCount:0,//删除成功的个数
			deleteCount:0,//要删除的个数
			//查询功能数据
			retrieveForm: {
				type: "",
				num: "",
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
			deleteRules: {
				deadline: [
					      {
            required: true,
            message: "必填",
            trigger: "change"
          }]
			},
			tableMaxHeight: document.body.offsetHeight - 280,
		};
	},
	mounted() {
		this.getTrainInfos();
		this.getStorage();
		this.getDatas();
		//页面改变时,更改尺寸
		$(window).on("resize", this.changeTableMaxHeight);
		this.changeTableMaxHeight();
	},
	methods: {
		//获取所有车型信息
		getTrainInfos() {
			let vm = this;
			app.get("get_train_type").then(data => {
				vm.numOptions = [{
						value: "",
						label: "所有"
					}],
					//车型
					vm.typeOptions = [{
						value: "",
						label: "所有"
					}];
				if (data.msg.length > 0) {
					let k = 0;
					for (let i = 0; i < data.msg.length; i++) {
						vm.$set(vm.typeOptions, i + 1, {
							value: data.msg[i],
							label: data.msg[i]
						});
					}
					vm.retrieveForm.type=vm.typeOptions[0].value
					vm.getStatisticsTrainNum()
				}
			});
		},
		//车型选择器改变时执行
		selectedTypeChange() {
			this.$refs.retrieveForm.validate(valid => {
				if (valid) {
					this.getStatisticsTrainNum();
				}
			});
		},

		getStatisticsTrainNum() {
			let vm = this;
			let param = {
				trainType: vm.retrieveForm.type
			};
			app.get("get_trainNum_by_type", param).then(data => {
				if (data.msg.length > 0) {
					let allData = data.msg;
					vm.numOptions = [{
						value: "",
						label: "所有"
					}];
					for (let j = 0; j < allData.length; j++) {
						vm.$set(vm.numOptions, j + 1, {
							value: allData[j],
							label: allData[j]
						});
					}
					vm.retrieveForm.num = vm.numOptions[0].value;
				}
			});
		},
		queryStart(currentPage){
			this.currentPage=currentPage
			this.handleCurrentChange(currentPage)
		},
		getDatas() {
			let vm = this;
			this.listLoading = true;
			let param = {
				trainType: vm.retrieveForm.type,
				trainNum: vm.retrieveForm.num,
				pageNum: vm.pageNum,
				pageSize: vm.pageSize
			}
			app.get("get_datas", param).then(d => {
				if (d) {
					vm.datas = d.msg.rows;
					vm.total = d.msg.total;
					this.listLoading = false;
				}
			});
		},
		//点击删除按钮开始执行该方法
		deleteBtnChecked() {
			this.$refs.deleteForm.validate(valid => {
				if (valid) {
					const endTime=util.formatDate(new Date(this.deleteForm.deadline), "yyyy-MM-dd hh:mm:ss")
					this.deleteSuccessCount=0;
					//批量删除
					if (this.isBatchRemove) {
						let maxTime = this.sels[0].earliestTime;
						this.deleteCount=this.sels.length;
						for (let i = 0; i < this.sels.length; i++) {
							if (maxTime < this.sels[i].earliestTime) {
								maxTime = this.sels[i].earliestTime;
							}
						}
						if (endTime < maxTime) {
							this.$message({
								message: "请选择晚于所有最早数据时间的日期",
								type: "error"
							});
							return;
						}
						for (let i = 0; i < this.sels.length; i++) {
							if (endTime > this.sels[i].latestTime) {
								let param = {
									trainId: this.sels[i].id,
								};
								this.sendDeleteRequest('drop_table', param)
							}else{
								let param = {
									trainId: this.sels[i].id,
									deadline: endTime
								};
								this.sendDeleteRequest('delete_datas', param)
							}
						}
					} else {
						//单个删除
						this.deleteCount=1;
						if (endTime < this.row.earliestTime) {
							this.$message({
								message: "请选择晚于最早数据时间的日期",
								type: "error"
							});
							return;
						}
						let param = {
							trainId: this.row.id,
							deadline:endTime
						}
						
						if (endTime > this.row.latestTime) {
							this.sendDeleteRequest('drop_table', param)
						}else{
							this.sendDeleteRequest('delete_datas', param)
						}
					}
				}
			})
		},
		//发送删除请求
		sendDeleteRequest(url, param) {
			this.deleteLoading = true;
			app.post(url, param).then(data => {
		    this.handleDeleteResponse(data)
			});
		},
		handleDeleteResponse(data) {
			this.deleteSuccessCount++;
			if (this.deleteSuccessCount === this.deleteCount ) {
				this.deleteLoading = false;
				this.deleteFormVisible = false;
				if (data.code === 0) {
					this.$message({
						message: data.msg,
						type: "success"
					});
					let vm = this;
					let param = {
						trainType: vm.retrieveForm.type,
						trainNum: vm.retrieveForm.num,
						pageNum: vm.pageNum,
						pageSize: vm.pageSize
					};
					this.listLoading = true;
					app.get("get_datas", param).then(d => {
						if (d.code === 0) {
							if (d.msg.total === 0) {
								this.getTrainInfos();
								this.retrieveForm.type=''
								this.retrieveForm.num=''
								this.queryStart(1);
							} else {
								if (d.msg.rows.length === 0) {
									this.queryStart(this.currentPage - 1);
								} else {
									this.listLoading = false;
									this.datas = d.msg.rows;
									this.total = d.msg.total;
								}
							}
						}
					});
				}
			}
		},
		//单个删除
		singleRemove(index, row) {
			this.row = row;
			this.isBatchRemove = false;
			this.deleteFormVisible = true;
		},
		//批量删除
		batchRemove() {
			this.isBatchRemove = true;
			if (this.sels.length == 0) {
				this.$message({
					message: "请至少勾选一条数据删除",
					type: "error"
				});
				return;
			}
			this.deleteFormVisible = true;
		},
		//分页触发
		handleCurrentChange(val) {
			this.pageNum = val;
			this.getDatas();
		},
		//改变页码
		handleSizeChange(val) {
			this.pageSize = val;
			this.getDatas();
		},
		getStorage() {
			let vm = this;
			app.get("get_storage").then(d => {
				if (d) {
					let num = new Number((d.msg.free / d.msg.total) * 100);
					vm.percentage = parseInt(num.toFixed(0));
				}
			});
		},
		//列表选中的选项
		selsChange(sels) {
			this.sels = sels;
		},
		//动态更改表格最大高度
		changeTableMaxHeight() {
			this.tableMaxHeight = document.body.offsetHeight - 280;
			let sectionHeight = document.body.offsetHeight - 80 + "px";
			$("#dataManager").css({
				height: sectionHeight
			});
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

section#dataManager {
  background-color: white;
  margin-left: 12px;
}

#dataManager .query {
  padding: 16px 15px 0px;
}

#dataManager .query .el-input {
  width: 140px;
}
#dataManager .toolbar {
  padding: 0px 15px;
  border-bottom: 1px solid transparent;
  border-top-right-radius: 3px;
  border-top-left-radius: 3px;
}
#dataManager .el-form-item__label {
  text-align: right;
}
#dataManager .systemColor .el-breadcrumb__item__inner {
  color: #97a8be;
}
</style>