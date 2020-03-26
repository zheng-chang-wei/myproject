<template>
  <div id="worksheet">
    <van-steps :active="step">
      <van-step>基本信息</van-step>
      <van-step>故障信息</van-step>
      <van-step>其他信息</van-step>
      <van-step>工单预览</van-step>
    </van-steps>
    <!-- 基本信息 -->
    <basic v-if="step===0" :basic-info-form="basicInfoForm" @nextStep="nextStep" />

    <!-- 故障信息 -->
    <fault v-if="step===1" :basic-info-form="basicInfoForm" @prevStep="prevStep" @nextStep="nextStep" />

    <!-- 其他信息 -->
    <other
      v-if="step===2"
      :basic-info-form="basicInfoForm"
      :pic-urls="picUrls"
      @prevStep="prevStep"
      @nextStep="nextStep"
      @addPicture="addPicture"
      @deletePicture="deletePicture"
    />

    <!-- 预览 -->
    <section v-if=" step===3 ">
      <basic read-only :basic-info-form="basicInfoForm" />
      <fault read-only :basic-info-form="basicInfoForm" />
      <other read-only :basic-info-form="basicInfoForm" :pic-urls="picUrls" />
      <div class="button-area ">
        <van-button style="width: 45% " @click="prevStep ">上一步</van-button>
        <van-button type="primary" style="width: 45%;margin-left: 10px" @click="commit">提交工单</van-button>
      </div>
    </section>
  </div>
</template>
<script>
import basic from './basic'
import fault from './fault'
import other from './other'
export default {
  components: {
    basic,
    fault,
    other
  },
  props: {
    basicInfoForm: {
      type: Object,
      default: null
    },
    sheetId: {
      type: Number,
      default: null
    },
    picUrls: {
      type: Array,
      default: null
    },
    show: {
      type: Boolean,
      default: null
    }
  },
  data() {
    return {
      step: 0
    }
  },
  watch: {
    show() {
      this.step = 0
    }
  },
  methods: {
    nextStep() {
      this.step = this.step + 1
    },
    prevStep() {
      this.step = this.step - 1
    },
    addPicture(file, detail) {
      this.$emit('addPicture', file, detail)
    },
    deletePicture(pics) {
      this.$emit('deletePicture', pics)
    },
    commit() {
      this.$emit('commit', this.sheetId, this.basicInfoForm)
    }
  }
}

</script>
<style>
	.van-steps {
		margin-bottom: 5px;
	}

	#worksheet {
		height: 100%;
		background-color: #f8f8f8;
	}

	.button-area {
		margin-top: 5px;
		text-align: center;
	}

</style>
