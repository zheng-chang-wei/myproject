<template>
  <div class="app-container">
    <el-tabs v-model="activeName">
      <el-tab-pane label="ComID对象" name="1">
        <Condition type="ComId" />
      </el-tab-pane>
      <el-tab-pane label="CS端口对象" name="2">
        <Condition type="CsPort" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import Condition from './components/Condition'
export default {
  components: {
    Condition
  },
  data() {
    return {
      activeName: '1'
    }
  },
  methods: {
  }
}
</script>
