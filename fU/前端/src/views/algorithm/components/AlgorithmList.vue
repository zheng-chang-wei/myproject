<template>
  <div class="algorithm-list">
    <div class="title" style="margin-bottom:1px">算法列表</div>
    <div>
      <el-table :data="algorithms" border :height="tableMaxHeight">
        <el-table-column prop="data.code" label="算法编号" align="center" sortable />
        <el-table-column prop="data.name" label="算法名称" align="center" />
        <el-table-column
          prop="data.subsystem"
          label="算法分组"
          align="center"
          :filters="systems"
          :filter-method="filterSystem"
          filter-placement="bottom-end"
        />
        <el-table-column
          prop="data.board"
          label="运行板卡"
          align="center"
          :filters="boards"
          :filter-method="filterBoard"
          filter-placement="bottom-end"
        />
        <el-table-column
          prop="data.enable"
          label="使能设置"
          align="center"
          :filters="enables"
          :filter-method="filterEnable"
          filter-placement="bottom-end"
        >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.data.enable" type="success" effect="dark" size="small">ON</el-tag>
            <el-tag v-else type="info" effect="dark" size="small">OFF</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="data.status" label="运行状态" align="center" :filters="status" :filter-method="filterStatus" sortable :sort-method="sortStatus" filter-placement="bottom-end">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.data.status==='Run'" effect="dark" type="success" size="small">运行</el-tag>
            <el-tag v-else-if="scope.row.data.status==='Err'" effect="dark" type="danger" size="small">异常</el-tag>
            <el-tag v-else effect="dark" type="info" size="small">停止</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
import app from '@/common/js/app'
export default {
  props: {
    algorithms: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      systems: [],
      enables: [{
        text: 'ON',
        value: true
      }, {
        text: 'OFF',
        value: false
      }],
      status: [
        {
          text: '运行',
          value: 'Run'
        },
        {
          text: '停止',
          value: 'Stop'
        }, {
          text: '异常',
          value: 'Err'
        }
      ],
      boards: [],
      sortMap: {
        'Err': 3,
        'Run': 2,
        'Stop': 1
      },
      tableMaxHeight: '400px'
    }
  },
  created() {
    this.changeHeight()
  },
  mounted() {
    window.addEventListener('resize', this.changeHeight)
    this.getSystems()
    this.getPhmBoards()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeHeight)
  },
  methods: {
    changeHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 110 + 'px'
    },
    getSystems() {
      this.systems = []
      app.get('subsystem_listSubsystem_by_parms').then(res => {
        if (res.code === 0) {
          res.data.forEach(t => {
            this.systems.push({
              text: t.name,
              value: t.name
            })
          })
        }
      })
    },
    getPhmBoards() {
      this.boards = []
      app.get('algorithm_phm_board').then(res => {
        if (res.code === 0) {
          res.data.forEach(t => {
            this.boards.push({
              text: t,
              value: t
            })
          })
        }
      })
    },
    filterBoard(value, row) {
      return row.data.board === value
    },
    filterSystem(value, row) {
      return row.data.subsystem === value
    },
    filterEnable(value, row) {
      return row.data.enable === value
    },
    filterStatus(value, row) {
      return row.data.status === value
    },
    sortStatus(a, b) {
      const index1 = this.sortMap[a.data.status]
      const index2 = this.sortMap[b.data.status]
      return index1 - index2
    }
  }
}
</script>
<style>
.algorithm-list{
  margin: 10px;
}
</style>

