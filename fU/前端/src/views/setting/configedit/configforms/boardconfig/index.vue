<template>
  <div>
    <div>
      <SSD v-if="cardForm.type==='SSD'" />
      <CPU v-if="cardForm.type==='CPU'" ref="myCardForm" :card-form="cardForm" />
      <phmCard1 v-else-if="cardForm.type==='PHM_AGX'" ref="myCardForm" :card-form="cardForm" />
      <phmCard2 v-else-if="cardForm.type==='PHM_IMX8'||cardForm.type==='PHM_TX2'" ref="myCardForm" :card-form="cardForm" />
      <ecnCard v-else-if="cardForm.type==='ECN'" ref="myCardForm" :card-form="cardForm" @uploadSuccess="uploadSuccess" />
      <mvbCard v-else-if="cardForm.type==='MVB'" ref="myCardForm" :card-form="cardForm" @uploadSuccess="uploadSuccess" />
      <empty v-else-if="cardForm.type==='NO'" />
    </div>
    <div style="position: absolute; bottom: 10px;right:20px">
      <!-- <el-button style="width: 170px" @click="cancel">取消</el-button> -->
      <el-button style="width: 170px" type="primary" @click="save">保存</el-button>
      <el-button style="width: 170px" type="primary" @click="previousStep">上一步</el-button>
      <el-button style="width: 170px" type="primary" @click="nextStep">下一步</el-button>
    </div>
  </div>
</template>

