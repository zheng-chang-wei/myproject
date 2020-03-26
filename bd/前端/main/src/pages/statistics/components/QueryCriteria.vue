<template>
  <!--查询-->
  <el-row id="query">
    <el-form ref="retrieveForm" :inline="true" :model="retrieveForm" size="mini">
      <el-form-item v-if="containProject" label="项目名称">
        <el-select v-model="retrieveForm.projectId" placeholder="项目名称">
          <el-option v-for="item in projectOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item v-if="!containProject" label="车门类型">
        <el-select v-model="retrieveForm.doorTypeId" placeholder="车门类型">
          <el-option v-for="item in doorTypeOptions" :key="item.id" :label="item.doorTypeName" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="发生阶段">
        <el-select v-model="retrieveForm.stageId" placeholder="发生阶段">
          <el-option v-for="item in faultStageOptions" :key="item.id" :label="item.stageName" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="运营影响">
        <el-select v-model="retrieveForm.effectId" placeholder="运营影响">
          <el-option v-for="item in effectOptions" :key="item.id" :label="item.effectName" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="故障模式">
        <el-select v-model="retrieveForm.modeId" placeholder="故障模式">
          <el-option v-for="item in faultModeOptions" :key="item.id" :label="item.modeName" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="时间范围">
        <el-date-picker
          v-model="retrieveForm.times"
          type="daterange"
          value-format="yyyy-MM-dd"
          range-separator="-"
          style="width:194px;"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="submit" />
      </el-form-item>
    </el-form>
  </el-row>
</template>
<script>
import app from '@/common/js/app'
export default {
  props: {
    containProject: Boolean
  },
  data() {
    return {
      // 查询功能数据
      retrieveForm: {
        projectId: null,
        doorTypeId: null,
        effectId: null,
        stageId: null,
        modeId: null,
        times: []
      },
      projectOptions: [],
      doorTypeOptions: [],
      effectOptions: [],
      faultStageOptions: [],
      faultModeOptions: []
    }
  },
  mounted() {
    const date = new Date()
    const year = date.getFullYear()
    // 获取当前月份(0-11,0代表1月)
    const month = date.getMonth()
    // 获取当前日(1-31)
    const day = date.getDate()
    this.retrieveForm.times = [year + '-01-01', year + '-' + (month + 1) + '-' + day]
    if (this.containProject) {
      this.getProject()
    } else {
      this.getDoorType()
      this.submit()
    }
    this.getFaultStage()
    this.getEffect()
    this.getFaultMode()
  },
  methods: {
    // 获取项目名称
    getProject() {
      app.get('get_project').then(data => {
        if (data.msg) {
          this.projectOptions = data.msg
          if (this.projectOptions.length > 0) {
            this.retrieveForm.projectId = this.projectOptions[0].id
            this.submit()
          }
        }
      })
    },
    // 获取车门类型
    getDoorType() {
      app.get('list_door_type').then(data => {
        if (data.code === 0) {
          this.doorTypeOptions = data.msg
          this.doorTypeOptions.unshift({
            id: null,
            doorTypeName: '所有'
          })
        }
      })
    },
    // 获取发生阶段
    getFaultStage() {
      app.get('list_fault_stage').then(data => {
        if (data.code === 0) {
          this.faultStageOptions = data.msg
          this.faultStageOptions.unshift({
            id: null,
            stageName: '所有'
          })
        }
      })
    },
    // 获取运营影响
    getEffect() {
      app.get('list_effect').then(data => {
        if (data.code === 0) {
          this.effectOptions = data.msg
          this.effectOptions.unshift({
            id: null,
            effectName: '所有'
          })
        }
      })
    },
    // 获取故障模式
    getFaultMode() {
      app.get('list_fault_mode').then(data => {
        if (data.code === 0) {
          this.faultModeOptions = data.msg
          this.faultModeOptions.unshift({
            id: null,
            modeName: '所有'
          })
        }
      })
    },
    // 开始查询
    submit() {
      if (this.retrieveForm.times[1].indexOf(':') === -1) {
        this.retrieveForm.times[1] = this.retrieveForm.times[1] + ' 23:59:59'
      }
      this.$emit('submit', this.retrieveForm)
    }
  }
}

</script>
<style>
#query {
  padding: 16px 0px 0px;
}

#query .el-input {
  width: 100px;
  font-size: 11px;
}

#query .el-form-item__label {
  font-size: 11px;
}

</style>
