<template>
  <div id="outputTab" style="margin-bottom:30px">
    <el-form inline size="mini">
      <el-form-item>
        <el-button type="primary" @click="addOutput">添加</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" @click="deleteOutput">删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      ref="outputTable"
      :data="outputTableData"
      border
      style="margin-top:5px"
      :row-style="{ fontSize: '11px'}"
      :header-cell-style="{padding:'4px',fontSize:'12px'}"
      :cell-style="{padding:'3px'}"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号" width="70" align="center" />
      <el-table-column prop="comId" label="comId" align="center" />
      <el-table-column prop="algorithms" label="算法" align="center">
        <template slot-scope="scope">
          <template v-for="(item,index) in scope.row.algorithms">
            <span v-if="index<scope.row.algorithms.length-1" :key="index"> {{ item }},</span>
            <span v-else :key="index"> {{ item }}</span>
          </template>
        </template>
      </el-table-column>
      <el-table-column prop="source" label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" circle @click="editOutput(scope.$index)" />
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="输出序列配置" :visible.sync="outputDialogVisible">
      <el-form ref="comIdForm" :model="comIdForm" :rules="comIdFormRules" inline size="mini">
        <el-form-item label="comId" prop="comId">
          <el-input v-model="comIdForm.comId" />
        </el-form-item>
      </el-form>
      <el-form inline size="mini" style="margin-top:5px">
        <el-form-item>
          <el-button type="primary" icon="el-icon-plus" @click="handleAddAlgorithm">新增</el-button>
        </el-form-item>
      </el-form>
      <el-table
        :data="algorithmTableData"
        style="margin-top:5px"
        border
        :row-style="{ fontSize: '11px'}"
        :header-cell-style="{padding:'4px',fontSize:'12px'}"
        :cell-style="{padding:'3px'}"
      >
        <el-table-column type="index" label="序号" align="center" width="70" />
        <el-table-column prop="name" label="算法名称" align="center">
          <template slot-scope="scope">
            <el-select v-model="scope.row.name" placeholder="请选择">
              <el-option
                v-for="item in algorithmOptions"
                :key="item"
                :label="item"
                :value="item"
              />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="danger" @click="deleteAlgorithm(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="outputDialogVisible=false">取 消</el-button>
        <el-button type="primary" @click="confirm()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  props: {
    outputTableData: {
      type: Array,
      default: null
    },
    algorithmOptions: {
      type: Array,
      default: null
    }
  },

  data() {
    return {
      outputDialogVisible: false,
      algorithmTableData: [],
      currentIndex: -1,
      comIdForm: {
        comId: ''
      },
      comIdFormRules: {
        comId: [
          {
            required: true,
            message: '必填',
            trigger: 'change'
          }
        ]
      }
    }
  },
  mounted() {
  },
  methods: {
    editOutput(index) {
      this.currentIndex = index
      this.outputDialogVisible = true
      this.comIdForm.comId = this.outputTableData[index].comId
      this.algorithmTableData = []
      this.outputTableData[index].algorithms.forEach(element => {
        this.algorithmTableData.push({
          name: element
        })
      })
    },
    addOutput() {
      this.currentIndex = -1
      this.outputDialogVisible = true
      this.comIdForm.comId = ''
      this.algorithmTableData = []
    },
    deleteOutput() {
      this.$refs.outputTable.selection.forEach(element => {
        const index = this.outputTableData.indexOf(element)
        this.outputTableData.splice(index, 1)
      })
    },
    confirm() {
      this.$refs.comIdForm.validate(valid => {
        if (valid) {
          const algorithms = []
          for (let index = 0; index < this.algorithmTableData.length; index++) {
            const element = this.algorithmTableData[index]
            if (element.name === '') {
              this.$message({
                message: '有算法名称为空',
                type: 'error'
              })
              return
            }
            algorithms.push(element.name)
          }
          const obj = {
            comId: this.comIdForm.comId,
            algorithms: algorithms
          }
          if (this.currentIndex === -1) {
            this.outputTableData.push(obj)
          } else {
            this.$set(this.outputTableData, this.currentIndex, obj)
          }

          this.outputDialogVisible = false
        }
      })
    },
    handleAddAlgorithm() {
      this.algorithmTableData.push({
        name: ''
      })
    },
    deleteAlgorithm(index) {
      this.algorithmTableData.splice(index, 1)
    }
  }
}
</script>

<style>
#outputTab .el-form-item--mini .el-form-item__error {
  top: 30%;
    left: 102%;
    float: right;
    width: 70px;
}
</style>
