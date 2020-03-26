<template>
  <div>
    <h5>跳转中...</h5>
  </div>
</template>
<script>
const Base64 = require('js-base64').Base64
import app from '@/common/js/app'
import util from '@/common/js/util'
export default {
  created() {
    const parameter = util.getUrlKey('param')
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
      app.get('get_worksheet_by_id', {
        sheetId: items[2]
      }).then(data => {
        const sheet = data.data.sheet
        this.$router.push({
          path: '/addFaultInformation/addFault',
          query: {
            detailID: sheet.detailId + '',
            sheetID: sheet.id + '',
            state: sheet.state,
            jump: true
          }
        })
      })
    }).catch(() => {
      this.$message({
        message: '登录失败',
        type: 'error'
      })
      this.$router.push({
        path: '/'
      })
    })
  }
}

</script>
