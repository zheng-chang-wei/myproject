import axios from 'axios'
import allApi from './allApi'
import qs from 'qs'
// axios.defaults.baseURL = 'dev-api'
import { Message } from 'element-ui'
axios.defaults.withCredentials = true
axios.interceptors.response.use(res => {
  if (res.data.code === 500 || res.data.code === '500') {
    console.log('code====' + res.data.code)
    Message({
      message: res.data.msg,
      type: 'error',
      dangerouslyUseHTMLString: true,
      duration: 5000
    })
    return Promise.reject(res.data)
  } else {
    return res.data
  }
}, error => {
  console.log('err')
  return Promise.reject(error)
})

export default {
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
  getUrl(url) {
    return allApi[url]
  }
}
