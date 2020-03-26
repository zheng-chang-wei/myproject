<template>
  <div class="detail-part">
    <div class="detail-form">
      <el-row>
        <el-col :span="8">
          <div>日期：{{ fault.faultTime }}</div>
        </el-col>
        <el-col :span="8">
          <div>故障名称：{{ fault.faultName }}</div>
        </el-col>
        <el-col :span="8">
          <div>故障代码：{{ fault.faultCode }}</div>
        </el-col>
      </el-row>
    </div>
    <div class="detail-form">
      <el-row>
        <el-col :span="8">
          <div>项目：{{ fault.project }}</div>
        </el-col>
        <el-col :span="8">
          <div>车辆：{{ fault.trainNo }}</div>
        </el-col>
        <el-col :span="4">
          <div>车厢号：{{ fault.carNo }}</div>
        </el-col>
        <el-col :span="4">
          <div>门地址：{{ fault.doorAddr }}</div>
        </el-col>
      </el-row>
    </div>
    <div class="detail-form">
      <el-row>
        <el-col :span="8">
          <div>故障描述：{{ fault.description }}</div>
        </el-col>
        <el-col :span="8">
          <div>检修指南：{{ fault.repair }}</div>
        </el-col>
        <el-col :span="8">
          <div>解决方案：{{ fault.solution }}</div>
        </el-col>
      </el-row>
    </div>
    <div class="detail-chart">
      <el-row :gutter="10">
        <el-col :span="12">
          <analog-line :data="faultData" />
        </el-col>
        <el-col :span="12">
          <digital-line :datas="lineDatas" @query="showData" />
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import AnalogLine from '@/components/datachart/AnalogLine'
import DigitalLine from '@/components/datachart/DigitalLineGroup'
import app from '@/common/js/app'
export default {
  components: {
    AnalogLine,
    DigitalLine
  },
  props: {
    fault: {
      type: Object,
      default: null
    },
    faultData: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      lineDatas: []
    }
  },
  methods: {
    showData(param) {
      this.lineDatas = []
      if (param.variables && param.variables.length > 0) {
        const variables = []
        for (let i = 0; i < param.variables.length; i++) {
          variables.push(param.variables[i])
        }
        const params = {
          id: this.fault.id,
          variables: variables.join(',')
        }
        console.log(params)
        app.get('get_fault_digital_data', params).then(data => {
          if (data.code === 0) {
            const msg = data.msg
            const keys = msg.keys
            const datas = msg.datas
            for (let i = 0; i < keys.length; i++) {
              const rows = []
              const label = keys[i]
              for (let j = 0; j < datas.length; j++) {
                const row = {}
                row['日期'] = datas[j].timestamp.substr(11)
                row[label] = datas[j].values[i]
                rows.push(row)
              }
              const value = {
                columns: ['日期', label],
                rows: rows
              }
              this.lineDatas.push(value)
            }
            console.log(this.lineDatas)
          }
        })
      }
    }
  }
}
</script>
<style scoped>
 .detail-part .detail-form{
    padding: 5px;
    border-left: 1px solid #ccc;
    border-right: 1px solid #ccc;
    border-bottom: 1px solid #ccc;
    color:#fff;
  }
  .detail-part .detail-form:first-of-type{
    border-top: 1px solid #ccc;
  }
  .detail-part .detail-chart{
    margin-top: 20px;
  }

  .detail-form .el-col{
    padding-left: 2px;
    padding-right: 2px;
  }
</style>

