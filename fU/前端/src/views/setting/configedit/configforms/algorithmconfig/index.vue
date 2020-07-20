<template>
  <div>
    <el-form inline>
      <el-form-item>
        <el-button type="primary" icon="el-icon-plus" @click="newAlgorithm">添加算法</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-document-copy" @click="copyAlgorithm">复制算法</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" icon="el-icon-delete" @click="deleteAlgorithm">删除算法</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-collection" @click="groupManage">分组管理</el-button>
      </el-form-item>
    </el-form>
    <el-table
      ref="algorithmTable"
      :data="algorithmSettings"
      border
      :header-cell-style="{padding:'3px',fontSize:'12px'}"
      :cell-style="{padding:'3px'}"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-form label-position="right" inline>
            <el-form-item label="脚本文件:">
              <el-input v-model="scope.row.fileOriginalName" disabled />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="openUploadDialog(scope.$index)">选择</el-button>
              <el-button type="primary" @click="openConfigDialog(scope.$index)">配置输入</el-button>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="序号" type="index" align="center" width="75" />
      <el-table-column label="算法名称" prop="name" align="center">
        <template slot-scope="scope">
          <el-input v-model="scope.row.name" />
        </template>
      </el-table-column>
      <el-table-column label="槽位号" prop="slotId" align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.slotId" @change="setCurrentBoard">
            <el-option
              v-for="item in slotIdOptions"
              :key="item.slotId"
              :label="item.slotId"
              :value="item.slotId"
            />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="算法分组" prop="subsystemId" align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.subsystemId">
            <el-option
              v-for="item in subsystemOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="脚本类型" prop="type" align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.type">
            <el-option
              v-for="item in typeOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="使能" prop="enable" align="center">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.enable" />
        </template>
      </el-table-column>
    </el-table>
    <uploadDialog ref="uploadDialog" @uploadSuccess="uploadSuccess" />
    <groupManagerDialog ref="groupManagerDialog" @saveSuccess="groupSaveSuccess" />
    <configDialog v-if="algorithmSettings.length>0" ref="configDialog" :all-variables="allVariables" :board="board" @configSuccess="configSuccess" />
    <div style="position: absolute; bottom: 10px;right:20px">
      <!-- <el-button style="width: 170px" @click="cancel">取消</el-button> -->
      <el-button style="width: 170px" type="primary" @click="save">保存</el-button>
      <el-button style="width: 170px" type="primary" @click="previousStep">上一步</el-button>
      <el-button style="width: 170px" type="primary" @click="nextStep">下一步</el-button>
    </div>
  </div>
</template>

