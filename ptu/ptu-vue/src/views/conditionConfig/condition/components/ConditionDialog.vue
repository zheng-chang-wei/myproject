<template>
  <el-dialog :title="title" :visible.sync="addConditionVisible" :close-on-click-modal="false">
    <el-form ref="form" :model="form" :rules="formRules" :inline="true" size="mini">
      <el-form-item label="条件名称" prop="conditionName">
        <el-input v-model="form.conditionName" />
      </el-form-item>
    </el-form>
    <el-form :inline="true" size="mini">
      <el-form-item>
        <el-button type="primary" icon="el-icon-plus" @click="handleAddLogical">添加条件</el-button>
        <el-button type="danger" icon="el-icon-delete" @click="handleDelLogical">删除条件</el-button>
      </el-form-item>
    </el-form>
    <el-table
      ref="logicalTable"
      border
      :data="logicalTableDatas"
      highlight-current-row
      :header-cell-style="{padding:'3px',fontSize:'11px'}"
      :cell-style="{padding:'3px',fontSize:'10px'}"
    >
      <el-table-column type="selection" align="center" />
      <el-table-column prop="featureName" label="特征名称" align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.featureName" size="mini" @change="featureChange">
            <el-option v-for="item in featureNameOptions" :key="item" :label="item" :value="item" />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="logicalOperator" label="关系运算符" align="center" width="110">
        <template slot-scope="scope">
          <el-select v-model="scope.row.logicalOperator" size="mini">
            <el-option v-for="item in logicalOperatorOptions" :key="item" :label="item" :value="item" />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="value" label="参数值" align="center" width="110">
        <template slot-scope="scope">
          <el-input v-model="scope.row.value" size="mini" oninput="this.value=this.value.replace(/\D/g,'')" />
        </template>
      </el-table-column>
      <el-table-column prop="relationship" label="关系" align="center" width="110">
        <template slot-scope="scope">
          <el-select v-model="scope.row.relationship" size="mini" :disabled="logicalTableDatas.indexOf(scope.row)===logicalTableDatas.length-1">
            <el-option v-for="item in relationshiprOptions" :key="item" :label="item" :value="item" />
          </el-select>
        </template>
      </el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button type="cancel" style="width:90px;" size="mini" @click.native="addConditionVisible = false">取消</el-button>
      <el-button type="primary" :loading="addLoading" size="mini" style="width:90px;" @click.native="saveCondition">提交</el-button>
    </div>
  </el-dialog>
