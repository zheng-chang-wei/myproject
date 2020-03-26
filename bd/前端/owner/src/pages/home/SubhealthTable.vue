<template>
  <div id="subhealth-list">
    <el-row class="subhealth-panel home-card">
      <div class="card-border card-border-top-left" />
      <div class="card-border card-border-top-right" />
      <div class="card-border card-border-bottom-left" />
      <div class="card-border card-border-bottom-right" />
      <div class="card-panel">
        <div class="card-title">状态预警</div>
        <el-table ref="table" :data="subhealthList" :height="(tableHeight-30)+'px'"
          :header-cell-style="{background:'#0f1741',color:'#fff',padding:'1px',fontSize:'12px'}"
          :row-style="{background:'#394761',color:'#fff',fontSize:'10px'}" :cell-style="{padding:'1px',borderColor:'#0f1741'}"
          class="fault-table" width="100%" highlight-current-row @row-click="rowClick">
          <el-table-column prop="faultContent" label="预警描述" align="center" />
          <el-table-column prop="faultTime" label="发生时间" align="center" />
          <el-table-column label="详情" align="center">
            <template slot-scope="scope">
              <el-button :type="scope.row.selected?'success':'primary'" icon="el-icon-info"
                :disabled="scope.row.selected" @click.stop="handleActive(scope.$index, scope.row)" size="mini" />
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-row>
    <el-dialog class="fault-suggestion" :visible.sync="detailVisible">
      <template slot="title">
        <span class="dialog-title">处理建议</span>
      </template>
      <suggestion-table :select="select" :item="'预警'" />
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
    subhealthList: {
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
    this.$bus.$on('setSubhealthRowSelection', () => {
      this.$refs.table.setCurrentRow(this.subhealthList[0])
      this.rowClick(this.subhealthList[0])
    })
  },
  destroyed () {
    this.$bus.$off('setSubhealthRowSelection')
  },
  methods: {
    rowClick (row) {
      let times = []
      app.get('subhealthData', {id: row.id}).then(data => {
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
          this.$bus.$emit('drawSubhealthLine', faultLineData)
        }
      })
    },
    handleActive (index, rows) {
      this.select = rows
      this.detailVisible = true
    }
  }
}

</script>
<style scoped>
  #subhealth-list {
    background-color: #0f1741;
  }

  .subhealth-panel {
    height: 100%;
    margin: 2px 2px 0px 0px
  }

  .subhealth-panel .card-panel {
    height: 100%;
    position: relative;
    overflow: hidden;
  }

  .subhealth-panel .card-title {
    color: #fff;
    text-align: center;
    background-color: #FF9900;
    font-weight: bold;
  }

</style>
