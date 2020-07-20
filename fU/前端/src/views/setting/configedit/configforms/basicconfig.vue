<template>
  <div>
    <el-input v-model="fileName" style="width:50%" />
    <input ref="file" style="display: none" type="file" accept=".zip" @change="handleClick">
    <el-button style="width: 20%; margin-left:10px" type="primary" @click="openFolderDialog">浏览</el-button>
    <el-button style="width: 20%" type="primary" @click="importFile">导入</el-button>
    <div class="divide-line" />
    <el-form ref="basicInfo" v-model="basicInfo" label-width="100px" style="margin-top: 10px">
      <el-form-item label="配置名称" prop="name">
        <el-input v-model="basicInfo.name" style="width: 80%" @input="change" />
      </el-form-item>
      <el-form-item label="线路" prop="line">
        <el-input v-model="basicInfo.line" style="width: 80%" @input="change" />
      </el-form-item>
      <el-form-item label="车号" prop="train">
        <el-input v-model="basicInfo.train" style="width: 80%" @input="change" />
      </el-form-item>
    </el-form>
    <div style="position: absolute; bottom: 10px;right:20px">
      <!-- <el-button style="width: 170px" @click="cancel">取消</el-button> -->
      <el-button style="width: 170px" type="primary" @click="save">保存</el-button>
      <el-button style="width: 170px" type="primary" @click="nextStep">下一步</el-button>
    </div>
  </div>
</template>

<script>
import app from '@/common/js/app'
export default {
  props: {
    basicInfo: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      fileName: '',
      fileobj: '',
      rules: {
        name: [
          {
            required: true,
            message: '必填',
            trigger: 'change'
          }
        ], line: [
          {
            required: true,
            message: '必填',
            trigger: 'change'
          }
        ], train: [
          {
            required: true,
            message: '必填',
            trigger: 'change'
          }
        ]
      }
    }
  },
  mounted() {
  },
  methods: {
    openFolderDialog() {
      this.$refs.file.click()
    },
    handleClick(e) {
      const files = e.target.files
      this.fileobj = files[0]
      this.fileName = this.fileobj.name
    },
    importFile() {
      this.$emit('importFile', this.fileobj)
    },
    change() {
      this.$forceUpdate()
    },
    nextStep() {
      if (!this.verify()) {
        return
      }
      this.basicInfo['id'] = localStorage.getItem('settingId')
      app.postData('save_basic', this.basicInfo).then(data => {
        if (data.code === 0) {
          localStorage.setItem('settingId', data.data)
          this.$emit('nextStep')
          this.$emit('basicSaveSuccess')
        }
      })
    },
    cancel() {
      this.$emit('cancel')
    },
    save() {
      if (!this.verify()) {
        return
      }
      this.basicInfo['id'] = localStorage.getItem('settingId')
      app.postData('save_basic', this.basicInfo).then(data => {
        if (data.code === 0) {
          localStorage.setItem('settingId', data.data)
          this.$message({
            message: '保存成功',
            type: 'success'
          })
          this.$emit('basicSaveSuccess')
        }
      })
    },
    verify() {
      if (this.basicInfo.name.replace(/\s+/g, '') === '') {
        this.$message({
          message: '配置名称不能为空',
          type: 'error'
        })
        return false
      }
      if (this.basicInfo.line.replace(/\s+/g, '') === '') {
        this.$message({
          message: '线路不能为空',
          type: 'error'
        })
        return false
      }
      if (this.basicInfo.train.replace(/\s+/g, '') === '') {
        this.$message({
          message: '车号不能为空',
          type: 'error'
        })
        return false
      }
      return true
    }
  }
}
</script>

<style>
  .divide-line{
    height: 10px;
    border-bottom: 1px solid #CCCCCC;
    margin-top: 10px;
  }

</style>
