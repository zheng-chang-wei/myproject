<template>
  <el-tabs v-model="activeName" @tab-click="tabClick">
    <template v-for="(variable,i) in mvbVariables">
      <el-tab-pane :key="i" :label="variable.type+'_'+variable.slotId" :name="i+''">
        <el-table
          :ref="'tableMVB'+i"
          :data="variable.variables"
          :row-style="{ fontSize: '11px'}"
          :header-cell-style="{padding:'4px',fontSize:'12px'}"
          :cell-style="{padding:'3px'}"
          border
          @selection-change="handleSelectionChange"
        >
          <el-table-column v-if="withSelect" type="selection" width="55" align="center" />
          <el-table-column prop="signalName" label="变量名" align="center">
            <template slot-scope="scope">
              <el-tag v-if="!withSelect&&scope.row.isInvalid" size="mini" type="danger">失效</el-tag>
              <span>{{ scope.row.signalName }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="变量别名" align="center" />
          <el-table-column prop="device" label="设备号" align="center" />
          <el-table-column prop="deviceName" label="设备名" align="center" />
          <el-table-column prop="carriage" label="车厢名" align="center" />
          <el-table-column prop="system" label="系统" align="center" />
          <el-table-column prop="port" label="端口" align="center" />
          <el-table-column prop="fcode" label="fcode" align="center" />
          <el-table-column prop="dataTypeAlias" label="数据类型" align="center" />
        </el-table>
      </el-tab-pane>
    </template>
    <template v-for="(variable,i) in ecnVariables">
      <el-tab-pane :key="i+mvbVariables.length" :label="variable.type+'_'+variable.slotId" :name="(i+mvbVariables.length).toString()">
        <el-table
          :ref="'tableECN'+i"
          :data="variable.variables"
          :row-style="{ fontSize: '11px'}"
          :header-cell-style="{padding:'4px',fontSize:'12px'}"
          :cell-style="{padding:'3px'}"
          border
          @selection-change="handleSelectionChange"
        >
          <el-table-column v-if="withSelect" type="selection" width="55" align="center" />
          <el-table-column prop="signalName" label="变量名" align="center">
            <template slot-scope="scope">
              <el-tag v-if="!withSelect&&scope.row.isInvalid" size="mini" type="danger">失效</el-tag>
              <span>{{ scope.row.signalName }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="变量别名" align="center" />
          <el-table-column prop="sourceIp" label="源IP" align="center" />
          <el-table-column prop="comId" label="comId" align="center" />
          <el-table-column prop="dataTypeAlias" label="数据类型" align="center" />
        </el-table>
      </el-tab-pane>
    </template>
    <template v-for="(variable,i) in adVariables">
      <el-tab-pane :key="i+mvbVariables.length+ecnVariables.length" :label="variable.type+'_'+variable.slotId" :name="(i+mvbVariables.length+ecnVariables.length).toString()">
        <el-table
          :ref="'tableAD'+i"
          :data="variable.variables"
          :row-style="{ fontSize: '11px'}"
          :header-cell-style="{padding:'4px',fontSize:'12px'}"
          :cell-style="{padding:'3px'}"
          border
          @selection-change="handleSelectionChange"
        >
          <el-table-column v-if="withSelect" type="selection" width="55" align="center" />
          <el-table-column prop="name" label="变量名" align="center">
            <template slot-scope="scope">
              <el-tag v-if="!withSelect&&scope.row.isInvalid" size="mini" type="danger">失效</el-tag>
              <span>{{ scope.row.name }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="chnId" label="通道号" align="center" />
          <el-table-column prop="sampleRate" label="采样率" align="center" />
        </el-table>
      </el-tab-pane>
    </template>
    <template v-if="board!==null&&(board.type==='PHM_AGX'||board.type==='PHM_TX2')">
      <el-tab-pane label="视频流" name="video">
        <el-form size="mini" style="margin-top:20px" label-position="right" label-width="80px">
          <el-form-item label="视频URL:" style="margin-top:20px">
            <span v-if="!withSelect">{{ video.videoUrl }}</span>
            <el-input v-else v-model="video.videoUrl" style="width:50%" />
          </el-form-item>
          <el-form-item v-if="withSelect" style="margin-top:20px">
            <span>形式如：“rtsp://{ ip } : { port }/{ id }或rtsp://{ username }:{ password }@{ ip }:{ port }/{ xxx }/{ xxx }/{ xxx}”</span>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </template>

  </el-tabs>
</template>
<script>
import util from '@/common/js/util'
export default {
  props: {
    allVariables: {
      type: Array,
      default: null
    },
    // groups: {
    //   type: Object,
    //   default: null
    // },
    board: {
      type: Object,
      default: null
    },
    withSelect: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      activeName: '0',
      currentTabSlotId: 0,
      video: {
        videoUrl: ''
      },
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
    refresh(groups) {
      this.groups = groups
      this.reSetVideo()
      if (this.withSelect) {
        if (this.mvbVariables.length !== 0) {
          this.currentTabSlotId = this.mvbVariables[0].slotId
        } else if (this.ecnVariables.length !== 0) {
          this.currentTabSlotId = this.ecnVariables[0].slotId
        } else if (this.adVariables.length !== 0) {
          this.currentTabSlotId = this.adVariables[0].slotId
        }
        this.setTableSelected()
        if (util.isEmpty(this.mvbVariables) && util.isEmpty(this.ecnVariables) && util.isEmpty(this.adVariables)) {
          this.activeName = 'video'
        } else {
          this.activeName = '0'
        }
      }
    },
    reSetVideo() {
      if (this.groups.videoUrl !== undefined) {
        this.video.videoUrl = this.groups.videoUrl
      }
    },
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
        if (!util.isEmpty(this.groups.mvbGroups)) {
          this.groups.mvbGroups.forEach(mvbGroup => {
            this.setConfigVariableDialogTableSelected(mvbGroup, this.mvbVariables, 'MVB')
          })
        } else {
          this.clearSelection(this.mvbVariables, 'MVB')
        }
        if (!util.isEmpty(this.groups.ecnGroups)) {
          this.groups.ecnGroups.forEach(ecnGroups => {
            this.setConfigVariableDialogTableSelected(ecnGroups, this.ecnVariables, 'ECN')
          })
        } else {
          this.clearSelection(this.ecnVariables, 'ECN')
        }
        if (!util.isEmpty(this.groups.adGroups)) {
          this.groups.adGroups.forEach(adGroups => {
            this.setConfigVariableDialogTableSelected(adGroups, this.adVariables, 'AD')
          })
        } else {
          this.clearSelection(this.adVariables, 'AD')
        }
      }
    },
    clearSelection(variables, type) {
      for (let index = 0; index < variables.length; index++) {
        this.$refs[`table` + type + index][0].clearSelection()
      }
    },
    // 设置配置变量对话框中table选中行
    setConfigVariableDialogTableSelected(group, variables, type) {
      let i = 0
      this.$refs[`table` + type + i][0].clearSelection()
      variables.forEach(element => {
        const variable = variables[i]
        if (group.slotId === variable.slotId && this.currentTabSlotId === group.slotId) {
          group.variables.forEach(variable1 => {
            variable.variables.forEach(variable2 => {
              if (util.isEquals(variable1, variable2, type)) {
                this.$refs[`table` + type + i][0].toggleRowSelection(variable2, true)
              }
            })
          })
        }
        i++
      })
    }
  }
}
</script>
<style>

</style>
