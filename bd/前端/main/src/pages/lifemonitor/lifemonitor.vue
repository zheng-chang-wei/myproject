<template>
  <section id="lifemonitor" class="app-container">
    <el-row>
      <!-- 左侧滑动界面 -->
      <!-- <left-slide
        :datas="webSocketFaultDatas"
        :display-quick-navigation="true"
        @cityChange="leftSlideCityChange"
        @lineChange="leftSlideLineChange"
        @carChange="leftSlideCarChange"
      /> -->
      <el-row class="center" style="margin-top: 10px;  font-family:'微软雅黑';font-weight:400;font-style:normal;font-size:20px;color:#000000;">
        {{ title }}</el-row>
      <el-col :span="4">
        <!-- 基本信息 -->
        <el-row style="background-color:#AEAEAE;margin-top:20px; border-radius: 7px;border: none;">
          <el-row class="center" style="background-color:#666666;color:white;height:30px">基本信息</el-row>
          <el-row class="basicInfoStyle">车厢：{{ carriageName }}&nbsp;&nbsp;&nbsp;车门：{{ doorIndex }}</el-row>
          <el-row class="basicInfoStyle">门地址：{{ doorAddr }}</el-row>
          <el-row class="basicInfoStyle">MDCU1 ip: {{ mdcu1 }}</el-row>
          <el-row class="basicInfoStyle" style="margin-bottom:10px;">MDCU2 ip: {{ mdcu2 }}</el-row>
        </el-row>
      </el-col>
      <el-col :span="20">
        <!-- 车厢 -->
        <carriages v-if="carriageDatas.length>0" :carriages="carriageDatas" :carriage-span="carriageSpan" @doorChecked="doorChecked" />
      </el-col>
    </el-row>
    <el-row style="margin-top:10px">
      <el-col :span="24">
        <div style="text-align:center;background-color:#666666;color:white;height:30px;width:100%;font-weight:bold; border-radius:7px 7px 0 0;">
          <!--寿命状态隐藏-->
          <el-button
            v-if="isShow"
            style=" font-size: 37px;float: left;margin-top: -15px;box-shadow: 0 0 black;"
            icon="el-icon-caret-top"
            type="text"
            :circle="true"
            size="small"
            @click="isShow=!isShow"
          />
          <!--寿命状态展开-->
          <el-button
            v-else
            style=" font-size: 37px;float: left;margin-top: -15px;box-shadow: 0 0 black;"
            icon="el-icon-caret-bottom"
            type="text"
            :circle="true"
            size="small"
            @click="isShow=!isShow"
          />
          <el-row class="center" style="background-color:#666666;color:white;height:30px">{{ doorIndex }}号车门寿命状态
          </el-row>
        </div>
        <!--展开隐藏过度动画-->
        <transition name="el-zoom-in-top">
          <!--状态列表-->
          <el-table v-show="isShow" border :data="lifeStatetable" style="width: 100%">
            <el-table-column prop="itemName" label="部件名称" width="180" />
            <el-table-column prop="lifeValue" label="实际值" />
            <el-table-column prop="referenceValue" label="参考值" />
            <!--百分比列-->
            <el-table-column prop="lifePercentage" label="百分比">
              <template slot-scope="scope">
                <el-progress :text-inside="true" :stroke-width="15" :percentage="scope.row.lifePercentage" :color="customColorMethod(scope.row.lifePercentage)" />
              </template>
            </el-table-column>
          </el-table>
        </transition>
      </el-col>
    </el-row>
    <!--寿命预警查询-->
    <el-row style="margin-top:20px;">
      <el-divider>寿命预警信息查询</el-divider>
      <el-col :span="24">
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
          <el-form-item label="城市" prop="cityName">
            <el-select v-model="retrieveForm.cityName" placeholder="请选择" @change="changeCity(retrieveForm.cityName)">
              <el-option v-for="item in cityNames" :key="item" :label="item" :value="item" />
            </el-select>
          </el-form-item>
          <el-form-item label="线路" prop="lineName">
            <el-select v-model="retrieveForm.lineName" placeholder="请选择" @change="changeLine(retrieveForm.cityName,retrieveForm.lineName)">
              <el-option v-for="item in lineNames" :key="item" :label="item" :value="item" />
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
          <el-form-item label="是否调试" prop="debugMode">
            <el-select v-model="retrieveForm.debugMode" placeholder="请选择">
              <el-option label="所有" value />
              <el-option label="是" value="1" />
              <el-option label="否" value="0" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="search" @click="queryButtonChecked()">查询</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <!--寿命预警信息列表table-->
      <el-col :span="24">
        <el-row class="center" style="background-color:#666666;color:white;height:30px">寿命预警信息列表
        </el-row>
        <el-table v-loading="listLoading" :data="tableData" stripe border style="margin-top:10px;">
          <el-table-column prop="warningTime" label="日期" width="250" align="center" />
          <el-table-column prop="lifeItemName" label="部件名称" align="center" />
          <el-table-column prop="remainderLife" label="剩余寿命" align="center" />
          <el-table-column prop="cityName" label="城市" align="center" />
          <el-table-column prop="lineName" label="线路" align="center" />
          <el-table-column prop="trainNo" label="车辆" align="center" />
          <el-table-column prop="carNo" label="车厢号" align="center" />
          <el-table-column prop="doorAddr" label="门地址" align="center" />
          <el-table-column prop="debugMode" label="是否调试" align="center">
            <template slot-scope="scope">
              <el-button v-if="scope.row.debugMode===true" type="text" style="color:black" size="small">是
              </el-button>
              <el-button v-else type="text" style="color:black" size="small">否</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--分页  工具条-->
        <el-col :span="24" class="toolbar" style="margin-top:20px;">
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
  </section>
