import apiDomain from './apiDomain.js'
const appApi = {
  listComIdOverview: '/overview/listComIds',
  listCsPortOverview: '/overview/listCsPorts',

  upload_file: '/upload/uploadFile',
  download: '/download/download',
  getDataOverview: '/download/getDataOverview',
  delete: '/download/delete',
  dropTable: '/download/dropTable',
  clearDownloadedFiles: '/download/clearDownloadedFiles',

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
for (var i in appApi) {
  appApi[i] = 'http://' + apiDomain.ip + appApi[i]
}
export default appApi
