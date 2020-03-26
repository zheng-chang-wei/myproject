import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
// import 'element-ui/lib/theme-chalk/index.css'
import '../theme/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */
import {
  mockXHR
} from '../mock'
if (process.env.NODE_ENV === 'production') {
  mockXHR()
}

import echarts from 'echarts'
Vue.prototype.$echarts = echarts

// set ElementUI lang to EN
Vue.use(ElementUI, {
  locale
})

import Vant from 'vant'
import 'vant/lib/index.css'
import 'vant/lib/icon/local.css'

import global_ from './components/Global' // 引用文件
Vue.prototype.GLOBAL = global_
Vue.config.productionTip = false

Vue.use(Vant)
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
