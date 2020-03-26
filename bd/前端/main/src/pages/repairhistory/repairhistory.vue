<template>
  <section id="repairhistory" class="app-container">
    <!--查询-->
    <!-- TODO 不适用trainSelect控件 -->
    <trainSelect style="margin-top:16px" is-contain-fault-type @query="query" />
    <!-- <el-row class="center" style="margin-top:20px;background-color:#575757; color:white;height:30px">
      故障维修记录</el-row> -->
    <!--故障维修记录table-->
    <el-table
      v-loading="repairHistoryListLoading"
      :data="repairHistoryTableDatas"
      :max-height="tableMaxHeight"
      border
      @row-click="getAllDetails"
    >
      <el-table-column prop="sheet.project" label="项目名称" width="150px" align="center" />
      <el-table-column prop="sheet.trainId" label="列车编号" align="center" />
      <el-table-column prop="sheet.doorId" label="车门编号" align="center" />
      <el-table-column prop="sheet.faultMode" label="故障模式" align="center" />
      <el-table-column
        prop="sheet.faultTime"
        label="故障时间"
        width="170px"
        align="center"
      />
      <el-table-column prop="sheet.state" label="当前状态" align="center" />
      <el-table-column prop="sheet.faultTypeName" label="故障类型" align="center" />
      <el-table-column prop="option" label="是否可处理" align="center" />
    </el-table>
    <!--添加维修记录按钮-->
    <el-col :span="10" style="position:absolute;bottom:10px;">
      <el-button size="small" @click="handleConfigVariable">配置自定义变量</el-button>
      <el-button size="small" @click="addWorkSheet">添加维修记录</el-button>
      <el-button size="small" type="primary" @click="exportSheets">导出记录</el-button>
    </el-col>
    <!--分页  工具条-->
    <el-col :span="14" class="toolbar" style="position:absolute;bottom:10px;right:3%">
      <el-pagination
        :current-page.sync="currentPage"
        :page-sizes="[10, 20, 30, 50, 100]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        style="float: right;"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </el-col>
  </section>
</template>

<script>
import app from '@/common/js/app'
import trainSelect from '@/components/systemsetting/trainSelect'
export default {
  components: {
    trainSelect
  },
  data() {
    return {
      projectName: null,
      // 列车信息 trainNo：列车编号 state:列车状态
      trainNo: null,
      faultType: null,
      // 故障表格数据
      repairHistoryTableDatas: [],
      // 分页信息
      pageNum: 1,
      pageSize: 10,
      currentPage: 1,
      total: 0,
      repairHistoryListLoading: false,
      tableMaxHeight: document.body.offsetHeight - 160
    }
  },
  mounted() {
    this.getAllWorkSheet()
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeTableHeight)
    this.changeTableHeight()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeTableHeight)
  },
  methods: {
    exportSheets() {
      const params = {
        project: this.projectName,
        trainNo: this.trainNo,
        faultType: this.faultType
      }
      app.download('sheet_export', params).then(res => {
        const link = document.createElement('a')
        const blob = new Blob([res], { type: 'application/octet-stream' })
        link.style.display = 'none'
        link.href = URL.createObjectURL(blob)
        link.setAttribute('download', '工单列表' + '.csv')
        document.body.appendChild(link)
        link.click()
        document.body.removeChild(link)
      })
    },
    changeTableHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 160
    },
    query(formObject) {
      this.projectName = formObject.projectName
      this.trainNo = formObject.trainNo
      this.faultType = formObject.faultType
      this.currentPage = 1
      this.handleCurrentChange(1)
    },
    // 分页触发
    handleCurrentChange(val) {
      this.pageNum = val
      this.getAllWorkSheet()
    },
    // 改变页码
    handleSizeChange(val) {
      this.pageSize = val
      this.getAllWorkSheet()
    },
    // table的点击事件
    getAllDetails(row, column, event) {
      // 跳转到addFault界面，该界面只对填写的信息进行展示。 可以通过query传递参数。在
      // addFault通过this.$route.query.proName 取参。
      this.$router.push({
        path: '/repairhistory/addFault',
        query: {
          detailID: row.sheet.detailId + '',
          sheetID: row.sheet.id + '',
          state: row.sheet.state,
          jump: true
        }
      })
    },
    addWorkSheet() {
      this.$router.push({
        path: '/repairhistory/addFault'
      })
    },
    // 获取所有的workSheet
    getAllWorkSheet() {
      var vm = this
      const param = {
        project: vm.projectName,
        trainNo: vm.trainNo,
        faultType: vm.faultType,
        pageNum: vm.pageNum,
        pageSize: vm.pageSize
      }
      app.get('get_all_workSheet', param).then(response => {
        if (response.code === 0) {
          vm.repairHistoryTableDatas = []
          for (let index = 0; index < response.data.length; index++) {
            const element = response.data[index]
            vm.repairHistoryTableDatas.push({
              sheet: element.sheet,
              option: element.option ? '是' : '否'
            })
          }
          vm.total = response.total
        }
      })
    },
    handleConfigVariable() {
      this.$router.push({
        path: '/repairhistory/config'
      })
    }
  }
}

</script>

<style>
	#repairhistory {
		width: 100%;
		height: 100%;
	}

	#repairhistory .center {
		display: flex;
		-webkit-align-items: center;
		align-items: center;
		-webkit-justify-content: center;
		justify-content: center;
	}

</style>
