/**
 * Created by ASen on 2017/8/18.
 */
import * as types from './mutation-types'
const matutions = {
	[types.SET_HARDWARE](state, hardware) {
		state.hardware = hardware
	},
	[types.SET_TEMPLATE](state, template) {
		state.tem = template
	},
	[types.SET_APP](state, app) {
		state.applicationchi = app
	},
	[types.SET_IMAGE](state, image){
		state.image = image
	},
	[types.SET_APP_NAME](state, name){
		state.app_name = name
	},
	[types.SET_APP_TYPE](state, type){
		state.app_type = type
	},
	[types.SET_APP_HARDWARE](state, hardware){
		state.app_hardware = hardware
	},
	[types.SET_LOGO_URL](state, url){
		state.logo_url = url
	},
	[types.SET_IFRAME](state, iframe) {
		state.iframe= iframe
	},
	[types.SET_BERFORE_PAGE](state, beforePage) {
		localStorage.setItem('beforePage',beforePage)
		state.beforePage= localStorage.getItem('beforePage')
	},
	[types.SET_APP_ID](state, app_id) {
		state.app_id= app_id
	},
	[types.SET_COMPANYID](state, companyId) {
		state.companyId= companyId
	},
	[types.SET_APPID](state, appId) {
		state.appId= appId
	},
    [types.SET_APPTYPEID](state, appTypeId) {
		state.appTypeId= appTypeId
	}
}
export default matutions
