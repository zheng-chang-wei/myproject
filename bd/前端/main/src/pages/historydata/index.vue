<template>
  <section class="app-container history-data">
    <el-row style="min-width:900px; height:100%">
      <el-col class="rightMaxStyle">
        <el-row>
          <el-form ref="request" :inline="true" class="query" :model="request" :rules="formRules">
            <!-- <el-form-item label="时间范围" prop="times">
              <el-date-picker v-model="request.times" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions" value-format="yyyy-MM-dd" @change="selectTime" />
            </el-form-item> -->
            <el-form-item label="日期" prop="time">
              <el-date-picker v-model="request.time" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" :picker-options="pickerOptions" style="width:192px;" />
            </el-form-item>
            <el-form-item label="项目" prop="project">
              <el-select v-model="request.project" placeholder="项目名称" @change="projectChanged">
                <el-option v-for="item in projects" :key="item" :label="item" :value="item" />
              </el-select>
            </el-form-item>
            <el-form-item label="车辆" prop="train">
              <el-select v-model="request.train" placeholder="车辆号">
                <el-option v-for="item in trains" :key="item" :label="item" :value="item" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="search" @click="queryRecords">查询</el-button>
            </el-form-item>
          </el-form>
        </el-row>
        <el-row class="center" style="background-color:#00cccc;color:white;height:30px">历史数据记录</el-row>
        <el-table
          v-loading="loading"
          :data="records"
          border
          highlight-current-row
          :max-height="tableMaxHeight"
          @row-dblclick="selectRow"
        >
          <el-table-column prop="project" label="项目" align="center" />
          <el-table-column prop="train" label="车辆" align="center" />
          <el-table-column prop="carriageId" label="车厢" align="center" sortable />
          <el-table-column prop="doorId" label="门地址" align="center" sortable />
          <el-table-column prop="timestamp" label="时间" align="center" />
          <el-table-column prop="state" label="状态" align="center" />
        </el-table>
        <el-col :span="24" class="toolbar" style="margin-top:10px;">
          <el-pagination
            :current-page.sync="currentPage"
            :page-sizes="[10,20,30,50,100]"
            :page-size="pageSize"
            layout="total,sizes,prev,pager,next,jumper"
            :total="total"
            style="float: right;"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </el-col>
      </el-col>
    </el-row>
    <el-dialog class="data-dialog" title="详细数据" :visible.sync="detailVisible" :close-on-click-modal="false" fullscreen destroy-on-close>
      <history-data :datas="datas" :record="selectRecord" />
    </el-dialog>
  </section>
</template>
<script>
import app from '@/common/js/app'
import HistoryData from './historydata'
const variables = ['电机电压', '电机电流', '编码器值', '开门时间', '关门时间']
export default {
  components: {
    HistoryData
  },
  data() {
    return {
      formRules: {
        time: [
          {
            type: 'string',
            required: true,
            message: '请选择日期',
            trigger: 'change'
          }
        ],
        project: [
          {
            type: 'string',
            required: true,
            message: '请选择项目',
            trigger: 'blur'
          }
        ],
        train: [
          {
            type: 'string',
            required: true,
            message: '请选择车辆',
            trigger: 'blur'
          }
        ]
      },
      request: {
        project: null,
        train: null,
        time: null
      },
      projects: [],
      trains: [],
      records: [],
      currentPage: 1,
      pageNum: 1,
      pageSize: 20,
      total: 0,
      loading: false,
      tableMaxHeight: document.body.offsetHeight - 220,
      detailVisible: false,
      datas: {},
      selectRecord: null,
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now()
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date())
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', date)
          }
        }]
      }
    }
  },
  mounted() {
    this.getProject()
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeTableHeight)
    // window.on('resize', this.changeTableHeight)
    this.changeTableHeight()
  },
  methods: {
    selectTime(e) {
      if (e === null) {
        this.minDate = ''
      }
    },
    changeTableHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 220
      var sectionHeight = document.body.offsetHeight - 50 + 'px'
      var element = document.getElementById('faultInfor')
      if (element !== null) {
        element.style.height = sectionHeight
      }
    },
    getProject() {
      app.get('get_project').then(data => {
        if (data.msg) {
          data.msg.forEach(element => {
            this.projects.push(element.name)
          })
          if (this.projects.length > 0) {
            this.request.project = this.projects[0]
            this.projectChanged()
          }
        }
      })
    },
    projectChanged() {
      this.trains = []
      const param = {
        project: this.request.project
      }
      app.get('get_train_no', param).then(data => {
        if (data.msg) {
          data.msg.forEach(element => {
            this.trains.push(element.trainNo)
          })
          if (this.trains.length > 0) {
            this.request.train = this.trains[0]
          }
        }
      })
    },
    queryRecords() {
      this.currentPage = 1
      this.$refs['request'].validate(valid => {
        if (valid) {
          this.loading = true
          var param = {
            time: this.request.time + ' 00:00:00',
            project: this.request.project,
            train: this.request.train,
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
          app.get('history_count', param).then(data => {
            if (data.msg) {
              this.total = data.msg
              this.handleCurrentChange(1)
            } else {
              this.loading = false
            }
          }).catch(e => {
            this.loading = false
          })
        }
      })
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.getDataRecords()
    },
    handleCurrentChange(val) {
      this.pageNum = val
      this.getDataRecords()
    },
    getDataRecords() {
      this.$refs['request'].validate(valid => {
        if (valid) {
          this.records = []
          this.loading = true
          var param = {
            time: this.request.time + ' 00:00:00',
            project: this.request.project,
            train: this.request.train,
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
          console.log(param)
          app.get('history_record', param).then(data => {
            if (data.msg) {
              data.msg.records.forEach(item => {
                this.records.push(item)
              })
            }
            // this.total = data.msg.total
            this.loading = false
          }).catch(e => {
            this.loading = false
          })
        }
      })
    },
    selectRow(row) {
      const params = {
        project: row.project,
        train: row.train,
        carriageId: row.carriageId,
        doorId: row.doorId,
        timestamp: row.timestamp,
        variables: variables.join(',')
      }
      const times = []
      app.get('history_data', params).then(data => {
        if (data.code === 0) {
          const msg = data.msg
          if (msg.keys) {
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
            this.datas = {
              keys: keys,
              times: times,
              data: values,
              state: msg.state,
              baseValues: msg.baseValues
            }
          }
          this.detailVisible = true
          this.selectRecord = row
        }
      })
    }
  }
}
</script>
<style scoped>
.history-data{
  width: 100%;
  height: 100%;
  background-color: #242640;
}
.history-data .query{
  padding:16px 15px 0px;
}
.history-data .query .el-input {
	width: 120px;
}
.center {
	display: flex;
	-webkit-align-items: center;
	align-items: center;
	-webkit-justify-content: center;
	justify-content: center;
}
</style>
