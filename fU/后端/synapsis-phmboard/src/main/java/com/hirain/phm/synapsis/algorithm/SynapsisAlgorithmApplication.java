package com.hirain.phm.synapsis.algorithm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.hirain.phm.synapsis")
@EnableAsync
public class SynapsisAlgorithmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SynapsisAlgorithmApplication.class, args);
	}

}
