<template>
  <div>
    <div v-for="item in types">
      <div class="type-title">{{item.type}}</div>
      <default-algorithms :datas="item.algorithms"></default-algorithms>
    </div>
  </div>
</template>
<script>
  import DefaultAlgorithms from '../DefaultAlgorithms/index'
  export default {
    components: {
      DefaultAlgorithms
    },
    props: {
      types: Array
    }
  }

</script>
<style scoped>
  .type-title {
    margin-bottom: 10px;
  }

</style>
