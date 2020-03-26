<!--  深圳业主首页 -->
<template>
  <div class="app-container owner-dashboard">
    <el-row>
      <el-col :span="6" style="margin-top: 10px">
        <el-row>
          <train-online :train="train" />
        </el-row>
        <el-row>
          <fault-count :fault-count="faultCount" />
        </el-row>
      </el-col>
      <el-col :span="9" style="margin-top:10px">
        <el-row>
          <div style="height: 372px">
            <fault-list :fault-list="faultList" :table-height="328" />
          </div>
        </el-row>
        <el-row>
          <div style="height:300px">
            <annual-fault :annual-fault="annualFault" :chart-height="'272px'" :title="'故障'" :title-style="'background-color:#FF0010'" />
          </div>
        </el-row>
      </el-col>
      <el-col :span="9" style="margin-top:10px">
        <el-row>
          <div style="height: 372px">
            <subhealth-list :subhealth-list="subhealthList" :table-height="328" />
          </div>
        </el-row>
        <el-row>
          <div style="height: 300px">
            <annual-fault :annual-fault="annualSubhealth" :chart-height="'270px'" :title="'亚健康'" :title-style="'background-color:#FF9900'" />
          </div>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import TrainOnline from './TrainOnline'

import FaultList from './FaultList'
import AnnualFault from './AnnualFault'

import SubhealthList from './SubhealthList'

import FaultCount from './FaultCount'

import app from '@/common/js/app'

