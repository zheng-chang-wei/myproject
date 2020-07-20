<template>
	<section id="historyManager">
		<!--查询-->
		<el-row :span="24" class="query" style="padding-bottom: 0px;min-width:900px">
			<el-form :inline="true" :rules="retrieveRules" ref="retrieveForm" :model="retrieveForm">
				<el-form-item label="车型" prop="type">
					<el-select v-model="retrieveForm.type" style="width:150px;" placeholder="请选择"
						@change="trainTypeChanged">
						<el-option v-for="item in typeOptions" :key="item" :label="item" :value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="车号" prop="num">
					<el-select v-model="retrieveForm.num" style="width:150px;" placeholder="请选择">
						<el-option v-for="item in numOptions" :key="item" :label="item" :value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item prop="starttime" label="时间范围">
					<el-date-picker v-model="retrieveForm.starttime" type="datetime" placeholder="选择日期时间"
						style="width:192px;" :editable="false">
					</el-date-picker>
				</el-form-item>
				<el-form-item prop="endtime" label="-">
					<el-date-picker v-model="retrieveForm.endtime" type="datetime" placeholder="选择日期时间"
						style="width:192px;"  :editable="false">
					</el-date-picker>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" icon="search" @click="retrieveStatisticsResult">查询</el-button>
				</el-form-item>
			</el-form>
		</el-row>
		<el-row style="margin-top:10px;">
			<el-col :span="24">
				<el-tabs v-model="activeName" style="margin-left:10px;">
					<el-tab-pane label="一号轴" name="1">
						<ve-line :title="title" :data="{columns:chartColumns1, rows:chartData1}" :settings="chartSettings.chart1"
							:extend="chartExtend" :loading="dataloading" :toolbox="toolbox" ref="chart1" height="500px">
						</ve-line>
					</el-tab-pane>
					<el-tab-pane label="二号轴" name="2">
						<ve-line :title="title" :data="{columns:chartColumns1, rows:chartData2}" :settings="chartSettings.chart1"
							:extend="chartExtend" :loading="dataloading" :toolbox="toolbox" ref="chart2" height="500px">
						</ve-line>
					</el-tab-pane>
					<el-tab-pane label="三号轴" name="3">
						<ve-line :title="title" :data="{columns:chartColumns1, rows:chartData3}" :settings="chartSettings.chart1"
							:extend="chartExtend" :loading="dataloading" :toolbox="toolbox" ref="chart3" height="500px">
						</ve-line>
					</el-tab-pane>
					<el-tab-pane label="四号轴" name="4">
						<ve-line :title="title" :data="{columns:chartColumns1, rows:chartData4}" :settings="chartSettings.chart1"
							:extend="chartExtend" :loading="dataloading" :toolbox="toolbox" ref="chart4" height="500px">
						</ve-line>
					</el-tab-pane>
					<el-tab-pane label="五号轴" name="5">
						<ve-line :title="title" :data="{columns:chartColumns1, rows:chartData5}" :settings="chartSettings.chart1"
							:extend="chartExtend" :loading="dataloading" :toolbox="toolbox" ref="chart5" height="500px">
						</ve-line>
					</el-tab-pane>
					<el-tab-pane label="六号轴" name="6">
						<ve-line :title="title" :data="{columns:chartColumns1, rows:chartData6}" :settings="chartSettings.chart1"
							:extend="chartExtend" :loading="dataloading" :toolbox="toolbox" ref="chart6" height="500px">
						</ve-line>
					</el-tab-pane>
				</el-tabs>
			</el-col>
		</el-row>
		<div style="text-align:center;margin-bottom:20px;">
			<el-button icon="el-icon-arrow-left" @click="lastMonth"> 上个月</el-button>
			<el-button @click="nextMonth">下个月<i class="el-icon-arrow-right el-icon--right"></i>
			</el-button>
			<el-button v-if="roleName==='超级管理员'||roleName==='管理员'" icon="el-icon-download" @click="downLoad"> 下载当前数据
			</el-button>
		</div>
	</section>
</template>


<script>
import VeLine from "v-charts/lib/line.common";
import app from "common/js/app";
import util from "common/js/util";
import 'v-charts/lib/style.css';
import 'echarts/lib/component/dataZoom';
import 'echarts/lib/component/toolbox'
import "echarts/lib/component/title";
import FileSaver from 'file-saver';
import { switchCase } from 'babel-types';
const clone = obj => {
	return JSON.parse(JSON.stringify(obj));
};

