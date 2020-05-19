<template>
  <section>
    <queryForm :json-fields="jsonFields" :table-datas="tableDatas" :cols="cols" :title="title" type="CsPort" @getDatas="getDatas" />
    <span class="center" style="font-size:13px;margin-bottom:5px">{{ title }}</span>
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :border="true"
      :data="tableDatas"
      :max-height="tableMaxHeight"
      highlight-current-row
      :header-cell-style="{padding:'3px',fontSize:'10.5px'}"
      :cell-style="{padding:'3px',fontSize:'10px'}"
    >
      <el-table-column type="index" label="序号" align="center" width="50px" />
      <el-table-column prop="comId" label="comId" align="center" width="60px" />
      <el-table-column prop="ip" label="源IP地址" align="center" />
      <el-table-column prop="trainNo" label="车号" align="center" width="50px" />
      <el-table-column prop="cardNo" label="板卡号" align="center" width="60px" />
      <el-table-column prop="port" label="端口号" align="center" width="60px" />
      <el-table-column prop="enable" label="使能状态" align="center" />
      <el-table-column prop="linkPHM" label="网口断开" align="center" />
      <el-table-column prop="linkFlashPHM" label="网口闪断" align="center" />
      <el-table-column prop="rxTrafficPHM" label="流量异常（收）" align="center" width="110px" />
      <el-table-column prop="txTrafficPHM" label="流量异常（发）" align="center" width="110px" />
      <el-table-column prop="rxErrRatePHM" label="误码异常" align="center" />
      <el-table-column prop="rxErrPredictPHM" label="误码趋势异常" align="center" width="100px" />
      <el-table-column label="是否异常" align="center">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.error ? 'danger' : 'success'"
            disable-transitions
          >{{ scope.row.error ? '是' : '否' }}</el-tag>
        </template>
      </el-table-column>
    </el-table>

  </section>
</template>

<script>
import app from '@/common/js/app'
import queryForm from './queryForm'
export default {
  components: {
    queryForm
  },
  data() {
    return {
      tableDatas: [],
      listLoading: false,
      tableMaxHeight: 0,
      jsonFields: {
        'ComID': 'comId',
        '源IP地址': 'ip',
        '车号': 'trainNo',
        '板卡号': 'cardNo',
        '端口号': 'port',
        '使能状态': 'enable',
        '网口断开': 'linkPHM',
        '网口闪断': 'linkFlashPHM',
        '流量异常（收）': 'rxTrafficPHM',
        '流量异常（发）': 'txTrafficPHM',
        '误码异常': 'rxErrRatePHM',
        '误码趋势异常': 'rxErrPredictPHM'
      },
      title: '',
      cols: [{
        wch: 5
      }, {
        wch: 10
      }, {
        wch: 5
      }, {
        wch: 6
      }, {
        wch: 6
      }, {
        wch: 8
      }, {
        wch: 8
      }, {
        wch: 8
      }, {
        wch: 15
      }, {
        wch: 15
      }, {
        wch: 8
      }, {
        wch: 15
      }]
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeTableMaxHeight)
    this.changeTableMaxHeight()
  },
  mounted() {
  },
  destroyed() {
    window.removeEventListener('resize', this.changeTableMaxHeight)
  },
  methods: {
    // 动态更改表格最大高度
    changeTableMaxHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 190
    },
    getDatas(time) {
      this.listLoading = true
      const parm = {
        startTime: time[0],
        endTime: time[1]
      }
      app.get('listCsPortOverview', parm).then(response => {
        if (response.code === 0) {
          this.tableDatas = response.msg
          this.listLoading = false
          let enableCount = 0
          let errorCount = 0
          this.tableDatas.forEach(element => {
            if (element.enable === 1) {
              enableCount++
              if (element.linkPHM + element.linkFlashPHM + element.rxTrafficPHM + element.txTrafficPHM + element.rxErrRatePHM + element.rxErrPredictPHM >= 1) {
                errorCount++
              }
            }
          })
          this.title = '一共' + this.tableDatas.length + '个网口，使能' + enableCount + '个网口，使能网口中有' + errorCount + '个异常。'
        }
      }).catch(response => {
        this.listLoading = false
        console.log(response)
      })
    }
  }
}
</script>
<style scoped>

</style>