const THIS_YEAR = new Date().getFullYear()
const LAST_YEAR = THIS_YEAR - 1
export default {
  components: {
    TrainOnline,
    FaultList,
    AnnualFault,
    SubhealthList,
    FaultCount
  },
  data() {
    return {
      train: {
        online: 0,
        total: 0,
        onlineDoor: 0
      },
      faultCount: {
        columns: ['type', 'lastYear', 'thisYear'],
        rows: [{
          'type': '故障',
          'thisYear': 0,
          'lastYear': 0
        },
        {
          'type': '亚健康预警',
          'thisYear': 0,
          'lastYear': 0
        }
        ]
      },
      faultList: [{
        project: '深圳7号线',
        train: '717',
        faultContent: '1车1门 短路',
        faultTime: '2019-12-08 13:45:00',
        carriage: 1,
        door: 1,
        faultMode: '短路',
        repair: '无',
        treatment: '无'
      }],
      subhealthList: [{
        project: '深圳7号线',
        train: '717',
        faultContent: '1车1门 闭锁组件异常',
        faultTime: '2019-12-08 13:45:00',
        carriage: 1,
        door: 1,
        faultMode: '闭锁组件异常',
        repair: '无',
        treatment: '无'
      }],
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
      },
      annualSubhealth: {
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
  mounted() {
    this.countTrains()
    this.countTodayFault()
    this.annualFaultCount()
    this.countAnnualFault()
    this.countTodaySubhealth()

    this.annualSubhealthCount()
    this.countAnnualSubhealth()
  },
  methods: {
    countTrains() {
      console.log('count trains')
      const vm = this
      app.get('dashboard_train').then(data => {
        if (data) {
          vm.train = data.msg
        }
      })
    },
    countTodayFault() {
      console.log('today fault')
      const vm = this
      vm.faultList = []
      app.get('dashboard_fault_today').then(data => {
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
    annualFaultCount() {
      const vm = this
      app.get('dashboard_fault_annual_count', {
        year: LAST_YEAR
      }).then(res => {
        if (res) {
          const count = res.msg
          vm.faultCount.rows[0].lastYear = count
        }
      })
      app.get('dashboard_fault_annual_count', {
        year: THIS_YEAR
      }).then(res => {
        if (res) {
          const count = res.msg
          vm.faultCount.rows[0].thisYear = count
        }
      })
    },
    annualSubhealthCount() {
      const vm = this
      app.get('dashboard_subhealth_annual_count', {
        year: LAST_YEAR
      }).then(res => {
        if (res) {
          const count = res.msg
          vm.faultCount.rows[1].lastYear = count
        }
      })
      app.get('dashboard_subhealth_annual_count', {
        year: THIS_YEAR
      }).then(res => {
        if (res) {
          const count = res.msg
          vm.faultCount.rows[1].thisYear = count
        }
      })
    },
    countAnnualFault() {
      const vm = this
      app.get('dashboard_fault_annual', {
        year: LAST_YEAR
      }).then(res => {
        if (res) {
          const counts = res.msg
          for (let i = 0; i < 12; i++) {
            vm.annualFault.rows[i].lastYear = counts.counts[i]
          }
        }
      })
      app.get('dashboard_fault_annual', {
        year: THIS_YEAR
      }).then(res => {
        if (res) {
          const counts = res.msg
          for (let i = 0; i < 12; i++) {
            vm.annualFault.rows[i].thisYear = counts.counts[i]
          }
        }
      })
    },
    countAnnualSubhealth() {
      const vm = this
      app.get('dashboard_subhealth_annual', {
        year: LAST_YEAR
      }).then(res => {
        if (res) {
          const counts = res.msg
          for (let i = 0; i < 12; i++) {
            vm.annualSubhealth.rows[i].lastYear = counts.counts[i]
          }
        }
      })
      app.get('dashboard_fault_annual', {
        year: THIS_YEAR
      }).then(res => {
        if (res) {
          const counts = res.msg
          for (let i = 0; i < 12; i++) {
            vm.annualSubhealth.rows[i].thisYear = counts.counts[i]
          }
        }
      })
    },
    countCurrentMonthFault() {
      const vm = this
      const month = new Date().getMonth() + 1
      app.get('dashboard_fault_month', {
        year: THIS_YEAR,
        month: month
      }).then(res => {
        if (res) {
          const count = res.msg
          vm.annualFault.rows[month - 1].thisYear = count
        }
      })
    },
    countCurrentMonthSubhealth() {
      const vm = this
      const month = new Date().getMonth() + 1
      app.get('dashboard_subhealth_month', {
        year: THIS_YEAR,
        month: month
      }).then(res => {
        if (res) {
          const count = res.msg
          vm.annualSubhealth.rows[month - 1].thisYear = count
        }
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
  $border-color:rgb(54, 227, 253);
  .card-border {
    position: absolute;
    width: 15px;
    height: 15px;
  }

  .home-card {
    border: 1px solid rgb(54, 88, 141);
    box-shadow: rgb(105, 149, 02) 0px 0px 8px inset;
    padding: 10px;
    position: relative;
  }

  .card-border-top-left {
    top: -1px;
    left: -1px;
    border-left: 1px solid $border-color;
    border-top: 1px solid $border-color;
    box-shadow: $border-color 2px 2px 2px inset;
  }

  .card-border-top-right {
    top: -1px;
    right: -1px;
    border-right: 1px solid $border-color;
    border-top: 1px solid $border-color;
    box-shadow: rgb(11, 234, 235) -2px 2px 2px inset;
  }

  .card-border-bottom-left {
    bottom: -1px;
    left: -1px;
    border-bottom: 1px solid $border-color;
    border-left: 1px solid $border-color;
    box-shadow: rgb(11, 234, 235) 2px -2px 2px inset;
  }

  .card-border-bottom-right {
    bottom: -1px;
    right: -1px;
    border-right: 1px solid $border-color;
    border-bottom: 1px solid $border-color;
    box-shadow: rgb(11, 234, 235) -2px -2px 2px inset;
  }

  .owner-dashboard .el-table--enable-row-hover .el-table__body tr:hover>td {
    background-color: #394761 !important;
  }

  .owner-dashboard .el-table,
  .owner-dashboard .el-table__expanded-cell {
    background-color: #0f1741 !important;
  }

  .owner-dashboard .el-table--border::after,
  .owner-dashboard .el-table--group::after,
  .owner-dashboard .el-table::before {
    background-color: #0f1741 !important;
  }

</style>
