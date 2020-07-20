<template>
  <div id="statusAbstract">
    <h4 class="title">状态摘要</h4>
    <el-card shadow="hover" style="height:190px;">
      <div slot="header" class="clearfix">
        <el-col :span="6" class="button-text">{{ abstract.title }}状态:</el-col>
        <el-col :span="6" class="button-text">{{ abstract.running }}个运行</el-col>
        <el-col v-if="abstract.error>0" :span="6" class="button-text">{{ abstract.error }}个异常</el-col>
        <el-col v-if="abstract.stopped>0" :span="6" class="button-text">{{ abstract.stopped }}个已停止</el-col>
      </div>
      <el-row>
        <el-col :span="6">
          <div class="button-text" style="line-height: 20px">{{ abstract.title }}分类:</div>
        </el-col>
        <el-col :span="18">
          <el-row v-for="item in abstract.types" :key="item.type" class="button-text" style="line-height: 20px">
            {{ item.type }}：{{ item.running }} / {{ item.total }}
          </el-row>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>
<script>
import app from '@/common/js/app'
export default {
  data() {
    return {
      abstract: {
        title: '板卡',
        running: 0,
        error: 0,
        stopped: 0,
        types: []
      }
    }
  },
  mounted() {
    this.getAbstract()
  },
  methods: {
    getAbstract() {
      app.get('board_abstract').then(res => {
        if (res.code === 0) {
          this.abstract.running = res.data.running
          this.abstract.error = res.data.error
          this.abstract.stopped = res.data.stopped
          this.abstract.types = res.data.types
        }
      }).catch(() => {
        this.$emit('error')
      })
    }
  }
}

</script>
<style scoped>.button-text {
  padding: 0;
  font-size: 8pt;
}

.status-card {
  background-color: #ffffff;
}

</style><style>#statusAbstract .el-card__header {
  padding: 18px 5px;
}

#statusAbstract .el-card__body {
  padding: 10px 5px;
}

</style>
