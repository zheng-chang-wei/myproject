<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <keep-alive>
        <router-view :key="key" />
      </keep-alive>

    </transition>
  </section>
</template>

<script>
export default {
  name: 'AppMain',
  data() {
    return {
      websocket: null
    }
  },
  computed: {
    key() {
      return this.$route.path
    }
  },
  mounted() {
    this.initWebSocket()
  },
  methods: {
    initWebSocket() {
      if ('WebSocket' in window) {
        this.websocket = new WebSocket('ws://localhost:18081/websocket')
        // 连接错误
        this.websocket.onerror = this.setErrorMessage
        // 连接成功
        this.websocket.onopen = this.setOnopenMessage
        // 收到消息的回调
        this.websocket.onmessage = this.setOnmessageMessage
        // 连接关闭的回调
        this.websocket.onclose = this.setOncloseMessage
        // 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = this.onbeforeunload
      } else {
        alert('当前浏览器 Not support websocket')
      }
    },
    setErrorMessage() {
      console.log('链接失败')
    },
    setOnopenMessage() {
      this.send('admin')
      console.log('链接成功')
    },
    setOnmessageMessage(event) {
      const data = JSON.parse(event.data)
      if (data.code === 1) { // 删除数据
        this.$bus.$emit('deleteData')
      } else if (data.code === 2) { // 要下载文件的个数
        this.$bus.$emit('downloadCount', data.data)
      } else if (data.code === 3) { // 一个文件下载成功
        this.$bus.$emit('downloadSuccess', data.data)
      } else if (data.code === 4) { // 一个文件上传成功
        this.$bus.$emit('uploadSuccess', data.data)
      } else if (data.code === 500) { // 下载文件失败
        this.$bus.$emit('downloadError')
      }
    },
    setOncloseMessage() {
      this.websocket.close()
    },
    onbeforeunload() {
      this.websocket.close()
    },
    // websocket发送消息
    send(msg) {
      this.websocket.send(msg)
    },
    closeWebSocket() {
      if (this.websocket) {
        this.websocket.close()
      }
    }
  }
}
</script>

<style scoped>
.app-main {
  /*50 = navbar  */
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;
}
.fixed-header+.app-main {
  padding-top: 50px;
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 15px;
  }
}
.red{
  border: 1px solid red;
}
.center {
  display: flex;
  -webkit-align-items: center;
  align-items: center;
  -webkit-justify-content: center;
  justify-content: center;
}
.el-pagination__total {
    margin-right: 0px;
}
.el-pagination__sizes {
    margin: 0 0 0 0;
}
.el-pagination__jump {
    margin-left: 0px;
}
</style>
