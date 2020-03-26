<template>
  <el-collapse-item style="margin-left:10px" title="驳回信息" :name="name">
    <el-form :model="formData" :inline="true" size="mini" disabled>
      <div style="border:1px solid #000;border-radius: 5px; margin-top:10px">
        <div style="margin:10px">
          <el-form-item label="驳回人" prop="auditor">
            <el-input v-model="formData.auditor" />
          </el-form-item>
          <el-form-item label="日期" prop="date">
            <el-date-picker
              v-model="formData.date"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择日期时间"
              :editable="false"
            />
          </el-form-item>
          <el-form-item label="驳回信息" prop="description">
            <el-input
              v-model="formData.description"
              style="width:500px"
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 8}"
            />
          </el-form-item>
        </div>
      </div>
    </el-form>
  </el-collapse-item>
</template>
<script>
export default {
  props: {
    formData: {
      type: Object,
      default: null
    },
    disableEdit: Boolean,
    name: {
      type: Number,
      default: null
    }
    // afterSaleAuditFormShow:Boolean
  },
  data() {
    return {
    }
  },
  mounted() {
  },
  methods: {

  }
}
</script>
<style >
</style>
