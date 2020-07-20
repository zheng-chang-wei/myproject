/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.multicast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.algorithm.converter.RawDataConverter;
import com.hirain.phm.synapsis.algorithm.converter.StorageDataConverter;
import com.hirain.phm.synapsis.algorithm.domain.ADRawData;
import com.hirain.phm.synapsis.algorithm.domain.AlgoRawData;
import com.hirain.phm.synapsis.algorithm.domain.BusRawData;
import com.hirain.phm.synapsis.algorithm.domain.StorageData;
import com.hirain.phm.synapsis.algorithm.message.AlgoRawDataMessage;
import com.hirain.phm.synapsis.algorithm.message.MulticastAlgoMessage;
import com.hirain.phm.synapsis.algorithm.service.AlgoService;
import com.hirain.phm.synapsis.algorithm.storage.CacheService;
import com.hirain.phm.synapsis.algorithm.util.BeanConstants;
import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.Variable.VariableType;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 23, 2019 4:22:24 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 23, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Service
public class MulticastReceiverImpl implements MulticastReceiver {

	@Autowired
	private AlgoService algoService;

	@Autowired
	private CacheService storageService;

	@Autowired
	@Qualifier(BeanConstants.BOARDSETTING)
	private BoardSetting boardSetting;

	@Autowired
	@Qualifier(BeanConstants.MULTICAST_IPPORT_MAP)
	private Map<String, Integer> multicastIpPortMap;

	@Autowired
	@Qualifier(BeanConstants.MULTICAST_IPTYPE_MAP)
	private Map<String, VariableType> multicastIpVariableTypeMap;

	@Autowired
	@Qualifier(BeanConstants.MULTICAST_IPALOGPORT_MAP)
	private Map<String, Integer> multicastIpAlgoPortMap;

	@Autowired
	@Qualifier(BeanConstants.MULTICAST_SOCKET_MAP)
	private Map<MulticastSocket, InetAddress> multicastSocketMap;

	@Autowired
	private Map<String, RawDataConverter> converters;

	@Autowired
	private StorageDataConverter storageConverter;

	private final ExecutorService dataReceiveExecutor = Executors.newFixedThreadPool(5,
			r -> new Thread(r, MulticastReceiverImpl.class.getName() + "-datareceive"));

	private AtomicBoolean start = new AtomicBoolean(false);

	/**
	 * @see com.hirain.phm.synapsis.algorithm.multicast.MulticastReceiver#subscribe()
	 */
	@Override
	public void subscribe() throws Exception {
		start.set(true);
		Iterator<Map.Entry<String, Integer>> entries = multicastIpPortMap.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String, Integer> entry = entries.next();
			String multicastIp = entry.getKey();
			Integer multicastPort = entry.getValue();

			// 设置组播组地址
			InetAddress group = InetAddress.getByName(multicastIp);
			// 创建组播套接字
			MulticastSocket msr = new MulticastSocket(multicastPort);
			NetworkInterface ni = NetworkInterface.getByName(boardSetting.getType().equals(BoardType.PHM_IMX8.name()) ? "eth1" : "eth2");
			msr.setNetworkInterface(ni);
			// 加入连接
			msr.joinGroup(group);
			multicastSocketMap.put(msr, group);
			byte[] buffer = new byte[1024];
			// 建立一个指定缓冲区大小的数据包
			DatagramPacket dp = new DatagramPacket(buffer, buffer.length);

			dataReceiveExecutor.submit(() -> {
				while (start.get()) {
					try {
						msr.receive(dp);
						Integer algoPort = multicastIpAlgoPortMap.get(multicastIp);
						VariableType variableType = multicastIpVariableTypeMap.get(multicastIp);
						byte[] payload = decode(dp.getData()).getData();
						if (algoPort != null) {
							algoService.sendAlgoRawData(packAlgoRawData(payload, algoPort, variableType));// 数据发送给相应的算法
						}
						storageService.cache(packStorageData(payload, variableType));
					} catch (Exception e) {
						doThrow(e);
					}
				}
			});
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.multicast.MulticastReceiver#stop()
	 */
	@Override
	public void stop() throws Exception {
		start.set(false);
		Iterator<Map.Entry<MulticastSocket, InetAddress>> entries = multicastSocketMap.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<MulticastSocket, InetAddress> entry = entries.next();
			if (entry.getKey() != null) {
				entry.getKey().leaveGroup(entry.getValue());
				// entry.getKey().close();
			}
		}
		multicastSocketMap.clear();
	}

	@Override
	public MulticastAlgoMessage decode(byte[] multicastDatas) {
		ByteBuffer buffer = ByteBuffer.wrap(multicastDatas).order(MessageConstant.MESSAGE_ORDER);
		MulticastAlgoMessage message = new MulticastAlgoMessage();
		message.setTarget(buffer.get());
		message.setSource(buffer.get());
		message.setFrameCommd(buffer.getShort());
		message.setCounter(buffer.getInt());
		int len = buffer.getInt();
		message.setLength(len);
		byte[] data = new byte[len];
		buffer.get(data);
		message.setData(data);
		return message;
	}

	/**
	 * 把 组播接收到的算法订阅数据 加工成 发送给算法的算法原始数据
	 *
	 * @param datas
	 * @param algoPort
	 * @param type
	 * @return
	 */
	private AlgoRawDataMessage packAlgoRawData(byte[] datas, int algoPort, VariableType type) {
		AlgoRawDataMessage rawMessage = new AlgoRawDataMessage();
		rawMessage.setUdpPort(algoPort);
		AlgoRawData rawData = converters.get(type.toString()).convert(datas);
		if (rawData instanceof ADRawData) {
			rawMessage.setAdData((ADRawData) rawData);
		} else if (rawData instanceof BusRawData) {
			rawMessage.setBusData((BusRawData) rawData);
		}
		return rawMessage;
	}

	/**
	 * 把 组播接收到的算法订阅数据 加工成 用于报文存储的存储数据
	 *
	 * @param datas
	 * @param type
	 * @return
	 */
	private StorageData packStorageData(byte[] datas, VariableType type) {
		StorageData storageData = storageConverter.converter(datas, type);
		return storageData;
	}

	// 编写一个泛型方法对异常进行包装,解决Lambuda表达式中无法抛出异常的问题
	private <E extends Exception> void doThrow(Exception e) throws E {
		throw (E) e;
	}

}
