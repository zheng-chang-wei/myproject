<script>
const serverIpAndPort = 'http://192.168.40.33:8082/'
const comIdFeatureNames = ['periodStabilityPHM', 'lostRatePHM', 'abnomalLostPHM', 'windowTime', 'periodMean', 'periodStd', 'lostRate', 'lostMaxRate', 'lifeSignalStopRate', 'lifeSignalStopMaxRate']
const csPortFeatureNames = ['linkPHM', 'linkFlashPHM', 'rxTrafficPHM', 'rxErrRatePHM', 'rxErrPredictPHM', 'txTrafficPHM', 'txErrRatePHM', 'txErrPredictPHM', 'enable', 'linkMean', 'rxMcast', 'rxTrafficMean', 'rxTrafficStd', 'rxErrRateMean', 'rxErrRateStd', 'txMcast', 'txTrafficMean', 'txTrafficStd', 'txErrRateMean', 'txErrRateStd']

export default {
  serverIpAndPort, // 服务端IP
  comIdFeatureNames,
  csPortFeatureNames
}

</script>
