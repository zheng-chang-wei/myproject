<template>
  <section>
    <van-cell-group v-if="!readOnly">
      <van-cell title="是否需要备件" center>
        <template>
          <van-switch v-model="basicInfoForm.needParts" />
        </template>
      </van-cell>
      <van-field v-model="basicInfoForm.partName" label="需求配件名称" :disabled="!basicInfoForm.needParts" />
      <van-cell title="需求配件数量" center>
        <template>
          <van-stepper v-model="basicInfoForm.partCount" min="0" integer :disabled="!basicInfoForm.needParts" />
        </template>
      </van-cell>
      <van-cell title="是否分析报告" center>
        <template>
          <van-switch v-model="basicInfoForm.needReport" />
        </template>
      </van-cell>
      <van-cell title="现场处理人数" center>
        <template>
          <van-stepper v-model="basicInfoForm.processorCount" min="0" integer />
        </template>
      </van-cell>
      <van-field v-model="basicInfoForm.processor" label="现场处理人" />
    </van-cell-group>
    <van-cell-group v-if="!readOnly" title="上传图片">
      <van-cell>
        <van-uploader v-model="pictures" :after-read="afterRead" :before-read="beforeRead" :max-count="5" multiple @delete="deletePicture" />
      </van-cell>
    </van-cell-group>
    <div v-if="!readOnly" class="button-area">
      <van-button style="width: 45%" @click="prevStep">上一步</van-button>
      <van-button type="primary" style="width: 45%;margin-left: 10px" @click="nextStep">下一步</van-button>
    </div>
    <van-cell-group v-if="readOnly" title="其他信息预览">
      <van-cell title="是否需要配件" :value="basicInfoForm.needParts?'需要':'不需要'" />
      <van-cell title="需求配件名称" :value="basicInfoForm.partName" />
      <van-cell title="需求配件数量" :value="basicInfoForm.partCount" />
      <van-cell title="是否分析报告" :value="basicInfoForm.needReport?'是':'否'" />
      <van-cell title="现场处理人数" :value="basicInfoForm.processorCount" />
      <van-cell title="现场处理人" :value="basicInfoForm.processor" />

      <van-cell title="图片预览">
        <template>
          <span>{{ picUrls.length }}张</span>
          <van-button type="primary" :disabled="picUrls.length===0" @click="imageview.show=true">查看</van-button>
        </template>
      </van-cell>
    </van-cell-group>
    <van-image-preview v-model="imageview.show" :images="picUrls" @change="imageChange">
      <template v-slot:index>第{{ imageview.index+1 }}张</template>
    </van-image-preview>
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
    },
    picUrls: {
      type: Array,
      default: null
    }
  },
  data() {
    return {
      pictures: [],
      imageview: {
        show: false,
        index: 0
      }
    }
  },
  computed: {},
  mounted() {},
  methods: {
    prevStep() {
      this.$emit('prevStep')
    },
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
      if (param.needParts) {
        if (!param.partName) {
          result.message = '需要填写配件名称'
          return result
        }
      }
      if (!param.processor) {
        result.message = '现场处理人必填'
      } else {
        result.error = false
      }
      return result
    },
    afterRead(file, detail) {
      this.$emit('addPicture', file, detail)
    },
    beforeRead(file, detail) {
      const isLt2M = file.sieze / 1024 / 1024 < 10
      if (!isLt2M) {
        this.$toast('上传的图片必须小于10MB！')
      }
      return isLt2M
    },
    deletePicture(file, detail) {
      console.log(detail)
      this.$emit('deletePicture', [{
        file: file,
        detail: detail
      }])
    },
    imageChange(index) {
      this.imageview.index = index
    }
  }
}

</script>
