<template>
  <section id="deptManager" class="app-container">
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;padding-top:16px">
      <el-form :inline="true" size="mini">
        <el-form-item>
          <el-button v-show="btnArr[0]=='1'" type="primary" @click="handleAdd('addForm')">
            新增</el-button>
          <el-button v-show="btnArr[1]=='1'" type="primary" @click="handleEdit">
            编辑</el-button>
          <el-button v-show="btnArr[2]=='1'" type="danger" @click="batchRemove">
            删除</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <el-table
      :data="dataSource"
      style="width: 98%;margin-bottom: 20px;margin-left:15px;"
      border
      row-key="id"
      @selection-change="checkedChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="text" label="部门名称" />
    </el-table>

    <!--新增界面-->
    <el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
      <el-form
        ref="addForm"
        :rules="addRules"
        :model="addForm"
        label-width="100px"
        style="width:80%;margin-left:5%"
      >
        <el-form-item label="部门名称" prop="deptName" required>
          <el-input v-model="addForm.deptName" auto-complete="off" placeholder="请输入菜单名称" :maxlength="20" />
        </el-form-item>
        <el-form-item label="上级部门" prop="parentId">
          <el-select v-model="addForm.parentId" placeholder="请选择">
            <el-option
              v-for="(item,index) in parentMenuOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              :style="optionMarginLeft(item,index)"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="addLoading" style="width:90px;" @click.native="addSubmit">提交
        </el-button>
        <el-button type="cancel" style="width:90px;" @click.native="addFormVisible = false">取消</el-button>
      </div>
    </el-dialog>

    <!--编辑界面-->
    <el-dialog title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false">
      <el-form
        ref="editForm"
        :rules="editRules"
        :model="editForm"
        label-width="100px"
        style="width:80%;margin-left:5%"
      >
        <el-form-item label="部门名称" prop="deptName" required>
          <el-input v-model="editForm.deptName" auto-complete="off" placeholder="请输入菜单名称" :maxlength="20" />
        </el-form-item>
        <el-form-item label="上级部门" prop="parentId">
          <el-select v-model="editForm.parentId" placeholder="请选择">
            <el-option
              v-for="(item,index) in parentMenuOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              :style="optionMarginLeft(item,index)"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="editLoading" style="width:90px;" @click.native="editSubmit">提交
        </el-button>
        <el-button type="cancel" style="width:90px;" @click.native="editFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script type="text/ecmascript-6">
