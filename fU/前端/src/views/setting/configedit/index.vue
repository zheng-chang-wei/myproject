<template>
  <div style="position: relative">
    <el-steps :active="myActive" finish-status="success" align-center>
      <el-step title="基本配置" />
      <el-step title="板卡配置" />
      <el-step title="算法配置" />
      <el-step title="存储配置" />
      <el-step title="时间来源配置" />
    </el-steps>
    <basicConfig v-if="myActive===0" class="common-config" :style="commonStyle" :basic-info="basicInfo" @nextStep="nextStep" @cancel="cancel" @importFile="importFile" />
    <boardConfig
      v-if="myActive===1"
      class="common-config"
      :style="commonStyle"
      :board-settings="boardSettings"
      @previousStep="previousStep"
      @toAlgorithmConfig="toAlgorithmConfig"
      @cancel="cancel"
    />
    <algorithmConfig
      v-if="myActive===2"
      class="common-config"
      :style="commonStyle"
      :algorithm-settings="algorithmSettings"
      @previousStep="previousStep"
      @nextStep="nextStep"
      @cancel="cancel"
    />
    <storeConfig
      v-if="myActive===3"
      class="common-config"
      :style="commonStyle"
      :store-config="storeConfig"
      :variables="algorithmSettings.variables"
      @previousStep="previousStep"
      @nextStep="nextStep"
      @cancel="cancel"
    />
    <clockConfig
      v-if="myActive===4"
      class="common-config"
      :style="commonStyle"
      :clock-config-info="clockConfigInfo"
      :variables="algorithmSettings.variables"
      @previousStep="previousStep"
      @save="save"
      @cancel="cancel"
    />
  </div>
</template>

<script>
import basicConfig from './configforms/basicconfig'
import boardConfig from './configforms/boardconfig/index'
import algorithmConfig from './configforms/algorithmconfig/index'
import storeConfig from './configforms/storeconfig'
import clockConfig from './configforms/clockconfig'
import app from '@/common/js/app'
export default {
  components: {
    basicConfig,
    boardConfig,
    algorithmConfig,
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
    },
    active: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      myActive: this.active,
      commonStyle: {}
    }
  },
  mounted() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
    this.$bus.$on('changeActive', (data) => {
      this.myActive = data
    })
  },
  destroyed() {
    this.$root.Bus.$off('changeActive')
  },
  methods: {
    changeHeight() {
      this.commonStyle = { height: document.body.offsetHeight - 145 + 'px' }
    },
    cancel() {
      this.$emit('cancel')
    },
    importFile(result) {
      this.$emit('importFile', result)
    },
    // 上一步
    previousStep() {
      this.myActive--
    },
    // 下一步
    nextStep() {
      this.myActive++
    },
    // 跳转到算法配置
    toAlgorithmConfig(boardSettings) {
      this.myActive++
      this.boardSettings = boardSettings
      this.algorithmSettings.variables = []
      for (let i = 0; i < this.boardSettings.length; i++) {
        const variables = this.boardSettings[i].variables
        if (variables && variables.length > 0) {
          const btaElement = {
            type: this.boardSettings[i].type,
            slotId: parseInt(this.boardSettings[i].slotId),
            variables: variables
          }
          this.algorithmSettings.variables.push(btaElement)
        }
      }
      this.algorithmSettings.variables.sort((a, b) => b.type.charCodeAt(0) - a.type.charCodeAt(0))
    },
    save() {
      for (let i = 0; i < this.boardSettings.length; i++) {
        const type = this.boardSettings[i].type
        if (type.indexOf('AD') === -1) {
          this.boardSettings[i].variables = null
        }
      }
      const param = {
        name: this.basicInfo.name,
        line: this.basicInfo.line,
        train: this.basicInfo.train,
        rawStrategy: this.storeConfig.rawStrategy,
        rawSpace: this.storeConfig.rawSpace,
        resultStrategy: this.storeConfig.resultStrategy,
        resultSpace: 100 - this.storeConfig.rawSpace,
        timeOn: this.clockConfigInfo.timeOn,
        boardSettings: this.boardSettings,
        algorithmSettings: this.algorithmSettings.algorithms,
        timeVariables: {
          mvbGroup: this.clockConfigInfo.mvbGroup,
          ecnGroup: this.clockConfigInfo.ecnGroup
        },
        storeVariables: this.storeConfig.storeVariables
      }
      app.postData('save_protocol', param).then(data => {
        if (data.code === 0) {
          this.$emit('saveSuccess')
          this.$message({
            message: '保存成功',
            type: 'success'
          })
        }
      })
    }
  }
}
</script>

<style>
.el-steps {
  margin-top: 10px;
}

.common-config {
  margin: 10px auto;
  width: 98%;
  border: 1px solid #CCCCCC;
  border-radius: 2px;
  padding: 10px;
  overflow: auto;
}
</style>
