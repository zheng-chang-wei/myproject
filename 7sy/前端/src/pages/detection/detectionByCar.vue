<template>
	<div>
		<!--导航条-->
		<section id="detectionManager">
			<!-- <nav-bar :titles="['轴温异常检测']"></nav-bar> -->
			<!--查询-->
			<el-row>
				<el-col :span="24" class="query" style="padding-bottom: 0px;min-width:900px">
					<el-form :inline="true" :rules="retrieveRules" ref="retrieveForm" :model="retrieveForm">
						<el-form-item label="车型" prop="type">
							<el-select v-model="retrieveForm.type" style="width:140px;" placeholder="请选择"
								@change="getStatisticsTrainNum">
								<el-option v-for="item in typeOptions" :key="item" :label="item"
									:value="item">
								</el-option>
							</el-select>
						</el-form-item>
						<el-form-item label="车号" prop="num">
							<el-select v-model="retrieveForm.num" style="width:140px;" placeholder="请选择">
								<el-option v-for="item in numOptions" :key="item" :label="item"
									:value="item">
								</el-option>
							</el-select>
						</el-form-item>
						<el-form-item prop="starttime" label="时间范围">
							<!-- <span class="demonstration">开始时间</span> -->
							<el-date-picker v-model="retrieveForm.starttime" type="datetime" placeholder="选择日期时间"
								style="width:192px;"  :editable="false">
							</el-date-picker>
						</el-form-item>
						<el-form-item prop="endtime" label="-">
							<!-- <span class="demonstration">结束时间</span> -->
							<el-date-picker v-model="retrieveForm.endtime" type="datetime" placeholder="选择日期时间"
								style="width:192px;"  :editable="false">
							</el-date-picker>
						</el-form-item>
						<el-form-item>
							<el-button type="primary" icon="search" @click="getExceptionData" :loading="isLoading">查询</el-button>
						</el-form-item>
					</el-form>
				</el-col>
			</el-row>

			<el-row id="ExceptionData" class="backgroundimage" v-model="alldata">
				<el-col :span=1 style="width:7.2%;height:100%">
				</el-col>
				<el-col :span=1 style="width:4%;height:100%">
					<el-row style="width:50%;height:30%">
					</el-row>
					<template v-for="item in axleNums1">
						<el-row id="rowStyleSuccess" :key="item.axleName" :style="exceptionDataColor[item.isException]"
							v-if="item.axleName==14" @click.native="getExceptionAxleData(item.axleName)">
							{{item.axleName}}</el-row>
						<el-row id="rowStyleSuccess" :key="item.axleName" :style="exceptionDataColor[item.isException]"
							v-else-if="item.axleName==13" @click.native="getExceptionAxleData(item.axleName)">
							{{item.axleName}}</el-row>
					</template>
				</el-col>
				<el-col :span=1 style="width:4%;height:100%">
					<template v-for="item in axleNums1">
						<el-row id="axle16Success" :key="item.axleName" :style="exceptionDataColor[item.isException]"
							v-if="item.axleName==16" @click.native="getExceptionAxleData(item.axleName)">
							{{item.axleName}}</el-row>
					</template>
					<el-row style="width:50%;height:70%">
					</el-row>
					<template v-for="item in axleNums1">
						<el-row id="axle11Success" :key="item.axleName" :style="exceptionDataColor[item.isException]"
							v-if="item.axleName==11" @click.native="getExceptionAxleData(item.axleName)">
							{{item.axleName}}</el-row>
					</template>
				</el-col>
				<el-col :span=1 style="width:4%;height:100%">
					<el-row style="width:50%;height:30%">
					</el-row>
					<template v-for="item in axleNums1">
						<el-row id="axle11Success" :key="item.axleName" :style="exceptionDataColor[item.isException]"
							v-if="item.axleName==15" @click.native="getExceptionAxleData(item.axleName)">
							{{item.axleName}}</el-row>
					</template>
					<el-row id="nullData">
					</el-row>
					<template v-for="item in axleNums1">
						<el-row id="axle11Success" :key="item.axleName" :style="exceptionDataColor[item.isException]"
							v-if="item.axleName==12" @click.native="getExceptionAxleData(item.axleName)">
							{{item.axleName}}</el-row>
					</template>
				</el-col>

				<el-col :span=1 style="width:4%;height:100%">
					<el-row style="width:50%;height:30%">
					</el-row>
					<template v-for="item in axleNums2">
						<el-row
							style="margin-top:12px;width:50%;height:10%;color:white;margin-left:50%;border-radius: 4px; cursor:pointer;"
							:key="item.axleName" :style="exceptionDataColor[item.isException]" v-if="item.axleName==24"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
						<el-row
							style="margin-top:12px;width:50%;height:10%;color:white;margin-left:50%;border-radius: 4px; cursor:pointer;"
							:key="item.axleName" :style="exceptionDataColor[item.isException]"
							v-else-if="item.axleName==23" @click.native="getExceptionAxleData(item.axleName)">
							{{item.axleName}}</el-row>
					</template>
				</el-col>
				<el-col :span=1 style="width:4%;height:100%">
					<template v-for="item in axleNums2">
						<el-row id="axle16Success" :key="item.axleName" :style="exceptionDataColor[item.isException]"
							v-if="item.axleName==26" @click.native="getExceptionAxleData(item.axleName)">
							{{item.axleName}}</el-row>
					</template>
					<el-row style="width:50%;height:70%">
					</el-row>
					<template v-for="item in axleNums2">
						<el-row id="axle11Success" :key="item.axleName" :style="exceptionDataColor[item.isException]"
							v-if="item.axleName==21" @click.native="getExceptionAxleData(item.axleName)">
							{{item.axleName}}</el-row>
					</template>
				</el-col>
				<el-col :span=1 style="width:4%;height:100%">
					<el-row style="width:50%;height:30%">
					</el-row>
					<template v-for="item in axleNums2">
						<el-row id="axle11Success" :key="item.axleName" :style="exceptionDataColor[item.isException]"
							v-if="item.axleName==25" @click.native="getExceptionAxleData(item.axleName)">
							{{item.axleName}}</el-row>
					</template>
					<el-row id="nullData">
					</el-row>
					<template v-for="item in axleNums2">
						<el-row id="axle11Success" :key="item.axleName" :style="exceptionDataColor[item.isException]"
							v-if="item.axleName==22" @click.native="getExceptionAxleData(item.axleName)">
							{{item.axleName}}</el-row>
					</template>
				</el-col>

				<el-col :span=1 style="width:4%;height:100%">
					<el-row style="width:50%;height:30%">
					</el-row>
					<template v-for="item in axleNums3">
						<el-row id="rowStyleSuccessleft" style="width:50%; margin-left:50%" :key="item.axleName"
							:style="exceptionDataColor[item.isException]" v-if="item.axleName==34"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
						<el-row id="rowStyleSuccessleft" style="width:50%; margin-left:50%" :key="item.axleName"
							:style="exceptionDataColor[item.isException]" v-else-if="item.axleName==33"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
					</template>
				</el-col>
				<el-col :span=1 style="width:4%;height:100%">
					<template v-for="item in axleNums3">
						<el-row id="axle16Success" :key="item.axleName" :style="exceptionDataColor[item.isException]"
							v-if="item.axleName==36" @click.native="getExceptionAxleData(item.axleName)">
							{{item.axleName}}</el-row>
					</template>
					<el-row style="width:50%;height:70%">
					</el-row>
					<template v-for="item in axleNums3">
						<el-row id="axle11Success" :key="item.axleName" :style="exceptionDataColor[item.isException]"
							v-if="item.axleName==31" @click.native="getExceptionAxleData(item.axleName)">
							{{item.axleName}}</el-row>
					</template>
				</el-col>
				<el-col :span=1 style="width:4%;height:100%">
					<el-row style="width:50%;height:30%">
					</el-row>
					<template v-for="item in axleNums3">
						<el-row id="axle11Success" :key="item.axleName" :style="exceptionDataColor[item.isException]"
							v-if="item.axleName==35" @click.native="getExceptionAxleData(item.axleName)">
							{{item.axleName}}</el-row>
					</template>
					<el-row id="nullData">
					</el-row>
					<template v-for="item in axleNums3">
						<el-row id="axle11Success" :key="item.axleName" :style="exceptionDataColor[item.isException]"
							v-if="item.axleName==32" @click.native="getExceptionAxleData(item.axleName)">
							{{item.axleName}}</el-row>
					</template>
				</el-col>
				<el-col :span=1 style="width:14%;height:100%">
				</el-col>

				<el-col :span=1 style="width:4%;height:100%">
					<el-row style="width:50%;height:30%">
					</el-row>
					<template v-for="item in axleNums4">
						<el-row
							style="margin-top:12px;width:50%;height:10%;color:white;margin-left:50%;border-radius: 4px; cursor:pointer;"
							:key="item.axleName" :style="exceptionDataColor[item.isException]" v-if="item.axleName==42"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
					</template>
					<el-row id="nullData">
					</el-row>
					<template v-for="item in axleNums4">
						<el-row
							style="width:50%;height:10%;color:white;margin-left:50%;border-radius: 4px; cursor:pointer;"
							:key="item.axleName" :style="exceptionDataColor[item.isException]" v-if="item.axleName==45"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
					</template>
				</el-col>

				<el-col :span=1 style="width:3.3%;height:100%">
				</el-col>

				<el-col :span=1 style="width:2%;height:100%">
					<template v-for="item in axleNums4">
						<el-row id="axle16Success" style="width:100%" :key="item.axleName"
							:style="exceptionDataColor[item.isException]" v-if="item.axleName==41"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
					</template>
					<el-row style="width:50%;height:70%">
					</el-row>
					<template v-for="item in axleNums4">
						<el-row id="axle11Success" style="width:100%" :key="item.axleName"
							:style="exceptionDataColor[item.isException]" v-if="item.axleName==46"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
					</template>
				</el-col>

				<el-col :span=1 style="width:4%;height:100%">
					<el-row style="width:50%;height:30%">
					</el-row>
					<template v-for="item in axleNums4">
						<el-row id="rowStyleSuccessleft" style="width:50%;" :key="item.axleName"
							:style="exceptionDataColor[item.isException]" v-if="item.axleName==43"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
						<el-row id="rowStyleSuccessleft" style="width:50%;" :key="item.axleName"
							:style="exceptionDataColor[item.isException]" v-else-if="item.axleName==44"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
					</template>
				</el-col>

				<el-col :span=1 style="width:2%;height:100%">
					<el-row style="width:50%;height:30%">
					</el-row>
					<template v-for="item in axleNums5">
						<el-row
							style="margin-top:12px;width:100%;height:10%;color:white;border-radius: 4px; cursor:pointer;"
							:key="item.axleName" :style="exceptionDataColor[item.isException]" v-if="item.axleName==52"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
					</template>
					<el-row id="nullData">
					</el-row>
					<template v-for="item in axleNums5">
						<el-row style="width:100%;height:10%;color:white;border-radius: 4px; cursor:pointer;"
							:key="item.axleName" :style="exceptionDataColor[item.isException]" v-if="item.axleName==55"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
					</template>
				</el-col>

				<el-col :span=1 style="width:2.3%;height:100%">
				</el-col>

				<el-col :span=1 style="width:2%;height:100%">
					<template v-for="item in axleNums5">
						<el-row id="axle16Success" style="width:100%" :key="item.axleName"
							:style="exceptionDataColor[item.isException]" v-if="item.axleName==51"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
					</template>
					<el-row style="width:50%;height:70%">
					</el-row>
					<template v-for="item in axleNums5">
						<el-row id="axle11Success" style="width:100%" :key="item.axleName"
							:style="exceptionDataColor[item.isException]" v-if="item.axleName==56"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
					</template>
				</el-col>

				<el-col :span=1 style="width:4%;height:100%">
					<el-row style="width:50%;height:30%">
					</el-row>
					<template v-for="item in axleNums5">
						<el-row id="rowStyleSuccessleft" style="width:50%;" :key="item.axleName"
							:style="exceptionDataColor[item.isException]" v-if="item.axleName==53"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
						<el-row id="rowStyleSuccessleft" style="width:50%;" :key="item.axleName"
							:style="exceptionDataColor[item.isException]" v-else-if="item.axleName==54"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
					</template>
				</el-col>

				<el-col :span=1 style="width:4%;height:100%">
					<el-row style="width:50%;height:30%">
					</el-row>
					<template v-for="item in axleNums6">
						<el-row
							style="margin-top:12px;width:50%;height:10%;color:white;border-radius:4px; margin-left:50%; cursor:pointer;"
							:key="item.axleName" :style="exceptionDataColor[item.isException]" v-if="item.axleName==62"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
					</template>
					<el-row id="nullData">
					</el-row>
					<template v-for="item in axleNums6">
						<el-row
							style="width:50%;height:10%;color:white;border-radius: 4px; margin-left:50%; cursor:pointer;"
							:key="item.axleName" :style="exceptionDataColor[item.isException]" v-if="item.axleName==65"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
					</template>
				</el-col>
				<el-col :span=1 style="width:2.3%;height:100%">
				</el-col>
				<el-col :span=1 style="width:2%;height:100%">
					<template v-for="item in axleNums6">
						<el-row id="axle16Success" style="width:100%" :key="item.axleName"
							:style="exceptionDataColor[item.isException]" v-if="item.axleName==61"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
					</template>
					<el-row style="width:50%;height:70%">
					</el-row>
					<template v-for="item in axleNums6">
						<el-row id="axle11Success" style="width:100%" :key="item.axleName"
							:style="exceptionDataColor[item.isException]" v-if="item.axleName==66"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
					</template>
				</el-col>
				<el-col :span=1 style="width:4%;height:100%">
					<el-row style="width:50%;height:30%">
					</el-row>
					<template v-for="item in axleNums6">
						<el-row id="rowStyleSuccessleft" style="width:50%;" :key="item.axleName"
							:style="exceptionDataColor[item.isException]" v-if="item.axleName==63"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
						<el-row id="rowStyleSuccessleft" style="width:50%;" :key="item.axleName"
							:style="exceptionDataColor[item.isException]" v-else-if="item.axleName==64"
							@click.native="getExceptionAxleData(item.axleName)">{{item.axleName}}</el-row>
					</template>
				</el-col>
			</el-row>
			<div style="border:2px solid #000;border-radius:5px;margin:5px 10px;text-align:center">
				<el-row style="margin-top:5px">测点:{{retrieveForm.axleName}}</el-row>
				<el-row style="margin-top:10px">
					<el-col :span="24" v-model='tmpChartData'>
						<ve-line :title="tmpChartTitle" :data="{columns:chartColumns1, rows:tmpChartData}" :settings="chartSettings.chart1"
							:extend="chartExtend" :loading="dataloading" :toolbox="toolbox" ref="chart1" height="500px">
						</ve-line>
					</el-col>
					<el-col :span="24" v-model='residCharData'>
						<ve-line :title="residCharTitle" :data="{columns:chartColumns2, rows:residCharData}" :settings="chartSettings.chart2"
							:extend="chartExtend" :loading="dataloading" :toolbox="toolbox" ref="chart2" height="500px">
						</ve-line>
					</el-col>
				</el-row>
			</div>
		</section>
	</div>
