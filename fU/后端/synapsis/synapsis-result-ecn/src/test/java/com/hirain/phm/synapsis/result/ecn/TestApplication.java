package com.hirain.phm.synapsis.result.ecn;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hirain.phm.synapsis.result.domain.AlgorithmHeader;
import com.hirain.phm.synapsis.result.domain.AlgorithmResult;
import com.hirain.phm.synapsis.result.domain.CommonHeader;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 2:51:46 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootApplication(scanBasePackages = "com.hirain.phm.synapsis")
@MapperScan(basePackages = "com.hirain.phm.synapsis.*.*.dao")
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	/**
	 * @return
	 */
	public static AlgorithmResult create() {
		AlgorithmResult result = new AlgorithmResult();
		result.setName("结果");
		result.setAlgorithm("算法1");
		result.setSubsystem("受电弓");
		result.setCode(1);
		result.setFileType(1);
		result.setTimestamp(new Date());
		result.setValue(1);
		result.setHeader(TestApplication.createHeader());
		result.setAlgorithmHeader(TestApplication.createAlgorithmHeader());
		return result;
	}

	/**
	 * @return
	 */
	public static AlgorithmHeader createAlgorithmHeader() {
		AlgorithmHeader algorithmHeader = new AlgorithmHeader();
		algorithmHeader.setHeaderType((short) 1);
		algorithmHeader.setAlgorithmId(1);
		algorithmHeader.setSupplier(1);
		algorithmHeader.setVersion("V1.0");
		algorithmHeader.setLifeSignal(1);
		algorithmHeader.setCarriage(1);
		algorithmHeader.setEnd(1);
		algorithmHeader.setDeviceSeq(1);
		algorithmHeader.setSubsystem(1);
		algorithmHeader.setCrc(1);
		return algorithmHeader;
	}

	/**
	 * @return
	 */
	public static CommonHeader createHeader() {
		CommonHeader header = new CommonHeader();
		header.setSystemTime(new Date());
		header.setLongiDegree(1);
		header.setLongiMinute(1.0);
		header.setLongiDirection("E");
		header.setLatiDegree(1);
		header.setLatiMinute(1.0);
		header.setLatiDirection("N");
		header.setVersion("V1.0");
		header.setCrc(1);
		return header;
	}
}
