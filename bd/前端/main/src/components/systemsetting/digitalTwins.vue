<template>
  <section id="digital" class="app-container">
    <!--查询-->
    <el-row style="margin-top:16px">
      <el-col :span="5">
        <el-form :inline="true" size="mini">
          <el-form-item label="车厢">
            <el-input v-model="carriage" disabled style="width:50px" />
          </el-form-item>
          <el-form-item label="门">
            <el-input v-model="door" disabled style="width:50px" />
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="16">
        <trainSelect single @query="getTrains" />
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="18" :offset="3">
        <trainGraph v-if="carriages.length>0" :carriages="carriages" :carriage-span="carriageSpan" @doorChecked="doorChecked" />
      </el-col>
    </el-row>
    <el-row style="margin-top: 10px">
      <el-radio-group v-model="dateRange" :disabled="btnDisabled" size="medium" @change="getLastResults">
        <el-radio-button label="最近一个月" />
        <el-radio-button label="最近三个月" />
        <el-radio-button label="最近半年" />
        <el-radio-button label="最近一年" />
      </el-radio-group>
    </el-row>
    <el-row v-loading="loading">
      <el-col v-for="(line,index) in lineDatas" :key="index" :span="11" :offset="index%2">
        <linePart :line-data="line" :colors="colors[(index % colors.length)]" />
      </el-col>
    </el-row>
  </section>
</template>
<script>
const rangeMap = new Map()
rangeMap.set('最近一个月', 1)
rangeMap.set('最近三个月', 3)
rangeMap.set('最近半年', 6)
rangeMap.set('最近一年', 12)
import trainSelect from './trainSelect'
import trainGraph from '../TrainGraph'
import app from '@/common/js/app'
import linePart from './LinePart'
export default {
  components: {
    trainSelect,
    trainGraph,
    linePart
  },
  data() {
    return {
      carriages: [],
      carriageSpan: 3,
      carriage: 1,
      door: 1,
      dateRange: '最近一个月',
      lastMonth: 1,
      colors: [
        // ['#c23531'],
        // ['#2f4554'],
        // ['#d48265'],
        // ['#91c7ae'],
        // ['#749f83'],
        // ['#ca8622'],
        // ['#bda29a'],
        // ['#6e7074'],
        // ['#546570'],
        // ['#c4ccd3'],
        ['#2ec7c9'],
        ['#b6a2de'],
        ['#5ab1ef'],
        ['#ffb980'],
        ['#d87a80'],
        ['#8d98b3'],
        ['#e5cf0d']
      ],
      lineDatas: [],
      loading: false,
      trainInfo: {}
    }
  },
  computed: {
    btnDisabled() {
      return this.carriages.length === 0
    }
  },
  methods: {
    getTrains(formObject) {
      this.lineDatas = []
      if (formObject.projectName && formObject.trainNo) {
        const param = {
          project: formObject.projectName,
          trainNo: formObject.trainNo
        }
        this.trainInfo = formObject
        app.get('get_train_config_info', param).then(data => {
          if (data.msg) {
            const train = JSON.parse(data.msg)
            if (train != null) {
              const cars = train.cars
              // 删除前两节车厢，车头和车尾
              this.carriages = cars
              // 如果车箱数超过10节，一节车厢占2列
              if (cars.length > 10) {
                this.carriageSpan = 2
              } else {
                this.carriageSpan = 3
              }
            }
            this.getParamResults()
          }
        })
      } else {
        this.$message({
          message: '请选择一辆车（且只能选择一辆）',
          type: 'error'
        })
      }
    },
    doorChecked(carriage, doorIndex) {
      this.lineDatas = []
      this.carriage = carriage.name
      this.door = doorIndex
      this.getParamResults()
    },
    getLastResults() {
      this.lastMonth = rangeMap.get(this.dateRange)
      this.getParamResults()
    },
    getParamResults() {
      this.loading = true
      const request = {
        project: this.trainInfo.projectName,
        train: this.trainInfo.trainNo,
        carId: this.carriage,
        doorId: this.door,
        lastMonth: this.lastMonth
      }
      var vm = this
      app.get('digital_twin_all', request).then(res => {
        if (res.data) {
          vm.lineDatas = res.data.results
          vm.loading = false
        }
      })
    }
  }
}

</script>
<style>
</style>
