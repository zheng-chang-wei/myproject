import Vue from 'vue'
import Router from 'vue-router'
import Realtimemonitor from '@/pages/realtimemonitor/realtimemonitor'
import Home from '@/pages/home/index'
import Statistics from '@/pages/statistics/index'
Vue.use(Router)

export default new Router({
  // mode: 'history',
  // base: '/bode',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/realtimemonitor',
      name: 'Realtimemonitor',
      component: Realtimemonitor
    },
    {
      path: '/statistics',
      name: '统计',
      component: Statistics
    }]
})
