import axios from 'axios'
import allApi from './allApi'
import qs from 'qs'
axios.defaults.baseURL = 'dev-api'
import router from '../../router'
import Vue from 'vue'
var notifyList = []
axios.defaults.withCredentials = true
import { removeToken } from '@/utils/auth'
axios.interceptors.response.use(res => {
  // console.log(res)
  // if (res['content-type'] === 'application/octet-stream') {
  //   return res
  // }
  if (res.data.code === 500 || res.data.code === '500') {
    console.log('code====' + res.data.code)
    if (res.data.msg === 'NotLogin') {
      removeToken()
      if (notifyList.length !== 0) {
        notifyList[0].close()
      }
      const notify = new Vue().$notify({ title: '未登录', message: '自动返回登录页' })
      notifyList[0] = notify
      router.push({
        path: '/login'
      })
    } else {
      new Vue().$message({
        message: res.data.msg,
        type: 'warning'
      })
    }
    return Promise.reject(res.data.code)
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
  get(url, data) {
    return axios.get(allApi[url], { params: data })
  },

  post(url, data) {
    return axios.post(allApi[url], qs.stringify(data))
  },
  postData(url, data) {
    return axios.post(allApi[url], data)
  },
  postJSON(url, data) {
    return axios.post(allApi[url], JSON.parse(JSON.stringify(data)))
  },
  uploadFile(url, data) {
    return axios.post(allApi[url], data)
  },
  getUrl(url) {
    return allApi[url]
  },

  download(url, data) {
    return axios({
      method: 'get',
      url: allApi[url],
      responseType: 'blob',
      data
    })
  }
}
