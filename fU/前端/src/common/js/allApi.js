const appApi = {
  // 用户管理
  logIn: '/login', // 登入
  logOut: '/logout', // 登出
  list_all_protocols: '/setting/all', // 获取所有配置
  get_setting_by_id: '/setting/select', // 根据id获取配置

  upload_protocol: '/setting/upload/protocol', // 上传数据流文件
  upload_script: '/setting//upload/script', // 上传脚本文件
  save_protocol: '/setting/update', // 保存配置
  activate_protocol: '/setting/activate', // 激活配置
  delete_protocol: '/setting/delete', // 删除配置
  select_all_subsystems: '/setting/subsystems', // 查询所有子系统
  export_setting: '/setting/export', // 配置文件导出
  import_setting: '/setting/import', // 配置文件导入

  save_basic: '/setting/update/basic', // 保存基本信息
  save_board: '/setting/update/board', // 保存板卡信息
  save_algorithm: '/setting/update/algorithm', // 保存算法信息
  save_output: '/result/ecn/save', // 保存输出信息
  save_store: '/setting/update/store', // 保存存储信息
  save_time: '/setting/update/time', // 保存时间信息

  list_variables: '/protocol/variables', // 查询变量列表

  board_list: '/board/list',
  board_list_force: '/board/list/force',
  board_abstract: '/board/count',
  board_history: '/board/history',

  algorithm_list: '/algorithm/list',
  algorithm_abstract: '/algorithm/count',
  algorithm_history: '/algorithm/history',
  algorithm_phm_board: '/algorithm/phmboards',

  data_root: '/data/root',
  data_list: '/data/list',
  data_query: '/data/query', // 查询原始文件列表
  data_header: '/data/header', // 解析选中原始文件头信息
  data_datas: '/data/datas', // 查询原始数据
  data_delete: '/data/delete', // 删除原始数据文件
  data_download: 'data/download', // 下载原始数据文件

  result_all: '/result/all', // 查询分析文件列表
  result_list: '/result/list', // 查询分析文件列表
  result_header: '/result/header', // 解析分析文件头信息
  result_delete: '/result/delete', // 删除分析数据
  result_download: '/result/download', // 下载分析数据文件
  result_segment_header: '/result/segment/header', // 解析分析文件头信息
  result_datas: '/result/datas', // 查询分析数据
  result_video: '/result/video',
  result_output_setting: '/result/ecn/getOutputSetting', // 根据id获取输出配置

  subsystem_listSubsystem_by_parms: '/subsystem/list',
  subsystem_save: '/subsystem/save',
  subsystem_update: '/subsystem/update',
  subsystem_delete_by_id: '/subsystem/deleteById',

  users: '/rbac/users',
  user: '/rbac/user',
  user_delete: '/rbac/user/delete',
  roles: '/rbac/roles',

  state: '/state',
  states: '/states',
  launch: '/launch',
  terminate: '/terminate',
  launch_steps: '/launch/steps'
}

export default appApi
