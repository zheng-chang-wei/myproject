<template>
  <div>
    <div :class="[ledStatus[0],'led1']" />
    <div :class="[ledStatus[1],'led2']" />
    <div :class="[ledStatus[2],'led3']" />
    <div :class="[ledStatus[3],'led4']" />
  </div>
</template>

<script>
export default {
  name: 'LedStatus',
  data() {
    return {
      ledStatus: ['NULL', 'NULL', 'NULL', 'NULL']
    }
  },
  methods: {
    setLedStatus: function(arrStatus) {
      this.ledStatus = arrStatus
    }
  }
}
</script>

<style scoped>
  .led1 {
    width: 4px;
    height: 4px;
    border: 1px solid darkgray;
    border-radius: 2px;
    display: inline-block;
    margin-left: 1px;
  }

  .led2 {
    width: 4px;
    height: 4px;
    border: 1px solid darkgray;
    border-radius: 2px;
    display: inline-block;
    margin-left: -2px;
  }

  .led3 {
    width: 4px;
    height: 4px;
    border: 1px solid darkgray;
    border-radius: 2px;
    display: inline-block;
    margin-left: -2px;
  }

  .led4 {
    width: 4px;
    height: 4px;
    border: 1px solid darkgray;
    border-radius: 2px;
    display: inline-block;
    margin-left: -2px;
  }

  @keyframes fade {
    from {
      opacity: 1.0;
    }
    50% {
      opacity: 0.4;
    }
    to {
      opacity: 1.0;
    }
  }

  @-webkit-keyframes fade {
    from {
      opacity: 1.0;
    }
    50% {
      opacity: 0.4;
    }
    to {
      opacity: 1.0;
    }
  }

  .NULL {
    display: none;
  }

  .GREEN_ON {
    background-color: green;
  }

  .GREEN_FLASHING {
    background-color: green;
    animation: fade 600ms infinite;
    -webkit-animation: fade 600ms infinite;
  }

  .ORANGE_ON {
    background-color: orange;
  }

  .ORANGE_FLASHING {
    background-color: orange;
    animation: fade 600ms infinite;
    -webkit-animation: fade 600ms infinite;
  }

  .RED_ON {
    background-color: red;
  }

  .RED_FLASHING {
    background-color: red;
    animation: fade 600ms infinite;
    -webkit-animation: fade 600ms infinite;
  }
</style>
