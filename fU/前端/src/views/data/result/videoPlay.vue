<template>
  <div>
    <div style="text-align: center;line-height: 40px;">
      IP:{{ ip }}
    </div>
    <video-player
      ref="videoPlayer"
      class="video-player vjs-custom-skin"
      :playsinline="true"
      :options="playerOptions"
    />
  </div>
</template>

<script>
export default {
  name: 'VideoPlay',
  data() {
    return {
      ip: '127.0.0.1',
      playerOptions: {
        playbackRates: [0.7, 1.0, 1.5, 2.0],
        autoplay: false,
        muted: false,
        loop: false,
        preload: 'auto',
        language: 'zh-CN',
        aspectRatio: '16:9',
        fluid: true,
        sources: [{
          type: '',
          src: 'http://localhost/video/111.mp4'
        }],
        poster: '../../static/images/test.jpg',
        width: document.documentElement.clientWidth - 200,
        notSupportedMessage: '此视频暂无法播放，请稍后再试',
        controlBar: {
          timeDivider: true,
          durationDisplay: true,
          remainingTimeDisplay: false,
          fullscreenToggle: true
        }
      }
    }
  },
  methods: {
    showVideo(uri, ip) {
      this.ip = ip
      const myPlayer = this.$refs.videoPlayer.player
      myPlayer.src(uri)
    }
  }
}
</script>

<style scoped lang="scss">
</style>
