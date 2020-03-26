<template>
  <el-col :span="24" class="query" style="padding-bottom: 0px;min-width:900px">
    <el-form :inline="true" :model="retrieveForm" size="mini">
      <el-form-item label="项目名称" prop="cityName">
        <el-select v-model="retrieveForm.projectName" placeholder="项目名称" @change="projectNameChange">
          <el-option
            v-for="item in projectOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="车辆编号" prop="train">
        <el-select v-model="retrieveForm.trainNo" placeholder="车辆编号">
          <el-option
            v-for="item in trainNoOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item v-if="isContainFaultType" label="故障类型">
        <el-select v-model="retrieveForm.faultType" placeholder="故障类型">
          <el-option
            v-for="item in faultTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="search" :disabled="checkButton()" @click="query">查询</el-button>
      </el-form-item>
    </el-form>
  </el-col>
</template>
<script>
const all_option = {
  value: null,
  label: '全部'
}
import app from '@/common/js/app'
export default {
  props: {
    single: Boolean,
    isContainFaultType: Boolean
  },
  data() {
    return {
      projectOptions: [all_option],
      trainNoOptions: [all_option],
      faultTypeOptions: [all_option],
      // 查询功能数据
      retrieveForm: {
        projectName: null,
        trainNo: null,
        faultType: null
      }
    }
  },
  mounted() {
    this.getProject()
    if (this.isContainFaultType) {
      this.getFaultTypes()
    }
  },
  methods: {
    // 获取项目名称
    getProject() {
      app.get('get_project').then(data => {
        if (data.msg) {
          data.msg.forEach(element => {
            this.projectOptions.push({
              value: element.name,
              label: element.name
            })
          })
        }
      })
    },
    projectNameChange() {
      this.trainNoOptions = [all_option]
      this.retrieveForm.trainNo = null
      const params = {
        project: this.retrieveForm.projectName
      }
      app.get('get_train_no', params).then(data => {
        if (data.msg) {
          data.msg.forEach(element => {
            this.trainNoOptions.push({
              value: element.trainNo,
              label: element.trainNo
            })
          })
        }
      })
    },
    getFaultTypes() {
      this.faultTypeOptions = [all_option]
      app.get('get_fault_types').then(response => {
        if (response.code === 0) {
          response.data.forEach(element => {
            this.faultTypeOptions.push({
              value: element.code,
              label: element.name
            })
          })
        }
      })
    },
    query() {
      this.$emit('query', this.retrieveForm)
    },
    checkButton() {
      if (this.single) {
        return !(this.retrieveForm.projectName && this.retrieveForm.trainNo)
      } else {
        return false
      }
    }
  }
}

</script>
<style>
	.el-form-item {
		margin-bottom: 10px;
	}

</style>
