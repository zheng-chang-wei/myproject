<template>
  <div class="app-container">
    <el-form ref="form" :rules="formRules" :model="form" label-width="150px" size="mini">
      <el-form-item label="数据下载目标IP" prop="targetIp">
        <el-input v-model="form.targetIp" maxlength="15" />
      </el-form-item>
      <el-form-item label="数据下载目标路径" prop="targetPath">
        <el-input v-model="form.targetPath" />
      </el-form-item>
      <el-form-item label="FTP用户名" prop="userName">
        <el-input v-model="form.userName" />
      </el-form-item>
      <el-form-item label="FTP密码" prop="password">
        <el-input v-model="form.password" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="float:right" @click="onSubmit">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import app from '@/common/js/app'
export default {
  data() {
    const checkTargetIp = (rule, value, callback) => {
      if (!this.isValidIP(value)) {
        callback(new Error('IP地址不合法'))
      } else {
        callback()
      }
    }
    return {
      form: {
        id: '',
        targetPath: '',
        targetIp: ''
      },
      formRules: {
        targetIp: [
          {
            required: true,
            message: '必填',
            trigger: 'change'
          },
          {
            validator: checkTargetIp,
            trigger: 'change'
          }
        ],
        targetPath: [
          {
            required: true,
            message: '必填',
            trigger: 'change'
          }
        ],
        userName: [
          {
            required: true,
            message: '必填',
            trigger: 'change'
          }
        ],
        password: [
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
    this.getTargetConfig()
  },
  methods: {
    isValidIP(ip) {
      var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
      return reg.test(ip)
    },
    onSubmit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          app.post('setTargetConfig', this.form).then(response => {
            if (response.code === 0) {
              this.$message({
                type: 'success',
                message: '保存成功'
              })
            }
          })
        }
      })
    },
    getTargetConfig() {
      app.get('getTargetConfig').then(response => {
        if (response.code === 0) {
          this.form = response.msg
        }
      })
    }
  }
}
</script>

<style scoped>
.line{
  text-align: center;
}
</style>

