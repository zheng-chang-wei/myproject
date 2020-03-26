<template>
  <section id="trainGraph">
    <el-row>
      <!-- 车头 -->
      <el-col id="head" class="head" :span="2">
        <el-col id="doorHeadTop" class="door" :style="doorColor(carriages[0].doors[headDoorLength-1].state)"
          @click.native="doorChecked(carriages[0],carriages[0].doors[headDoorLength-1].addr)">
          {{ carriages[0].doors[headDoorLength-1].addr }}</el-col>
        <el-col class="doorWarning doorHeadWarning"
          :style="warningImg(carriages[0].doors[headDoorLength-1].state,'top')" />
        <el-col class="carriageNameStyle">端</el-col>
        <el-col class="doorWarning doorHeadWarning"
          :style="warningImg(carriages[0].doors[headDoorLength-2].state,'bottom')" />
        <el-col class="door doorHeadWarning" :style="doorColor(carriages[0].doors[headDoorLength-2].state)"
          @click.native="doorChecked(carriages[0],carriages[0].doors[headDoorLength-2].addr)">
          {{ carriages[0].doors[headDoorLength-2].addr }}</el-col>
      </el-col>
      <!-- 首节车厢 -->
      <el-col class="carriageStyle" :span="carriageSpan">
        <!-- 车门上半部分-->
        <template v-for="(door,index) in carriages[0].doors">
          <el-col v-if="index<(carriages[0].doors.length-2)/2" :key="door.addr" class="door doorTop"
            :style="doorStyle(carriages[0].doors[2*(index+1)-1].state)"
            @click.native="doorChecked(carriages[0],2*(index+1))">
            {{ 2*(index+1) }}
          </el-col>
        </template>
        <template v-for="(door,index) in carriages[0].doors">
          <el-col v-if="index<(carriages[0].doors.length-2)/2" :key="1+carriages[0].name+index+1" class="doorWarning"
            :style="warningStyle(door.state)" />
        </template>
        <!-- 车厢名称 -->
        <el-col class="carriageNameStyle">{{ carriages[0].name }}</el-col>
        <!-- 车门下半部分 -->
        <template v-for="(door,index) in carriages[0].doors">
          <el-col v-if="index>=(carriages[0].doors.length-2)/2&&index<(carriages[0].doors.length-2)"
            :key="carriages[0].name+index" class="doorWarning" :style="warningStyle(door.state)" />
        </template>
        <template v-for="(door,index) in carriages[0].doors">
          <el-col v-if="index>=(carriages[0].doors.length-2)/2&&index<(carriages[0].doors.length-2)" :key="door.addr"
            class="door doorBottom"
            :style="doorStyle(carriages[0].doors[2*(index-(carriages[0].doors.length-2)/2)].state)"
            @click.native="doorChecked(carriages[0],2*(index-(carriages[0].doors.length-2)/2)+1)">
            {{ 2*(index-(carriages[0].doors.length-2)/2)+1 }}
          </el-col>
        </template>
      </el-col>
      <!-- 车厢前半部分 -->
      <el-col v-for="carriage in carriagesTop" :key="carriage.name" class="carriageStyle" :span="carriageSpan">
        <template>
          <!-- 车门上半部分-->
          <template v-for="(door,index) in carriage.doors">
            <el-col v-if="index<carriage.doors.length/2" :key="door.addr" class="door doorTop"
              :style="doorStyle(carriage.doors[2*(index+1)-1].state)" @click.native="doorChecked(carriage,2*(index+1))">
              {{ 2*(index+1) }}
            </el-col>
          </template>
          <template v-for="(door,index) in carriage.doors">
            <el-col v-if="index<carriage.doors.length/2" :key="1+carriage.name+index+1" class="doorWarning"
              :style="warningStyle(door.state)" />
          </template>
        </template>
        <!-- 车厢名称 -->
        <el-col class="carriageNameStyle">{{ carriage.name }}</el-col>
        <!-- 车门下半部分 -->
        <template>
          <template v-for="(door,index) in carriage.doors">
            <el-col v-if="index>=carriage.doors.length/2" :key="carriage.name+index" class="doorWarning"
              :style="warningStyle(door.state)" />
          </template>
          <template v-for="(door,index) in carriage.doors">
            <el-col v-if="index>=carriage.doors.length/2" :key="door.addr" class="door doorBottom"
              :style="doorStyle(carriage.doors[2*(index-carriage.doors.length/2)].state)"
              @click.native="doorChecked(carriage,2*(index-carriage.doors.length/2)+1)">
              {{ 2*(index-carriage.doors.length/2)+1 }}
            </el-col>
          </template>
        </template>
      </el-col>
      <!-- 车厢后半部分 -->
      <el-col v-for="(carriage) in carriagesBottom" :key="carriage.name" class="carriageStyle" :span="carriageSpan">
        <!-- 车门上半部分-->
        <template v-for="(door,index) in carriage.doors">
          <el-col v-if="index<carriage.doors.length/2" :key="door.addr" class="door doorTop"
            :style="doorStyle(carriage.doors[2 * (carriage.doors.length / 2 - index - 1)].state)"
            @click.native="doorChecked(carriage,2 * (carriage.doors.length / 2 - index - 1) + 1)">
            {{ 2 * (carriage.doors.length / 2 - index - 1) + 1 }}
          </el-col>
        </template>
        <template v-for="(door,index) in carriage.doors">
          <el-col v-if="index<carriage.doors.length/2" :key="1+carriage.name+index+1" class="doorWarning"
            :style="warningStyle(door.state)" />
        </template>
        <el-col class="carriageNameStyle">{{ carriage.name }}</el-col>
        <!-- 车门下半部分-->
        <template v-for="(door,index) in carriage.doors">
          <el-col v-if="index>=carriage.doors.length/2" :key="carriage.name+index" class="doorWarning"
            :style="warningStyle(door.state)" />
        </template>
        <template v-for="(door,index) in carriage.doors">
          <el-col v-if="index>=carriage.doors.length/2" :key="door.addr" class="door doorBottom"
            :style="doorStyle(carriage.doors[ 2 * (carriage.doors.length - index)-1].state)"
            @click.native="doorChecked(carriage,2 * (carriage.doors.length - index))">
            {{ 2 * (carriage.doors.length - index) }}
          </el-col>
        </template>
      </el-col>
      <!-- 最后一节车厢 -->
      <el-col class="carriageStyle" :span="carriageSpan">
        <!-- 车门上半部分-->
        <template v-for="(door,index) in carriages[carriages.length-1].doors">
          <el-col v-if="index<(carriages[carriages.length-1].doors.length-2)/2" :key="door.addr" class="door doorTop"
            :style="doorStyle(carriages[carriages.length-1].doors[2 * ((carriages[carriages.length-1].doors.length-2) / 2 - index - 1)].state)"
            @click.native="doorChecked(carriages[carriages.length-1],2 * ((carriages[carriages.length-1].doors.length-2) / 2 - index - 1) + 1)">
            {{ 2 * ((carriages[carriages.length-1].doors.length-2) / 2 - index - 1) + 1 }}
          </el-col>
        </template>
        <template v-for="(door,index) in carriages[carriages.length-1].doors">
          <el-col v-if="index<(carriages[carriages.length-1].doors.length-2)/2"
            :key="1+carriages[carriages.length-1].name+index+1" class="doorWarning" :style="warningStyle(door.state)" />
        </template>
        <el-col class="carriageNameStyle">{{ carriages[carriages.length-1].name }}</el-col>

        <!-- 车门下半部分-->
        <template v-for="(door,index) in carriages[carriages.length-1].doors">
          <el-col
            v-if="index>=(carriages[carriages.length-1].doors.length-2)/2&&index<carriages[carriages.length-1].doors.length-2"
            :key="carriages[carriages.length-1].name+index" class="doorWarning" :style="warningStyle(door.state)" />
        </template>
        <template v-for="(door,index) in carriages[carriages.length-1].doors">
          <el-col
            v-if="index>=(carriages[carriages.length-1].doors.length-2)/2&&index<carriages[carriages.length-1].doors.length-2"
            :key="door.addr" class="door doorBottom"
            :style="doorStyle(carriages[carriages.length-1].doors[ 2 * (carriages[carriages.length-1].doors.length-2 - index)-1].state)"
            @click.native="doorChecked(carriages[carriages.length-1],2 * (carriages[carriages.length-1].doors.length-2 - index))">
            {{ 2 * (carriages[carriages.length-1].doors.length-2 - index) }}
          </el-col>
        </template>

      </el-col>
      <!-- 车尾 -->
      <el-col id="end" class="head" :span="2">
        <el-row id="doorEndTop" class="door"
          :style="doorStyle(carriages[carriages.length-1].doors[headDoorLength-2].state)"
          @click.native="doorChecked(carriages[carriages.length-1],carriages[carriages.length-1].doors[headDoorLength-2].addr)">
          {{ carriages[carriages.length-1].doors[headDoorLength-2].addr }}</el-row>
        <el-row class="doorWarning doorEndWarning"
          :style="warningImg(carriages[0].doors[headDoorLength-1].state,'top')" />
        <el-row class="carriageNameStyle">端</el-row>
        <el-row class="doorWarning doorEndWarning"
          :style="warningImg(carriages[0].doors[headDoorLength-2].state,'bottom')" />
        <el-row class="door doorEndWarning"
          :style="doorStyle(carriages[carriages.length-1].doors[headDoorLength-1].state)"
          @click.native="doorChecked(carriages[carriages.length-1],carriages[carriages.length-1].doors[headDoorLength-1].addr)">
          {{ carriages[carriages.length-1].doors[headDoorLength-1].addr }}</el-row>
      </el-col>
    </el-row>
  </section>
