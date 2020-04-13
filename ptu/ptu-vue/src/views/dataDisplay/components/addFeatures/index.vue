<template>
  <!-- 添加对象和特征 -->
  <el-row id="addFeatures">
    <el-row class="objTable" :style="'height:'+(commonHeight/2-5)+'px'">
      <el-row class="center" style="height:25px">
        显示变量
      </el-row>
      <el-button style="width:97%;margin:5px 5px" icon="el-icon-plus" type="primary" size="mini" @click="openAddFeaturesDialog">添加</el-button>
      <el-table ref="table" :data="selectedFeaturesTableDatas" style="width: 100%;" border :height="(commonHeight/2)-70" :row-style="{ fontSize: '11px' }" :header-cell-style="{padding:'3px',fontSize:'12px'}" :cell-style="{padding:'3px'}">
        <el-table-column prop="featureName" label="特征名称" align="center" />
        <el-table-column align="center" label="操作" width="56">
          <template slot-scope="scope">
            <el-button
              type="danger"
              icon="el-icon-delete"
              @click.stop="handleDelete(scope.$index, scope.row)"
            />
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <el-dialog title="添加变量" :visible.sync="addFeaturesDialogVisible">
      <el-table
        ref="allFeaturesTable"
        :border="true"
        :data="allFeaturesTable"
        highlight-current-row
        :max-height="tableMaxHeight"
        :header-cell-style="{padding:'3px'}"
        :cell-style="{padding:'3px'}"
      >
        <el-table-column type="selection" align="center" />
        <el-table-column prop="featureName" label="特征名称" align="center" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="addFeaturesDialogVisible = false">取 消</el-button>
        <el-button type="primary" size="mini" @click="addFeaturesConfirm">确 定</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>

<script>
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
      addFeaturesDialogVisible: false,
      commonHeight: 0,
      allFeaturesTable: [],
      selectedFeaturesTableDatas: [],
      tableMaxHeight: ''
    }
  },
  created() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeHeight)
    this.changeHeight()
  },
  destroyed() {
    window.removeEventListener('resize', this.changeHeight)
  },
  mounted() {
    this.getDatas()
  },
  methods: {
    changeHeight() {
      this.commonHeight = document.body.offsetHeight - 62
      this.tableMaxHeight = document.body.offsetHeight - 240
    },
    openAddFeaturesDialog() {
      this.addFeaturesDialogVisible = true
      const vm = this
      setTimeout(function() {
        vm.$refs.allFeaturesTable.clearSelection()
        vm.selectedFeaturesTableDatas.forEach(element => {
          vm.$refs.allFeaturesTable.toggleRowSelection(element, true)
        })
      }, '10')
    },
    addFeaturesConfirm() {
      if (this.$refs.allFeaturesTable.selection.length > 10) {
        this.$message({
          message: '最多添加10个变量',
          type: 'warning'
        })
        return
      }
      this.selectedFeaturesTableDatas = []
      this.$refs.allFeaturesTable.selection.forEach(element => {
        this.selectedFeaturesTableDatas.push(element)
      })
      this.addFeaturesDialogVisible = false
      this.$bus.$emit('featuresChange', this.selectedFeaturesTableDatas)
    },
    handleDelete(index, row) {
      this.selectedFeaturesTableDatas.splice(index, 1)
      this.$bus.$emit('featuresChange', this.selectedFeaturesTableDatas)
    },
    getDatas() {
      switch (this.type) {
        case 'ComId':
          global.comIdFeatureNames.forEach(element => {
            this.allFeaturesTable.push({
              featureName: element
            })
          })
          break
        case 'CsPort':
          global.csPortFeatureNames.forEach(element => {
            this.allFeaturesTable.push({
              featureName: element
            })
          })
          break
        default:
          break
      }
    }

  }
}
</script>

<style>
#addFeatures .cell .el-button{
  padding: 9px 8px;
  font-size: 10px;
  border-radius: 15px;
}
#addFeatures .el-input{
  width: 100px;
}
.objTable{
    margin-left:5px;
    margin-top: 5px;
    border-radius: 2px;
    border: 1px solid #CCCCCC;
    line-height: 1px;
}
</style>
