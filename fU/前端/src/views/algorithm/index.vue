<template>
  <div class="algorithm-monitor">
    <el-row :gutter="5">
      <el-col :md="6" :xs="24">
        <status-abstract :data="abstract" />
        <status-history :data="history" />
      </el-col>
      <el-col :md="18" :xs="24">
        <algorithm-list :algorithms="algorithms" />
        <!-- 暂时不要分页 -->
        <!-- <el-col :span="24">
          <el-pagination :small="true" style="float:right" :total="total" :current-page="pageNum" :page-size="pageSize" @current-change="handleCurrentChange" />
        </el-col> -->
      </el-col>
    </el-row>
  </div>
</template>
<script>
import StatusAbstract from './components/StatusAbstract'
import StatusHistory from './components/StatusHistory'
import AlgorithmList from './components/AlgorithmList'
import app from '@/common/js/app'
export default {
  components: {
    StatusAbstract,
    StatusHistory,
    AlgorithmList
  },
  data() {
    return {
      history: [],
      abstract: {
        title: '算法',
        running: 0,
        error: 0,
        stopped: 0,
        types: []
      },
      algorithms: [],
      timeout: 0,
      total: 0,
      pageSize: 0,
      pageNum: 0
    }
  },
  created() {
    this.collect()
    this.$bus.$on('launch', (event) => {
      this.getAbstract()
      this.getHistory()
      this.list()
    })
  },
  destroyed() {
    console.log(this.timeout)
    window.clearTimeout(this.timeout)
  },
  methods: {
    collect() {
      this.getAbstract()
      this.getHistory()
      this.list()
      console.log('collect')
      this.timeout = window.setTimeout(this.collect, 10 * 1000)
    },
    getAbstract() {
      app.get('algorithm_abstract').then(res => {
        if (res.code === 0) {
          this.abstract.running = res.data.running
          this.abstract.error = res.data.error
          this.abstract.stopped = res.data.stopped
          this.abstract.types = []
          res.data.subsystems.forEach(subsystem => {
            this.abstract.types.push({
              type: subsystem.subsystem,
              running: subsystem.running,
              total: subsystem.total
            })
          })
        }
      })
    },
    getHistory() {
      app.get('algorithm_history').then(res => {
        if (res.code === 0) {
          this.history = res.data
          // console.log(res.data)
        }
      })
    },
    list() {
      const vm = this
      app.get('algorithm_list', {
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }).then(res => {
        if (res.code === 0) {
          vm.algorithms = res.data
          this.total = res.total
        }
      })
    },
    handleCurrentChange(val) {
      this.pageNum = val
      this.list()
    }
  }
}
</script>
<style>
  .algorithm-monitor .board-card {
    margin: 10px;
  }

  .algorithm-monitor .status-card {
    border-left: 1px solid #ccc;
    border-right: 1px solid #ccc;
    border-bottom: 1px solid #ccc;
  }

  .algorithm-monitor .status-card ul {
    list-style: none;
    padding: 0;
  }

  .algorithm-monitor .status-card:first-of-type {
    border-top: 1px solid #ccc;
  }

  .algorithm-monitor .status-title {
    display: table;
    margin: 10px;
  }

  .algorithm-monitor .li-inline {
    display: inline;
  }

  .algorithm-monitor h4 {
    font-size: 18px;
    font-weight: 400;
    line-height: 32px;
    margin-top: 0;
    margin-bottom: 5px;
  }
  .algorithm-monitor .title{
    color: white;
    font-size: 14px;
    background-color: #7eb2db;
    height: 30px;
    line-height: 30px;
    text-align: center;
  }

</style>

