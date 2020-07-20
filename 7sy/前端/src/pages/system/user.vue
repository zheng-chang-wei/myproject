<template>
  <div>
    <section id="userManager">
    <!-- <nav-bar :titles="['系统管理', '用户管理']"></nav-bar> -->

      <!--查询-->
      <el-col :span="24" class="query" style="padding-bottom: 0px;min-width:900px">
        <el-form :inline="true"  ref="retrieveForm" :model="retrieveForm">
          <el-form-item prop="username">
            <el-input v-model="retrieveForm.username" placeholder="用户名" :maxlength="20"></el-input>
          </el-form-item>
          <el-form-item prop="roleName">
            	<el-select  v-model="retrieveForm.roleName" 
								placeholder="请选择">
								<el-option  v-for="item in roleNamesOptions" :key="item" :label="item"
									:value="item">
								</el-option>
							</el-select>
          </el-form-item>
          <el-form-item prop="name">
            <el-input v-model="retrieveForm.name" placeholder="姓名" :maxlength="20"></el-input>
          </el-form-item>
          <el-form-item prop="idNum">
            <el-input v-model="retrieveForm.idNum" placeholder="证件号" :maxlength="50"></el-input>
          </el-form-item>
          <el-form-item prop="deptName">
            <el-input v-model="retrieveForm.deptName" placeholder="部门名称" :maxlength="20"></el-input>
          </el-form-item>
          <el-form-item prop="parentName">
            <el-input v-model="retrieveForm.parentName" placeholder="创建人" :maxlength="20"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="search" @click="retrieveUsers">查询</el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <!--工具条-->
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true">
          <el-form-item>
            <el-button type="primary" @click="handleAdd">
              <i class="iconfont icon-add"></i>新增</el-button>
            <el-button type="primary" @click="handleEdit">
              <i class="iconfont icon-edit"></i>编辑</el-button>
            <el-button type="danger" @click="batchRemove">
              <i class="iconfont icon-delete"></i>删除</el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <!--列表-->
      <el-table :data="users" :border="true" v-loading="listLoading" :height="tableMaxHeight" highlight-current-row style="width: 98%;margin-left:15px" @selection-change="selsChange">
        <el-table-column type="selection"  align="center" width="45"> </el-table-column>
        <el-table-column prop="username" label="用户名" align="center" sortable> </el-table-column>
        <el-table-column prop="roleList" label="角色" align="center" sortable> </el-table-column>
        <el-table-column prop="name" label="姓名" align="center" sortable> </el-table-column>
        <el-table-column prop="idNum" label="证件号码" align="center" sortable> </el-table-column>
        <el-table-column prop="deptName" label="部门名称" align="center" sortable> </el-table-column>
        <el-table-column prop="parentName" label="创建人" align="center" sortable> </el-table-column>
        <el-table-column prop="createTime" label="创建时间"  align="center" sortable> </el-table-column>
      </el-table>

      <!--分页  工具条-->
      <el-col :span="24" class="toolbar" style="position:absolute;bottom:20px;right:0">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="currentPage" :page-sizes="[10, 50, 100]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total" style="float: right;">
        </el-pagination>
      </el-col>
      <!--新增界面-->
      <el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
        <el-form :rules="addRules" :model="addForm" label-width="80px" ref="addForm" style="margin-left:5%;">
          <el-form-item label="用户名" prop="username"  size="small">
            <el-input v-model="addForm.username" auto-complete="off" placeholder="请输入用户名" :maxlength="21" ></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password"  size="small">
            <el-input v-model="addForm.password" show-password auto-complete="off"  placeholder="请输入密码" :maxlength="21"></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="name"  size="small">
            <el-input v-model="addForm.name" auto-complete="off" placeholder="请输入姓名" :maxlength="21"></el-input>
          </el-form-item>
          <el-form-item label="部门名称" prop="deptName"  size="small">
            <el-input v-model="addForm.deptName" auto-complete="off" placeholder="请输入部门名称" :maxlength="21"></el-input>
          </el-form-item>
          <el-form-item label="证件号码" prop="idNum"  size="small">
            <el-input v-model="addForm.idNum" auto-complete="off" placeholder="请输入证件号码" :maxlength="21"></el-input>
          </el-form-item>
          <el-form-item label="角色" prop="roles" required size="small">
            <!-- <el-transfer v-model="addForm.roles" :data="rolesOptions" :titles="['可选','已选']"></el-transfer> -->
            <el-select v-model="addForm.roles" placeholder="请选择">
              <el-option v-for="item in rolesOptions" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click.native="addSubmit" :loading="addLoading" style="width:90px;">提交</el-button>
          <el-button type="cancel" @click.native="addFormVisible = false" style="width:90px;">取消</el-button>
        </div>
      </el-dialog>

      <!--编辑界面-->
      <el-dialog title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false">
        <el-form :rules="editRules" :model="editForm" label-width="80px" ref="editForm" style="margin-left:5%;">
          <el-form-item label="用户名" prop="username"  size="small">
            <el-input v-model="editForm.username" auto-complete="off" placeholder="请输入用户名" :maxlength="21"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password"  size="small">
            <el-input v-model="editForm.password"  auto-complete="off"  placeholder="请输入密码" :maxlength="21" type="password"></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="name"  size="small">
            <el-input v-model="editForm.name" auto-complete="off" placeholder="请输入姓名" :maxlength="21"></el-input>
          </el-form-item>
          <el-form-item label="部门名称" prop="deptName"  size="small">
            <el-input v-model="editForm.deptName" auto-complete="off" placeholder="请输入部门名称" :maxlength="21"></el-input>
          </el-form-item>
          <el-form-item label="证件号码" prop="idNum"  size="small">
            <el-input v-model="editForm.idNum" auto-complete="off" placeholder="请输入证件号码" :maxlength="21"></el-input>
          </el-form-item>
          <el-form-item label="角色" prop="roles" :formatter="formatRole" >
            <el-select v-model="editForm.roles" placeholder="请选择" :disabled="editForm.roles== '1'">
              <el-option v-for="item in rolesOptions" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click.native="editSubmit" :loading="editLoading" style="width:90px;">提交</el-button>
          <el-button type="cancel" @click.native="editFormVisible = false" style="width:90px;">取消</el-button>
        </div>
      </el-dialog>
    </section>
  </div>
