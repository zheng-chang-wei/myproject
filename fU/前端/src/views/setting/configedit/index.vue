<template>
  <div id="configEdit">
    <el-steps :active="myActive" finish-status="success" align-center>
      <el-step title="基本配置" />
      <el-step title="板卡配置" />
      <el-step title="算法配置" />
      <el-step title="输出配置" />
      <el-step title="存储配置" />
      <el-step title="时间来源配置" />
    </el-steps>
    <basicConfig v-if="myActive===0" class="common-config" :style="commonStyle" :basic-info="basicInfo" @nextStep="nextStep" @cancel="cancel" @importFile="importFile" @basicSaveSuccess="basicSaveSuccess" />
    <boardConfig
      v-if="myActive===1"
      class="common-config"
      :style="boardStyle"
      :board-settings="boardSettings"
      @previousStep="previousStep"
      @toAlgorithmConfig="toAlgorithmConfig"
      @uploadSuccess="uploadSuccess"
      @cancel="cancel"
    />
    <algorithmConfig
      v-if="myActive===2"
      class="common-config"
      :style="commonStyle"
      :algorithm-settings="algorithmSettings"
      :board-settings="boardSettings"
      :all-variables="allVariables"
      @previousStep="previousStep"
      @nextStep="nextStep"
      @cancel="cancel"
    />
    <outputconfig
      v-if="myActive===3"
      class="common-config"
      :style="commonStyle"
      :clock-config-info="clockConfigInfo"
      :algorithm-settings="algorithmSettings"
      :output-settings="outputSettings"
      :mvb-variables="mvbVariables"
      :ecn-variables="ecnVariables"
      :ad-variables="adVariables"
      @previousStep="previousStep"
      @nextStep="nextStep"
      @cancel="cancel"
    />
    <storeConfig
      v-if="myActive===4"
      class="common-config"
      :style="commonStyle"
      :store-config="storeConfig"
      :all-variables="allVariables"
      @previousStep="previousStep"
      @nextStep="nextStep"
      @cancel="cancel"
    />
    <clockConfig
      v-if="myActive===5"
      class="common-config"
      :style="commonStyle"
      :clock-config-info="clockConfigInfo"
      :variables="allVariables"
      @previousStep="previousStep"
      @cancel="cancel"
    />
  </div>
</template>

<script>
import basicConfig from './configforms/basicconfig'
import boardConfig from './configforms/boardconfig/index'
import algorithmConfig from './configforms/algorithmconfig/index'
import outputconfig from './configforms/outputconfig'
import storeConfig from './configforms/storeconfig'
import clockConfig from './configforms/clockconfig'
import app from '@/common/js/app'
const types = ['CHAR8', 'BOOLEAN', 'UINT8', 'INT8', 'UINT16', 'INT16', 'UINT32', 'INT32', 'FLOAT', 'DOUBLE', 'LONG', 'BITS']
export default {
  components: {
    basicConfig,
    boardConfig,
    algorithmConfig,
    outputconfig,
    storeConfig,
    clockConfig
  },
  props: {
    basicInfo: {
      type: Object,
      default: null
    },
    boardSettings: {
      type: Array,
      default: null
    },
    algorithmSettings: {
      type: Array,
      default: null
    },
    outputSettings: {
      type: Object,
      default: null
    },
    storeConfig: {
      type: Object,
      default: null
    },
    clockConfigInfo: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      myActive: 0,
      commonStyle: {},
      boardStyle: {},
      allVariables: [],
      mvbVariables: [],
      ecnVariables: [],
      adVariables: [],
      variablesCount: 0,
      currentCount: 0
    }
  },
  mounted() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeHeight)
  },
  methods: {
    setActive(active) {
      this.myActive = active
    },
    changeHeight() {
      this.commonStyle = { height: document.body.offsetHeight - 130 + 'px' }
      this.boardStyle = { height: document.body.offsetHeight - 355 + 'px' }
    },
    cancel() {
      this.$emit('cancel')
    },
    importFile(result) {
      this.$emit('importFile', result)
    },
    // 数据流文件上传成功
    uploadSuccess(type) {
      switch (type) {
        case 'ECN':
          this.algorithmSettings.forEach(element => {
            element.ecnGroups = []
          })
          this.storeConfig.ecnGroups = []
          this.clockConfigInfo.ecnGroup = []
          this.outputSettings.commonSegments = []
          break
        case 'MVB':
          this.algorithmSettings.forEach(element => {
            element.mvbGroups = []
          })
          this.storeConfig.mvbGroups = []
          this.clockConfigInfo.mvbGroup = []
          break

        default:
          break
      }
    },
    // 上一步
    previousStep() {
      this.myActive--
      this.$emit('isShowChassis', this.myActive === 1)
    },
    // 下一步
    nextStep() {
      this.myActive++
      this.$emit('isShowChassis', this.myActive === 1)
    },

    toAlgorithmConfig() {
      this.$emit('isShowChassis', false)
      this.allVariables = []
      this.mvbVariables = []
      this.ecnVariables = []
      this.adVariables = []
      const settingId = localStorage.getItem('settingId')
      this.variablesCount = 0
      this.currentCount = 0
      this.boardSettings.forEach(boardSetting => {
        if (boardSetting.type === 'MVB' || boardSetting.type === 'ECN' || boardSetting.type.indexOf('AD') !== -1) {
          this.variablesCount++
        }
      })
      if (this.variablesCount === 0) {
        this.myActive++
        return
      }
      for (let index = 0; index < this.boardSettings.length; index++) {
        const boardSetting = this.boardSettings[index]
        let parm = null
        if (boardSetting.type === 'MVB' || boardSetting.type === 'ECN') {
          parm = {
            settingId: settingId,
            type: boardSetting.type
          }
        } else if (boardSetting.type.indexOf('AD') !== -1) {
          parm = {
            settingId: settingId,
            type: 'AD'
          }
        }
        if (parm === null) {
          continue
        }
        app.get('list_variables', parm).then(response => {
          if (response.code === 0) {
            const element = response.data[0]
            element.variables.forEach(variable => {
              variable['dataTypeAlias'] = types[parseInt(variable.dataType)]
            })
            const btaElement = {
              type: element.type,
              slotId: element.slotId,
              variables: element.variables
            }
            if (boardSetting.type === 'MVB') {
              this.mvbVariables.push(btaElement)
            } else if (boardSetting.type === 'ECN') {
              this.ecnVariables.push(btaElement)
            } else if (boardSetting.type.indexOf('AD')) {
              this.adVariables.push(btaElement)
            }
            this.allVariables.push(btaElement)
            this.currentCount++
            if (this.currentCount === this.variablesCount) {
              this.myActive++
            }
          }
        })
      }
    },
    basicSaveSuccess() {
      this.$emit('saveSuccess')
    }
  }
}
</script>

<style>
.el-steps {
  margin-top: 10px;
}
#configEdit .el-step__icon {
    width: 19px;
    height: 19px;
    font-size: 10px;
}
#configEdit .el-step__title{
  font-size: 12px;
  line-height: 20px;
}
#configEdit .el-form-item__label {
    font-size: 12px;
}
.common-config {
  margin: 10px auto;
  width: 98%;
  /* border: 1px solid #CCCCCC; */
  border-radius: 2px;
  padding: 10px;
  overflow: auto;
}
</style>
