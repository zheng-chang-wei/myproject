<template>
  <el-dialog id="csPortDialog" title="添加CSPort对象" :visible.sync="addObjDialogVisible" width="60%" top="1vh">
    <!--查询-->
    <el-form :inline="true" :model="form" size="mini">
      <el-form-item label="comId" prop="comId">
        <el-input v-model="form.comId" placeholder="comId" :maxlength="20" @input="inputChange" />
      </el-form-item>
      <el-form-item label="源IP地址" prop="ip">
        <el-input v-model="form.ip" placeholder="源IP地址" :maxlength="20" @input="inputChange" />
      </el-form-item>
      <el-form-item label="车号" prop="trainNo">
        <el-input v-model="form.trainNo" placeholder="车号" :maxlength="20" @input="inputChange" />
      </el-form-item>
      <el-form-item label="板卡号" prop="cardNo">
        <el-input v-model="form.cardNo" placeholder="板卡号" :maxlength="20" @input="inputChange" />
      </el-form-item>
      <el-form-item label="端口号" prop="port">
        <el-input v-model="form.port" placeholder="端口号" :maxlength="20" @input="inputChange" />
      </el-form-item>
    </el-form>
    <el-table
      ref="allObjTable"
      :border="true"
      :data="allObjTableDatas"
      highlight-current-row
      :height="tableMaxHeight"
      :header-cell-style="{padding:'3px'}"
      :cell-style="{padding:'3px'}"
      @select="tableSelect"
    >
      <el-table-column type="selection" align="center" />
      <el-table-column prop="comId" label="comId" align="center" />
      <el-table-column prop="ip" label="源IP地址" align="center" />
      <el-table-column prop="trainNo" label="车号" align="center" />
      <el-table-column prop="cardNo" label="板卡号" align="center" />
      <el-table-column prop="port" label="端口号" align="center" />
    </el-table>
    <!--分页  工具条-->
    <el-row class="toolbar" style="margin-top:10px">
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
    <span slot="footer" class="dialog-footer">
      <el-button size="mini" @click="addObjDialogVisible = false">取 消</el-button>
      <el-button type="primary" size="mini" @click="addComIdObjConfirm">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import app from '@/common/js/app'
export default {
  data() {
    return {
      form: {
        comId: '',
        ip: '',
        trainNo: '',
        cardNo: '',
        port: ''
      },
      addObjDialogVisible: false,
      tableMaxHeight: '0',
      allObjTableDatas: [],
      total: 0,
      pageNum: 1,
      pageSize: 20,
      currentPage: 1,
      selectedObjTableDatas: []
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
  },
  mounted() {
    this.getTableDatas()
  },
  methods: {
    changeHeight() {
      this.tableMaxHeight = document.body.offsetHeight * 0.6 + 'px'
    },
    openAddObjDialog(selection) {
      this.selectedObjTableDatas = []
      selection.forEach(element => {
        this.selectedObjTableDatas.push(element)
      })
      this.addObjDialogVisible = true
      const vm = this
      if (vm.$refs.allObjTable !== undefined) {
        vm.setSelect()
      } else {
        setTimeout(function() {
          vm.setSelect()
        }, '10')
      }
    },
    tableSelect(selection, row) {
      const sameData = this.getSameData(row, this.selectedObjTableDatas)
      if (sameData === null) {
        this.selectedObjTableDatas.push(row)
      } else {
        this.selectedObjTableDatas.splice(this.selectedObjTableDatas.indexOf(sameData), 1)
      }
    },
    inputChange() {
      this.currentPage = 1
      this.handleCurrentChange(1)
    },
    addComIdObjConfirm() {
      this.$emit('addComIdObjConfirm', this.selectedObjTableDatas)
      this.addObjDialogVisible = false
    },
    setSelect() {
      this.$refs.allObjTable.clearSelection()
      this.selectedObjTableDatas.forEach(element => {
        this.$refs.allObjTable.toggleRowSelection(this.getSameData(element, this.allObjTableDatas), true)
      })
    },
    getSameData(element, tableData) {
      for (let index = 0; index < tableData.length; index++) {
        const data = tableData[index]
        if (data.comId === element.comId && data.ip === element.ip && data.port === element.port) {
          return data
        }
      }
      return null
    },
    getTableDatas() {
      const parm = {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        comId: this.form.comId,
        ip: this.form.ip,
        trainNo: this.form.trainNo,
        port: this.form.port,
        cardNo: this.form.cardNo
      }
      app.get('listCsPorts', parm).then(response => {
        if (response.code === 0) {
          this.allObjTableDatas = response.msg.rows
          this.total = response.msg.total
          if (this.$refs.allObjTable !== undefined) {
            const vm = this
            setTimeout(function() {
              vm.setSelect()
            }, '10')
          }
        }
      }).catch(response => {
        console.log(response)
      })
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

#csPortDialog .el-input{
  width: 80px;
}
</style>
