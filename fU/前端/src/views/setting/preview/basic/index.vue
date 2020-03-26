<template>
  <el-card class="box-card" shadow="hover">
    <el-row slot="header" class="center">
      <el-col :span="23"><span>基本配置</span></el-col>
      <el-col :span="1">
        <el-button type="text" @click="editClicked">编辑</el-button>
      </el-col>
    </el-row>
    <el-form :model="basicInfo" label-width="90px">
      <el-form-item label="配置名称：" size="small">
        {{ basicInfo.name }}
      </el-form-item>
      <el-form-item label="线路：" size="small">
        {{ basicInfo.line }}
      </el-form-item>
      <el-form-item label="车号：" size="small">
        {{ basicInfo.train }}
      </el-form-item>
      <!-- <el-form-item label="数据传输IP：" size="small">
        {{ basicInfo.IP }}
      </el-form-item> -->
    </el-form>
  </el-card>
</template>

<script>
export default {
  components: {

  },
  props: {
    basicInfo: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
    }
  },
  methods: {
    editClicked() {
      this.$emit('edit', 0)
    }
  }
}
</script>

<style>

</style>
