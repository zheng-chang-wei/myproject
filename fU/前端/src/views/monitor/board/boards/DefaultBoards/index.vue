<template>
  <div>
    <el-row :gutter="10">
      <el-col :md="12" :sm="24">
        <el-col :span="24" v-for="board in left">
          <basic-board :board="board">
            <template slot-scope="scope">
              <mvb-board v-if="board.type==='MVB'" :board="scope.board"></mvb-board>
              <ecn-board v-else-if="board.type==='ECN'" :board="scope.board"></ecn-board>
              <phm-board v-else-if="board.type==='PHM'" :board="scope.board"></phm-board>
              <cpu-board v-else-if="board.type==='CPU'" :board="scope.board"></cpu-board>
              <wireless-board v-else-if="board.type==='无线'" :board="scope.board"></wireless-board>
              <ssd-board v-else-if="board.type==='SSD'" :board="scope.board"></ssd-board>
              <div v-else style="height: 25px"></div>
            </template>
          </basic-board>
        </el-col>
      </el-col>
      <el-col :md="12" :sm="24">
        <el-col :span="24" v-for="board in right">
          <basic-board :board="board">
            <template slot-scope="scope">
              <mvb-board v-if="board.type==='MVB'" :board="scope.board"></mvb-board>
              <ecn-board v-else-if="board.type==='ECN'" :board="scope.board"></ecn-board>
              <phm-board v-else-if="board.type==='PHM'" :board="scope.board"></phm-board>
              <cpu-board v-else-if="board.type==='CPU'" :board="scope.board"></cpu-board>
              <wireless-board v-else-if="board.type==='无线'" :board="scope.board"></wireless-board>
              <ssd-board v-else-if="board.type==='SSD'" :board="scope.board"></ssd-board>
              <div v-else style="height: 25px"></div>
            </template>
          </basic-board>
        </el-col>
      </el-col>
    </el-row>
  </div>
</template>
<script>
  import BasicBoard from '../BasicBoard'
  import MvbBoard from '../MvbBoard'
  import EcnBoard from '../EcnBoard'
  import PhmBoard from '../PhmBoard'
  import CpuBoard from '../CpuBoard'
  import WirelessBoard from '../WirelessBoard'
  import SsdBoard from '../SsdBoard'
  export default {
    components: {
      BasicBoard,
      MvbBoard,
      EcnBoard,
      PhmBoard,
      CpuBoard,
      WirelessBoard,
      SsdBoard,
    },
    props: {
      boards: Array,
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
      }
    },
  }

</script>