</template>
<script>
const doorStates = [
  '隔离',
  '紧急解锁',
  '门故障',
  '防挤压',
  '门完全打开',
  '关门过程中',
  '开门过程中',
  '门完全关闭'
]
export default {
  props: {
    carriages: {
      type: Array,
      default: null
    },
    carriageSpan: {
      type: Number,
      default: null
    }
  },
  data () {
    return {}
  },
  computed: {
    headDoorLength () {
      return this.carriages[0].doors.length
    },
    // 左半部分车厢
    carriagesTop () {
      const vm = this
      return this.carriages.filter(function (carriage, index) {
        return index !== 0 && index < vm.carriages.length / 2
      })
    },
    // 右半部分车厢
    carriagesBottom () {
      const vm = this
      return this.carriages.filter(function (carriage, index) {
        return index !== vm.carriages.length - 1 && index >= vm.carriages.length / 2
      })
    }
  },
  methods: {
    doorStyle (state) {
      var marginLeft = 'margin-left:' + (100 - (15 * this.carriages[1].doors.length) / 2) / (this.carriages[1].doors.length / 2 + 1) + '%'
      return marginLeft + ';' + this.doorColor(state)
    },
    doorColor (state) {
      var background = 'background-color:'
      // state = doorStates[1]
      switch (state) {
        case doorStates[0]: // 隔离
          return 'background: url(' + require('../../static/imgs/isolation.png') + ') no-repeat center  center'
        case doorStates[1]: // 紧急解锁
          return 'background: url(' + require('../../static/imgs/emergencyUnlock.png') + ') no-repeat center  center'
        case doorStates[2]: // 门故障
          return background + 'red'
        case doorStates[3]: // 防挤压
          return 'background: url(' + require('../../static/imgs/antiExtrusion.png') + ') no-repeat center  center'
        case doorStates[4]: // 门完全打开
          return background + 'yellow'
        case doorStates[5]: // 关门过程中
          return background + 'blue'
        case doorStates[6]: // 开门过程中
          return background + 'rgba(204, 102, 204, 1)'
        case doorStates[7]: // 门完全关闭
          return background + '#00FF00'
        default:
          return 'color:white;background: black;'
      }
    },
    doorChecked (carriage, doorIndex) {
      this.$emit('doorChecked', carriage, doorIndex)
    },
    warningStyle (state) {
      var marginLeft = 'margin-left:' + (100 - (15 * this.carriages[1].doors.length) / 2) / (this.carriages[1].doors.length / 2 + 1) + '%'
      return marginLeft + ';' + this.warningImg(state)
    },
    warningImg (state) {
      state = 2
      switch (state) {
        case 1: // 亚健康预警
          return (
            'background: #00FF00;border-color: #00FF00;'
          )
        case 2: // 寿命预警
          return (
            'background: red ;border-color: red;'
          )
        case 3: // 亚健康预警和寿命预警
          return (
            'background: yellow ;border-color: yellow;'
            // 'background: url(' + require('../../static/imgs/1.png') + ') no-repeat center '
          )
        default:
          return ''
      }
    }
  }
}
</script>
<style>#trainGraph {
  color: white;
}

