<template>
  <el-row id="config" :gutter="20">
    <form ref="fileForm" action="" method="get" style="display:none">
      <input ref="input" type="text" name="settingId" value="">
    </form>
    <el-col :span="7">
      <el-row class="config-list">
        <el-button style="width:97%;margin:5px 5px" icon="el-icon-plus" type="primary" @click="newSetting">新建</el-button>
        <el-table
          ref="table"
          :data="settingTableDatas"
          :row-style="{ cursor: 'pointer', fontSize: '12px' }"
          :header-cell-style="{padding:'3px',fontSize:'12px'}"
          :cell-style="{padding:'3px'}"
          :highlight-current-row="isHighlightCurrentRow"
          :height="tableHeight"
          @row-click="rowClick"
        >
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left">
                <el-form-item label="最后一次修改时间:">
                  <span>{{ props.row.lastModify }}</span>
                </el-form-item>
                <el-form-item label="操作用户:">
                  <span>{{ props.row.userName }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="配置名称" align="center" />
          <el-table-column align="center" label="操作" width="150">
            <template slot-scope="scope">
              <el-tooltip class="item" effect="dark" content="激活" placement="top">
                <el-button
                  :type="scope.row.selected?'success':'primary'"
                  icon="el-icon-check"
                  :disabled="scope.row.selected"
                  @click.stop="handleActive(scope.$index, scope.row)"
                />
              </el-tooltip>
              <el-tooltip class="item" effect="dark" content="导出" placement="top">
                <el-button
                  type="primary"
                  icon="el-icon-download"
                  @click.stop="handleExport(scope.$index, scope.row)"
                />
              </el-tooltip>
              <el-tooltip class="item" effect="dark" content="删除" placement="top">
                <el-button
                  v-if="!scope.row.selected"
                  type="danger"
                  icon="el-icon-delete"
                  @click.stop="handleDelete(scope.$index, scope.row)"
                />
              </el-tooltip>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
    </el-col>
    <el-col :span="17">
      <el-row v-if="isShowChassis" class="center" style="margin-top:5px">
        <chassis ref="chassis" @onclick="cardChange" />
      </el-row>
      <div class="config-edit">
        <configEdit ref="configEdit" :basic-info="basicInfo" :board-settings="boardSettings" :algorithm-settings="algorithmSettings" :output-settings="outputSettings" :store-config="storeConfig" :clock-config-info="clockConfigInfo" @saveSuccess="saveSuccess" @isShowChassis="setShowChassis" @importFile="importFile" />
      </div>
    </el-col>
  </el-row>
</template>

<script>
import configEdit from './configedit/index'
import chassis from '../monitor/board/diagram/index'

import app from '@/common/js/app'
export default {
  components: {
    configEdit,
    chassis
  },
  data() {
    return {
      tableHeight: 0,
      isHighlightCurrentRow: true,
      currentCheckedRowId: 0,
      settingTableDatas: [],
      basicInfo: {
        name: '',
        line: '',
        train: ''
      },
      boardSettings: [],
      algorithmSettings: [],
      storeConfig: {
        rawStrategy: 0,
        resultStrategy: 1,
        rawSpace: 60,
        ecnGroups: [],
        mvbGroups: [],
        adGroups: []
      },
      clockConfigInfo: {
        timeOn: false,
        busType: '',
        ecnGroup: null,
        mvbGroup: null
      },
      outputSettings: {
        commonSegments: [],
        algorithms: []
      },
      loading: null,
      isShowChassis: false
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
  },
  mounted() {
    localStorage.setItem('settingId', null)
    this.listAllProtocols()
    this.$bus.$on('launch', (event) => {
      this.$refs.chassis.listBoards()
    })
  },
  destroyed() {
    this.$bus.$off('launch')
    window.removeEventListener('resize', this.changeHeight)
  },
  methods: {
    changeHeight() {
      this.tableHeight = document.body.offsetHeight - 109 + 'px'
    },
    cardChange(board) {
      this.$bus.$emit('cardChange', {
        slotId: board.slotID,
        type: board.boardType
      })
    },
    newSetting() {
      this.isShowChassis = false
      localStorage.setItem('settingId', null)
      this.isHighlightCurrentRow = false
      this.basicInfo = {
        name: '',
        line: '',
        train: ''
      }
      this.boardSettings = []
      this.algorithmSettings = []
      this.storeConfig = {
        rawStrategy: 0,
        rawSpace: 60,
        resultStrategy: 1,
        resultSpace: 40,
        ecnGroups: [],
        mvbGroups: [],
        adGroups: []
      }
      this.clockConfigInfo = {
        timeOn: false,
        busType: '',
        ecnGroup: null,
        mvbGroup: null
      }
      this.outputSettings = {
        commonSegments: [],
        algorithms: []
      }
      this.$refs.configEdit.setActive(0)
    },
    listAllProtocols() {
      app.get('list_all_protocols').then(data => {
        if (data.code === 0) {
          this.settingTableDatas = data.data
        }
      })
    },
    rowClick(row) {
      this.$refs.configEdit.setActive(0)
      this.isShowChassis = false
      this.isHighlightCurrentRow = true
      const param = {
        id: row.id
      }
      localStorage.setItem('settingId', row.id)
      this.currentCheckedRowId = row.id
      app.get('get_setting_by_id', param).then(response => {
        if (response.code === 0) {
          this.initAllData(response.data)
        }
      })
      app.get('result_output_setting', param).then(response => {
        if (response.code === 0) {
          this.outputSettings = response.data
        }
      })
    },
    initAllData(data) {
      const info = data.setting
      this.basicInfo = {}
      this.boardSettings = []
      this.algorithmSettings = []
      this.storeConfig = {}
      this.clockConfigInfo = {}
      this.basicInfo.name = info.name
      this.basicInfo.line = info.line
      this.basicInfo.train = info.train
      this.boardSettings = info.boardSettings
      this.algorithmSettings = info.algorithmSettings
      this.storeConfig = info.storeSetting

      this.reSetBoardSettingsIp()
      if (this.storeConfig === null) {
        this.storeConfig = {
          rawStrategy: 0,
          rawSpace: 60,
          resultStrategy: 1,
          resultSpace: 40,
          ecnGroups: [],
          mvbGroups: [],
          adGroups: []
        }
      }
      this.clockConfigInfo = info.timeSetting
      if (this.clockConfigInfo === null) {
        this.clockConfigInfo = {
          type: 'local',
          busType: '',
          ecnGroup: null,
          mvbGroup: null
        }
      }
    },
    reSetBoardSettingsIp() {
      this.boardSettings.forEach(boardSetting => {
        if (boardSetting.ips && boardSetting.ips !== null && boardSetting.ips.length !== 0) {
          for (let index = 0; index < boardSetting.ips.length; index++) {
            boardSetting['ip' + (index + 1)] = boardSetting.ips[index]
          }
        }
      })
    },
    handleActive(index, rows) {
      this.$confirm('确认激活该配置？', '提示', {
        type: 'warning'
      }).then(() => {
        const param = {
          settingId: rows.id
        }
        this.initLoading()
        app.post('activate_protocol', param).then(response => {
          if (response.code === 0) {
            const data = response.data
            switch (data.result) {
              case 0:
                this.activationSuccess()
                break
              case 1:
                this.verifyFail(data)
                break
              case 2:
                this.startFail(data)
                break

              default:
                break
            }
            this.listAllProtocols()
            this.closeLoading()
          }
        }).catch(() => {
          this.listAllProtocols()
          this.closeLoading()
        })
      }).catch(() => {})
    },
    activationSuccess() {
      this.$message({
        message: '激活成功',
        type: 'success'
      })
      this.$bus.$emit('launch', 'launch')
    },
    verifyFail(data) {
      const errors = data.validateResult.errors
      const warnings = data.validateResult.warnings
      let msg = ''
      if (errors !== null) {
        errors.forEach(element => {
          if (msg !== '') {
            msg += '</br></br>'
          }
          msg += element
        })
      }
      if (warnings !== null) {
        warnings.forEach(element => {
          if (msg !== '') {
            msg += '</br></br>'
          }
          msg += element
        })
      }

      this.$message({
        dangerouslyUseHTMLString: true,
        message: msg,
        type: 'error'
      })
    },
    startFail(data) {
      this.$message({
        dangerouslyUseHTMLString: true,
        message: '启动失败',
        type: 'error'
      })
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
      const url = 'http://172.10.90.1:8080/setting/export'
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
      // if (this.canJumpToPreview) {
      //   this.rowClick({
      //     id: this.currentCheckedRowId
      //   })
      // }
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
    },
    initLoading() {
      this.loading = this.$loading({
        lock: true,
        text: '配置激活中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
    },
    closeLoading() {
      if (this.loading !== null) {
        this.loading.close()
        this.loading = null
      }
    },
    setShowChassis(value) {
      this.isShowChassis = value
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
/* #config .el-button{
  padding: 7px 15px;
  font-size: 12px;
  line-height: 1;
  border-radius: 3px;
} */
#config .el-input__inner{
  font-size: 13px;
  height: 32px;
  line-height: 32px;
}
#config .el-table__expanded-cell[class*=cell] {
    padding: 10px 10px;
    background: #f6f6f6;
}
#config .el-form-item {
    margin-bottom: 0px;
}
#config .el-dialog__body {
    padding: 10px 10px;
    color: #606266;
    font-size: 14px;
    word-break: break-all;
}
#config label {
    font-weight: 500;
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
    margin-bottom: 5px;
    border-radius: 2px;
    border: 1px solid #CCCCCC;
    line-height: 1px;
}
#config .el-table__body tr.current-row>td {
  background-color: #CCFFFF!important;
}

</style>
