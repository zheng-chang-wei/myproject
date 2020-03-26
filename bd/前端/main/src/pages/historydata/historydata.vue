<template>
  <div>
    <div class="detail-chart">
      <el-row :gutter="10">
        <el-col :span="12">
          <analog-line :data="datas" />
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
    datas: {
      type: Object,
      default: null
    },
    record: {
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
          project: this.record.project,
          train: this.record.train,
          carriageId: this.record.carriageId,
          doorId: this.record.doorId,
          timestamp: this.record.timestamp,
          variables: variables.join(',')
        }
        app.get('history_data', params).then(data => {
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
