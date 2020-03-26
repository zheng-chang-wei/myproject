<template>
  <section id="realtimemonitor">
    <el-row class="center common">
      <el-col :span="5">
        <el-select v-model="projectName" placeholder="项目名称" size="mini" @change="getTrainNo(true)">
          <el-option v-for="item in projectOptions" :key="item" :label="item" :value="item" />
        </el-select>
      </el-col>
      <el-col :span="5" style="margin-left:10px">
        <el-select v-model="trainNo" size="mini" placeholder="车辆编号" @change="trainNoChange">
          <el-option v-for="item in trainNoOptions" :key="item" :label="item" :value="item" />
        </el-select>
      </el-col>
      <el-col :span="14">
        <img class="image-style" :src="require('../../../static/imgs/back.svg')" style="cursor: pointer;"
          @click="toHome">
        <signalQuality :signal-quality="signalQuality" class="image-style" />
      </el-col>
    </el-row>
    <el-row class="common">
      <el-col :span="4">
        <el-row class="home-card" :style="{'height':carHeight}">
          <div class="card-title">车门信息</div>
          <el-row class="basicinfo-style">车厢：{{ carriageName }}&nbsp;&nbsp;&nbsp;门地址：{{ doorAddr }}</el-row>
          <el-row class="basicinfo-style">门状态：{{ doorState }}&nbsp;&nbsp;&nbsp;门控器版本：{{ doorVersion }}</el-row>
          <el-row class="basicinfo-style">{{ doorOpenOrCloseTime }}ms</el-row>
          <el-row class="basicinfo-style">公里数：{{ kilometres }}</el-row>
          <el-row class="basicinfo-style">地点：{{ location }}</el-row>
          <el-row class="basicinfo-style">上下行：{{ direction }}</el-row>
        </el-row>
      </el-col>
      <el-col :span="20">
        <el-row class="home-card" :style="{'height':carHeight}">
          <div class="card-title">列车示意图</div>
          <carriages :style="carriagesStyle" v-if="carriages.length > 0" :carriages="carriages"
            :carriage-span="carriageSpan" @doorChecked="doorChecked" />
        </el-row>
      </el-col>
    </el-row>
    <el-row class="home-card common">
      <div class="card-title">车辆信号曲线</div>
      <el-row>
        <el-col :span="20">
          <ve-line :height="lineHeight+'px'" :data="{columns:chartColumns, rows:trianSignalChartData}"
            :settings="chartSettings.chart" :extend="extend" :tooltip-visible="false" />
        </el-col>
        <el-col :span="4" :style="{'height':lineHeight+'px','overflow-y':'auto'}">
          <el-checkbox-group v-model="checkedTrainSignals">
            <el-checkbox v-for="(signal,index) in trainSignals" :key="signal" :label="signal">
              <span>{{ signal }}</span>
              <span :style="lineColor(signal,index)">&nbsp;&nbsp;&nbsp;&nbsp;</span>
            </el-checkbox>
          </el-checkbox-group>
        </el-col>
      </el-row>
    </el-row>
    <el-row class=" home-card common">
      <div class="card-title">电机与指令信号</div>
      <el-row>
        <el-col :span="20">
          <ve-line :height="lineHeight+'px'" :data="{columns:chartColumns, rows:commandChartData}"
            :settings="chartSettings.chart" :extend="extend" :tooltip-visible="false" />
        </el-col>
        <el-col :span="4"
          :style="{'height':(lineHeight/2+50)+'px','overflow-y':'auto','margin-top':(lineHeight/2-50)+'px'}">
          <el-checkbox-group v-model="checkedCommandSignals">
            <el-checkbox v-for="(signal,index) in commandSignals" :key="signal" :label="signal">
              <span>{{ signal }}</span>
              <span :style="lineColor(signal,index)">&nbsp;&nbsp;&nbsp;&nbsp;</span>
            </el-checkbox>
          </el-checkbox-group>
        </el-col>
      </el-row>
    </el-row>
  </section>
</template>

