<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <router-view :key="key" />
    </transition>
  </section>
</template>

<script>
// const serverList = ['10.40.16.19', '192.168.40.33']
const serverList = ['localhost', '192.168.40.33']
export default {
  name: 'AppMain',
  data: function() {
    return {
      faultNames: ['输出断路', '开关门超时', '编码器故障', '锁闭开关故障', '绿色环线故障', '门板开关故障', '电机过流', '电机开路'],
      warning: '',
      dataObj: {},
      roleName: '',
      menus: [],
      websocketServerIdx: -1,
      websocketServerValid: 0,
      websocket: {
        readyState: 0
      }
    }
  },
  computed: {
    key() {
      return this.$route.path
    }
  },
  mounted() {
    var vm = this
    this.$bus.$on('sendMessage', function(value) {
      vm.sendMessage(value)
    })
  },
  created() {
    this.initWebSocket()
  },
  beforeDestroy() {
    this.onbeforeunload()
    this.$bus.$off()
  },
  methods: {
    initWebSocket() {
      if ('WebSocket' in window) {
        if (this.websocketServerValid === 0) {
          this.websocketServerIdx++
        }
        if (this.websocketServerIdx >= serverList.length) {
          this.websocketServerValid = -1
          return
        }
        var websocketServer = serverList[this.websocketServerIdx]
        this.websocket = new WebSocket('ws://' + websocketServer + ':8082/websocket')
        console.log('ws://' + websocketServer + ':8082/websocket')
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
      this.msg =
					'WebSocket连接发生错误' + '   状态码：' + this.websocket.readyState
      // console.log(this.msg);
    },
    setOnopenMessage() {
      this.websocketServerValid = 1
      this.msg =
					'WebSocket连接成功' + '   状态码：' + this.websocket.readyState
      console.log(this.msg)
    },
    setOnmessageMessage(event) {
      var data = JSON.parse(event.data)
      var faultPackets = data.fault
      if (faultPackets) {
        // 将故障信息传入到instantnotice界面
        this.$bus.$emit('FaultMessage', faultPackets)
        var faults = this.handleFaultMessage(faultPackets)
        if (faults.length > 0) {
          this.warning = faults[0].detail
          this.$bus.$emit('updateFaultMessage', faults)
        }
      }
      var packet = data.packet
      if (packet) {
        this.$bus.$emit('updateRealTimeMessage', data)
      }
    },
    setOncloseMessage() {
      this.msg =
					'WebSocket连接关闭' + '   状态码：' + this.websocket.readyState
      console.log(this.msg)
      if (this.websocketServerValid === 0) {
        this.initWebSocket()
      }
    },
    onbeforeunload() {
      this.closeWebSocket()
    },
    // websocket发送消息
    sendMessage(msg) {
      if (this.websocket.readyState !== 1) {
        this.websocket.close()
        this.initWebSocket()
        setTimeout(() => {
          if (this.websocket.readyState === 1) {
            this.websocket.send(msg)
          }
        }, 250)
      } else {
        this.websocket.send(msg)
      }
    },
    closeWebSocket() {
      this.websocket.close()
    },
    handleFaultMessage(faultPackets) {
      var faults = []
      for (var index = 0; index < faultPackets.length; index++) {
        var fault = faultPackets[index]
        var messages = fault.messages
        for (var j = 0; j < messages.length; j++) {
          var message = messages[j]
          var faultMessage = {
            detail: '',
            type: ''
          }
          faultMessage.detail = message.timestamp + ' ' + fault.city + '地铁' + fault.line + '号线' + '-' + fault.train + '号车' +
							' 车厢：' + message.carriageId + ' 车门：' + message.doorId + '  ' + message.faultName
          faultMessage.type = '故障'
          faults.push(faultMessage)
        }
      }
      return faults.reverse().slice(0, 10)
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

<style>
.center {
  display: flex;
  -webkit-align-items: center;
  align-items: center;
  -webkit-justify-content: center;
  justify-content: center;
}

.red {
  border-width: 1px;
  border-style: solid;
  border-color: red;
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 15px;
  }
}
</style>
