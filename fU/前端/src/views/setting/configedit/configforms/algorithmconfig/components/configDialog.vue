<template>
  <!--  配置变量对话框-->
  <el-dialog title="配置变量" :visible.sync="configVariableDialogVisible" width="65%">
    <CustomTab ref="customTabRef" :all-variables="allVariables" :groups="groups" :board="board" @tabClick="tabClick" @handleSelectionChange="handleSelectionChange" />
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="configVariableDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
import CustomTab from '@/components/CustomTab'
import util from '@/common/js/util'
const types = ['CHAR8', 'BOOLEAN', 'UINT8', 'INT8', 'UINT16', 'INT16', 'UINT32', 'INT32', 'FLOAT', 'DOUBLE', 'LONG', 'BITS']
export default {
  components: {
    CustomTab
  },
  props: {
    board: {
      type: Object,
      default: null
    },
    allVariables: {
      type: Array,
      default: null
    }
  },
  data() {
    return {
      currentTabSlotId: 0,
      configVariableDialogVisible: false,
      tempSelected: [],
      selected: [],
      groups: {}
    }
  },
  computed: {
    mvbVariables() {
      return this.allVariables.filter(function(variable, index) {
        return variable.type.indexOf('MVB') !== -1
      })
    },
    ecnVariables() {
      return this.allVariables.filter(function(variable, index) {
        return variable.type.indexOf('ECN') !== -1
      })
    },
    adVariables() {
      return this.allVariables.filter(function(variable, index) {
        return variable.type.indexOf('AD') !== -1
      })
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    openDialog(groups) {
      this.groups = groups
      this.configVariableDialogVisible = true
      this.setSelected()
      // 设置数据类型
      this.setDataTypeAlias()
      this.setTempSelected()
      this.setCurrentTabSlotId()
      const vm = this
      setTimeout(function() {
        vm.$refs.customTabRef.refresh(vm.groups)
      }, '10')
    },
    setSelected() {
      util.isEmpty()
      if (!util.isEmpty(this.groups.mvbGroups)) {
        this.groups.mvbGroups.forEach(mvbGroup => {
          this.setTableData(mvbGroup)
        })
      }
      if (!util.isEmpty(this.groups.ecnGroups)) {
        this.groups.ecnGroups.forEach(ecnGroups => {
          this.setTableData(ecnGroups)
        })
      }
      if (!util.isEmpty(this.groups.adGroups)) {
        this.groups.adGroups.forEach(adGroups => {
          this.setTableData(adGroups)
        })
      }
    },
    setTableData(group) {
      for (let i = 0; i < this.allVariables.length; i++) {
        const variable = this.allVariables[i]
        if (group.slotId === variable.slotId) {
          this.selected.push({
            variables: group.variables,
            type: variable.type,
            slotId: group.slotId
          })
        }
      }
    },
    setDataTypeAlias() {
      this.selected.forEach(element => {
        element.variables.forEach(variable => {
          variable['dataTypeAlias'] = types[parseInt(variable.dataType)]
        })
      })
    },
    setTempSelected() {
      for (let index = 0; index < this.allVariables.length; index++) {
        const variable = this.allVariables[index]
        this.selected.forEach(element => {
          if (element.slotId === variable.slotId) {
            this.$set(this.tempSelected, index, {
              variables: element.variables,
              type: element.type,
              slotId: element.slotId
            })
          }
        })
      }
    },
    setCurrentTabSlotId() {
      if (this.selected.length > 0) {
        this.currentTabSlotId = this.selected[0].slotId
      } else {
        if (this.mvbVariables.length !== 0) {
          this.currentTabSlotId = this.mvbVariables[0].slotId
        } else if (this.ecnVariables.length !== 0) {
          this.currentTabSlotId = this.ecnVariables[0].slotId
        } else if (this.adVariables.length !== 0) {
          this.currentTabSlotId = this.adVariables[0].slotId
        }
      }
    },
    tabClick(currentTabSlotId) {
      this.currentTabSlotId = currentTabSlotId
    },
    handleSelectionChange(val) {
      for (let index = 0; index < this.allVariables.length; index++) {
        const variable = this.allVariables[index]
        if (variable.slotId === this.currentTabSlotId) {
          if (val.length !== 0) {
            this.$set(this.tempSelected, index, {
              variables: val,
              type: variable.type,
              slotId: variable.slotId
            })
          } else {
            this.$set(this.tempSelected, index, null)
          }
        }
      }
    },
    // 点击配置变量对话框确认
    confirm() {
      this.selected = []
      this.groups.mvbGroups = []
      this.groups.ecnGroups = []
      this.groups.adGroups = []
      this.groups.videoUrl = this.$refs.customTabRef.video.videoUrl

      this.tempSelected.forEach(element => {
        if (element === null) {
          return
        }
        this.selected.push(element)
        if (element.type.indexOf('MVB') !== -1) {
          this.groups.mvbGroups.push({
            slotId: element.slotId,
            variables: element.variables
          })
        } else if (element.type.indexOf('ECN') !== -1) {
          this.groups.ecnGroups.push({
            slotId: element.slotId,
            variables: element.variables
          })
        } else if (element.type.indexOf('AD') !== -1) {
          this.groups.adGroups.push({
            slotId: element.slotId,
            variables: element.variables
          })
        }
      })
      if (this.groups.videoUrl !== '' && this.selected.length > 0) {
        this.$message({
          type: 'error',
          message: '配置输入冲突'
        })
        return
      }
      this.configVariableDialogVisible = false
      this.$emit('configSuccess')
    }
  }
}
</script>
<style>

</style>
