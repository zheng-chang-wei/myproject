<template>
  <div class="component">
    <el-row>
      <el-col :span="3">
        <el-row class="basicInfoStyle">车厢：{{ carNo }}</el-row>
        <el-row class="basicInfoStyle">门地址：{{ doorAddr }}</el-row>
      </el-col>
      <el-col :span="20">
        <train-graph v-if="carriage.carriageDatas.length>0" :carriages="carriage.carriageDatas" :carriage-span="carriage.carriageSpan" @doorChecked="doorChecked" />
      </el-col></el-row>
    <el-row style="margin-top:10px">
      <el-col :span="24">
        <el-table border :data="lifeStatetable" style="width:100%">
          <el-table-column prop="itemName" label="部件名称" width="180" />
          <el-table-column prop="lifeValue" label="实际值" />
          <el-table-column prop="referenceValue" label="参考值" />
          <!--百分比列-->
          <el-table-column prop="lifePercentage" label="百分比">
            <template slot-scope="scope">
              <el-progress :text-inside="true" :stroke-width="15" :percentage="scope.row.lifePercentage" :color="customColorMethod(scope.row.lifePercentage)" />
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>
<script>
// import carriages from '@/components/realtimemonitor/carriages.vue'
import trainGraph from '@/components/TrainGraph'
export default {
  components: {
    // carriages,
    trainGraph
  },
  props: {
    carriage: {
      type: Object,
      default() {
        return {
          carriageSpan: 3,
          carriageDatas: []
        }
      }
    },
    lifeStatetable: {
      type: Array,
      default() {
        []
      }
    }
  },
  data() {
    return {
      doorAddr: '',
      carNo: ''
    }
  },
  methods: {
    doorChecked(carriage, doorIndex) {
      this.doorAddr = carriage.doors[doorIndex - 1].addr
      this.carNo = carriage.name
      this.$emit('doorChecked', this.carNo, this.doorAddr)
    },
    customColorMethod(percentage) {
      this.$emit('customColorMethod', percentage)
    }
  }
}
</script>
<style scoped>
.component .basicInfoStyle {
		margin-top: 20px;
		margin-left: 30px;
		color: white;
	}
</style>

