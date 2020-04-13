import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
// import '@/permission' // permission control
import echarts from 'echarts'
Vue.prototype.$echarts = echarts
// set ElementUI lang to EN
Vue.use(ElementUI, { locale })

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
