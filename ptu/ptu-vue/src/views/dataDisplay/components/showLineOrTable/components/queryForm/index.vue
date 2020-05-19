<template>
  <!--查询-->
  <el-form ref="queryForm" :inline="true" :model="retrieveForm" size="mini" :rules="queryFormRules">
    <el-form-item label="逻辑条件" prop="logicalCondition">
      <el-select v-model="retrieveForm.logicalCondition" style="width:125px" placeholder="请选择逻辑条件">
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
        style="width:325px"
        type="datetimerange"
        value-format="yyyy-MM-dd HH:mm:ss"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        :editable="false"
        @change="timeChange"
      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="queryTableDatas">表格</el-button>
      <el-button type="primary" icon="el-icon-search" @click="queryLineDatas">曲线</el-button>
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
    const checkTime = (rule, value, callback) => {
      const sTime = new Date(value[0]).getTime()
      const eTime = new Date(value[1]).getTime()
      const total = (eTime - sTime) / 1000
      const day = parseFloat(total / (60 * 60))// 计算整数天数
      if (sTime !== 0 && eTime !== 0) {
        if (day > 24) {
          callback(new Error('时间跨度不能超过1天'))
        } else {
          callback()
        }
      } else {
        callback()
      }
    }
    return {
      retrieveForm: {
        logicalCondition: '',
        time: ''
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
          }, {
            validator: checkTime,
            trigger: 'change'
          }
        ]
      }
    }
  },
  created() {
    const time = localStorage.getItem('time')
    if (time !== undefined && time !== null) {
      this.retrieveForm.time = time.split(',')
    }
    this.$bus.$on('timeChange', (data) => {
      this.retrieveForm.time = data
    })
  },
  destroyed() {
    this.$bus.$off('timeChange')
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
    },
    timeChange() {
      this.$bus.$emit('timeChange', this.retrieveForm.time)
      localStorage.setItem('time', this.retrieveForm.time)
    }
  }
}
</script>

<style>
.el-time-panel {
    width: 100%;
}
</style>
