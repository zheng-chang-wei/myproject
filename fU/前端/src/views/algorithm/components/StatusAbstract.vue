<template>
  <div class="status-abstract board-card">
    <div class="title">状态摘要</div>
    <div class="status-card">
      <el-row>
        <el-col :span="8">
          <div class="status-title">{{ data.title }}状态</div>
        </el-col>
        <el-col :span="16">
          <div class="status-title" style="height:50px">
            <el-button type="text" class="button-text" @click="showStatus('working')">{{ data.running }}个正常运行</el-button>
            <span v-if="data.error>0">
              <el-button type="text" class="button-text" @click="showStatus('error')">{{ data.error }}个发生异常</el-button>
            </span>
            <span v-if="data.stopped>0">
              <el-button type="text" class="button-text" @click="showStatus('stopped')">{{ data.stopped }}个已停止</el-button>
            </span>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="status-card">
      <el-row>
        <el-col :span="8">
          <div class="status-title">{{ data.title }}分组</div>
        </el-col>
        <el-col :span="16">
          <div style="height:150px;padding:2px">
            <el-scrollbar style="height:100%">
              <ul class="status-title">
                <li v-for="item in data.types" :key="item.type" style="line-height: 25px">
                  {{ item.type }}：{{ item.running }} / {{ item.total }}
                </li>
              </ul>
            </el-scrollbar>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    data: { type: Object, default: () => {} }
  },
  data() {
    return {}
  },
  methods: {
    showStatus(status) {
      console.log('show status')
      this.$bus.$emit('GroupType', 'status', status)
    }
  }
}

</script>
<style scoped>
  .button-text {
    padding: 0;
    font-size: 12pt;
    margin-left: 5px;
  }

  .status-card{
    background-color: #ffffff;
  }

</style>
<style>
.status-card .el-scrollbar__wrap
{
  overflow-x: hidden !important;
}
</style>