</template>
<script>
import app from '@/common/js/app'
import global from '@/common/global'
export default {
  props: {
    type: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      title: '',
      form: {
        conditionName: ''
      },
      addConditionVisible: false,
      logicalTableDatas: [],
      addLoading: false,
      featureNameOptions: [],
      logicalOperatorOptions: ['>', '<', '==', '!='],
      relationshiprOptions: ['&&', '||'],
      id: null,
      formRules: {
        conditionName: [
          {
            required: true,
            message: '必填',
            trigger: 'change'
          }
        ]
      },
      relationships: []
    }
  },
  created() {
    switch (this.type) {
      case 'ComId':
        this.featureNameOptions = global.comIdFeatureNames_CN
        break
      case 'CsPort':
        this.featureNameOptions = global.csPortFeatureNames_CN
        break

      default:
        break
    }
  },
  methods: {
    openDialog(obj) {
      this.title = obj.title
      // 新增
      if (obj.row === null) {
        this.logicalTableDatas = []
        this.form.conditionName = ''
        this.id = null
      } else {
        // 编辑
        this.id = obj.row.id
        this.form.conditionName = obj.row.conditionName
        this.relationships = this.getRelationships(obj.row.expression)
        this.logicalTableDatas = this.getTableDatas(obj.row.expression)
      }
      this.addConditionVisible = true
      this.addLoading = false
    },
    getTableDatas(expressionString) {
      const ands = expressionString.split('&&')
      const expressions = []
      const tableDatas = []
      ands.forEach(element => {
        const ors = element.split('||')
        if (ors.length > 0) {
          ors.forEach(or => {
            expressions.push(or)
          })
        } else {
          expressions.push(ors)
        }
      })
      for (let index = 0; index < expressions.length; index++) {
        const element = expressions[index]
        const logicalOperator = this.getLogicalOperator(element)
        const value = element.split(logicalOperator)

        tableDatas.push({
          featureName: value[0],
          logicalOperator: logicalOperator,
          value: value[1],
          relationship: this.relationships[index]
        })
      }

      return tableDatas
    },
    // 获取逻辑运算符
    getLogicalOperator(element) {
      for (let index = 0; index < this.logicalOperatorOptions.length; index++) {
        const logicalOperator = this.logicalOperatorOptions[index]
        if (element.indexOf(logicalOperator) !== -1) {
          return logicalOperator
        }
      }
    },
    getRelationships(expressionString) {
      const relationships = []
      for (let i = 0; i < expressionString.length - 1; i++) {
        const value = expressionString.slice(i, i + 2)
        if (this.relationshiprOptions.indexOf(value) !== -1) {
          relationships.push(value)
        }
      }
      relationships.push('')
      return relationships
    },
    handleAddLogical() {
      this.logicalTableDatas.push({
        featureName: '',
        logicalOperator: '',
        relationship: '',
        value: ''
      })
    },
    featureChange(value) {
    },
    handleDelLogical() {
      this.$refs.logicalTable.selection.forEach(element => {
        const index = this.logicalTableDatas.indexOf(element)
        this.logicalTableDatas.splice(index, 1)
      })
      this.logicalTableDatas[this.logicalTableDatas.length - 1].relationship = ''
    },
    saveCondition() {
      this.$refs.form.validate(valid => {
        if (valid) {
          if (this.logicalTableDatas.length === 0) {
            this.$message({
              type: 'warning',
              message: '表格不能为空'
            })
            return
          }
          if (!this.validateLogicalTableDatas()) {
            return
          }
          this.addLoading = true
          // 逻辑表达式
          let expression = ''
          this.logicalTableDatas.forEach(element => {
            expression += element.featureName + ' ' + element.logicalOperator + ' ' + element.value + ' ' + element.relationship + ' '
          })
          const parm = {
            id: this.id,
            conditionName: this.form.conditionName,
            expression: expression,
            type: this.type
          }
          app.post('saveCondition', parm).then(response => {
            if (response.code === 0) {
              this.addConditionVisible = false

              this.$message({
                type: 'success',
                message: '保存成功,刷新页面方能启用'
              })
              this.$emit('getCondition')
            }
          }).catch(() => { this.addLoading = false })
        }
      })
    },
    validateLogicalTableDatas() {
      const featureNames = []
      const logicals = []

      for (let i = 0; i < this.logicalTableDatas.length; i++) {
        const element = this.logicalTableDatas[i]
        if (element.featureName === '') {
          this.$message({
            type: 'warning',
            message: '特征名称不能为空'
          })
          return false
        }
        if (element.logicalOperator === '') {
          this.$message({
            type: 'warning',
            message: '关系运算符不能为空'
          })
          return false
        }
        if (element.value === '') {
          this.$message({
            type: 'warning',
            message: '参数值不能为空'
          })
          return false
        }
        if (i !== this.logicalTableDatas.length - 1 && element.relationship === '') {
          this.$message({
            type: 'warning',
            message: '关系不能为空'
          })
          return false
        }
        const index = featureNames.indexOf(element.featureName)
        if (index === -1) {
          featureNames.push(element.featureName)
          logicals.push([element])
        } else {
          logicals[index].push(element)
        }
      }
      return true
    }
  }
}
</script>
<style >
.el-dialog__body {
    padding: 5px 15px;
    color: #606266;
    font-size: 14px;
    word-break: break-all;
}
</style>
