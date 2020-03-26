<template>
  <!-- 警告title -->
  <div v-show="message!=''" id="warning">
    <transition name="el-zoom-in-top">
      <el-row id="fault" @click.native="click">{{ message }}</el-row>
    </transition>
  </div>
</template>
<script>
import 'element-ui/lib/theme-chalk/base.css'
export default {
  props: {
    message: {
      type: String,
      default: null
    }
  },
  data() {
    return {
    }
  },
  mounted() {
  },
  methods: {
    click() {
      this.$router.push('/instantnotice')
    }
  }
}
</script>

<style >
#warning #fault {
  position: relative;
  width: 500px;
  height: 31px;
  background-color: rgba(204, 102, 0, 1);
  border: none;
  border-radius: 5px;
  color: #ffffff;
  display: flex;
  -webkit-align-items: center;
  align-items: center;
  -webkit-justify-content: center;
  justify-content: center;
  margin-left: auto;
  margin-right: auto;
  cursor: pointer;
}
</style>
