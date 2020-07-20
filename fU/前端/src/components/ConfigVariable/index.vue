<template>
  <section>
    <el-button type="primary" style="margin-bottom:10px; margin-top:10px" @click="openConfigDialog">选择存储变量
    </el-button>
    <!-- <el-button type="primary" style="margin-bottom:10px; margin-top:10px" @click="clearInvalidVariables">清空失效变量
    </el-button> -->
    <CustomTab ref="customTab1" :all-variables="selected" :board="board" :with-select="false" />
    <!--  配置变量对话框-->
    <el-dialog title="配置变量" :visible.sync="configVariableDialogVisible" width="65%">

      <CustomTab ref="customTab2" :all-variables="allVariables" :board="board" @tabClick="tabClick" @handleSelectionChange="handleSelectionChange" />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="configVariableDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirm">确 定</el-button>
      </div>
    </el-dialog>
  </section>
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
    },
    groups: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      currentTabSlotId: 0,
      activeNameSelf: 0,
      configVariableDialogVisible: false,
      tempSelected: [],
      selected: []
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
    this.setSelected()
    this.checkInvalidVariables()
    // 设置数据类型
    this.setDataTypeAlias()
    this.setTempSelected()
    this.setCurrentTabSlotId()
    this.$refs.customTab1.refresh(this.groups)
    // this.$refs.customTab2.refresh(this.groups)
  },
  methods: {
    openConfigDialog() {
      this.configVariableDialogVisible = true
      const vm = this
      setTimeout(function() {
        vm.$refs.customTab2.refresh(vm.groups)
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
      this.configVariableDialogVisible = false
      this.selected = []
      this.groups.mvbGroups = []
      this.groups.ecnGroups = []
      this.groups.adGroups = []
      this.groups.videoIp = this.$refs.customTab2.video.videoIp
      this.groups.videoUrl = this.$refs.customTab2.video.videoUrl
      this.$refs.customTab1.reSetVideo()

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
    },
    checkInvalidVariables() {
      const variables = util.getAllVariables(this.allVariables)

      this.selected.forEach(selectedVariable => {
        selectedVariable.variables.forEach(element => {
          element['isInvalid'] = util.isInvalid(element, selectedVariable.type, variables)
        })
      })
    },

    clearInvalidVariables() {
      this.selected.forEach(selectedVariable => {
        let i = selectedVariable.variables.length
        while (i--) {
          if (selectedVariable.variables[i].isInvalid) {
            selectedVariable.variables.splice(i, 1)
          }
        }
      })
    }
  }
}
</script>
<style>

</style>
