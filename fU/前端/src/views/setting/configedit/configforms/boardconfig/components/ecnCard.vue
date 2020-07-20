<template>
  <div>
    <el-form ref="myCardForm" v-model="myCardForm" label-position="right" label-width="120px">
      <el-form-item label="槽位:">
        <el-col :span="2">
          {{ myCardForm.slotId }}
        </el-col>
        <el-col :span="6">
          {{ myCardForm.type }}
        </el-col>
        <el-col :span="8" class="cardEnable">
          使能板卡：<el-switch v-model="myCardForm.enable" @change="switchChange" />
        </el-col>
      </el-form-item>
      <el-form-item label="数据流文件:" required>
        <el-input v-model="myCardForm.originalName" disabled style="width:50%" />
        <el-button type="primary" @click="uploadBtnChecked">上传文件</el-button>
      </el-form-item>
      <el-form-item label="前面板ETH 1 ip:" required>
        <el-input v-model="myCardForm.ip1" style="width:50%" :disabled="isDisabled" />
      </el-form-item>
      <el-form-item label="前面板ETH 2 ip:" required>
        <el-input v-model="myCardForm.ip2" style="width:50%" :disabled="isDisabled" />
      </el-form-item>
    </el-form>
    <el-dialog title="上传数据流文件" :visible.sync="uploadDialogVisible" :close-on-click-modal="false" :before-close="handleClose">
      <el-upload
        ref="upload"
        action=""
        accept=".xls,.xlsx"
        :file-list="fileList"
        :auto-upload="false"
        :http-request="uploadFile"
      >
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <el-button style="margin-left: 10px;margin-bottom:10px" size="small" type="success" :loading="uploadLoading" @click="submitUpload">上传到设备
        </el-button>
        <div slot="tip" class="el-upload__tip">只能上传xls或xlsx文件</div>
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
    cardForm: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      uploadDialogVisible: false,
      uploadLoading: false,
      myCardForm: null,
      fileList: [],
      isDisabled: true
    }
  },
  created() {
    this.setCardForm(this.cardForm)
  },
  mounted() {
  },
  methods: {
    setCardForm(cardForm) {
      this.myCardForm = {
        slotId: cardForm.slotId,
        type: cardForm.type,
        enable: cardForm.enable,
        filename: cardForm.filename,
        originalName: cardForm.originalName,
        ip1: cardForm.ip1,
        ip2: cardForm.ip2,
        label1: '前面板ETH1 ip',
        label2: '前面板ETH2 ip',
        filenameLabel: '数据流文件'
      }
      this.isDisabled = !this.myCardForm.enable
      if (this.myCardForm.filename === undefined) {
        this.myCardForm.filename = ''
      }
      for (let index = 1; index <= 2; index++) {
        if (this.myCardForm['ip' + index] === undefined) {
          this.myCardForm['ip' + index] = ''
        }
      }
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
      formData.append('type', this.myCardForm.type)
      formData.append('file', param.file)
      app.uploadFile('upload_protocol', formData).then(data => {
        if (data.code === 0) {
          this.myCardForm.originalName = param.file.name
          this.uploadLoading = false
          this.$message({
            message: param.file.name + '上传成功',
            type: 'success'
          })
          param.onSuccess()
          this.myCardForm.filename = data.data.path
          this.handleClose()
          this.$emit('uploadSuccess', this.myCardForm.type)
        }
      }).catch(response => {
        param.onError()
        console.log(response)
      })
    },
    getCardForm() {
      return this.myCardForm
    },
    handleClose() {
      this.fileList = []
      this.uploadDialogVisible = false
    },
    switchChange(value) {
      this.isDisabled = !value
    }
  }
}
</script>

<style>

</style>
