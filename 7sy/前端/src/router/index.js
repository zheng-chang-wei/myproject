import Vue from 'vue'
import Router from "vue-router";
import Login from "pages/login";
import Main from "pages/main";
import Home from "pages/home";
const User = () => import("pages/system/user");
const Role = () => import("pages/system/role");
const Menu = () => import("pages/system/menu");
const Log = () => import("pages/system/log");
const Online = () => import("pages/system/online");
const Data = () => import("pages/system/data");
const DetectionByCar = () => import("pages/detection/detectionByCar");
const StatisticsByCar = () => import("pages/statistics/statisticsByCar");
const StatisticsByPoint = () => import("pages/statistics/statisticsByPoint");
const HistoryData = () => import("pages/history/historyData");
const Test = () => import("pages/test");


Vue.use(Router)

export default new Router({
	routes: [{
			path: "/",
			redirect: "/login"
		},
		{
			path: "/login",
			component: Login
		},

		{
			path: "/main",
			component: Main,
			children: [{
					path: "",
					redirect: "/home"
			},
			{
				path: "/test",
				component: Test
			},
				{
					path: "/home",
					component: Home,
					name: "首页"
				},

				{
					path: "/system/user",
					component: User,
					name: "用户管理",
					meta: {
						keepAlive: true // 需要被缓存
					}
				},
				{
					path: "/system/role",
					component: Role,
					name: "角色管理"
				},
				{
					path: "/system/menu",
					component: Menu,
					name: "菜单管理"
				},
				{
					path: "/system/log",
					component: Log,
					name: "日志管理",
					meta: {
						keepAlive: true // 需要被缓存
					}
				},
				{
					path: "/system/online",
					component: Online,
					name: "在线用户"
				},
				{
					path: "/system/data",
					component: Data,
					name: "平台数据管理",
					meta: {
						keepAlive: false // 不需要被缓存
					}
				},
				{
					path: "/detection/detectionByCar",
					component: DetectionByCar,
					name: "异常检测按车辆",
					meta: {
						keepAlive: false // 不需要被缓存
					}
				},
				{
					path: "/statistics/statisticsByCar",
					component: StatisticsByCar,
					name: "异常统计按车辆",
					meta: {
						keepAlive: true // 需要被缓存
					}
				},
				{
					path: "/statistics/statisticsByPoint",
					component: StatisticsByPoint,
					name: "异常统计按测点",
					meta: {
						keepAlive: true // 需要被缓存
					}
				},
				{
					path: "/history/historyData",
					component: HistoryData,
					name: "历史数据查询",
					meta: {
						keepAlive: false // 不需要被缓存
					}
				},
			]
		}
	]
});
