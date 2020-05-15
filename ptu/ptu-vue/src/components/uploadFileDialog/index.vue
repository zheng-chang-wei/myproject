<template>
  <el-dialog id="uploadFile" title="上传文件" :visible.sync="uploadImageDialogVisible" :close-on-click-modal="false" :before-close="handleClose">
    <el-upload
      ref="upload"
      action=""
      multiple
      accept=".csv"
      :limit="2000"
      :file-list="fileList"
      :auto-upload="false"
      :on-change="addFile"
      :http-request="uploadFile"
      :before-upload="beforeUpload"
      :on-exceed="onExceed"
    >
      <el-button slot="trigger" size="mini" type="primary">选取文件</el-button>
      <el-button style="margin-left: 10px;" size="mini" type="success" @click="submitUpload">上传到服务器
      </el-button>
      <div slot="tip" class="el-upload__tip">只能上传csv文件</div>
    </el-upload>
  </el-dialog>
</template>

<script>
import app from '@/common/js/app'
export default {
  props: {

  },
  data() {
    return {
      uploadImageDialogVisible: false,
      fileList: [],
      loading: null,
      paramList: [],
      onceUploadCount: 10	// 一次上传文件的数量
    }
  },
  created() {
    this.$bus.$on('uploadSuccess', (data) => {
      console.log(data)
      this.handleFileUploadResult(data)
    })
  },
  mounted() {
  },
  destroyed() {
    this.$bus.$off('uploadSuccess')
  },
  methods: {
    // 上传文件按钮被点击后，显示上传文件的提示框
    openFileUploadDialog() {
      this.uploadImageDialogVisible = true
    },
    submitUpload() {
      this.paramList = []
      this.fileDataList = []
      this.updateSuccessCount = 0
      if (this.fileList.length === 0) {
        this.$message({
          message: '请先选取文件',
          type: 'warning'
        })
      } else {
        this.$refs.upload.submit()
        this.appendFileAndSendFile(0)
      }
    },

    // 上传文件
    uploadFile(param) {
      this.paramList.push(param)
      this.fileDataList.push(param.file)
    },
    appendFileAndSendFile(updateSuccessCount) {
      let loopTimes = this.onceUploadCount + updateSuccessCount
      if (loopTimes > this.fileDataList.length) {
        loopTimes = this.fileDataList.length
      }
      for (let i = updateSuccessCount; i < loopTimes; i++) {
        const fileData = new FormData()
        const element = this.fileDataList[i]
        fileData.append('file', element)
        fileData.append('count', updateSuccessCount)
        app.postData('upload_file', fileData).then(data => {}).catch(response => {
          // this.closeLoading()
        })
      }
    },
    handleFileUploadResult(data) {
      let param = null
      this.paramList.forEach(element => {
        if (data.msg.split(' ')[0] === element.file.name) {
          param = element
        }
      })
      if (data.code === 0) {
        this.$message({
          message: data.msg,
          type: 'success'
        })
        param.onSuccess()
      } else if (data.code === 1) {
        this.$message({
          message: data.msg,
          type: 'warning'
        })
        param.onError()
      } else if (data.code === 500) {
        this.$message({
          message: data.msg,
          type: 'error'
        })
        param.onError()
      }
      this.updateSuccessCount++

      if (this.updateSuccessCount === this.paramList.length) {
        this.closeLoading()
        this.uploadFileResponseDialogVisible = true
        this.$emit('uploadComplete')
      } else if (this.updateSuccessCount % this.onceUploadCount === 0) {
        this.appendFileAndSendFile(this.updateSuccessCount)
      }
    },

    beforeUpload(file) {
      this.loading = this.$loading({
        lock: true,
        text: '文件上传中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      const isLt10M = file.size / 1024 / 1024 < 10 // 这里做文件大小限制
      if (!isLt10M) {
        this.$message({
          message: '上传文件须小于 10MB!',
          type: 'error'
        })
      }
      return isLt10M
    },
    onExceed() {
      this.$message({
        message: '文件个数超过2000，请重新选择',
        type: 'error'
      })
    },
    // 显示上传的文件
    addFile(file, fileList) {
      this.fileList = fileList
    },
    handleClose(done) {
      this.close()
      done()
    },
    close() {
      this.fileList = []
    },
    closeLoading() {
      if (this.loading !== null) {
        this.loading.close()
        this.loading = null
      }
    }
  }
}
</script>
<style >
#uploadFile .el-dialog__body {
    padding: 5px 15px 15px;
    color: #606266;
    font-size: 14px;
    word-break: break-all;
}
</style>
