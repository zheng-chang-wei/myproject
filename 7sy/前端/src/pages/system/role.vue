<template>
	<div>
		<section id="roleManager">
		<!--导航条-->
		<nav-bar :titles="['系统管理', '角色管理']"></nav-bar>
			<!--查询-->
			<el-col :span="24" class="query" style="padding-bottom: 0px;min-width:900px">
				<el-form :inline="true" :rules="retrieveRules" ref="retrieveForm" :model="retrieveForm">
					<el-form-item prop="roleName">
						<el-input v-model="retrieveForm.roleName" placeholder="角色名称" :maxlength="20"></el-input>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="retrieveRoles">
							<i class="iconfont icon-search"></i>查询</el-button>
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
						<el-button type="danger" @click="handleDel">
							<i class="iconfont icon-delete"></i>删除</el-button>
						<el-button type="primary" @click="handleAuthority">
							<i class="iconfont icon-authority"></i>分配权限</el-button>
					</el-form-item>
				</el-form>
			</el-col>
			<!-- 表格 -->
			<el-table :border="true" :data="roleTableData" v-loading="listLoading" :height="tableMaxHeight" highlight-current-row style="width: 98%;margin-left:15px;" @selection-change="roleSelectChange">
				<el-table-column type="selection" align="center"> </el-table-column>
				<el-table-column prop="roleName" label="角色名" align="center"> </el-table-column>
				<el-table-column prop="remark" label="描述" align="center"> </el-table-column>
				<el-table-column prop="createTime" label="创建时间" :formatter="formatCreatetime" align="center"> </el-table-column>
			</el-table>
			<el-col :span="24" class="toolbar" style="position:absolute;bottom:40px;right:0">
				<el-pagination layout="total, sizes, prev, pager, next, jumper" @size-change="pageChange2" :page-size="pageSize" :total="pageNumber" @current-change="pageChange" :page-sizes="[20, 50, 100]" :current-page.sync="currentPage" style="float: right;"></el-pagination>
			</el-col>
			<!--新增界面-->
			<el-dialog title="新增" :visible.sync="addRoleVisible" :close-on-click-modal="false">
				<el-form :model="addRole" label-width="100px" ref="addRole" :rules="rules" style="width:80%;margin-left:5%">
					<el-form-item label="角色名" prop="roleName" required>
						<el-input v-model="addRole.roleName" auto-complete="off" placeholder="请输入角色名称" :maxlength="20"></el-input>
					</el-form-item>
					<el-form-item label="描述" prop="remark" style="margin-top:20px">
						<el-input v-model="addRole.remark" auto-complete="off" :maxlength="20"></el-input>
					</el-form-item>
				</el-form>
				<div slot="footer" class="dialog-footer">
					<el-button type="primary" @click.native="addSubmit" :loading="addLoading" style="width:90px;">提交</el-button>
					<el-button type="cancel" @click.native="addRoleVisible = false" style="width:90px;">取消</el-button>
				</div>
			</el-dialog>
			<!--编辑界面-->
			<el-dialog title="编辑" :visible.sync="editRoleVisible" :close-on-click-modal="false">
				<el-form :model="editRole" label-width="100px" ref="editRole" :rules="rules" style="width:80%;margin-left:5%">
					<el-form-item label="角色名" prop="roleName" required>
						<el-input v-model="editRole.roleName" auto-complete="off" placeholder="请输入角色名称"></el-input>
					</el-form-item>
					<el-form-item label="描述" prop="remark" style="margin-top:20px">
						<el-input v-model="editRole.remark" auto-complete="off"></el-input>
					</el-form-item>
				</el-form>
				<div slot="footer" class="dialog-footer">
					<el-button type="primary" @click.native="editSubmit" :loading="editLoading" style="width:90px;">提交</el-button>
					<el-button type="cancel" @click.native="editRoleVisible = false" style="width:90px;">取消</el-button>
				</div>
			</el-dialog>
			<!--分配权限界面-->
			<el-dialog title="分配权限" :visible.sync="authorityRoleVisible" :close-on-click-modal="false">
				<div style="min-height:300px;">
					<el-tree :data="treeData" ref="tree" show-checkbox node-key="id" :props="defaultProps">
					</el-tree>
				</div>
				<div slot="footer" class="dialog-footer">
					<el-button type="primary" @click.native="authoritySubmit" :loading="authorityLoading" style="width:90px;">保存</el-button>
					<el-button type="cancel" @click.native="authorityRoleVisible = false" style="width:90px;">取消</el-button>
				</div>
			</el-dialog>
		</section>
	</div>
