<template>
  <section style="overflow-y:scroll">
    <el-row>
      <span>原始数据:</span>
      <el-select v-model="storeConfig.rawStrategy" placeholder="请选择存储模式" style="margin-left:2px" @input="change">
        <el-option v-for="item in dataState" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>
      <span style="padding-left:10px">分析数据:</span>
      <el-select v-model="storeConfig.resultStrategy" placeholder="请选择存储模式" style="margin-left:2px" @input="change">
        <el-option v-for="item in dataState" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>
    </el-row>
    <el-row class="divide-line" />
    <el-row style="margin-top:15px">
      存储容量
    </el-row>
    <el-row style="margin-top:15px">
      <el-col :span="12" class="center">
        原始数据
      </el-col>
      <el-col :span="12" class="center">
        分析数据
      </el-col>
    </el-row>
    <el-row style="margin-top:15px" class="center">
      <slider :value="storeConfig.rawSpace" :min="0" :max="100" @mouseup="mouseup" />
    </el-row>
    <el-row class="divide-line" />
    <ConfigVariable :all-variables="allVariables" :groups="storeConfig" style="margin-bottom:50px" />
    <div style="position: absolute; bottom: 10px;right:20px">
      <!-- <el-button style="width: 170px" @click="cancel">取消</el-button> -->
      <el-button style="width: 170px" type="primary" @click="save">保存</el-button>
      <el-button style="width: 170px" type="primary" @click="previousStep">上一步</el-button>
      <el-button style="width: 170px" type="primary" @click="nextStep">下一步</el-button>
    </div>

  </section>
</template>

<script>
import ConfigVariable from '@/components/ConfigVariable'
import Slider from '@/components/Slider'
import app from '@/common/js/app'
import util from '@/common/js/util'
export default {
  components: {
    Slider,
    ConfigVariable
  },
  props: {
    allVariables: {
      type: Array,
      default: null
    },
    storeConfig: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      activeName: '0',
      dataState: [{
        label: '循环写入',
        value: 0
      }, {
        label: '存满即止',
        value: 1
      }]
    }
  },
  created() {
  },
  mounted() {
  },
  methods: {
    save() {
      if (!this.verify()) {
        return
      }
      const parm = this.getParm()
      app.postData('save_store', parm).then(data => {
        if (data.code === 0) {
          this.$message({
            message: '保存成功',
            type: 'success'
          })
        }
      })
    },
    nextStep() {
      if (!this.verify()) {
        return
      }
      const parm = this.getParm()
      app.postData('save_store', parm).then(data => {
        if (data.code === 0) {
          this.$emit('nextStep')
        }
      })
    },
    getParm() {
      const parm = {
        settingId: localStorage.getItem('settingId'),
        rawStrategy: this.storeConfig.rawStrategy,
        rawSpace: this.storeConfig.rawSpace,
        resultStrategy: this.storeConfig.resultStrategy,
        resultSpace: 100 - this.storeConfig.rawSpace,
        mvbGroups: this.storeConfig.mvbGroups,
        ecnGroups: this.storeConfig.ecnGroups,
        adGroups: this.storeConfig.adGroups
      }
      return parm
    },
    cancel() {
      this.$emit('cancel')
    },
    previousStep() {
      this.$emit('previousStep')
    },

    mouseup(value) {
      this.storeConfig.rawSpace = value
    },
    change() {
      this.$forceUpdate()
    },
    verify() {
      if (this.storeConfig.rawStrategy === null) {
        this.$message({
          dangerouslyUseHTMLString: true,
          message: '原始数据不能为空',
          type: 'error'
        })
        return false
      }
      if (this.storeConfig.resultStrategy === null) {
        this.$message({
          dangerouslyUseHTMLString: true,
          message: '分析数据不能为空',
          type: 'error'
        })
        return false
      }
      if (this.storeConfig.rawSpace === null || this.storeConfig.rawSpace === 0) {
        this.$message({
          dangerouslyUseHTMLString: true,
          message: '存储容量中原始数据占比为0',
          type: 'error'
        })
        return false
      }
      let variables = []
      if ((this.storeConfig.adGroups === null || this.storeConfig.adGroups.length === 0) && (this.storeConfig.ecnGroups === null || this.storeConfig.ecnGroups.length === 0) && (this.storeConfig.mvbGroups === null || this.storeConfig.mvbGroups.length === 0)) {
        this.$message({
          dangerouslyUseHTMLString: true,
          message: '变量未配置',
          type: 'error'
        })
        return false
      } else {
        variables = util.getAllVariables(this.allVariables)
      }
      let isInvalid = false
      if (this.storeConfig.adGroups !== null) {
        this.storeConfig.adGroups.forEach(element => {
          for (let index = 0; index < element.variables.length; index++) {
            const variable = element.variables[index]
            if (util.isInvalid(variable, 'AD', variables)) {
              isInvalid = true
              return
            }
          }
        })
      }
      if (this.storeConfig.ecnGroups !== null) {
        this.storeConfig.ecnGroups.forEach(element => {
          for (let index = 0; index < element.variables.length; index++) {
            const variable = element.variables[index]
            if (util.isInvalid(variable, 'ECN', variables)) {
              isInvalid = true
              return
            }
          }
        })
      }
      if (this.storeConfig.mvbGroups !== null) {
        this.storeConfig.mvbGroups.forEach(element => {
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
        this.$message({
          dangerouslyUseHTMLString: true,
          message: '有失效变量',
          type: 'error'
        })
        return false
      }
      return true
    }
  }
}
</script>

<style scoped>
  .divide-line{
    height: 10px;
    border-bottom: 1px solid #CCCCCC;
  }
  .center {
		display: flex;
		-webkit-align-items: center;
		align-items: center;
		-webkit-justify-content: center;
		justify-content: center;
	}
</style>
