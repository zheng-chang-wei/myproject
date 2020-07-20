<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb class="breadcrumb-container" />

    <div class="right-menu center">
      <el-button type="success" :disabled="!canLaunch" :loading="launchLoading" @click="handleLaunch">启动</el-button>
      <el-button type="danger" :disabled="!canTerminate" :loading="terminateLoading" style="margin-right:50px" @click="handleTerminate">停止</el-button>
      <el-tooltip content="退出" placement="top">
        <img :src="require('../../assets/images/logout.svg')" style="margin-right:10px;cursor: pointer;" @click="logout">
      </el-tooltip>
    </div>
    <el-dialog :visible.sync="progressVisible" title="进度" :close-on-click-modal="false" destroy-on-close>
      <div>
        <p v-for="step in steps" :key="step">{{ step.message }}</p>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  mapGetters
} from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import app from '@/common/js/app'
export default {
  components: {
    Breadcrumb,
    Hamburger
  },
  data() {
    return {
      canLaunch: true,
      canTerminate: true,
      launchLoading: false,
      terminateLoading: false,
      steps: [],
      progressVisible: false
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar'
    ])
  },
  mounted() {
    this.getState()
    this.$bus.$on('launch', (event) => {
      this.getState()
    })
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      const vm = this
      this.$confirm('确认退出登录吗?', '提示', {
        type: 'warning'
      }).then(() => {
        app.post('logOut').then(d => {
          vm.$router.push(`/login?redirect=${this.$route.fullPath}`)
        })
      }
      ).catch(() => {})
    },
    handleLaunch() {
      this.launchLoading = true
      app.post('launch').then(res => {
        if (res.code === 0) {
          const response = res.data
          if (response.result === 0) {
            this.canLaunch = false
            this.canTerminate = true
          } else if (response.result === 1) {
            this.$message({
              message: '配置没有通过校验：' + response.validateResult.outline,
              type: 'error'
            })
          } else if (response.result === 2) {
            this.$message({
              message: '启动失败',
              type: 'error'
            })
          }
          this.$bus.$emit('launch', 'launch')
        }
        this.launchLoading = false
      }).catch(() => {
        this.launchLoading = false
        this.$bus.$emit('launch', 'launch')
      })
    },
    handleTerminate() {
      this.terminateLoading = true
      app.post('terminate').then(res => {
        if (res.code === 0) {
          this.canLaunch = true
          this.canTerminate = false
          this.$bus.$emit('launch', 'terminate')
        }
        this.terminateLoading = false
      }).catch(() => {
        this.terminateLoading = false
      })
    },
    getState() {
      app.get('state').then(res => {
        if (res.code === 0) {
          console.log(res.data)
          if (res.data === 'RUNNING') {
            this.canLaunch = false
            this.canTerminate = true
          } else {
            this.canLaunch = true
            this.canTerminate = false
          }
        }
      })
    },
    queryState() {
      this.getState()
    },
    queryLaunch() {
      app.get('launch_steps').then(res => {
        if (res.code === 0) {
          this.steps = res.data.steps
          this.progressVisible = true
        }
      })
    }
  }
}

</script>

<style lang="scss" scoped>
  .navbar {
    height: 50px;
    overflow: hidden;
    position: relative;
    background: #fff;
    box-shadow: 0 1px 4px rgba(0, 21, 41, .08);

    .hamburger-container {
      line-height: 46px;
      height: 100%;
      float: left;
      cursor: pointer;
      transition: background .3s;
      -webkit-tap-highlight-color: transparent;

      &:hover {
        background: rgba(0, 0, 0, .025)
      }
    }

    .breadcrumb-container {
      float: left;
    }

    .right-menu {
      float: right;
      height: 100%;
      line-height: 50px;

      &:focus {
        outline: none;
      }

      .right-menu-item {
        display: inline-block;
        padding: 0 8px;
        height: 100%;
        font-size: 18px;
        color: #5a5e66;
        vertical-align: text-bottom;

        &.hover-effect {
          cursor: pointer;
          transition: background .3s;

          &:hover {
            background: rgba(0, 0, 0, .025)
          }
        }
      }

      .avatar-container {
        margin-right: 30px;

        .avatar-wrapper {
          margin-top: 5px;
          position: relative;

          .user-avatar {
            cursor: pointer;
            width: 40px;
            height: 40px;
            border-radius: 10px;
          }

          .el-icon-caret-bottom {
            cursor: pointer;
            position: absolute;
            right: -20px;
            top: 25px;
            font-size: 12px;
          }
        }
      }
    }
  }

</style>