</template>

<script type="text/ecmascript-6">
import appApi from "../../common/js/allApi.js";
// import navBar from "components/nav-bar";
import app from "common/js/app";
// import ElFormItem from 'components/form/src/form-item.vue'
export default {
  data() {
    //新增&编辑弹框里长度到达限制时提示
    const checkRoleName = (rule, value, callback) => {
      if (value.length === 20) {
        callback(new Error("角色名称长度限制20字符"));
      } else {
        callback();
      }
    };
    const checkRolekey = (rule, value, callback) => {
      if (value.length === 20) {
        callback(new Error("rolekey长度限制20字符"));
      } else {
        callback();
      }
    };
    return {
      roleTableData: [],
      roleSelect: [],
      listLoading: true,
      pickerOptions0: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        }
      },
      retrieveForm: {
        roleName: ""
      },
      //新增界面数据
      addRole: {
        roleId: "",
        roleName: "",
        remark: ""
      },
      //编辑界面数据
      editRole: {
        roleId: "",
        roleName: "",
        remark: ""
      },
      //表单验证提示
      rules: {
        name: [
          { required: true, message: "必填", trigger: "submit" },
          {
            validator: checkRoleName,
            trigger: "change"
          }
        ],
        roleKey: [
          { required: true, message: "必填", trigger: "submit" },
          {
            validator: checkRolekey,
            trigger: "change"
          }
        ]
        // state: [{ required: true, message: '请选择启用状态', trigger: 'submit' }]
      },
      authorityList: [],
      addRoleVisible: false, //新增界面是否显示
      addLoading: false,
      editRoleVisible: false, //编辑界面是否显示
      editLoading: false,
      authorityRoleVisible: false,
      authorityLoading: false,
      pageNumber: 0, //总页数
      currentPage: 1, //表格当前页
      pageNum: 1,
      pageSize: 20,
      treeData: [], //菜单树
      defaultProps: {
        children: "children",
        label: "name"
      },
      child: [], //在树上获得的最子节点
      tArr: [], //完整的父子id
      exp: [],
      //查询时验证
      retrieveRules: {
        roleName: [
          {
            validator: checkRoleName,
            trigger: "change"
          }
        ]
      },
      tableMaxHeight: document.body.offsetHeight - 380,
    };
  },
  mounted() {
    this.getRoles(1);
    //页面改变时,更改尺寸
    $(window).on("resize", this.changeTableMaxHeight);
    this.changeTableMaxHeight();
  },
  components: {
    // navBar
    // ElFormItem,
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
            RegExp.$1.length == 1
              ? o[k]
              : ("00" + o[k]).substr(("" + o[k]).length)
          );
      return fmt;
    },
    //格式化启用状态
    isUse(row, column) {
      return row.state == 0 ? "启用" : "禁用";
    },
    //格式化创建时间
    formatCreatetime(row, column) {
      return (row.createTime = row.createTime
        ? this.formatDate(new Date(row.createTime), "yyyy-MM-dd hh:mm:ss")
        : "");
    },
    roleSelectChange(val) {
      this.roleSelect = val;
    },
    //查询
    retrieveRoles() {
      this.$refs.retrieveForm.validate(valid => {
        if (valid) {
          this.getRoles(1);
        }
      });
    },
    //动态更改表格最大高度
    changeTableMaxHeight() {
      // this.tableMaxHeight = document.body.offsetHeight - 380;
      let sectionHeight = document.body.offsetHeight - 162 + "px";
      $("section").css({ height: sectionHeight });
    },
    //获取角色
    getRoles(val) {
      let vm = this;
      let param = {
        pageNum: vm.pageNum,
        pageSize: vm.pageSize
      };
      app.get("get_all_role", param).then(data => {
        if (data.msg) {
          vm.roleTableData = data.msg.rows;
          vm.pageNumber = data.msg.total;
          vm.listLoading = false;
        }
      });
    },
    handleAdd() {
      let vm = this;
      vm.addRoleVisible = true;
      vm.addLoading = false;
      setTimeout(function() {
        if (vm.$refs.addRole) vm.$refs["addRole"].resetFields();
      }, 50);
      vm.addRole = {
        id: "",
        roleName: "",
        remark: ""
      };
    },
    //新增
    addSubmit() {
      this.$refs.addRole.validate(valid => {
        let vm = this;
        if (valid) {
          this.$confirm("确认提交吗？", "提交", {}).then(() => {
            this.addLoading = true;
            let param = Object.assign({}, vm.addRole);
            app.post("add_role", param).then(data => {
              if (data.msg) {
                vm.addLoading = false;
                vm.$message({
                  message: data.msg,
                  type: "success"
                });
                vm.$refs["addRole"].resetFields();
                vm.addRoleVisible = false;

                vm.currentPage = 1;
                vm.getRoles(1);
              }
            });
            // $.ajax({
            //   type: "POST",
            //   xhrFields: {
            //     withCredentials: true
            //   },
            //   crossDomain: true,
            //   url: appApi["add_role"],
            //   data: para,
            //   success: function(data) {
            //     vm.addLoading = false;
            //     if (data.code == 0) {
            //       vm.$message({
            //         message: data.msg,
            //         type: "success"
            //       });
            //       vm.$refs["addRole"].resetFields();
            //       vm.addRoleVisible = false;
            //     } else {
            //       vm.$message({
            //         message: data.msg,
            //         type: "warning"
            //       });
            //     }

            //     vm.currentPage = 1;
            //     vm.getRoles(1);
            //   },
            //   error: function(xhr, type, errorThrown) {}
            // });
          }).catch(() => {});
        }
      });
    },
    handleEdit() {
      let vm = this;
      vm.editLoading = false;
      if (vm.roleSelect.length == 1) {
        let param = {
          id: vm.roleSelect[0].roleId
        };
        app.get("toUpdate_role", param).then(data => {
          if (data.msg) {
            vm.editRoleVisible = true;
            setTimeout(function() {
              if (vm.$refs["editRole"]) {
                vm.$refs["editRole"].resetFields();
              }
              vm.editRole.roleId = data.msg.roleId;
              vm.editRole.roleName = data.msg.roleName;
              vm.editRole.remark = data.msg.remark;
            }, 0);
          }
        });
        // $.ajax({
        //   type: "POST",
        //   xhrFields: {
        //     withCredentials: true
        //   },
        //   crossDomain: true,
        //   url: appApi["toUpdate_role"],
        //   data: para,
        //   success: function(data) {
        //     vm.editRoleVisible = true;
        //     setTimeout(function() {
        //       if (vm.$refs["editRole"]) {
        //         vm.$refs["editRole"].resetFields();
        //       }
        //       vm.editRole.roleId = data.msg.roleId;
        //       vm.editRole.roleName = data.msg.roleName;
        //       vm.editRole.remark = data.msg.remark;
        //     }, 0);
        //   },
        //   error: function(xhr, type, errorThrown) {}
        // });
      } else if (vm.roleSelect.length == 0) {
        vm.$message({
          message: "至少选择一个角色编辑"
        });
      } else {
        vm.$message({
          message: "只能选择一个角色编辑"
        });
      }
    },
    editSubmit() {
      this.$refs.editRole.validate(valid => {
        let vm = this;
        if (valid) {
          this.$confirm("确认提交吗？", "提交", {}).then(() => {
            this.editLoading = true;
            let param = Object.assign({}, vm.editRole);
            app.post("update_role", param).then(data => {
              if (data.msg) {
                vm.editLoading = false;
                vm.$message({
                  message: data.msg,
                  type: "success"
                });
                vm.$refs["editRole"].resetFields();
                vm.editRoleVisible = false;
                vm.currentPage = 1;
                vm.getRoles(1);
              }
            });
            // $.ajax({
            //   type: "POST",
            //   xhrFields: {
            //     withCredentials: true
            //   },
            //   crossDomain: true,
            //   url: appApi["update_role"],
            //   data: para,
            //   success: function(data) {
            //     vm.editLoading = false;
            //     if (data.code == 0) {
            //       vm.$message({
            //         message: data.msg,
            //         type: "success"
            //       });
            //       vm.$refs["editRole"].resetFields();
            //       vm.editRoleVisible = false;
            //     } else {
            //       vm.$message({
            //         message: data.msg,
            //         type: "warning"
            //       });
            //     }
            //     vm.currentPage = 1;
            //     vm.getRoles(1);
            //   },
            //   error: function(xhr, type, errorThrown) {}
            // });
          }).catch(() => {});
        }
      });
    },
    handleDel() {
      let vm = this;
      let ids = "";
      if (vm.roleSelect.length != 0) {
        for (let i = 0; i < vm.roleSelect.length; i++) {
          if (i == 0) {
            ids = vm.roleSelect[i].roleId;
          } else {
            ids = ids + "," + vm.roleSelect[i].roleId;
          }
        }
        let param = {
          ids: ids
        };
        this.$confirm("确认删除吗？", "删除", {
          type: "warning"
        }).then(() => {
          app.post("delete_role", param).then(data => {
            if (data.msg) {
              vm.$message({
                message: data.msg,
                type: "success"
              });
              vm.getRoles(1);
            }
          });
          //   $.ajax({
          //     type: "POST",
          //     xhrFields: {
          //       withCredentials: true
          //     },
          //     crossDomain: true,
          //     url: appApi["delete_role"],
          //     data: para,
          //     success: function(data) {
          //       if (data.code == 0) {
          //         vm.$message({
          //           message: data.msg,
          //           type: "success"
          //         });
          //       } else {
          //         vm.$message({
          //           message: data.msg,
          //           type: "warning"
          //         });
          //       }
          //       vm.getRoles(1);
          //     },
          //     error: function(xhr, type, errorThrown) {}
          //   });
        }).catch(() => {});
      } else {
        vm.$message({
          message: "请至少选择一个角色删除",
          type: "error"
        });
      }
    },
    handleAuthority() {
      let vm = this;
      vm.authorityLoading = false;
      if (vm.roleSelect.length == 1) {
        app.get("get_menu").then(data => {
          if (data.msg) {
            vm.treeData = data.msg;
            vm.authorityRoleVisible = true;
            let param = {
              id: vm.roleSelect[0].roleId
            };
            app.get("get_role_authority", param).then(data => {
              if (data.msg) {
                let getId = data.msg;
                let idArr = [];
                for (let z = 0; z < getId.length; z++) {
                  idArr.push(getId[z].id);
                }
                let childArr = [];
                let recId = function(data) {
                  for (let j = 0; j < data.length; j++) {
                    if (data[j].children.length == 0) {
                      if (idArr.indexOf(data[j].id) != -1) {
                        childArr.push(data[j].id);
                      }
                    } else {
                      recId(data[j].children);
                    }
                  }
                };
                recId(vm.treeData);
                vm.$refs.tree.setCheckedKeys(childArr);
              }
            });
          }
        });
        // $.ajax({
        //   type: "Get",
        //   xhrFields: {
        //     withCredentials: true
        //   },
        //   crossDomain: true,
        //   url: appApi["get_menu"],
        //   success: function(data) {
        //     vm.treeData = data.msg;
        //     vm.authorityRoleVisible = true;
        //     let para = {
        //       id: vm.roleSelect[0].roleId
        //     };
        //     $.ajax({
        //       type: "Post",
        //       data: para,
        //       xhrFields: {
        //         withCredentials: true
        //       },
        //       crossDomain: true,
        //       url: appApi["get_role_authority"],
        //       success: function(data) {
        //         let getId = data.msg;
        //         let idArr = [];
        //         for (let z = 0; z < getId.length; z++) {
        //           idArr.push(getId[z].id);
        //         }
        //         let childArr = [];
        //         let recId = function(data) {
        //           for (let j = 0; j < data.length; j++) {
        //             if (data[j].children.length == 0) {
        //               if (idArr.indexOf(data[j].id) != -1) {
        //                 childArr.push(data[j].id);
        //               }
        //             } else {
        //               recId(data[j].children);
        //             }
        //           }
        //         };
        //         recId(vm.treeData);
        //         vm.$refs.tree.setCheckedKeys(childArr);
        //       },
        //       error: function(xhr, type, errorThrown) {}
        //     });
        //   },
        //   error: function(xhr, type, errorThrown) {}
        // });
      } else if (vm.roleSelect.length == 0) {
        vm.$message({
          message: "至少选择一个角色"
        });
      } else {
        vm.$message({
          message: "只能选择一个角色"
        });
      }
    },
    authoritySubmit() {
      let vm = this;
      vm.initParent();
      setTimeout(function() {
        let resId = vm.tArr.join(",");
        let param = {
          roleId: vm.roleSelect[0].roleId,
          resIds: resId
        };
        vm.$confirm("确认提交吗？", "提交", {}).then(() => {
          vm.authorityLoading = true;
          app.post("submit_role_authority", param).then(data => {
            if (data) {
              vm.authorityLoading = false;
              vm.$message({
                message: data.msg,
                type: "success"
              });
              vm.authorityRoleVisible = false;
              vm.currentPage = 1;
              vm.getRoles(1);
              //菜单发生变化,触发main.vue中的事件
              vm.$root.Bus.$emit("refreshMenu", 123);
            }
          });
        //   $.ajax({
        //     type: "POST",
        //     xhrFields: {
        //       withCredentials: true
        //     },
        //     crossDomain: true,
        //     url: appApi["submit_role_authority"],
        //     data: para,
        //     success: function(data) {
        //       vm.authorityLoading = false;
        //       vm.$message({
        //         message: "保存成功",
        //         type: "success"
        //       });
        //       vm.authorityRoleVisible = false;
        //       vm.currentPage = 1;
        //       vm.getRoles(1);
        //       //菜单发生变化,触发main.vue中的事件
        //       vm.$root.Bus.$emit("refreshMenu", 123);
        //     },
        //     error: function(xhr, type, errorThrown) {}
        //   });
        }).catch(() => {});
      }, 500);
    },
    //获得所有父级
    getParent: function() {
      let vm = this;
      let rec = function(data) {
        for (let i = 0; i < data.length; i++) {
          if (vm.child.indexOf(data[i].id) != -1) {
            if (
              vm.tArr.indexOf(data[i].parentId) == -1 &&
              data[i].parentId != 0
            ) {
              vm.tArr.push(data[i].parentId);
              vm.child.push(data[i].parentId);
            }
            vm.child.splice(vm.child.indexOf(data[i].id), 1);
          }
          if (data[i].children.length == 0) {
          } else {
            rec(data[i].children);
          }
        }
      };
      rec(vm.treeData);
      vm.judgeRec();
    },
    //判断是否继续迭代
    judgeRec: function() {
      let vm = this;
      let level1 = [];
      for (let i = 0; i < vm.treeData.length; i++) {
        level1.push(vm.treeData[i].id);
      }
      for (let j = 0; j < vm.child.length; j++) {
        if (level1.indexOf(vm.child[j]) == -1) {
          vm.exp.push("t");
        } else {
          vm.exp.push("f");
        }
      }
      if (vm.exp.indexOf("t") != -1) {
        setTimeout(vm.getParent, 50);
      } else {
      }
      vm.exp = [];
    },
    initParent: function() {
      let vm = this;
      vm.tArr = [];
      let node = vm.$refs.tree.getCheckedKeys(true);
      vm.child = node;
      for (let z = 0; z < vm.child.length; z++) {
        vm.tArr.push(vm.child[z]);
      }
      vm.getParent();
    },
    pageChange(val) {
      this.pageNum = val;
      this.getRoles();
    },
    pageChange2(val) {
      this.pageSize = val;
      this.getRoles();
    }
  }
};
</script>
<style scoped>
#roleManager section {
  background-color: white;
  margin-left: 12px;
  margin-right: 12px;
  border: 1px solid #e8e8e8;
  /* height: 82vh; */
}

#roleManager .query {
  padding: 16px 15px 0px;
}

#roleManager .toolbar {
  padding: 0px 15px;
  border-bottom: 1px solid transparent;
  border-top-right-radius: 3px;
  border-top-left-radius: 3px;
}

#roleManager .el-breadcrumb {
  height: 30px;
  line-height: 30px;
  padding-left: 20px;
}

#roleManager .el-form-item__label {
  text-align: left;
}

#roleManager .systemColor .el-breadcrumb__item__inner {
  color: #97a8be;
}
</style>
