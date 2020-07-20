/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.video;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.imageio.ImageIO;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.algorithm.domain.VideoCache;
import com.hirain.phm.synapsis.algorithm.domain.VideoFrame;
import com.hirain.phm.synapsis.algorithm.ftp.FTPService;
import com.hirain.phm.synapsis.algorithm.ftp.FTPServicePool;
import com.hirain.phm.synapsis.algorithm.message.AlgoRawDataMessage;
import com.hirain.phm.synapsis.algorithm.service.AlgoService;
import com.hirain.phm.synapsis.algorithm.util.BeanConstants;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec May 6, 2020 3:52:51 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 6, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Service
public class VideoServiceImpl implements VideoService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AlgoService algoService;

	@Autowired
	private FTPServicePool ftpPool;

	@Value("${synapsis.video.framerate}")
	private double VIDEO_FRAMERATE;

	@Value("${synapsis.video.cache.time.range}")
	private int CACHE_TIME_RANGE;

	@Autowired
	@Qualifier(BeanConstants.VIDEOCACHEROOTPATH)
	private String videoCacheRoot;

	@Autowired
	@Qualifier(BeanConstants.ALGOCODE_UDPPORT_MAP)
	private Map<Integer, Integer> algoCode2UdpPortMap;

	@Autowired
	@Qualifier(BeanConstants.ALGOCODE_RTSPADDR_MAP)
	private Map<Integer, String> algoCode2RtspAddrMap;

	@Autowired
	@Qualifier(BeanConstants.VIDEOFRAMECACHE)
	private LinkedBlockingQueue<VideoFrame> videoFrameCache;

	private List<VideoCache> videoCaches = new ArrayList<>();

	private AtomicBoolean start = new AtomicBoolean(false);

	private AtomicBoolean isGrabbering = new AtomicBoolean(false);

	private List<FFmpegFrameGrabber> grabbers = new ArrayList<>();

	private final ExecutorService videoReceiveExecutor = Executors.newFixedThreadPool(10,
			r -> new Thread(r, VideoServiceImpl.class.getName() + "-videoReceive"));

	private final ExecutorService timestampCheckExecutor = Executors
			.newSingleThreadExecutor(r -> new Thread(r, VideoServiceImpl.class.getName() + "-timestampCheck"));

	private final ExecutorService videoStorageExecutor = Executors.newFixedThreadPool(10,
			r -> new Thread(r, VideoServiceImpl.class.getName() + "-videoStorage"));

	/**
	 * @see com.hirain.phm.synapsis.algorithm.video.VideoService#startCheck(int)
	 */
	@Override
	public void startCheck(int range) {
		try {
			timestampCheckExecutor.submit(() -> {
				while (start.get()) {
					VideoFrame frameCache = null;
					if (!videoFrameCache.isEmpty() && (frameCache = videoFrameCache.peek()) != null) {
						if (!isWithinValidity(frameCache.getTimestamp(), range)) {// 超出 {range}秒 时间范围
							videoFrameCache.poll();
						}
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						log.error(e.getMessage(), e);
					}
				}
			});
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.video.VideoService#startCacheFrame()
	 */
	@Override
	public void startCacheFrame() throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {
		start.set(true);
		avutil.av_log_set_level(avutil.AV_LOG_ERROR);// 设置JavaCV只显示error级别的log
		Iterator<Map.Entry<Integer, String>> entries = algoCode2RtspAddrMap.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<Integer, String> entry = entries.next();
			String rtspAddr = entry.getValue();
			try {
				videoReceiveExecutor.submit(() -> {
					try {
						FFmpegFrameGrabber grabber = initGrabber(rtspAddr);
						grabbers.add(grabber);
						grabber.start();
						Frame frame = null;
						while (start.get()) {
							if ((frame = grabber.grabImage()) != null) {
								isGrabbering.set(true);
								Integer algoPort = algoCode2UdpPortMap.get(entry.getKey());
								VideoFrame videoFrame = new VideoFrame(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")), frame.clone());
								videoFrameCache.offer(videoFrame);
								for (VideoCache videoCache : videoCaches) {
									videoCache.getVideoFrameCache().offer(videoFrame);
								}
								if (algoPort != null) {
									byte[] photoData = convertFrame2Bytes(frame, "jpg");// png图片比jpg图片格式大
									algoService.sendAlgoRawData(packAlgoRawData(photoData, algoPort));// 视频图片数据发送给相应的算法
									photoData = null;
									// log.info(" send frme_datas to algo-------------- " + photoData.length);
								}
							} else {
								// log.info("***********************************restart grabber");
								grabber.close();
								grabber.start();
							}
						}
						isGrabbering.set(false);
					} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
						log.error(e.getMessage(), e);
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				});
			} catch (Throwable t) {
				log.error(t.getMessage(), t);
			}

		}
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.video.VideoService#storageWarningVideo(java.lang.String, java.lang.String)
	 */
	@Override
	public void storageWarningVideo(String folderName, String videoName, long warningTime)
			throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {
		String folderPath = videoCacheRoot + folderName;
		File folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String videoPath = folder.getAbsolutePath() + File.separator + videoName;
		File video = new File(videoPath);
		video.createNewFile();
		while (true) {
			if (videoFrameCache.peek() != null) {
				break;
			}
		}
		try {
			videoStorageExecutor.submit(() -> {
				try {
					VideoCache videoCache = new VideoCache();
					videoCache.init(videoFrameCache);
					videoCache.getStart().set(true);
					videoCaches.add(videoCache);

					FrameRecorder recorder = initRecorder(videoPath, videoFrameCache.peek().getFrame());
					recorder.start();// 开启录制器
					long startTime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
					long videoTS = 0;

					while (start.get() && videoCache.getStart().get()) {
						videoTS = 1000 * (LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() - startTime);
						if (videoTS > recorder.getTimestamp()) {
							recorder.setTimestamp(videoTS);
						}
						VideoFrame videoFrame = videoCache.read();
						if (videoFrame != null && Math.abs(videoFrame.getTimestamp() - warningTime) <= CACHE_TIME_RANGE) {
							recorder.record(videoFrame.getFrame());
						}
						if (videoFrame != null && videoFrame.getTimestamp() - warningTime > CACHE_TIME_RANGE) {
							videoCache.getStart().set(false);
							videoCache.getVideoFrameCache().clear();
						}
					}
					recorder.close();
					videoCaches.remove(videoCache);
					{// 视频文件上传FTP
						FTPService ftpService = ftpPool.borrowObject();
						ftpService.connectFtpServer();
						ftpService.upload(new File(folderPath));
						ftpService.closeFTP();
						ftpPool.returnObject(ftpService);
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			});
		} catch (Throwable t) {
			log.error(t.getMessage(), t);
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.video.VideoService#stop()
	 */
	@Override
	public void stop() throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {
		start.set(false);
		while (true) {
			if (!isGrabbering.get()) {// 加此判断，保证grabber在未工作的状态下下关闭grabber
				for (FFmpegFrameGrabber grabber : grabbers) {
					grabber.close();
				}
				grabbers.clear();
				videoFrameCache.clear();
				break;
			}
		}
	}

	private FFmpegFrameGrabber initGrabber(String rtspAddr) throws Exception {
		FFmpegFrameGrabber grabber = FFmpegFrameGrabber.createDefault(rtspAddr);
		grabber.setFrameRate(VIDEO_FRAMERATE);
		grabber.setImageWidth(640);
		grabber.setImageHeight(480);
		grabber.setOption("rtsp_transport", "tcp"); // 使用tcp的方式，不然会丢包很严重
		grabber.setOption("stimoout", "5000000");// 增加超时参数
		grabber.setVideoCodec(avcodec.AV_CODEC_ID_H264);
		return grabber;
	}

	private FrameRecorder initRecorder(String videoPath, Frame frame) throws Exception {
		FrameRecorder recorder = FrameRecorder.createDefault(videoPath, frame.imageWidth, frame.imageHeight);
		recorder.setFrameRate(VIDEO_FRAMERATE);
		recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // avcodec.AV_CODEC_ID_H264，编码
		recorder.setFormat("mp4");
		// recorder.setVideoBitrate((int) ((640 * 480 * VIDEO_FRAMERATE) * 1 * 0.07));
		recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
		return recorder;
	}

	/**
	 * 把 组播接收到的算法订阅数据 加工成 发送给算法的算法原始数据
	 *
	 * @param photoData
	 * @param algoPort
	 * @return
	 */
	private AlgoRawDataMessage packAlgoRawData(byte[] photoData, int algoPort) {
		AlgoRawDataMessage rawMessage = new AlgoRawDataMessage();
		rawMessage.setUdpPort(algoPort);
		rawMessage.setPhotoData(photoData);
		return rawMessage;
	}

	/**
	 * 将收取的Frame对象转换成byte[]
	 */
	private byte[] convertFrame2Bytes(Frame frame, String formatName) {
		Java2DFrameConverter java2dFrameConverter = new Java2DFrameConverter();
		BufferedImage bufferedImage = java2dFrameConverter.convert(frame);
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, formatName, bStream);
		} catch (IOException e) {
			throw new RuntimeException("read bufImg throws Exception:" + e.getMessage(), e);
		}
		return bStream.toByteArray();
	}

	/**
	 * 判断该时戳是否在range 秒以内
	 * 
	 * @param timestamp
	 * @param range
	 */
	private boolean isWithinValidity(long timestamp, int range) {
		Long now_second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));// 获取秒数
		if (Math.abs(now_second - timestamp) <= range) {
			return true;
		}
		return false;
	}

	// 编写一个泛型方法对异常进行包装,解决Lambuda表达式中无法抛出异常的问题
	private <E extends Exception> void doThrow(Exception e) throws E {
		throw (E) e;
	}

}