</template>

<script type="text/ecmascript-6">
let Base64 = require("js-base64").Base64;
import app from "common/js/app";
export default {
  data() {
    //新增&编辑弹框里长度到达限制时提示
    const checkUserName = (rule, value, callback) => {
      if (value.length === 21) {
        callback(new Error("用户名长度限制20字符"));
      } else {
        callback();
      }
    };

    const checkPassWord = (rule, value, callback) => {
      if (!/[a-z]/.test(value) || !/[A-Z]/.test(value) || !/[0-9]/.test(value)){
        callback(new Error("密码必须包含英文大小写字母和数字"));
      }
      if (value.length === 21) {
        callback(new Error("密码长度限制20字符"));
      } else if(value.length <6){
        callback(new Error("密码长度至少为6字符"));
      }else {
        callback();
      }
    };
    return {
      isCurrentUser:false,
      isAdd: false,
      users: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      currentPage: 1,
      listLoading: false,
      sels: [], //列表选中的选项
      rolesOptions: [],
      roleNamesOptions: [],
      //查询功能数据
      retrieveForm: {
        username: "",
        roleName: "所有",
        parentName: "",
        deptName:"",
        name: "",
        idNum: ""
      },
      //新增界面数据
      addForm: {
        username: "",
        name: "",
        password: "",
        idNum: "",
        deptName: "",
        roles: ""
      },
      addFormVisible: false, //新增界面是否显示
      addLoading: false,
      //编辑界面数据
      editForm: {
        username: "",
        name: "",
        password: "",
        idNum: "",
        deptName: "",
        roles: ""
      },
      editFormVisible: false, //编辑界面是否显示
      editLoading: false,
      //新增弹框表单验证
      addRules: {
        username: [
          {
            required: true,
            message: "必填",
            trigger: "change"
          },
          {
            validator: checkUserName,
            trigger: "change"
          }
        ],
        password: [
          {
            required: true,
            message: "必填",
            trigger: "change"
          },
          {
            validator: checkPassWord,
            trigger: "change"
          }
        ],
        name: [
          {
            required: true,
            message: "必填",
            trigger: "change"
          },
          {
            validator: checkUserName,
            trigger: "change"
          }
        ],
        idNum: [
          {
            required: true,
            message: "必填",
            trigger: "change"
          },
          {
            validator: checkUserName,
            trigger: "change"
          }
        ],
        deptName: [
          {
            required: true,
            message: "必填",
            trigger: "change"
          },
          {
            validator: checkUserName,
            trigger: "change"
          }
        ],
        roles: [
          {
            required: true,
            message: "必填",
            trigger: "change"
          }
        ],
      },
       //编辑弹框表单验证
      editRules: {
        username: [
          {
            required: true,
            message: "必填",
            trigger: "change"
          },
          {
            validator: checkUserName,
            trigger: "change"
          }
        ],
        password: [
          {
            required: true,
            message: "必填",
            trigger: "change"
          },
          {
            validator: checkPassWord,
            trigger: "change"
          }
        ],
        name: [
          {
            required: true,
            message: "必填",
            trigger: "change"
          },
          {
            validator: checkUserName,
            trigger: "change"
          }
        ],
        idNum: [
          {
            required: true,
            message: "必填",
            trigger: "change"
          },
          {
            validator: checkUserName,
            trigger: "change"
          }
        ],
        deptName: [
          {
            required: true,
            message: "必填",
            trigger: "change"
          },
          {
            validator: checkUserName,
            trigger: "change"
          }
        ],
        roles: [
          {
            required: true,
            message: "必填",
            trigger: "change"
          }
        ],
      },
      tableMaxHeight:  document.body.offsetHeight - 280,
    };
  },
  mounted() {
    this.getUsers();
    this.getCurrentUserRoles();
    //页面改变时,更改尺寸
    $(window).on("resize", this.changeTableMaxHeight);
    this.changeTableMaxHeight();
  },
  components: {
  },
  methods: {
    //格式化角色
    formatRole(row, column) {
      switch (row.roles) {
        case 1:
          return "超级管理员";
        case 2:
          return "管理员";
        case 3:
          return "普通用户";
        default:
          return "";
      }
    },
    //列表选中的选项
    selsChange(sels) {
      this.sels = sels;
    },
    //动态更改表格最大高度
    changeTableMaxHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 280;
      let sectionHeight = document.body.offsetHeight - 80 + "px";
      $("#userManager").css({ height: sectionHeight });
    },
    //获取当前用户角色
    getCurrentUserRoles() {
        switch (localStorage.getItem('roleName')) {
        case "超级管理员":
           this.roleNamesOptions=["所有","超级管理员","管理员","普通用户"];
           break;
        case "管理员":
          this.roleNamesOptions=["所有","管理员","普通用户"];
      }
     
    },
    //获取角色
    getRoles() {
      let vm = this;
      let userId = 0;
      if (vm.sels[0]) {
        userId = vm.sels[0].id;
      }
      let param = {
        isAdd: vm.isAdd,
        userId: userId,
        pageNum: 1,
        pageSize: 999 //一次性拿到所有角色
      };
      app.get("get_role", param).then(data => {
        if (!data) return;
        if (data.msg.rows && data.msg.rows.length != 0) {
          vm.rolesOptions = [];
          data.msg.rows.forEach(item => {
            vm.rolesOptions.push({
              value: item.roleId,
              label: item.roleName
            });
          });
        }
      });
    },
    queryStart(currentPage){
			this.currentPage=currentPage
			this.handleCurrentChange(currentPage)
		},
    //查询用户
    retrieveUsers() {
      this.$refs.retrieveForm.validate(valid => {
        if (valid) {
          this.queryStart(1);
        }
      });
    },
    //获取用户
    getUsers() {
      this.listLoading = true;
      let vm = this;
      let param = {
        username: vm.retrieveForm.username,
        name: vm.retrieveForm.name,
        roleName: vm.retrieveForm.roleName,
        idNum: vm.retrieveForm.idNum,
        parentName: vm.retrieveForm.parentName,
        deptName:vm.retrieveForm.deptName,
        pageNum: vm.pageNum,
        pageSize: vm.pageSize
      };
      if (vm.retrieveForm.roleName==='所有') {
        param.roleName='';
      }
      app.get("get_user", param).then(d => {
        if (d) {
          let roles = [];
          vm.users = d.msg.rows;
          vm.total = d.msg.total;
          vm.users.forEach(item => {
            if (item.roleList)
              item.roleList = item.roleList
                .map(itemI => itemI.roleName)
                .join(",");
          });
          this.listLoading = false;
        }
      });
    },
    //显示新增弹窗
    handleAdd() {
      let vm = this;
      vm.isAdd = true;
      this.getRoles();
      this.addFormVisible = true;
      this.addForm = {
        username: "",
        name: "",
        password: "123456",
        deptName: "",
        idNum: "",
        roles: ""
      };
    },
    //新增
    addSubmit() {
      this.$refs.addForm.validate(valid => {
        let vm = this;
        if (valid) {
          this.$confirm("确认提交吗？", "提交", {}).then(() => {
            vm.addLoading = true;

            let param = Object.assign({}, vm.addForm);
            // para.password = md5(para.password);
            param.password = Base64.encode(param.password);
            // param.roles = param.roles.join(",");
            app.post("add_user", param).then(data => {
              vm.addLoading = false;
              if (data.code == "0001") {
                vm.$message({
                  message: data.msg,
                  type: "error"
                });
              } else {
                vm.$message({
                  message: data.msg,
                  type: "success"
                });
                vm.$refs["addForm"].resetFields();
                vm.addFormVisible = false;
                vm.queryStart(1);
              }
            });
          }).catch(() => {});
        }
      });
    },
    //显示编辑弹窗
    handleEdit() {
      let vm = this;
      this.isCurrentUser=false;
      //进行判断,只能选择一个进行编辑
      if (this.sels.length == 0) {
        this.$message("请勾选一个用户");
        return;
      }
      if (this.sels.length > 1) {
        this.$message("只能选择一个用户");
        return;
      }
      vm.isAdd = false;
      this.getRoles();
      //初始化
      vm.editForm = {
        username: "",
        name: "",
        password: "",
        idNum: "",
        deptName: "",
        roles: ""
      };
      let param = {
        userId: vm.sels[0].id
      };
      app.get("toUpdate_user", param).then(data => {
        vm.editFormVisible = true;
        let editData = data.msg.tSysUser,
          editRoles = data.msg.roles;
        if (data.msg) {
          vm.editForm.id = vm.sels[0].id;
          vm.editForm.username = editData.username;
          vm.editForm.name = editData.name;
          vm.editForm.deptName = editData.deptName;
          vm.editForm.password = Base64.decode(editData.password);
          vm.editForm.idNum = editData.idNum;
          vm.editForm.roles = editRoles[0].roleId;
          if (localStorage.getItem('username')===editData.username) {
            vm.isCurrentUser=true;
          }
        }
      });
    },
    //编辑
    editSubmit() {
      this.$refs.editForm.validate(valid => {
        let vm = this;
        if (valid) {
          this.$confirm("确认提交吗？", "提示", {}).then(() => {
            this.editLoading = true;
            let param = Object.assign({}, vm.editForm);
            param.password = Base64.encode(param.password);
            app.post("update_user", param).then(data => {
              vm.editLoading = false;
              if (data.code == "0001") {
                vm.$message({
                  message: data.msg,
                  type: "error"
                });
              } else {
                vm.$message({
                  message: data.msg,
                  type: "success"
                });
                if (vm.isCurrentUser) {
                  localStorage.setItem('username', vm.editForm.username);
                }
                vm.$root.Bus.$emit("updateUserInfo", 123);
                vm.$refs["editForm"].resetFields();
                vm.editFormVisible = false;

                vm.getUsers();
              }
            });
          }).catch(() => {});
        }
      });
    },
    //批量删除
    batchRemove() {
    	let vm = this;
    	let ids = this.sels.map(item => item.id).toString();
    	if (this.sels.length == 0) {
    		this.$message({
    			message: "请至少勾选一个用户删除",
    			type: "error"
    		});
    		return;
    	}
    	this.$confirm("确认删除选中记录？", "提示", {
    		type: "warning"
    	}).then(() => {
    		this.listLoading = true;
    		let param = {
    			userIds: ids
    		};
    		app.post("delete_user", param).then(data => {
    			
    			if (data.code == "0003" || data.code == "0002") {
    				vm.$message({
    					message: data.msg,
    					type: "error"
    				});
    			} else {
    				vm.$message({
    					message: data.msg,
    					type: "success"
    				});
    				let param = {
    					username: vm.retrieveForm.username,
    					name: vm.retrieveForm.name,
    					roleName: vm.retrieveForm.roleName,
    					idNum: vm.retrieveForm.idNum,
    					parentName: vm.retrieveForm.parentName,
    					deptName: vm.retrieveForm.deptName,
    					pageNum: vm.pageNum,
    					pageSize: vm.pageSize
    				};
    				if (vm.retrieveForm.roleName === '所有') {
    					param.roleName = '';
    				}
    				app.get("get_user", param).then(d => {
    					if (d) {
    						if (d.msg.total === 0) {
    							let roles = [];
    							vm.users = d.msg.rows;
                  vm.total = d.msg.total;
                  vm.listLoading = false;
    						} else {
    							if (d.msg.rows.length === 0) {
    								vm.queryStart(vm.currentPage - 1)
    							} else {
                    vm.listLoading = false;
    								let roles = [];
    								vm.users = d.msg.rows;
    								vm.total = d.msg.total;
    								vm.users.forEach(item => {
    									if (item.roleList)
    										item.roleList = item.roleList
    										.map(itemI => itemI.roleName)
    										.join(",");
    								});
    							}
    						}
    					}
    				});
    			}
    		});
    	}).catch(() => {});
    },
    //分页触发
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getUsers();
    },
    //改变页码
    handleSizeChange(val) {
      this.pageSize = val;
      this.getUsers();
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

section#userManager {
  background-color: white;
  margin-left: 12px;
}