</template>

<script>
import app from '@/common/js/app'
// import LeftSlide from '@/components/LeftSlide.vue'
import carriages from '@/components/realtimemonitor/carriages.vue'
const all = '所有'
export default {

  components: {
    carriages
    // LeftSlide
  },
  data() {
    return {
      tempCarriageDatas: [],
      // 表格验证规则
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
        }]
      },
      // 是否展开状态寿命列表
      isShow: true,
      title: '',
      // 左侧栏数据源
      webSocketFaultDatas: [],
      city: '',
      lineNum: '',
      trainNum: '',
      carriageSpan: 3,
      carriageDatas: [],
      carriageName: '1', // 车厢名称
      doorIndex: 2, // 门号
      doorAddr: '2', // 门地址
      mdcu1: '',
      mdcu2: '',
      // 寿命预警列表
      tableData: [],
      // 分页信息
      pageNum: 1,
      pageSize: 20,
      currentPage: 1,
      total: 0,
      listLoading: false,
      // 选择的寿命预警信息列
      lifeWaringRow: {},
      // 城市信息
      cityNames: [all],
      // 线路信息
      lineNames: [all],

      trainNos: [all],
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
        cityName: all,
        lineName: all,
        trainNo: all,
        carNo: '',
        doorAddr: '',
        debugMode: ''
      },
      // 状态数据源
      lifeStatetable: []
    }
  },
  mounted() {
    this.timer3()
    this.getCityMessage()
    this.onEventBus()
    this.getCityName()
    this.getAllItemName()
    setTimeout(() => {
      this.sendMessage()
    }, 1000)
  },
  beforeDestroy() {
    this.offEventBus()
    clearInterval(this.timer3)
  },
  methods: {
    onEventBus() {
      var vm = this
      this.$bus.$on('updateFaultMessage', function(data) {
        vm.updateFaultMessage(data)
      })
      this.$bus.$on('updateRealTimeMessage', function(data) {
        vm.updateRealTimeMessage(data)
      })
    },
    offEventBus() {
      this.$bus.$off('updateFaultMessage')
      this.$bus.$off('updateRealTimeMessage')
    },
    timer3() {
      return setInterval(() => {
        for (var j = 0; j < this.carriageDatas.length; j++) {
          for (var k = 0; k < this.carriageDatas[j].doors.length; k++) {
            if (this.tempCarriageDatas.length !== 0) {
              if (this.tempCarriageDatas[j].doors[k].refresh === this.carriageDatas[j].doors[k].refresh) {
                this.carriageDatas[j].doors[k].state = ''
              }
            }
          }
        }
        this.copyCarriageDatas()
      }, 5000)
    },
    copyCarriageDatas() {
      this.tempCarriageDatas = []
      this.carriageDatas.forEach(carriage => {
        var tempCarriage = {}
        var tempDoors = []
        carriage.doors.forEach(door => {
          var tempDoor = {}
          tempDoor.refresh = door.refresh
          tempDoors.push(tempDoor)
        })
        tempCarriage.doors = tempDoors
        this.tempCarriageDatas.push(tempCarriage)
      })
    },
    // 将数据添加到左边栏 并且最多存储10条信息
    updateFaultMessage(faults) {
      this.webSocketFaultDatas = faults
    },
    // 处理实时数据
    updateRealTimeMessage(data) {
      // 获取实时数据
      var packet = data.packet
      // console.log(data);

      if (packet && packet.length !== 0) {
        // 筛选当前城市，线路，列车的数据
        if (
          this.city !== packet.city ||
						this.lineNum !== packet.line ||
						this.trainNum !== packet.train
        ) {
          return
        }
        var keys = packet.keys
        // 获取门状态的index
        var doorStateIndex = keys.indexOf('车门状态')
        // 获取车厢号的index
        var carriageNumIndex = keys.indexOf('车厢号')
        // 获取门地址的index
        var doorNumIndex = keys.indexOf('门地址')

        for (var i = 0; i < packet.frames.length; i++) {
          var values = packet.frames[i].values
          var state = values[doorStateIndex]
          var carriageNum = values[carriageNumIndex]
          // 门地址
          var doorNum = values[doorNumIndex]
          // 给每个门设置门状态
          this.setDoorState(carriageNum, doorNum, state)
        }
      }
    },
    setDoorState(carriageNum, doorNum, state) {
      for (var j = 0; j < this.carriageDatas.length; j++) {
        if (this.carriageDatas[j].name === carriageNum) {
          for (var k = 0; k < this.carriageDatas[j].doors.length; k++) {
            if (this.carriageDatas[j].doors[k].addr === doorNum) {
              var door = this.carriageDatas[j].doors[k]
              this.$set(door, 'state', state)
              if (door.refresh !== undefined) {
                // 给refresh设置一个上限，防止一直增加
                if (door.refresh > 99999) {
                  this.$set(door, 'refresh', 0)
                } else {
                  // 门状态刷新后，将refresh值加1，用于判断该门是否刷新了
                  this.$set(door, 'refresh', door.refresh + 1)
                }
              } else {
                this.$set(door, 'refresh', 0)
              }
            }
          }
        }
      }
    },
    leftSlideCityChange(city) {
      this.city = city
      this.title = this.city + '地铁'
      localStorage.setItem('city', city)
    },
    leftSlideLineChange(line) {
      this.lineNum = line
      this.title = this.city + '地铁' + this.lineNum + '号线'
      localStorage.setItem('lineNum', line)
    },
    leftSlideCarChange(car) {
      this.trainNum = car
      this.title = this.city + '地铁' + this.lineNum + '号线-' + this.trainNum + '号车'
      localStorage.setItem('trainNum', car)
      this.sendMessage()
    },
    getCityMessage() {
      this.city = localStorage.getItem('city')
      this.lineNum = localStorage.getItem('lineNum')
      this.trainNum = localStorage.getItem('trainNum')
      if (this.city && this.lineNum && this.trainNum) {
        this.title = this.city + '地铁' + this.lineNum + '号线-' + this.trainNum + '号车'
      }
    },
    // 触发Eventbus，发送车辆信息到后端
    sendMessage() {
      if (this.city && this.lineNum && this.trainNum) {
        var msg = "{'city': " + this.city + ",'line': " + this.lineNum + ",'train': " + this.trainNum + '}'
        this.$bus.$emit('cityChange', msg)
        this.getTrainConfig()
      }
    },
    // 车厢门点击事件，可以通过该点击事件获取  车厢 车门等信息。
    doorChecked(carriage, doorIndex) {
      this.carriageName = carriage.name
      this.doorAddr = carriage.doors[doorIndex - 1].addr
      this.doorIndex = doorIndex
      this.mdcu1 = carriage.mdcus[0].ip
      this.mdcu2 = carriage.mdcus[1].ip
      this.getDoorLifeStateDatas()
    },
    // 获取车辆配置信息
    getTrainConfig() {
      const params = {
        cityName: this.city,
        lineName: this.lineNum,
        trainNo: this.trainNum
      }
      app.get('get_train_config_info', params).then(data => {
        if (data.msg) {
          const train = JSON.parse(data.msg)
          if (train != null) {
            const cars = train.cars
            // 删除前两节车厢，车头和车尾
            cars.splice(0, 2)
            this.carriageDatas = cars
            // 如果车箱数超过10节，一节车厢占2列
            if (cars.length > 10) {
              this.carriageSpan = 2
            } else {
              this.carriageSpan = 3
            }
          }
        }
      })
    },
    // 根据列车信息获取该车门寿命状态
    getDoorLifeStateDatas() {
      const params = {
        cityName: this.city,
        lineName: this.lineNum,
        trainNo: this.trainNum,
        doorAddr: this.doorAddr,
        carNo: this.carriageName
      }
      this.lifeStatetable = []
      app.get('get_doorlifestate_data', params).then(data => {
        if (data.msg) {
          this.lifeStatetable = data.msg
        }
      })
    },
    // 查询按钮触发
    queryButtonChecked() {
      // 点击查询按钮后跳转到第一页
      this.currentPage = 1
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
            cityName: this.retrieveForm.cityName,
            lineName: this.retrieveForm.lineName,
            trainNo: this.retrieveForm.trainNo,
            carNo: this.retrieveForm.carNo,
            doorAddr: this.retrieveForm.doorAddr,
            debugMode: this.retrieveForm.debugMode,
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
          if (this.retrieveForm.cityName === all) {
            param.cityName = ''
          }
          if (this.retrieveForm.lineName === all) {
            param.lineName = ''
          }
          if (this.retrieveForm.trainNo === all) {
            param.trainNo = ''
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
              this.total = data.msg.total
            }
            this.listLoading = false
          })
        }
      })
    },
    // 初始化时获取所有城市名称
    getCityName() {
      app.get('get_city_name').then(data => {
        if (data.msg) {
          for (var i = 0; i < data.msg.length; i++) {
            this.cityNames.push(data.msg[i].cityName)
          }
        }
      })
    },

    // 获取全部部件信息
    getAllItemName() {
      app.get('get_lifeItemNames').then(data => {
        if (data.msg) {
          for (var i = 0; i < data.msg.length; i++) {
            this.lifeItemNames.push(data.msg[i].itemName)
            this.lifeItemIds.push(data.msg[i].id)
          }
        }
      })
    },
    // 通过部件名称获取部件id
    getItemCode(itemName) {
      var index = this.lifeItemNames.indexOf(itemName)
      this.retrieveForm.lifeItemId = this.lifeItemIds[index]
    },
    // 通过城市获取线路名称
    changeCity(cityName) {
      this.lineNames = [all]
      this.trainNos = [all]
      this.retrieveForm.lineName = all
      this.retrieveForm.trainNo = all
      if (cityName === all) {
        return
      }
      const param = {
        cityName: cityName
      }
      app.get('get_lines_by_cityName', param).then(data => {
        if (data.msg) {
          for (var i = 0; i < data.msg.length; i++) {
            this.lineNames.push(data.msg[i].lineName)
          }
        }
      })
    },
    // 根据城市和线路获取部件信息
    changeLine(cityName, lineName) {
      this.trainNos = [all]
      this.retrieveForm.trainNo = all
      this.retrieveForm.lifeItemName = all
      const param = {
        cityName: cityName,
        lineName: lineName
      }
      this.getTrainNosByCityNameAndLineName(param)
      this.getlifeItemNameByCityNameAndLineName(param)
    },
    getTrainNosByCityNameAndLineName(param) {
      app.get('get_train_by_trainParam', param).then(data => {
        if (data.msg) {
          this.trainNos = [all]
          for (var i = 0; i < data.msg.length; i++) {
            this.trainNos.push(data.msg[i].trainNo)
          }
        }
      })
    },
    getlifeItemNameByCityNameAndLineName(param) {
      app.get('get_lifeItemName_by_cityName_lineName', param).then(data => {
        if (data.msg) {
          this.lifeItemNames = [all]
          this.lifeItemIds = [all]
          for (var i = 0; i < data.msg.length; i++) {
            this.lifeItemNames.push(data.msg[i].itemName)
            this.lifeItemIds.push(data.msg[i].id)
          }
        }
      })
    },
    // 分页触发
    handleCurrentChange(val) {
      this.pageNum = val
      this.getLifeMonitorData()
    },
    // 改变页码
    handleSizeChange(val) {
      this.pageSize = val
      this.getLifeMonitorData()
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

<style>
	#lifemonitor {
		width: 100%;
		height: 100%;
	}

	#lifemonitor .query .el-input {
		width: 120px;
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
