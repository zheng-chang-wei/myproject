<template>
  <section id="addInfor" class="app-container">
    <el-card style="width: 100%;color: white;background-color: #242640;">
      <el-form label-position="left">
        <el-form-item class="form-label" label="处理建议">
          {{ treatmentSuggestion }}
        </el-form-item>
        <el-form-item class="form-label" label="检修建议">
          {{ repairSuggestion }}
        </el-form-item>
      </el-form>
    </el-card>
    <div v-if="disableEdit&&isDisplayReject" style="text-align:center;">
      <el-button style="margin-top:10px" @click="subWorkStep()">{{ dialogTitle }}</el-button>
      <!-- 质量调查没有驳回 -->
      <el-button v-if="!isQualityAudit" @click="reject()">驳回</el-button>
    </div>
    <!-- 折叠面板-->
    <el-collapse style="margin-top:10px;color: white;background-color: #242640;" :value="activeName">
      <template v-for="(item,index) in stepList">
        <reject v-if="item.result==='驳回'||item.result==='申诉'||item.result==='问题复现'" :key="index" :name="index" :form-data="item.form" />
        <!-- 基本信息 -->
        <basicInfo
          v-else-if="item.type==='创建工单'"
          :key="index"
          :name="index"
          :jump="jump"
          :sheet-id="sheetId"
          :detail-id="detailId"
          :basic-info-form="item.form"
          :disable-edit="disableEdit"
        />
        <!-- 售后审核 -->
        <afterSaleAudit v-else-if="item.type==='售后审核'" :key="index" :name="index" :form-data="item.form" />
        <!-- 质量调查 -->
        <qualitySurvey v-else-if="item.type==='质量调查'" :key="index" :name="index" :form-data="item.form" />
        <!-- 质量审核 -->
        <qualityAudit v-else-if="item.type==='质量审核'" :key="index" :name="index" :form-data="item.form" />
        <!-- 问题解决 -->
        <problemSolving v-else-if="item.type==='问题解决'" :key="index" :name="index" :form-data="item.form" />
        <!-- 跟踪 -->
        <problemTrack v-else-if="item.type==='跟踪'" :key="index" :name="index" :form-data="item.form" />
      </template>
    </el-collapse>

    <el-dialog title="驳回" :visible.sync="rejectDialogVisible" :close-on-click-modal="false">
      <el-form ref="rejectForm" :model="rejectForm" :rules="rejectFormRules" size="mini" label-width="100px" style="margin-left:5%;">
        <el-form-item label="驳回原因" prop="description">
          <el-input v-model="rejectForm.description" style="width:90%;" type="textarea" :autosize="{ minRows: 4, maxRows: 8}" placeholder="请输入驳回原因" />
        </el-form-item>
      </el-form>
      <div style="text-align:center;margin-top:10px">
        <el-button @click="rejectSubmit()">提交</el-button>
      </div>
    </el-dialog>
    <!-- <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :before-close="handleClose" :close-on-click-modal="false"> -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false">
      <!--售后审核-->
      <template v-if="dialogConfigs.afterSaleAuditDialogVisible">
        <el-form
          ref="afterSaleAuditForm"
          :model="afterSaleAuditForm"
          :rules="afterSaleAuditFormRules"
          size="mini"
          label-width="110px"
          style="margin-left:5%;"
        >
          <el-form-item label="故障审核人" prop="auditor">
            <el-input v-model="afterSaleAuditForm.auditor" auto-complete="off" placeholder="请输入名称" />
          </el-form-item>
          <el-form-item label="日期" prop="date">
            <el-date-picker
              v-model="afterSaleAuditForm.date"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择日期时间"
              style="width:90%;"
              :editable="false"
            />
          </el-form-item>
          <el-form-item label="故障件图号" prop="faultNo">
            <el-input v-model="afterSaleAuditForm.faultNo" placeholder="请输入图号" />
          </el-form-item>
          <el-form-item label="需求配件图号" prop="spareNo">
            <el-input v-model="afterSaleAuditForm.spareNo" placeholder="请输入图号" />
          </el-form-item>
          <el-form-item label="审核人意见" prop="description">
            <el-input
              v-model="afterSaleAuditForm.description"
              style="width:90%;"
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 8}"
              placeholder="请输入故障信息、发生地点、时间"
            />
          </el-form-item>
          <el-form-item label="是否计入统计" prop="statistics">
            <el-select v-model="afterSaleAuditForm.statistics" placeholder="请选择">
              <el-option label="是" value="true" />
              <el-option label="否" value="false" />
            </el-select>
          </el-form-item>
        </el-form>
        <div style="text-align:center;margin-top:10px">
          <el-button @click="submit(afterSaleAuditForm,0,false)">提交</el-button>
        </div>
      </template>
      <!--质量调查-->
      <div v-if="dialogConfigs.qualitySurveyDialogVisible">
        <el-form
          ref="qualitySurveyForm"
          :model="qualitySurveyForm"
          :rules="qualitySurveyFormRules"
          size="mini"
          label-width="150px"
          style="margin-left:5%;"
        >
          <el-form-item label="质量问题调查结果" prop="investResult">
            <el-input
              v-model="qualitySurveyForm.investResult"
              style="width:90%;"
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 8}"
              placeholder="请输入质量问题调查结果"
            />
          </el-form-item>
          <el-form-item label="质量问题的责任部门" prop="departments">
            <el-checkbox-group v-model="qualitySurveyForm.departments">
              <el-checkbox v-for="item in departmentList" :key="item.value" :label="item.label">
                {{ item.label }}
              </el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="处理意见" prop="suggestion">
            <el-input
              v-model="qualitySurveyForm.suggestion"
              style="width:90%;"
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 8}"
              placeholder="处理意见"
            />
          </el-form-item>
          <!-- <el-form-item label="编制" prop="organization">
						<el-input style="width:90%;" v-model="qualitySurveyForm.organization" placeholder="请输入编制"></el-input>
					</el-form-item>
					<el-form-item label="日期" prop="auditTime">
						<el-date-picker style="width:90%;" v-model="qualitySurveyForm.auditTime" type="datetime"
							value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间" :editable="false"></el-date-picker>
					</el-form-item> -->
        </el-form>
        <div style="text-align:center;margin-top:10px">
          <el-button @click="submit(qualitySurveyForm,1,false)">提交</el-button>
        </div>
      </div>
      <!--质量审核-->
      <div v-if="dialogConfigs.qualityAuditDialogVisible">
        <el-form
          ref="qualityAuditForm"
          :model="qualityAuditForm"
          :rules="qualityAuditFormRules"
          size="mini"
          label-width="140px"
          style="margin-left:5%;"
        >

          <el-form-item label="质量审核人意见" prop="description">
            <el-input
              v-model="qualityAuditForm.description"
              style="width:90%;"
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 8}"
              placeholder="请输入质量审核人意见"
            />
          </el-form-item>
          <!-- <el-form-item label="编制" prop="organization">
						<el-input v-model="qualityAuditForm.organization" placeholder="请输入编制"></el-input>
					</el-form-item>
					<el-form-item label="日期" prop="auditTime">
						<el-date-picker v-model="qualityAuditForm.auditTime" type="datetime"
							value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间" :editable="false"></el-date-picker>
					</el-form-item> -->
        </el-form>
        <div style="text-align:center;margin-top:10px">
          <el-button @click="submit(qualityAuditForm,2,false)">提交</el-button>
        </div>
      </div>
      <!--问题解决-->
      <div v-if="dialogConfigs.problemSolvingDialogVisible">
        <el-form
          ref="problemSolvingForm"
          :model="problemSolvingForm"
          :rules="problemSolvingFormRules"
          size="mini"
          label-width="130px"
          style="margin-left:5%;"
        >
          <el-form-item label="责任部门处理过程描述及结果" prop="description">
            <el-input
              v-model="problemSolvingForm.description"
              style="width:90%;"
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 8}"
              placeholder="请输入责任部门处理过程描述及结果"
            />
          </el-form-item>
          <!-- <el-form-item label="申述" prop="isAppeal">
						<el-select v-model="problemSolvingForm.isAppeal" placeholder="请选择">
							<el-option label="是" value="是"></el-option>
							<el-option label="否" value="否"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="申诉原因" prop="appealReason">
						<el-input style="width:90%;" type="textarea" :autosize="{ minRows: 4, maxRows: 8}"
							v-model="problemSolvingForm.appealReason" placeholder="请输入申诉原因"></el-input>
					</el-form-item>
					<el-form-item label="编制" prop="organization">
						<el-input v-model="problemSolvingForm.organization" placeholder="请输入编制"></el-input>
					</el-form-item>
					<el-form-item label="日期" prop="auditTime">
						<el-date-picker v-model="problemSolvingForm.auditTime" type="datetime"
							value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间" :editable="false"></el-date-picker>
					</el-form-item> -->
        </el-form>
        <div style="text-align:center;margin-top:10px">
          <el-button @click="submit(problemSolvingForm,3,false)">提交</el-button>
        </div>
      </div>
      <!--跟踪-->
      <div v-if="dialogConfigs.problemTrackDialogVisible">
        <el-form ref="problemTrackForm" :model="problemTrackForm" :rules="problemTrackFormRules" size="mini">
          <el-form-item label="最终处理结果的跟踪与验证" prop="description">
            <el-input v-model="problemTrackForm.description" type="textarea" :autosize="{ minRows: 4, maxRows: 8}" placeholder="请输入最终处理结果的跟踪与验证" />
          </el-form-item>
          <!-- <el-form-item label="完成故障维修的最终时间" prop="endTime">
								<el-date-picker style="width:100%;" v-model="problemTrackForm.endTime" type="datetime"
									value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间" :editable="false">
								</el-date-picker>
							</el-form-item>
							<el-row>签名</el-row>
							<el-form-item prop="memberName">
								<el-input style="width:100%;" v-model="problemTrackForm.memberName" placeholder="请输入名称">
								</el-input>
							</el-form-item>
							<el-row>日期</el-row>
							<el-form-item prop="auditTime">
								<el-date-picker style="width:100%;" v-model="problemTrackForm.auditTime" type="datetime"
									value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间" :editable="false">
								</el-date-picker>
							</el-form-item> -->
        </el-form>
        <div style="text-align:center;margin-top:10px">
          <el-button @click="submit(problemTrackForm,4,false)">提交</el-button>
        </div>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import app from '@/common/js/app'
