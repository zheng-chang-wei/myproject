
<template>
  <section id="instantnotice">
    <div>
      <div class="instantDiv">及时通知消息</div>
      <div class="instantTable">
        <el-table v-loading="listLoading" :data="tableData" stripe height="500" border>
          <el-table-column prop="faultTime" label="时间" />
          <el-table-column prop="city" label="城市" />
          <el-table-column prop="lines" label="线路" />
          <el-table-column prop="car" label="车辆" />
          <el-table-column prop="doorAddress" label="门地址" />
          <el-table-column prop="faultType" label="类型" />
        </el-table>
      </div>
      <div style="text-align:center">
        <el-button @click="reback">返回</el-button>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      listLoading: false
    }
  },
  mounted() {
    this.FaultMessage()
  },

  methods: {
    // 获取历史即时消息
    FaultMessage() {

    },
    reback() {
      this.$router.go(-1)
    }
  }
}
</script>

<style>
#instantnotice {
  width: 100%;
  height: 100%;
}
#instantnotice .instantDiv {
  text-align: center;
  background-color: #666666;
  height: 50px;
  padding-top: 25px;
  color: white;
  font-size: 20px;
  font-weight: bolder;
}
#instantnotice .instantTable {
  margin-top: 20px;
  width: 80%;
  margin-left: 10%;
  margin-right: 10%;
}
</style>