</template>

<script>
import VeLine from "v-charts/lib/line.common";
import app from "common/js/app";
import util from "common/js/util";
import 'echarts/lib/component/toolbox';
import 'echarts/lib/component/dataZoom';
import "echarts/lib/component/title";
const LABEL_MAP_CHART1 = {
	acquisitionTime: '日期',
	actualValue: '实际轴温数据',
	predicteValue: '预测轴温数据'
}
const LABEL_MAP_CHART2 = {
	acquisitionTime: '日期',
	residuals: '预测残差数据',
	threshold: '异常检测门限',
	threshold1: '异常检测门限',
	threshold2: '异常检测门限',
}
//折线图的基本属性
const INITIAL_CHART_SETTINGS1 = {
	yAxisType: ['normal'],
	yAxisName: ['Temp/℃'],
	xAxisType: 'time',
	showDataZoom: true,
}

//折线图的基本属性
const INITIAL_CHART_SETTINGS2 = {
	yAxisType: ['normal'],
	yAxisName: ['Residual/℃'],
	xAxisType: 'time',
	showDataZoom: true,
}

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
				} else if (day > 366) {
					callback(new Error("时间间隔不能超过一年"));
				} else {
					callback();
				}
			} else {
				callback();
			}
		};
		this.toolbox = {
			show: true,
			feature: {
				dataZoom: {
					yAxisIndex: "none"
				},
				restore: {},
				saveAsImage: {}
			},
			right: 25,
			// left:50,
			top: 25
		}
		return {
			exceptionDataColor: {
				0: {
					'background-color': '#13ce66'
				},
				1: {
					'background-color': 'red'
				}
			},
			tmpChartTitle:{text:'',show:false},
			residCharTitle:{text:'',show:false},
			alldata: [],
			axle1: 0,
			tmpChartData: [],
			residCharData: [],
			isLoading: false,
			dataloading: false,
			//每个轴的测点
			chartColumns1: [],
			axleNums1: [],
			axleNums2: [],
			axleNums3: [],
			axleNums4: [],
			axleNums5: [],
			axleNums6: [],
			chartExtend: {
				series: {
					symbol: 'none',
					smooth: false,
					sampling: 'average'
				},
				grid: {
					left: 35,
					right: 30
				},
				dataZoom: [{
					type: 'inside',
					xAxisIndex: [0],
					start: 0,
					end: 100
				}],
			},
			//车型
			typeOptions: [],
			//车号
			numOptions: [],
			//查询功能数据
			retrieveForm: {
				type: '',
				num: '',
				axleName: '',
				starttime: "", //查询时开始时间
				endtime: "" //查询时结束时间
			},
			//查询时验证
			retrieveRules: {
				endtime: [{
					validator: checkTime,
					trigger: "change"
				}]
			}
		};
	},
	components: {
		VeLine
	},

	computed: {
		trainNum: function () {
			return this.$route.query.trainNum;
		},
		trainType: function () {
			return this.$route.query.trainType;
		},
		endTime: function () {
			return this.$route.query.endTime;
		},
		//是否是点击维修履历表格中数据跳转过来
		jump: function () {
			if (typeof (this.$route.query.jump) === 'string') {
				return this.$route.query.jump === 'true';
			}
			return this.$route.query.jump;
		}
	},
	created() {
		//获取折线图的图例
		this.chartColumns1 = Object.keys(LABEL_MAP_CHART1)
		this.chartColumns2 = Object.keys(LABEL_MAP_CHART2)
		//设置折线图的属性
		this.chartSettings = {
			chart1: {
				...INITIAL_CHART_SETTINGS1,
				// 重要：配置不同折线图所需的label的字段和名称映射，这里的字段需要和对应的chartData中的columns的各项一致
				labelMap: LABEL_MAP_CHART1
			},
			chart2: {
				...INITIAL_CHART_SETTINGS2,
				// 重要：配置不同折线图所需的label的字段和名称映射，这里的字段需要和对应的chartData中的columns的各项一致
				labelMap: LABEL_MAP_CHART2
			},
		};
	},

	mounted() {
		this.getTrainTypeResult()
		if (this.jump) {
			this.retrieveForm.type = this.trainType
			this.getStatisticsTrainNum()
			this.retrieveForm.num = this.trainNum
			let d = new Date(this.endTime.replace(/-/g,  "/"))
			this.retrieveForm.endtime = new Date(this.endTime.replace(/-/g,  "/"))
			d.setMonth(d.getMonth() - 1)
			this.retrieveForm.starttime = d
			this.getAxleExceptionData()
		} else {
			this.retrieveForm.starttime = localStorage.getItem('detection_starttime')
			this.retrieveForm.endtime = localStorage.getItem('detection_endtime')
		}
	},
	beforeDestroy() {
		this.desposeChart();
	},
	methods: {
		clearChart() {
			this.tmpChartData = [];
			this.residCharData = [];
			this.tmpChartData = null;
			this.residCharData = null;
		},
		desposeChart() {
			this.clearChart()
			this.$refs.chart1.echarts.dispose();
			this.$refs.chart2.echarts.dispose();
		},
		getExceptionData() {
			this.$refs.retrieveForm.validate(valid => {
				if (valid) {
					this.getAxleExceptionData();
				}
			});
		},
		getAxleExceptionData() {
			let vm = this;
			if (vm.retrieveForm.type.length == 0) {
				this.$message.warning('请选择车型');
				return false;
			}
			if (vm.retrieveForm.num.length == 0) {
				this.$message.warning('请选择车号');
				return false;
			}
			if (vm.retrieveForm.starttime == null || vm.retrieveForm.endtime == null || vm.retrieveForm.starttime.length == 0 || vm.retrieveForm.endtime.length == 0) {
				this.$message.warning('请选择完整时间');
				return false;
			}
			this.saveQueryCondition()
			let param = {
				trainType: vm.retrieveForm.type,
				trainNum: vm.retrieveForm.num,
				startDate: util.formatDate(new Date(vm.retrieveForm.starttime), "yyyy-MM-dd hh:mm:ss"),
				endDate: util.formatDate(new Date(vm.retrieveForm.endtime), "yyyy-MM-dd hh:mm:ss")
			};
			this.isLoading = true
			this.clearChart()
			for (let i = 0; i < 6; i++) {
				this.axleNums1[i] = [];
				this.axleNums2[i] = [];
				this.axleNums3[i] = [];
				this.axleNums4[i] = [];
				this.axleNums5[i] = [];
				this.axleNums6[i] = [];
			}
			app.get("get_axle_exception_state_data", param).then(data => {
				this.isLoading = false
				if (data.msg) {
					vm.alldata = data.msg;
					if (data.msg.length === 0) {
						vm.$message({
							message: "数据库中无匹配数据",
							type: "warning"
						});
					} else {
						for (let i = 0; i < 6; i++) {
							vm.axleNums1[i] = data.msg[5 - i];
							vm.axleNums2[i] = data.msg[11 - i];
							vm.axleNums3[i] = data.msg[17 - i];
							vm.axleNums4[i] = data.msg[i + 18];
							vm.axleNums5[i] = data.msg[i + 24];
							vm.axleNums6[i] = data.msg[i + 30];
						}
						let axleName = localStorage.getItem('axleName')
						if (axleName !== null && axleName !== '') {
							this.getExceptionAxleData(axleName)
						}
					}
				}
			});
		},
		saveQueryCondition() {
			localStorage.setItem('detection_type', this.retrieveForm.type)
			localStorage.setItem('detection_num', this.retrieveForm.num)
			localStorage.setItem('detection_starttime', this.retrieveForm.starttime)
			localStorage.setItem('detection_endtime', this.retrieveForm.endtime)
		},
		getExceptionAxleData(axleName) {
			this.retrieveForm.axleName=axleName
			let vm = this;
			if (vm.retrieveForm.type.length == 0) {
				this.$message.warning('请选择车型');
				return false;
			}
			if (vm.retrieveForm.num.length == 0) {
				this.$message.warning('请选择车号');
				return false;
			}
			if (vm.retrieveForm.starttime == null || vm.retrieveForm.endtime == null || vm.retrieveForm.starttime.length == 0 || vm.retrieveForm.endtime.length == 0) {
				this.$message.warning('请选择完整时间');
				return false;
			}
			localStorage.setItem('axleName', axleName)
			this.setTitle()
			this.$refs.retrieveForm.validate(valid => {
				if (valid) {
					vm.retrieveForm.axleName = axleName;
					this.dataloading = true;
					let param = {
						trainType: vm.retrieveForm.type,
						trainNum: vm.retrieveForm.num,
						startDate: util.formatDate(new Date(vm.retrieveForm.starttime), "yyyy-MM-dd hh:mm:ss"),
						endDate: util.formatDate(new Date(vm.retrieveForm.endtime), "yyyy-MM-dd hh:mm:ss"),
						axleName: axleName
					};
					app.get("get_axle_exception_data", param).then(data => {
						if (data.msg) {
							if (data.msg.length == 0) {
								vm.$message({
									message: "数据库中无匹配数据",
									type: "warning"
								});
							}
							this.tmpChartData = data.msg;
							this.residCharData = data.msg;
						}
						this.dataloading = false;
					});
				}
			});
		},

		//获取车辆类型
		getTrainTypeResult() {
			let vm = this;
			let datas = []
			app.get("get_train_type").then(data => {
				if (data.msg) {
					vm.typeOptions = data.msg;
					let type = localStorage.getItem('detection_type')
					if (type !== null && type !== '') {

						if (this.typeOptions.indexOf(type) !== -1) {
							this.retrieveForm.type = type
							this.getStatisticsTrainNum()
						}

					}
				}
			});
		},

		//获取该车型下的所有车号
		getStatisticsTrainNum() {
			let vm = this;
			let datas = []
			let param = {
				trainType: vm.retrieveForm.type
			};
			vm.retrieveForm.num = "";
			app.get("get_trainNum_by_type", param).then(data => {
				if (data.msg) {
					vm.numOptions = data.msg;
					let num = localStorage.getItem('detection_num')
					if (num!==null) {
						if (this.numOptions.indexOf(num) !== -1) {
						this.retrieveForm.num = num
						this.getAxleExceptionData()
					
					}
				}
				}
			});
		},
		setTitle() {
			let title = this.retrieveForm.type + "-" + this.retrieveForm.num + "-" + this.retrieveForm.axleName + "_" + util.formatDate(new Date(this.retrieveForm.starttime), "yyyy-MM-dd") + "_" + util.formatDate(new Date(this.retrieveForm.endtime), "yyyy-MM-dd");
			this.tmpChartTitle.text = 'PredictionTemperature_' + title
			this.residCharTitle.text = 'Residual_' + title
		}
	}
};
</script>
<style>
#detectionManager {
  background-color: white;
  margin-left: 12px;
  margin-right: 12px;
  border: 1px solid #e8e8e8;
}
#detectionManager .query {
  padding: 16px 15px 0px;
}

#detectionManager .el-form-item__label {
  text-align: right;
}
#detectionManager .backgroundimage{
  background:url(../../assets/qsj.png);
  background-size:100% 100%;
  width:100%;
  height:250px;
}

#detectionManager .systemColor .el-breadcrumb__item__inner {
  color: #97a8be;
}

#rowStyleSuccess{
  margin-top:12px;
  width:50%;
  height:10%;
  color:white;
  margin-left:50%;
  text-align:center;
  border-radius: 4px;
  cursor:pointer;
}
#rowStyleSuccessleft{
  margin-top:12px;
  width:100%;
  height:10%;
  color:white;
 
  text-align:center;
  border-radius: 4px;
  cursor:pointer;
}

#nullData{
   margin-top:12px;
  width:50%;
  height:10%;
  color:white;
  margin-left:50%;
  text-align:center;
}
#axle16Success{
  margin-top:12px;
  width:50%;
  height:10%;
  color:white;
  text-align:center;
  border-radius: 4px;
  cursor:pointer;
}

#axle11Success{
  width:50%;
  height:10%;
  color:white;
  text-align:center;
  border-radius: 4px;
  cursor:pointer;
}

</style>