import basicInfo from '@/components/maintenance/basicInfo.vue'
import afterSaleAudit from '@/components/maintenance/afterSaleAudit.vue'
import qualitySurvey from '@/components/maintenance/qualitySurvey.vue'
import qualityAudit from '@/components/maintenance/qualityAudit.vue'
import problemSolving from '@/components/maintenance/problemSolving.vue'
import problemTrack from '@/components/maintenance/problemTrack.vue'
import reject from '@/components/maintenance/reject.vue'
// 流程与dialog的映射关系
const STEP_MAP_DIALOG = {
  售后审核: 'afterSaleAuditDialogVisible',
  质量调查: 'qualitySurveyDialogVisible',
  质量审核: 'qualityAuditDialogVisible',
  问题解决: 'problemSolvingDialogVisible',
  跟踪: 'problemTrackDialogVisible'
}
const STEPS = ['售后审核', '质量调查', '质量审核', '问题解决', '跟踪']
export default {
  components: {
    basicInfo,
    afterSaleAudit,
    qualitySurvey,
    qualityAudit,
    problemSolving,
    problemTrack,
    reject
  },
  data() {
    return {
      activeName: '',
      stepList: [],
      // flag为false表示工单被驳回到“创建工单”流程，各流程只能查看不能做其他操作
      isDisplayReject: true,
      isQualityAudit: false,
      disableEdit: true,
      // 弹框头部信息
      dialogTitle: '',
      dialogVisible: false,
      rejectDialogVisible: false,
      rejectForm: {},
      // dialogForm
      dialogConfigs: {
        afterSaleAuditDialogVisible: false,
        qualitySurveyDialogVisible: false,
        qualityAuditDialogVisible: false,
        problemSolvingDialogVisible: false,
        problemTrackDialogVisible: false
      },
      // 基本信息form
      basicInfoForm: {},
      // 售后审核form
      afterSaleAuditForm: {},
      // 质量调查form
      qualitySurveyForm: {
        departments: []
      },
      departmentList: [],
      // 质量审核form
      qualityAuditForm: {},
      // 问题解决form、
      problemSolvingForm: {
        description: '',
        isAppeal: '否',
        description1: '',
        organization: '',
        auditTime: ''
      },
      // 追踪form
      problemTrackForm: {
        lastInfor: '',
        endTime: '',
        memberName: '',
        auditTime: ''
      },
      treatmentSuggestion: '',
      repairSuggestion: '',
      rejectFormRules: {
        description: [{
          required: true,
          message: '请输入驳回原因',
          trigger: 'change'
        }]
      },
      afterSaleAuditFormRules: {
        auditor: [{
          required: true,
          message: '请输入故障审核人',
          trigger: 'change'
        }],
        date: [{
          required: true,
          message: '请选择日期',
          trigger: 'change'
        }],
        faultNo: [{
          required: true,
          message: '请输入故障件图号',
          trigger: 'change'
        }],
        spareNo: [{
          required: true,
          message: '请输入需求配件图号',
          trigger: 'change'
        }],
        description: [{
          required: true,
          message: '请输入审核人意见',
          trigger: 'change'
        }],
        statistics: [{
          required: true,
          message: '请选择是否计入统计',
          trigger: 'change'
        }]
      },
      qualitySurveyFormRules: {
        investResult: [{
          required: true,
          message: '请输入质量问题调查结果',
          trigger: 'change'
        }],
        departments: [{
          required: true,
          message: '请选择质量问题的责任部门',
          trigger: 'change'
        }],
        suggestion: [{
          required: true,
          message: '请输入处理意见',
          trigger: 'change'
        }]
      },
      qualityAuditFormRules: {
        description: [{
          required: true,
          message: '请输入质量审核人意见',
          trigger: 'change'
        }]
      },
      problemSolvingFormRules: {
        description: [{
          required: true,
          message: '请输入责任部门处理过程描述及结果',
          trigger: 'change'
        }]
      },
      problemTrackFormRules: {
        description: [{
          required: true,
          message: '请输入最终处理结果的跟踪与验证',
          trigger: 'change'
        }]
      }
    }
  },
  computed: {
    sheetId: function() {
      return this.$route.query.sheetID
    },
    detailId: function() {
      return this.$route.query.detailID
    },
    state: function() {
      return this.$route.query.state
    },
    // 是否是点击维修履历表格中数据跳转过来
    jump: function() {
      if (typeof (this.$route.query.jump) === 'string') {
        return this.$route.query.jump === 'true'
      }
      return this.$route.query.jump
    }
  },
  mounted() {
    this.initDisableEdit()
    this.initDialogTitle()
    this.getAllWorkStepsById()
  },
  methods: {
    initDisableEdit() {
      this.disableEdit = this.jump
      this.stepList = []
      if (!this.jump) {
        this.stepList.push({
          form: {
            needParts: true,
            needReport: true,
            temporary: '更换',
            repairTime: 1
          },
          type: '创建工单'
        })
      }
    },
    // 通过sheetid获取worksteps
    getAllWorkStepsById() {
      if (this.sheetId == null) {
        this.activeName = 0
        return
      }
      const params = {
        sheetId: this.sheetId
      }
      app.get('get_worksheet_by_id', params).then(response => {
        this.stepList = []
        if (response.code === 0) {
          const data = response.data
          if (data.suggestion !== null) {
            this.treatmentSuggestion = data.suggestion.treatment
            this.repairSuggestion = data.suggestion.repair
          }
          var stepDatas = data.steps
          if (data.sheet.state === '创建工单') {
            if (stepDatas.length === 1) {
              this.disableEdit = false
            } else {
              this.isDisplayReject = false
            }
          } else if (data.sheet.state === '关闭') {
            this.isDisplayReject = false
          } else if (data.sheet.state === '质量调查') {
            this.isQualityAudit = true
          }

          this.basicInfoForm = data.sheet.detail
          for (var i = 0; i < stepDatas.length; i++) {
            // 工单信息
            if (i === 0) {
              this.stepList.push({
                form: this.basicInfoForm,
                type: stepDatas[i].type
              })
            } else {
              // 驳回信息
              if (stepDatas[i].result === '驳回' || stepDatas[i].result === '申诉' || stepDatas[i].result === '问题复现') {
                var description = ''
                if (stepDatas[i].content) {
                  description = JSON.parse(stepDatas[i].content).description
                }
                this.stepList.push({
                  form: {
                    auditor: stepDatas[i].auditor,
                    date: stepDatas[i].endTime,
                    description: description
                  },
                  result: stepDatas[i].result
                })
              } else if (stepDatas[i].content) {
                var form = JSON.parse(stepDatas[i].content)
                form.date = stepDatas[i].endTime
                this.stepList.push({
                  form: form,
                  type: stepDatas[i].type,
                  result: stepDatas[i].result
                })
              }
            }
          }
        }
        this.stepList.reverse()
        // 激活最后一条
        this.activeName = 0

        params.state = response.data.sheet.state
        app.get('get_workStepContent', params).then(data => {
          if (data.code === 0) {
            if (data.data !== null) {
              const content = data.data.data
              switch (params.state) {
                case STEPS[0]: // 售后审核

                  break
                case STEPS[1]: // 质量调查
                  this.qualitySurveyForm = content
                  break
                case STEPS[2]: // 质量审核
                  this.qualityAuditForm = content
                  break
                case STEPS[3]: // 问题解决
                  this.problemSolvingForm = content
                  break
                case STEPS[4]: // 跟踪
                  this.problemTrackForm = content
                  break

                default:
                  break
              }
            }
          }
        })
      })
    },
    // 打开对话框
    subWorkStep() {
      this.dialogVisible = true
      if (this.dialogConfigs.qualitySurveyDialogVisible) {
        this.findAllDepts()
      }
    },
    findAllDepts() {
      const vm = this
      app.get('get_dept_list').then(data => {
        if (data) {
          if (data.msg && data.msg.length !== 0) {
            vm.departmentList = []
            data.msg.forEach(item => {
              vm.departmentList.push({
                value: item.deptId,
                label: item.deptName
              })
            })
          }
        }
      })
    },
    initDialogTitle() {
      var index = STEPS.indexOf(this.state)
      if (index < 5) {
        this.dialogTitle = STEPS[index]
        this.dialogConfigs[STEP_MAP_DIALOG[STEPS[index]]] = true
      }
    },
    // 返回上一界面
    resetForm() {
      this.$router.go(-1)
    },
    // 驳回
    reject() {
      this.rejectDialogVisible = true
      this.rejectForm = {}
    },
    rejectSubmit() {
      if (this.dialogConfigs.afterSaleAuditDialogVisible) {
        this.submit(this.rejectForm, 0, true)
      } else if (this.dialogConfigs.qualitySurveyDialogVisible) {
        this.submit(this.rejectForm, 1, true)
      } else if (this.dialogConfigs.qualityAuditDialogVisible) {
        this.submit(this.rejectForm, 2, true)
      } else if (this.dialogConfigs.problemSolvingDialogVisible) {
        this.submit(this.rejectForm, 3, true)
      } else if (this.dialogConfigs.problemTrackDialogVisible) {
        this.submit(this.rejectForm, 4, true)
      }
    },
    // isReject 表示是否驳回
    submit(form, index, isReject) {
      const params = {
        sheetId: this.sheetId,
        result: !isReject,
        object: form
      }
      var url = ''
      switch (index) {
        // 售后
        case 0:
          url = 'submit_after_sale'
          if (isReject) {
            form = this.$refs.rejectForm
          } else {
            form = this.$refs.afterSaleAuditForm
          }
          break
          // 质量调查
        case 1:
          url = 'submit_quality_invest'
          if (isReject) {
            form = this.$refs.rejectForm
          } else {
            form = this.$refs.qualitySurveyForm
          }
          break
          // 质量审核
        case 2:
          url = 'submit_quality_examine'
          if (isReject) {
            form = this.$refs.rejectForm
          } else {
            form = this.$refs.qualityAuditForm
          }
          break
          // 问题解决
        case 3:
          if (isReject) {
            form = this.$refs.rejectForm
            url = 'submit_appeal'
          } else {
            url = 'submit_resolve'
            form = this.$refs.problemSolvingForm
          }
          break
          // 跟踪
        case 4:
          if (isReject) {
            url = 'submit_repetition'
            form = this.$refs.rejectForm
          } else {
            url = 'submit_close'
            form = this.$refs.problemTrackForm
          }
          break
        default:
          break
      }
      form.validate(valid => {
        if (valid) {
          app.postData(url, params).then(data => {
            this.handleMsg(data)
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
        this.dialogVisible = false
        this.rejectDialogVisible = false
        this.resetForm()
      } else {
        this.$message({
          message: data.msg,
          type: 'error'
        })
      }
    },
    // 弹窗关闭提醒
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 5张图片，本次选择了 ${
          files.length
        } 个文件，共选择了 ${files.length + fileList.length} 个文件`
      )
    },
    handleSuccess(res, file) {
      this.form.imagePath += file.response.data + ','
    },
    // 上传文件的路径
    uploadUrl() {
      return app.getUrl('upload_file')
    },
    // 删除上传的图片
    deleteFile(file, fileList) {
      const param = {
        filepaths: file.response.data
      }
      app.post('delete_file_byFilepaths', param).then(data => {
        if (data.msg) {
          this.form.imagePath.replace(file.response.data + ',', '')
          this.$message({
            showClose: true,
            message: '删除图片成功',
            type: 'success'
          })
        }
      })
    }
  }
}

</script>

<style>
	#addInfor {
    background-color: #242640;
    padding-top: 5px;
		/* margin-left: 12px; */
		/* margin-right: 12px; */

		/* border: 1px solid #e8e8e8; */
  }
  #addInfor .el-collapse-item__header{
    color: white;
    background-color: #242640;
  }
  #addInfor .el-collapse-item__wrap{
    color: white;
    background-color: #242640;
  }
  #addInfor .el-radio__label{
    color: white;
  }
  #addInfor .el-upload-list__item-name{
    color: white;
  }
  #addInfor .el-icon-document{
    color: white;
  }

  .form-label .el-form-item__label{
    color: white;
  }

	.el-dialog .el-input {
		width: 90%;
	}

	.el-dialog .el-select {
		width: 100%;
	}

</style>