//initialdata与图例字段的映射关系
const LABEL_MAP_CHART1 = {
	acquisitionTime: '日期',
	axle1: '1号测点',
	axle2: '2号测点',
	axle3: '3号测点',
	axle4: '4号测点',
	axle5: '5号测点',
	axle6: '6号测点',
	gpsSpeed: 'GPS速度',
	ambientTemperature1: '环境温度1',
	ambientTemperature2: '环境温度2'
}
//折线图的基本属性
const INITIAL_CHART_SETTINGS = {
	axisSite: {
		right: ['axle1', 'axle2', 'axle3', 'axle4', 'axle5','axle6', 'ambientTemperature1', 'ambientTemperature2']
	},
	yAxisType: ['normal', 'normal'],
	// yAxisName: ['Speed/km/h', 'Temp/℃'],
	xAxisType: 'time',
	// showDataZoom: true,
}



export default {
	data() {
		// 组件创建完毕，放在这里不会影响初始化速度，data中的逻辑清晰一些
		const checkTime = (rule, value, callback) => {
			let sTime = new Date(this.retrieveForm.starttime).getTime(),
			eTime = new Date(value).getTime();
			let total = (eTime - sTime)/1000;
			let day = parseInt(total / (24*60*60));//计算整数天数
			if (sTime!=0&&eTime!=0) {
				if (eTime < sTime) {
					callback(new Error("结束时间早于开始时间"));
				} else if(day>184){
					callback(new Error("时间间隔不能超过半年"));
				}else {
					callback();
				}
			}else{
				callback();
			}
		};

		return {
			title:{text:'',show:false},
			roleName: "",
			//tab标识tab是否切换
			activeName: '1',
			//加载动画
			dataloading: false,
			allDatas: [],
			//折线图数据    
			chartData1: [],
			chartData2: [],
			chartData3: [],
			chartData4: [],
			chartData5: [],
			chartData6: [],

			toolbox: {
				show: true,
				feature: {
					dataZoom: {
							yAxisIndex: "none"
					},
					restore: {},
					saveAsImage: {}
				},
				right: 16,
				top:-7
					// left:50,
			},
			//ve-line的扩展属性
			chartExtend: {
				series: {
					symbol: 'none',
					smooth: false,
					showSymbol:false,
					hoverAnimation: false,
					showAllSymbol: false,
					sampling:'average'
				},
				grid: {
					left: 35,
					right: 30
				},
				legend: {
					x: 'left'
    		},
				dataZoom: [
          {
            type: 'inside',
            xAxisIndex: [0],
            start: 0,
            end: 100
          }
				],
			
			},
			//车号
			numOptions: [],
			//车型
			typeOptions: [],

			//查询功能数据
			retrieveForm: {
				num: "", //车号
				type: "", //车型
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
	beforeDestroy () {
		this.clearData();
		for (let index = 1; index < 7; index++) {
			this.$refs[`chart${index}`].echarts.dispose();
		}
	},
	created() {
		this.roleName = localStorage.getItem('roleName');
		//.$refs[`chart1`].echarts.resize();
		//获取折线图的图例
		this.chartColumns1 = Object.keys(LABEL_MAP_CHART1)
		//设置折线图的属性
		this.chartSettings = {
			chart1: {
				...INITIAL_CHART_SETTINGS,
				// 重要：配置不同折线图所需的label的字段和名称映射，这里的字段需要和对应的chartData中的columns的各项一致
				labelMap: LABEL_MAP_CHART1
			},
		};
	},

	watch: {
		//监控tab切换时重绘折线图  
		activeName(v) {
			this.$nextTick(_ => {
				this.getStatisticsResult()
				this.$refs[`chart${v}`].echarts.resize();
			})
		},
	},
	mounted() {
		this.getTrainTypeResult();
		let startTime=localStorage.getItem('starttime')
		let endTime=localStorage.getItem('endtime')
		let activeName=localStorage.getItem('activeName')
		this.retrieveForm.starttime=startTime
		this.retrieveForm.endtime=endTime
	},

	methods: {
		clearData(){
			this.chartData1=[]
			this.chartData2=[]
			this.chartData3=[]
			this.chartData4=[]
			this.chartData5=[]
			this.chartData6=[]
			this.chartData1=null
			this.chartData2=null
			this.chartData3=null
			this.chartData4=null
			this.chartData5=null
			this.chartData6=null
		},
		//通过车型和车号查询数据
		retrieveStatisticsResult() {
			// this.activeName='1'
			this.$refs.retrieveForm.validate(valid => {
				if (valid) {
					this.getStatisticsResult();
				}
			});
		},
		getStatisticsResult() {
			let vm = this;
			if (vm.retrieveForm.type.length === 0) {
				this.$message.warning('请选择车型');
				return false;
      }
			if (vm.retrieveForm.num.length === 0) {
				this.$message.warning('请选择车号');
				return false;
      }
			if (vm.retrieveForm.starttime===null||vm.retrieveForm.endtime===null||vm.retrieveForm.starttime.length === 0 || vm.retrieveForm.endtime.length === 0) {
				this.$message.warning('请选择完整时间');
				return false;
			}
			this.setTitle()
			this.clearData()
			let param = {
				trainType:vm.retrieveForm.type,
				trainNum: vm.retrieveForm.num,
				startDate: util.formatDate(new Date(vm.retrieveForm.starttime), "yyyy-MM-dd hh:mm:ss"),
				endDate: util.formatDate(new Date(vm.retrieveForm.endtime), "yyyy-MM-dd hh:mm:ss"),
				axleNum:vm.activeName
			};
			this.dataloading = true;
			app.get("get_data_by_trainIdAndtrainType", param).then(data => {
				this.saveQueryCondition()
				if (data.msg.length==0) {
					vm.$message({
						message: "数据库中无匹配数据",
						type: "warning"
					});
				}else{
					vm.chartSettings.chart1.yAxisName = ['Speed/km/h', 'Temp/℃'];
				}
				switch (vm.activeName) {
					case '1':
						vm.chartData1 = data.msg
						break;
					case '2':
						vm.chartData2 = data.msg
						break;
					case '3':
						vm.chartData3 = data.msg
						break;
					case '4':
						vm.chartData4 = data.msg
						break;
					case '5':
						vm.chartData5 = data.msg
						break;
					case '6':
						vm.chartData6 = data.msg
						break;
					default:
						break;
				}
				this.dataloading = false;
			});
		},
		saveQueryCondition(){
			localStorage.setItem('type', this.retrieveForm.type)
			localStorage.setItem('num', this.retrieveForm.num)
			localStorage.setItem('starttime', this.retrieveForm.starttime)
			localStorage.setItem('endtime', this.retrieveForm.endtime)
			localStorage.setItem('activeName', this.activeName)
		},
		lastMonth() {
				if (this.retrieveForm.starttime!==''&&this.retrieveForm.endtime!=='') {
					let starttime = new Date(this.retrieveForm.starttime)
					starttime.setMonth(starttime.getMonth() - 1)
					this.retrieveForm.starttime = starttime
					let endtime = new Date(this.retrieveForm.endtime)
					endtime.setMonth(endtime.getMonth() - 1)
					this.retrieveForm.endtime = endtime
				}
				this.getStatisticsResult();
		},
		nextMonth() {
			if (this.retrieveForm.starttime!==''&&this.retrieveForm.endtime!=='') {
				let starttime = new Date(this.retrieveForm.starttime)
				starttime.setMonth(starttime.getMonth() + 1)
				this.retrieveForm.starttime = starttime
				let endtime = new Date(this.retrieveForm.endtime)
				endtime.setMonth(endtime.getMonth() + 1)
				this.retrieveForm.endtime = endtime
			}
			this.getStatisticsResult();
		},
		//获取车辆类型
		getTrainTypeResult() {
			let vm = this;
			let datas = []
			app.get("get_train_type").then(data => {
				if (data.msg) {
					vm.typeOptions = data.msg;
					let type = localStorage.getItem('type')
					if (type !== null) {
						if (this.typeOptions.indexOf(type) !== -1) {
							this.retrieveForm.type = type
							this.trainTypeChanged()
						}
					}
				}
			});
		},

		//车型改变后，获取该车型下的所有车号
		trainTypeChanged() {
			let vm = this;
			let datas = []
			let param = {
				trainType: vm.retrieveForm.type
			};
			vm.retrieveForm.num = "";
			app.get("get_trainNum_by_type", param).then(data => {
				if (data.msg) {
					vm.numOptions = data.msg;
						let num=localStorage.getItem('num')
						if (num!==null) {
							if (this.numOptions.indexOf(num)!==-1) {
								this.retrieveForm.num=num
								this.retrieveStatisticsResult()
							}
						}
				}
			});
		},
	
		downLoad() {
			this.export2Excel();
		},
		//格式化jason数据
		formatJson(filterField, jsonData) {
			return jsonData.map(function (dataObj) {
				return filterField.map(function (field) {
					let fieldObj = dataObj[field];
					if (fieldObj.length == 0) {
						fieldObj = "--";
					}
					return fieldObj;
				});
			});
		},
		setTitle(){
			this.title.text = this.retrieveForm.type + "-" + this.retrieveForm.num + "-"+this.activeName+"号轴-"+ util.formatDate(new Date(this.retrieveForm.starttime), "yyyy-MM-dd") + "_" + util.formatDate(new Date(this.retrieveForm.endtime), "yyyy-MM-dd");
		},
		export2Excel() {
			require.ensure([], () => {
				let {export_json_to_excel} = require('@/vendor/Export2Excel');
				let tHeader = ['时间', '环境温度1', '环境温度2'];
				let filterField = ['acquisitionTime', 'ambientTemperature1', 'ambientTemperature2'];
				let tmpResult = [];
				switch (this.activeName) {
					case '1':
						tHeader.push('一轴测点1', '一轴测点2', '一轴测点3', '一轴测点4', '一轴测点5', '一轴测点6')
						filterField.push('axle1', 'axle2', 'axle3', 'axle4', 'axle5', 'axle6')
						for (let i = 0; i < this.chartData1.length; i++) {
							let tmpData = {
								acquisitionTime: util.formatDate(new Date(this.chartData1[i].acquisitionTime), "yyyy-MM-dd hh:mm:ss"),
								ambientTemperature1: this.chartData1[i].ambientTemperature1,
								ambientTemperature2: this.chartData1[i].ambientTemperature2,
								axle1: this.chartData1[i].axle1,
								axle2: this.chartData1[i].axle2,
								axle3: this.chartData1[i].axle3,
								axle4: this.chartData1[i].axle4,
								axle5: this.chartData1[i].axle5,
								axle6: this.chartData1[i].axle6,
								gpsSpeed: this.chartData1[i].gpsSpeed,
							};
							tmpResult.push(tmpData);
							tmpData = {};
						}
						break;
					case '2':
						tHeader.push('二轴测点1', '二轴测点2', '二轴测点3', '二轴测点4', '二轴测点5', '二轴测点6')
						filterField.push('axle21', 'axle22', 'axle23', 'axle24', 'axle25', 'axle26')
						for (let i = 0; i < this.chartData2.length; i++) {
							let tmpData = {
								acquisitionTime: util.formatDate(new Date(this.chartData2[i].acquisitionTime), "yyyy-MM-dd hh:mm:ss"),
								ambientTemperature1: this.chartData2[i].ambientTemperature1,
								ambientTemperature2: this.chartData2[i].ambientTemperature2,
								axle21: this.chartData2[i].axle1,
								axle22: this.chartData2[i].axle2,
								axle23: this.chartData2[i].axle3,
								axle24: this.chartData2[i].axle4,
								axle25: this.chartData2[i].axle5,
								axle26: this.chartData2[i].axle6,
								gpsSpeed: this.chartData2[i].gpsSpeed,
							};
							tmpResult.push(tmpData);
							tmpData = {};
						}
						break;
					case '3':
						tHeader.push('三轴测点1', '三轴测点2', '三轴测点3', '三轴测点4', '三轴测点5', '三轴测点6')
						filterField.push('axle31', 'axle32', 'axle33', 'axle34', 'axle35', 'axle36')
										for (let i = 0; i < this.chartData3.length; i++) {
							let tmpData = {
								acquisitionTime: util.formatDate(new Date(this.chartData3[i].acquisitionTime), "yyyy-MM-dd hh:mm:ss"),
								ambientTemperature1: this.chartData3[i].ambientTemperature1,
								ambientTemperature2: this.chartData3[i].ambientTemperature2,
								axle31: this.chartData3[i].axle1,
								axle32: this.chartData3[i].axle2,
								axle33: this.chartData3[i].axle3,
								axle34: this.chartData3[i].axle4,
								axle35: this.chartData3[i].axle5,
								axle36: this.chartData3[i].axle6,
								gpsSpeed: this.chartData3[i].gpsSpeed,
							};
							tmpResult.push(tmpData);
							tmpData = {};
						}
						break;
					case '4':
						tHeader.push('四轴测点1', '四轴测点2', '四轴测点3', '四轴测点4', '四轴测点5', '四轴测点6')
						filterField.push('axle41', 'axle42', 'axle43', 'axle44', 'axle45', 'axle46')
						for (let i = 0; i < this.chartData4.length; i++) {
							let tmpData = {
								acquisitionTime: util.formatDate(new Date(this.chartData4[i].acquisitionTime), "yyyy-MM-dd hh:mm:ss"),
								ambientTemperature1: this.chartData4[i].ambientTemperature1,
								ambientTemperature2: this.chartData4[i].ambientTemperature2,
								axle41: this.chartData4[i].axle1,
								axle42: this.chartData4[i].axle2,
								axle43: this.chartData4[i].axle3,
								axle44: this.chartData4[i].axle4,
								axle45: this.chartData4[i].axle5,
								axle46: this.chartData4[i].axle6,
								gpsSpeed: this.chartData4[i].gpsSpeed,
							};
							tmpResult.push(tmpData);
							tmpData = {};
						}
						break;
					case '5':
						tHeader.push('五轴测点1', '五轴测点2', '五轴测点3', '五轴测点4', '五轴测点5', '五轴测点6')
						filterField.push('axle51', 'axle52', 'axle53', 'axle54', 'axle55', 'axle56')
						for (let i = 0; i < this.chartData5.length; i++) {
							let tmpData = {
								acquisitionTime: util.formatDate(new Date(this.chartData5[i].acquisitionTime), "yyyy-MM-dd hh:mm:ss"),
								ambientTemperature1: this.chartData5[i].ambientTemperature1,
								ambientTemperature2: this.chartData5[i].ambientTemperature2,
								axle51: this.chartData5[i].axle1,
								axle52: this.chartData5[i].axle2,
								axle53: this.chartData5[i].axle3,
								axle54: this.chartData5[i].axle4,
								axle55: this.chartData5[i].axle5,
								axle56: this.chartData5[i].axle6,
								gpsSpeed: this.chartData5[i].gpsSpeed,
							};
							tmpResult.push(tmpData);
							tmpData = {};
						}
						break;
					case '6':
						tHeader.push('六轴测点1', '六轴测点2', '六轴测点3', '六轴测点4', '六轴测点5', '六轴测点6')
						filterField.push('axle61', 'axle62', 'axle63', 'axle64', 'axle65', 'axle66')
						for (let i = 0; i < this.chartData6.length; i++) {
							let tmpData = {
								acquisitionTime: util.formatDate(new Date(this.chartData6[i].acquisitionTime), "yyyy-MM-dd hh:mm:ss"),
								ambientTemperature1: this.chartData6[i].ambientTemperature1,
								ambientTemperature2: this.chartData6[i].ambientTemperature2,
								axle61: this.chartData6[i].axle1,
								axle62: this.chartData6[i].axle2,
								axle63: this.chartData6[i].axle3,
								axle64: this.chartData6[i].axle4,
								axle65: this.chartData6[i].axle5,
								axle66: this.chartData6[i].axle6,
								gpsSpeed: this.chartData6[i].gpsSpeed,
							};
							tmpResult.push(tmpData);
							tmpData = {};
						}
						break;
					default:
						break;
				}
				tHeader.push('GPS速度')
				filterField.push('gpsSpeed')
				let data = this.formatJson(filterField, tmpResult);
				export_json_to_excel(tHeader, data, this.title.text);
			})
		},
	}
};
</script>

<style>
#historyManager{
  background-color: white;
  margin-left: 12px;
  margin-right: 12px;
  border: 1px solid #e8e8e8;
}
#historyManager .query {
  padding: 16px 15px 0px;
}

#historyManager .el-form-item__label {
  text-align: right;
}

/* #historyManager .systemColor .el-breadcrumb__item__inner {
  color: #97a8be;
} */
 .red {
  border-width: 1px;
  border-style: solid;
  border-color: red;
}
.center {
  display: flex;
  -webkit-align-items: center;
  align-items: center;
  -webkit-justify-content: center;
  justify-content: center;
}
</style>