<template>
  <div>
    <el-form v-model="outputForm" inline size="mini">
      <el-form-item label="输出至TRDP：">
        <el-switch v-model="outputForm.trdp" :disabled="switchDisabled" @change="switchChange" />
      </el-form-item>
    </el-form>
    <!-- <el-button type="primary" style="margin-bottom:10px; margin-top:10px" @click="clearInvalidVariables">清空失效变量
    </el-button> -->
    <el-tabs v-model="activeName">
      <el-tab-pane label="公共包头配置" name="1">
        <commonTab :common-table-data="commonTableData" :variables="variables" />
      </el-tab-pane>
      <el-tab-pane label="输出序列配置" name="2">
        <outputTab :output-table-data="outputTableData" :algorithm-options="algorithmOptions" />
      </el-tab-pane>
    </el-tabs>

    <div style="position: absolute; bottom: 10px;right:20px">
      <!-- <el-button style="width: 170px" @click="cancel">取消</el-button> -->
      <el-button style="width: 170px" type="primary" @click="save">保存</el-button>
      <el-button style="width: 170px" type="primary" @click="previousStep">上一步</el-button>
      <el-button style="width: 170px" type="primary" @click="nextStep">下一步</el-button>
    </div>
  </div>
</template>

<script>
import commonTab from '@/components/outputConfigComponents/commonTab'
import outputTab from '@/components/outputConfigComponents/outputTab'
import app from '@/common/js/app'
// import util from '@/common/js/util'
export default {
  components: {
    commonTab,
    outputTab
  },
  props: {
    outputSettings: {
      type: Object,
      default: null
    },
    algorithmSettings: {
      type: Array,
      default: null
    },
    mvbVariables: {
      type: Array,
      default: null
    },
    ecnVariables: {
      type: Array,
      default: null
    },
    adVariables: {
      type: Array,
      default: null
    }
  },

  data() {
    return {
      switchDisabled: false,
      outputForm: {
        trdp: false
      },
      queryForm: {
        comId: ''
      },
      activeName: '1',
      commonTableData: [],
      outputTableData: [],
      variables: [],
      commonLength: 0,
      algorithmOptions: []
    }
  },
  mounted() {
    if (this.ecnVariables.length !== 0) {
      this.switchDisabled = false
      this.variables = this.ecnVariables[0].variables
      this.algorithmSettings.forEach(element => {
        this.algorithmOptions.push(element.name)
      })
      this.initData()
      if (this.commonTableData.length !== 0 || this.outputTableData.length !== 0) {
        this.outputForm.trdp = true
      }
    } else {
      this.outputForm.trdp = false
      this.switchDisabled = true
    }
  },
  methods: {
    initData() {
      if (this.outputSettings !== null) {
        this.commonLength = this.outputSettings.commonLength
        this.commonTableData = this.outputSettings.commonSegments
        this.outputTableData = this.outputSettings.algorithms
        if (this.outputTableData === null) {
          this.outputTableData = []
        }
        if (this.commonTableData === null) {
          this.commonTableData = []
        }
        // this.commonTableData.forEach(element => {
        //   element['isInvalid'] = util.isInvalid(element.source, 'ECN', this.variables)
        // })
      }
    },
    initCommonTableData() {
      this.commonTableData = []
      app.get('result_segment_header').then(data => {
        if (data.code === 0) {
          this.commonLength = data.data.length
          data.data.segments.forEach(element => {
            this.commonTableData.push({
              index: element.index,
              name: element.name,
              dataType: element.dataType,
              byteLen: element.byteLen,
              type: 1,
              source: {
                signalName: ''
              }
            })
          })
        }
      })
    },
    switchChange() {
      if (this.outputForm.trdp) {
        this.initData()
        if (this.commonTableData === null || this.commonTableData.length === 0) {
          this.initCommonTableData()
        }
      }
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
      const parm = this.getParm()
      app.postData('save_output', parm).then(data => {
        if (data.code === 0) {
          this.$emit('nextStep')
        }
      })
    },
    save() {
      if (!this.verify()) {
        return
      }
      const parm = this.getParm()
      app.postData('save_output', parm).then(data => {
        if (data.code === 0) {
          this.$message({
            message: '保存成功',
            type: 'success'
          })
        }
      })
    },
    getParm() {
      let parm = null
      if (this.outputForm.trdp) {
        parm = {
          settingId: localStorage.getItem('settingId'),
          commonLength: this.commonLength,
          commonSegments: this.commonTableData,
          algorithms: this.outputTableData
        }
      } else {
        parm = {
          settingId: localStorage.getItem('settingId'),
          commonLength: this.commonLength,
          commonSegments: [],
          algorithms: []
        }
      }
      return parm
    },
    verify() {
      let errors = ''
      if (this.outputForm.trdp) {
        let isInvalid = false
        this.commonTableData.forEach(element => {
          if (element.source.signalName === '') {
            if (errors !== '') {
              errors += ' ， '
            }
            errors += element.name
          }
          if (element.isInvalid) {
            isInvalid = true
          }
        })
        if (errors !== '') {
          errors += '未选择信号'
        }
        if (isInvalid) {
          if (errors !== '') {
            errors += '</br></br>'
          }
          errors += '公共包头配置包含失效变量'
        }
      }
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
    clearInvalidVariables() {
      this.commonTableData.forEach(element => {
        if (element.isInvalid) {
          element.source = {
            signalName: ''
          }
          element.isInvalid = false
        }
      })
    }
  }
}
</script>

<style>

</style>
