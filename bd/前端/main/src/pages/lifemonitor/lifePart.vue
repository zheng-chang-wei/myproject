<template>
  <div id="lifemonitor" class="app-container">
    <el-row>
      <el-col :span="24">
        <el-form ref="retrieveForm" :inline="true" class="query" :model="retrieveForm" :rules="formRules">
          <el-form-item label="时间范围" prop="startTime">
            <el-date-picker v-model="retrieveForm.startTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间" style="width:192px" :editable="false" />
          </el-form-item>
          <el-form-item prop="endTime" label="-">
            <el-date-picker
              v-model="retrieveForm.endTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择日期时间"
              style="width:192px;"
              :editable="false"
            />
          </el-form-item>
          <el-form-item label="项目" prop="projectName">
            <el-select v-model="retrieveForm.projectName" placeholder="请选择" @change="projectChanged">
              <el-option v-for="item in projectNames" :key="item" :label="item" :value="item" />
            </el-select>
          </el-form-item>
          <el-form-item label="车辆" prop="trainNo">
            <el-select v-model="retrieveForm.trainNo" placeholder="请选择">
              <el-option v-for="item in trainNos" :key="item" :label="item" :value="item" />
            </el-select>
          </el-form-item>
          <el-form-item label="部件名称" prop="lifeItemName">
            <el-select v-model="retrieveForm.lifeItemName" placeholder="请选择" @change="getItemCode(retrieveForm.lifeItemName)">
              <el-option v-for="item in lifeItemNames" :key="item" :label="item" :value="item" />
            </el-select>
          </el-form-item>
          <el-form-item label="车厢号" prop="carNo">
            <el-input v-model="retrieveForm.carNo" placeholder="请输入车厢号" />
          </el-form-item>
          <el-form-item label="门地址" prop="doorAddr">
            <el-input v-model="retrieveForm.doorAddr" placeholder="请输入门地址" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="search" @click="queryButtonChecked()">查询</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <el-tabs tab-position="left">
        <el-tab-pane label="预警列表">
          <life-warning :table-data="tableData" :page-info="pageInfo" :max-height="tableMaxHeight" @sizeChange="handleSizeChange" @currentChange="handleCurrentChange" />
        </el-tab-pane>
        <el-tab-pane label="部件寿命">
          <component-life :carriage="carriage" :life-statetable="lifeStatetable" @doorChecked="getDoorLifeStateDatas" @customColorMethod="customColorMethod" />
        </el-tab-pane>
      </el-tabs>
    </el-row>
  </div>
