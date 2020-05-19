<template>
  <el-form ref="queryForm" :model="retrieveForm" :inline="true" size="mini" :rules="queryFormRules">
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
      <el-button type="primary" icon="el-icon-search" @click="getDatas">查询</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="exportExcel">导出数据</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import util from '@/common/js/util'
export default {
  components: {
  },
  props: {
    tableDatas: {
      type: Array,
      default: null
    },
    jsonFields: {
      type: Object,
      default: null
    },
    title: {
      type: String,
      default: null
    },
    type: {
      type: String,
      default: null
    },
    cols: {
      type: Array,
      default: null
    }
  },
  data() {
    const checkTime = (rule, value, callback) => {
      const sTime = new Date(value[0]).getTime()
      const eTime = new Date(value[1]).getTime()
      const total = (eTime - sTime) / 1000
      const day = parseFloat(total / (60 * 60))// 计算整数天数
      if (sTime !== 0 && eTime !== 0) {
        if (day > 24 * 10) {
          callback(new Error('时间跨度不能超过10天'))
        } else {
          callback()
        }
      } else {
        callback()
      }
    }
    return {
      retrieveForm: { time: '' },
      queryFormRules: {
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
      },
      excelName: ''
    }
  },
  created() {
    this.$bus.$on('timeChange', (data) => {
      this.retrieveForm.time = data
    })
  },
  destroyed() {
    this.$bus.$off('timeChange')
  },
  mounted() {
    const time = localStorage.getItem('time')
    if (time !== undefined && time !== null) {
      this.retrieveForm.time = time.split(',')
    }
  },
  methods: {
    getDatas() {
      this.$refs.queryForm.validate(valid => {
        if (valid) {
          this.$emit('getDatas', this.retrieveForm.time)
        }
      })
    },
    exportExcel() {
      this.excelName = this.type + '数据总览' + util.replaceTime(this.retrieveForm.time[0]) + ' - ' + util.replaceTime(this.retrieveForm.time[1]) + '.xlsx'
      const keys = Object.keys(this.jsonFields)
      const head = [this.title]
      for (let index = 0; index < keys.length - 1; index++) {
        head.push(null)
      }
      const excelData = [head]
      excelData.push(keys)
      this.tableDatas.forEach(element => {
        const arr = []
        Object.values(this.jsonFields).forEach(function(value, key) {
          arr.push(element[value])
        })
        excelData.push(arr)
      })
      util.exportSpecialExcel(excelData, keys.length - 1, this.excelName, this.cols)
    },
    timeChange() {
      this.$bus.$emit('timeChange', this.retrieveForm.time)
      localStorage.setItem('time', this.retrieveForm.time)
    }

  }
}
</script>