<script>
import app from '@/common/js/app'
import carriages from '@/components/realtimemonitor/carriages.vue'
import signalQuality from '@/components/realtimemonitor/signalQuality.vue'
import VeLine from 'v-charts/lib/line.common'
const labelMapChart = {
  x: '',
  x1: '',
  x2: '',
  x3: '',
  x4: '',
  x5: '',
  x6: '',
  x7: '',
  x8: '',
  x9: '',
  x10: '',
  x11: '',
  x12: '',
  x13: '',
  x14: '',
  x15: '',
  x16: '',
  x17: '',
  x18: '',
  x19: '',
  x20: '',
  x21: '',
  x22: '',
  x23: '',
  x24: '',
  x25: '',
  x26: '',
  x27: '',
  x28: '',
  x29: '',
  x30: '',
  x31: '',
  x32: '',
  x33: '',
  x34: '',
  x35: ''
}
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
  '防挤压停',
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
const colors = ['#FF0000', '#FFFF00', '#0000FF', '#008000', '#FFA500', '#F0F8FF', '#00FFFF', '#8A2BE2', '#7FFF00', '#FF00FF', '#FF69B4', '#EEE8AA', '#FF4500', '#7FFFD4', '#5F9EA0', '#FF0000', '#FFFF00', '#0000FF', '#008000', '#FFA500', '#F0F8FF', '#00FFFF', '#8A2BE2', '#7FFF00', '#FF00FF', '#FF69B4', '#EEE8AA', '#FF4500', '#7FFFD4', '#5F9EA0', '#FF0000', '#FFFF00', '#0000FF', '#008000', '#FFA500']
export default {
  components: {
    VeLine,
    carriages,
    signalQuality
  },
  data () {
    return {
      extend: {
        series: {
          smooth: false,
          symbol: 'none',
          type: 'line',
          animation: false
        },
        grid: {
          show: true,
          top: 7,
          bottom: 2,
          left: 20,
          right: 20
          // backgroundColor: '#FFFFFF'
        },
        color: colors,
        textStyle: {
          color: '#FFFFFF'
        }

      },
      lineHeight: 0,
      carriagesStyle: {'margin-left': (100 - (12.5 * 6 + 8.33333 * 2)) / 2 + '%'},
      carHeight: '20%',
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
      trianSignalChartData: [], // 车辆信号曲线
      checkedTrainSignals: ['门隔离', '门紧急解锁', '门零速', '关门信号反馈', '开门信号反馈'],
      trainSignals: trainSignals,
      commandChartData: [], // 电机与指令曲线
      checkedCommandSignals: ['电机电流'],
      commandSignals: commandSignals,
      // 缓存
      trainSignalList: [],
      commandSignalList: [],

      projectOptions: [],
      trainNoOptions: [],
      // 临时列车数据，用于保存5秒前的列车数据
      tempCarriageDatas: [],
      trainNo: '',
      projectName: '',
      count: 0,
      signalQuality: 0
    }
  },
  computed: {
    unevenNumber: function () {
      if (this.carriageName === '1' || this.carriageName === this.carriages.length + '') {
        return this.carriages[0].doors.filter(function (door) {
          return door.addr % 2 !== 0
        })
      } else {
        return this.carriages[1].doors.filter(function (door) {
          return door.addr % 2 !== 0
        })
      }
    },
    evenNumber: function () {
      if (this.carriageName === '1' || this.carriageName === this.carriages.length + '') {
        return this.carriages[0].doors.filter(function (door) {
          return door.addr % 2 === 0
        })
      } else {
        return this.carriages[1].doors.filter(function (door) {
          return door.addr % 2 === 0
        })
      }
    }
  },
  created () {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
    // 获取折线图的图例['x','x1','x2',...,'x29']
    this.chartColumns = Object.keys(labelMapChart)
    // 设置折线图的属性
    this.chartSettings = {
      chart: {
        // 重要：配置不同折线图所需的label的字段和名称映射，这里的字段需要和对应的chartData中的columns的各项一致
        labelMap: labelMapChart,
        xAxisType: 'category'
      }
    }

    this.timer1()
    this.timer2()
    this.timer3()
    this.timer4()
  },
  mounted () {
    this.onEventBus()
    this.getAllProject()
  },
  beforeDestroy () {
    this.offEventBus()
    clearInterval(this.timer1)
    clearInterval(this.timer2)
    clearInterval(this.timer3)
    clearInterval(this.timer4)
  },
  methods: {
    changeHeight () {
      this.carHeight = document.documentElement.clientHeight * 0.22 + 'px'
      this.lineHeight = document.documentElement.clientHeight * 0.325
    },
    timer1 () {
      return setInterval(() => {
        for (let i = 0; i < this.trainSignalList.length; i++) {
          this.trianSignalChartData.push(this.trainSignalList[i])
          if (this.trianSignalChartData.length > chartDataBuffer) {
            // shift() 方法用于把数组的第一个元素从其中删除，并返回第一个元素的值。
            this.trianSignalChartData.shift()
          }
        }
        this.trainSignalList = []
        this.checkedSignalFilter()
      }, 1000)
    },
    timer2 () {
      return setInterval(() => {
        for (let j = 0; j < this.commandSignalList.length; j++) {
          this.commandChartData.push(this.commandSignalList[j])
          if (this.commandChartData.length > chartDataBuffer) {
            this.commandChartData.shift()
          }
        }
        this.commandSignalList = []
        this.checkedSignalFilter()
      }, 1000)
    },
    timer3 () {
      return setInterval(() => {
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
    },
    timer4 () {
      return setInterval(() => {
        if (this.count > 10) {
          this.signalQuality = 100
        } else {
          this.signalQuality = (this.count / 10) * 100
        }
        this.count = 0
      }, 5000)
    },
    lineColor (signal, index) {
      let l = signal.length
      let blen = 0
      for (let i = 0; i < l; i++) {
        if ((signal.charCodeAt(i) & 0xff00) !== 0) {
          blen++
        }
        blen++
      }
      let marginLeft = 90 - blen * 5.1
      return {'margin-left': marginLeft + 'px', 'background-color': colors[index]}
    },

    // 拷贝列车数据
    copyCarriageDatas () {
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
    onEventBus () {
      const vm = this
      this.$bus.$on('updateFaultMessage', function (data) {
        vm.updateFaultMessage(data)
      })
      this.$bus.$on('updateRealTimeMessage', function (data) {
        vm.updateRealTimeMessage(data)
      })
    },
    offEventBus () {
      this.$bus.$off('updateFaultMessage')
      this.$bus.$off('updateRealTimeMessage')
    },

    updateFaultMessage (faults) {
      this.faultDatas = faults
    },
    // 处理实时数据
    updateRealTimeMessage (data) {
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
                this.doorOpenOrCloseTime = '关门时间：' + values[keys.indexOf('关门时间')]
                break
              case doorStates[6]: // 开门过程中
                this.doorOpenOrCloseTime = '开门时间：' + values[keys.indexOf('开门时间')]
                break
              default:
            }
          }
          // 给每个门设置门状态
          this.setDoorState(carriageNum, doorNum, state)

          if (carriageNum === this.carriageName && doorNum === this.doorAddr) {
            // 给当前选中的车辆信号曲线赋值
            // this.setSignalChartData(keys, values);
            valueList.push(values)
            keyList.push(keys)
          }
        }
        this.refreshSignalChartData(keyList, valueList)
        // 筛选选中的信号
        // this.checkedSignalFilter();
      }
    },

    trainNoChange (trainNo) {
      this.sendMessage()
    },
    // 获取项目名称
    getAllProject () {
      app.get('get_all_project').then(data => {
        if (data.msg) {
          data.msg.forEach(element => {
            this.projectOptions.push(element.name)
          })
          let projectName = localStorage.getItem('projectName')
          if (this.projectName !== null) {
            if (this.projectOptions.indexOf(projectName) !== -1) {
              this.projectName = projectName
              this.getTrainNo(false)
            }
          }
        }
      })
    },
    getTrainNo (isProjectChange) {
      this.trainNoOptions = []
      const params = {
        project: this.projectName
      }
      this.trainNo = ''
      app.get('get_train_no', params).then(data => {
        if (data.msg) {
          data.msg.forEach(element => {
            this.trainNoOptions.push(element.trainNo)
          })
          if (!isProjectChange) {
            let trainNo = localStorage.getItem('trainNo')
            if (this.trainNo !== null) {
              if (this.trainNoOptions.indexOf(trainNo) !== -1) {
                this.trainNo = trainNo
                this.sendMessage()
              }
            }
          }
        }
      })
    },
    // 获取车辆配置信息
    getTrainConfig () {
      const params = {
        project: this.projectName,
        trainNo: this.trainNo
      }
      app.get('get_train_config_info', params).then(data => {
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
    // 触发Eventbus，发送车辆信息到后端
    sendMessage () {
      if (this.projectName && this.trainNo) {
        localStorage.setItem('projectName', this.projectName)
        localStorage.setItem('trainNo', this.trainNo)
        const msg = "{'project': " + this.projectName + ",'train': " + this.trainNo + '}'
        this.$bus.$emit('sendMessage', msg)
        this.getTrainConfig()
      }
    },
    // 车辆信号取消按钮选中后清空数据
    handleTrainSignalCheckAllCancel () {
      this.checkedTrainSignals = []
    },
    // 机车与指令信号取消按钮选中后清空数据
    handleCommandSignalCheckAllCancel () {
      this.checkedCommandSignals = []
    },

    doorChecked (carriage, doorIndex) {
      if (this.carriageName !== carriage.name || this.doorAddr !== doorIndex) {
        this.trianSignalChartData = []
        this.commandChartData = []
      }
      this.carriageName = carriage.name
      this.doorAddr = carriage.doors[doorIndex - 1].addr

      this.doorState = ''
      this.motorCurrent = ''
      this.doorOpenOrCloseTime = '开门时间：0'
    },
    setDoorState (carriageNum, doorNum, state) {
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
    refreshSignalChartData (keyList, valueList) {
      for (let i = 0; i < keyList.length; i++) {
        const values = valueList[i]
        const keys = keyList[i]
        let time = values[keys.indexOf('时间')]

        let n = 0
        const trainSignalObject = {}
        const commandSignalObject = {}
        for (const key in labelMapChart) {
          if (key === 'x') {
            time = time.substring(11, time.length)
            trainSignalObject[key] = time
            commandSignalObject[key] = time
          } else {
            let trainSignalIndex = -1
            let commandSignalIndex = -1
            if (n < trainSignals.length) {
              trainSignalIndex = keys.indexOf(trainSignals[n])
            }
            if (trainSignalIndex === -1) {
              trainSignalObject[key] = ''
            } else {
              trainSignalObject[key] = values[trainSignalIndex]
            }

            // 给机车与指令信号赋值
            if (n < commandSignals.length) {
              commandSignalIndex = keys.indexOf(commandSignals[n])
              if (commandSignalIndex === -1) {
                commandSignalObject[key] = ''
              } else {
                commandSignalObject[key] = values[commandSignalIndex]
              }
            }
            n++
          }
        }
        this.trainSignalList.push(trainSignalObject)
        this.commandSignalList.push(commandSignalObject)
      }
    },

    checkedSignalFilter () {
      // 如果勾选框都没被选中清空数据
      if (this.checkedTrainSignals.length === 0) {
        this.trianSignalChartData = []
      }
      if (this.checkedCommandSignals.length === 0) {
        this.commandChartData = []
      }
      for (let j = 1; j < this.chartColumns.length; j++) {
        // 根据勾选筛选车辆信号
        for (let i = 0; i < this.trianSignalChartData.length; i++) {
          if (j - 1 < trainSignals.length) {
            // checkedTrainSignals1中是被选中的信号名称，如果trainSignals1中的某个信号不在选中的信号中，对应的曲线数据清空
            if (this.checkedTrainSignals.indexOf(trainSignals[j - 1]) === -1) {
              this.trianSignalChartData[i][this.chartColumns[j]] = ''
            }
          }
        }
        // 根据勾选筛选机车与指令信号
        if (j - 1 < commandSignals.length) {
          for (let n = 0; n < this.commandChartData.length; n++) {
            if (this.checkedCommandSignals.indexOf(commandSignals[j - 1]) === -1) {
              this.commandChartData[n][this.chartColumns[j]] = ''
            }
          }
        }
      }
    },
    toHome () {
      this.$router.push({
        path: './'
      })
    }
  }
}
</script>

<style lang="scss" >
#realtimemonitor {
  color: #fff;
  padding: 2px;
  background-color: #0f1741;
  font-size: small;
}

#realtimemonitor .common {
  margin: 2px 0 0 2px
}

#realtimemonitor .el-checkbox__label {
  color: #fff;
  margin-top: 5px;
  font-size: 10px
}

#realtimemonitor .home-card {
  border: 1px solid rgb(54, 88, 141);
  box-shadow: rgb(105, 149, 02) 0px 0px 8px inset;
  position: relative;
  font-size: 10px
}

#realtimemonitor .card-title {
  text-align: center;
  background-color: #00cccc;
  font-size: small;
}

#realtimemonitor .basicinfo-style {
  margin-top: 5px;
  margin-left: 15px;
  position: relative;
}
.center {
  display: flex;
  -webkit-align-items: center;
  align-items: center;
  -webkit-justify-content: center;
  justify-content: center;
}

#realtimemonitor .red {
  border-width: 1px;
  border-style: solid;
  border-color: red;
}

#realtimemonitor .el-select {
  width: 100%;
}
#realtimemonitor .image-style {
  float: right;
  margin-right: 10px
}
</style>
