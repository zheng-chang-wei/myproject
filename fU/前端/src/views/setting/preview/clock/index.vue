<template>
  <el-card class="box-card" shadow="hover">
    <el-row slot="header" class="center">
      <el-col :span="23"><span>时间来源配置</span></el-col>
      <el-col :span="1">
        <el-button type="text" @click="editClicked">编辑</el-button>
      </el-col>
    </el-row>
    <el-form :model="clockConfigInfo" label-width="95px">
      <el-form-item label="时间类型：" size="small">
        {{ clockConfigInfo.timeOn?'总线时间':'系统时间' }}
      </el-form-item>
      <el-form-item v-if="clockConfigInfo.timeOn" label="总线类型：" size="small">
        {{ clockConfigInfo.busType }}
      </el-form-item>
    </el-form>
    <el-row style="margin:5px 10px">
      <el-table
        v-if="clockConfigInfo.busType===busTypes[0]"
        :data="clockConfigInfo.mvbGroup.variables"
        tooltip-effect="dark"
        border
      >
        <el-table-column prop="time" label="时间" align="center" />
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
        v-else-if="clockConfigInfo.busType===busTypes[1]"
        :data="clockConfigInfo.ecnGroup.variables"
        tooltip-effect="dark"
        border
      >
        <el-table-column prop="time" label="时间" align="center" />
        <el-table-column prop="signalName" label="变量名" align="center" />
        <el-table-column prop="name" label="变量别名" align="center" />
        <el-table-column prop="sourceIp" label="源IP" align="center" />
        <el-table-column prop="comId" label="comId" align="center" />
        <el-table-column prop="dataType" label="数据类型" align="center" />
      </el-table>
    </el-row>
  </el-card>
</template>

<script>
export default {
  components: {

  },
  props: {
    clockConfigInfo: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      busTypes: ['MVB', 'TRDP']
    }
  },
  created() {
  },
  methods: {
    editClicked() {
      this.$emit('edit', 4)
    }
  }
}
</script>

<style>

</style>
