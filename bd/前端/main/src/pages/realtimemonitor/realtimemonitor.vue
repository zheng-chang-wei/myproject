<template>
  <section id="realtimemonitor" class="app-container">
    <project-select :signal-quality="signalQuality" @getTrainConfig="getTrainConfig" />
    <el-row class="common">
      <el-col :span="4">
        <el-row id="basicinformation" class="basicinformation-panel home-card" style=" height:200px">
          <div class="card-border card-border-top-left" />
          <div class="card-border card-border-top-right" />
          <div class="card-border card-border-bottom-left" />
          <div class="card-border card-border-bottom-right" />
          <div class="card-panel">
            <div class="card-title">车门信息</div>
            <el-row class="basicinfo-style">车厢：{{ carriageName }}&nbsp;&nbsp;&nbsp;门地址：{{ doorAddr }}</el-row>
            <el-row class="basicinfo-style">门状态：{{ doorState }}</el-row>
            <el-row class="basicinfo-style">门控器版本：{{ doorVersion }}</el-row>
            <el-row class="basicinfo-style">{{ doorOpenOrCloseTime }}ms</el-row>
            <el-row class="basicinfo-style">公里数：{{ kilometres }}</el-row>
            <el-row class="basicinfo-style">地点：{{ location }}</el-row>
            <el-row class="basicinfo-style">上下行：{{ direction }}</el-row>
          </div>
        </el-row>
      </el-col>
      <el-col :span="20">
        <el-row class="carriages-panel home-card" style=" height:200px">
          <div class="card-border card-border-top-left" />
          <div class="card-border card-border-top-right" />
          <div class="card-border card-border-bottom-left" />
          <div class="card-border card-border-bottom-right" />
          <div class="card-panel">
            <div class="card-title">列车示意图</div>
            <carriages
              v-if="carriages.length > 0"
              :carriages="carriages"
              :carriage-span="carriageSpan"
              @doorChecked="doorChecked"
            />
          </div>
        </el-row>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="14">
        <analog-line style="height:420px" />
      </el-col>
      <el-col :span="10">
        <digital-line style="height:420px" @showDigitalSignal="showDigitalSignal" />
      </el-col>
    </el-row>
    <el-row class="dataanalysis-panel home-card common">
      <div class="card-border card-border-top-left" />
      <div class="card-border card-border-top-right" />
      <div class="card-border card-border-bottom-left" />
      <div class="card-border card-border-bottom-right" />
      <div class="card-title">车门数据</div>
      <el-row style="background-color:#AEAEAE;margin-bottom:50px">
        <el-menu :default-active="activeIndex" mode="horizontal" background-color="#242640" text-color="#fff" active-text-color="#ffd04b" @select="handleMenuSelect">
          <el-menu-item index="1">车辆信号</el-menu-item>
          <el-menu-item index="2">输入输出信号</el-menu-item>
          <el-menu-item index="3">电机与指令信号</el-menu-item>
        </el-menu>
        <el-table
          :data="tableData"
          :border="true"
          highlight-current-row
          max-height="440"
          background-color="#242640"
          :header-cell-style="{background:'#0f1741',color:'#fff',padding:'3px'}"
          :row-style="{background:'#0f1741',color:'#fff'}"
          :cell-style="{padding:'4px',borderColor:'#999999'}"
        >
          <el-table-column prop="signalName" label="信号名称" align="center" width="180" />
          <template v-if="carriages[0]">
            <el-table-column v-for="(item) in unevenNumber" :key="item.addr" :prop="'door'+item.addr" :label="'车门'+item.addr" align="center">
              <template slot-scope="scope">
                <img v-if="commandSignals.indexOf(scope.row['signalName'])==-1&&scope.row['signalName']!='关门时间'" style="width: 20%;height:20%" :src="scope.row['door'+item.addr]=='1'?require('../../../static/imgs/green.png'):require('../../../static/imgs/yellow.svg')" alt>
                <span v-else>{{ scope.row['door'+item.addr] }}</span>
              </template>
            </el-table-column>
            <el-table-column v-for="(item) in evenNumber" :key="item.addr" :prop="'door'+item.addr" :label="'车门'+item.addr" align="center">
              <template slot-scope="scope">
                <img v-if="commandSignals.indexOf(scope.row['signalName'])==-1&&scope.row['signalName']!='关门时间'" style="width: 20%;height:20%" :src="scope.row['door'+item.addr]=='1'?require('../../../static/imgs/green.png'):require('../../../static/imgs/yellow.svg')" alt>
                <span v-else>{{ scope.row['door'+item.addr] }}</span>
              </template>
            </el-table-column>
          </template>
        </el-table>
      </el-row>
    </el-row>
  </section>
