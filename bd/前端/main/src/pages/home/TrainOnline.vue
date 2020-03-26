<template>
  <el-row class="panel-group">
    <div class="card-panel card-train" :style="'height:'+trainOnlineHeight">
      <div class="card-panel-icon-wrapper icon-train">
        <svg-icon icon-class="train-card" class-name="card-panel-icon" />
      </div>
      <div class="card-panel-description">
        <div class="card-panel-text icon-train">
          在线车辆数目
        </div>
        <div class="card-panel-num icon-train">{{ train.online }}/{{ train.total }}</div>
      </div>
    </div>
    <div class="card-panel card-door" :style="'height:'+trainOnlineHeight">
      <div class="card-panel-icon-wrapper icon-door">
        <svg-icon icon-class="door-card" class-name="card-panel-icon" />
      </div>
      <div class="card-panel-description">
        <div class="card-panel-text icon-door">
          在线车门数目
        </div>
        <div class="card-panel-num icon-door">{{ train.onlineDoor }}</div>
      </div>
    </div>
  </el-row>
</template>
<script>
export default {
  props: {
    train: {
      type: Object,
      default: null
    },
    trainOnlineHeight: {
      type: String,
      default: null
    }
  }
}

</script>
<style lang="scss" scoped>
  $train-color:rgb(0, 153, 153);
  $door-color:rgb(102, 102, 255);

  .panel-group {
    .card-panel {
      font-size: 12px;
      position: relative;
      overflow: hidden;
      background: rgb(67, 81, 107);
      box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
      border-color: rgba(0, 0, 0, .05);

      .icon-train {
        color: $train-color;
      }

      .icon-door {
        color: $door-color;
      }

      .card-panel-icon-wrapper {
        float: left;
        margin: 7px 0 0 5px;
        padding: 10px;
        transition: all 0.38s ease-out;
        border-radius: 6px;
      }

      .card-panel-icon {
        float: left;
        font-size: 48px;
      }

      .card-panel-description {
        font-weight: bold;
        margin: 20px;
        text-align: center;

        .card-panel-text {
          line-height: 18px;
          font-size: 16px;
          margin-bottom: 12px;
        }

        .card-panel-num {
          font-size: 20px;
        }
      }
    }

    .card-train {
      border-top: 3px solid $train-color;
    }

    .card-door {
      border-top: 3px solid $door-color;
    }

  }

  @media (max-width:550px) {
    .card-panel-description {
      display: none;
    }

    .card-panel-icon-wrapper {
      float: none !important;
      width: 100%;
      height: 100%;
      margin: 0 !important;

      .svg-icon {
        display: block;
        margin: 14px auto !important;
        float: none !important;
      }
    }
  }

</style>
