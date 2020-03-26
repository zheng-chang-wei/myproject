import axios from 'axios'
import allApi from './allApi'
import qs from 'qs'
import router from '../../router'
import { Notify, Message } from 'element-ui'
axios.defaults.baseURL = 'api'
var notifyList = []
axios.defaults.withCredentials = true
axios.interceptors.response.use(res => {
  if (res.data.code === 500 || res.data.code === '500') {
    console.log('code====' + res.data.code)
    if (res.data.msg === 'NotLogin') {
      if (notifyList.length !== 0) {
        notifyList[0].close()
      }
      const notify = Notify({ title: '未登录', message: '自动返回登录页' })
      notifyList[0] = notify
      router.push({
        path: '/login'
      })
    } else {
      Message({
        message: res.data.msg,
        type: 'error'
      })
    }
    return Promise.reject(res.data)
  } else {
    return res.data
  }
}, error => {
  console.log('err')
  return Promise.reject(error)
})

export default {
  // getQueryString(name) {
  //   var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i')
  //   var r = window.location.hash.slice(window.location.hash.indexOf('?') + 1).match(reg)
  //   if (r != null) return unescape(r[2]); return null
  // },
  get (url, data) {
    return axios.get(allApi[url], { params: data })
  },

  post (url, data) {
    return axios.post(allApi[url], qs.stringify(data))
  },
  postData (url, data) {
    return axios.post(allApi[url], data)
  },
  postJSON (url, data) {
    return axios.post(allApi[url], JSON.parse(JSON.stringify(data)))
  },
  uploadFile (url, data) {
    return axios.post(allApi[url], data)
  },
  getUrl (url) {
    return allApi[url]
  }
}
