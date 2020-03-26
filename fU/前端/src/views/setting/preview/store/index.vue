<template>
  <el-card class="box-card" shadow="hover">
    <el-row slot="header" class="center">
      <el-col :span="23"><span>存储配置</span></el-col>
      <el-col :span="1">
        <el-button type="text" @click="editClicked">编辑</el-button>
      </el-col>
    </el-row>
    <el-form :model="storeConfig" label-width="150px">
      <el-form-item label="原始数据存储方式：" size="small">
        {{ dataState[storeConfig.rawStrategy] }}
      </el-form-item>
      <el-form-item label="分析数据存储方式：" size="small">
        {{ dataState[storeConfig.resultStrategy] }}
      </el-form-item>
      <el-form-item label="原始数据存储容量：" size="small">
        {{ storeConfig.rawSpace }}%
      </el-form-item>
      <el-form-item label="原始数据存储容量：" size="small">
        {{ storeConfig.resultSpace }}%
      </el-form-item>
    </el-form>
    <CustomTab :variables="storeConfig.variables" style="margin:5px 10px" />
  </el-card>
</template>

<script>
import CustomTab from '@/components/CustomTab'
export default {
  components: {
    CustomTab
  },
  props: {
    storeConfig: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      variables: [],
      dataState: ['循环写入', '存满即止']
    }
  },
  created() {
  },
  methods: {
    editClicked() {
      this.$emit('edit', 3)
    }
  }
}
</script>

<style>

</style>
