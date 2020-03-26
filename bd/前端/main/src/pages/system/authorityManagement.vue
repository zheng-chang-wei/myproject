<template>
  <section id="authorityManager">
    <el-tabs v-model="activeName" style="margin-left:20px" tab-position="left">
      <el-tab-pane label="用户管理" name="1">
        <user />
      </el-tab-pane>
      <el-tab-pane label="部门管理" name="2">
        <dept />
      </el-tab-pane>
    </el-tabs>
  </section>
</template>

<script>
import user from '@/components/authorityManagement/user'
import dept from '@/components/authorityManagement/dept'
export default {
  components: {
    user,
    dept
  },
  data() {
    return {
      activeName: '1'
    }
  },
  mounted() {
    // 页面改变时,更改尺寸
    window.addEventListener('resize', this.changeSectionHeight)
    this.changeSectionHeight()
  },
  methods: {
    changeSectionHeight() {
      // var sectionHeight = document.body.offsetHeight - 115 + 'px'
      // $('#authorityManager').css({
      //   height: sectionHeight
      // })
    }
  }
}
</script>
<style>
#authorityManager {
	background-color: white;
	margin-left: 12px;
	margin-right: 12px;
	border: 1px solid #e8e8e8;
}
 .red {
  border-width: 1px;
  border-style: solid;
  border-color: red;
}
</style>
