<template>
  <el-row>
    <div class="boardOutside">
      <img style="position:absolute;width: 594px;left: 0px;top: 0px;" src="@/assets/images/jixiang.png">
      <div
        v-for="(board,i) in boards"
        v-show="board.show"
        :key="i"
        class="board"
        :style="{width: board.slotSpan===2?'50px':'25px',
                 backgroundImage:'url('+getImg (board.boardType)+')',
                 backgroundSize:'cover'}"
      >
        <div
          style="height: 135px;"
          @mouseover="showDetail($event,board)"
          @mouseleave="hideDetail($event)"
          @click="clickBoard(board)"
        >
          <div style="pointer-events: none;">
            <led-status :ref="'ledgroup'+board.slotID" class="led" />
          </div>
        </div>
      </div>
    </div>
    <div
      v-if="theCoveredBoard.type === 'NO' || theCoveredBoard.type === 'POWER' || theCoveredBoard.type === 'SSD'"
      style="height: 20px;"
    />
    <div
      v-show="theCoveredBoard.type != 'NO' && theCoveredBoard.type != 'POWER' && theCoveredBoard.type != 'SSD'"
      style="font-size: 13px;text-align: left;padding-left: 200px;line-height: 20px;"
    >
      槽位{{ theCoveredBoard.slotID }}:{{ theCoveredBoard.type }} {{ theCoveredBoard.cardStatus|filterStatus }}
    </div>
  </el-row>
</template>

<script>
import app from '@/common/js/app'

import ledStatus from './ledStatus'

import NO from '@/assets/images/NO.png'
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
  name: 'Index',
  components: { ledStatus },
  filters: {
    filterStatus(value) {
      if (value === 'IDLE') {
        return '空闲'
      } else if (value === 'WORKING') {
        return '正常运行'
      } else if (value === 'STOPPED') {
        return '停止运行'
      } else if (value === 'ERROR') {
        return '发生异常'
      } else if (value === 'OFFLINE') {
        return '离线'
      }
      return value
    }
  },
  data() {
    return {
      theCoveredBoard: { slotId: 0, type: 'NO' },
      img: { name: 'SSD', value: SSD },
      boards: [
        { boardType: 'SSD', slotID: 1, type: 'SSD', slotSpan: 1, show: true },
        { boardType: 'NO', slotID: 2, type: 'NO', slotSpan: 1, show: true },
        { boardType: 'NO', slotID: 3, type: 'NO', slotSpan: 1, show: true },
        { boardType: 'NO', slotID: 4, type: 'NO', slotSpan: 1, show: true },
        { boardType: 'NO', slotID: 5, type: 'NO', slotSpan: 1, show: true },
        { boardType: 'NO', slotID: 6, type: 'NO', slotSpan: 1, show: true },
        { boardType: 'NO', slotID: 7, type: 'NO', slotSpan: 1, show: true },
        { boardType: 'NO', slotID: 8, type: 'NO', slotSpan: 1, show: true },
        { boardType: 'NO', slotID: 9, type: 'NO', slotSpan: 1, show: true },
        { boardType: 'NO', slotID: 10, type: 'NO', slotSpan: 1, show: true },
        { boardType: 'NO', slotID: 11, type: 'NO', slotSpan: 1, show: true },
        { boardType: 'NO', slotID: 12, type: 'NO', slotSpan: 1, show: true },
        { boardType: 'NO', slotID: 13, type: 'NO', slotSpan: 1, show: true },
        { boardType: 'NO', slotID: 14, type: 'NO', slotSpan: 1, show: true },
        { boardType: 'NO', slotID: 15, type: 'NO', slotSpan: 1, show: true },
        { boardType: 'POWER', slotID: 16, type: 'POWER', slotSpan: 2, show: true },
        { boardType: 'POWER', slotID: 18, type: 'POWER', slotSpan: 2, show: true },
        { boardType: 'POWER-IN', slotID: 20, type: 'POWER', slotSpan: 2, show: true }]
    }
  },
  mounted() {
    this.listBoards()
  },
  methods: {
    getImg: function(boardType) {
      let img = null
      switch (boardType) {
        case 'NO':
          img = NO
          break
        case 'SSD':
          img = SSD
          break
        case 'CPU':
          img = CPU
          break
        case 'AD1':
          img = AD
          break
        case 'AD2':
          img = AD
          break
        case 'MVB':
          img = MVB
          break
        case 'ECN':
          img = ECN
          break
        case 'PHM_AGX':
          img = PHM_AGX
          break
        case 'PHM_TX2':
          img = PHM_TX2
          break
        case 'PHM_IMX8':
          img = PHM_IMX8
          break
        case 'POWER':
          img = POWER
          break
        case 'POWER-IN':
          img = POWER_IN
          break
        case 'WIRELESS':
          img = WIRELESS
          break
      }
      return img
    },
    listBoards() {
      const _this = this
      app.get('board_list').then(res => {
        if (res.code === 0) {
          const tmp = res.data
          localStorage.setItem('boards', JSON.stringify(tmp))
          for (let i = 0; i < tmp.length; i++) {
            const index = tmp[i].slotID - 1
            _this.boards[index] = tmp[i]
            _this.boards[index].show = true
            // 特殊的板卡占用两个槽位
            if (_this.boards[index].boardType === 'PHM_TX2' || _this.boards[index].boardType === 'PHM_AGX') {
              _this.boards[index].slotSpan = 2
            }
            // led 等显示
            const cardId = tmp[i].slotID
            if (tmp[i].outside !== null) {
              const ledstatus = tmp[i].outside.ledStatus
              _this.$refs['ledgroup' + cardId][0].setLedStatus(ledstatus)
            }
          }
          // 隱藏 NO的位置，留给双槽位办卡用
          for (let i = 2; i < 14; i++) {
            if (_this.boards[i].slotSpan === 2) {
              _this.boards[i + 1].show = false
            }
          }
          for (let i = 0; i < tmp.length; i++) {
            if (tmp[i].type !== 'SSD' && _this.clickBoard(tmp[i])) {
              break
            }
          }
          _this.$forceUpdate()
        }
      })
    },
    showDetail: function(el, board) {
      el.target.style.backgroundColor = 'lightgray'
      el.target.style.opacity = 0.5
      this.theCoveredBoard = board
    },
    hideDetail: function(el) {
      el.target.style.backgroundColor = ''
      this.theCoveredBoard = { slotId: 0, type: 'NO' }
    },
    clickBoard(board) {
      if (board.type === 'POWER' || board.type === 'POWER-IN') {
        return false
      }
      this.$emit('onclick', board)
      return true
    },
    getErr: function(item) {
      return true
    }
  }
}
</script>

<style scoped>
  .boardOutside {
    display: inline-block;
    padding: 14px 35px 0 ;
    min-width: 597px;
    height: 201px;
    font-size: 11px;
  }

  .board {
    height: 162px;
    position: relative;
    display: inline-block;
    cursor: pointer;
    pointer-events: auto;
  }

  .led {
    position: absolute;
    display: inline-block;
    top: 22px;
    left: 2px;
  }
</style>
