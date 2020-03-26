<template>
  <el-collapse-item title="质量审核" :name="name" style="margin-left:10px">
    <!--质量审核-->
    <div>
      <el-form ref="dform" :model="formData" :inline="true" size="mini" disabled>
        <div style="border:1px solid #000;border-radius: 5px; margin-top:10px">
          <div style="margin:10px">
            <el-form-item label="质量审核人意见：" prop="description">
              <el-input
                v-model="formData.description"
                style="width:500px"
                type="textarea"
                :autosize="{ minRows: 4, maxRows: 8}"
                placeholder="请输入质量审核人意见"
              />
            </el-form-item>
            <br>
            <el-form-item label="编制" prop="organization">
              <el-input v-model="formData.organization" placeholder="编制" />
            </el-form-item>
            <br>
            <el-form-item label="日期" prop="auditTime">
              <el-date-picker
                v-model="formData.date"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择日期时间"
                :editable="false"
              />
            </el-form-item>
          </div>
        </div>
      </el-form>
    </div>
    <!--
          -->
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
    // qualityAuditFormShow:Boolean
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
