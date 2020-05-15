<template>
  <div class="app-container">
    <el-tabs v-model="activeName">
      <el-tab-pane label="ComID对象" name="1">
        <ComIdObj />
      </el-tab-pane>
      <el-tab-pane label="CS端口对象" name="2">
        <CSObj />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import ComIdObj from './components/ComIdObj'
import CSObj from './components/CSObj'

export default {
  components: {
    ComIdObj,
    CSObj
  },
  data() {
    return {
      activeName: '1'

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
<style scoped>

</style>
