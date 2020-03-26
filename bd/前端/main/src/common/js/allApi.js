const appApi = {
  // 用户管理
  logIn: '/login', // 登入
  logOut: '/logout', // 登出
  get_user: '/user/list', // 查询用户
  add_user: '/user/addUser', // 添加用户
  delete_user: '/user/deleteUser', // 删除用户
  getUserById: '/user/getUserById', // 跳转至修改页面
  update_user: '/user/updateUser', // 修改用户
  // 角色管理
  get_role: '/role/list', // 查询角色
  get_all_role: '/role/getAllRoles', // 查询角色
  add_role: '/role/addRole', // 添加角色
  toUpdate_role: '/role/findRoleByRoleId', // 获取角色by角色id，@unused
  find_role_by_userId: '/role/findRoleByUserId', // 获取角色by用户名
  find_role_with_menus: '/role/findRoleWithMenus', // 获取角色和菜单项
  update_role: '/role/updateRole', // 修改角色
  delete_role: '/role/delete', // 删除角色
  submit_role_authority: '/role/bandRoleAndMenu', // 提交角色权限，@unused

  get_all_approval_role: '/approvalRole/getAllApprovalRoles',
  // 菜单管理
  get_role_authority: '/menu/toGrantTSysRole', // 查询角色权限
  get_menu: '/menu/getAllMenu', // 获取菜单
  add_menu: '/menu/add', // 添加菜单
  delete_menu: '/menu/delete', // 删除菜单
  update_menu: '/menu/update', // 修改菜单
  findLeftTree_menu: '/menu/tree', // 显示左侧菜单，@unused

  get_dept_tree: '/dept/tree',
  add_dept: '/dept/add',
  update_dept: '/dept/update',
  delete_dept: '/dept/delete',
  get_dept_list: '/dept/list',

  get_log: '/log/list', // 日志查询
  delete_log: '/log/delete', // 日志删除
  get_online: '/session/list', // 查询在线用户，@unused
  // get_train_by_trainParam: '/train/getCarsByLine',//获取所有车辆信息
  get_train_by_trainParam: '/bode/train/getTrainByTrainParam', // 获取所有车辆信息
  get_fault_message: '/bode/fault/findFaultDetailsByParams', // 获取故障信息
  get_city_name: '/bode/train/getAllCity', // 获取城市名称
  get_lines_by_cityName: '/bode/train/getLinesByCity', // 获取线路
  get_train_config_info: '/bode/train/getTrainConfigInfo', // 获取车辆配置信息
  get_train_door_count: '/bode/train/getTrainDoorCount', // 获取车辆门个数
  findLineTree: '/bode/train/findLineTree', //

  get_fault_name: '/bode/fault/getAllFaultNames', // 获取所有故障名称
  get_fault_by_cityName_lineName: '/bode/fault/findFaultInfosByCityAndLine', // 根据城市名称和线路名称获取故障信息
  update_faultdetail: '/bode/fault/updateFaultDetails', // 更新故障信息，@unused

  insert_dispatch_msg: '/dispatch/insertDispatch', // 插入分配信息，@unused
  find_dispatch_msg: '/dispatch/findDispatchByDispatchId', // 获取分配信息，@unused
  update_dispatch_msg: '/dispatch/updateDispatch', // 更新分配信息，@unused
  get_fault_data: '/bode/fault/faultData',
  get_fault_digital_data: '/bode/fault/digitalData',

  // 寿命预警界面调取接口
  get_lifemonitor_message: '/bode/lifewarning/listLifeWarningInfoByParams', // 获取寿命预警列表信息
  get_doorlifestate_data: '/lifemonitor/findItemLifeByDoor', // 获取车门状态信息
  get_lifeItemNames: '/bode/lifewarning/getAllLifeItemNames', // 获取所有部件信息
  get_lifeItemName_by_cityName_lineName: '/bode/lifewarning/findLifeInfosByCityAndLine', // 根据城市和线路获取预警部件

  // 亚健康接口调用
  update_subhealthDetail: '/bode/subhealth/updateSubhealthDetail', // 更新亚健康信息，@unused
  find_subhealthDetails: '/bode/subhealth/findSubhealthDetailsByParams', // 获取亚健康信息
  get_subhealthDetail_by_cityName_lineName: '', // 根据城市获取亚健康名称，@unused
  get_subhealthDetails: '/bode/subhealth/getAllSubhealthInfo', // 获取所有亚健康名称和代号
  get_subhealth_data: '/bode/subhealth/subhealthData',
  get_works_name: '', // 获取工作人员信息，@unused
  get_subhealth_digital_data: '/bode/subhealth/digitalData',

  // 维修履历调用接口
  upload_file: '/repair/upload', // 上传图片文件
  create_work_detail: '/repair/create', // 上传工单信息
  commit_work_detail: '/repair/commit', // 上传工单信息
  delete_file_byFilepaths: '/repair/delete', // 删除图片文件
  get_all_workSheet: '/repair/worksheets', // 获取所有的worksheet  之后会增加根据列车信息获取worksheet
  get_worksheet_by_id: '/repair/worksheet', // 通过sheetid获取该id下的所有steps
  get_workStepContent: '/repair/samestep', // 通过id和state获取最新信息
  submit_after_sale: '/repair/aftersale', // 提交售后信息
  submit_quality_invest: '/repair/qualityinvest', // 提交质量调查信息
  submit_quality_examine: '/repair/quality', // 提交质量调查信息
  submit_resolve: '/repair/resolve', // 提交问题解决信息
  submit_appeal: '/repair/appeal', // 提交申诉信息，@unused
  submit_close: '/repair/close', // 提交申诉信息
  get_fault_types: '/repair/getFaultTypes', // 获取所有类型

  submit_repetition: '/repair/repetition', // 提交跟踪驳回信息

  get_communicationsetting_info: '/communicationsetting/findTrainsByParams',
  logOffTrains: '/communicationsetting/logOffTrains', // 注销车辆
  stopReceived: '/communicationsetting/stopReceived', // 停止接收
  resumeReceived: '/communicationsetting/resumeReceived', // 恢复接收

  get_groundDataManager_info: '/dm/train',
  delete_groundDataManager_info: '/dm/train/delete',
  get_data_space: '/dm/space',
  get_project_space: '/dm/project',

  get_trainDataManager_info: '/train/query',
  delete_trainDataManager_info: '/train/delete',
  get_project: '/bode/train/projects',
  get_train_no: '/bode/train/project/trains',

  digital_twin_all: '/digitaltwin/all',

  editSheets: '/mobile/repair/worksheets/edit',
  historySheets: '/mobile/repair/worksheets',

  worksheet: '/repair/worksheet',

  dashboard_train: '/bode/train/dashboard',
  dashboard_sheet: '/repair/dashboard',
  dashboard_fault_today: '/bode/fault/dashboard/today/bode',
  dashboard_fault_annual: '/bode/fault/dashboard/annual',
  dashboard_fault_month: '/bode/fault/dashboard/month',
  dashboard_fault_annual_count: '/bode/fault/owner/dashboard/annual',

  dashboard_subhealth: '/bode/subhealth/dashboard/today/bode',
  dashboard_subhealth_annual: '/bode/subhealth/dashboard/annual',
  dashboard_subhealth_month: '/bode/subhealth/dashboard/month',
  dashboard_subhealth_annual_count: '/bode/subhealth/owner/dashboard/annual',

  count_fault_by_projectName: '/statistics/countFaultByProjectName',
  count_subhealth_by_projectName: '/statistics/countSubhealthByProjectName',
  list_door_type: '/statistics/listDoorType',
  list_effect: '/statistics/listEffect',
  list_fault_mode: '/statistics/listFaultMode',
  list_fault_stage: '/statistics/listFaultStage',
  count_project_fault_by_parms: '/statistics/countProjectFaultByParms',
  count_parts_fault_by_parms: '/statistics/countPartsFaultByParms',
  count_fault_name_by_parms: '/statistics/countFaultNameByParms',

  sheet_export: '/repair/export',

  config_list_variable: '/config/listVariable',
  config_save: '/config/save',
  config_update: '/config/update',
  config_delete: '/config/delete',

  history_record: '/data/records',
  history_data: '/data/datas',
  history_count: '/data/count'

}

export default appApi
