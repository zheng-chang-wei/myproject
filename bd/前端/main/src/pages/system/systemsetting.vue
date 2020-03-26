<template>
  <section id="systemSettingManager" class="app-container">
    <el-tabs v-model="activeName" style="margin-left:20px">
      <el-tab-pane label="地面端数据管理" name="1">
        <groundDataManager />
      </el-tab-pane>
      <el-tab-pane label="车载端数据管理" name="2">
        <trainDataManager />
      </el-tab-pane>
      <el-tab-pane label="通信设置" name="3">
        <communication-setting />
      </el-tab-pane>
      <el-tab-pane label="数字孪生管理" name="4">
        <digitalTwins :active-name="activeName" />
      </el-tab-pane>
      <el-tab-pane label="系统日志管理" name="5">
        <log />
      </el-tab-pane>
    </el-tabs>
  </section>
</template>

<script>
import communicationSetting from '@/components/systemsetting/communicationSetting'
import groundDataManager from '@/components/systemsetting/groundDataManager'
import trainDataManager from '@/components/systemsetting/trainDataManager'
import log from '@/components/systemsetting/log'
import digitalTwins from '@/components/systemsetting/digitalTwins'
export default {
  components: {
    communicationSetting,
    groundDataManager,
    trainDataManager,
    log,
    digitalTwins
  },
  data() {
    return {
      activeName: '1'
    }
  },
  mounted() {},
  methods: {}
}

</script>
<style>
	#systemSettingManager {
		background-color: white;
		margin-left: 12px;
		margin-right: 12px;
		/* border: 1px solid #e8e8e8; */
	}

</style>
