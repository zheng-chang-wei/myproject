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
        <el-table-column :key="item" :prop="item" :label="item" align="center" />
      </template>
    </el-table>
    <el-row v-if="isQueryTable" class="toolbar" style="position:absolute;bottom:10px;">
      <!-- <el-button type="primary" size="mini" @click="export2Excel">导出CSV</el-button> -->
      <JsonExcel
        class="export-excel-wrapper"
        :data="allTableDatas"
        :fields="json_fields"
        :name="excelName"
      >
        <!-- 上面可以自定义自己的样式，还可以引用其他组件button -->
        <el-button type="primary" size="small">导出Excel</el-button>
      </JsonExcel>
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
import { dateFormat } from '@/utils/index'
import showLine from './components/showLine/index'
import queryForm from './components/queryForm/index'
import JsonExcel from 'vue-json-excel'
export default {
  components: {
    showLine,
    queryForm,
    JsonExcel
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
    this.$bus.$on('objChange', (data) => {
      this.objs = []
      data.forEach(element => {
        this.objs.push(element)
      })
    })
    this.$bus.$on('featuresChange', (data) => {
      this.features = []
      this.json_fields = {
        'date': 'time',
        'IP': 'ip',
        'Comid': 'comId'
      }
      if (this.type === 'CsPort') {
        this.json_fields['port'] = 'port'
      }
      data.forEach(element => {
        this.features.push(element.featureName)
        this.json_fields[element.featureName] = element.featureName
      })
    })
  },
  mounted() {
    // this.getTableDatas()
  },
  beforeDestroy() {
    this.$bus.$off('objChange')
    this.$bus.$off('featuresChange')
  },
  methods: {
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
      this.excelName = this.type + '_' + dateFormat('YYYY-mm-dd HH:MM:SS', new Date()) + '.xls'
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
        features += element + ','
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
        time: this.formData.time
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
