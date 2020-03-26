<template>
  <el-row id="config" :gutter="5">
    <form ref="fileForm" action="" method="get" style="display:none">
      <input ref="input" type="text" name="settingId" value="">
    </form>
    <el-col :span="8">
      <el-row :style="commonStyle" class="config-list">
        <el-button style="width:97%;margin:5px 5px" icon="el-icon-plus" type="primary" @click="newSetting">新建</el-button>
        <el-table ref="table" :data="settings" style="width: 100%;" border :max-height="476" :row-style="{ cursor: 'pointer', fontSize: '12px' }" :header-cell-style="{padding:'3px',fontSize:'12px'}" :cell-style="{padding:'3px'}" :highlight-current-row="isHighlightCurrentRow" @row-click="rowClick">
          <el-table-column prop="name" label="配置名称" align="center" />
          <el-table-column prop="lastModify" :formatter="formatTime" label="最后一次修改时间" align="center" />
          <el-table-column align="center" label="操作" width="139">
            <template slot-scope="scope">
              <el-button
                :type="scope.row.selected?'success':'primary'"
                icon="el-icon-check"
                :disabled="scope.row.selected"
                @click.stop="handleActive(scope.$index, scope.row)"
              />
              <el-button
                type="primary"
                icon="el-icon-download"
                @click.stop="handleExport(scope.$index, scope.row)"
              />
              <el-button
                v-if="!scope.row.selected"
                type="danger"
                icon="el-icon-delete"
                @click.stop="handleDelete(scope.$index, scope.row)"
              />
            </template>
          </el-table-column>
        </el-table>
      </el-row>
    </el-col>
    <el-col :span="16">
      <div :style="commonStyle" class="config-edit">
        <preview v-if="isPreview" :basic-info="basicInfo" :board-settings="boardSettings" :algorithm-settings="algorithmSettings" :store-config="storeConfig" :clock-config-info="clockConfigInfo" @edit="edit" />
        <configEdit v-else :active="active" :basic-info="basicInfo" :board-settings="boardSettings" :algorithm-settings="algorithmSettings" :store-config="storeConfig" :clock-config-info="clockConfigInfo" @saveSuccess="saveSuccess" @cancel="cancel" @importFile="importFile" />
      </div>
    </el-col>
  </el-row>
</template>

