<template>
  <section>
    <van-cell-group v-if="!readOnly">
      <van-field v-model="basicInfoForm.project" label="项目名称" required />
      <van-field v-model="basicInfoForm.trainId" label="列车编号" required />
      <van-field v-model="basicInfoForm.doorId" label="车门编号" required />
      <van-field v-model="basicInfoForm.doorType" label="车门种类" required />
      <van-field v-model="basicInfoForm.kilometers" label="运行公里数" required />
      <van-field v-model="basicInfoForm.effect" label="运行影响" required />
      <van-field v-model="basicInfoForm.levelOne" label="一级部件" required />
      <van-field v-model="basicInfoForm.levelTwo" label="二级部件" required />
    </van-cell-group>
    <div v-if="!readOnly" class="button-area">
      <van-button type="primary" style="width: 90%" @click="nextStep">下一步</van-button>
    </div>
    <van-cell-group v-if="readOnly" title="基本信息预览">
      <van-cell title="项目名称" :value="basicInfoForm.project" />
      <van-cell title="列车编号" :value="basicInfoForm.trainId" />
      <van-cell title="车门编号" :value="basicInfoForm.doorId" />
      <van-cell title="车门种类" :value="basicInfoForm.doorType" />
      <van-cell title="运行公里数" :value="basicInfoForm.kilometers" />
      <van-cell title="运营影响" :value="basicInfoForm.effect" />
      <van-cell title="一级部件" :value="basicInfoForm.levelOne" />
      <van-cell title="二级部件" :value="basicInfoForm.levelTwo" />
    </van-cell-group>
  </section>
</template>
<script>
export default {
  props: {
    basicInfoForm: {
      type: Object,
      default: null
    },
    readOnly: {
      type: Boolean,
      default: null
    }
  },
  methods: {
    nextStep() {
      const validate = this.validate()
      if (validate.error) {
        this.$toast(validate.message)
      } else {
        this.$emit('nextStep')
      }
    },
    validate() {
      const param = this.basicInfoForm
      var result = {
        error: true,
        message: ''
      }
      if (!param.project) {
        result.message = '项目名称必填'
      } else if (!param.trainId) {
        result.message = '列车编号必填'
      } else if (!param.doorId) {
        result.message = '车门编号必填'
      } else if (!param.doorType) {
        result.message = '车门类型必填'
      } else if (!param.kilometers) {
        result.message = '运行公里数必填'
      } else if (!param.effect) {
        result.message = '运营影响必填'
      } else if (!param.levelOne) {
        result.message = '一级部件必填'
      } else if (!param.levelTwo) {
        result.message = '二级部件必填'
      } else {
        result.error = false
      }
      return result
    }
  }
}

</script>
