<template>
  <el-row class="center common">
    <el-col :span="5">
      <el-row class="projectName-panel home-card">
        <div class="card-border card-border-top-left" />
        <div class="card-border card-border-top-right" />
        <div class="card-border card-border-bottom-left" />
        <div class="card-border card-border-bottom-right" />
        <el-col :span="24">
          <el-select v-model="projectName" placeholder="项目名称" size="mini" @change="projectNameChange">
            <el-option
              v-for="item in projectOptions"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-col>
      </el-row>
    </el-col>
    <el-col :span="5">
      <el-row class="home-card">
        <div class="card-border card-border-top-left" />
        <div class="card-border card-border-top-right" />
        <div class="card-border card-border-bottom-left" />
        <div class="card-border card-border-bottom-right" />
        <el-col :span="24">
          <el-select v-model="trainNo" size="mini" placeholder="车辆编号" @change="trainNoChange">
            <el-option
              v-for="item in trainNoOptions"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-col>
      </el-row>
    </el-col>
    <el-col :span="14">
      <signalQuality :signal-quality="signalQuality" class="image-style" />
    </el-col>
  </el-row>
</template>

<script>
import app from '@/common/js/app'
import signalQuality from '@/components/realtimemonitor/signalQuality.vue'
export default {
  components: {
    signalQuality
  },
  props: {
    signalQuality: {
      type: Number,
      default: null
    }
  },
  data() {
    return {
      projectOptions: [],
      trainNoOptions: [],
      trainNo: '',
      projectName: ''
    }
  },
  created() {
  },
  mounted() {
    this.getProject()
    this.sendMessage()
  },
  beforeDestroy() {
  },
  methods: {
    // 获取项目名称
    getProject() {
      app.get('get_project').then(data => {
        if (data.msg) {
          data.msg.forEach(element => {
            this.projectOptions.push(element.name)
          })
        }
      })
    },
    projectNameChange() {
      this.trainNoOptions = []
      const params = {
        project: this.projectName
      }
      this.trainNo = ''
      app.get('get_train_no', params).then(data => {
        if (data.msg) {
          data.msg.forEach(element => {
            this.trainNoOptions.push(element.trainNo)
          })
        }
      })
    },
    trainNoChange() {
      this.sendMessage()
    },
    // 触发Eventbus，发送车辆信息到后端
    sendMessage() {
      if (this.projectName && this.trainNo) {
        const msg = "{'project': " + this.projectName + ",'train': " + this.trainNo + '}'
        this.$bus.$emit('sendMessage', msg)
        const parms = {
          project: this.projectName,
          trainNo: this.trainNo
        }
        this.$emit('getTrainConfig', parms)
      }
    }
  }
}
</script>

