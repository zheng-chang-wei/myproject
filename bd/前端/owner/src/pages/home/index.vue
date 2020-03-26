<template>
  <div id="home">
    <el-row class="common center">
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
         <img class="image-style" :src="require('../../../static/imgs/statistics.svg')" @click="toStatistics">
         <img class="image-style" :src="require('../../../static/imgs/realtimemonitor.svg')" @click="toRealtimemonitor">
      </el-col>
    </el-row>
    <el-row class="home-card common" :style="{'height':carHeight}">
      <div class="card-border card-border-top-left" />
      <div class="card-border card-border-top-right" />
      <div class="card-border card-border-bottom-left" />
      <div class="card-border card-border-bottom-right" />
      <carriages :style="{'margin-left': (100 - (12.5 * 6 + 8.33333 * 2)) / 2 + '%'}" v-if="carriages.length > 0" :carriages="carriages"
        :carriage-span="carriageSpan" />
    </el-row>
    <el-row>
      <el-col :span="8" style="margin-top: 0px">
        <fault-table :fault-list="faultList" :style="{'height': commonHeight+ 'px'}" :tableHeight="commonHeight" />
        <fault-line :style="{'height': commonHeight+ 'px','marginLeft':'2px'}" :lineHeight="commonHeight" />
      </el-col>
      <el-col :span="8" style="margin-top: 0px">
        <subhealth-table :subhealth-list="subhealthList" :style="{'height': commonHeight+ 'px'}" :tableHeight="commonHeight" />
        <subhealth-line :style="{'height': commonHeight+ 'px'}" :lineHeight="commonHeight" />
      </el-col>
      <el-col :span="8" style="margin-top: 0px">
        <life-table :life-list="lifeList" :style="{'height': commonHeight+ 'px'}" :tableHeight="commonHeight" />
        <life-pie :pieData="pieData" :style="{'height': commonHeight+ 'px'}" :lineHeight="commonHeight" />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import FaultTable from './FaultTable'
import FaultLine from './FaultLine'

import SubhealthTable from './SubhealthTable'
import SubhealthLine from './SubhealthLine'
import LifeTable from './LifeTable'
import LifePie from './LifePie'

import carriages from '@/components/realtimemonitor/carriages.vue'

import app from '@/common/js/app'

// const thisYear = new Date().getFullYear()
// const lastYear = thisYear - 1

