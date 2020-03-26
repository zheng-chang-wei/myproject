<template>
  <div class="statistics">
    <el-row class="center common">
      <el-col :span="5">
        <el-select v-model="line" size="mini" placeholder="线路"  disabled>
        </el-select>
      </el-col>
      <el-col :span="5" style="margin-left:10px">
        <el-select v-model="train" size="mini" placeholder="车辆" @change="trainChange">
          <el-option v-for="item in trains" :key="item" :label="item" :value="item" />
        </el-select>
      </el-col>
      <el-col :span="5" style="margin-left:10px">
          <el-select v-model="year" size="mini" placeholder="年份" @change="yearChange">
            <el-option v-for="item in years" :key="item" :label="item" :value="item"></el-option>
          </el-select>
      </el-col>
      <el-col :span="9">
        <img class="image-style" :src="require('../../../static/imgs/back.svg')" style="cursor: pointer;"
          @click="toHome">
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <div class="block" :style="{'height':lineHeight+'px'}">
          <div class="block-title block-fault">故障告警趋势</div>
          <line-pie :lineData="faults" :pieData="faultTypes" :lineHeight="lineHeight" :color="'red'"></line-pie>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="block" :style="{'height':lineHeight+'px'}">
          <div class="block-title block-subhealth">亚健康预警趋势</div>
          <line-pie :lineData="subhealths" :pieData="subhealthTypes" :lineHeight="lineHeight"  :color="'orange'"></line-pie>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="block" :style="{'height':lineHeight+'px','margin-top': '2%'}">
          <div class="block-table">
            <el-table  :data="orderTypes" :height="lineHeight*0.5+'px'" background-color="#242640" :header-cell-style="{background:'#242640',color:'#fff',padding:'5px',borderColor:'gray',textAlign:'center',fontSize:'12px'}"
              :row-style="{background:'#242640',color:'#fff',fontSize:'10px' }" :cell-style="{padding:'4px',borderColor:'gray',textAlign:'center'}">
              <el-table-column prop="name" label="项点"></el-table-column>
              <el-table-column prop="count" label="个数"></el-table-column>
            </el-table>
          </div>
          <total-pie :pieData="totalTypes" :lineHeight="lineHeight" style="margin-top: 20px"></total-pie>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import LinePie from './components/LinePie'
import TotalPie from './components/TotalPie'
import app from '@/common/js/app'
const startYear = 2018
const endYear = new Date().getFullYear()
export default {
  name: 'HelloWorld',
  components: {
    LinePie,
    TotalPie
  },
  data () {
    return {
      line: '7号线',
      train: '',
      trains: [],
      year: endYear,
      years: [],
      subhealths: {
        columns: ['month', 'count'],
        rows: []
      },
      faults: {
        columns: ['month', 'count'],
        rows: []
      },
      faultTypes: {
        columns: ['type', 'percent'],
        rows: []
      },
      subhealthTypes: {
        columns: ['type', 'percent'],
        rows: []
      },
      totalTypes: {
        columns: ['type', 'percent'],
        rows: []
      },
      types: [],
      lineHeight: 0
    }
  },
  computed: {
    orderTypes: function () {
      let temp = this.types
      return temp.sort((a, b) => {
        return b.count - a.count
      })
    }
  },
  created () {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
  },
  mounted () {
    this.initYears()
    this.listTrains()
  },
  beforeDestroy () {
  },
  methods: {
    changeHeight () {
      let height = document.documentElement.clientHeight
      this.lineHeight = height * 0.93
      this.setInputHeight()
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
    trainChange (item) {
      this.statistics()
    },
    yearChange (item) {
      this.statistics()
    },
    initYears () {
      for (let i = endYear; i >= startYear; i--) {
        this.years.push(i)
      }
    },
    listTrains () {
      app.get('trains').then(res => {
        if (res && res.data) {
          this.trains = res.data
          if (this.trains.length === 1) {
            this.train = this.trains[0]
            this.statistics()
          }
        }
      })
    },
    statistics () {
      this.statisticsByYear()
      this.statisticsType(this.year, 1, 12)
    },
    statisticsByYear () {
      let param = {
        trains: this.train,
        year: this.year
      }
      app.get('countByYear', param).then(res => {
        if (res && res.data) {
          this.year = res.data.year
          let rows1 = []
          let rows2 = []
          res.data.results.forEach(result => {
            rows1.push({
              month: result.month + '',
              count: result.fault
            })
            rows2.push({
              month: result.month + '',
              count: result.subhealth
            })
          })
          this.faults.rows = rows1
          this.subhealths.rows = rows2
        }
      })
    },
    statisticsType (year, startMonth, endMonth) {
      let param = {
        trains: this.train,
        year: year,
        start_month: startMonth,
        end_month: endMonth
      }
      this.types = []
      app.get('countByTopType', param).then(res => {
        if (res && res.data) {
          if (res.data.types.length === 0) {
            this.totalTypes.rows = [
              {
                type: '总体分布-暂无数据',
                percent: 0
              }
            ]
          } else {
            this.totalTypes.rows = res.data.types
          }
        }
      })
      // 故障类型统计
      app.get('countByFaultType', param).then(res => {
        if (res && res.data) {
          if (res.data.types.length === 0) {
            this.faultTypes.rows = [
              {
                type: '故障分类-暂无数据',
                percent: 0
              }
            ]
          } else {
            this.faultTypes.rows = res.data.types
            this.faultTypes.rows.forEach(r => {
              this.types.push({
                name: r.type,
                count: r.percent
              })
            })
          }
        }
      })
      // 亚健康类型统计
      app.get('countBySubhealthType', param).then(res => {
        if (res && res.data) {
          if (res.data.types.length === 0) {
            this.subhealthTypes.rows = [
              {
                type: '亚健康分类-暂无数据',
                percent: 0
              }
            ]
          } else {
            this.subhealthTypes.rows = res.data.types
            this.subhealthTypes.rows.forEach(r => {
              this.types.push({
                name: r.type,
                count: r.percent
              })
            })
          }
        }
      })
    },
    toHome () {
      // 回首页
      this.$router.push('/')
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.block {
  margin-left: 5px;
  margin-right: 5px;
  background-color: #242640;
}

.block-title {
  margin-top: 2%;
  height: 20px;
  color: white;
  font-weight: bold;
  display: flex;
  -webkit-align-items: center;
  align-items: center;
  -webkit-justify-content: center;
  justify-content: center;
}

.statistics {
  background-color: #0f1741;
  margin: 0px;
}

.block-fault {
  background: red;
}

.block-subhealth {
  background: orange;
}

.pull-right {
  float: right;
}

.block-table{
  background-color: #242640;
}
</style>
<style>
.statistics{
  border-width: 1px;
  border-style: solid;
  border-color: #0f1741;
}
.statistics  .el-select {
  width: 100%;
}
.statistics .image-style {
  float: right;
  margin-right: 10px
}
.statistics .common {
  margin: 2px 0 0 2px
}
.statistics .el-table--enable-row-hover .el-table__body tr:hover > td {
  background-color: #0f1741 !important;
}

.statistics .el-table,
.el-table__expanded-cell {
  background-color: #242640 !important;
}

.statistics .el-table--border::after,
.el-table--group::after,
.el-table::before {
  background-color: gray;
}

.statistics .el-table--border {
  border: 1px solid #242640;
}

.statistics .el-table--border::after,
.el-table--group::after {
  width: 0px;
}

.statistics .el-table::before {
  height: 0px;
}
</style>
