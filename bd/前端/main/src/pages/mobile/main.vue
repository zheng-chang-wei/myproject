<template>
  <div id="main">
    <van-nav-bar title="博得手机App" right-text="退出" @click-right="logout" />
    <sheetList />
  </div>
</template>
<script>
import app from '@/common/js/app'
import sheetList from './sheetList'
export default {
  components: {
    sheetList
  },
  methods: {
    logout() {
      app.post('logOut').then(d => {
        this.$router.push({
          path: './mobile/login'
        })
      }).catch(() => {
        this.$router.push({
          path: './mobile/login'
        })
      })
    }
  }
}

</script>
<style>
	#grid {
		margin: 10px;
		display: block;
	}

</style>