<script>
import app from '@/common/js/app'
import util from '@/common/js/util'
import uploadDialog from './components/uploadDialog'
import configDialog from './components/configDialog'
import groupManagerDialog from './components/groupManagerDialog'
export default {
  components: {
    uploadDialog,
    configDialog,
    groupManagerDialog
  },
  props: {
    algorithmSettings: {
      type: Array,
      default: null
    },
    boardSettings: {
      type: Array,
      default: null
    },
    allVariables: {
      type: Array,
      default: null
    }
  },
  data() {
    return {
      subsystemOptions: [], // 算法分组可选项
      typeOptions: [{
        id: 0,
        name: 'C'
      }, {
        id: 1,
        name: 'C++'
      }, {
        id: 2,
        name: 'Python'
      }],
      currentIndex: 0,
      board: null // 当前行槽位对应的板卡
    }
  },
  computed: {
    slotIdOptions() {
      return this.boardSettings.filter(function(boardSetting, index) {
        return boardSetting.type.indexOf('PHM') !== -1
      })
    }
  },
  created() {

  },
  mounted() {
    this.selectAllSubsystems()
  },
  methods: {
    openUploadDialog(index) {
      this.currentIndex = index
      // this.setCurrentBoard()
      this.$refs.uploadDialog.openDialog()
    },
    openConfigDialog(index) {
      this.currentIndex = index

      this.setCurrentBoard()
      this.$refs.configDialog.openDialog(this.algorithmSettings[this.currentIndex])
    },
    groupManage() {
      this.$refs.groupManagerDialog.openDialog()
    },
    configSuccess() {
    },
    // 上传文件成功后的回调方法
    uploadSuccess(data) {
      const algorithmElm = this.algorithmSettings[this.currentIndex]
      algorithmElm.fileOriginalName = data.fileOriginalName
      algorithmElm.filename = data.filename
    },
    groupSaveSuccess() {
      this.selectAllSubsystems()
    },
    // 查询所有算法分组
    selectAllSubsystems() {
      app.get('select_all_subsystems').then(data => {
        if (data.code === 0) {
          this.subsystemOptions = data.data
        }
      })
    },
    // 设置当前算法卡槽对应的板卡
    setCurrentBoard() {
      this.boardSettings.forEach(element => {
        if (element.slotId === this.algorithmSettings[this.currentIndex].slotId) {
          this.board = element
        }
      })
    },
    newAlgorithm() {
      if (this.slotIdOptions.length === 0) {
        this.$message({
          type: 'error',
          message: '未配置PHM板卡，不能配置算法'
        })
        return
      }
      const algorithmElm = {
        name: '',
        slotId: this.slotIdOptions[0].slotId,
        fileOriginalName: '',
        subsystemId: '',
        enable: true,
        mvbGroups: [],
        ecnGroups: [],
        adGroups: []
      }
      this.algorithmSettings.push(algorithmElm)
    },
    copyAlgorithm() {
      const selections = this.$refs.algorithmTable.selection
      if (selections.length === 0) {
        this.$message({
          message: '请选择一个要复制的算法',
          type: 'error'
        })
        return
      }
      selections.forEach(element => {
        this.algorithmSettings.push({
          // name: element.name,
          slotId: element.slotId,
          fileOriginalName: '',
          // filename: element.filename,
          subsystemId: element.subsystemId,
          type: element.type, // 脚本类型
          videoUrl: element.videoUrl,
          videoIp: element.videoIp,
          enable: element.enable,
          mvbGroups: element.mvbGroups,
          ecnGroups: element.ecnGroups,
          adGroups: element.adGroups
        })
      })
    },
    deleteAlgorithm() {
      const selections = this.$refs.algorithmTable.selection
      if (selections.length === 0) {
        this.$message({
          message: '至少选择一个要删除的算法',
          type: 'error'
        })
        return
      }
      selections.forEach(element => {
        const index = this.algorithmSettings.indexOf(element)
        this.algorithmSettings.splice(index, 1)
      })
    },
    save() {
      if (!this.verify()) {
        return
      }
      const parm = {
        settingId: localStorage.getItem('settingId'),
        algorithmVOList: this.algorithmSettings
      }
      app.postData('save_algorithm', parm).then(data => {
        if (data.code === 0) {
          if (data.data === null) {
            this.$message({
              message: '保存成功',
              type: 'success'
            })
          } else {
            this.$message({
              message: data.data.errors,
              type: 'error'
            })
          }
        }
      })
    },
    cancel() {
      this.$emit('cancel')
    },
    previousStep() {
      this.$emit('previousStep')
    },
    nextStep() {
      if (!this.verify()) {
        return
      }
      const parm = {
        settingId: localStorage.getItem('settingId'),
        algorithmVOList: this.algorithmSettings
      }
      app.postData('save_algorithm', parm).then(data => {
        if (data.code === 0) {
          this.$emit('nextStep')
        }
      })
    },

    verify() {
      let errors = ''

      this.algorithmSettings.forEach(algorithm => {
        if (algorithm.fileOriginalName === '') {
          if (errors !== '') {
            errors += '</br></br>'
          }
          errors += '算法' + algorithm.name + '脚本文件不能为空'
        }
        if (algorithm.subsystemId === undefined || algorithm.subsystemId === null) {
          if (errors !== '') {
            errors += '</br></br>'
          }
          errors += '算法' + algorithm.name + '算法分组不能为空'
        }
        if (algorithm.type === undefined || algorithm.type === null) {
          if (errors !== '') {
            errors += '</br></br>'
          }
          errors += '算法' + algorithm.name + '脚本类型不能为空'
        }
        let variables = []
        // 是否配置了变量
        let noVariables = true
        // 是否配置了视频流
        let noVideo = true
        // 是否可以配置视频流
        let canVideo = false
        if ((algorithm.adGroups === null || algorithm.adGroups.length === 0) && (algorithm.ecnGroups === null || algorithm.ecnGroups.length === 0) && (algorithm.mvbGroups === null || algorithm.mvbGroups.length === 0)) {
          noVariables = true
        } else {
          variables = util.getAllVariables(this.allVariables)
          noVariables = false
        }
        if (this.getBoardType(algorithm.slotId) === 'PHM_AGX' || this.getBoardType(algorithm.slotId) === 'PHM_TX2') {
          canVideo = true
          if (algorithm.videoUrl === null || algorithm.videoUrl === undefined || algorithm.videoUrl === '') {
            noVideo = true
          } else {
            noVideo = false
          }
        }
        if (canVideo && noVideo && noVariables) {
          if (errors !== '') {
            errors += '</br></br>'
          }
          errors += '算法' + algorithm.name + '输入未配置（视频流和变量至少配置一项）'
        }
        if (!canVideo && noVariables) {
          if (errors !== '') {
            errors += '</br></br>'
          }
          errors += '算法' + algorithm.name + '变量未配置'
        }
        let isInvalid = false
        if (algorithm.adGroups !== null) {
          algorithm.adGroups.forEach(element => {
            for (let index = 0; index < element.variables.length; index++) {
              const variable = element.variables[index]
              if (util.isInvalid(variable, 'AD', variables)) {
                isInvalid = true
                return
              }
            }
          })
        }
        if (algorithm.ecnGroups !== null) {
          algorithm.ecnGroups.forEach(element => {
            for (let index = 0; index < element.variables.length; index++) {
              const variable = element.variables[index]
              if (util.isInvalid(variable, 'ECN', variables)) {
                isInvalid = true
                return
              }
            }
          })
        }
        if (algorithm.mvbGroups !== null) {
          algorithm.mvbGroups.forEach(element => {
            for (let index = 0; index < element.variables.length; index++) {
              const variable = element.variables[index]
              if (util.isInvalid(variable, 'MVB', variables)) {
                isInvalid = true
                return
              }
            }
          })
        }
        if (isInvalid) {
          if (errors !== '') {
            errors += '</br></br>'
          }
          errors += '算法' + algorithm.name + '有失效变量'
        }
      })

      if (errors !== '') {
        this.$message({
          dangerouslyUseHTMLString: true,
          message: errors,
          type: 'error'
        })
        return false
      }
      return true
    },
    getBoardType(slotId) {
      for (let index = 0; index < this.boardSettings.length; index++) {
        const element = this.boardSettings[index]
        if (element.slotId === slotId) {
          return element.type
        }
      }
    }

  }
}
</script>

<style>

</style>
