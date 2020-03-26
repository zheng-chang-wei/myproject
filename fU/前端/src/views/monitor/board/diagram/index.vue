<template>
  <div class="boardOutside">
    <img style="position:absolute;width: 1100px;left: 15px;top: 5px;" src="@/assets/images/jixiang.png">
    <div v-for="(board,i) in boards"
         class="board"
         :style="{width: slotSpan[i]===2?'93px':'46px',
         backgroundImage:'url('+getImg(board.boardType)+')',
         backgroundSize:'cover'}">
      <div style="height: 250px;"
           @mouseover="showDetail($event,board)"
           @mouseleave="hideDetail($event)">
        <div style="pointer-events: none;">
          <led-status :ref="'ledgroup'+board.slotID" class="led"></led-status>
          <basic-board class="card"
                       :board="board"
                       v-show="board.slotID==theCoveredCard && board.slotID!==null">
            <template slot-scope="scope">
              <div :is=getBordType(board.type)
                   :board="scope.board"></div>
            </template>
          </basic-board>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import app from '@/common/js/app'
  import BasicBoard from '../boards/BasicBoard'
  import MvbBoard from '../boards/MvbBoard'
  import EcnBoard from '../boards/EcnBoard'
  import PhmBoard from '../boards/PhmBoard'
  import CpuBoard from '../boards/CpuBoard'
  import WirelessBoard from '../boards/WirelessBoard'
  import SsdBoard from '../boards/SsdBoard'

  import ledStatus from "./ledStatus";

  import SSD from '@/assets/images/SSD.png'
  import CPU from '@/assets/images/CPU.png'
  import AD from '@/assets/images/AD.png'
  import MVB from '@/assets/images/MVB.png'
  import ECN from '@/assets/images/ECN.png'
  import PHM_AGX from '@/assets/images/PHM-AGX.png'
  import PHM_IMX8 from '@/assets/images/PHM-IMX8.png'
  import PHM_TX2 from '@/assets/images/PHM-TX2.png'
  import WIRELESS from '@/assets/images/Wireless.png'
  import POWER from '@/assets/images/POWER.png'
  import POWER_IN from '@/assets/images/POWER-IN.png'

  export default {
    name: "index",
    components: {
      BasicBoard,
      MvbBoard,
      EcnBoard,
      PhmBoard,
      CpuBoard,
      WirelessBoard,
      SsdBoard,
      ledStatus
    },
    data() {
      return {
        theCoveredCard: 'none',
        img: {name: 'SSD', value: SSD},
        boards: [
          {boardType: "SSD", slotID: 1, type: "SSD"},
          {boardType: "CPU", slotID: 2, type: "CPU"},
          {boardType: "AD1", slotID: 3, type: "AD"},
          {boardType: "AD2", slotID: 4, type: "AD"},
          {boardType: "PHM_AGX", slotID: 5, type: "PHM"},
          {boardType: "PHM_TX2", slotID: 7, type: "PHM"},
          {boardType: "PHM_IMX8", slotID: 9, type: "PHM"},
          {boardType: "PHM_IMX8", slotID: 10, type: "PHM"},
          {boardType: "PHM_IMX8", slotID: 11, type: "PHM"},
          {boardType: "PHM_IMX8", slotID: 12, type: "PHM"},
          {boardType: "WIRELESS", slotID: 13, type: "无线"},
          {boardType: "ECN", slotID: 14, type: "ECN"},
          {boardType: "MVB", slotID: 15, type: "MVB"},
          {boardType: "POWER", slotID: null, type: "POWER"},
          {boardType: "POWER", slotID: null, type: "POWER"},
          {boardType: "POWER-IN", slotID: null, type: "POWER"}],
        slotSpan: [1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2]
      }
    },
    mounted() {
      this.listBoards()
    },
    methods: {
      getImg: function (boardType) {
        let img = null
        switch (boardType) {
          case 'SSD':
            img = SSD;
            break;
          case 'CPU':
            img = CPU;
            break;
          case 'AD1':
            img = AD;
            break;
          case 'AD2':
            img = AD;
            break;
          case 'MVB':
            img = MVB;
            break;
          case 'ECN':
            img = ECN;
            break;
          case 'PHM_AGX':
            img = PHM_AGX;
            break;
          case 'PHM_TX2':
            img = PHM_TX2;
            break;
          case 'PHM_IMX8':
            img = PHM_IMX8;
            break;
          case 'POWER':
            img = POWER;
            break;
          case 'POWER-IN':
            img = POWER_IN;
            break;
          case 'WIRELESS':
            img = WIRELESS;
            break;
        }
        return img
      },
      listBoards() {
        let _this = this;
        app.get('board_list').then(res => {
          if (res.code === 0) {
            console.log(res.data)
            let retBoards = res.data;
            for (let i = 0; i < retBoards.length; i++) {
              for (let j = 0; j < _this.boards.length - 3; j++) {
                if (retBoards[i].slotID == _this.boards[j].slotID) {
                  _this.boards[j] = retBoards[i]
                  try {
                    let cardId = retBoards[i].slotID
                    let ledstatus = retBoards[i].outside.ledStatus
                    _this.$refs['ledgroup' + cardId][0].setLedStatus(ledstatus)
                  } catch (e) {
                  }
                  break;
                }
              }
            }
            _this.$forceUpdate();
          }
        });
      },
      showDetail: function (el, board) {
        el.target.style.backgroundColor = 'lightgray'
        this.theCoveredCard = board.slotID
      },
      hideDetail: function (el) {
        el.target.style.backgroundColor = ''
        this.theCoveredCard = 'none'
      },
      getBordType: function (type) {
        let board = 'BasicBoard';
        switch (type) {
          case 'MVB':
            board = 'MvbBoard';
            break;
          case 'AD':
            board = 'EcnBoard';
            break;
          case 'PHM':
            board = 'PhmBoard';
            break;
          case 'CPU':
            board = 'CpuBoard';
            break;
          case 'SSD':
            board = 'SsdBoard';
            break;
          default:
            board = 'SsdBoard'
        }
        return board;
      }
    }
  }
</script>

<style scoped>
  .boardOutside {
    display: inline-block;
    padding: 30px 80px;
    min-width: 1200px;
    font-size: 11px;
  }

  .board {
    height: 300px;
    position: relative;
    display: inline-block;
    cursor: pointer;
    pointer-events: auto;
  }

  .card {
    position: absolute;
    top: 50px;
    left: 10px;
    width: 300px;
    background-color: white;
    z-index: 999;
    opacity: 0.95;
    text-align: left;
    pointer-events: none;
  }

  .led {
    position: absolute;
    display: inline-block;
    top: 50px;
    left: 3px;
  }

  .slot {
    position: absolute;
    bottom: 0px;
    left: 20px;
    width: 100%;
  }
</style>
