const appApi = {
  download: '/download/download',
  getDataOverview: '/download/getDataOverview',
  delete: '/download/delete',

  getComIdTableDatas: '/data/getComIdTableDatas',
  getCsPortTableDatas: '/data/getCsPortTableDatas',
  getComIdChartData: '/data/getComIdChartData',
  getCsPortChartData: '/data/getCsPortChartData',

  listComIds: '/objectConfig/listComIds',
  listCsPorts: '/objectConfig/listCsPorts',
  comIdImport: '/objectConfig/comIdImport',
  csPortImport: '/objectConfig/csPortImport',

  getTargetConfig: '/targetConfig/getTargetConfig',
  setTargetConfig: '/targetConfig/setTargetConfig',

  listCondition: '/condition/listCondition',
  saveCondition: '/condition/saveCondition',
  deleteCondition: '/condition/deleteCondition',
  editCondition: '/condition/editCondition'

}

export default appApi
