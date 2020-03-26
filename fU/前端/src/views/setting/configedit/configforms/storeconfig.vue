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
    <ConfigVariable :variables="variables" :groups="storeConfig.storeVariables" style="margin-bottom:50px" />
    <div style="position: absolute; bottom: 10px">
      <el-button style="width: 210px" @click="cancel">取消</el-button>
      <el-button style="width: 210px" type="primary" @click="previousStep">上一步</el-button>
      <el-button style="width: 210px" type="primary" @click="nextStep">下一步</el-button>
    </div>

  </section>
</template>

<script>
import ConfigVariable from '@/components/ConfigVariable'
import Slider from '@/components/Slider'
export default {
  components: {
    Slider,
    ConfigVariable
  },
  props: {
    variables: {
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
  mounted() {
  },
  methods: {
    cancel() {
      this.$emit('cancel')
    },
    previousStep() {
      this.$emit('previousStep')
    },
    nextStep() {
      this.$emit('nextStep')
    },
    mouseup(value) {
      this.storeConfig.rawSpace = value
    },
    change() {
      this.$forceUpdate()
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
