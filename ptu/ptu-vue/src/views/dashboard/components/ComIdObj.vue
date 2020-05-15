<template>
  <section>
    <queryForm :table-datas="tableDatas" :json-fields="jsonFields" :title="title" :cols="cols" type="ComID" @getDatas="getDatas" />
    <span class="center" style="font-size:13px;margin-bottom:5px">{{ title }}</span>
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :border="true"
      :data="tableDatas"
      :max-height="tableMaxHeight"
      highlight-current-row
      :header-cell-style="{padding:'3px',fontSize:'11px'}"
      :cell-style="{padding:'3px',fontSize:'10px'}"
    >
      <el-table-column type="index" label="序号" align="center" width="50px" />
      <el-table-column prop="comId" label="comId" align="center" width="70" />
      <el-table-column prop="ip" label="源IP地址" align="center" width="100" />
      <el-table-column prop="carriagePosition" label="车厢位置" align="center" width="75" />
      <el-table-column prop="remark1" label="备注" align="center" />
      <el-table-column prop="periodStabilityPHM" label="周期稳定性异常" align="center" width="105" />
      <el-table-column prop="lostRatePHM" label="丢帧率异常" align="center" width="95" />
      <el-table-column prop="abnomalLostPHM" label="丢帧行为异常" align="center" width="105" />
      <el-table-column prop="frameCnt" label="平均每秒报文数" align="center" width="115" />
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
        '车厢位置': 'carriagePosition',
        '备注': 'remark1',
        '周期稳定性异常': 'periodStabilityPHM',
        '丢帧率异常': 'lostRatePHM',
        '丢帧行为异常': 'abnomalLostPHM',
        '平均每秒报文数': 'frameCnt'
      },
      title: '',
      cols: [{
        wch: 5
      }, {
        wch: 10
      }, {
        wch: 8
      }, {
        wch: 60
      }, {
        wch: 15
      }, {
        wch: 15
      }, {
        wch: 15
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
      app.get('listComIdOverview', parm).then(response => {
        if (response.code === 0) {
          this.tableDatas = response.msg.comIdDataOverviewTableDataList
          this.title = '一共' + response.msg.allComIdObjectSize + '个ComID对象，其中' + response.msg.hasDataComIdObjectSize + '个对象有数据，有数据对象中有' + response.msg.errorCount + '个异常。1编组流量均值：' + response.msg.groupFlow1 + 'MB/S，2编组流量均值：' + response.msg.groupFlow2 + 'MB/S'
          this.listLoading = false
        }
      }).catch(response => {
        console.log(response)
        this.listLoading = false
      })
    }
  }
}
</script>
<style>

</style>
