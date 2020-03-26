const appApi = {
  countByMonth: '/bode/statistics/month',
  trains: '/bode/statistics/trains',
  countByYear: '/bode/statistics/year',
  countByFaultType: '/bode/statistics/type/fault',
  countBySubhealthType: '/bode/statistics/type/subhealth',
  countByTopType: '/bode/statistics/type/top',

  get_all_project: '/bode/train/projects',
  get_train_no: '/bode/train/project/trains',
  get_train_config_info: '/bode/train/getTrainConfigInfo', // 获取车辆配置信息

  dashboard_train: '/bode/train/dashboard',
  faultData: '/bode/fault/faultData',
  dashboard_fault_today: '/bode/fault/dashboard/today',

  subhealthData: '/bode/subhealth/subhealthData',
  dashboard_subhealth: '/bode/subhealth/dashboard',

  findDoorItemAVGValue: '/bode/lifeDoorItem/findDoorItemAVGValue'
}

export default appApi
