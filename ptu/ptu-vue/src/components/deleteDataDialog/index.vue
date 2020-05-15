<template>
  <!--删除界面-->
  <el-dialog title="删除数据【提交&删除操作无法撤销，删除前请确认】" :visible.sync="deleteFormVisible" :close-on-click-modal="false">
    <el-form
      ref="deleteForm"
      :model="deleteForm"
      :rules="deleteFormRules"
      label-width="80px"
      style="margin-left:5%;"
    >
      <el-form-item prop="deadline" label="截止日期">
        <el-date-picker
          v-model="deleteForm.deadline"
          placeholder="请选择要删除的截止日期"
          value-format="yyyy-MM-dd"
          :editable="false"
          style="width:50%"
        />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="cancel" @click.native="deleteFormVisible = false">取消
      </el-button>
      <el-button type="primary" :loading="deleteLoading" @click.native="deleteBtnChecked">
        提交&删除
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
import app from '@/common/js/app'
export default {
  props: {
    selecteds: {
      type: Array,
      required: true
    }
  },
  data() {
    const checkDeadline = (rule, value, callback) => {
      let minTime = this.selecteds[0].startTime
      for (let i = 0; i < this.selecteds.length; i++) {
        if (minTime < this.selecteds[i].startTime) {
          minTime = this.selecteds[i].startTime
        }
      }
      if (value < minTime) {
        callback(new Error('请选择晚于所有起始时间的日期'))
      } else {
        callback()
      }
    }
    return {
      deleteFormVisible: false,
      deleteLoading: false,
      deleteForm: {
        deadline: ''
      },
      deleteFormRules: {
        deadline: [
          {
            required: true,
            message: '必填',
            trigger: 'change'
          },
          {
            validator: checkDeadline,
            trigger: 'change'
          }
        ]
      },
      deleteCount: 0, // 要删除数据的个数
      deleteSuccessedCount: 0 // 已经删除数据的个数
    }
  },
  created() {
    this.$bus.$on('deleteData', () => {
      this.deleteSuccessedCount++
      if (this.deleteSuccessedCount === this.deleteCount) {
        this.deleteLoading = false
        this.deleteFormVisible = false
        this.$message({
          message: '删除成功',
          type: 'success'
        })
        this.$emit('deleteSuccess')
      }
    })
  },
  mounted() {
  },
  destroyed() {
    this.$bus.$off('deleteData')
  },
  methods: {
    openDeleteDialog(index, row) {
      if (this.selecteds.length === 0) {
        this.$message({
          message: '请至少勾选一条数据删除',
          type: 'error'
        })
        return
      }
      this.deleteFormVisible = true
    },
    deleteBtnChecked() {
      this.$refs.deleteForm.validate(valid => {
        if (valid) {
          this.deleteLoading = true
          this.deleteSuccessedCount = 0
          this.deleteCount = this.selecteds.length
          for (let i = 0; i < this.selecteds.length; i++) {
            if (this.deleteForm.deadline > this.selecteds[i].endTime) {
              const parm = {
                type: this.selecteds[i].type
              }
              this.handleDelete('dropTable', parm)
            } else {
              const parm = {
                type: this.selecteds[i].type,
                deadline: this.deleteForm.deadline
              }
              this.handleDelete('delete', parm)
            }
          }
        }
      })
    },
    handleDelete(url, parm) {
      app.post(url, parm).then(response => {
      }).catch(response => {
        console.log(response)
        this.deleteLoading = false
      })
    }

  }
}
</script>
