<template>
  <!-- 左侧滑动界面 -->
  <div id="slide">
    <el-col :class="leftColStyle">
      <el-button
        v-if="isOpen"
        class="button"
        icon="el-icon-caret-right"
        type="text"
        :circle="true"
        size="medium"
        @click="changeSize"
      />
      <el-button
        v-else
        class="button"
        icon="el-icon-caret-left"
        type="text"
        :circle="true"
        size="medium"
        @click="changeSize"
      />
      <transition name="el-zoom-in-center">
        <el-row v-show="!isOpen" :class="isShow">
          <quick-navigation
            v-if="displayQuickNavigation"
            style
            @cityChange="cityChange"
            @lineChange="lineChange"
            @carChange="carChange"
          />
          <instant-message :datas="datas" />
        </el-row>
      </transition>
    </el-col>
  </div>
</template>
<script>
import 'element-ui/lib/theme-chalk/base.css'
import InstantMessage from '@/components/InstantMessage.vue'
import QuickNavigation from '@/components/QuickNavigation.vue'
export default {
  components: {
    InstantMessage,
    QuickNavigation
  },
  props: {
    datas: {
      type: Array,
      default: null
    },
    displayQuickNavigation: Boolean
  },
  data() {
    return {
      leftColStyle: 'leftMinStyle',
      isOpen: true,
      isShow: 'ulIsShow'
    }
  },
  mounted() {},
  methods: {
    changeSize() {
      this.$emit('changeSize', this.isOpen)
      if (this.isOpen) {
        this.isShow = 'ulShow'
        this.leftColStyle = 'leftMaxStyle'
        this.isOpen = false
      } else {
        this.isShow = 'ulIsShow'
        this.leftColStyle = 'leftMinStyle'
        this.isOpen = true
      }
    },
    cityChange(city) {
      this.$emit('cityChange', city)
    },
    lineChange(line) {
      this.$emit('lineChange', line)
    },
    carChange(car) {
      this.$emit('carChange', car)
    }
  }
}
</script>

<style >
#slide .ulIsShow {
  display: none;
}
#slide .ulShow {
  list-style: none;
  background-color: #666666;
  margin: 10px;
}
#slide .leftMinStyle {
  position: absolute;
  left: 0px;
  top: 0px;
  z-index: 9;
  background-color: #666666;
  /* height: 750px; */
  width: 2%;
}
#slide .leftMaxStyle {
  position: absolute;
  z-index: 9;
  left: 0px;
  top: 0px;
  background-color: #666666;
  height: 750px;
  width: 25%;
}
#slide .button {
  background-color: #666666;
  border-style: none;
  font-size: 30px;
}
</style>
