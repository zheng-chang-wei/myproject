<template>
  <div>
    <el-collapse-item title="工单具体信息" :name="name" style="background-color: #242640;">
      <el-form ref="basicInfoForm" :model="basicInfoForm" :inline="true" :disabled="disableEdit" label-width="110px" :rules="formRules">
        <div class="sheet-block">
          基本信息
          <div style="margin:10px">
            <el-form-item label="项目名称">
              <el-select v-model="basicInfoForm.project" placeholder="项目名称" @change="projectChange">
                <el-option
                  v-for="item in projectOptions"
                  :key="item"
                  :label="item"
                  :value="item"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="车辆编号">
              <el-select v-model="basicInfoForm.trainId" placeholder="车辆编号">
                <el-option
                  v-for="item in trainNoOptions"
                  :key="item"
                  :label="item"
                  :value="item"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="车门编号" prop="doorId">
              <el-input v-model="basicInfoForm.doorId" />
            </el-form-item>
            <el-form-item label="车门种类">
              <el-select v-model="basicInfoForm.doorTypeId" placeholder="车门种类">
                <el-option
                  v-for="item in doorTypeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="运营影响">
              <el-select v-model="basicInfoForm.effectId" placeholder="运营影响">
                <el-option
                  v-for="item in effectOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="运行公里数" prop="kilometers">
              <el-input v-model="basicInfoForm.kilometers" />
            </el-form-item>
            <el-form-item label="一级部件" prop="levelOne">
              <el-input v-model="basicInfoForm.levelOne" />
            </el-form-item>
            <el-form-item label="二级部件" prop="levelTwo">
              <el-input v-model="basicInfoForm.levelTwo" />
            </el-form-item>
          </div>
        </div>
        <div class="sheet-block">
          故障信息
          <div style="margin:10px">
            <el-form-item label="故障件名称" prop="component">
              <el-input v-model="basicInfoForm.component" />
            </el-form-item>
            <el-form-item label="故障件数量" prop="count">
              <el-input v-model="basicInfoForm.count" />
            </el-form-item>
            <el-form-item label="故障发生阶段">
              <el-select v-model="basicInfoForm.stageId" placeholder="故障发生阶段">
                <el-option
                  v-for="item in faultStageOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="故障模式">
              <el-select v-model="basicInfoForm.modeId" placeholder="故障模式">
                <el-option
                  v-for="item in faultModeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="发生地点" prop="location">
              <el-input v-model="basicInfoForm.location" />
            </el-form-item>
            <el-form-item label="故障时间" prop="faultTime">
              <el-date-picker
                v-model="basicInfoForm.faultTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择日期时间"
                :editable="false"
              />
            </el-form-item>
            <el-form-item label="临时处理措施">
              <el-select v-model="basicInfoForm.temporary" placeholder="临时处理措施">
                <el-option
                  v-for="item in temporaryOptions"
                  :key="item"
                  :label="item"
                  :value="item"
                />
              </el-select>
            </el-form-item>
            <el-form-item :label="basicInfoForm.temporary+'数量'" prop="replaceOrAdjustCount">
              <el-input v-model="basicInfoForm.replaceOrAdjustCount" />
            </el-form-item>
            <el-form-item label="维修工时(单人)">
              <el-select v-model="basicInfoForm.repairTime" placeholder="维修工时（单人）">
                <el-option
                  v-for="item in repairTimeOptions"
                  :key="item"
                  :label="item"
                  :value="item"
                />
              </el-select>
            </el-form-item>
            <template v-for="(item,index) in basicInfoForm.variables">
              <el-form-item :key="index" :label="item">
                <el-input v-model="basicInfoForm['value'+index]" />
              </el-form-item>
            </template>
            <br>
            <el-form-item label="故障描述" prop="description">
              <el-input v-model="basicInfoForm.description" style="width:400px" type="textarea" :autosize="{ minRows: 4, maxRows: 8}" placeholder="请输入故障信息、发生地点、时间" />
            </el-form-item>
            <el-form-item label="初步原因分析" prop="reason">
              <el-input v-model="basicInfoForm.reason" style="width:400px" type="textarea" :autosize="{ minRows: 4, maxRows: 8}" placeholder="请输入初步分析原因以及处理措施，是否复现" />
            </el-form-item>
            <br>
            <el-form-item label="是否需要配件">
              <template>
                <el-radio-group v-model="basicInfoForm.needParts" style="width:140px">
                  <el-radio :label="true">是</el-radio>
                  <el-radio :label="false">否</el-radio>
                </el-radio-group>
              </template>
            </el-form-item>
            <el-form-item v-if="basicInfoForm.needParts" label="需求配件名称" prop="partName">
              <el-input v-model="basicInfoForm.partName" />
            </el-form-item>
            <el-form-item v-if="basicInfoForm.needParts" label="需求配件数量" prop="partCount">
              <el-input v-model="basicInfoForm.partCount" />
            </el-form-item>
            <br>
            <el-form-item label="是否分析报告">
              <template>
                <el-radio-group v-model="basicInfoForm.needReport" style="width:140px">
                  <el-radio :label="true">是</el-radio>
                  <el-radio :label="false">否</el-radio>
                </el-radio-group>
              </template>
            </el-form-item>
            <br>
            <el-form-item label="图片" prop="imagePath">
              <el-image v-for="url in imageUrls" :key="url" style="width: 100px; height: 100px" :src="url" fit="scale-down" :preview-src-list="imageUrls" />
              <el-button @click="uploadImage">上传图片</el-button>
              <el-button @click="deleteImage">删除图片</el-button>
            </el-form-item>
            <br>
            <el-form-item label="视频" prop="videoPath">
              <el-link :href="GLOBAL.serverIpAndPort+basicInfoForm.videoName" target="_blank" style="color:white">{{ basicInfoForm.videoName }}</el-link>
              <el-button @click="uploadVideo">上传视频</el-button>
            </el-form-item>
            <br>
            <el-form-item label="现场处理人数" prop="processorCount">
              <el-input v-model="basicInfoForm.processorCount" />
            </el-form-item>
            <el-form-item label="现场处理人" prop="processor">
              <el-input v-model="basicInfoForm.processor" />
            </el-form-item>
          </div>
        </div>
      </el-form>
    </el-collapse-item>
    <div v-if="!disableEdit" style="text-align:center;margin-top:10px;margin-bottom: 20px;">
      <el-button @click="resetForm()">返回</el-button>
      <el-button @click="commitBasicInfo()">提交</el-button>
    </div>
    <el-dialog title="上传图片" :visible.sync="uploadImageDialogVisible" :close-on-click-modal="false" :before-close="handleClose">
      <el-upload
        ref="upload"
        action=""
        multiple
        accept=".jpg,.png"
        :file-list="fileList"
        :auto-upload="false"
        :on-change="addFile"
        :http-request="uploadFile"
        :before-upload="beforeUpload"
      >
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload(true)">上传到服务器
        </el-button>
        <div slot="tip" class="el-upload__tip" style="color:#fff">只能上传jpg/png文件</div>
      </el-upload>
    </el-dialog>

    <el-dialog title="删除图片" :visible.sync="deleteImageDialogVisible" :close-on-click-modal="false" :before-close="handleClose">
      <el-button style="margin-left: 10px;" size="small" type="danger" @click="handleDelete">删除图片
      </el-button>
      <el-table :data="tableData" style="width: 100%;margin-top:20px" border @selection-change="selsChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column align="center" prop="url" label="图片">
          <template slot-scope="scope">
            <el-image style="width: 100px; height: 100px" :src="scope.row.url" fit="scale-down" />
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <el-dialog title="上传视频" :visible.sync="uploadVideoDialogVisible" :close-on-click-modal="false" :before-close="handleClose">
      <el-upload
        ref="upload"
        action=""
        accept=".MP4"
        :file-list="fileList"
        :auto-upload="false"
        :on-change="addFile"
        :http-request="uploadFile"
        :before-upload="beforeUpload"
      >
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload(false)">上传到服务器
        </el-button>
        <div slot="tip" class="el-upload__tip" style="color:#fff">只能上传MP4文件</div>
      </el-upload>
    </el-dialog>
  </div>
