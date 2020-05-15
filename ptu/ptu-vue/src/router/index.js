import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },

  {
    path: '/download',
    component: Layout,
    children: [
      {
        path: '',
        name: 'download',
        component: () => import('@/views/download/index'),
        meta: { title: '数据管理', icon: 'tree' }
      }
    ]
  },

  {
    path: '/conditionConfig',
    component: Layout,
    redirect: '/conditionConfig/object',
    meta: {
      title: '条件配置',
      icon: 'example'
    },
    children: [
      {
        path: 'object',
        component: () => import('@/views/conditionConfig/object/index'), // Parent router-view
        meta: { title: '对象配置', icon: 'form' }
      },
      {
        path: 'condition',
        component: () => import('@/views/conditionConfig/condition/index'),
        meta: { title: '逻辑条件配置', icon: 'form' }
      }
    ]
  },
  {
    path: '/data',
    component: Layout,
    redirect: '/data/ComId',
    meta: {
      title: '数据展示',
      icon: 'nested'
    },
    children: [
      {
        path: 'ComId',
        component: () => import('@/views/dataDisplay/ComId'), // Parent router-view
        meta: { title: 'ComID对象', icon: 'table' }
      },
      {
        path: 'CsPort',
        component: () => import('@/views/dataDisplay/CsPort'),
        meta: { title: 'CS端口对象', icon: 'table' }
      }
    ]
  },

  // {
  //   path: '/form',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       name: 'Form',
  //       component: () => import('@/views/parmConfig/index'),
  //       meta: { title: '参数配置', icon: 'form' }
  //     }
  //   ]
  // },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