</template>
<script>
import app from '@/common/js/app'
import lifeWarning from './tabPane/lifeWarning'
import componentLife from './tabPane/componentLife'
const all = '所有'
export default {
  components: {
    lifeWarning,
    componentLife
  },
  data() {
    return {
      formRules: {
        startTime: [{
          type: 'string',
          required: false,
          message: '请选择起始日期',
          trigger: 'change'
        }],
        // 时间格式设为string
        endTime: [{
          type: 'string',
          required: false,
          message: '请选择结束日期',
          trigger: 'change'
        }],
        projectName: [{
          type: 'string',
          required: true,
          message: '请选择项目',
          trigger: 'blur'
        }],
        trainNo: [{
          type: 'string',
          required: true,
          message: '请选择车辆',
          trigger: 'blur'
        }]
      },
      projectNames: [],
      trainNos: [],
      // 部件名称
      lifeItemNames: [all],
      // 部件id
      lifeItemIds: [all],
      // form表单
      retrieveForm: {
        id: '',
        startTime: '',
        endTime: '',
        lifeItemName: all,
        lifeItemId: '',
        projectName: '',
        trainNo: '',
        carNo: '',
        doorAddr: '',
        debugMode: ''
      },
      tableData: [],
      pageInfo: {
        // 分页信息
        pageNum: 1,
        pageSize: 20,
        currentPage: 1,
        total: 0
      },
      tableMaxHeight: document.body.offsetHeight - 280,
      carriage: {
        carriageSpan: 3,
        carriageDatas: []
      },
      // 状态数据源
      lifeStatetable: []
    }
  },
  mounted() {
    this.getProject()
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeTableHeight)
    this.changeTableHeight()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeTableHeight)
  },
  methods: {
    changeTableHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 280
      // var sectionHeight = document.body.offsetHeight - 50 + 'px'
      // var element = document.getElementById('faultInfor')
      // if (element !== null) {
      //   element.style.height = sectionHeight
      // }
    },
    // 获取项目名称
    getProject() {
      app.get('get_project').then(data => {
        if (data.msg) {
          data.msg.forEach(element => {
            this.projectNames.push(element.name)
          })
          if (this.projectNames.length > 0) {
            this.retrieveForm.projectName = this.projectNames[0]
            this.getVehicleNo()
          }
        }
      })
    },
    projectChanged() {
      this.getVehicleNo()
    },
    getVehicleNo() {
      this.trainNos = []
      this.retrieveForm.trainNo = ''
      const params = {
        project: this.retrieveForm.projectName
      }
      app.get('get_train_no', params).then(data => {
        if (data.msg) {
          if (data.msg != null && data.msg.length > 0) {
            data.msg.forEach(element => {
              if (element != null) { this.trainNos.push(element.trainNo) }
            })
          }
          if (this.trainNos.length > 0) {
            this.retrieveForm.trainNo = this.trainNos[0]
            this.refresh()
          }
        }
      })
    },
    queryButtonChecked() {
      this.refresh()
    },
    refresh() {
      // TODO 查询预警列表，查询车辆部件信息
      this.getLifeWarning()
      this.getTrainConfig()
    },
    getLifeWarning() {
      // 点击查询按钮后跳转到第一页
      this.pageInfo.currentPage = 1
      this.handleCurrentChange(1)
    },
    // 获取寿命预警信息
    getLifeMonitorData() {
      this.$refs['retrieveForm'].validate(valid => {
        if (valid) {
          this.tableData = []
          this.listLoading = true
          var param = {
            startTime: this.retrieveForm.startTime,
            endTime: this.retrieveForm.endTime,
            lifeItemId: this.retrieveForm.lifeItemId,
            project: this.retrieveForm.projectName,
            trainNo: this.retrieveForm.trainNo,
            carNo: this.retrieveForm.carNo,
            doorAddr: this.retrieveForm.doorAddr,
            debugMode: this.retrieveForm.debugMode,
            pageNum: this.pageInfo.pageNum,
            pageSize: this.pageInfo.pageSize
          }
          if (this.retrieveForm.lifeItemName === all) {
            param.lifeItemId = ''
          }
          app.get('get_lifemonitor_message', param).then(data => {
            if (data.msg.rows && data.msg.rows.length !== 0) {
              this.rolesOptions = []
              data.msg.rows.forEach(item => {
                this.tableData.push(item)
              })
              this.pageInfo.total = data.msg.total
            }
            this.listLoading = false
          })
        }
      })
    },
    // 分页触发
    handleCurrentChange(val) {
      this.pageInfo.pageNum = val
      this.getLifeMonitorData()
    },
    // 改变页码
    handleSizeChange(val) {
      this.pageInfo.pageSize = val
      this.getLifeMonitorData()
    },
    getTrainConfig() {
      const params = {
        project: this.retrieveForm.projectName,
        trainNo: this.retrieveForm.trainNo
      }
      app.get('get_train_config_info', params).then(data => {
        if (data.msg) {
          const train = JSON.parse(data.msg)
          if (train != null) {
            const cars = train.cars
            this.carriage.carriageDatas = cars
            // 如果车箱数超过10节，一节车厢占2列
            if (cars.length > 10) {
              this.carriage.carriageSpan = 2
            } else {
              this.carriage.carriageSpan = 3
            }
          }
        }
      })
    },
    getDoorLifeStateDatas(carNo, doorAddr) {
      const params = {
        project: this.retrieveForm.projectName,
        trainNo: this.retrieveForm.trainNo,
        doorAddr: doorAddr,
        carNo: carNo
      }
      this.lifeStatetable = []
      app.get('get_doorlifestate_data', params).then(data => {
        if (data.msg) {
          this.lifeStatetable = data.msg
        }
      })
    },
    // 进度条颜色
    customColorMethod(percentage) {
      if (percentage >= 90) {
        return 'red'
      } else {
        return '#909399'
      }
    }
  }
}
</script>
<style scoped>
	#lifemonitor {
		width: 100%;
    height: 100%;
    background-color: #242640;
	}

	#lifemonitor .query .el-input {
		width: 120px;
	}

  #lifemonitor .query .el-select{
    width:150px;
  }

	#lifemonitor .center {
		display: flex;
		-webkit-align-items: center;
		align-items: center;
		-webkit-justify-content: center;
		justify-content: center;
		border-radius: 7px 7px 0 0;
	}

	#lifemonitor .basicInfoStyle {
		margin-top: 20px;
		margin-left: 30px;
		color: white;
	}

	#lifemonitor .query {
		padding: 16px 15px 0px;
	}

	#lifemonitor .el-divider__text {
		position: absolute;
		background-color: #f6f6f8;
		padding: 0 20px;
		color: #303133;
	}
</style>

