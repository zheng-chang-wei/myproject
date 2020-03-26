<template>
  <section>
    <van-cell-group v-if="!readOnly">
      <van-field v-model="basicInfoForm.mode" required label="故障模式" />
      <van-field v-model="basicInfoForm.stage" required label="故障发生阶段" />
      <van-field v-model="basicInfoForm.component" required label="故障件名称" />
      <van-cell title="故障件数量">
        <template>
          <van-stepper v-model="basicInfoForm.count" min="0" integer />
        </template>
      </van-cell>
      <van-field v-model="basicInfoForm.location" required label="发生地点" />
      <van-field v-model="basicInfoForm.faultTime" required label="故障时间" clickable disabled @click="popTimePicker" />
      <van-field v-model="basicInfoForm.temporary" required label="临时处理措施" />
      <van-field v-model="basicInfoForm.description" required label="故障描述" type="textarea" rows="2" autosize placeholder="请输入故障信息、发生地点、时间" />
      <van-field v-model="basicInfoForm.reason" required label="初步原因分析" type="textarea" rows="2" autosize placeholder="请输入初步分析原因以及处理措施，是否复现" />
    </van-cell-group>
    <div v-if="!readOnly" class="button-area">
      <van-button style="width: 45%" @click="prevStep">上一步</van-button>
      <van-button type="primary" style="width: 45%;margin-left: 10px" @click="nextStep">下一步</van-button>
    </div>
    <van-cell-group v-if="readOnly" title="故障信息预览">
      <van-cell title="故障模式" :value="basicInfoForm.mode" />
      <van-cell title="故障发生阶段" :value="basicInfoForm.stage" />
      <van-cell title="故障件名称" :value="basicInfoForm.component" />
      <van-cell title="故障件数量" :value="basicInfoForm.count" />
      <van-cell title="发生地点" :value="basicInfoForm.location" />
      <van-cell title="故障时间" :value="basicInfoForm.faultTime" />
      <van-cell title="临时处理措施" :value="basicInfoForm.temporary" />
      <van-field v-model="basicInfoForm.description" label="故障描述" type="textarea" rows="2" autosize disabled />
      <van-field v-model="basicInfoForm.reason" label="初步原因分析" type="textarea" rows="2" autosize disabled />
    </van-cell-group>
    <van-popup v-model="picker.faultTime.show" position="bottom" style="width: 100%">
      <van-datetime-picker
        v-model="picker.faultTime.value"
        type="datetime"
        :formatter="formatter"
        :min-date="picker.faultTime.min"
        :max-date="picker.faultTime.max"
        @confirm="confirmFaultTime"
        @cancel="picker.faultTime.show=false"
      />
    </van-popup>
  </section>
</template>
<script>
export default {
  props: {
    basicInfoForm: {
      type: Object,
      default: null
    },
    readOnly: {
      type: Boolean,
      default: null
    }
  },
  data() {
    return {
      picker: {
        faultTime: {
          show: false,
          value: new Date(),
          min: new Date(2018, 12, 1),
          max: new Date()
        }
      }
    }
  },
  methods: {
    prevStep() {
      this.$emit('prevStep')
    },
    nextStep() {
      const validate = this.validate()
      if (validate.error) {
        this.$toast(validate.message)
      } else {
        this.$emit('nextStep')
      }
    },
    validate() {
      const param = this.basicInfoForm
      var result = {
        error: true,
        message: ''
      }
      if (!param.mode) {
        result.message = '故障模式必填'
      } else if (!param.stage) {
        result.message = '故障发生阶段必填'
      } else if (!param.component) {
        result.message = '故障件名称必填'
      } else if (!param.location) {
        result.message = '发生地点必填'
      } else if (!param.faultTime) {
        result.message = '故障时间必填'
      } else if (!param.temporary) {
        result.message = '临时措施必填'
      } else if (!param.description) {
        result.message = '故障描述必填'
      } else if (!param.reason) {
        result.message = '初步原因分析必填'
      } else {
        result.error = false
      }
      return result
    },
    popTimePicker() {
      this.picker.faultTime.show = true
    },
    formatter(type, value) {
      if (type === 'year') {
        return value + '年'
      } else if (type === 'month') {
        return value + '月'
      } else if (type === 'day') {
        return value + '日'
      } else if (type === 'hour') {
        return value + '时'
      } else if (type === 'minute') {
        return value + '分'
      }
      return value
    },
    confirmFaultTime() {
      this.basicInfoForm.faultTime = this.formatTime(this.picker.faultTime.value)
      this.picker.faultTime.show = false
    },
    formatTime(value) {
      var time = value.getFullYear() + '-'
      var month = value.getMonth() + 1
      if (month < 10) {
        time += '0'
      }
      time += month + '-'
      var day = value.getDate()
      if (day < 10) {
        time += '0'
      }
      time += day + ' '
      var hour = value.getHours()
      if (hour < 10) {
        time += '0'
      }
      time += hour + ':'
      var minute = value.getMinutes()
      if (minute < 10) {
        time += '0'
      }
      time += minute + ':00'
      return time
    }
  }
}

</script>
