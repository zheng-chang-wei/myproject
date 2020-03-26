
import apiDomain from './apiDomain.js'
const appApi = {
	// 用户管理
	logIn: '/login', // 登入
	logOut: '/logout', // 登出
	get_user: '/user/list', // 查询用户
	add_user: '/user/addUser', // 添加用户
	delete_user: '/user/deleteUser', // 删除用户
	toUpdate_user: '/user/getUser', // 跳转至修改页面
	update_user: '/user/updateUser', // 修改用户
	edit_user: '/user/editUser', // 修改当前用户信息
	get_current_user: '/user/getCurrentUser', // 获取当前登陆用户
	// 角色管理
	get_role: '/role/list', // 查询角色
	get_all_role: '/role/getAllRoles', // 查询角色
	add_role: '/role/addRole', // 添加角色
	toUpdate_role: '/role/findRoleByRoleId', // 获取角色by角色id
	find_role_by_userId: '/role/findRoleByUserId', // 获取角色by用户名
	update_role: '/role/updateRole', // 修改角色
	delete_role: '/role/delete', // 删除角色
	submit_role_authority: '/role/bandRoleAndMenu', // 提交角色权限
	// 菜单管理
	get_role_authority: '/menu/toGrantTSysRole', // 查询角色权限
	get_menu: '/menu/getAllMenu', // 获取菜单
	add_menu: '/menu/add', // 添加菜单
	delete_menu: '/menu/delete', // 删除菜单
	update_menu: '/menu/update', // 修改菜单
	findLeftTree_menu: '/menu/tree', // 显示左侧菜单



	exception_statistics_byCar: '/exceptionStatisticsByCar', //异常统计按车辆
	exception_statistics_byPoint: '/exceptionStatisticsByPoint', //异常统计按测点
	exception_statistics_table: '/findTableData', //异常统计表格数据
	exception_statistics_detail: '/detail', //异常统计查看详情

	get_log: '/log/list', //日志查询
	delete_log: '/log/delete', //日志删除

	get_online:'/session/list',//查询在线用户

	get_datas:'/data/list',//查询数据
	delete_datas:'/data/delete',//删除数据
	drop_table:'/data/dropTable',//删除数据
	get_storage:'/data/getStorage',//获取服务器存储空间
	
	get_lastMonth_exceptionData:'/initialdata/listLastMonthExceptionData',//获取最后一个月异常次数
	get_data_by_trainIdAndtrainType:'/initialdata/getdata',//按客户，车型，车号获取历史数据

	get_train_type: '/traininfo/trainType',//获取所有车辆类型
	get_trainNum_by_type: '/traininfo/trainNumByType',//根据车型获取车号
	get_trainNums: '/traininfo/trainNums',//获取车号

	upload_initialdata_file: '/initialdata/uploadfile',//上传文件


	get_axle_exception_state_data:'/exceptiondata/getdata',//获取轴的异常状态信息
	get_axle_exception_data:'/exceptiondata/getAxledata',//获取异常轴的具体数值

}

// for (var i in appApi) {
// 	appApi[i] = "http://"+apiDomain.ip + appApi[i]
// }
export default appApi
