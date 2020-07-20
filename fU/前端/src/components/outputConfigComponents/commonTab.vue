<template>
  <div style="margin-bottom:30px">
    <el-table
      :data="commonTableData"
      border
      :row-style="{ fontSize: '11px'}"
      :header-cell-style="{padding:'4px',fontSize:'12px'}"
      :cell-style="{padding:'3px'}"
    >
      <el-table-column type="index" label="序号" width="70" align="center" />
      <el-table-column prop="name" label="变量名称" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isInvalid" size="mini" type="danger">失效</el-tag>
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="dataType" label="类型" align="center" />
      <el-table-column prop="type" label="来源" align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.type" placeholder="请选择" @change="typeChange">
            <el-option
              v-for="item in typeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="source" label="已选信号" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.source.signalName }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="operation" label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" circle @click="signalSelect(scope.$index)" />
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="信号选择" :visible.sync="signalDialogVisible" width="65%">
      <el-table
        ref="table"
        :data="variables"
        :row-style="{ fontSize: '11px'}"
        :header-cell-style="{padding:'4px',fontSize:'12px'}"
        :cell-style="{padding:'3px'}"
        border
      >
        <el-table-column label="" width="35">
          <template slot-scope="scope">
            <el-radio v-model="templateRadio" :label="scope.row">&nbsp;</el-radio>
          </template>
        </el-table-column>
        <el-table-column prop="signalName" label="变量名" align="center" />
        <el-table-column prop="name" label="变量别名" align="center" />
        <el-table-column prop="sourceIp" label="源IP" align="center" />
        <el-table-column prop="comId" label="comId" align="center" />
        <el-table-column prop="dataTypeAlias" label="数据类型" align="center" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="signalDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  props: {
    commonTableData: {
      type: Array,
      default: null
    },

    variables: {
      type: Array,
      default: null
    }
  },

  data() {
    return {
      typeOptions: [{
        value: 1,
        label: '总线'
      }, {
        value: 0,
        label: '本地'
      }],
      signalDialogVisible: false,
      templateRadio: {},
      currentIndex: -1

    }
  },
  mounted() {
  },
  methods: {
    signalSelect(index) {
      this.currentIndex = index
      this.templateRadio = this.commonTableData[this.currentIndex].source
      this.signalDialogVisible = true
    },
    typeChange(value) {},
    confirm() {
      this.commonTableData[this.currentIndex].source = this.templateRadio
      this.commonTableData[this.currentIndex].isInvalid = false
      this.signalDialogVisible = false
    }

  }
}
</script>

<style>

</style>
