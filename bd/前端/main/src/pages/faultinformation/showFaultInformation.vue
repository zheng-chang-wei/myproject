<template>
  <section id="faultInfor" class="app-container fault-info">
    <el-row style="min-width:900px; height:100%">
      <!-- <left-slide :datas="webSocketFaultDatas" :display-quick-navigation="false" /> -->
      <el-col class="rightMaxStyle">
        <el-row>
          <el-form ref="retrieveForm" :inline="true" class="query" :model="retrieveForm" :rules="formRules">
            <!--时间选择器-->
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
            <el-form-item label="车厢号" prop="carNo">
              <el-input v-model="retrieveForm.carNo" placeholder="请输入车厢号" />
            </el-form-item>
            <el-form-item label="门地址" prop="doorAddr">
              <el-input v-model="retrieveForm.doorAddr" placeholder="请输入门地址" />
            </el-form-item>
            <!--故障名称下拉框-->
            <el-form-item label="故障名称" prop="faultName">
              <el-select
                v-model="retrieveForm.faultName"
                placeholder="请选择"
                @change="getFaultCode(retrieveForm.faultName)"
              >
                <el-option v-for="item in faultNames" :key="item" :label="item" :value="item" />
              </el-select>
            </el-form-item>
            <!--故障代码下拉框-->
            <el-form-item label="故障代码" prop="faultCode">
              <el-select
                v-model="retrieveForm.faultCode"
                placeholder="请选择"
                @change="getFaultName(retrieveForm.faultCode)"
              >
                <el-option v-for="item in faultCodes" :key="item" :label="item" :value="item" />
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
        </el-row>
        <!--故障信息table-->

        <el-row class="center" style="background-color:#00cccc;color:white;height:30px">故障信息列表
        </el-row>
        <el-table
          v-loading="listLoading"
          :data="tableData"
          border
          highlight-current-row
          :max-height="tableMaxHeight"
          @row-dblclick="selectRow"
        >
          <el-table-column prop="faultTime" label="日期" align="center" />
          <el-table-column prop="faultName" label="故障名称" align="center" />
          <el-table-column prop="faultCode" label="故障代码" width="120" align="center" />
          <el-table-column prop="project" label="项目" width="120" align="center" />
          <el-table-column prop="trainNo" label="车辆" width="120" align="center" />
          <el-table-column prop="carNo" label="车厢号" width="120" align="center" />
          <el-table-column prop="doorAddr" label="门地址" width="120" align="center" />
          <!-- <el-table-column prop="statistics" label="是否统计" width="120" align="center" >
            <template slot-scope="scope">
              <el-button v-if="scope.row.statistics===true" type="text" style="color:black" size="small">是
              </el-button>
              <el-button v-else type="text" style="color:black" size="small">否</el-button>
            </template>
          </el-table-column>
          <el-table-column prop="debugMode" label="是否调试" width="120" align="center" >
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
    <el-dialog class="detail-dialog" title="故障详情" :visible.sync="detailVisible" :close-on-click-modal="false" fullscreen destroy-on-close>
      <detail-part :fault="selectFault" :fault-data="faultData" />
    </el-dialog>
  </section>
</template>

