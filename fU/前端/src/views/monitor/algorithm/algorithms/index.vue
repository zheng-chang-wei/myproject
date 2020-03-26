<template>
  <div class="board-card algorithm-list">
    <div class="detail-title">
      <div class="pull-right">
        <el-select v-model="groupType" @change="groupTypeChange">
          <el-option v-for="item in groupOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </div>
      <h4>算法详情</h4>
    </div>
    <default-algorithms v-if="groupType==='default'" :datas="algorithms"></default-algorithms>
    <type-algorithms v-else-if="groupType==='slot'" :types="slotAlgorithms"></type-algorithms>
    <type-algorithms v-else-if="groupType==='status'" :types="statusAlgorithms"></type-algorithms>
    <type-algorithms v-else-if="groupType==='subsystem'" :types="systemAlgorithms"></type-algorithms>
  </div>
</template>
<script>
  import DefaultAlgorithms from './DefaultAlgorithms/index'
  import TypeAlgorithms from './TypeAlgorithms/index'
  import app from '@/common/js/app'
  export default {
    components: {
      DefaultAlgorithms,
      TypeAlgorithms
    },
    data() {
      return {
        groupType: 'default',
        groupOptions: [{
          value: 'default',
          label: '按照名称显示'
        }, {
          value: 'slot',
          label: '按照槽位显示'
        }, {
          value: 'status',
          label: '按照状态显示'
        }, {
          value: 'subsystem',
          label: '按照子系统显示'
        }],
        algorithms: [],
        firstStatus: 'error',
      }
    },
    computed: {
      slotAlgorithms: function () {
        let types = this.groupBy(this.algorithms, (algorithm) => {
          return algorithm.data.slotId;
        })
        var datas = [];
        types.forEach(function (value, key) {
          datas.push({
            type: '槽位' + key,
            algorithms: value,
          })
        });
        return datas;
      },
      systemAlgorithms: function () {
        let types = this.groupBy(this.algorithms, (algorithm) => {
          return algorithm.data.subsystem;
        })
        var datas = [];
        types.forEach(function (value, key) {
          datas.push({
            type: key,
            algorithms: value,
          })
        });
        return datas;
      },
      statusAlgorithms: function () {
        let states = this.groupBy(this.algorithms, (algorithm) => {
          return algorithm.data.status;
        });
        var datas = [];
        if (this.firstStatus === 'error') {
          this.getStatusAlgorithms('Err', '异常运行', states, datas);
          this.getStatusAlgorithms('Stop', '停止运行', states, datas);
          this.getStatusAlgorithms('Run', '正常运行', states, datas);
        } else if (this.firstStatus === 'stopped') {
          this.getStatusAlgorithms('Stop', '停止运行', states, datas);
          this.getStatusAlgorithms('Err', '异常运行', states, datas);
          this.getStatusAlgorithms('Run', '正常运行', states, datas);
        } else if (this.firstStatus === 'working') {
          this.getStatusAlgorithms('Run', '正常运行', states, datas);
          this.getStatusAlgorithms('Err', '异常运行', states, datas);
          this.getStatusAlgorithms('Stop', '停止运行', states, datas);
        }
        this.getStatusAlgorithms('Idle', '空闲', states, datas)
        console.log(datas);
        return datas;
      }
    },
    mounted() {
      this.list();
      this.$bus.$on('GroupType', (value, status) => {
        this.firstStatus = status;
        this.groupType = value;
      })
    },
    beforeDestroy() {
      this.$bus.$off();
    },
    methods: {
      list() {
        let vm = this;
        app.get('algorithm_list').then(res => {
          if (res.code === 0) {
            vm.algorithms = res.data;
          }
        })
      },
      groupBy(list, fn) {
        const groups = new Map();
        list.forEach(function (o) {
          const group = fn(o);
          groups.set(group, groups.get(group) || []);
          groups.get(group).push(o);
        })
        return groups
      },
      getStatusAlgorithms(key, text, states, algorithms) {
        if (states.get(key)) {
          algorithms.push({
            type: text,
            algorithms: states.get(key)
          })
        }
      },
      groupTypeChange(value) {
        if (value != 'status') {
          this.firstStatus = 'error';
        }
      }
    }
  }

</script>
<style scoped>
  .detail-title {
    margin-bottom: 10px;
  }

  .pull-right {
    float: right;
  }

</style>
