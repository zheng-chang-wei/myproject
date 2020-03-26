<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import apiDomain from './common/js/apiDomain.js'
export default {
  name: 'App',
  data () {
    return {
      serverList: [apiDomain.ip, '192.168.40.33'],
      websocketServerIdx: -1,
      websocketServerValid: 0,
      websocket: {
        readyState: 0
      }
    }
  },
  mounted () {
    this.initWebSocket()
    this.$bus.$on('sendMessage', (value) => {
      this.sendMessage(value)
    })
  },
  beforeDestroy () {
    this.onbeforeunload()
    this.$bus.$off()
  },
  methods: {
    initWebSocket () {
      if ('WebSocket' in window) {
        if (this.websocketServerValid === 0) {
          this.websocketServerIdx++
        }
        if (this.websocketServerIdx >= this.serverList.length) {
          this.websocketServerValid = -1
          return
        }
        let websocketServer = this.serverList[this.websocketServerIdx]
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
    setErrorMessage () {
      let msg = 'WebSocket连接发生错误' + '   状态码：' + this.websocket.readyState
      console.log(msg)
    },
    setOnopenMessage () {
      this.websocketServerValid = 1
      let msg = 'WebSocket连接成功' + '   状态码：' + this.websocket.readyState
      console.log(msg)
    },
    setOnmessageMessage (event) {
      let data = JSON.parse(event.data)
      let faultPackets = data.fault
      if (faultPackets) {
        // 将故障信息传入到instantnotice界面
        this.$bus.$emit('FaultMessage', faultPackets)
        let faults = this.handleFaultMessage(faultPackets)
        if (faults.length > 0) {
          this.warning = faults[0].detail
          this.$bus.$emit('updateFaultMessage', faults)
        }
      }

      let packet = data.packet
      if (packet) {
        this.$bus.$emit('updateRealTimeMessage', data)
      }
    },
    setOncloseMessage () {
      let msg = 'WebSocket连接关闭' + '   状态码：' + this.websocket.readyState
      console.log(msg)
      if (this.websocketServerValid === 0) {
        this.initWebSocket()
      }
    },
    onbeforeunload () {
      this.closeWebSocket()
    },
    // websocket发送消息
    sendMessage (msg) {
      console.log(this.websocket.readyState)

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
    closeWebSocket () {
      this.websocket.close()
    },
    handleFaultMessage (faultPackets) {
      let faults = []
      for (let index = 0; index < faultPackets.length; index++) {
        let fault = faultPackets[index]
        let messages = fault.messages
        for (let j = 0; j < messages.length; j++) {
          let message = messages[j]
          let faultMessage = {
            detail: '',
            type: ''
          }
          faultMessage.detail = message.timestamp + ' ' + fault.city + '地铁' + fault.line + '号线' + '-' + fault.train +
              '号车' + ' 车厢：' + message.carriageId + ' 车门：' + message.doorId + '  ' + message.faultName
          faultMessage.type = '故障'
          faults.push(faultMessage)
        }
      }

      return faults.reverse().slice(0, 10)
    }
  }
}

</script>

<style>
body {
  margin: 0px;
  height: 100%;
  background-color: #0f1741;
}

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
  font-size: 10px;
}

.red {
  border-width: 1px;
  border-style: solid;
  border-color: red;
}

.center {
  display: flex;
  -webkit-align-items: center;
  align-items: center;
  -webkit-justify-content: center;
  justify-content: center;
}
</style>
