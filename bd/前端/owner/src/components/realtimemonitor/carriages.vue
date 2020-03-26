<template>
  <section id="carriages">
    <trainGraph :style="{'margin-top':'10px'}" v-if="carriages.length>0" :carriages="carriages" :carriage-span="carriageSpan" @doorChecked="doorChecked" />
    <el-row style="margin-top:10px;margin-left: 2px;color:white">
      <el-col class="example" :span="2">
        <el-col class="exampleSize" style="background-color:rgba(0, 255, 0, 1); " />
        <span>&nbsp;门完全关闭</span>
      </el-col>
      <el-col class="example" :span="2">
        <el-col class="exampleSize" style="background-color:yellow; " />
        <span>&nbsp;门完全打开</span>
      </el-col>
      <el-col class="example" :span="2">
        <el-col class="exampleSize" style="background-color:rgba(204, 102, 204, 1);" />
        <span>&nbsp;开门过程中</span>
      </el-col>
      <el-col class="example" :span="2">
        <el-col class="exampleSize" style="background-color:blue;" />
        <span>&nbsp;关门过程中</span>
      </el-col>
      <el-col class="example" :span="2">
        <el-col class="exampleSize" style="background-color:black;" />
        <span>&nbsp;通讯故障</span>
      </el-col>
      <el-col class="example" :span="2">
        <el-col class="exampleSize" style="background-color:red;" />
        <span>&nbsp;门故障</span>
      </el-col>
      <el-col class="example" :span="2">
        <el-col class="exampleSize" :style="doorStyle('isolation.png')" />
        <span>&nbsp;隔离</span>
      </el-col>
      <el-col class="example" :span="2">
        <el-col class="exampleSize" :style="doorStyle('emergencyUnlock.png')" />
        <span>&nbsp;紧急解锁</span>
      </el-col>
      <el-col class="example" :span="2">
        <el-col class="exampleSize" :style="doorStyle('antiExtrusion.png')" />
        <span>&nbsp;防挤压</span>
      </el-col>
      <el-col class="example" style="margin-top:4px" :span="2">
        <el-col class="lifeSize" style="background:#00FF00" />
        <span>&nbsp;亚健康预警</span>
      </el-col>
      <el-col class="example" style="margin-top:4px" :span="2">
        <el-col class="lifeSize" style="background:red" />
        <span>&nbsp;寿命预警</span>
      </el-col>
    </el-row>
  </section>
</template>

<script>
import trainGraph from '../TrainGraph'
export default {
  components: {
    trainGraph
  },
  props: {
    carriages: {
      type: Array,
      default: null
    },
    carriageSpan: {
      type: Number,
      default: 3
    }
  },
  data () {
    return {
      exampleSpan: 0
    }
  },
  methods: {
    doorChecked (carriage, doorIndex) {
      this.$emit('doorChecked', carriage, doorIndex)
    },
    doorStyle (state) {
      return 'background: url(' + require('../../../static/imgs/' + state) + ') no-repeat center  center'
    }
  }
}

</script>
<style>

#carriages .example {
  margin-left: 5px;
  display: flex;
  -webkit-align-items: center;
  align-items: center;
  font-size:10px
}

#carriages .example .exampleSize {
  width: 20px;
  height: 20px;
  border-width: 1px;
  border-style: solid;
  border-color: rgba(153, 153, 153, 1);
}

#carriages .example .lifeSize {
  width: 20px;
  height: 6px;
  border-width: 1px;
  border-style: solid;
  border-color: rgba(153, 153, 153, 1);
}

#carriages .center {
  display: flex;
  -webkit-align-items: center;
  align-items: center;
  -webkit-justify-content: center;
  justify-content: center;
}

</style>