</template>
<script>
import app from '@/common/js/app'
export default {
  props: {
    basicInfoForm: {
      type: Object,
      default: null
    },
    disableEdit: Boolean,
    name: {
      type: Number,
      default: null
    },
    jump: Boolean,
    sheetId: {
      type: String,
      default: ''
    },
    detailId: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      uploadImageDialogVisible: false,
      deleteImageDialogVisible: false,
      uploadVideoDialogVisible: false,
      isUploadImage: false,
      sels: [],
      tableData: [],
      fileList: [],
      imageUrls: [],
      imageNames: [],
      projectOptions: [],
      trainNoOptions: [],
      doorTypeOptions: [],
      effectOptions: [],
      faultModeOptions: [],
      faultStageOptions: [],
      temporaryOptions: ['更换', '调整'],
      repairTimeOptions: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
      fileListLength: 0,
      successedFileListLength: 0,
      updateSuccessCount: 0,
      // 表单验证规则
      formRules: {
        kilometers: [{
          required: true,
          message: '请输入公里数',
          trigger: 'change'
        }],
        faultTime: [{
          type: 'string',
          required: true,
          message: '请选择故障日期',
          trigger: 'change'
        }],
        doorId: [{
          required: true,
          message: '请输入车门编号',
          trigger: 'change'
        }],
        levelOne: [{
          required: true,
          message: '请选择一级部件',
          trigger: 'change'
        }],
        levelTwo: [{
          required: true,
          message: '请选择二级部件',
          trigger: 'change'
        }],
        location: [{
          required: true,
          message: '请输入故障发生地点',
          trigger: 'change'
        }],
        component: [{
          required: true,
          message: '请输入故障件名称',
          trigger: 'change'
        }],
        count: [{
          required: true,
          message: '请选择故障故障件数量',
          trigger: 'change'
        }],
        replaceOrAdjustCount: [{
          required: true,
          message: '请输入数量',
          trigger: 'change'
        }],
        partName: [{
          required: true,
          message: '请输入需求配件名称',
          trigger: 'change'
        }],
        partCount: [{
          required: true,
          message: '请选择需求配件数量',
          trigger: 'change'
        }],
        description: [{
          required: true,
          message: '请输入故障描述',
          trigger: 'change'
        }],
        processorCount: [{
          required: true,
          message: '请输入现场处理人数',
          trigger: 'change'
        }],
        reason: [{
          required: true,
          message: '请输入初步原因分析以及处理措施',
          trigger: 'change'
        }],
        processor: [{
          required: true,
          message: '请输入处理人',
          trigger: 'change'
        }]
      }
    }
  },
  mounted() {
    if (!this.jump) {
      this.getProject()
      this.listDoorType()
      this.listEffect()
      this.listFaultMode()
      this.listFaultStage()
      this.getDatas()
    } else {
      if (this.basicInfoForm.imagePath) {
        this.imageNames = this.basicInfoForm.imagePath.split(',')
        this.imageNames.forEach(name => {
          this.imageUrls.push(this.GLOBAL.serverIpAndPort + name)
        })
      }
      if (this.basicInfoForm.variables) {
        this.basicInfoForm.variables = this.basicInfoForm.variables.split(',')
        const values = this.basicInfoForm.valuess.split(',')
        for (let i = 0; i < values.length; i++) {
          this.basicInfoForm['value' + i] = values[i]
        }
      }
    }
  },
  methods: {
    // 获取项目名称
    getProject() {
      app.get('get_project').then(data => {
        if (data.msg) {
          data.msg.forEach(element => {
            this.projectOptions.push(element.name)
          })
          if (this.projectOptions.length !== 0) {
            this.basicInfoForm.project = this.projectOptions[0]
            this.projectChange()
          }
        }
      })
    },
    projectChange() {
      this.trainNoOptions = []
      const params = {
        project: this.basicInfoForm.project
      }
      app.get('get_train_no', params).then(data => {
        if (data.msg) {
          data.msg.forEach(element => {
            this.trainNoOptions.push(element.trainNo)
          })
          if (this.trainNoOptions.length !== 0) {
            this.basicInfoForm.trainId = this.trainNoOptions[0]
          }
        }
      })
    },
    listDoorType() {
      this.doorTypeOptions = []
      app.get('list_door_type').then(data => {
        if (data.msg) {
          data.msg.forEach(element => {
            this.doorTypeOptions.push({
              value: element.id,
              label: element.doorTypeName
            })
          })
          if (this.doorTypeOptions.length !== 0) {
            this.basicInfoForm.doorTypeId = this.doorTypeOptions[0].value
          }
        }
      })
    },
    listEffect() {
      this.effectOptions = []
      app.get('list_effect').then(data => {
        if (data.msg) {
          data.msg.forEach(element => {
            this.effectOptions.push({
              value: element.id,
              label: element.effectName
            })
          })
          if (this.effectOptions.length !== 0) {
            this.basicInfoForm.effectId = this.effectOptions[0].value
          }
        }
      })
    },
    listFaultMode() {
      this.faultModeOptions = []
      app.get('list_fault_mode').then(data => {
        if (data.msg) {
          data.msg.forEach(element => {
            this.faultModeOptions.push({
              value: element.id,
              label: element.modeName
            })
          })
          if (this.faultModeOptions.length !== 0) {
            this.basicInfoForm.modeId = this.faultModeOptions[0].value
          }
        }
      })
    },
    listFaultStage() {
      this.faultStageOptions = []
      app.get('list_fault_stage').then(data => {
        if (data.msg) {
          data.msg.forEach(element => {
            this.faultStageOptions.push({
              value: element.id,
              label: element.stageName
            })
          })
          if (this.faultStageOptions.length !== 0) {
            this.basicInfoForm.stageId = this.faultStageOptions[0].value
          }
        }
      })
    },
    // 获取自定义变量
    getDatas() {
      this.basicInfoForm.variables = []
      app.get('config_list_variable').then(response => {
        if (response.code === 0) {
          response.data.forEach(element => {
            this.basicInfoForm.variables.push(element.name)
          })
        }
      })
    },
    beforeUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 10 // 这里做文件大小限制
      if (!isLt2M) {
        this.$message({
          message: '上传文件须小于 10MB!',
          type: 'warning'
        })
        this.updateSuccessCount++
      }
      return isLt2M
    },
    uploadImage() {
      this.uploadImageDialogVisible = true
    },
    uploadVideo() {
      this.uploadVideoDialogVisible = true
    },
    deleteImage() {
      this.deleteImageDialogVisible = true
      this.tableData = []
      this.imageUrls.forEach(url => {
        this.tableData.push({
          url: url
        })
      })
    },
    handleDelete() {
      var vm = this
      var filepaths = this.sels.toString()
      if (this.sels.length === 0) {
        this.$message({
          message: '请至少勾选一个图片删除',
          type: 'error'
        })
        return
      }
      this.$confirm('确认删除选中记录？', '提示', {
        type: 'warning'
      }).then(() => {
        const param = {
          filepaths: filepaths
        }
        app.post('delete_file_byFilepaths', param).then(data => {
          vm.listLoading = false
          if (data.code === '0') {
            vm.$message({
              message: '删除成功',
              type: 'success'
            })
            vm.deleteImageDialogVisible = false
            filepaths.split(',').forEach(filePath => {
              var index = vm.imageNames.indexOf(filePath)
              if (index !== -1) {
                vm.imageUrls.splice(index, 1)
                vm.imageNames.splice(index, 1)
              }
            })
          }
        })
      }).catch(() => {})
    },
    submitUpload(isUploadImage) {
      this.isUploadImage = isUploadImage
      this.updateSuccessCount = 0
      this.$refs.upload.submit()
    },
    // 上传文件
    uploadFile(param) {
      this.loading = this.$loading({
        lock: true,
        text: '文件上传中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      var vm = this
      vm.fileListLength = vm.fileList.length - vm.successedFileListLength
      const formData = new FormData()
      formData.append('file', param.file)
      app.uploadFile('upload_file', formData).then(data => {
        if (data.msg) {
          if (data.code === 0) {
            vm.successedFileListLength++
            vm.updateSuccessCount++
            if (vm.updateSuccessCount === vm.fileListLength) {
              vm.loading.close()
            }
            vm.$message({
              message: param.file.name + '上传成功',
              type: 'success'
            })
            if (vm.isUploadImage) {
              vm.imageUrls.push(this.GLOBAL.serverIpAndPort + data.data)
              vm.imageNames.push(data.data)
            } else {
              vm.basicInfoForm.videoName = data.data
            }
            param.onSuccess()
          }
        }
      }).catch(response => {
        param.onError()
        vm.updateSuccessCount++
        if (vm.updateSuccessCount === vm.fileListLength) {
          vm.loading.close()
        }
      })
    },
    selsChange(sels) {
      this.sels = []
      sels.forEach(sel => {
        var name = sel.url.substring(sel.url.lastIndexOf('/') + 1, sel.url.length)
        this.sels.push(name)
      })
    },
    // 显示上传的文件
    addFile(file, fileList) {
      this.fileList = fileList
    },
    handleClose(done) {
      this.close()
      done()
    },
    close() {
      this.fileList = []
      this.successedFileListLength = 0
    },
    // 返回上一界面
    resetForm() {
      this.$router.go(-1)
    },
    commitBasicInfo() {
      this.$refs.basicInfoForm.validate(valid => {
        if (valid) {
          var url = ''
          var params = {}
          this.basicInfoForm.imagePath = this.imageNames.join(',')
          this.basicInfoForm.valuess = ''
          for (let i = 0; i < this.basicInfoForm.variables.length; i++) {
            this.basicInfoForm.valuess = this.basicInfoForm.valuess + this.basicInfoForm['value' + i]
            if (i !== this.basicInfoForm.variables.length - 1) {
              this.basicInfoForm.valuess = this.basicInfoForm.valuess + ','
            }
          }
          this.basicInfoForm.variables = this.basicInfoForm.variables.join(',')
          if (this.jump) {
            url = 'commit_work_detail'
            params = {
              sheetId: this.sheetId,
              object: this.basicInfoForm
            }
          } else {
            url = 'create_work_detail'
            params = this.basicInfoForm
          }
          var vm = this
          app.postData(url, params).then(data => {
            vm.handleMsg(data)
          })
        }
      })
    },
    handleMsg(data) {
      if (data.code === 0) {
        this.$message({
          message: data.msg,
          type: 'success'
        })
        this.resetForm()
      } else {
        this.$message({
          message: data.msg,
          type: 'error'
        })
      }
    }
  }
}

</script>

<style>

.sheet-block{
  color: white;
  background-color: #242640;
  margin-top: 5px;
  border:1px solid #fff;
  border-radius: 5px;
}
.sheet-block .el-input {
  width:200px;
  color: white;background-color: #242640;
}

</style>
