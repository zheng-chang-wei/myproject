package com.hirain.phm.bd.ground.train;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com.hirain.phm.bd.ground")
@MapperScan("com.hirain.phm.bd.ground.**.dao")
public class TrainTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainTestApplication.class, args);
	}
}