export default {
  components: {
    FaultLine,
    FaultTable,
    SubhealthTable,
    SubhealthLine,
    LifeTable,
    LifePie,
    carriages
  },
  data () {
    return {
      projectOptions: [],
      trainNoOptions: [],
      projectName: '',
      trainNo: '',
      carriages: [],
      // 临时列车数据，用于保存5秒前的列车数据
      tempCarriageDatas: [],
      commonHeight: 0,
      carHeight: '',
      faultList: [],
      subhealthList: [],
      lifeList: [],
      faultLineData: {},
      pieData: {
        columns: ['type', 'percent'],
        rows: [
          {'type': '剩余寿命值', 'percent': '80'},
          {'type': '已用寿命值', 'percent': '20'}
        ]
      }
    }
  },
  created () {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
  },
  mounted () {
    this.timer()
    this.onEventBus()
    this.getAllProject()
  },
  beforeDestroy () {
    this.$bus.$off('updateRealTimeMessage')
    clearInterval(this.timer)
  },
  methods: {
    changeHeight () {
      this.setInputHeight()
      // 车厢区域高度
      this.carHeight = document.documentElement.clientHeight * 0.22 + 'px'
      // table和曲线的高度
      this.commonHeight = document.documentElement.clientHeight * 0.364
    },
    // 设置下拉框的高度
    setInputHeight () {
      let input = document.getElementsByClassName('el-input__inner')
      let inputIcon = document.getElementsByClassName('el-input__icon')
      let inputHeight = document.documentElement.clientHeight * 0.04 + 'px'
      for (let index = 0; index < inputIcon.length; index++) {
        const element = inputIcon[index]
        element.style.lineHeight = inputHeight
      }
      for (let index = 0; index < input.length; index++) {
        const element = input[index]
        element.style.height = inputHeight
        element.style.lineHeight = inputHeight
      }
    },
    timer () {
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
      this.$bus.$on('updateRealTimeMessage', function (data) {
        vm.updateRealTimeMessage(data)
      })
    },
    // 处理实时数据
    updateRealTimeMessage (data) {
      // 获取实时数据
      const packet = data.packet
      if (packet && packet.length !== 0) {
        const keys = packet.keys
        // 获取门状态的index
        const doorStateIndex = keys.indexOf('车门状态')
        // 获取车厢号的index
        const carriageNumIndex = keys.indexOf('车厢号')
        // 获取门地址的index
        const doorNumIndex = keys.indexOf('门地址')

        for (let i = 0; i < packet.frames.length; i++) {
          const values = packet.frames[i].values
          // 门状态
          const state = values[doorStateIndex]
          // 车厢号
          const carriageNum = values[carriageNumIndex]
          // 门地址
          const doorNum = parseInt(values[doorNumIndex])
          // 给每个门设置门状态
          this.setDoorState(carriageNum, doorNum, state)
        }
      }
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
                this.getData()
              }
            }
          }
        }
      })
    },
    trainNoChange (trainNo) {
      this.sendMessage()
      this.getData()
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
    countRecycle () {
      if (this.projectName !== null && this.trainNo !== null) {
        this.getData()
      }
      setTimeout(() => {
        this.countRecycle()
      }, 60 * 10 * 1000)
    },
    getData () {
      this.countTodayFault()
      this.countTodaySubhealth()
      this.listLifeWarningToday()
    },
    countTodayFault () {
      this.faultList = []
      let params = {
        project: this.projectName,
        trainNo: this.trainNo
      }
      app.get('dashboard_fault_today', params).then(data => {
        if (data) {
          const list = data.msg
          list.forEach(fault => {
            this.faultList.push({
              id: fault.id,
              project: fault.project,
              train: fault.trainNo,
              faultContent: '' + fault.carNo + '车' + fault.doorAddr + '门' + fault.faultName,
              faultTime: fault.faultTime,
              suggestion: fault.suggestion,
              treatment: fault.treatment ? fault.treatment.trim() : '无',
              repair: fault.repair ? fault.repair : '无',
              carriage: fault.carNo,
              door: fault.doorAddr,
              faultMode: fault.faultName
            })
          })
          this.$bus.$emit('setFaultFirstRowSelection')
        }
      })
    },
    countTodaySubhealth () {
      this.subhealthList = []
      let params = {
        project: this.projectName,
        trainNo: this.trainNo
      }
      app.get('dashboard_subhealth', params).then(data => {
        if (data) {
          const list = data.msg
          list.forEach(fault => {
            this.subhealthList.push({
              id: fault.id,
              project: fault.project,
              train: fault.trainNo,
              faultContent: '' + fault.carNo + '车' + fault.doorAddr + '门' + fault.subhealthName,
              faultTime: fault.startTime,
              suggestion: fault.suggestion,
              treatment: fault.treatment ? fault.treatment : '无',
              repair: fault.repair ? fault.repair : '无',
              carriage: fault.carNo,
              door: fault.doorAddr,
              faultMode: fault.subhealthName
            })
          })
          this.$bus.$emit('setSubhealthRowSelection')
        }
      })
    },
    listLifeWarningToday () {
      let params = {
        project: this.projectName,
        trainNo: this.trainNo
      }
      this.lifeList = []
      app.get('findDoorItemAVGValue', params).then(data => {
        if (data) {
          const list = data.msg
          list.forEach(warning => {
            this.lifeList.push({
              itemName: warning.itemName,
              warningTime: warning.time,
              usedLife: warning.usedLife,
              totleLife: warning.referenceValue
            })
          })
          this.$bus.$emit('setLifeRowSelection')
        }
      })
    },
    toRealtimemonitor () {
      this.$router.push({
        path: '/realtimemonitor'
      })
    },
    toStatistics () {
      this.$router.push({
        path: '/statistics'
      })
    }
  }
}

</script>

<style scoped>
  .app-container {
    height: 704px;
    padding: 0px;
    background-color: #242640;
  }

</style>

<style lang="scss">
  #home .el-button{
    padding: 5px 5px;
    font-size: 10px;
    border-radius: 10px;
  }
  $border-color:rgb(54, 227, 253);

  #home .card-border {
    position: absolute;
    width: 15px;
    height: 15px;
  }

  #home .common {
    margin: 0px 2px
  }

  #home .el-select {
    width: 100%;
  }

  #home .home-card {
    border: 1px solid rgb(54, 88, 141);
    box-shadow: rgb(105, 149, 02) 0px 0px 8px inset;
    padding: 10px;
    position: relative;
    font-size: 10px
  }

  #home .card-border-top-left {
    top: -1px;
    left: -1px;
    border-left: 1px solid $border-color;
    border-top: 1px solid $border-color;
    box-shadow: $border-color 2px 2px 2px inset;
  }

  #home .card-border-top-right {
    top: -1px;
    right: -1px;
    border-right: 1px solid $border-color;
    border-top: 1px solid $border-color;
    box-shadow: rgb(11, 234, 235) -2px 2px 2px inset;
  }

  #home .card-border-bottom-left {
    bottom: -1px;
    left: -1px;
    border-bottom: 1px solid $border-color;
    border-left: 1px solid $border-color;
    box-shadow: rgb(11, 234, 235) 2px -2px 2px inset;
  }

  #home .card-border-bottom-right {
    bottom: -1px;
    right: -1px;
    border-right: 1px solid $border-color;
    border-bottom: 1px solid $border-color;
    box-shadow: rgb(11, 234, 235) -2px -2px 2px inset;
  }

  #home .el-table--enable-row-hover .el-table__body tr:hover>td {
    background-color: rgb(11, 234, 235) !important;
  }

  #home .el-table__body tr.current-row>td {
    background-color: rgb(11, 234, 235) !important;
    // background-color: #f19944 !important;
  }

  #home .el-table,
  .el-table__expanded-cell {
    background-color: #0f1741 !important;
  }

  #home .el-table--border::after,
  .el-table--group::after,
  .el-table::before {
    background-color: #0f1741 !important;
  }

  #home .image-style {
    cursor: pointer;
    float: right;
    margin-right: 10px;
  }
</style>
