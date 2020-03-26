<template>
  <div>
    <el-input v-model="fileName" style="width:50%" />
    <input ref="file" style="display: none" type="file" accept=".zip" @change="handleClick">
    <el-button style="width: 20%; margin-left:10px" type="primary" @click="openFolderDialog">浏览</el-button>
    <el-button style="width: 20%" type="primary" @click="importFile">导入</el-button>
    <div class="divide-line" />
    <el-form ref="basicInfo" v-model="basicInfo" label-width="100px" style="margin-top: 10px">
      <el-form-item label="配置名称">
        <el-input v-model="basicInfo.name" style="width: 80%" @input="change" />
      </el-form-item>
      <el-form-item label="线路">
        <el-input v-model="basicInfo.line" style="width: 80%" @input="change" />
      </el-form-item>
      <el-form-item label="车号">
        <el-input v-model="basicInfo.train" style="width: 80%" @input="change" />
      </el-form-item>
      <!-- <el-form-item label="数据传输IP">
        <el-input v-model="basicInfo.IP" style="width: 80%" />
      </el-form-item> -->
    </el-form>
    <div style="position: absolute; bottom: 10px">
      <el-button style="width: 320px" @click="cancel">取消</el-button>
      <el-button style="width: 320px" type="primary" @click="nextStep">下一步</el-button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    basicInfo: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      fileName: '',
      fileobj: ''
    }
  },
  mounted() {
  },
  methods: {
    openFolderDialog() {
      this.$refs.file.click()
    },
    handleClick(e) {
      const files = e.target.files
      this.fileobj = files[0]
      this.fileName = this.fileobj.name
    },
    importFile() {
      this.$emit('importFile', this.fileobj)
    },
    change() {
      this.$forceUpdate()
    },
    nextStep() {
      this.$emit('nextStep')
    },
    cancel() {
      this.$emit('cancel')
    }
  }
}
</script>

<style>
  .divide-line{
    height: 10px;
    border-bottom: 1px solid #CCCCCC;
    margin-top: 10px;
  }

</style>
