<template>
  <!-- 快速导航-->
  <div id="quicknavigation">
    <el-row style="background:#AEAEAE;border: 1px #666666 solid;border-radius: 5px;">
      <el-row class="center" style="color:white;height:30px">快速导航</el-row>
      <el-form :inline="true" :model="retrieveForm" style="background:white; ">
        <el-form-item label="城市" prop="cityName" style="margin-top:10px;">
          <el-select v-model="retrieveForm.city" placeholder="请选择" @change="selectedCityChange">
            <el-option
              v-for="item in cityOptions"
              :key="item.label"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="线路" prop="line">
          <el-select v-model="retrieveForm.line" placeholder="请选择" @change="selectedLineChange">
            <el-option
              v-for="item in lineOptions"
              :key="item.label"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="车辆" prop="car">
          <el-select v-model="retrieveForm.car" placeholder="请选择" @change="selectedCarChange">
            <el-option v-for="item in carOptions" :key="item.label" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
      </el-form>
    </el-row>
  </div>
</template>
<script>
import app from '@/common/js/app'
export default {
  props: {
  },
  data() {
    return {
      cityOptions: [],
      lineOptions: [],
      carOptions: [],
      retrieveForm: {
        city: '',
        line: '',
        car: ''
      }
    }
  },
  mounted() {
    this.getAllCitys()
    this.getCityMessage()
  },
  methods: {
    getCityMessage() {
      this.retrieveForm.city = localStorage.getItem('city')
      this.retrieveForm.line = localStorage.getItem('lineNum')
      this.retrieveForm.car = localStorage.getItem('trainNum')

      this.lineOptions = JSON.parse(localStorage.getItem('lineOptions'))
      this.carOptions = JSON.parse(localStorage.getItem('carOptions'))
    },
    getAllCitys() {
      var vm = this
      vm.cityOptions = []
      app.get('get_city_name').then(d => {
        for (var i = 0; i < d.msg.length; i++) {
          vm.$set(vm.cityOptions, i, {
            value: d.msg[i].cityName,
            label: d.msg[i].cityName
          })
        }
      })
    },
    selectedCityChange() {
      var vm = this
      const parm = {
        cityName: this.retrieveForm.city
      }
      this.lineOptions = []
      this.retrieveForm.line = ''
      this.carOptions = []
      this.retrieveForm.car = ''
      app.get('get_lines_by_cityName', parm).then(d => {
        for (var i = 0; i < d.msg.length; i++) {
          vm.$set(vm.lineOptions, i, {
            value: d.msg[i].lineName,
            label: d.msg[i].lineName
          })
        }
        localStorage.setItem('lineOptions', JSON.stringify(vm.lineOptions))
      })

      this.$emit('cityChange', this.retrieveForm.city)
    },
    selectedLineChange() {
      var vm = this
      const parm = {
        cityName: this.retrieveForm.city,
        lineName: this.retrieveForm.line
      }
      this.carOptions = []
      this.retrieveForm.car = ''
      app.get('get_train_by_trainParam', parm).then(d => {
        for (var i = 0; i < d.msg.length; i++) {
          vm.$set(vm.carOptions, i, {
            value: d.msg[i].trainNo,
            label: d.msg[i].trainNo
          })
        }
        localStorage.setItem('carOptions', JSON.stringify(vm.carOptions))
      })
      this.$emit('lineChange', this.retrieveForm.line)
    },
    selectedCarChange() {
      this.$emit('carChange', this.retrieveForm.car)
    }
  }
}
</script>

<style >
#quicknavigation .el-form-item{
  margin-left: 30px;
}
 .red {
  border-width: 1px;
  border-style: solid;
  border-color: red;
}
</style>
