<template>
  <!--查询-->
  <el-form ref="queryForm" :inline="true" :model="retrieveForm" size="mini" :rules="queryFormRules">
    <el-form-item label="逻辑条件" prop="logicalCondition">
      <el-select v-model="retrieveForm.logicalCondition" placeholder="请选择逻辑条件">
        <el-option
          v-for="item in logicalConditionOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </el-form-item>
    <el-form-item label="日期" prop="time">
      <el-date-picker
        v-model="retrieveForm.time"
        value-format="yyyy-MM-dd"
        placeholder="选择日期"
        :editable="false"
      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="queryTableDatas">查询表格</el-button>
      <el-button type="primary" icon="el-icon-search" @click="queryLineDatas">查询曲线</el-button>
    </el-form-item>
  </el-form>

</template>

<script>
import app from '@/common/js/app'
export default {
  props: {
    type: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      retrieveForm: {
        logicalCondition: '',
        time: '2020-04-08'
      },
      logicalConditionOptions: [],
      queryFormRules: {
        logicalCondition: [
          {
            required: true,
            message: '必填',
            trigger: 'change'
          }
        ],
        time: [
          {
            required: true,
            message: '必填',
            trigger: 'change'
          }
        ]
      }
    }
  },
  created() {
  },
  mounted() {
    this.getCondition()
  },

  methods: {

    getCondition() {
      app.get('listCondition', { type: this.type }).then(response => {
        if (response.code === 0) {
          this.logicalConditionOptions = []
          response.msg.forEach(element => {
            this.logicalConditionOptions.push({
              label: element.conditionName,
              value: element.expression
            })
          })
          if (this.logicalConditionOptions.length !== 0) {
            this.retrieveForm.logicalCondition = this.logicalConditionOptions[0].value
          }
        }
      })
    },
    // 查询
    queryTableDatas() {
      this.$refs.queryForm.validate(valid => {
        if (valid) {
          this.$emit('queryTableDatas', this.retrieveForm)
        }
      })
    },
    queryLineDatas() {
      this.$refs.queryForm.validate(valid => {
        if (valid) {
          this.$emit('queryLineDatas', this.retrieveForm)
        }
      })
    }

  }
}
</script>

<style>

</style>
