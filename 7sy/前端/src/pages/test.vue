<template>
	<div>
		<!--查询-->
		<el-form :inline="true" ref="retrieveForm" :model="retrieveForm" style="margin-left:20px">
			<el-form-item prop="startTime" label="时间范围">
				<el-date-picker style="width:192px;" v-model="retrieveForm.startTime" type="datetime"
					placeholder="选择日期时间" :editable="false" value-format="yyyy-MM-dd HH:mm:ss">
				</el-date-picker>
			</el-form-item>
			<el-form-item prop="endTime" label="-">
				<el-date-picker style="width:192px;" v-model="retrieveForm.endTime" type="datetime" placeholder="选择日期时间"
					:editable="false" value-format="yyyy-MM-dd HH:mm:ss">
				</el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" icon="search" @click="getData1">导入</el-button>
			</el-form-item>
		</el-form>
		<el-form :inline="true" ref="retrieveForm" :model="retrieveForm" style="margin-left:20px">
			<el-form-item prop="startTime" label="时间范围">
				<el-date-picker style="width:192px;" v-model="retrieveForm.startTime" type="datetime"
					placeholder="选择日期时间" :editable="false" value-format="yyyy-MM-dd HH:mm:ss">
				</el-date-picker>
			</el-form-item>
			<el-form-item prop="endTime" label="-">
				<el-date-picker style="width:192px;" v-model="retrieveForm.endTime" type="datetime" placeholder="选择日期时间"
					:editable="false" value-format="yyyy-MM-dd HH:mm:ss">
				</el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" icon="search" @click="getData2">test</el-button>
			</el-form-item>
		</el-form>
		<p>{{data}}</p>
	</div>
</template>

<script>
import app from "common/js/app";
export default {
	data() {
		return {
			retrieveForm: {
				startTime: "", //查询时开始时间
				endTime: "" //查询时结束时间
			},
	data:''
		};
	},
	mounted() {
	},
	components: {},
	methods: {
		getData1() {
			app.get('test', this.retrieveForm).then(response => {
				this.data = response.msg
			  console.log(response.msg)
			});
		},
		getData2() {
			app.get('test2', this.retrieveForm).then(response => {
				this.data = '共 '+response.msg.length+' 条数据'
				console.log('共 '+response.msg.length+' 条数据')
			});
		},
	}
};
</script>
<style>

</style>