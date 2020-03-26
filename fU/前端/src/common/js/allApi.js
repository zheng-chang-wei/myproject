const appApi = {
  // 用户管理
  logIn: '/login', // 登入
  logOut: '/logout', // 登出
  list_all_protocols: '/setting/all', // 获取所有配置
  get_setting_by_id: '/setting/select', // 获取所有配置
  upload_protocol: '/setting/upload/protocol', // 上传数据流文件
  upload_script: '/setting//upload/script', // 上传脚本文件
  save_protocol: '/setting/update', // 保存配置
  activate_protocol: '/setting/activate', // 激活配置
  delete_protocol: '/setting/delete', // 删除配置
  select_all_subsystems: '/setting/selectAllSubsystems', // 查询所有子系统
  export_setting: '/setting/export', // 配置文件导出
  import_setting: '/setting/import', // 配置文件导入

  board_list: '/board/list',
  board_abstract: '/board/count',
  board_history: '/board/history',

  algorithm_list: '/algorithm/list',
  algorithm_abstract: '/algorithm/count',
  algorithm_history: '/algorithm/history',

  data_root: '/data/root',
  data_list: '/data/list',
  data_query: '/data/query', // 查询原始文件列表
  data_header: '/data/header', // 解析选中原始文件头信息
  result_all: '/result/all', // 查询分析文件列表
  result_list: '/result/list', // 查询分析文件列表
  result_header: '/result/header', // 解析分析文件头信息
  result_video: '/result/video',

  subsystem_listSubsystem_by_parms: '/subsystem/listSubsystemByParms',
  subsystem_save: '/subsystem/save',
  subsystem_update: '/subsystem/update',
  subsystem_delete_by_id: '/subsystem/deleteById'

}

export default appApi
