<template>
	<div>
		<section id="onlineManager">
		<!-- <nav-bar :titles="['系统管理', '在线用户']"></nav-bar> -->


			<!--列表-->
			<el-table :data="onlines" :border="true" v-loading="listLoading" :height="tableMaxHeight"
				highlight-current-row style="width: 98%;margin-left:15px">
				<!-- <el-table-column type="selection" width="55"> </el-table-column> -->
				<el-table-column prop="username" label="用户名" align="center" sortable> </el-table-column>
				<el-table-column prop="realName" label="姓名" align="center" sortable> </el-table-column>
				<el-table-column prop="host" label="IP地址" align="center" sortable> </el-table-column>
				<el-table-column prop="startTimestamp" label="登录时间" :formatter="formatStartTimestamp" align="center" sortable>
				</el-table-column>
				<el-table-column prop="lastAccessTime" label="最后访问时间" :formatter="formatLastAccessTime" align="center" sortable>
				</el-table-column>
			</el-table>

			<!--分页  工具条-->
			<el-col :span="24" class="toolbar" style="position:absolute;bottom:20px;right:0">
				<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
					:current-page.sync="currentPage" :page-sizes="[20, 50, 100]" :page-size="pageSize"
					layout="total, sizes, prev, pager, next, jumper" :total="total" style="float: right;">
				</el-pagination>
			</el-col>
		</section>
	</div>
</template>

<script type="text/ecmascript-6">
import appApi from "../../common/js/allApi.js";
import app from "common/js/app";
export default {
	data() {

		return {
			onlines: [],
			total: 0,
			pageNum: 1,
			pageSize: 20,
			currentPage: 1,
			listLoading: false,
			tableMaxHeight: document.body.offsetHeight - 130,
		};
	},
	mounted() {
		this.getOnlines();
		//页面改变时,更改尺寸
		$(window).on("resize", this.changeTableMaxHeight);
		this.changeTableMaxHeight();
	},
	components: {
	},
	methods: {
		//格式化时间(公用方法)
		formatDate(date, fmt) {
			let o = {
				"M+": date.getMonth() + 1, //月份
				"d+": date.getDate(), //日
				"h+": date.getHours(), //小时
				"m+": date.getMinutes(), //分
				"s+": date.getSeconds(), //秒
				"q+": Math.floor((date.getMonth() + 3) / 3), //季度
				S: date.getMilliseconds() //毫秒
			};
			if (/(y+)/.test(fmt))
				fmt = fmt.replace(
					RegExp.$1,
					(date.getFullYear() + "").substr(4 - RegExp.$1.length)
				);
			for (let k in o)
				if (new RegExp("(" + k + ")").test(fmt))
					fmt = fmt.replace(
						RegExp.$1,
						RegExp.$1.length == 1 ?
						o[k] :
						("00" + o[k]).substr(("" + o[k]).length)
					);
			return fmt;
		},
		//格式化登录时间
		formatStartTimestamp(row, column) {
			return (row.startTimestamp = row.startTimestamp ?
				this.formatDate(new Date(row.startTimestamp), "yyyy-MM-dd hh:mm:ss") :
				"");
		},
		//格式化最后访问时间
		formatLastAccessTime(row, column) {
			return (row.lastAccessTime = row.lastAccessTime ?
				this.formatDate(new Date(row.lastAccessTime), "yyyy-MM-dd hh:mm:ss") :
				"");
		},
		//动态更改表格最大高度
		changeTableMaxHeight() {
			this.tableMaxHeight = document.body.offsetHeight - 130;
			let sectionHeight = document.body.offsetHeight - 80 + "px";
			$("#onlineManager").css({
				height: sectionHeight
			});
		},
		getOnlines() {
			this.listLoading = true;
			let vm = this;
			let param = {
				pageNum: vm.pageNum,
				pageSize: vm.pageSize
			};
			app.get("get_online", param).then(d => {
				if (d) {
					vm.onlines = d.msg.rows;
					vm.total = d.msg.total;
					this.listLoading = false;
				}
			});
		},
		//分页触发
		handleCurrentChange(val) {
			this.pageNum = val;
			this.getOnlines();
		},
		//改变页码
		handleSizeChange(val) {
			this.pageSize = val;
			this.getOnlines();
		}
	}
};
</script>
<style>
section {
  background-color: white;
  margin-left: 12px;
  margin-right: 12px;
  border: 1px solid #e8e8e8;
}

section#onlineManager {
  background-color: white;
  margin-left: 12px;
}

#onlineManager .query {
  padding: 16px 15px 0px;
}

#onlineManager .query .el-input {
  width: 140px;
}

#onlineManager .el-dialog__wrapper .el-dialog {
  min-width: 700px;
}

#onlineManager .toolbar {
  padding: 0px 15px;
  border-bottom: 1px solid transparent;
  border-top-right-radius: 3px;
  border-top-left-radius: 3px;
}

#onlineManager .el-form-item__label {
  text-align: right;
}

#onlineManager .el-breadcrumb {
  height: 30px;
  line-height: 30px;
  padding-left: 20px;
}

#onlineManager .systemColor .el-breadcrumb__item__inner {
  color: #97a8be;
}

#onlineManager .el-dialog .el-input {
  width: 460px;
}

/* 更改穿梭框样式 */

#onlineManager .el-transfer .el-checkbox__inner {
  display: none;
}

#onlineManager .el-transfer .el-checkbox__label {
  padding-left: 0px;
}

#onlineManager .el-transfer .el-transfer-panel__item {
  padding-left: 0px;
  box-sizing: border-box;
  margin-left: 12px;
}

#onlineManager .el-checkbox__input.is-checked + .el-checkbox__label {
  background-color: #00a1e9;
  color: white;
  box-sizing: border-box;
  padding-left: 6px;
}

#onlineManager
  .el-transfer-panel__footer
  .el-checkbox__input.is-checked
  + .el-checkbox__label {
  background-color: white;
  color: #8391a5;
}
</style>