<template>
  <el-tabs v-model="activeNameSelf" @tab-click="tabClick">
    <template v-for="(variable,i) in mvbVariables">
      <el-tab-pane :key="i" :label="variable.type+'_'+variable.slotId" :name="i.toString()">
        <el-table
          :ref="'table'+i"
          :data="variable.variables"
          tooltip-effect="dark"
          style="width: 100%"
          border
          @selection-change="handleSelectionChange"
        >
          <el-table-column v-if="isSelect" type="selection" width="40" align="center" />
          <el-table-column prop="signalName" label="变量名" align="center" />
          <el-table-column prop="name" label="变量别名" align="center" />
          <el-table-column prop="device" label="设备号" align="center" />
          <el-table-column prop="deviceName" label="设备名" align="center" />
          <el-table-column prop="carriage" label="车厢名" align="center" />
          <el-table-column prop="system" label="系统" align="center" />
          <el-table-column prop="port" label="端口" align="center" />
          <el-table-column prop="fcode" label="fcode" align="center" />
          <el-table-column prop="dataType" label="数据类型" align="center" />
        </el-table>
      </el-tab-pane>
    </template>
    <template v-for="(variable,i) in ecnVariables">
      <el-tab-pane :key="i+mvbVariables.length" :label="variable.type+'_'+variable.slotId" :name="(i+mvbVariables.length).toString()">
        <el-table
          :ref="'table'+(i+mvbVariables.length)"
          :data="variable.variables"
          tooltip-effect="dark"
          style="width: 100%"
          border
          @selection-change="handleSelectionChange"
        >
          <el-table-column v-if="isSelect" type="selection" width="40" align="center" />
          <el-table-column prop="signalName" label="变量名" align="center" />
          <el-table-column prop="name" label="变量别名" align="center" />
          <el-table-column prop="sourceIp" label="源IP" align="center" />
          <el-table-column prop="comId" label="comId" align="center" />
          <el-table-column prop="dataType" label="数据类型" align="center" />
        </el-table>
      </el-tab-pane>
    </template>
    <template v-for="(variable,i) in adVariables">
      <el-tab-pane :key="i+mvbVariables.length+ecnVariables.length" :label="variable.type+'_'+variable.slotId" :name="(i+mvbVariables.length+ecnVariables.length).toString()">
        <el-table
          :ref="'table'+(i+mvbVariables.length+ecnVariables.length)"
          :data="variable.variables"
          tooltip-effect="dark"
          style="width: 100%"
          border
          @selection-change="handleSelectionChange"
        >
          <el-table-column v-if="isSelect" type="selection" width="40" align="center" />
          <el-table-column prop="name" label="变量名" align="center" />
          <el-table-column prop="chnId" label="通道号" align="center" />
          <el-table-column prop="sampleRate" label="采样率" align="center" />
        </el-table>
      </el-tab-pane>
    </template>
  </el-tabs>
</template>
<script>

export default {
  props: {
    variables: {
      type: Array,
      default: null
    },
    groups: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      activeNameSelf: 0,
      currentTabSlotId: 0
    }
  },
  computed: {
    mvbVariables() {
      return this.variables.filter(function(variable, index) {
        return variable.type.indexOf('MVB') !== -1
      })
    },
    ecnVariables() {
      return this.variables.filter(function(variable, index) {
        return variable.type.indexOf('ECN') !== -1
      })
    },
    adVariables() {
      return this.variables.filter(function(variable, index) {
        return variable.type.indexOf('AD') !== -1
      })
    },
    isSelect() {
      return this.groups !== null
    }
  },
  mounted() {
    if (this.variables.length > 0) {
      this.currentTabSlotId = this.variables[0].slotId
    }
    this.setTableSelected()
  },
  methods: {
    tabClick(tab) {
      this.currentTabSlotId = parseInt(tab.label.split('_')[1])
      this.$emit('tabClick', this.currentTabSlotId)
      this.setTableSelected()
    },
    handleSelectionChange(val) {
      this.$emit('handleSelectionChange', val)
    },
    setTableSelected() {
      if (this.groups !== null) {
        this.groups.mvbGroups.forEach(mvbGroup => {
          this.setConfigVariableDialogTableSelected(mvbGroup)
        })
        this.groups.ecnGroups.forEach(ecnGroups => {
          this.setConfigVariableDialogTableSelected(ecnGroups)
        })
        this.groups.adGroups.forEach(adGroups => {
          this.setConfigVariableDialogTableSelected(adGroups)
        })
      }
    },
    // 设置配置变量对话框中table选中行
    setConfigVariableDialogTableSelected(group) {
      for (let i = 0; i < this.variables.length; i++) {
        const variable = this.variables[i]
        if (group.slotId === variable.slotId && this.currentTabSlotId === group.slotId) {
          group.variables.forEach(variable1 => {
            variable.variables.forEach(variable2 => {
              if (this.isEquals(variable1, variable2)) {
                this.$refs[`table${i}`][0].toggleRowSelection(variable2, true)
              }
            })
          })
        }
      }
    },
    isEquals(variable1, variable2) {
      if (variable1.name === variable2.name && variable1.signalName === variable2.signalName && variable1.device === variable2.device && variable1.deviceName === variable2.deviceName) {
        return true
      }
      return false
    }
  }
}
</script>
<style>

</style>
