<template>
  <div>
    <el-form ref="myCardForm" v-model="myCardForm" label-position="right" label-width="120px">
      <el-form-item label="槽位:">
        <el-col :span="2">
          {{ myCardForm.slotId }}
        </el-col>
        <el-col :span="6">
          {{ myCardForm.type }}
        </el-col>
        <el-col :span="8" class="cardEnable">
          使能板卡：<el-switch v-model="myCardForm.enable" @change="switchChange" />
        </el-col>
      </el-form-item>
      <el-form-item label="前面板ETH 1 ip:" required>
        <el-input v-model="myCardForm.ip1" style="width:50%" :disabled="isDisabled" />
      </el-form-item>
      <el-form-item label="前面板ETH 2 ip:" required>
        <el-input v-model="myCardForm.ip2" style="width:50%" :disabled="isDisabled" />
      </el-form-item>
      <el-form-item label="目标 ip 1:" required>
        <el-input v-model="myCardForm.ip3" style="width:50%" :disabled="isDisabled" />
      </el-form-item>
      <el-form-item label="目标 ip 2:" required>
        <el-input v-model="myCardForm.ip4" style="width:50%" :disabled="isDisabled" />
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  props: {
    index: {
      type: Number,
      default: null
    },
    cardForm: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      myCardForm: null,
      isDisabled: true
    }
  },
  created() {
    this.setCardForm(this.cardForm)
  },
  methods: {
    setCardForm(cardForm) {
      this.myCardForm = {
        slotId: cardForm.slotId,
        type: cardForm.type,
        enable: cardForm.enable,
        ip1: cardForm.ip1,
        ip2: cardForm.ip2,
        ip3: cardForm.ip3,
        ip4: cardForm.ip4,
        label1: '前面板ETH1 ip',
        label2: '前面板ETH2 ip',
        label3: '目标ip1',
        label4: '目标ip2'
      }
      this.isDisabled = !this.myCardForm.enable
      for (let index = 1; index <= 4; index++) {
        if (this.myCardForm['ip' + index] === undefined) {
          this.myCardForm['ip' + index] = ''
        }
      }
    },
    switchChange(value) {
      this.isDisabled = !value
    }
  }
}
</script>

<style>

</style>
