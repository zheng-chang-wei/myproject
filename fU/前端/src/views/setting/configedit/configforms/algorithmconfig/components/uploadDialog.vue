<template>
  <el-dialog title="上传脚本文件" :visible.sync="uploadDialogVisible" :close-on-click-modal="false">
    <el-upload
      ref="upload"
      action=""
      accept=".py,.c"
      :auto-upload="false"
      :file-list="fileList"
      :http-request="uploadFile"
    >
      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      <el-button
        style="margin-left: 10px;margin-bottom:10px"
        size="small"
        type="success"
        :loading="uploadLoading"
        @click="submitUpload"
      >上传到设备
      </el-button>
      <div slot="tip" class="el-upload__tip">只能上传py或c文件</div>
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
      uploadLoading: false,
      uploadDialogVisible: false,
      fileList: []
    }
  },
  methods: {
    openDialog() {
      this.uploadDialogVisible = true
    },
    submitUpload() {
      this.$refs.upload.submit()
    },
    // 上传文件
    uploadFile(param) {
      this.uploadLoading = true
      const formData = new FormData()
      formData.append('file', param.file)
      app.uploadFile('upload_script', formData).then(data => {
        if (data.code === 0) {
          this.uploadLoading = false
          this.$message({
            message: param.file.name + '上传成功',
            type: 'success'
          })
          param.onSuccess()
          this.handleClose()
          this.$emit('uploadSuccess', {
            fileOriginalName: param.file.name,
            filename: data.data
          })
        }
      }).catch(response => {
        param.onError()
      })
    },
    handleClose() {
      this.fileList = []
      this.uploadDialogVisible = false
    }
  }
}
</script>

<style>

</style>
