<template>
  <el-row id="priview" :style="commonStyle">
    <basic :basic-info="basicInfo" @edit="edit" />
    <boardSetting :board-settings="boardSettings" @edit="edit" />
    <algorithm :algorithm-settings="algorithmSettings" @edit="edit" />
    <store :store-config="storeConfig" @edit="edit" />
    <clock :clock-config-info="clockConfigInfo" @edit="edit" />

  </el-row>
</template>

<script>
import basic from './basic'
import boardSetting from './boardSetting'
import algorithm from './algorithm'
import store from './store'
import clock from './clock'
export default {
  components: {
    basic,
    boardSetting,
    algorithm,
    store,
    clock
  },
  props: {
    basicInfo: {
      type: Object,
      default: null
    },
    boardSettings: {
      type: Array,
      default: null
    },
    algorithmSettings: {
      type: Object,
      default: null
    },
    storeConfig: {
      type: Object,
      default: null
    },
    clockConfigInfo: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      commonStyle: {}
    }
  },

  mounted() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
  },
  methods: {
    changeHeight() {
      this.commonStyle = { height: document.body.offsetHeight - 70 + 'px' }
    },
    edit(active) {
      this.$emit('edit', active)
    }
  }
}
</script>
<style>
#priview {
  overflow: auto;
}
#priview .el-card__body{
padding: 0px;
}
#priview .el-card__header {
    padding: 5px 25px;
}
#priview .el-form-item {
    margin-bottom: 5px;
}
#priview label {
    font-weight: 540;
}
.box-card {
  margin-left: 2%;
  margin-top: 10px;
  width: 97%;
}

.center {
  display: flex;
  /*定义body的元素垂直居中*/
  align-items: center;
  /*定义body的里的元素水平居中*/
  /* justify-content: center; */
}
</style>

