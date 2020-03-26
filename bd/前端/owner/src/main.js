// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'

import 'element-ui/lib/theme-chalk/index.css'
import echarts from 'echarts'
Vue.prototype.$echarts = echarts
Vue.use(ElementUI)
Vue.config.productionTip = false
var EventBus = new Vue()
Object.defineProperties(Vue.prototype, {

  $bus: {
    get: function () {
      return EventBus
    }
  }
})
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  data: {
    Bus: new Vue()
  },
  template: '<App/>'
})
