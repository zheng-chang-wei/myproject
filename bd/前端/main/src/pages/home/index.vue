<template>
  <div class="app-container default-dashboard">
    <el-row :style="'margin-top:'+marginTop">
      <el-col :span="4">
        <train-online :train="train" :train-online-height="trainOnlineHeight" />
        <worksheet-count :sheet-count="sheetCount" :sheet-count-height="sheetCountHeight" />
      </el-col>
      <el-col :span="10">
        <fault-list :fault-list="faultList" :table-height="tableHeight" />
        <annual-fault :annual-fault="annualFault" :chart-height="chartHeight" :title="'故障'" />
      </el-col>
      <el-col :span="10">
        <subhealth-list :subhealth-list="subhealthList" :table-height="tableHeight" />
        <life-list :life-list="lifeList" :table-height="tableHeight" />
        <component-list />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import TrainOnline from './TrainOnline'
import WorksheetCount from './WorksheetCount'

import FaultList from './FaultList'
import AnnualFault from './AnnualFault'

import SubhealthList from './SubhealthList'
import LifeList from './LifeList'
import ComponentList from './ComponentList'

import app from '@/common/js/app'

const thisYear = new Date().getFullYear()
const lastYear = thisYear - 1

export default {
  components: {
    TrainOnline,
    WorksheetCount,
    FaultList,
    AnnualFault,
    SubhealthList,
    LifeList,
    ComponentList
  },
  data() {
    return {
      tableHeight: 0,
      marginTop: 0,
      train: {
        online: 0,
        total: 0,
        onlineDoor: 0
      },
      sheetCount: {
        columns: ['type',
          'newSheet', 'handled', 'unhandled', 'unhandledBeforeMonth'
        ],
        rows: [{
          'type': '今日工单',
          'newSheet': 0
        },
        {
          'type': '今日处理',
          'handled': 0
        },
        {
          'type': '今日剩余',
          'unhandled': 0
        },
        {
          'type': '30天未处理',
          'unhandledBeforeMonth': 0
        }
        ]
      },
      faultList: [],
      subhealthList: [],
      lifeList: [],
      annualFault: {
        columns: ['month', 'thisYear', 'lastYear'],
        rows: [{
          'month': '1',
          'thisYear': 0,
          'lastYear': 0
        },
        {
          'month': '2',
          'thisYear': 0,
          'lastYear': 0
        },
        {
          'month': '3',
          'thisYear': 0,
          'lastYear': 0
        },
        {
          'month': '4',
          'thisYear': 0,
          'lastYear': 0
        },
        {
          'month': '5',
          'thisYear': 0,
          'lastYear': 0
        },
        {
          'month': '6',
          'thisYear': 0,
          'lastYear': 0
        },
        {
          'month': '7',
          'thisYear': 0,
          'lastYear': 0
        },
        {
          'month': '8',
          'thisYear': 0,
          'lastYear': 0
        },
        {
          'month': '9',
          'thisYear': 0,
          'lastYear': 0
        },
        {
          'month': '10',
          'thisYear': 0,
          'lastYear': 0
        },
        {
          'month': '11',
          'thisYear': 0,
          'lastYear': 0
        },
        {
          'month': '12',
          'thisYear': 0,
          'lastYear': 0
        }
        ]
      }
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeHeight)
  },
  mounted() {
    this.countRecycle()
    this.countAnnualFault()
  },
  methods: {
    changeHeight() {
      const offsetHeight = document.body.offsetHeight - 50
      this.marginTop = offsetHeight * 0.01 + 'px'
      this.trainOnlineHeight = offsetHeight * 0.15 + 'px'
      this.sheetCountHeight = offsetHeight * 0.69 + 'px'
      this.tableHeight = offsetHeight * 0.5
      this.chartHeight = offsetHeight * 0.48
    },
    countRecycle() {
      this.countTrains()
      this.countSheets()
      this.countTodayFault()
      this.countTodaySubhealth()
      this.countCurrentMonthFault()
      setTimeout(() => {
        this.countRecycle()
      }, 60 * 10 * 1000)
    },
    countTrains() {
      console.log('count trains')
      const vm = this
      app.get('dashboard_train').then(data => {
        if (data) {
          vm.train = data.msg
        }
      })
    },
    countSheets() {
      console.log('count sheets')
      const vm = this
      app.get('dashboard_sheet').then(data => {
        if (data) {
          const rows = []
          const count = data.data
          rows.push({
            'type': '今日工单',
            'newSheet': count.newSheets
          })
          rows.push({
            'type': '今日处理',
            'handled': count.handled
          })
          rows.push({
            'type': '今日剩余',
            'unhandled': count.unHandled
          })
          rows.push({
            'type': '30天未处理',
            'unhandledBeforeMonth': count.unHandledBeforeMonth
          })
          vm.sheetCount.rows = rows
        }
      })
    },
    countTodayFault() {
      console.log('today fault')
      const vm = this
      vm.faultList = []
      app.get('dashboard_fault_today').then(data => {
        console.log(data)
        if (data) {
          const list = data.msg
          list.forEach(fault => {
            vm.faultList.push({
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
        }
      })
    },
    countTodaySubhealth() {
      console.log('today subhealth')
      const vm = this
      vm.subhealthList = []
      app.get('dashboard_subhealth').then(data => {
        if (data) {
          const list = data.msg
          list.forEach(fault => {
            vm.subhealthList.push({
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
        }
      })
    },
    countAnnualFault() {
      const vm = this
      app.get('dashboard_fault_annual', {
        year: lastYear
      }).then(res => {
        if (res) {
          const counts = res.msg
          for (let i = 0; i < 12; i++) {
            vm.annualFault.rows[i].lastYear = counts.counts[i]
          }
        }
      })
      app.get('dashboard_fault_annual', {
        year: thisYear
      }).then(res => {
        if (res) {
          const counts = res.msg
          for (let i = 0; i < 12; i++) {
            vm.annualFault.rows[i].thisYear = counts.counts[i]
          }
        }
      })
    },
    countCurrentMonthFault() {
      const vm = this
      const month = new Date().getMonth() + 1
      app.get('dashboard_fault_month', {
        year: thisYear,
        month: month
      }).then(res => {
        if (res) {
          const count = res.msg
          vm.annualFault.rows[month - 1].thisYear = count
        }
      })
    }
  }
}

</script>

<style>

  .default-dashboard .el-table--enable-row-hover .el-table__body tr:hover>td {
    background-color: #394761 !important;
  }

  .default-dashboard .el-table,
  .default-dashboard .el-table__expanded-cell {
    background-color: #0f1741 !important;
  }

  .default-dashboard .el-table--border::after,
  .default-dashboard .el-table--group::after,
  .default-dashboard .el-table::before {
    background-color: #0f1741 !important;
  }

</style>
