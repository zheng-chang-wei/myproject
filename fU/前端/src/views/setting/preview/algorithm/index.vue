<template>
  <el-card class="box-card" shadow="hover">
    <el-row slot="header" class="center">
      <el-col :span="23"><span>算法配置</span></el-col>
      <el-col :span="1">
        <el-button type="text" @click="editClicked">编辑</el-button>
      </el-col>
    </el-row>
    <el-collapse style="margin:5px 10px">
      <template v-for="algorithm in algorithmSettings.algorithms">
        <el-collapse-item :key="algorithm.slotId" :title="algorithm.name">
          <el-form label-position="left" label-width="100px">
            <el-form-item label="脚本文件">
              {{ algorithm.filename }}
            </el-form-item>
            <el-form-item label="卡槽号">
              {{ algorithm.slotId }}
            </el-form-item>
          </el-form>
          <CustomTab :variables="algorithm.variables" />
        </el-collapse-item>
      </template>
    </el-collapse>
  </el-card>
</template>

<script>
import CustomTab from '@/components/CustomTab'
export default {
  components: {
    CustomTab
  },
  props: {
    algorithmSettings: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
    }
  },
  created() {

  },
  methods: {
    editClicked() {
      this.$emit('edit', 2)
    }
  }
}
</script>

<style>

</style>