import Vue from 'vue'
import app from '@/common/js/app'
export default {
  components: {
  },
  data() {
    // 新增&编辑弹框里长度到达限制时提示
    var checkDeptName = (rule, value, callback) => {
      if (value.length === 0) {
        callback(new Error('不能为空'))
      } else if (value.length === 20) {
        callback(new Error('菜单名称长度限制20字符'))
      } else {
        callback()
      }
    }
    return {
      listLoading: false,
      sels: [], // 列表选中的选项
      dataSource: [],
      // 上级菜单选项
      parentMenuOptions: [{
        value: '0',
        label: '------顶层部门------',
        level: '0'
      }],
      // 新增界面数据
      addForm: {
        deptName: '',
        parentId: '0'
      },
      addFormVisible: false, // 新增界面是否显示
      addLoading: false,
      // 编辑界面数据
      editForm: {
        name: '',
        parentId: '0'
      },
      editFormVisible: false, // 编辑界面是否显示
      editLoading: false,

      statusCode: '111', // 用于新增,编辑,删除按钮的显示隐藏,默认111全显示
      btnArr: ['1', '1', '1'], // 把楼上的分割成数组
      // 新增弹框表单验证
      addRules: {
        deptName: [{
          required: true,
          message: '必填',
          trigger: 'blur'
        },
        {
          validator: checkDeptName,
          trigger: 'change'
        }
        ]
      },
      // 编辑弹框表单验证
      editRules: {
        deptName: [{
          required: true,
          message: '必填',
          trigger: 'blur'
        },
        {
          validator: checkDeptName,
          trigger: 'change'
        }
        ]
      },
      tableMaxHeight: document.body.offsetHeight - 280
    }
  },
  mounted() {
    this.getDepts()
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeTableMaxHeight)
    this.changeTableMaxHeight()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeTableMaxHeight)
  },
  methods: {
    checkedChange(sels) {
      this.sels = sels
    },
    // 动态更改表格最大高度
    changeTableMaxHeight() {
      this.tableMaxHeight = document.body.offsetHeight - 220
      // var sectionHeight = document.body.offsetHeight - 162 + "px";
      // $("#deptManager").css({
      // 	height: sectionHeight
      // });
    },

    getDepts() {
      const vm = this
      app.get('get_dept_tree').then(data => {
        if (data) {
          vm.dataSource = data.msg
          vm.getParentDept()
        }
      })
    },
    // 获取上级部门选项
    getParentDept() {
      const vm = this
      // 用于取出格式化后的数据
      function DataTransfer(data) {
        if (!(this instanceof DataTransfer)) {
          return new DataTransfer(data, null, null)
        }
      }
      DataTransfer.treeToArray = function(data, parent, level) {
        let tmp = []
        Array.from(data).forEach(function(record) {
          if (parent) {
            Vue.set(record, '_parent', parent)
          }
          let _level = 0
          if (level !== undefined && level !== null) {
            _level = level + 1
          }
          Vue.set(record, '_level', _level)
          tmp.push(record)
          if (record.children && record.children.length > 0) {
            const children = DataTransfer.treeToArray(
              record.children,
              record,
              _level
            )
            tmp = tmp.concat(children)
          }
        })
        return tmp
      }
      const data = DataTransfer.treeToArray(vm.dataSource, null, null)
      vm.parentMenuOptions = []
      // 提取data,渲染selet选项
      data.forEach(item => {
        vm.parentMenuOptions.push({
          value: item.id,
          label: item.text,
          level: item._level
        })
      })
      vm.parentMenuOptions = [{
        value: '0',
        label: '------顶层部门------',
        level: '0'
      }].concat(vm.parentMenuOptions)
    },
    // 菜单选项空格
    optionMarginLeft(item, index) {
      return 'margin-left:' + 14 * item.level + 'px'
    },
    // 显示新增弹窗
    handleAdd() {
      var vm = this
      setTimeout(() => {
        if (vm.$refs.addForm) vm.$refs.addForm.resetFields()
      }, 50)
      this.addFormVisible = true
      this.addForm = {
        deptName: '',
        parentId: '0'
      }
    },
    // 新增
    addSubmit() {
      this.$refs.addForm.validate(valid => {
        var vm = this
        if (valid) {
          this.$confirm('确认提交吗？', '提交', {
            type: 'warning'
          }).then(() => {
            const para = Object.assign({}, vm.addForm)
            vm.addLoading = true
            app.post('add_dept', para).then(data => {
              if (data) {
                vm.addLoading = false
                vm.$message({
                  message: data.msg,
                  type: 'success'
                })
                vm.$refs['addForm'].resetFields()
                vm.addFormVisible = false
                vm.getDepts()
              }
            })
          }
          ).catch(() => {})
        }
      })
    },
    // 显示编辑界面
    handleEdit() {
      var vm = this
      setTimeout(() => {
      }, 50)
      // 进行判断,只能选择一个进行编辑
      if (this.sels.length === 0) {
        this.$message('请勾选一个用户')
        return
      }
      if (this.sels.length > 1) {
        this.$message('只能选择一个用户')
        return
      }
      this.editForm = {
        deptName: '',
        parentId: '0'
      }
      vm.editFormVisible = true
      vm.editForm.deptId = vm.sels[0].id
      vm.editForm.deptName = vm.sels[0].text
      vm.editForm.parentId = vm.sels[0].parentId !== 0 ? vm.sels[0].parentId : '0'
    },
    // 编辑
    editSubmit() {
      var vm = this
      this.$refs.editForm.validate(valid => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {
            type: 'warning'
          }).then(() => {
            this.editLoading = true
            const param = Object.assign({}, vm.editForm)
            app.post('update_dept', param).then(data => {
              if (data) {
                vm.editLoading = false
                vm.$message({
                  message: data.msg,
                  type: 'success'
                })
                vm.$refs['editForm'].resetFields()
                vm.editFormVisible = false
                vm.getDepts()
              }
            })
          }
          ).catch(() => {})
        }
      })
    },
    // 删除
    batchRemove() {
      var vm = this
      var ids = this.sels.map(item => item.id).toString()
      if (this.sels.length === 0) {
        this.$message({
          message: '请至少勾选一个用户删除',
          type: 'error'
        })
        return
      }
      this.$confirm('确认删除选中记录？', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true
        const param = {
          ids: ids
        }
        app.post('delete_dept', param).then(data => {
          if (data) {
            vm.listLoading = false
            vm.getDepts()
            vm.$message({
              message: '删除成功',
              type: 'success'
            })
          }
        })
      }).catch(() => {})
    }
  }
}
</script>

<style>
/* section#deptManager {
  background-color: white;
  margin-left: 12px;
  margin-right: 12px;
  height: 82vh;
  border: 1px solid #e8e8e8;
} */

#deptManager .query {
  padding: 10px 15px 0px;
}

#deptManager .toolbar {
  padding: 0px 15px;
  border-bottom: 1px solid transparent;
  border-top-right-radius: 3px;
  border-top-left-radius: 3px;
}

#deptManager .el-form-item__label {
  text-align: right;
}

#deptManager .el-breadcrumb {
  height: 30px;
  line-height: 30px;
  padding-left: 20px;
}

#deptManager .systemColor .el-breadcrumb__item__inner {
  color: #97a8be;
}

#deptManager .el-select {
  width: 100%;
}

#deptManager .el-dialog__wrapper .el-dialog__body {
  max-height: 410px;
}
#deptManager .el-table::before {
  background-color: transparent;
}
</style>
