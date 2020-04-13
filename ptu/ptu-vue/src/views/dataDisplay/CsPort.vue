<template>
  <el-row id="config" :gutter="5">
    <el-col :span="6">
      <addObj type="CsPort" />
      <addFeatures type="CsPort" />
    </el-col>
    <el-col :span="18">
      <showLineOrTable type="CsPort" />
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

  }
}
</script>

<style>

</style>
