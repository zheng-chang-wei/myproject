<template>
  <div id="sheetInfo">
    <basic read-only :basic-info-form="basicInfoForm" />
    <fault read-only :basic-info-form="basicInfoForm" />
    <other read-only :basic-info-form="basicInfoForm" :pic-urls="picUrls" />
    <van-cell title="关闭时间" :value="closeTime" />
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
    closeTime: {
      type: String,
      default: null
    },
    picUrls: {
      type: Array,
      default: null
    }
  },
  data() {
    return {
      // picUrls: []
    }
  }
}

</script>
<style>
	#sheetInfo {
		background-color: #f8f8f8;
	}

</style>
