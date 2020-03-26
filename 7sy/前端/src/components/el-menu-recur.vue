<template lang="pug">
	el-submenu(:index="index+''")
		template(slot="title")
			i.iconfont(:class="root.icon")
			|{{root.name}}
		template(v-for="(sub, index2) in root.children")
			//- router-link(v-if="sub.children.length==0", :to="sub.url")
			el-menu-item(:index="sub.url" :value='sub.id' ) {{sub.name}}
			//- el-menu-recur(:root="sub", :index="index+'-'+index2" v-if="sub.children.length>0")
</template>
<script>
    import {mapMutations} from 'vuex'
	export default {
		props: ['root', 'index'],
		name: 'el-menu-recur',
		methods:{
			// getId(Id){
			// 	if(Id){
			// 		this.setCompanyId(Id);
			// 	}
			// },
			// ...mapMutations({
			// 	setCompanyId:'SET_COMPANYID'
			// })
		}

	}
</script>
<style scoped lang="stylus">
	a
		text-decoration none
	.iconfont
		font-size: 20px;
		padding-right: 10px;
		color #fff;
</style>
