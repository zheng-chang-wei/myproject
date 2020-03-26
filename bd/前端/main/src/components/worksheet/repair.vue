<template>
  <section id="repair">
    <van-cell-group>
      <van-field v-model="suggestion.treatment" label="处理建议" type="textarea" rows="2" autosize />
      <van-field v-model="suggestion.repair" label="检修建议" type="textarea" rows="5" autosize />
    </van-cell-group>
  </section>
</template>
<script>
export default {
  props: {
    suggestion: {
      type: Object,
      default: null
    }
  }
}

</script>
<style>
  #repair {
    margin-top: 5px;
  }

</style>
