<template>
  <div class="algorithm-monitor">
    <el-row>
      <!-- 机箱示意图 -->
    </el-row>
    <el-row :gutter="5">
      <el-col :md="10" :xs="24">
        <status-abstract :data="abstract"></status-abstract>
        <status-history :data="history"></status-history>
      </el-col>
      <el-col :md="14" :xs="24">
        <algorithms></algorithms>
      </el-col>
    </el-row>
  </div>
</template>
<script>
  import StatusAbstract from '../components/StatusAbstract'
  import StatusHistory from '../components/StatusHistory'
  import algorithms from './algorithms/index'
  import app from '@/common/js/app'
  export default {
    components: {
      StatusAbstract,
      StatusHistory,
      algorithms
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
        }
      }
    },
    mounted() {
      this.getAbstract();
      this.getHistory();
    },
    methods: {
      getAbstract() {
        app.get('algorithm_abstract').then(res => {
          if (res.code === 0) {
            this.abstract.running = res.data.running;
            this.abstract.error = res.data.error;
            this.abstract.stopped = res.data.stopped;
            this.abstract.types = [];
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
            this.history = res.data;
            // console.log(res.data)
          }
        })
      }
    }
  }

</script>
<style>
  .algorithm-monitor .board-card {
    /* border: 1px solid #000; */
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

</style>
