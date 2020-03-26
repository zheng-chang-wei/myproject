<template>
	<div class="container">
		<router-view></router-view>
	</div>
</template>

<script>
import Login from 'pages/login.vue'
export default {
	data() {
		return {

		}
	},
	components: {
		Login
	},
	mounted() {
		this.tryAjaxSetUp();
	},
	methods: {
		tryAjaxSetUp() {
			let vm = this;
			$.ajaxSetup({
				contentType: "application/x-www-form-urlencoded;charset=utf-8",
				complete: function(XMLHttpRequest, textStatus) {
					var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
					if (JSON.parse(XMLHttpRequest.responseText).resultCode == 'NotLogin') {
						vm.$router.push({
							path: '/login'
						});
					}
				},
			});

		},
	}
}
</script>

<style lang="stylus">
	.container
		height:100%
		position: relative
	html, body, #app
		margin 0
		height 100%
		font-family '微软雅黑'
	#app
		display flex
		>div
			overflow auto
		>#left-menu
			width 220px
		>#router-view
			flex 1
</style>
