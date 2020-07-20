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
        <!-- <el-col :span="8" class="cardEnable">
          使能板卡：<el-switch v-model="myCardForm.enable" />
        </el-col> -->
      </el-form-item>
      <el-form-item label="前面板ETH2 ip:" required>
        <el-input v-model="myCardForm.ip1" style="width:50%" />
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
      valid: true
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
        enable: true,
        ip1: cardForm.ip1,
        label1: '前面板ETH2 ip'
      }
      if (this.myCardForm.ip1 === undefined) {
        this.myCardForm.ip1 = ''
      }
    }
  }
}
</script>

<style>

</style>