<script>
import configEdit from './configedit/index'
import preview from './preview/index'
import app from '@/common/js/app'
import util from '@/common/js/util'
// import FileSaver from 'file-saver'
export default {
  components: {
    configEdit,
    preview
  },
  data() {
    return {
      commonStyle: {},
      isHighlightCurrentRow: true,
      time: ['年', '月', '日', '时', '分', '秒', '毫秒'],
      canJumpToPreview: false,
      currentCheckedRowId: 0,
      isPreview: false,
      settings: [],
      active: 0,
      basicInfo: {},
      boardSettings: [],
      algorithmSettings: {
        algorithms: [],
        variables: []
      },
      storeConfig: {
        storeVariables: {
          ecnGroups: [],
          mvbGroups: [],
          adGroups: []
        }
      },
      clockConfigInfo: {
        timeOn: false,
        busType: '',
        ecnGroup: null,
        mvbGroup: null
      }
    }
  },
  mounted() {
    this.listAllProtocols()
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
  },
  methods: {
    changeHeight() {
      this.commonStyle = { height: document.body.offsetHeight - 62 + 'px' }
    },
    newSetting() {
      this.canJumpToPreview = false
      this.isHighlightCurrentRow = false
      this.basicInfo = {
        name: '1',
        line: '2',
        train: '3',
        IP: '4'
      }
      this.boardSettings = [{
        type: 'MVB',
        enable: true,
        slotId: 1,
        ip: '2',
        fileOriginalName: '',
        variables: []
      }]
      this.algorithmSettings = {
        algorithms: [{
          name: '123',
          enable: true,
          slotId: 1,
          filename: '',
          mvbGroups: [],
          ecnGroups: [],
          adGroups: []
        }],
        variables: []
      }
      this.storeConfig = {
        rawStrategy: 1,
        rawSpace: 0,
        reaultStrategy: 0,
        resultSpace: 100,
        storeVariables: {
          ecnGroups: [],
          mvbGroups: [],
          adGroups: []
        }
      }
      this.clockConfigInfo = {
        timeOn: false,
        busType: '',
        ecnGroup: null,
        mvbGroup: null
      }
      this.isPreview = false
      this.active = 0
      this.$bus.$emit('changeActive', 0)
    },
    listAllProtocols() {
      app.get('list_all_protocols').then(data => {
        if (data.code === 0) {
          this.settings = data.data
        }
      })
    },
    rowClick(row) {
      this.isHighlightCurrentRow = true
      const param = {
        id: row.id
      }
      this.currentCheckedRowId = row.id
      app.get('get_setting_by_id', param).then(response => {
        if (response.code === 0) {
          this.canJumpToPreview = true
          this.initAllData(response.data)
        }
        this.isPreview = true
      })
    },
    initAllData(data) {
      const info = data.setting
      this.basicInfo = {}
      this.boardSettings = []
      this.algorithmSettings = {}
      this.storeConfig = {}
      this.clockConfigInfo = {}
      this.basicInfo.name = info.name
      this.basicInfo.line = info.line
      this.basicInfo.train = info.train
      this.boardSettings = info.boardSettings
      this.algorithmSettings.algorithms = info.algorithmSettings
      this.storeConfig.rawStrategy = info.rawStrategy
      this.storeConfig.rawSpace = info.rawSpace
      this.storeConfig.resultStrategy = info.resultStrategy
      this.storeConfig.resultSpace = info.resultSpace
      this.storeConfig.storeVariables = info.storeVariables
      this.clockConfigInfo.timeOn = info.timeOn
      this.algorithmSettings.variables = []

      data.variableGroups.forEach(element => {
        const btaElement = {
          type: element.type,
          slotId: element.slotId,
          variables: element.variables
        }
        this.algorithmSettings.variables.push(btaElement)
        info.boardSettings.forEach(board => {
          if (board.type === element.type && board.slotId === element.slotId) {
            board.variables = element.variables
          }
        })
      })
      if (info.timeVariables !== null) {
        this.initClockConfigInfo(info)
      }
      this.integrationAlgorithmVariables()
      this.integrationStoreConfigVariables()
    },
    initClockConfigInfo(info) {
      if (info.timeVariables.ecnGroup) {
        this.clockConfigInfo.busType = 'TRDP'
        this.clockConfigInfo.ecnGroup = info.timeVariables.ecnGroup
        this.initTime(this.clockConfigInfo.ecnGroup.variables)
      } else {
        this.clockConfigInfo.busType = 'MVB'
        this.clockConfigInfo.mvbGroup = info.timeVariables.mvbGroup
        this.initTime(this.clockConfigInfo.mvbGroup.variables)
      }
    },
    initTime(variables) {
      for (let index = 0; index < variables.length; index++) {
        const element = variables[index]
        element.time = this.time[index]
      }
    },
    // 整合算法变量
    integrationAlgorithmVariables() {
      this.algorithmSettings.algorithms.forEach(algorithm => {
        const variables = []
        algorithm.mvbGroups.forEach(element => {
          variables.push({
            type: 'MVB',
            slotId: element.slotId,
            variables: element.variables
          })
        })
        algorithm.ecnGroups.forEach(element => {
          variables.push({
            type: 'ECN',
            slotId: element.slotId,
            variables: element.variables
          })
        })
        algorithm.adGroups.forEach(element => {
          variables.push({
            type: 'AD',
            slotId: element.slotId,
            variables: element.variables
          })
        })
        algorithm.variables = variables
      })
    },
    // 整合存储配置变量
    integrationStoreConfigVariables() {
      const variables = []
      this.storeConfig.storeVariables.mvbGroups.forEach(element => {
        variables.push({
          type: 'MVB',
          slotId: element.slotId,
          variables: element.variables
        })
      })
      this.storeConfig.storeVariables.ecnGroups.forEach(element => {
        variables.push({
          type: 'ECN',
          slotId: element.slotId,
          variables: element.variables
        })
      })
      this.storeConfig.storeVariables.adGroups.forEach(element => {
        variables.push({
          type: 'AD',
          slotId: element.slotId,
          variables: element.variables
        })
      })
      this.storeConfig.variables = variables
    },
    edit(active) {
      this.active = active
      this.isPreview = false
    },
    // 格式化创建时间
    formatTime(row, column) {
      return (row.lastModify = row.lastModify
        ? util.formatDate(new Date(row.lastModify), 'yyyy-MM-dd hh:mm')
        : '')
    },
    handleActive(index, rows) {
      this.$confirm('确认激活该配置？', '提示', {
        type: 'warning'
      }).then(() => {
        const param = {
          settingId: rows.id
        }
        app.post('activate_protocol', param).then(data => {
          if (data.code === 0) {
            this.$message({
              message: '激活成功',
              type: 'success'
            })
            this.listAllProtocols()
          }
        })
      }).catch(() => {})
    },
    handleDelete(index, rows) {
      this.$confirm('确认删除选中记录？', '提示', {
        type: 'warning'
      }).then(() => {
        const param = {
          settingId: rows.id
        }
        app.post('delete_protocol', param).then(data => {
          if (data.code === 0) {
            this.$message({
              message: '删除成功',
              type: 'success'
            })
            this.listAllProtocols()
          }
        })
      }).catch(() => {})
    },
    handleExport(index, rows) {
      const url = 'http://localhost:8080/setting/export'
      const fileForm = this.$refs.fileForm
      const input = this.$refs.input
      fileForm.setAttribute('action', url)
      input.setAttribute('value', rows.id)
      fileForm.submit()
    },
    saveSuccess() {
      this.listAllProtocols()
    },
    cancel() {
      if (this.canJumpToPreview) {
        this.rowClick({
          id: this.currentCheckedRowId
        })
      }
    },
    importFile(fileobj) {
      const formData = new FormData()
      formData.append('file', fileobj)
      app.uploadFile('import_setting', formData).then(response => {
        if (response.code === 0) {
          this.initAllData(response.data)
          this.$message({
            message: '上传成功',
            type: 'success'
          })
        }
      }).catch(response => {
        console.log(response)
      })
    }
  }
}
</script>

<style>
#config .cell .el-button{
  padding: 9px 8px;
  font-size: 10px;
  border-radius: 15px;
}
.config-list{
    margin-left:5px;
    margin-top: 5px;
    border-radius: 2px;
    border: 1px solid #CCCCCC;
    line-height: 1px;
}
.config-edit{
    margin-right:5px;
    margin-top: 5px;
    border-radius: 2px;
    border: 1px solid #CCCCCC;
    line-height: 1px;
}
/* #config .el-table__body tr.current-row>td {
  background-color: #CCFFFF!important;
} */
</style>
