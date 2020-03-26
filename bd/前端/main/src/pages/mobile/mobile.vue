<template>
  <div id="mobile">
    <router-view />
  </div>
</template>
<script>
export default {
  name: 'Mobile'
}

</script>
<style>
  #mobile {
    color: #2c3e50;
  }

  body {
    overflow-x: hidden;
    /* max-width: 700px; */
    margin: 0 auto;
    font-size: 14;
    background-color: #f8f8f8;
  }

</style>
