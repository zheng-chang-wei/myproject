import Vue from 'vue'
import Router from 'vue-router'
import Mail from '@/pages/mail'
import Mobile from '@/pages/mobile/mobile'
import MobileMail from '@/pages/mobile/Notice'
import MobileLogin from '@/pages/mobile/login'
import MobileMain from '@/pages/mobile/main'
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
  path: '/login',
  component: () =>
      import('@/pages/login'),
  hidden: true
},

// {
//   path: '/',
//   redirect: '/login'
// },
{
  path: '/404',
  component: () =>
      import('@/views/404'),
  hidden: true
},

{
  path: '/',
  component: Layout,
  redirect: '/dashboard',
  name: '首页',
  children: [{
    path: 'dashboard',
    name: 'Dashboard',
    component: () =>
        import('@/pages/home/index'),
    meta: {
      keepAlive: false,
      title: '首页',
      icon: 'home'
    }
  }]
},
{
  path: '/realtimemonitor',
  component: Layout,
  redirect: '/realtimemonitor/realtimemonitor',
  name: '实时监控',
  children: [{
    path: 'realtimemonitor',
    name: 'Realtimemonitor',
    component: () =>
        import('@/pages/realtimemonitor/realtimemonitor'),
    meta: {
      title: '实时监控',
      icon: 'eye-open',
      keepAlive: true
    }
  }]
},
{
  path: '/faultinformation',
  component: Layout,
  redirect: '/faultinformation/showFaultInformation',
  name: '故障信息',
  children: [{
    path: 'showFaultInformation',
    name: 'Raultinformation',
    component: () =>
        import('@/pages/faultinformation/showFaultInformation'),
    meta: {
      keepAlive: false,
      title: '故障信息',
      icon: '故障'
    }
  }]
},
{
  path: '/unhealthymonitor',
  component: Layout,
  redirect: '/unhealthymonitor/unhealthymonitor',
  name: '亚健康预警',
  children: [{
    path: 'unhealthymonitor',
    name: 'Unhealthymonitor',
    component: () =>
        import('@/pages/unhealthymonitor/unhealthymonitor'),
    meta: {
      keepAlive: false,
      title: '亚健康预警',
      icon: '亚健康'
    }
  }]
},
{
  path: '/lifemonitor',
  component: Layout,
  redirect: '/lifemonitor/lifemonitor',
  name: '寿命预警',
  children: [{
    path: 'lifemonitor',
    name: 'Lifemonitor',
    component: () =>
        import('@/pages/lifemonitor/lifePart'),
    meta: {
      keepAlive: false,
      title: '寿命预警',
      icon: 'caution'
    }
  }]
},
{
  path: '/historydata',
  component: Layout,
  redirect: '/history/data',
  name: '历史数据',
  children: [{
    path: 'historydata',
    name: 'historydata',
    component: () => import('@/pages/historydata/index'),
    meta: {
      keepAlive: false,
      title: '历史数据',
      icon: 'database'
    }
  }]
},
{
  path: '/repairhistory',
  component: Layout,
  redirect: '/repairhistory/repairhistory',
  name: '维修履历',
  children: [{
    path: 'repairhistory',
    name: 'Repairhistory',
    component: () =>
          import('@/pages/repairhistory/repairhistory'),
    meta: {
      keepAlive: false,
      title: '维修履历',
      icon: 'form'
    }
  }, {
    path: 'addFault',
    name: 'addFault',
    component: () =>
          import('@/pages/addFaultInformation/addFault'),
    meta: {
      keepAlive: false,
      title: '添加维修履历',
      icon: 'table'
    },
    hidden: true
  }, {
    path: 'config',
    name: 'config',
    component: () =>
          import('@/pages/addFaultInformation/configVariable'),
    meta: {
      keepAlive: false,
      title: '自定义变量',
      icon: 'table'
    },
    hidden: true
  }]
},
{
  path: '/system',
  component: Layout,
  redirect: '/system/groundDataManager',
  name: '系统设置',
  meta: {
    keepAlive: false,
    title: '系统设置',
    icon: 'setting'
  },
  children: [{
    path: 'groundDataManager',
    name: 'GroundDataManager',
    component: () =>
          import('@/components/systemsetting/groundDataManager'),
    meta: {
      keepAlive: false,
      title: '地面端数据管理',
      icon: 'ground'
    }
  },
  {
    path: 'trainDataManager',
    name: 'TrainDataManager',
    component: () =>
          import('@/components/systemsetting/trainDataManager'),
    meta: {
      keepAlive: false,
      title: '车载端数据管理',
      icon: 'train'
    }
  },
  {
    path: 'communicationSetting',
    name: 'CommunicationSetting',
    component: () =>
          import('@/components/systemsetting/communicationSetting'),
    meta: {
      keepAlive: false,
      title: '通信设置',
      icon: 'communication'
    }
  },
  {
    path: 'digitalTwins',
    name: 'DigitalTwins',
    component: () =>
          import('@/components/systemsetting/digitalTwins'),
    meta: {
      keepAlive: false,
      title: '数字孪生管理',
      icon: '数字孪生'
    }
  },
  {
    path: 'log',
    name: 'Log',
    component: () =>
          import('@/components/systemsetting/log'),
    meta: {
      keepAlive: false,
      title: '系统日志管理',
      icon: '日志'
    }
  }
  ]
},
{
  path: '/authorityManagement',
  component: Layout,
  redirect: '/authorityManagement/user',
  name: '权限管理',
  meta: {
    keepAlive: false,
    title: '权限管理',
    icon: 'password'
  },
  children: [{
    path: 'user',
    name: 'user',
    component: () =>
          import('@/components/authorityManagement/user'),
    meta: {
      keepAlive: false,
      title: '用户管理',
      icon: 'user'
    }
  }, {
    path: 'role',
    name: 'role',
    component: () =>
          import('@/components/authorityManagement/role'),
    meta: {
      keepAlive: false,
      title: '角色管理',
      icon: 'role'
    }
  }, {
    path: 'dept',
    name: 'dept',
    component: () =>
          import('@/components/authorityManagement/dept'),
    meta: {
      keepAlive: false,
      title: '部门管理',
      icon: 'tree'
    }
  }
  ]
},
{
  path: '/statistics',
  component: Layout,
  redirect: '/statistics/faultUnhealthy',
  name: '统计',
  meta: {
    keepAlive: false,
    title: '统计',
    icon: '统计'
  },
  children: [{
    path: 'faultUnhealthy',
    name: 'faultUnhealthy',
    component: () =>
          import('@/pages/statistics/faultUnhealthy'),
    meta: {
      keepAlive: false,
      title: '故障和亚健康类别',
      icon: 'line'
    }
  }, {
    path: 'projectFaultCountAndRate',
    name: 'projectFaultCountAndRate',
    component: () =>
          import('@/pages/statistics/projectFaultCountAndRate'),
    meta: {
      keepAlive: false,
      title: '项目故障数和故障率类别',
      icon: '柱状图'
    }
  }, {
    path: 'faultparts',
    name: 'faultparts',
    component: () =>
          import('@/pages/statistics/faultParts'),
    meta: {
      keepAlive: false,
      title: '故障部件类别',
      icon: '多维柱状图'
    }
  }, {
    path: 'projectFaultName',
    name: 'projectFaultName',
    component: () =>
          import('@/pages/statistics/projectFaultName'),
    meta: {
      keepAlive: false,
      title: '项目与故障类型类别',
      icon: '柱状图'
    }
  }]
},
{
  path: '/mail',
  component: Mail,
  name: '邮件跳转',
  hidden: true
},
{
  path: '/mobile/login',
  component: MobileLogin,
  name: '移动端登录',
  hidden: true
},
{
  path: '/mobile/mail',
  component: MobileMail,
  name: '跳转',
  hidden: true
},
{
  path: '/mobile',
  component: Mobile,
  hidden: true,
  children: [{
    path: '',
    redirect: '/mobile/main'
  }, {
    path: '/mobile/main',
    component: MobileMain
  }
    // 404 page must be placed at the end !!!
  ]
},
{ path: '*', redirect: '/404', hidden: true }
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
