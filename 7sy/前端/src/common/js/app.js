import axios from 'axios'
import allApi from './allApi'
import apiDomain from './apiDomain.js'
import qs from 'qs'
var notifyList=[]
axios.defaults.baseURL = 'api'
axios.defaults.withCredentials = true
axios.interceptors.response.use(res => {

	if (typeof res.data == 'string') {
		if(res.data == 'login'){
			vm.$router.push({
				path: '/login'
			})
		}
		return res.data
	}
	console.log('code====' + res.data.code);
	if (res.data.code == 500) {
		if (res.data.msg == 'NotLogin') {
			vm.$root.Bus.$emit("handleError");
			if (notifyList.length !== 0) {
				notifyList[0].close()
			}
			let notify = vm.$notify({ title: '未登录', message: '自动返回登录页' })
			notifyList[0]=notify
			vm.$router.push({
				path: '/login'
			})
		} else {
			vm.$message({
				message: res.data.msg,
				type: "error"
			});
		}
		return Promise.reject(res.data)
	} else {
		return res.data;
	}
}, error => {
	console.log('err');
	return Promise.reject(error)
})

export default {
	getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.hash.slice(window.location.hash.indexOf('?') + 1).match(reg);
		if (r != null) return unescape(r[2]); return null;
	},
	get(url, data) {
		return axios.get(allApi[url], { params: data })
	},
	
	post(url, data) {
		return axios.post(allApi[url], qs.stringify(data))
	},
	uploadFile(url, data){
		return axios.post(allApi[url], data)
	},
	getUrl(url){
		return allApi[url]
	}
}