#userManager .query {
  padding: 16px 15px 0px;
}

#userManager .query .el-input {
  width: 130px;
}

#userManager .toolbar {
  padding: 0px 15px;
  border-bottom: 1px solid transparent;
  border-top-right-radius: 3px;
  border-top-left-radius: 3px;
}

#userManager .el-form-item__label {
  text-align: right;
}

#userManager .el-breadcrumb {
  height: 30px;
  line-height: 30px;
  padding-left: 20px;
}

#userManager .systemColor .el-breadcrumb__item__inner {
  color: #97a8be;
}

#userManager .el-dialog .el-input {
  width: 90%;
}
#userManager .el-dialog .el-select {
  width: 100%;
}

/* 更改穿梭框样式 */

/* #userManager .el-transfer .el-checkbox__inner {
  display: none;
}

#userManager .el-transfer .el-checkbox__label {
  padding-left: 0px;
}

#userManager .el-transfer .el-transfer-panel__item {
  padding-left: 0px;
  box-sizing: border-box;
  margin-left: 12px;
} */

#userManager .el-checkbox__input.is-checked + .el-checkbox__label {
  background-color: #00a1e9;
  color: white;
  box-sizing: border-box;
  padding-left: 6px;
}

#userManager
  .el-transfer-panel__footer
  .el-checkbox__input.is-checked
  + .el-checkbox__label {
  background-color: white;
  color: #8391a5;
}
</style>