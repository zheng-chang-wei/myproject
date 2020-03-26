<template>
  <div id="editSheet">
    <van-tabs v-model="active">
      <van-tab title="维修建议">
        <repair :suggestion="suggestion" />
      </van-tab>
      <van-tab title="填写工单">
        <worksheet
          :basic-info-form="basicInfoForm"
          :show="show"
          :sheet-id="sheetId"
          :pic-urls="picUrls"
          @commit="commit"
          @addPicture="addPicture"
          @deletePicture="deletePicture"
        />
      </van-tab>
    </van-tabs>
  </div>
</template>
<script>
import worksheet from './worksheet'
import repair from './repair'
export default {
  components: {
    worksheet,
    repair
  },
  props: {
    basicInfoForm: {
      type: Object,
      default: null
    },
    suggestion: {
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
      active: 0
    }
  },
  watch: {
    show() {
      this.active = 0
    }
  },
  methods: {
    addPicture(file, detail) {
      this.$emit('addPicture', file, detail)
    },
    deletePicture(file, detail) {
      this.$emit('deletePicture', file, detail)
    },
    commit(sheetId, detail) {
      this.$emit('commit', sheetId, detail)
    }
  }
}

</script>
