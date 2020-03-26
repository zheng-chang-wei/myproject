<template>
  <div class="status-abstract board-card">
    <h4>状态摘要</h4>
    <div class="status-card">
      <el-row>
        <el-col :span="4">
          <div class="status-title">{{data.title}}状态</div>
        </el-col>
        <el-col :span="20">
          <div class="status-title">
            <el-button type="text" class="button-text" @click="showStatus('working')">{{data.running}}个正常运行</el-button>
            <span v-if="data.error>0">
              <el-button type="text" class="button-text" @click="showStatus('error')">{{data.error}}个异常运行</el-button>
            </span>
            <span v-if="data.stopped>0">
              <el-button type="text" class="button-text" @click="showStatus('stopped')">{{data.stopped}}个已停止</el-button>
            </span>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="status-card">
      <el-row>
        <el-col :span="4">
          <div class="status-title">{{data.title}}分类</div>
        </el-col>
        <el-col :span="20">
          <ul class="status-title">
            <li v-for="item in data.types" style="line-height: 25px">
              {{item.type}}：{{item.running}} / {{item.total}}
            </li>
          </ul>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
  export default {
    props: {
      data: Object
    },
    data() {
      return {}
    },
    methods: {
      showStatus(status) {
        console.log('show status');
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

</style>
