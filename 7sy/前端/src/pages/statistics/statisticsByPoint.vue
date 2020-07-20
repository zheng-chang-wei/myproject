<template>
	<div>
		<section id="statisticsByPoint">
			<!--导航条-->
			<!-- <nav-bar :titles="['轴温异常统计', '测点层级']"></nav-bar> -->
			<!--查询-->
			<el-row :span="24" class="query" style="padding-bottom: 0px;min-width:900px">
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
					<el-form-item prop="starttime" label="时间范围">
						<el-date-picker v-model="retrieveForm.starttime" type="datetime" placeholder="选择日期时间"
							style="width:192px;"  :editable="false"></el-date-picker>
					</el-form-item>
					<el-form-item prop="endtime" label="-">
						<el-date-picker v-model="retrieveForm.endtime" type="datetime" placeholder="选择日期时间"
							style="width:192px;"  :editable="false"></el-date-picker>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" icon="search" @click="retrieveStatisticsResult">查询</el-button>
					</el-form-item>
				</el-form>
			</el-row>
			<el-row>
				<el-col :span="12">
					<!-- 异常触发次数 -->
					<ve-histogram :data="triggerTimesChartData" :settings="triggerTimesChartSettings"
						:events="triggerTimesChartEvents" :extend="histogramExtend" :height="histogramMaxHeight"></ve-histogram>
				</el-col>
				<el-col :span="12">
					<el-table :data="malfunctions" :border="true" v-loading="listLoading" :height="tableMaxHeight"
						highlight-current-row style="width: 97%;margin-left:15px">
						<el-table-column prop="axleNum" label="轴号" align="center"></el-table-column>
						<el-table-column prop="pointNum" label="测点号" align="center"></el-table-column>
						<el-table-column prop="faultTime" label="故障时间"  align="center">
						</el-table-column>
						<el-table-column prop="details" label="详情查询" align="center">
							<template slot-scope="scope">
								<el-button type="primary" icon="el-icon-search"
									@click="handleSearch(scope.$index, scope.row)">
								</el-button>
							</template>
						</el-table-column>
					</el-table>
				</el-col>
			</el-row>
			<!--分页  工具条-->
			<el-col :span="24" class="toolbar" style="position:absolute;bottom:20px;right:0">
				<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
					:current-page.sync="currentPage" :page-sizes="[10,20, 50, 100]" :page-size="pageSize"
					layout="total, sizes, prev, pager, next, jumper" :total="total" style="float: right;">
				</el-pagination>
			</el-col>
			<!--详情界面对话框-->
			<el-dialog :title="detialDialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false"  :closed='closed'>
				<el-radio-group v-model="tabPosition" style="margin-bottom: 30px;" @change="tabChange">
					<el-radio-button label="0">最近一天</el-radio-button>
					<el-radio-button label="1">最近两天</el-radio-button>
					<el-radio-button label="2">最近一周</el-radio-button>
					<el-radio-button label="3">最近两周</el-radio-button>
				</el-radio-group>
				<ve-line :title="tmpChartTitle" :data="temperatureResultChartData" :settings="temperatureResultChartSettings" :extend="extend"
					:toolbox="toolbox" class="line" ref="chart1"></ve-line>
				<ve-line :title="residCharTitle" :data="residualChartData" :settings="residualChartSettings" :extend="extend" :toolbox="toolbox"
					class="line" ref="chart2"></ve-line>
				<div slot="footer" class="dialog-footer">
					<el-button type="cancel" @click.native="dialogVisible = false" style="width:90px;">取消</el-button>
				</div>
			</el-dialog>
		</section>
	</div>
</template>

