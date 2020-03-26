<template>
  <div>
    <el-row :gutter="10">
      <el-col :md="12" :sm="24" v-for="data in datas">
        <algorithm-card :algorithm="data"></algorithm-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
  import AlgorithmCard from '../AlgorithmCard'
  export default {
    components: {
      AlgorithmCard,
    },
    props: {
      datas: Array
    }
  }

</script>