#trainGraph .head {
  margin-left: 0.3%;
  height: 72px;
  background: #304156;
  box-sizing: border-box;
  border-width: 1px;
  border-style: solid;
  border-color: rgba(153, 153, 153, 1);
  border-radius: 54px;
}

#trainGraph #head {
  border-top-right-radius: 0px;
  border-bottom-right-radius: 0px;
}

#trainGraph #end {
  border-top-left-radius: 0px;
  border-bottom-left-radius: 0px;
}

#trainGraph .carriageStyle {
  margin-left: 0.3%;
  border-width: 1px;
  border-style: solid;
  border-color: rgba(153, 153, 153, 1);
  height: 72px;
  background-color: #304156;
}

#trainGraph .door {
  height: 22px;
  border-width: 1px;
  border-style: solid;
  border-color: rgba(153, 153, 153, 1);
  background-color: rgba(0, 255, 0, 1);
  display: flex;
  -webkit-align-items: center;
  align-items: center;
  -webkit-justify-content: center;
  justify-content: center;
  cursor: pointer;
}

.center {
  display: flex;
  -webkit-align-items: center;
  align-items: center;
  -webkit-justify-content: center;
  justify-content: center;
}

#trainGraph .doorTop {
  margin-top: -1px;
  margin-left: 2.2%;
  width: 15%;
}

#trainGraph .doorWarning {
  margin-top: 2px;
  width: 15%;
  height: 2.5px;
  border-width: 1px;
  border-style: solid;
  border-color: #304156;
}

#trainGraph .doorBottom {
  margin-top: 2px;
  margin-left: 2.2%;
  width: 15%;
}

#trainGraph #doorHeadTop {
  margin-top: -1px;
  margin-left: 70%;
  width: 25%;
}

#trainGraph .doorHeadWarning {
  margin-top: 2px;
  margin-left: 70%;
  width: 25%;
}

#trainGraph #doorEndTop {
  margin-top: -1px;
  margin-left: 4%;
  width: 25%;
}

#trainGraph .doorEndWarning {
  margin-top: 2px;
  margin-left: 4%;
  width: 25%;
}

#trainGraph .carriageNameStyle {
  height: 17px;
  display: flex;
  -webkit-align-items: center;
  align-items: center;
  -webkit-justify-content: center;
  justify-content: center;
}
</style>
