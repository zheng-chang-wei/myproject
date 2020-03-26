<template>
  <div id="fault-list">
    <el-row class="fault-panel home-card">
      <div class="card-border card-border-top-left" />
      <div class="card-border card-border-top-right" />
      <div class="card-border card-border-bottom-left" />
      <div class="card-border card-border-bottom-right" />
      <div class="card-panel">
        <div class="card-title">故障告警</div>
        <el-table :data="faultList" ref="table"
          :header-cell-style="{background:'#0f1741',color:'#fff',padding:'1px',fontSize:'12px'}"
          :row-style="{background:'#394761',color:'#fff',fontSize:'10px'}" :cell-style="{padding:'1px',borderColor:'#0f1741',fontSize:'10px'}"
          width="100%" :height="(tableHeight-30)+ 'px'" highlight-current-row @row-click="rowClick">
          <el-table-column prop="faultContent" label="故障描述" align="center" />
          <el-table-column prop="faultTime" label="发生时间" align="center" />
          <el-table-column label="详情" align="center" >
            <template slot-scope="scope">
              <el-button
                :type="scope.row.selected?'success':'primary'"
                icon="el-icon-info"
                :disabled="scope.row.selected"
                @click.stop="showDetails(scope.$index, scope.row)"
                size="mini"
              />
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-row>
    <el-dialog class="fault-suggestion" :visible.sync="detailVisible">
      <template slot="title">
        <span class="dialog-title">处理建议</span>
      </template>
      <suggestion-table :select="select" :item="'故障'" />
    </el-dialog>
  </div>
</template>
<script>
import SuggestionTable from './SuggestionTable'
import app from '@/common/js/app'
export default {
  components: {
    SuggestionTable
  },
  props: {
    faultList: {
      type: Array,
      default: null
    },
    tableHeight: {
      type: Number,
      default: null
    }
  },
  data () {
    return {
      detailVisible: false,
      select: {}
    }
  },
  mounted () {
    this.$bus.$on('setFaultFirstRowSelection', () => {
      this.$refs.table.setCurrentRow(this.faultList[0])
      this.rowClick(this.faultList[0])
    })
  },
  destroyed () {
    this.$bus.$off('setFaultFirstRowSelection')
  },
  methods: {
    rowClick (row) {
      let times = []
      app.get('faultData', {id: row.id}).then(data => {
        if (data.code === 0) {
          let msg = data.msg
          let datas = msg.datas
          let keys = msg.keys
          let values = []
          for (let index = 0; index < keys.length; index++) {
            values.push([])
          }
          for (let index = 0; index < datas.length; index++) {
            let value = datas[index]
            times.push(value.timestamp)
            for (let i = 0; i < keys.length; i++) {
              values[i].push(value.values[i])
            }
          }
          let faultLineData = {
            keys: keys,
            times: times,
            data: values,
            state: msg.state
          }
          this.$bus.$emit('drawFaultLine', faultLineData)
        }
      })
    },
    showDetails (index, rows) {
      this.select = rows
      this.detailVisible = true
    }
  }
}

</script>

<style scoped>
  #fault-list {
    background-color: #0f1741;
    /* height: 464px; */
  }

  .fault-panel {
    height: 100%;
    margin: 2px 2px 0px 2px
  }

  .fault-panel .card-panel {
    height: 100%;
    position: relative;
    overflow: hidden;
  }

  .fault-panel .card-title {
    color: #fff;
    text-align: center;
    background-color: #FF0010;
    font-weight: bold;
  }

</style>
