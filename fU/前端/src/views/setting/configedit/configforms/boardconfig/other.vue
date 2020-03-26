<template>
  <div class="trdp">
    <el-collapse>
      <el-collapse-item :title="otherForm.type">
        <el-form ref="otherForm" v-model="otherForm" label-position="left" label-width="100px">
          <el-form-item prop="delivery" style="text-align: right">
            <el-switch v-model="otherForm.enable" style="margin-right: 30px" />
            <el-button type="danger" icon="el-icon-close" size="mini" circle @click="deleteBoard()" />
          </el-form-item>
          <el-form-item label="卡槽号">
            <el-input v-model="otherForm.slotId" style="width:300px" />
          </el-form-item>
          <el-form-item label="前面板IP">
            <el-input v-model="otherForm.ip" style="width:300px" />
          </el-form-item>
        </el-form>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
export default {
  props: {
    index: {
      type: Number,
      default: null
    },
    otherForm: {
      type: Object,
      default: null
    }
  },
  data() {
    return {

    }
  },
  methods: {
    deleteBoard() {
      this.$emit('deleteBoard', this.index)
    }
  }
}
</script>

<style>

</style>