</template>

<script>
import app from '@/common/js/app'
import carriages from '@/components/realtimemonitor/carriages.vue'
import AnalogLine from './datachart/AnalogLine'
import DigitalLine from './datachart/DigitalLineGroup'
import projectSelect from './components/projectSelect'
// 曲线图中显示多少个点
const chartDataBuffer = 100
// 车辆信号
const trainSignals = [
  '门防挤压',
  '门隔离',
  '门紧急解锁',
  '门开好',
  '门关好',
  '门动作中',
  '门零速',
  '门使能',
  '门控制选择信号',
  '再开闭信号反馈',
  '检测到障碍物',
  '关门信号反馈',
  '开门信号反馈',
  '门存在故障',
  '服务按钮信号',
  '集控开门信号',
  '集控关门信号',
  '集控再开闭信号',
  '零速信号',
  '隔离信号',
  '紧急解锁信号',
  '闭锁开关信号',
  '左门板开关信号',
  '右门板开关信号',
  '关门时间',
  '开门过程',
  '关门过程',
  '防挤压过程中',
  '防挤压停'
]
// 输入输出信号
const inputAndOutputSignals = [
  '门地址编码1',
  '门地址编码2',
  '门地址编码3',
  '门地址编码4',
  '开关门指示灯输出',
  '蜂鸣器输出'
]
// 电机指令信号
const commandSignals = ['电机电压', '电机电流', '编码器']
// 门状态
const doorStates = [
  '隔离',
  '紧急解锁',
  '门故障',
  '防挤压',
  '门完全打开',
  '关门过程中',
  '开门过程中',
  '门完全关闭'
]
const colors = ['#FF0000', '#FFFF00', '#0000FF', '#008000', '#FFA500', '#F0F8FF', '#00FFFF', '#8A2BE2', '#7FFF00', '#FF00FF', '#FF69B4', '#EEE8AA', '#FF4500', '#7FFFD4', '#5F9EA0', '#FF0000', '#FFFF00', '#0000FF', '#008000', '#FFA500', '#F0F8FF', '#00FFFF', '#8A2BE2', '#7FFF00', '#FF00FF', '#FF69B4', '#EEE8AA', '#FF4500', '#7FFFD4']
export default {
  components: {
    carriages,
    AnalogLine,
    DigitalLine,
    projectSelect
  },
  data() {
    return {
      analogSignalData: {
        times: [],
        values: [
          [],
          [],
          []
        ]
      },
      carriagesStyle: { 'margin-left': (100 - (12.5 * 6 + 8.33333 * 2)) / 2 + '%' },
      digitalSignalChartDatas: [],
      digitalSignals: [],
      // 城市线路车号：深圳地铁7号线-717号车
      title: '',
      // 每节车厢占用的列数
      carriageSpan: 3,

      // 故障数据
      faultDatas: [],
      // 列车信息
      carriages: [],
      // 基本信息中车厢名称
      carriageName: '1',
      // 基本信息中门地址
      doorAddr: 1,
      // 门状态
      doorState: '通讯故障',
      doorOpenOrCloseTime: '开门时间：0',
      // 电机电流
      motorCurrent: '',
      // 门控器版本
      doorVersion: '',
      // 公里数
      kilometres: '',
      // 地点
      location: '',
      // 上下行
      direction: '',
      // 曲线所有颜色
      colors: colors,
      trainSignals: trainSignals,

      inputAndOutputSignals: inputAndOutputSignals,

      commandSignals: commandSignals,
      // 缓存
      trainSignalList: [],
      inputAndOutputSignalList: [],
      commandSignalList: [],

      projectOptions: [],
      trainNoOptions: [],

      // 当前显示菜单所在页，1表示车辆信号，2表示输入输出信号，3表示电机与指令信号
      activeIndex: '1',
      // 表格数据
      tableData: [],
      // 临时列车数据，用于保存5秒前的列车数据
      tempCarriageDatas: [],
      count: 0,
      signalQuality: 0,
      timer1: null,
      timer3: null,
      timer4: null
    }
  },
  computed: {
    unevenNumber: function() {
      if (this.carriageName === '1' || this.carriageName === this.carriages.length + '') {
        return this.carriages[0].doors.filter(function(door) {
          return door.addr % 2 !== 0
        })
      } else {
        return this.carriages[1].doors.filter(function(door) {
          return door.addr % 2 !== 0
        })
      }
    },
    evenNumber: function() {
      if (this.carriageName === '1' || this.carriageName === this.carriages.length + '') {
        return this.carriages[0].doors.filter(function(door) {
          return door.addr % 2 === 0
        })
      } else {
        return this.carriages[1].doors.filter(function(door) {
          return door.addr % 2 === 0
        })
      }
    }
  },
  created() {
    this.timer()
  },
  mounted() {
    this.initTableData()
    this.onEventBus()
  },
  beforeDestroy() {
    this.offEventBus()
    this.clearLine()
    clearInterval(this.timer1)
    clearInterval(this.timer3)
    clearInterval(this.timer4)
  },
  methods: {
    timer() {
      this.timer1 = setInterval(() => {
        this.$bus.$emit('drawAnalogSignalLine', this.analogSignalData)
        this.$bus.$emit('drawdigitalSignalLine', this.digitalSignalChartDatas)
      }, 1000)
      this.timer3 = setInterval(() => {
        for (let j = 0; j < this.carriages.length; j++) {
          for (let k = 0; k < this.carriages[j].doors.length; k++) {
            if (this.tempCarriageDatas.length !== 0) {
              if (this.tempCarriageDatas[j].doors[k].refresh === this.carriages[j].doors[k].refresh) {
                this.carriages[j].doors[k].state = ''
              }
            }
          }
        }
        this.copyCarriageDatas()
      }, 5000)
      this.timer4 = setInterval(() => {
        if (this.count > 10) {
          this.signalQuality = 100
        } else {
          this.signalQuality = (this.count / 10) * 100
        }
        this.count = 0
      }, 5000)
    },
    // 拷贝列车数据
    copyCarriageDatas() {
      this.tempCarriageDatas = []
      this.carriages.forEach(carriage => {
        const tempCarriage = {}
        const tempDoors = []
        carriage.doors.forEach(door => {
          const tempDoor = {}
          tempDoor.refresh = door.refresh
          tempDoors.push(tempDoor)
        })
        tempCarriage.doors = tempDoors
        this.tempCarriageDatas.push(tempCarriage)
      })
    },
    onEventBus() {
      const vm = this
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

    updateFaultMessage(faults) {
      this.faultDatas = faults
    },
    // 处理实时数据
    updateRealTimeMessage(data) {
      // 获取实时数据
      const packet = data.packet

      if (packet && packet.length !== 0) {
        this.count++
        const keys = packet.keys
        // 获取门状态的index
        const doorStateIndex = keys.indexOf('车门状态')
        // 获取车厢号的index
        const carriageNumIndex = keys.indexOf('车厢号')
        // 获取门地址的index
        const doorNumIndex = keys.indexOf('门地址')

        const valueList = []
        const keyList = []
        for (let i = 0; i < packet.frames.length; i++) {
          const values = packet.frames[i].values
          // 门状态
          const state = values[doorStateIndex]
          // 车厢号
          const carriageNum = values[carriageNumIndex]
          // 门地址
          const doorNum = parseInt(values[doorNumIndex])

          // 刷新开关门状态和开关门时间
          if (carriageNum === this.carriageName && doorNum === this.doorAddr) {
            this.doorState = state
            this.motorCurrent = values[keys.indexOf('电机电流')]
            switch (state) {
              case doorStates[5]: // 关门过程中
                this.doorOpenOrCloseTime =
                  '关门时间：' + values[keys.indexOf('关门时间')]
                break
              case doorStates[6]: // 开门过程中
                this.doorOpenOrCloseTime =
                  '开门时间：' + values[keys.indexOf('开门时间')]
                break
              default:
            }
          }
          // 给每个门设置门状态
          this.setDoorState(carriageNum, doorNum, state)

          // 给实时监控界面最下面的表格数据赋值
          this.setTableData(carriageNum, doorNum, values, keys)

          if (carriageNum === this.carriageName && doorNum === this.doorAddr) {
            // 给当前选中的车辆信号曲线赋值
            // this.setSignalChartData(keys, values);
            valueList.push(values)
            keyList.push(keys)
          }
        }
        this.refreshSignalChartData(keyList, valueList)
      }
    },
    // 获取车辆配置信息
    getTrainConfig(parms) {
      this.clearLine()
      app.get('get_train_config_info', parms).then(data => {
        if (data.msg) {
          const train = JSON.parse(data.msg)
          if (train != null) {
            const cars = train.cars
            this.carriages = cars
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
    initTableData() {
      this.activeIndex = '1'
      this.tableData = []
      for (let i = 0; i < trainSignals.length; i++) {
        this.tableData.push({
          signalName: trainSignals[i]
        })
      }
    },
    // 导航菜单选择时给表数据的第一列赋值
    handleMenuSelect(key) {
      this.activeIndex = key
      this.tableData = []
      switch (key) {
        case '1': // "车辆信号"
          for (let i = 0; i < trainSignals.length; i++) {
            this.tableData.push({
              signalName: trainSignals[i]
            })
          }
          break
        case '2': // 输入输出信号
          for (let j = 0; j < inputAndOutputSignals.length; j++) {
            this.tableData.push({
              signalName: inputAndOutputSignals[j]
            })
          }
          break
        case '3': // 电机与指令信号
          for (let k = 0; k < commandSignals.length; k++) {
            this.tableData.push({
              signalName: commandSignals[k]
            })
          }
          break
      }
    },
    doorChecked(carriage, doorIndex) {
      if (this.carriageName !== carriage.name || this.doorAddr !== doorIndex) {
        this.clearLine()
      }
      this.carriageName = carriage.name
      this.doorAddr = carriage.doors[doorIndex - 1].addr

      this.doorState = ''
      this.motorCurrent = ''
      this.doorOpenOrCloseTime = '开门时间：0'
      this.initTableData()
    },
    setDoorState(carriageNum, doorNum, state) {
      for (let j = 0; j < this.carriages.length; j++) {
        if (this.carriages[j].name === carriageNum) {
          for (let k = 0; k < this.carriages[j].doors.length; k++) {
            if (this.carriages[j].doors[k].addr === parseInt(doorNum)) {
              const door = this.carriages[j].doors[k]
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
    setTableData(carriageNum, doorNum, values, keys) {
      if (carriageNum === this.carriageName) {
        for (let j = 0; j < this.carriages.length; j++) {
          if (this.carriages[j].name === carriageNum) {
            for (let k = 0; k < this.carriages[j].doors.length; k++) {
              if (this.carriages[j].doors[k].addr === doorNum) {
                for (let n = 0; n < this.tableData.length; n++) {
                  const obj = this.tableData[n]
                  switch (this.activeIndex) {
                    case '1': // "车辆信号"
                      obj['door' + (k + 1)] =
                        values[keys.indexOf(trainSignals[n])]
                      obj['signalName'] = trainSignals[n]
                      break
                    case '2': // 输入输出信号
                      obj['door' + (k + 1)] =
                        values[keys.indexOf(inputAndOutputSignals[n])]
                      obj['signalName'] = inputAndOutputSignals[n]
                      break
                    case '3': // 电机与指令信号
                      obj['door' + (k + 1)] =
                        values[keys.indexOf(commandSignals[n])]
                      obj['signalName'] = commandSignals[n]
                      break
                  }
                  this.$set(this.tableData, n, obj)
                }
              }
            }
          }
        }
      }
    },
    refreshSignalChartData(keyList, valueList) {
      for (let i = 0; i < keyList.length; i++) {
        const values = valueList[i]
        const keys = keyList[i]
        const time = values[keys.indexOf('时间')]
        this.analogSignalData.times.push(time)
        for (let j = 0; j < commandSignals.length; j++) {
          this.analogSignalData.values[j].push(values[keys.indexOf(commandSignals[j])])
        }
        if (this.analogSignalData.times.length >= chartDataBuffer) {
          this.analogSignalData.times.shift()
          for (let j = 0; j < this.analogSignalData.values.length; j++) {
            this.analogSignalData.values[j].shift()
          }
        }
        for (let j = 0; j < this.digitalSignals.length; j++) {
          const obj = {
            'time': time
          }
          obj[this.digitalSignals[j]] = values[keys.indexOf(this.digitalSignals[j])]
          this.digitalSignalChartDatas[j].rows.push(obj)
          if (this.digitalSignalChartDatas[j].rows.length >= chartDataBuffer) {
            this.digitalSignalChartDatas[j].rows.shift()
          }
        }
      }
    },
    showDigitalSignal(variables) {
      this.digitalSignals = variables
      this.digitalSignalChartDatas = []
      for (let j = 0; j < this.digitalSignals.length; j++) {
        this.digitalSignalChartDatas.push({
          'columns': ['time', this.digitalSignals[j]],
          'rows': []
        })
      }
    },
    clearLine() {
      this.analogSignalData = {
        times: [],
        values: [[], [], []]
      }
      this.digitalSignalChartDatas = []
    }
  }
}
</script>

<style lang="scss">$border-color: rgb(54, 227, 253);

.card-border {
  position: absolute;
  width: 15px;
  height: 15px;
}

#realtimemonitor {
  color: #fff;
  padding: 2px;
  background-color: #0f1741;
  font-size: small;
  font-size: 10px
}

#realtimemonitor .common {
  margin: 2px 0 0 2px
}

#realtimemonitor .el-checkbox__label {
  color: #fff;
  margin-top: 15px;
}

#realtimemonitor .home-card {
  border: 1px solid rgb(54, 88, 141);
  box-shadow: rgb(105, 149, 02) 0px 0px 8px inset;
  padding: 10px;
  position: relative;
}

#realtimemonitor .card-border-top-left {
  top: -1px;
  left: -1px;
}

#realtimemonitor .card-border-top-right {
  top: -1px;
  right: -1px;
}

#realtimemonitor .card-border-bottom-left {
  bottom: -1px;
  left: -1px;
}

#realtimemonitor .card-border-bottom-right {
  bottom: -1px;
  right: -1px;
}

#realtimemonitor #carriages {
  height: 208px;
  color: black;
}

#realtimemonitor .card-title {
  text-align: center;
  background-color: #00cccc;
  font-size: medium;
}

#realtimemonitor .basicinfo-style {
  margin-top: 9px;
  margin-left: 9px;
  position: relative;
}