<script>
import VeHistogram from "v-charts/lib/histogram.common";
import VeLine from "v-charts/lib/line.common";
import app from "common/js/app";
import util from "common/js/util";
import "echarts/lib/component/dataZoom";
import "echarts/lib/component/toolbox";
import "echarts/lib/component/title";
export default {
  data() {
 		const checkTime = (rule, value, callback) => {
			let sTime = new Date(this.retrieveForm.starttime).getTime(),
			eTime = new Date(value).getTime();
			let total = (eTime - sTime)/1000;
			let day = parseInt(total / (24*60*60));//计算整数天数
			if (sTime!=0&&eTime!=0) {
				if (eTime < sTime) {
					callback(new Error("结束时间早于开始时间"));
				} else if(day>731){
					callback(new Error("时间间隔不能超过两年"));
				}else {
					callback();
				}
			}else{
				callback();
			}
		};
    let vm = this;
    this.triggerTimesChartEvents = {
      click: function(e) {
        let name = e.name.toString();
        vm.axleNum = name.charAt(0);
        vm.queryStart()
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
    };
    return {
      tmpChartTitle:{text:'',show:false},
			residCharTitle:{text:'',show:false},
      detialDialogTitle:'',
      total: 0,
      pageNum: 1,
      pageSize: 10,
      currentPage: 1,
      row: "",
      tabPosition: "0",
      axleNum: "1",
      selectedNum: "0",
      selectedType: "0",
			tableMaxHeight: document.body.offsetHeight - 220,
			histogramMaxHeight:'0px',
      malfunctions: [],
      listLoading: false,
      sels: [], //列表选中的选项
      dialogVisible: false,
      triggerTimesChartSettings: {
        stack: {
          测点: ["x1", "x2", "x3", "x4", "x5","x6"]
        },
        yAxisName: [],
        labelMap: {
          x1: "1号测点",
          x2: "2号测点",
          x3: "3号测点",
          x4: "4号测点",
          x5: "5号测点",
          x6: "6号测点"
        }
      },

      //异常触发次数数据
      triggerTimesChartData: {
        columns: ["x", "x1", "x2", "x3", "x4", "x5","x6"],
        rows: []
      },
      temperatureResultChartSettings: {
        xAxisType: "time",
        labelMap: {
          t1: "预测轴温数据",
          t2: "实测轴温数据"
        },
        yAxisName: ["Temp/℃"]
      },

      //轴温异常检测结果数据
      temperatureResultChartData: {
        columns: ["x", "t1", "t2"],
        rows: []
      },

      residualChartSettings: {
        xAxisType: "time",
        labelMap: {
          t1: "预测残差数据",
          t2: "异常检测门限DL",
          t3: "异常检测门限",
          t4: "异常检测门限UL"
        },
        yAxisName: ["Residual/℃"]
      },
      //残差结果数据
      residualChartData: {
        columns: ["x", "t1", "t2", "t3", "t4"],
        rows: []
      },
      histogramExtend: {
        series: {
          smooth: false,
          symbol: "none"
        },
        grid: {
          left: 40,
          right: 30
        }
      },
      extend: {
        series: {
          smooth: false,
          symbol: "none"
        },
        grid: {
          left: 40,
          right: 30
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
        num: "",
        type: "",
        starttime: "", //查询时开始时间
        endtime: "" //查询时结束时间
      },
      //查询时验证
      retrieveRules: {
        starttime: [
          {
            validator: checkTime,
            trigger: "change"
          }
        ],
        endtime: [
          {
            validator: checkTime,
            trigger: "change"
          }
        ]
      }
    };
  },
  components: {
    VeHistogram,
    VeLine
  },
  mounted() {
    this.getTrainInfos();
    //页面改变时,更改尺寸
    $(window).on("resize", this.changeTableMaxHeight);
    this.changeTableMaxHeight();
  },
  methods: {
    closed(){
      this.temperatureResultChartData.rows = [];
      this.residualChartData.rows = [];
      this.temperatureResultChartData.rows = null;
      this.residualChartData.rows = null;
      // this.$refs.chart1.echarts.dispose();
      // this.$refs.chart2.echarts.dispose();
    },
    //
    retrieveStatisticsResult() {
      this.$refs.retrieveForm.validate(valid => {
        if (valid) {
          if (this.retrieveForm.starttime==null||this.retrieveForm.endtime==null||this.retrieveForm.starttime.length == 0 || this.retrieveForm.endtime.length == 0) {
            this.$message.warning('请选择完整时间');
            return false;
          }
          this.getStatisticsResult();
          this.queryStart()
        }
      });
    },
    queryStart(){
			this.currentPage=1
			this.handleCurrentChange(1)
		},
    //车型选择器改变时执行
    selectedTypeChange() {
      let vm = this;
      let datas = [];
      let param = {
        trainType: vm.retrieveForm.type
      };
      app.get("get_trainNum_by_type", param).then(data => {
        if (data.msg.length>0) {
          let allData = data.msg;
          vm.numOptions = [];
          for (let j = 0; j < allData.length; j++) {
            vm.$set(vm.numOptions, j, {
              value: allData[j],
              label: allData[j]
            });
          }
          vm.retrieveForm.num = this.numOptions[0].value;
        }
      });
    },
    //获取统计结果
    getStatisticsResult() {
      let vm = this;
      let param = {
        trainType: vm.retrieveForm.type,
        trainNum: vm.retrieveForm.num,
        startTime: util.formatDate(new Date(vm.retrieveForm.starttime), "yyyy-MM-dd hh:mm:ss"),
        endTime: util.formatDate(new Date(vm.retrieveForm.endtime), "yyyy-MM-dd hh:mm:ss")
      };
      vm.triggerTimesChartData.rows=[]
      vm.triggerTimesChartSettings.yAxisName = [""];
      app.get("exception_statistics_byPoint", param).then(data => {
        if (data.msg.length>0) {
          let rows = data.msg;
          for (let i = 0; i < rows.length; i++) {
            vm.$set(vm.triggerTimesChartData.rows, i, {
              x: rows[i].x,
              x1: rows[i].y[0],
              x2: rows[i].y[1],
              x3: rows[i].y[2],
              x4: rows[i].y[3],
              x5: rows[i].y[4],
              x6: rows[i].y[5],
            });
          }
          vm.triggerTimesChartSettings.yAxisName = ["异常触发次数"];
        }else{
          vm.$message({
            message: "数据库中无匹配数据",
            type: "warning"
          });
        }
      });
    },
    getTableData() {
      let vm = this;
      this.listLoading = true;
      let param = {
        trainType: vm.retrieveForm.type,
        trainNum: vm.retrieveForm.num,
        startTime: util.formatDate(new Date(vm.retrieveForm.starttime), "yyyy-MM-dd hh:mm:ss"),
        endTime: util.formatDate(new Date(vm.retrieveForm.endtime), "yyyy-MM-dd hh:mm:ss"),
        axleNum: vm.axleNum,
        pageNum: vm.pageNum,
        pageSize: vm.pageSize
      };
      app.get("exception_statistics_table", param).then(data => {
        if (data.msg) {
          vm.malfunctions = data.msg.rows;
          vm.total = data.msg.total;
          vm.listLoading = false;
        }
      });
    },
    //获取所有车型信息
    getTrainInfos() {
      let vm = this;
      app.get("get_train_type").then(data => {
        if (data.msg.length>0) {
          let k = 0;
          for (let i = 0; i < data.msg.length; i++) {
            vm.$set(vm.typeOptions, i, {
              value: data.msg[i],
              label: data.msg[i]
            });
          }
          vm.retrieveForm.type = this.typeOptions[0].value;
          vm.selectedTypeChange();
        }
      });
    },
    tabChange() {
      this.handleSearch(0, this.row);
    },
    handleSearch(index, row) {
      this.dialogVisible = true;
      let vm = this;
      this.detialDialogTitle='车型'+vm.retrieveForm.type+'-车号'+vm.retrieveForm.num+'-轴号'+row.axleNum+'-测点'+row.pointNum+'详细数据';
      const title=vm.retrieveForm.type+'-'+vm.retrieveForm.num+'-'+row.axleNum+''+row.pointNum+'_'+ util.formatDate(new Date(this.retrieveForm.starttime), "yyyy-MM-dd") + "_" + util.formatDate(new Date(this.retrieveForm.endtime), "yyyy-MM-dd")
      this.setTitle(title)
      this.row = row;
      let param = {
        trainType: vm.retrieveForm.type,
        trainNum: vm.retrieveForm.num,
        axleNum: row.axleNum,
        pointNum: row.pointNum,
        faultTime: row.faultTime,
        timeRange: vm.tabPosition
      };
      app.get("exception_statistics_detail", param).then(data => {
        if (data.msg) {
          let rows = data.msg;
          vm.temperatureResultChartData.rows = [];
          vm.residualChartData.rows = [];
          for (let i = 0; i < rows.length; i++) {
            vm.$set(vm.temperatureResultChartData.rows, i, {
              x: rows[i].x,
              t1: rows[i].t1,
              t2: rows[i].t2
            });
            vm.$set(vm.residualChartData.rows, i, {
              x: rows[i].x,
              t1: rows[i].t3,
              t2: rows[i].t4.parameter1,
              t3: rows[i].t4.parameter2,
              t4: rows[i].t4.parameter3
            });
          }
          vm.triggerTimesChartSettings.yAxisName = ["异常触发次数"];
        }
      });
    },
    //分页触发
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getTableData();
    },
    //改变页码
    handleSizeChange(val) {
      this.pageSize = val;
      this.getTableData();
    },

   	setTitle(title) {
			this.tmpChartTitle.text = 'PredictionTemperature_' + title
			this.residCharTitle.text = 'Residual_' + title
		},
    //动态更改表格最大高度
    changeTableMaxHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 230;
			this.histogramMaxHeight=document.body.offsetHeight - 170 + "px";
			let sectionHeight = document.body.offsetHeight - 80 + "px";
      $("#statisticsByPoint").css({
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
#statisticsByPoint .toolbar {
  padding: 0px 15px;
  border-bottom: 1px solid transparent;
  border-top-right-radius: 3px;
  border-top-left-radius: 3px;
}
#statisticsByPoint .query {
  padding: 16px 15px 0px;
}

#statisticsByPoint .el-form-item__label {
  text-align: right;
}
#statisticsByPoint .query .el-input {
  width: 140px;
}
#statisticsByPoint .systemColor .el-breadcrumb__item__inner {
  color: #97a8be;
}
#statisticsByPoint .el-dialog {
  min-width: 700px;
}
#statisticsByPoint .line {
  margin: 0px, 55px;
}
</style>