<script>
import mvbCard from './components/mvbCard'
import ecnCard from './components/ecnCard'
import phmCard1 from './components/phmCard1'
import phmCard2 from './components/phmCard2'
import SSD from './components/SSD'
import CPU from './components/CPU'
import empty from './components/empty'
import app from '@/common/js/app'
export default {
  components: {
    mvbCard,
    ecnCard,
    phmCard1,
    phmCard2,
    SSD,
    CPU,
    empty
  },
  props: {
    boardSettings: {
      type: Array,
      default: null
    }
  },
  data() {
    return {
      cardForm: {
        slotId: null
      }
    }
  },
  mounted() {
    const vm = this
    this.$bus.$on('cardChange', (data) => {
      vm.myCardChange(data)
    })
  },

  destroyed() {
    this.$root.Bus.$off('cardChange')
  },
  methods: {
    uploadSuccess(type) {
      this.$emit('uploadSuccess', type)
    },
    myCardChange(data) {
      data.enable = false
      if (data.type === 'NO' || data.type === 'SSD') {
        this.cardForm = data
        return
      }
      this.setBoardSettings()
      this.cardForm = data
      if (this.boardSettings.length !== 0) {
        const index = this.getBoardSettingBySoltId(data.slotId)
        if (index !== null) {
          this.cardForm = this.boardSettings[index]
        }
      }
      if (this.$refs.myCardForm && this.$refs.myCardForm !== null) {
        this.$refs.myCardForm.setCardForm(this.cardForm)
      }
    },
    // 保存上此编辑的内容
    setBoardSettings() {
      if (this.$refs.myCardForm && this.$refs.myCardForm !== null) {
        this.cardForm = this.$refs.myCardForm.myCardForm
      }
      if (this.cardForm.slotId && this.cardForm.slotId !== null && this.cardForm.type !== 'NO' && this.cardForm.type !== 'SSD') {
        const index = this.getBoardSettingBySoltId(this.cardForm.slotId)
        if (index !== null) {
          this.$set(this.boardSettings, index, this.cardForm)
        } else {
          this.boardSettings.push(this.cardForm)
        }
      }
    },
    getBoardSettingBySoltId(slotId) {
      for (let index = 0; index < this.boardSettings.length; index++) {
        const boardSetting = this.boardSettings[index]
        if (slotId === boardSetting.slotId) {
          return index
        }
      }
      return null
    },

    previousStep() {
      this.$emit('previousStep')
    },
    nextStep() {
      const parm = this.getParm()
      if (parm.code !== 0) {
        this.$message({
          dangerouslyUseHTMLString: true,
          message: parm.data,
          type: 'error'
        })
        return
      }
      app.postData('save_board', parm.data).then(data => {
        if (data.code === 0) {
          if (data.data === null) {
            this.$emit('toAlgorithmConfig')
          } else {
            this.$message({
              message: data.data.errors,
              type: 'error'
            })
          }
        }
      })
    },
    save() {
      const parm = this.getParm()
      if (parm.code !== 0) {
        this.$message({
          dangerouslyUseHTMLString: true,
          message: parm.data,
          type: 'error'
        })
        return
      }
      app.postData('save_board', parm.data).then(data => {
        if (data.code === 0) {
          if (data.data === null) {
            this.$message({
              message: '保存成功',
              type: 'success'
            })
          } else {
            this.$message({
              message: data.data.errors,
              type: 'error'
            })
          }
        }
      })
    },
    getParm() {
      this.setBoardSettings()
      const boardVOList = []
      const errors = []

      if (this.boardSettings.length === 0) {
        errors.push('所有板卡都未配置')
      } else {
        this.verifyBoard()
        this.boardSettings.forEach(boardSetting => {
          const ips = this.setIP(boardSetting)
          // 板卡使能校验输入，不使能不校验输入内容
          if (boardSetting.enable) {
            let error = ''
            error = this.verifyFileName(error, boardSetting)
            if (error !== '') {
              error += '</br></br>'
            }
            error += this.verifyTrustLine(boardSetting)
            error += this.verifyMode(boardSetting)
            error += this.verifyIP(boardSetting, ips)
            if (error !== '') {
              errors.push(error)
            }
          }
          boardVOList.push({
            enable: boardSetting.enable,
            ips: ips,
            slotId: boardSetting.slotId,
            type: boardSetting.type,
            filename: boardSetting.filename,
            originalName: boardSetting.originalName,
            trustLine: boardSetting.trustLine,
            mode: boardSetting.mode
          })
        })
      }

      const parm = {
        settingId: localStorage.getItem('settingId'),
        boardVOList: boardVOList
      }

      if (errors.length === 0) {
        return {
          code: 0,
          data: parm
        }
      } else {
        let msg = ''
        for (let index = 0; index < errors.length; index++) {
          const element = errors[index]
          if (element !== '' && index !== 0) {
            msg += '</br></br>'
          }
          msg += element
        }
        return {
          code: 1,
          data: msg
        }
      }
    },
    setIP(boardSetting) {
      const ips = []
      if (boardSetting.ip1 !== undefined && boardSetting.ip1 !== null) {
        ips.push(boardSetting.ip1)
      }
      if (boardSetting.ip2 !== undefined && boardSetting.ip2 !== null) {
        ips.push(boardSetting.ip2)
      }
      if (boardSetting.ip3 !== undefined && boardSetting.ip3 !== null) {
        ips.push(boardSetting.ip3)
      }
      if (boardSetting.ip4 !== undefined && boardSetting.ip4 !== null) {
        ips.push(boardSetting.ip4)
      }
      return ips
    },
    verifyBoard() {
      let warnings = ''
      const boards = JSON.parse(localStorage.getItem('boards'))
      boards.forEach(element => {
        if (!this.isExist(element)) {
          if (warnings !== '') {
            warnings += '</br></br>'
          }
          warnings += element.boardType + '板卡：未配置'
        }
      })
      if (warnings !== '') {
        this.$message({
          dangerouslyUseHTMLString: true,
          message: warnings,
          type: 'warning'
        })
      }
    },
    isExist(board) {
      if (board.boardType === 'SSD') {
        return true
      }
      for (let index = 0; index < this.boardSettings.length; index++) {
        const boardSetting = this.boardSettings[index]
        if (board.boardType === boardSetting.type) {
          return true
        }
      }
      return false
    },
    verifyIP(boardSetting, ips) {
      let errors = ''
      for (let index = 0; index < ips.length; index++) {
        const ip = ips[index]
        if (ip.replace(/\s+/g, '') === '') {
          if (errors !== '') {
            errors += '</br></br>'
          }
          errors += boardSetting.type + '板卡：' + boardSetting['label' + (index + 1)] + '不能为空'
        } else if (!this.isValidIP(ip)) {
          if (errors !== '') {
            errors += '</br></br>'
          }
          errors += boardSetting.type + '板卡：' + boardSetting['label' + (index + 1)] + '不合法'
        }
      }
      return errors
    },
    verifyFileName(errors, boardSetting) {
      const br = '</br></br>'
      let filenameError = ''
      if (boardSetting.type === 'MVB' || boardSetting.type === 'ECN') {
        if (boardSetting.filename.replace(/\s+/g, '') === '') {
          filenameError += boardSetting.type + '板卡：' + boardSetting.filenameLabel + '不能为空'
        }
      }
      if (errors !== '') {
        if (filenameError !== '') {
          errors += br + filenameError
        }
      } else {
        errors = filenameError
      }
      return errors
    },
    cancel() {
      this.$emit('cancel')
    },
    isValidIP(ip) {
      var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
      return reg.test(ip)
    },
    verifyTrustLine(boardSetting) {
      const br = '</br></br>'
      let trustLineError = ''
      if (boardSetting.type === 'MVB') {
        if (boardSetting.trustLine === '' || boardSetting.trustLine === null) {
          trustLineError += boardSetting.type + '板卡：' + '信任线不能为空'
        }
      }
      if (trustLineError !== '') {
        trustLineError += br
      }
      return trustLineError
    },
    verifyMode(boardSetting) {
      const br = '</br></br>'
      let modeError = ''
      if (boardSetting.type === 'MVB') {
        if (boardSetting.mode === '') {
          modeError += boardSetting.type + '板卡：' + '板卡模式不能为空'
        }
        if (modeError !== '') {
          modeError += br
        }
      }
      return modeError
    }
  }
}
</script>

<style>
.cardEnable{
  font-size: 14px;
  color: #606266;
  font-weight: 700;
}
</style>
