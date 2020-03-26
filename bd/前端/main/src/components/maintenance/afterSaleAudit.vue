<template>
  <el-collapse-item title="售后审核" :name="name" style="margin-left:10px">
    <!--售后审核-->
    <el-form :model="formData" :inline="true" size="mini" disabled>
      <div style="border:1px solid #000;border-radius: 5px; margin-top:10px">
        <div style="margin:10px">
          <el-form-item label="故障审核人" prop="auditor">
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
          <el-form-item label="故障件图号" prop="faultNo">
            <el-input v-model="formData.faultNo" />
          </el-form-item>
          <el-form-item label="需求配件图号" prop="spareNo">
            <el-input v-model="formData.spareNo" />
          </el-form-item>
          <el-form-item label="审核人意见" prop="description">
            <el-input
              v-model="formData.description"
              style="width:500px"
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 8}"
            />
          </el-form-item>
          <br>
          <el-form-item label="是否计入统计" prop="statistics">
            <el-input v-if="formData.statistics" value="是" />
            <el-input v-else value="否" />
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
