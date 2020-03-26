<template>
  <div>
    <div v-for="item in types">
      <div class="type-title">{{item.type}}</div>
      <default-boards :boards="item.boards"></default-boards>
    </div>
  </div>
</template>
<script>
  import DefaultBoards from '../DefaultBoards/index'
  export default {
    components: {
      DefaultBoards
    },
    props: {
      types: Array,
    }
  }

</script>
<style scoped>
  .type-title {
    margin-bottom: 10px;
  }

</style>
