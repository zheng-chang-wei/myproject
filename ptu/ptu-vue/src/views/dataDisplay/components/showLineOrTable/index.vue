<template>
  <div class="dataStyle" :style="dataStyle">
    <queryForm :type="type" @queryTableDatas="queryTableDatas" @queryLineDatas="queryLineDatas" />
    <el-table
      v-if="isQueryTable"
      v-loading="listLoading"
      border
      style="width:100%"
      :data="tableDatas"
      highlight-current-row
      :max-height="tableMaxHeight"
      :header-cell-style="{padding:'1px',fontSize:'9px'}"
      :cell-style="{padding:'3px',fontSize:'10px'}"
    >
      <el-table-column prop="date" label="time" align="center" />
      <el-table-column prop="comId" label="comId" align="center" />
      <el-table-column prop="ip" label="源IP地址" align="center" />
      <el-table-column v-if="type==='CsPort'" prop="port" label="端口" align="center" width="42" />
      <template v-for="item in features">
        <el-table-column :key="item.name_EN" :prop="item.name_EN" :label="item.name_CN" align="center" />
      </template>
    </el-table>
    <el-row v-if="isQueryTable" class="toolbar" style="position:absolute;bottom:10px;">
      <el-button type="primary" size="mini" @click="exportExcel(tableDatas)">导出本页</el-button>
    </el-row>
    <el-row v-if="isQueryTable" class="toolbar" style="position:absolute;bottom:10px;margin-left:90px">
      <el-button type="primary" size="mini" @click="exportExcel(allTableDatas)">导出所有</el-button>
    </el-row>
    <!--分页  工具条-->
    <el-row v-if="isQueryTable" class="toolbar" style="position:absolute;bottom:10px;right:20px">
      <el-pagination
        :current-page.sync="currentPage"
        :page-sizes="[20, 50, 100]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        style="float: right;"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </el-row>
    <el-row v-else>
      <showLine ref="showLine" :type="type" />
    </el-row>
  </div>
</template>

<script>
import app from '@/common/js/app'
import util from '@/common/js/util'
import showLine from './components/showLine/index'
import queryForm from './components/queryForm/index'
export default {
  components: {
    showLine,
    queryForm
  },
  props: {
    type: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      dataStyle: {},
      allTableDatas: [],
      tableDatas: [],
      features: [],
      objs: [],
      listLoading: false,
      isQueryTable: true,
      total: 0,
      pageNum: 1,
      pageSize: 20,
      currentPage: 1,
      tableMaxHeight: 0,
      formData: null,
      json_fields: {},
      excelName: ''
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
  },
  mounted() {
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.changeHeight)
  },
  methods: {
    objChange(data) {
      this.objs = []
      data.forEach(element => {
        this.objs.push(element)
      })
    },
    featuresChange(data) {
      this.features = []
      this.json_fields = {
        '时间': 'date',
        '源IP地址': 'ip',
        'ComID': 'comId'
      }
      if (this.type === 'CsPort') {
        this.json_fields['port'] = 'port'
      }
      data.forEach(element => {
        this.features.push({
          name_CN: element.featureName_CN,
          name_EN: element.featureName_EN
        })
        this.json_fields[element.featureName_CN] = element.featureName_EN
      })
    },
    changeHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 170
      if (this.isQueryTable) {
        this.dataStyle = { height: document.body.offsetHeight - 62 + 'px' }
      } else {
        this.dataStyle = {}
      }
    },
    // 查询
    queryTableDatas(formData) {
      this.excelName = this.type + util.replaceTime(formData.time[0]) + '-' + util.replaceTime(formData.time[1]) + '.xlsx'
      this.formData = formData
      this.isQueryTable = true
      this.changeHeight()
      this.currentPage = 1
      this.handleCurrentChange(this.currentPage)
    },
    queryLineDatas(formData) {
      if (!this.checkEmpty()) {
        return
      }
      this.formData = formData
      this.isQueryTable = false
      this.changeHeight()
      const vm = this
      setTimeout(function() {
        vm.$refs.showLine.queryLineDatas({
          parm: vm.getParm(),
          objs: vm.objs,
          features: vm.features
        })
      }, '10')
    },
    getTableDatas() {
      if (!this.checkEmpty()) {
        return
      }
      let url = ''
      if (this.type === 'CsPort') {
        url = 'getCsPortTableDatas'
      } else {
        url = 'getComIdTableDatas'
      }
      this.listLoading = true
      app.get(url, this.getParm()).then(response => {
        if (response.code === 0) {
          this.tableDatas = []
          this.allTableDatas = response.msg.rows
          for (let i = (this.pageNum - 1) * this.pageSize; i < (this.pageNum - 1) * this.pageSize + this.pageSize; i++) {
            if (i >= this.allTableDatas.length) {
              break
            }
            this.tableDatas.push(this.allTableDatas[i])
          }
          this.total = response.msg.total
          this.listLoading = false
          if (this.total === 0) {
            this.$message({
              type: 'warning',
              message: '查询数据为空'
            })
          }
        }
      }).catch(response => {
        console.log(response)
        this.listLoading = false
      })
    },
    checkEmpty() {
      if (this.objs.length === 0) {
        this.$message({
          type: 'warning',
          message: '至少选择一个对象'
        })
        return false
      }
      if (this.features.length === 0) {
        this.$message({
          type: 'warning',
          message: '至少选择一个变量'
        })
        return false
      }
      return true
    },
    getParm() {
      let comIds = ''
      let ips = ''
      let ports = ''
      let features = ''
      this.features.forEach(element => {
        features += element.name_EN + ','
      })
      if (this.type === 'CsPort') {
        this.objs.forEach(element => {
          comIds += element.comId + ','
          ips += element.ip + ','
          ports += element.port + ','
        })
      } else {
        this.objs.forEach(element => {
          comIds += element.comId + ','
          ips += element.ip + ','
        })
      }
      const parm = {
        comIds: comIds.substring(0, comIds.length - 1),
        ips: ips.substring(0, ips.length - 1),
        ports: ports.substring(0, ports.length - 1),
        features: features.substring(0, features.length - 1),
        logicalCondition: this.formData.logicalCondition,
        startTime: this.formData.time[0],
        endTime: this.formData.time[1]
      }
      return parm
    },
    // 分页触发
    handleCurrentChange(val) {
      this.pageNum = val
      this.getTableDatas()
    },
    // 改变页码
    handleSizeChange(val) {
      this.pageSize = val
      this.getTableDatas()
    },
    exportExcel(datas) {
      const keys = Object.keys(this.json_fields)
      const excelData = []
      excelData.push(keys)
      datas.forEach(element => {
        const arr = []
        Object.values(this.json_fields).forEach(function(value, key) {
          arr.push(element[value])
        })
        excelData.push(arr)
      })
      const cols = [{
        wch: 20
      }]
      for (let index = 1; index < keys.length; index++) {
        const element = keys[index]
        cols.push({
          wch: element.length * 2
        })
      }
      util.exportSpecialExcel(excelData, 0, this.excelName, cols)
    },
    exportAll() {

    }
  }
}
</script>

<style>

.dataStyle{
    padding: 10px;
    margin-right:5px;
    margin-top: 5px;
    border-radius: 2px;
    border: 1px solid #CCCCCC;
    line-height: 1px;
}
</style>
