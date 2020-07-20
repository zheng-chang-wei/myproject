export default {
  getFileUrl(file) { // 将本地地址转化为url地址
    var url
    if (navigator.userAgent.indexOf('MSIE') >= 1) { // IE
      url = file
    } else if (navigator.userAgent.indexOf('Firefox') > 0) { // Firefox
      url = window.URL.createObjectURL(file)
    } else if (navigator.userAgent.indexOf('Chrome') > 0) { // Chrome
      url = window.URL.createObjectURL(file)
    }
    return url
  },
  // 格式化时间(公用方法)
  formatDate(date, fmt) {
    var o = {
      'M+': date.getMonth() + 1, // 月份
      'd+': date.getDate(), // 日
      'h+': date.getHours(), // 小时
      'm+': date.getMinutes(), // 分
      's+': date.getSeconds(), // 秒
      'q+': Math.floor((date.getMonth() + 3) / 3), // 季度
      S: date.getMilliseconds() // 毫秒
    }
    if (/(y+)/.test(fmt)) {
      fmt = fmt.replace(
        RegExp.$1,
        (date.getFullYear() + '').substr(4 - RegExp.$1.length)
      )
    }
    for (var k in o) {
      if (new RegExp('(' + k + ')').test(fmt)) {
        fmt = fmt.replace(
          RegExp.$1,
          RegExp.$1.length === 1
            ? o[k]
            : ('00' + o[k]).substr(('' + o[k]).length)
        )
      }
    }
    return fmt
  },
  getAllVariables(allVariables) {
    let variables = []
    for (let i = 0; i < allVariables.length; i++) {
      variables = variables.concat(allVariables[i].variables)
    }
    return variables
  },
  isInvalid(element, type, variables) {
    for (let i = 0; i < variables.length; i++) {
      const variable = variables[i]
      if (this.isEquals(element, variable, type)) {
        return false
      }
    }
    return true
  },
  isEquals(variable1, variable2, type) {
    if (type === 'MVB') {
      if (variable1.name === variable2.name && variable1.signalName === variable2.signalName && variable1.device === variable2.device && variable1.deviceName === variable2.deviceName) {
        return true
      }
    } else if (type === 'ECN') {
      if (variable1.name === variable2.name && variable1.signalName === variable2.signalName && variable1.comId === variable2.comId && variable1.sourceIp === variable2.sourceIp) {
        return true
      }
    } else if (type.indexOf('AD') !== -1) {
      if (variable1.name === variable2.name) {
        return true
      }
    }
    return false
  },
  isEmpty(array) {
    if (array !== undefined && array !== null && array.length > 0) {
      return false
    }
    return true
  }
}
