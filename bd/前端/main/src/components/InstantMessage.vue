<template>
  <!-- 左侧即时消息 -->
  <div id="instantMessage">
    <el-row style="background-color: white;border: 1px #666666 solid;margin-top:10px;">
      <el-row class="instantcenter" style="color:white;height:30px;background-color:#AEAEAE">即时消息</el-row>
      <template v-for="item in datas">
        <el-row id="messageLi" :key="item.detail" style="background:#DBDBDB" class="instantcenter">
          <el-col :span="20" style="margin-left:10px">{{ item.detail }}</el-col>
          <el-col :span="5">
            <el-button v-if="item.type==='预警'" style="background-color:green;color:white" type="text" size="small" icon="el-icon-edit" @click="redict">{{ item.type }}</el-button>
            <el-button v-else-if="item.type==='寿命'" style="background-color:#BA55D3;color:white" type="text" size="small" icon="el-icon-edit" @click="redict">{{ item.type }}</el-button>
            <el-button v-else-if="item.type==='故障'" style="background-color:red;color:white" type="text" size="small" icon="el-icon-edit" @click="redict">{{ item.type }}</el-button>
          </el-col>
        </el-row>
      </template>
    </el-row>
  </div>
</template>
<script>
export default {
  props: {
    datas: {
      type: Array,
      default: null
    }
  },
  data() {
    return {}
  },
  mounted() {},
  methods: {
    redict() {
      this.$router.push('/instantnotice')
    }
  }
}
</script>

<style >
#instantMessage .instantcenter {
  display: flex;
  -webkit-align-items: center;
  align-items: center;
  -webkit-justify-content: center;
  justify-content: center;
}
#instantMessage #messageLi {
  list-style: none;
  margin-top: 5px;
  margin-bottom: 10px;
}
</style>