#realtimemonitor .lineColor {
  display: flex;
  -webkit-align-items: center;
  align-items: center;
  -webkit-justify-content: center;
  justify-content: center;
  width: 10px;
  height: 10px;
  margin-top: 23.2px;
}

.center {
  display: flex;
  -webkit-align-items: center;
  align-items: center;
  -webkit-justify-content: center;
  justify-content: center;
}

.verticalcenter_horizontalright {
  height: 100%;
  display: flex;
  -webkit-align-items: center;
  align-items: center;
  -webkit-justify-content: center;
  justify-content: center;
}

#realtimemonitor .el-select {
  width: 100%;
}

#realtimemonitor .el-table__body tr.current-row>td {
  background-color: #f19944 !important;
}

#realtimemonitor .image-style {
  float: right;
  margin-right: 10px
}

#realtimemonitor .el-table--enable-row-hover .el-table__body tr:hover>td {
  background-color: #0f1741 !important;
}

#realtimemonitor .el-table,
.el-table__expanded-cell {
  background-color: #0f1741 !important;
}

#realtimemonitor .el-table--border::after,
.el-table--group::after,
.el-table::before {
  background-color: #999999
}

.alignBottomParent {
  display: flex;
  align-items: flex-end;
}

.alignBottomItem {
  display: table-cell;
  vertical-align: bottom;
}

</style>
