<template>
  <section>
    <el-collapse>
      <el-collapse-item :title="algorithmElm.name">
        <el-form :model="algorithmElm" label-position="left" label-width="100px">
          <el-form-item prop="delivery" style="text-align: right">
            <el-switch v-model="algorithmElm.enable" style="margin-right: 30px" @input="change" />
            <el-button type="danger" icon="el-icon-close" size="mini" circle @click="deleteBoard()" />
          </el-form-item>
          <el-form-item label="脚本文件">
            <el-input v-model="algorithmElm.fileOriginalName" style="width:300px" />
            <el-button type="primary" @click="uploadDialogVisible = true">上传脚本文件</el-button>
          </el-form-item>
          <el-form-item label="槽位号">
            <el-select v-model="algorithmElm.slotId" style="width:300px" @input="change">
              <el-option
                v-for="item in variables"
                :key="item.slotId"
                :label="item.slotId"
                :value="item.slotId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="算法分组">
            <el-select v-model="algorithmElm.subsystemId" style="width:300px" @input="change">
              <el-option
                v-for="item in subsystems"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <ConfigVariable :variables="variables" :groups="algorithmElm" />
        </el-form>
      </el-collapse-item>
    </el-collapse>
    <el-dialog title="上传脚本文件" :visible.sync="uploadDialogVisible" :close-on-click-modal="false">
      <el-upload ref="upload" action="" multiple accept=".xls,.xlsx" :auto-upload="false" :http-request="uploadFile">
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <el-button
          style="margin-left: 10px;margin-bottom:10px"
          size="small"
          type="success"
          :loading="uploadLoading"
          @click="submitUpload"
        >上传到服务器
        </el-button>
        <div slot="tip" class="el-upload__tip">只能上传Excel文件</div>
      </el-upload>
    </el-dialog>
  </section>
</template>

<script>
import app from '@/common/js/app'
import ConfigVariable from '@/components/ConfigVariable'
export default {
  components: {
    ConfigVariable
  },
  props: {
    variables: {
      type: Array,
      default: null
    },
    subsystems: {
      type: Array,
      default: null
    },
    algorithmElm: {
      type: Object,
      default: null
    },
    index: {
      type: Number,
      default: null
    }
  },
  data() {
    return {
      uploadDialogVisible: false,
      uploadLoading: false
    }
  },
  computed: {
  },
  mounted() {
  },
  methods: {
    change() {
      this.$forceUpdate()
    },
    deleteBoard() {
      this.$emit('deleteBoard', this.index)
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
          this.algorithmElm.fileOriginalName = param.file.name
          this.algorithmElm.filename = data.data
          this.uploadLoading = false
          this.$message({
            message: param.file.name + '上传成功',
            type: 'success'
          })
          param.onSuccess()
        }
      }).catch(response => {
        param.onError()
      })
    }
  }
}
</script>

<style>

</style>
