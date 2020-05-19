<template>
  <el-row id="config" :gutter="5">
    <el-col :span="6">
      <addObj type="ComId" @objChange="objChange" />
      <addFeatures type="ComId" @featuresChange="featuresChange" />
    </el-col>
    <el-col :span="18">
      <showLineOrTable ref="comIdLine" type="ComId" />
    </el-col>
  </el-row>
</template>

<script>
import addObj from './components/addObj/index'
import addFeatures from './components/addFeatures/index'
import showLineOrTable from './components/showLineOrTable/index'
export default {
  components: {
    addObj,
    addFeatures,
    showLineOrTable
  },
  data() {
    return {
    }
  },
  created() {

  },
  mounted() {
  },
  methods: {
    objChange(selectedObjTableDatas) {
      this.$refs.comIdLine.objChange(selectedObjTableDatas)
    },
    featuresChange(selectedFeaturesTableDatas) {
      this.$refs.comIdLine.featuresChange(selectedFeaturesTableDatas)
    }
  }
}
</script>

<style>

</style>
