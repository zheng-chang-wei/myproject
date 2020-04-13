<template>
  <section>
    <el-form :inline="true" size="mini">
      <el-form-item>
        <el-input v-model="fileName" />
        <input ref="file" style="display: none" accept=".xls,.xlsx" type="file" @change="handleClick">
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="openFolderDialog">浏览</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :loading="importLoading" @click="importFile">导入</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :border="true"
      :data="tableDatas"
      :max-height="tableMaxHeight"
      highlight-current-row
    >
      <el-table-column type="index" label="序号" align="center" width="50px" />
      <el-table-column prop="comId" label="comId" align="center" />
      <el-table-column prop="ip" label="源IP地址" align="center" />
      <el-table-column prop="trainNo" label="车号" align="center" />
      <el-table-column prop="cardNo" label="板卡号" align="center" />
      <el-table-column prop="port" label="端口号" align="center" />
    </el-table>
  </section>
</template>

<script>
import app from '@/common/js/app'
export default {
  data() {
    return {
      tableDatas: [],
      listLoading: false,
      tableMaxHeight: 0,
      fileName: '',
      fileobj: null,
      importLoading: false
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeTableMaxHeight)
    this.changeTableMaxHeight()
  },
  mounted() {
    this.getDatas()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeTableMaxHeight)
  },
  methods: {
    // 动态更改表格最大高度
    changeTableMaxHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 200
    },
    getDatas() {
      this.listLoading = true
      app.get('listCsPorts').then(response => {
        if (response.code === 0) {
          this.tableDatas = response.msg
          this.listLoading = false
        }
      }).catch(response => {
        this.listLoading = false
        console.log(response)
      })
    },
    openFolderDialog() {
      this.$refs.file.click()
    },
    handleClick(e) {
      const files = e.target.files
      this.fileobj = files[0]
      this.fileName = this.fileobj.name
    },
    importFile() {
      const formData = new FormData()
      formData.append('file', this.fileobj)
      if (this.fileobj === null) {
        return
      }
      this.importLoading = true
      app.postData('csPortImport', formData).then(response => {
        if (response.code === 0) {
          this.$message({
            message: '上传成功',
            type: 'success'
          })
          this.getDatas()
          this.importLoading = false
        }
      }).catch(response => {
        this.importLoading = false
        console.log(response)
      })
    }
  }
}
</script>
<style scoped>

</style>
