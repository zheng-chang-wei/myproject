<template>
  <div>
    <h5>跳转中……</h5>
  </div>
</template>
<script>
const Base64 = require('js-base64').Base64
import app from '@/common/js/app'
import utils from '@/common/js/util'
export default {
  created() {
    const parameter = utils.getUrlKey('param')
    console.log(parameter)
    const parameters = Base64.decode(parameter)
    console.log(parameters)
    const items = parameters.split('&')
    const param = {
      accountname: items[0],
      password: items[1]
    }
    app.post('logIn', param).then(data => {
      localStorage.setItem('username', param.accountname)
      this.$router.push({
        path: '/mobile/main',
        query: {
          sheetId: items[2]
        }
      })
    }).catch(() => {
      this.$toast('登录失败')
      this.$router.push({
        path: '/mobile/login'
      })
    })
  }
}

</script>
