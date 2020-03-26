<template>
  <el-collapse-item title="问题解决" :name="name" style="margin-left:10px">
    <!--问题解决-->
    <el-form ref="eform" :model="formData" :inline="true" size="mini" disabled>
      <div style="border:1px solid #000;border-radius: 5px; margin-top:10px">
        <div style="margin:10px">
          <el-form-item label="责任部门处理过程描述及结果" prop="description">
            <el-input
              v-model="formData.description"
              style="width:500px"
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 8}"
              placeholder="责任部门处理过程描述及结果"
            />
          </el-form-item>
          <br>
          <el-form-item label="申述" prop="isAppeal">
            <el-input v-model="formData.isAppeal" placeholder="是否申诉" />
          </el-form-item>
          <br>
          <el-form-item label="申诉原因" prop="description1">
            <el-input
              v-model="formData.description1"
              style="width:500px"
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 8}"
              placeholder="申诉原因"
            />
          </el-form-item>
          <br>
          <br>
          <el-form-item label="编制" prop="organization">
            <el-input v-model="formData.organization" placeholder="请输入编制" />
          </el-form-item>
          <br>
          <el-form-item label="日期" prop="auditTime">
            <el-date-picker
              v-model="formData.auditTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择日期时间"
              style="width:150px;margin-left:5px"
              :editable="false"
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
    // problemSolvingFormShow:Boolean
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
