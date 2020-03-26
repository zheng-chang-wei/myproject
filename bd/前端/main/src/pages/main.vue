<template>
  <div id="app">
    <div id="top" style="overflow:hidden">
      <div class="left">
        <el-button icon="el-icon-watch" @click="redirect(&quot;/realtimemonitor/realtimemonitor&quot;)">实时监控</el-button>
        <el-button v-if="checkButtonVisible('故障信息')" icon="el-icon-info" @click="redirect(&quot;/faultinformation/showFaultInformation&quot;)">故障信息</el-button>
        <el-button v-if="checkButtonVisible('亚健康预警')" icon="el-icon-circle-plus" @click="redirect(&quot;/unhealthymonitor/unhealthymonitor&quot;)">亚健康预警</el-button>
        <el-button v-if="checkButtonVisible('寿命预警')" icon="el-icon-timer" @click="redirect(&quot;/lifemonitor/lifemonitor&quot;)">寿命监控</el-button>
        <el-button v-if="checkButtonVisible('维修履历')" icon="el-icon-document" @click="redirect(&quot;/repairhistory/repairhistory&quot;)">维修履历</el-button>
        <el-button v-if="checkButtonVisible('系统设置')" icon="el-icon-edit" @click="redirect(&quot;/system/systemsetting&quot;)">系统设置</el-button>
        <el-button v-if="checkButtonVisible('权限管理')" icon="el-icon-edit" @click="redirect(&quot;/system/authorityManagement&quot;)">权限管理</el-button>
      </div>
      <ul class="right">
        <li>
          <el-button @click="logout">退出</el-button>
        </li>
      </ul>
    </div>
    <div id="bottom">
      <div id="router-view">
        <router-view />
      </div>
    </div>
  </div>
</template>
<script>
import app from 'common/js/app'
// import warning from 'components/warning.vue'
const serverList = ['192.168.40.33', '180.168.223.150']
export default {
  name: 'App',
  components: {
    // warning
  },
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
    username: function() {
      return localStorage.getItem('username')
    }
  },
  mounted() {
    // this.getRoleName();
    this.getRoleWithMenus()
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
    // 退出登录
    logout() {
      var vm = this
      this.$confirm('确认退出登录吗?', '提示', {
        type: 'warning'
      }).then(() => {
        app.post('logOut').then(d => {
          sessionStorage.removeItem('userInfo')
          vm.$router.push({
            path: '/login'
          })
        })
      }).catch(() => {})
    },
    getRoleName() {
      app.get('find_role_by_userId').then(d => {
        this.dataObj = {} // 真正实现数据更新的是这行代码
        this.dataObj['roleName'] = d.msg
      })
    },
    getRoleWithMenus() {
      var vm = this
      app.get('find_role_with_menus').then(data => {
        const role = data.msg
        vm.roleName = role.roleName
        vm.menus = []
        for (var i = 0; i < role.menus.length; i++) {
          vm.menus.push(role.menus[i].menuName)
        }
        localStorage.setItem('menus', vm.menus.join(','))
      })
    },
    checkButtonVisible(name) {
      return this.menus.indexOf(name) > -1
    },
    redirect(path) {
      this.$router.push(path)
    },
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
<style>
	html,
	body,
	#app {
		margin: 0;
		height: 100%;
		background: #f6f6f8;
		font-size: 14px;
	}

	#top {
		flex-shrink: 0;
		height: 100px;
		width: 100%;
		line-height: 70px;
		border-bottom: 1px solid #e6e6e6;
		margin-bottom: -1px;
		background: #fff;
		z-index: 30;
	}

	#top .clears {
		clear: both;
	}

	#top .left {
		float: left;
		padding-left: 20px;
		margin-left: 2px;
		color: #0f8ac6;
		font-size: 24px;
		letter-spacing: 4px;
	}

	#top .right {
		float: right;
		padding: 0;
		margin: 0;
		color: #969696;
	}

	#top .right li {
		list-style: none;
		float: left;
		margin-right: 40px;
		height: 50px;
		padding-left: 30px;
		position: relative;
		cursor: pointer;
	}

	/* #top .right li:last-child {
  margin-right: 15px;
}
#top .right li:nth-child(3)::before,
#top .right li:nth-child(4)::before {
  width: 1px;
  top: 10px;
  left: -20px;
}
#top .right li:nth-child(4)::before {
  left: -30px;
}
#top .right li:not(:first-child)::before {
  display: block;
  content: "";
  height: 44px;
  position: absolute;
  background: #e6e6e6;
} */

	#top .right li i {
		position: absolute;
		font-size: 24px;
		left: -6px;
	}

	#app {
		display: flex;
		flex-direction: column;
		overflow: hidden;
		width: 100%;
	}

	#app>#bottom {
		display: flex;
		flex-grow: 1;
		align-items: stretch;
		height: 100%;
		width: 100%;
	}

	#app>#bottom>div {
		overflow: auto;
	}

	#app>#bottom>#router-view {
		flex-grow: 1;
		width: 100%;
		padding-top: 10px;
		box-sizing: border-box;
	}

</style>
