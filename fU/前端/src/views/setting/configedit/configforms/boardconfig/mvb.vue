<template>
  <div class="mvb">
    <el-collapse>
      <el-collapse-item :title="mvbForm.type">
        <el-form ref="mvbForm" v-model="mvbForm" label-position="left" label-width="100px">
          <el-form-item prop="enable" style="text-align: right">
            <el-switch v-model="mvbForm.enable" style="margin-right: 30px" />
            <el-button type="danger" icon="el-icon-close" size="mini" circle @click="deleteBoard()" />
          </el-form-item>
          <el-form-item label="卡槽号">
            <el-input v-model="mvbForm.slotId" style="width:300px" />
          </el-form-item>
          <el-form-item label="前面板IP">
            <el-input v-model="mvbForm.ip" style="width:300px" />
          </el-form-item>
          <el-form-item label="数据流文件">
            <el-input v-model="mvbForm.fileOriginalName" style="width:300px" />
            <el-button type="primary" @click="uploadBtnChecked">上传文件</el-button>
          </el-form-item>
        </el-form>
      </el-collapse-item>
    </el-collapse>
    <el-dialog title="上传数据流文件" :visible.sync="uploadDialogVisible" :close-on-click-modal="false">
      <el-upload
        ref="upload"
        action=""
        multiple
        accept=".xls,.xlsx"
        :auto-upload="false"
        :http-request="uploadFile"
      >
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <el-button style="margin-left: 10px;margin-bottom:10px" size="small" type="success" :loading="uploadLoading" @click="submitUpload">上传到服务器
        </el-button>
        <div slot="tip" class="el-upload__tip">只能上传Excel文件</div>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script>
import app from '@/common/js/app'
export default {
  props: {
    index: {
      type: Number,
      default: null
    },
    mvbForm: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      uploadDialogVisible: false,
      uploadLoading: false
    }
  },
  methods: {
    deleteBoard() {
      this.$emit('deleteBoard', this.index)
    },
    uploadBtnChecked() {
      this.uploadDialogVisible = true
    },
    submitUpload() {
      this.$refs.upload.submit()
    },
    // 上传文件
    uploadFile(param) {
      this.uploadLoading = true
      const formData = new FormData()
      formData.append('type', this.mvbForm.type)
      formData.append('file', param.file)
      app.uploadFile('upload_protocol', formData).then(data => {
        if (data.code === 0) {
          this.mvbForm.fileOriginalName = param.file.name
          this.uploadLoading = false
          this.$message({
            message: param.file.name + '上传成功',
            type: 'success'
          })
          param.onSuccess()
          this.mvbForm.variables = data.data.variables
          this.mvbForm.filename = data.data.path
        }
      }).catch(response => {
        param.onError()
        console.log(response)
      })
    }

  }
}
</script>

<style>

</style>
