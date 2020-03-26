<template>
  <section id="unhealthy" class="app-container">
    <el-row style="min-width:900px; height:100%">
      <!-- <left-slide :datas="webSocketFaultDatas" :display-quick-navigation="false" /> -->
      <el-col class="rightMaxStyle">
        <el-row>
          <el-col>
            <el-form
              ref="retrieveForm"
              :inline="true"
              class="query"
              :model="retrieveForm"
              :rules="formRules"
            >
              <el-form-item label="时间范围" prop="startTime">
                <el-date-picker
                  v-model="retrieveForm.startTime"
                  type="datetime"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  placeholder="选择日期时间"
                  style="width:192px;"
                  :editable="false"
                />
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
                <el-select v-model="retrieveForm.projectName" placeholder="项目名称" @change="projectChanged">
                  <el-option v-for="item in projectOptions" :key="item" :label="item" :value="item" />
                </el-select>
              </el-form-item>
              <el-form-item label="车辆" prop="trainNo">
                <el-select v-model="retrieveForm.trainNo" placeholder="请选择">
                  <el-option v-for="item in trainNos" :key="item" :label="item" :value="item" />
                </el-select>
              </el-form-item>
              <el-form-item label="车箱号" prop="carNo">
                <el-input v-model="retrieveForm.carNo" placeholder="请输入车厢号" />
              </el-form-item>
              <el-form-item label="门地址" prop="doorAddr">
                <el-input v-model="retrieveForm.doorAddr" placeholder="请输入门地址" />
              </el-form-item>
              <el-form-item label="预警名称" prop="subhealthName">
                <el-select v-model="retrieveForm.subhealthName" placeholder="请选择">
                  <el-option v-for="item in subhealthNames" :key="item" :label="item" :value="item" />
                </el-select>
              </el-form-item>

              <!-- <el-form-item label="是否调试" prop="debugMode">
                <el-select v-model="retrieveForm.debugMode" placeholder="请选择">
                  <el-option label="所有" value />
                  <el-option label="是" value="1" />
                  <el-option label="否" value="0" />
                </el-select>
              </el-form-item> -->
              <el-form-item>
                <el-button type="primary" icon="search" @click="queryButtonChecked()">查询</el-button>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        <el-row class="center" style="background-color:#0cc;color:white;height:30px">亚健康预警信息列表
        </el-row>
        <el-table v-loading="listLoading" :data="tableData" border highlight-current-row :max-height="tableMaxHeight" @row-dblclick="selectRow">
          <!-- <el-table-column type="selection" width="55" align="center"></el-table-column> -->
          <el-table-column prop="startTime" label="日期" align="center" />
          <el-table-column prop="subhealthName" label="预警名称" align="center" />
          <el-table-column prop="project" label="项目" align="center" />
          <el-table-column prop="trainNo" label="车辆" align="center" />
          <el-table-column prop="carNo" label="车厢号" width="120" align="center" />
          <el-table-column prop="doorAddr" label="门地址" width="120" align="center" />
          <el-table-column prop="accuracy" label="准确度" width="120" align="center" />
          <!-- <el-table-column prop="statistics" label="是否统计" width="120" align="center">
            <template slot-scope="scope">
              <el-button v-if="scope.row.statistics===true" type="text" style="color:black" size="small">是
              </el-button>
              <el-button v-else type="text" style="color:black" size="small">否</el-button>
            </template>
          </el-table-column>
          <el-table-column prop="debugMode" label="是否调试" width="120" align="center">
            <template slot-scope="scope">
              <el-button v-if="scope.row.debugMode===true" type="text" style="color:black" size="small">是
              </el-button>
              <el-button v-else type="text" style="color:black" size="small">否</el-button>
            </template>
          </el-table-column> -->
        </el-table>
        <!--分页  工具条-->
        <el-col :span="24" class="toolbar" style="position:absolute;bottom:0px;right:15px">
          <el-pagination
            :current-page.sync="currentPage"
            :page-sizes="[10, 20, 30, 50, 100]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            style="float: right;"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </el-col>
      </el-col>
    </el-row>
    <el-dialog class="detail-dialog" title="亚健康预警详情" :visible.sync="dialogVisible" :close-on-click-modal="false" fullscreen>
      <detail-part :subhealth="selectSubhealth" :subhealth-data="subhealthData" />
    </el-dialog>
  </section>
