import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [{
  path: '/',
  component: () =>
    import('@/views/login/index'),
  hidden: true
},

{
  path: '/404',
  component: () =>
      import('@/views/404'),
  hidden: true
},

{
  path: '/dashboard',
  component: Layout,
  children: [{
    path: '',
    name: 'Dashboard',
    component: () =>
        import('@/views/dashboard/index'),
    meta: {
      title: '首页',
      icon: 'home'
    }
  }]
},
{
  path: '/algorithm',
  component: Layout,
  redirect: '/algorithm/monitor',
  name: 'Algorithm',
  children: [{
    path: 'monitor',
    name: 'monitor',
    component: () => import('@/views/algorithm/index'),
    meta: {
      title: '算法监控',
      icon: 'algorithm'
    }
  }]
},
{
  path: '/config',
  component: Layout,
  redirect: '/config/configManagement',
  name: 'Config',
  children: [{
    path: 'configManagement',
    name: 'ConfigManagement',
    component: () =>
        import('@/views/setting/index'),
    meta: {
      title: '配置管理',
      icon: 'config'
    }
  }]
},
{
  path: '/data',
  component: Layout,
  redirect: '/data/raw',
  name: 'Data',
  meta: {
    title: '数据管理',
    icon: 'data'
  },
  children: [{
    path: 'raw',
    name: 'Raw',
    component: () =>
        import('@/views/data/raw/index'),
    meta: {
      title: '原始数据',
      icon: 'table'
    }
  },
  {
    path: 'analysis',
    name: 'Analysis',
    component: () =>
          import('@/views/data/result/index'),
    meta: {
      title: '分析数据',
      icon: 'analysis'
    }
  }
  ]
},
{
  path: '/system',
  component: Layout,
  redirect: '/system/user',
  name: 'System',
  meta: {
    title: '系统管理',
    icon: 'setting'
  },
  children: [{
    path: 'user',
    name: 'User',
    component: () =>
        import('@/views/system/user/index'),
    meta: {
      title: '用户管理',
      icon: 'user'
    }
  },
  {
    path: 'log',
    name: 'Log',
    component: () =>
          import('@/views/tree/index'),
    meta: {
      title: '日志管理',
      icon: 'log'
    }
  },
  {
    path: 'update',
    name: 'Update',
    component: () =>
          import('@/views/tree/index'),
    meta: {
      title: '版本更新',
      icon: 'update'
    }
  },
  {
    path: 'config',
    name: 'SoftConfig',
    component: () =>
          import('@/views/tree/index'),
    meta: {
      title: '软件设置',
      icon: 'softConfig'
    }
  }]
},
// 404 page must be placed at the end !!!
{
  path: '*',
  redirect: '/404',
  hidden: true
}
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({
    y: 0
  }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
