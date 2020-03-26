<template>
  <div>
    <el-radio v-model="clockConfigInfo.timeOn" :label="false" style="margin-top:20px" @input="change">系统时间</el-radio>
    <el-radio v-model="clockConfigInfo.timeOn" :label="true" @input="change">总线时间</el-radio>
    <template v-if="clockConfigInfo.timeOn">
      <div>
        <div style="height:20px; margin-top:20px">
          总线类型：
          <el-select v-model="clockConfigInfo.busType" placeholder="请选择" @change="selectTypeChange" @input="change">
            <el-option v-for="item in busTypes" :key="item" :label="item" :value="item" />
          </el-select>
        </div>
        <div style="min-height:200px; margin-top:40px; margin-bottom:60px">
          <el-table :data="dateTableData" style="margin-top:20px; width: 100%">
            <el-table-column prop="lable" align="center" />
            <el-table-column label="信号名" prop="variable.name" align="center" />
            <el-table-column align="center">
              <template slot-scope="scope">
                <el-button size="mini" type="primary" @click="handleChoose(scope.$index, scope.row)">选择</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </template>
    <div style="position: absolute; bottom: 10px;">
      <el-button style="width: 210px" @click="cancel">取消</el-button>
      <el-button style="width: 210px" type="primary" @click="previousStep">上一步</el-button>
      <el-button style="width: 210px" type="primary" @click="save">保存</el-button>
    </div>
    <el-dialog title="变量列表" :visible.sync="dialogVisible" width="65%">
      <el-table
        v-if="clockConfigInfo.busType==='MVB'"
        ref="table"
        :data="dialogVariables.variables"
        tooltip-effect="dark"
        style="width: 100%"
        border
      >
        <el-table-column label="" width="35">
          <template slot-scope="scope">
            <el-radio v-model="templateRadio" :label="scope.row">&nbsp;</el-radio>
          </template>
        </el-table-column>
        <el-table-column prop="signalName" label="变量名" align="center" />
        <el-table-column prop="name" label="变量别名" align="center" />
        <el-table-column prop="device" label="设备号" align="center" />
        <el-table-column prop="deviceName" label="设备名" align="center" />
        <el-table-column prop="carriage" label="车厢名" align="center" />
        <el-table-column prop="system" label="系统" align="center" />
        <el-table-column prop="port" label="端口" align="center" />
        <el-table-column prop="fcode" label="fcode" align="center" />
        <el-table-column prop="dataType" label="数据类型" align="center" />
      </el-table>
      <el-table
        v-else-if="clockConfigInfo.busType==='TRDP'"
        ref="table"
        :data="dialogVariables.variables"
        tooltip-effect="dark"
        style="width: 100%"
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
        <el-table-column prop="dataType" label="数据类型" align="center" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible=false">取 消</el-button>
        <el-button type="primary" @click="confirm()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  props: {
    clockConfigInfo: {
      type: Object,
      default: null
    },
    variables: {
      type: Array,
      default: null
    }
  },

  data() {
    return {
      templateRadio: {},
      dialogVisible: false,
      busTypes: ['MVB', 'TRDP'],
      currentCheckedIndex: 0,
      dateTableData: [{
        lable: '年',
        variable: { name: '' }
      }, {
        lable: '月',
        variable: { name: '' }
      }, {
        lable: '日',
        variable: { name: '' }
      }, {
        lable: '时',
        variable: { name: '' }
      }, {
        lable: '分',
        variable: { name: '' }
      }, {
        lable: '秒',
        variable: { name: '' }
      }, {
        lable: '毫秒',
        variable: { name: '' }
      }],
      mvbVariables: {},
      ecnVariables: {},
      dialogVariables: {},
      a: null,
      b: {}

    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      this.variables.forEach(variable => {
        if (variable.type.indexOf('MVB') !== -1) {
          this.mvbVariables.variables = variable.variables
          this.mvbVariables.slotId = variable.slotId
        } else if (variable.type.indexOf('ECN') !== -1) {
          this.ecnVariables.variables = variable.variables
          this.ecnVariables.slotId = variable.slotId
        }
      })

      if (this.clockConfigInfo.mvbGroup) {
        this.dialogVariables = this.mvbVariables
        this.clockConfigInfo.busType = 'MVB'
        for (let index = 0; index < this.clockConfigInfo.mvbGroup.variables.length; index++) {
          const mvb = this.clockConfigInfo.mvbGroup.variables[index]
          this.dateTableData[index].variable = mvb
        }
      } else if (this.clockConfigInfo.ecnGroup) {
        this.dialogVariables = this.ecnVariables
        this.clockConfigInfo.busType = 'TRDP'
        for (let index = 0; index < this.clockConfigInfo.ecnGroup.variables.length; index++) {
          const ecn = this.clockConfigInfo.ecnGroup.variables[index]
          this.dateTableData[index].variable = ecn
        }
      } else {
        this.clockConfigInfo.mvbGroup = {
          variables: []
        }
        this.clockConfigInfo.ecnGroup = {
          variables: []
        }
      }
      if (this.clockConfigInfo.busType === '') {
        if (this.mvbVariables.variables !== undefined && this.ecnVariables.variables === undefined) {
          this.clockConfigInfo.busType = 'MVB'
          this.busTypes = ['MVB']
          this.dialogVariables = this.mvbVariables
        } else if (this.mvbVariables.variables === undefined && this.ecnVariables.variables !== undefined) {
          this.clockConfigInfo.busType = 'TRDP'
          this.busTypes = ['TRDP']
          this.dialogVariables = this.ecnVariables
        } else {
          this.dialogVariables = this.mvbVariables
        }
      }
    },
    selectTypeChange() {
      if (this.clockConfigInfo.busType === this.busTypes[0]) {
        this.dialogVariables = this.mvbVariables
      } else if (this.clockConfigInfo.busType === this.busTypes[1]) {
        this.dialogVariables = this.ecnVariables
      }
    },
    handleChoose(index, row) {
      this.dialogVisible = true
      this.currentCheckedIndex = index
      this.templateRadio = this.getSame(row.variable)
    },
    getSame(row) {
      for (let index = 0; index < this.dialogVariables.variables.length; index++) {
        const variable = this.dialogVariables.variables[index]
        if (row.name === variable.name && row.signalName === variable.signalName && row.comId === variable.comId) {
          return variable
        }
      }
    },
    confirm() {
      this.dateTableData[this.currentCheckedIndex].variable = this.templateRadio
      if (this.clockConfigInfo.busType === 'MVB') { // MVB
        this.$set(this.clockConfigInfo.mvbGroup.variables, this.currentCheckedIndex, this.templateRadio)
        this.clockConfigInfo.mvbGroup.slotId = this.dialogVariables.slotId
      } else if (this.clockConfigInfo.busType === 'TRDP') { // TRDP
        this.$set(this.clockConfigInfo.ecnGroup.variables, this.currentCheckedIndex, this.templateRadio)
        this.clockConfigInfo.ecnGroup.slotId = this.dialogVariables.slotId
      }
      this.dialogVisible = false
    },
    cancel() {
      this.$emit('cancel')
    },
    previousStep() {
      this.$emit('previousStep')
    },
    save() {
      this.$emit('save')
    },
    change() {
      this.$forceUpdate()
    }
  }
}
</script>

<style>

</style>
