package com.hirain.phm.bd.ground.train.test;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.GroundWebApplication;
import com.hirain.phm.bd.ground.train.dao.ProjectMapper;
import com.hirain.phm.bd.ground.train.dao.TrainMapper;
import com.hirain.phm.bd.ground.train.dao.TrainStorageInfoMapper;
import com.hirain.phm.bd.ground.train.domain.Project;
import com.hirain.phm.bd.ground.train.domain.Train;
import com.hirain.phm.bd.ground.train.service.TrainService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GroundWebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FaultTest {


	@Autowired
	TrainMapper trainMapper;

	@Autowired
	TrainService trainService;


	@Autowired
	TrainStorageInfoMapper trainStorageInfoMapper;

	@Autowired
	ProjectMapper projectMapper;


	@Test
	public void insertCity() {
		String[] cities = { "上海", "北京", "武汉", "成都", "广州", "深圳" };
		String[] lines = { "01", "02", "03" };
		String[] trains = { "01", "02", "03" };

		for (String city : cities) {
			Project project = new Project();
			project.setCity(city);
			for (String lineName : lines) {
				project.setName(city + "地铁" + lineName + "号线");
				project.setLine(lineName);
				projectMapper.insertUseGeneratedKeys(project);
				for (String trainNo : trains) {
					Train train = new Train();
					train.setProjectId(project.getId());
					train.setTrainNo(trainNo);
					train.setMac1("58-94-6B-33-89-B0");
					train.setMac2("58-94-6B-33-89-B0");
					train.setConfigInfo(getTrain());
					train.setProtocolFile("protocolfile");
					train.setTrainStatus((byte) 111);
					trainMapper.insertUseGeneratedKeys(train);

				}

			}
		}
	}

	@Test
	public void updateTrain() {
		List<Train> trainList = trainMapper.selectAll();
		for (Train train : trainList) {
			train.setConfigInfo(getTrain());
			trainMapper.updateByPrimaryKey(train);
		}
	}

	public String getTrain() {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n" + "<Train>\r\n"
				+ "    <projectName>上海7号线一期</projectName>\r\n" + "    <cityName>上海</cityName>\r\n" + "    <lineName>1</lineName>\r\n"
				+ "    <trainNo>1</trainNo>\r\n" + "    <servers>\r\n" + "        <server>\r\n"
				+ "            <ip type=\"Type1\">10.40.16.41</ip>\r\n" + "            <ip type=\"Type2\">10.0.1.190</ip>\r\n"
				+ "        </server>\r\n" + "        <server>\r\n" + "            <ip type=\"Type1\">172.20.10.2</ip>\r\n"
				+ "            <ip type=\"Type2\">10.0.1.199</ip>\r\n" + "        </server>\r\n" + "    </servers>\r\n" + "    <carInfo>\r\n"
				+ "        <carAmount>6</carAmount>\r\n" + "        <doorAmount>10</doorAmount>\r\n" + "    </carInfo>\r\n" + "    <cars>\r\n"
				+ "        <car index=\"1\" type=\"2\" name=\"1\">\r\n" + "            <mdcu ip=\"10.40.16.41\"/>\r\n"
				+ "            <mdcu ip=\"10.0.1.121\"/>\r\n" + "            <doors>\r\n" + "                <door addr=\"1\"/>\r\n"
				+ "                <door addr=\"2\"/>\r\n" + "                <door addr=\"3\"/>\r\n" + "                <door addr=\"4\"/>\r\n"
				+ "                <door addr=\"5\"/>\r\n" + "                <door addr=\"6\"/>\r\n" + "                <door addr=\"7\"/>\r\n"
				+ "                <door addr=\"8\"/>\r\n" + "                <door addr=\"9\"/>\r\n" + "                <door addr=\"10\"/>\r\n"
				+ "                <door addr=\"11\"/>\r\n" + "                <door addr=\"12\"/>\r\n" + "            </doors>\r\n"
				+ "        </car>\r\n" + "        <car index=\"2\" type=\"1\" name=\"2\">\r\n" + "            <mdcu ip=\"172.20.10.2\"/>\r\n"
				+ "            <mdcu ip=\"10.0.1.122\"/>\r\n" + "            <doors>\r\n" + "                <door addr=\"1\"/>\r\n"
				+ "                <door addr=\"2\"/>\r\n" + "                <door addr=\"3\"/>\r\n" + "                <door addr=\"4\"/>\r\n"
				+ "                <door addr=\"5\"/>\r\n" + "                <door addr=\"6\"/>\r\n" + "                <door addr=\"7\"/>\r\n"
				+ "                <door addr=\"8\"/>\r\n" + "                <door addr=\"9\"/>\r\n" + "                <door addr=\"10\"/>\r\n"
				+ "            </doors>\r\n" + "        </car>\r\n" + "        <car index=\"3\" type=\"1\" name=\"3\">\r\n"
				+ "            <mdcu ip=\"10.0.1.115\"/>\r\n" + "            <mdcu ip=\"10.0.1.123\"/>\r\n" + "            <doors>\r\n"
				+ "                <door addr=\"1\"/>\r\n" + "                <door addr=\"2\"/>\r\n" + "                <door addr=\"3\"/>\r\n"
				+ "                <door addr=\"4\"/>\r\n" + "                <door addr=\"5\"/>\r\n" + "                <door addr=\"6\"/>\r\n"
				+ "                <door addr=\"7\"/>\r\n" + "                <door addr=\"8\"/>\r\n" + "                <door addr=\"9\"/>\r\n"
				+ "                <door addr=\"10\"/>\r\n" + "            </doors>\r\n" + "        </car>\r\n"
				+ "        <car index=\"4\" type=\"1\" name=\"4\">\r\n" + "            <mdcu ip=\"10.0.1.116\"/>\r\n"
				+ "            <mdcu ip=\"10.0.1.124\"/>\r\n" + "            <doors>\r\n" + "                <door addr=\"1\"/>\r\n"
				+ "                <door addr=\"2\"/>\r\n" + "                <door addr=\"3\"/>\r\n" + "                <door addr=\"4\"/>\r\n"
				+ "                <door addr=\"5\"/>\r\n" + "                <door addr=\"6\"/>\r\n" + "                <door addr=\"7\"/>\r\n"
				+ "                <door addr=\"8\"/>\r\n" + "                <door addr=\"9\"/>\r\n" + "                <door addr=\"10\"/>\r\n"
				+ "            </doors>\r\n" + "        </car>\r\n" + "        <car index=\"5\" type=\"1\" name=\"5\">\r\n"
				+ "            <mdcu ip=\"10.0.1.117\"/>\r\n" + "            <mdcu ip=\"10.0.1.125\"/>\r\n" + "            <doors>\r\n"
				+ "                <door addr=\"1\"/>\r\n" + "                <door addr=\"2\"/>\r\n" + "                <door addr=\"3\"/>\r\n"
				+ "                <door addr=\"4\"/>\r\n" + "                <door addr=\"5\"/>\r\n" + "                <door addr=\"6\"/>\r\n"
				+ "                <door addr=\"7\"/>\r\n" + "                <door addr=\"8\"/>\r\n" + "                <door addr=\"9\"/>\r\n"
				+ "                <door addr=\"10\"/>\r\n" + "            </doors>\r\n" + "        </car>\r\n"
				+ "        <car index=\"6\" type=\"2\" name=\"6\">\r\n" + "            <mdcu ip=\"10.40.16.22\"/>\r\n"
				+ "            <mdcu ip=\"10.0.1.126\"/>\r\n" + "            <doors>\r\n" + "                <door addr=\"1\"/>\r\n"
				+ "                <door addr=\"2\"/>\r\n" + "                <door addr=\"3\"/>\r\n" + "                <door addr=\"4\"/>\r\n"
				+ "                <door addr=\"5\"/>\r\n" + "                <door addr=\"6\"/>\r\n" + "                <door addr=\"7\"/>\r\n"
				+ "                <door addr=\"8\"/>\r\n" + "                <door addr=\"9\"/>\r\n" + "                <door addr=\"10\"/>\r\n"
				+ "                <door addr=\"11\"/>\r\n" + "                <door addr=\"12\"/>\r\n" + "            </doors>\r\n"
				+ "        </car>\r\n" + "    </cars>\r\n" + "</Train>\r\n" + "";
			return xml;
	}
}