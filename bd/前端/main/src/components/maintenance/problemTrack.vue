<template>
  <el-collapse-item title="跟踪" :name="name" style="margin-left:10px">
    <!--跟踪-->
    <div>
      <el-form ref="fform" :model="formData" :inline="true" size="mini" disabled>
        <div style="border:1px solid #000;border-radius: 5px; margin-top:10px">
          <div style="margin:10px">
            <el-form-item label="最终处理结果的跟踪与验证" prop="description">
              <el-input
                v-model="formData.description"
                style="width:500px"
                type="textarea"
                :autosize="{ minRows: 4, maxRows: 8}"
                placeholder="请输入最终处理结果的跟踪与验证"
              />
            </el-form-item>
            <br>
            <el-form-item label="完成故障维修的最终时间" prop="endTime">
              <el-input v-model="formData.endTime" placeholder="结束时间" />
            </el-form-item>
            <br>
            <el-form-item label="签名" prop="memberName">
              <el-input v-model="formData.memberName" placeholder="人员名称" />
            </el-form-item>
            <br>
            <el-form-item label="日期" prop="auditTime">
              <el-input v-model="formData.endTime" placeholder="时间" />
            </el-form-item>
          </div>
        </div>
      </el-form>
    </div>
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
    // problemTrackFormShow:Boolean
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
