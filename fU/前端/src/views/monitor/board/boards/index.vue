<template>
  <div class="board-card board-list">
    <div class="detail-title">
      <div class="pull-right">
        <el-select v-model="groupType" @change="groupTypeChange">
          <el-option v-for="item in groupOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </div>
      <h4>板卡详情</h4>
    </div>
    <!-- 按照槽位显示 -->
    <default-boards v-if="groupType==='default'" :boards="boards"></default-boards>
    <!-- 按照板卡类型显示 -->
    <type-boards v-else-if="groupType==='type'" :types="typeBoards"></type-boards>
    <!-- 按照板卡状态显示 -->
    <type-boards v-else-if="groupType==='status'" :types="statusBoards"></type-boards>
  </div>
</template>
<script>
  import DefaultBoards from './DefaultBoards/index'
  import TypeBoards from './TypeBoards/index'
  import app from '@/common/js/app'
  export default {
    components: {
      DefaultBoards,
      TypeBoards
    },
    data() {
      return {
        groupType: 'default',
        groupOptions: [{
          value: 'default',
          label: '按照槽位显示'
        }, {
          value: 'type',
          label: '按照类型显示'
        }, {
          value: 'status',
          label: '按照状态显示'
        }],
        boards: [],
        firstStatus: 'error',
      }
    },
    computed: {
      left: function () {
        let arr = [];
        for (let i = 0; i < this.boards.length; i = i + 2) {
          arr.push(this.boards[i]);
        }
        return arr;
      },
      right: function () {
        let arr = [];
        for (let i = 1; i < this.boards.length; i = i + 2) {
          arr.push(this.boards[i]);
        }
        return arr;
      },
      typeBoards: function () {
        let types = this.groupBy(this.boards, (board) => {
          return board.type;
        });
        var boardLists = [];
        types.forEach(function (value, key) {
          boardLists.push({
            type: key,
            boards: value,
          })
        });
        console.log('typeBoards');
        return boardLists;
      },
      statusBoards: function () {
        let states = this.groupBy(this.boards, (board) => {
          return board.cardStatus || 'other';
        });
        var boardLists = [];
        if (this.firstStatus === 'error') {
          this.getStatusBoards('ERROR', '异常运行', states, boardLists);
          this.getStatusBoards('STOPPED', '停止运行', states, boardLists);
          this.getStatusBoards('WORKING', '正常运行', states, boardLists);
        } else if (this.firstStatus === 'stopped') {
          this.getStatusBoards('STOPPED', '停止运行', states, boardLists);
          this.getStatusBoards('ERROR', '异常运行', states, boardLists);
          this.getStatusBoards('WORKING', '正常运行', states, boardLists);
        } else if (this.firstStatus === 'working') {
          this.getStatusBoards('WORKING', '正常运行', states, boardLists);
          this.getStatusBoards('ERROR', '异常运行', states, boardLists);
          this.getStatusBoards('STOPPED', '停止运行', states, boardLists);
        }
        this.getStatusBoards('IDLE', '空闲', states, boardLists);
        this.getStatusBoards('other', '其他', states, boardLists);
        console.log('statusBoards');
        return boardLists;
      }
    },
    mounted() {
      this.listBoards();
      this.$bus.$on('GroupType', (value, status) => {
        this.firstStatus = status;
        this.groupType = value;
      })
    },
    beforeDestroy() {
      this.$bus.$off();
    },
    methods: {
      listBoards() {
        let vm = this;
        app.get('board_list').then(res => {
          if (res.code === 0) {
            vm.boards = res.data;
          }
        });
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
      getStatusBoards(key, text, states, boardLists) {
        if (states.get(key)) {
          boardLists.push({
            type: text,
            boards: states.get(key)
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