<script>
import app from '@/common/js/app'
// import LeftSlide from '@/components/LeftSlide.vue'
import detailPart from './detail/faultDetailPart'
const all = '所有'
const variables = ['电机电压', '电机电流', '编码器值', '开门时间', '关门时间']
export default {
  components: {
    // LeftSlide
    detailPart
  },
  data() {
    return {
      // 表单验证方式
      formRules: {
        startTime: [
          {
            type: 'string',
            required: false,
            message: '请选择起始日期',
            trigger: 'change'
          }
        ],
        endTime: [
          {
            type: 'string',
            required: false,
            message: '请选择结束日期',
            trigger: 'change'
          }
        ]
      },
      // 分页信息
      pageNum: 1,
      pageSize: 20,
      // 当前页
      currentPage: 1,
      // 总共有多少行
      total: 0,
      // 表格数据正在加载
      listLoading: false,
      // 车辆号
      trainNos: [all],
      // 故障名称
      faultNames: [all],
      // 故障代码
      faultCodes: [all],
      projectOptions: [all],
      // form表单
      retrieveForm: {
        startTime: '',
        endTime: '',
        faultName: all,
        faultCode: all,
        trainNo: all,
        projectName: all,
        carNo: '',
        doorAddr: '',
        debugMode: ''
      },
      // 控制左边页面里ul的显示情况
      isShow: 'ulIsShow',
      // 故障信息表
      tableData: [],
      // 表格最大高度
      tableMaxHeight: document.body.offsetHeight - 280,
      // 左侧故障数据
      webSocketFaultDatas: [],
      detailVisible: false,
      selectFault: {},
      faultData: {}
    }
  },
  mounted() {
    this.getProject()
    // this.getFaultNames();
    this.onEventBus()
    this.getFaultData()
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeTableHeight)
    this.changeTableHeight()
  },
  beforeDestroy() {
    this.offEventBus()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeTableHeight)
  },
  methods: {
    changeTableHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 280
      var sectionHeight = document.body.offsetHeight - 50 + 'px'
      var element = document.getElementById('faultInfor')
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
    // 查询获取故障信息
    getFaultData() {
      this.$refs['retrieveForm'].validate(valid => {
        if (valid) {
          this.tableData = []
          this.listLoading = true
          var param = {
            startTime: this.retrieveForm.startTime,
            endTime: this.retrieveForm.endTime,
            faultName: this.retrieveForm.faultName,
            faultCode: this.retrieveForm.faultCode,
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
          if (this.retrieveForm.faultName === all) {
            param.faultName = ''
          }
          if (this.retrieveForm.faultCode === all) {
            param.faultCode = ''
          }
          // 查询故障信息
          app.get('get_fault_message', param).then(data => {
            if (data.msg.rows && data.msg.rows.length !== 0) {
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
    projectChanged() {
      this.getVehicleNo()
      this.getFaultInfoByProject()
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
    getFaultInfoByProject() {
      this.retrieveForm.faultName = all
      this.retrieveForm.faultCode = all
      app.get('get_fault_by_project', { project: this.retrieveForm.projectName }).then(data => {
        if (data.msg) {
          this.faultNames = [all]
          this.faultCodes = [all]
          for (var i = 0; i < data.msg.length; i++) {
            this.faultNames.push(data.msg[i].faultName)
            this.faultCodes.push(data.msg[i].faultCode)
          }
        }
      })
    },
    // 初始化时获取故障信息
    getFaultNames() {
      this.faultNames = []
      this.faultCodes = []
      app.get('get_fault_name').then(data => {
        if (data.msg) {
          for (var i = 0; i < data.msg.length; i++) {
            this.faultNames.push(data.msg[i].faultName)
            this.faultCodes.push(data.msg[i].faultCode)
          }
        }
      })
    },

    // 通过故障名称获取故障代码
    getFaultCode(faultName) {
      var index = this.faultNames.indexOf(faultName)
      this.retrieveForm.faultCode = this.faultCodes[index]
    },
    // 通过故障代码获取故障名称
    getFaultName(faultCode) {
      var index = this.faultCodes.indexOf(faultCode)
      this.retrieveForm.faultName = this.faultNames[index]
    },
    // 分页触发
    handleCurrentChange(val) {
      this.pageNum = val
      this.getFaultData()
    },
    // 改变页码
    handleSizeChange(val) {
      this.pageSize = val
      this.getFaultData()
    },
    selectRow(row) {
      console.log(row)
      const times = []
      this.faultData = {}
      app.get('get_fault_data', { id: row.id, variables: variables.join(',') }).then(data => {
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
            this.faultData = {
              keys: keys,
              times: times,
              data: values,
              state: msg.state,
              baseValues: msg.baseValues
            }
            console.log(this.faultData)
          }
          this.detailVisible = true
          this.selectFault = row
        }
      })
    }
  }
}
</script>

<style>
#faultInfor {
  width: 100%;
  height: 100%;
   background-color: #242640;
}

#faultInfor .query {
  padding: 16px 15px 0px;
}
#faultInfor .query .el-input {
	width: 120px;
}
/* #faultInfor .rightMaxStyle {
  width: 97%;
  margin-left: 3%;
} */
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
