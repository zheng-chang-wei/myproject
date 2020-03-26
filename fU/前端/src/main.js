import Vue from 'vue'
import App from './App'
import store from './store'
import router from './router'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
// import 'element-ui/lib/theme-chalk/index.css'
import '../theme/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // 组件中语言使用中文

// require('video.js/dist/video-js.css')
require('vue-video-player/src/custom-theme.css')

import '@/styles/index.scss' // global css

import '@/icons' // 图标
// import '@/permission' //页面访问权限

import VideoPlayer from 'vue-video-player'

Vue.use(ElementUI, { locale })
Vue.use(VideoPlayer)

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */
import { mockXHR } from '../mock'
if (process.env.NODE_ENV === 'production') {
  mockXHR()
}

Vue.config.productionTip = false

var EventBus = new Vue()
Object.defineProperties(Vue.prototype, {
  $bus: {
    get: function() {
      return EventBus
    }
  }
})
new Vue({
  el: '#app',
  router,
  store,
  data: {
    Bus: new Vue()
  },
  render: h => h(App)
})