</template>
<script>
import app from '@/common/js/app'
import detailPart from './detail/subhealthDetailPart'
// import LeftSlide from '@/components/LeftSlide.vue'
const variables = ['电机电压', '电机电流', '编码器值', '开门时间', '关门时间']
const all = '所有'
export default {
  components: {
    // LeftSlide
    detailPart
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
        endTime: [{
          type: 'string',
          required: false,
          message: '请选择结束日期',
          trigger: 'change'
        }]
      },
      // 分页信息
      pageNum: 1,
      pageSize: 20,
      currentPage: 1,
      total: 0,
      listLoading: false,
      // 城市信息
      // 列车信息
      trainNos: [all],
      projectOptions: [all],
      // 获取亚健康名称
      subhealthNames: [],
      // 亚健康code
      subhealthCodes: [],
      // form表单
      retrieveForm: {
        startTime: '',
        endTime: '',
        subhealthName: all,
        trainNo: all,
        projectName: all,
        carNo: '',
        doorAddr: '',
        debugMode: ''
      },
      // 亚健康信息表
      tableData: [],
      webSocketFaultDatas: [],
      tableMaxHeight: document.body.offsetHeight - 280,
      dialogVisible: false,
      subhealthData: {},
      selectSubhealth: {}
    }
  },
  mounted() {
    // this.getCityName()
    this.getProject()
    this.getsubhealthName()
    this.getSubhealthDetails()
    this.onEventBus()
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeTableMaxHeight)
    this.changeTableMaxHeight()
  },
  beforeDestroy() {
    this.offEventBus()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeTableMaxHeight)
  },
  methods: {
    // 动态更改表格最大高度
    changeTableMaxHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 280
      var sectionHeight = document.body.offsetHeight - 50 + 'px'
      var element = document.getElementById('unhealthy')
      if (element !== null) {
        element.style.height = sectionHeight
      }
    },
    // 获取evenbus传送的数据
    onEventBus() {
      var vm = this
      this.$bus.$on('updateFaultMessage', function(data) {
        vm.updateFaultMessage(data)
      })
    },
    // 取消订阅信息
    offEventBus() {
      this.$bus.$off('updateFaultMessage')
    },
    // 将数据添加到左边栏 并且最多存储10条信息
    updateFaultMessage(faults) {
      this.webSocketFaultDatas = faults
    },
    // 查询按钮触发
    queryButtonChecked() {
      // 点击查询按钮后跳转到第一页
      this.currentPage = 1
      this.handleCurrentChange(1)
    },
    // 查询亚健康信息
    getSubhealthDetails() {
      this.$refs['retrieveForm'].validate(valid => {
        if (valid) {
          this.tableData = []
          this.listLoading = true
          var param = {
            startTime: this.retrieveForm.startTime,
            endTime: this.retrieveForm.endTime,
            subhealthName: this.retrieveForm.subhealthName,
            project: this.retrieveForm.projectName,
            trainNo: this.retrieveForm.trainNo,
            carNo: this.retrieveForm.carNo,
            doorAddr: this.retrieveForm.doorAddr,
            debugMode: this.retrieveForm.debugMode,
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
          if (this.retrieveForm.projectName === all) {
            param.project = ''
          }
          if (this.retrieveForm.trainNo === all) {
            param.trainNo = ''
          }
          if (this.retrieveForm.subhealthName === all) {
            param.subhealthName = ''
          }
          app.get('find_subhealthDetails', param).then(data => {
            if (data.msg.rows && data.msg.rows.length !== 0) {
              this.rolesOptions = []
              data.msg.rows.forEach(item => {
                this.tableData.push(item)
              })
              this.total = data.msg.total
            }
            this.listLoading = false
          })
        }
      })
    },
    // 获取项目名称
    getProject() {
      app.get('get_project').then(data => {
        if (data.msg) {
          data.msg.forEach(element => {
            this.projectOptions.push(element.name)
          })
        }
      })
    },
    // 初始化时获取亚健康信息
    getsubhealthName() {
      this.subhealthNames = [all]
      this.subhealthCodes = []
      this.retrieveForm.subhealthName = all
      app.get('get_subhealthDetails').then(data => {
        if (data.msg) {
          for (var i = 0; i < data.msg.length; i++) {
            this.subhealthNames.push(data.msg[i].typeName)
            this.subhealthCodes.push(data.msg[i].typeCode)
          }
        }
      })
    },
    projectChanged() {
      this.getVehicleNo()
    },
    getVehicleNo() {
      this.trainNos = [all]
      this.retrieveForm.trainNo = all
      const params = {
        project: this.retrieveForm.projectName
      }
      app.get('get_train_no', params).then(data => {
        if (data.msg) {
          data.msg.forEach(element => {
            this.trainNos.push(element.trainNo)
          })
        }
      })
    },

    // 分页触发
    handleCurrentChange(val) {
      this.pageNum = val
      this.getSubhealthDetails()
    },
    // 改变页码
    handleSizeChange(val) {
      this.pageSize = val
      this.getSubhealthDetails()
    },
    selectRow(row) {
      const times = []
      this.subhealthData = {}
      // TODO 查询故障数据
      app.get('get_subhealth_data', { id: row.id, variables: variables.join(',') }).then(data => {
        if (data.code === 0) {
          const msg = data.msg
          if (msg.keys != null) {
            const datas = msg.datas
            const keys = msg.keys
            const values = []
            for (let index = 0; index < keys.length; index++) {
              values.push([])
            }
            for (let index = 0; index < datas.length; index++) {
              const value = datas[index]
              times.push(value.timestamp.substr(11))
              for (let i = 0; i < keys.length; i++) {
                values[i].push(value.values[i])
              }
            }
            this.subhealthData = {
              keys: keys,
              times: times,
              data: values,
              state: msg.state,
              baseValues: msg.baseValues
            }
          }
          this.dialogVisible = true
          this.selectSubhealth = row
        }
      })
    }
  }
}
</script>

<style>
#unhealthy {
  width: 100%;
  height: 100%;
}
#unhealthy .query {
  padding: 16px 15px 0px;
}
#unhealthy .query .el-input {
	width: 120px;
}
#unhealthy .default-scrollbar {
  height: 100%;
}

/* #unhealthy .rightMaxStyle {
  width: 97%;
  margin-left: 3%;
} */

#ignoreBtn.el-button:focus {
  background-color: white;
}
#ignoreBtn.el-button--small.is-circle {
  padding: 0px;
}

.center {
	display: flex;
	-webkit-align-items: center;
	align-items: center;
	-webkit-justify-content: center;
	justify-content: center;
}

.detail-dialog .el-dialog__body{
  padding-top:5px;
}
</style>
