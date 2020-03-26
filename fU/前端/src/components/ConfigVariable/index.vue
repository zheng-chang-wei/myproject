<template>
  <section>
    <el-button type="primary" style="margin-bottom:10px; margin-top:10px" @click="configVariableDialogVisible = true">配置变量
    </el-button>
    <CustomTab :variables="selected" />
    <!--  配置变量对话框-->
    <el-dialog title="配置变量" :visible.sync="configVariableDialogVisible" width="65%">
      <CustomTab :variables="variables" :groups="groups" @tabClick="tabClick" @handleSelectionChange="handleSelectionChange" />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="configVariableDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirm">确 定</el-button>
      </div>
    </el-dialog>
  </section>
</template>
<script>
import CustomTab from '@/components/CustomTab'
export default {
  components: {
    CustomTab
  },
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
      currentTabSlotId: 0,
      activeNameSelf: 0,
      configVariableDialogVisible: false,
      tempSelected: [],
      selected: []
    }
  },
  computed: {

  },
  mounted() {
    this.groups.mvbGroups.forEach(mvbGroup => {
      this.setTableData(mvbGroup)
    })
    this.groups.ecnGroups.forEach(ecnGroups => {
      this.setTableData(ecnGroups)
    })
    this.groups.adGroups.forEach(adGroups => {
      this.setTableData(adGroups)
    })

    for (let index = 0; index < this.variables.length; index++) {
      const variable = this.variables[index]
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
    if (this.selected.length > 0) {
      this.currentTabSlotId = this.selected[0].slotId
    } else {
      this.currentTabSlotId = this.variables[0].slotId
    }
  },
  methods: {
    tabClick(currentTabSlotId) {
      this.currentTabSlotId = currentTabSlotId
    },
    handleSelectionChange(val) {
      for (let index = 0; index < this.variables.length; index++) {
        const variable = this.variables[index]
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
    setTableData(group) {
      for (let i = 0; i < this.variables.length; i++) {
        const variable = this.variables[i]
        if (group.slotId === variable.slotId) {
          this.selected.push({
            variables: group.variables,
            type: variable.type,
            slotId: group.slotId
          })
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
    }
  }
}
</script>
<style>

</style>
