<script>
const serverIpAndPort = 'http://192.168.40.33:8082/'
const comIdFeatureNames_EN = ['periodStabilityPHM', 'lostRatePHM', 'abnomalLostPHM', 'windowTime', 'periodMean', 'periodStd', 'lostRate', 'lostMaxRate', 'lifeSignalStopRate', 'lifeSignalStopMaxRate']
const comIdFeatureNames_CN = ['周期稳定性异常', '丢帧率异常', '丢帧行为异常', '时间窗-us', '周期均值-us', '周期标准差-us', '丢帧率', '最长连续丢帧率', '生命信号停滞率', '最长连续生命信号停滞率']
const csPortFeatureNames_EN = ['linkPHM', 'linkFlashPHM', 'rxTrafficPHM', 'rxErrRatePHM', 'rxErrPredictPHM', 'txTrafficPHM', 'txErrRatePHM', 'txErrPredictPHM', 'enable', 'linkMean', 'rxMcast', 'rxTrafficMean', 'rxTrafficStd', 'rxErrRateMean', 'rxErrRateStd', 'txMcast', 'txTrafficMean', 'txTrafficStd', 'txErrRateMean', 'txErrRateStd']
const csPortFeatureNames_CN = ['网口断开', '网口闪断', '流量异常-收', '误码异常-收', '误码趋势异常-收', '流量异常-发', '误码异常-预留', '误码趋势异常-预留', '使能状态', '连接占比', '组播流量-收', '流量均值-收', '流量标准差-收', '误码率均值-收', '误码率标准差-收', '组播流量-发', '流量均值-发', '流量标准差-发', '误码率均值-预留', '误码率标准差-预留']

export default {
  serverIpAndPort, // 服务端IP
  comIdFeatureNames_EN,
  comIdFeatureNames_CN,
  csPortFeatureNames_EN,
  csPortFeatureNames_CN
}

</script>
