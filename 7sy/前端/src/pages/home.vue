<template>
	<div>
		<section id="home">

			<!--查询-->
			<el-col :span="24" class="query" style="padding-bottom: 0px;min-width:900px">
				<el-form :inline="true"  ref="retrieveForm" :model="retrieveForm">
          <el-form-item label="车型" prop="type">
						<el-select v-model="retrieveForm.type" style="width:150px;" placeholder="请选择"
							@change="getStatisticsTrainNum">
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
						<el-button type="primary" icon="search" @click="queryBtnChecked">查询</el-button>
					</el-form-item>
				</el-form>
			</el-col>

			<!--列表-->
			<el-table :data="datas" :border="true" v-loading="listLoading" :height="tableHeight"
				highlight-current-row style="width: 98%;margin-left:15px"   :row-style="{height:'0px'}" :cell-style="{padding:'2px'}">
				<el-table-column prop="trainType" label="车型" align="center" sortable> </el-table-column>
				<el-table-column prop="trainNum" label="车号" align="center" sortable> </el-table-column>
				<el-table-column prop="lastExceptionDate" label="近一个月最后一次报警时间" :formatter="formatTime" align="center" sortable width="300">
				</el-table-column>
				<el-table-column prop="exceptionCount" label="近一个月异常报警次数"  align="center" sortable width="250">
				</el-table-column>
				<el-table-column prop="details" label="数据查看" align="center">
					<template slot-scope="scope">
						<el-button type="primary" @click="changeToDetection(scope.$index, scope.row)" size="small ">
							<i class="el-icon-data-line el-icon--center"></i>
						</el-button>
					</template>
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
			row: '',
			datas: [],
			total: 0,
			pageNum: 1,
			pageSize: 20,
			currentPage: 1,
			listLoading: false,
			//查询功能数据
			retrieveForm: {
				type: "",
				num: "",
			},
			tableHeight: document.body.offsetHeight - 220,
		};
	},
	mounted() {
		this.getTrainInfos();
		this.getDatas();
		//页面改变时,更改尺寸
		$(window).on("resize", this.changeTableMaxHeight);
		this.changeTableMaxHeight();
	},
	components: {
	},
	methods: {
		//格式化时间
		formatTime(row, column) {
			if (row.exceptionCount===0) {
				return '无'
			}
			return (row.lastExceptionDate = row.lastExceptionDate ?
				util.formatDate(new Date(row.lastExceptionDate), "yyyy-MM-dd hh:mm:ss") :
				"");
		},

		//动态更改表格最大高度
		changeTableMaxHeight() {
			this.tableHeight=document.body.offsetHeight - 220
			let sectionHeight = document.body.offsetHeight - 80+ "px";
			$("#home").css({
				height: sectionHeight
			});
		},
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
					vm.retrieveForm.type = "";
					vm.getStatisticsTrainNum();
				}
			});
		},
		//车型选择器改变时执行
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
		queryBtnChecked(){
			this.currentPage=1
			this.handleCurrentChange(1)
		},
		getDatas() {
			let vm = this;
			this.listLoading = true;
			let param = {
				trainType: vm.retrieveForm.type,
				trainNum: vm.retrieveForm.num,
				pageNum: vm.pageNum,
				pageSize: vm.pageSize
			};
			app.get("get_lastMonth_exceptionData", param).then(d => {
				if (d) {
					vm.datas = d.msg.rows;
					vm.total = d.msg.total;
					this.listLoading = false;
				}
			});
		},
		changeToDetection(index, row) {
			vm.$root.Bus.$emit("menuActive", "/detection/detectionByCar");
			this.$router.push({
				path: "/detection/detectionByCar",
				query: {
					trainType: row.trainType,
					trainNum: row.trainNum,
					endTime: row.endTime,
					jump:true,
				}
			});
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
	}
};
</script>
<style>

#home {
  background-color: white;
  margin-left: 12px;
	margin-right: 12px;
  border: 1px solid #e8e8e8;
}

#home .query {
  padding: 16px 15px 0px;
}

#home .query .el-input {
  width: 140px;
}
#home .toolbar {
  padding: 0px 15px;
  border-bottom: 1px solid transparent;
  border-top-right-radius: 3px;
  border-top-left-radius: 3px;
}
#home .el-form-item__label {
  text-align: right;
}
#home .systemColor .el-breadcrumb__item__inner {
  color: #97a8be;
}
</style>