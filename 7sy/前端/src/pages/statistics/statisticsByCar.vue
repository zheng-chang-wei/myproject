<template>
	<div>
		<section id="statisticsByCar">
			<!--查询-->
			<el-row>
				<el-col :span="24" class="query" style="padding-bottom: 0px;min-width:900px">
					<el-form :inline="true" :rules="retrieveRules" ref="retrieveForm" :model="retrieveForm">
						<el-form-item label="统计维度" prop="dimensionality">
							<el-select v-model="retrieveForm.dimensionality" placeholder="请选择"
								@change="selectedDimensionalityChange">
								<el-option v-for="item in dimensionalityOptions" :key="item.value" :label="item.label"
									:value="item.value">
								</el-option>
							</el-select>
						</el-form-item>
						<el-form-item label="车号" prop="num">
							<el-select v-model="retrieveForm.num" v-bind:disabled="disabledType" placeholder="请选择">
								<el-option v-for="item in numOptions" :key="item.value" :label="item.label"
									:value="item.value">
								</el-option>
							</el-select>
						</el-form-item>
						<el-form-item prop="starttime" label="时间范围">
							<el-date-picker style="width:192px;" v-model="retrieveForm.starttime" type="datetime"
								v-bind:disabled="disabledStarttime" placeholder="选择日期时间" :editable="false">
							</el-date-picker>
						</el-form-item>
						<el-form-item prop="endtime" label="-">
							<el-date-picker style="width:192px;" v-model="retrieveForm.endtime" type="datetime"
								v-bind:disabled="disabledEndtime" placeholder="选择日期时间" :editable="false">
							</el-date-picker>
						</el-form-item>
						<el-form-item>
							<el-button type="primary" icon="search" @click="retrieveStatisticsResult">查询</el-button>
						</el-form-item>
					</el-form>
				</el-col>
			</el-row>
			<el-row class="query">
				<el-col :span="20">
					<ve-histogram v-loading="listLoading" :data="chartData" :settings="chartSettings" :extend="extend"
						:height="histogramMaxHeight"></ve-histogram>
				</el-col>
				<el-col :span="4" v-bind:hidden="radioVisible">
					<div style="padding:0px 20px 20px 20px;">
						<p>时间粒度</p>
						<el-radio-group v-model="radio" @change="radioChange">
							<el-radio :label="0">天</el-radio>
							<br />
							<el-radio :label="1">周</el-radio>
							<br />
							<el-radio :label="2">月</el-radio>
							<br />
							<el-radio :label="3">年</el-radio>
						</el-radio-group>
					</div>
				</el-col>
			</el-row>
		</section>
	</div>
</template>

<script>
import VeHistogram from "v-charts/lib/histogram.common";
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
				} else if (day > 731) {
					callback(new Error("时间间隔不能超过两年"));
				} else {
					callback();
				}
			} else {
				callback();
			}
		};
		return {
			chartSettings: {
				labelMap: {
					y: ""
				},
				yAxisName: []
			},
			chartData: {
				columns: ["x", "y"],
				rows: []
			},
			extend: {
				series: {
					// smooth: false,
					symbol: 'none',
				},
				grid: {
					left: 30,
					right: 30
				},
			},
			//统计维度
			dimensionalityOptions: [{
					value: "0",
					label: "按车号统计"
				},
				{
					value: "1",
					label: "按时间统计"
				}
			],
			radio: 0,
			listLoading: false,
			histogramMaxHeight: '',
			radioVisible: true,
			disabledStarttime: false,
			disabledEndtime: false,
			disabledType: true,
			// selectedType: '0',
			//车型
			numOptions: [],
			//查询功能数据
			retrieveForm: {
				dimensionality: "0",
				num: "",
				starttime: '', //查询时开始时间
				endtime: '' //查询时结束时间
			},
			//查询时验证
			retrieveRules: {
				endtime: [{
					validator: checkTime,
					trigger: "change"
				}],
			}
		};
	},
	components: {
		VeHistogram
	},
	mounted() {
		this.getTrainInfos();
		//页面改变时,更改尺寸
		$(window).on("resize", this.changeHistogramMaxHeight);
		this.changeHistogramMaxHeight();
	},
	methods: {
		//点击查询按钮查询统计结果
		retrieveStatisticsResult() {
			this.$refs.retrieveForm.validate(valid => {
				if (valid) {
					this.getStatisticsResult();
				}
			});
		},
		//查询统计结果
		getStatisticsResult() {
			let vm = this;
			if (vm.retrieveForm.dimensionality == '0') {
				if (vm.retrieveForm.starttime == null || vm.retrieveForm.endtime == null || vm.retrieveForm.starttime.length == 0 || vm.retrieveForm.endtime.length == 0) {
					this.$message.warning('请选择完整时间');
					return false;
				}
			}
			vm.listLoading = true
			let param = {
				dimensionality: vm.retrieveForm.dimensionality,
				num: vm.retrieveForm.num,
				startTime: util.formatDate(new Date(vm.retrieveForm.starttime), "yyyy-MM-dd hh:mm:ss"),
				endTime: util.formatDate(new Date(vm.retrieveForm.endtime), "yyyy-MM-dd hh:mm:ss"),
				granularityTime: vm.radio
			};
			app.get("exception_statistics_byCar", param).then(data => {
				if (data.msg) {
					let rows = data.msg;
					vm.chartData.rows = [];
					if (rows.length === 0) {
						vm.$message({
							message: "数据库中无匹配数据",
							type: "warning"
						});
						vm.chartSettings.yAxisName = [""];
					} else {
						for (let i = 0; i < rows.length; i++) {
							vm.$set(vm.chartData.rows, i, {
								x: rows[i].x,
								y: rows[i].y[0]
							});
						}
						vm.chartSettings.yAxisName = ["异常触发次数"];
					}
				}
				vm.listLoading = false
			});
		},
		//获取所有车号信息
		getTrainInfos() {
			let vm = this;
			app.get("get_trainNums").then(data => {
				if (data.msg) {
					for (let i = 0; i < data.msg.length; i++) {
						vm.$set(vm.numOptions, i, {
							value: data.msg[i],
							label: data.msg[i]
						});
					}
				}
			});
		},
		radioChange() {
			this.getStatisticsResult();
		},
		//统计维度选择器改变时执行
		selectedDimensionalityChange(id) {
			if (id == 0) {
				this.retrieveForm.num = '';
				this.disabledStarttime = false;
				this.disabledEndtime = false;
				this.disabledType = true;
				this.radioVisible = true;
			} else if (id == 1) {
				this.retrieveForm.num = this.numOptions[0].value;
				this.retrieveForm.starttime = '';
				this.retrieveForm.endtime = '';
				this.disabledStarttime = true;
				this.disabledEndtime = true;
				this.disabledType = false;
				this.radioVisible = false;
			}
		},
		//动态更改表格最大高度
		changeHistogramMaxHeight() {
			this.histogramMaxHeight = document.body.offsetHeight - 180 + "px";
			let sectionHeight = document.body.offsetHeight - 80 + "px";
			$("#statisticsByCar").css({
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

section#statisticsByCar {
  background-color: white;
  margin-left: 12px;
}

#statisticsByCar .query {
  padding: 16px 15px 0px;
}

#statisticsByCar .query .el-input {
  width: 140px;
}

#statisticsByCar .el-form-item__label {
  text-align: right;
}
</style>
