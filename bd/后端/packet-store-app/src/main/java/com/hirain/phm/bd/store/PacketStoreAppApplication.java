package com.hirain.phm.bd.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.hirain.phm.bd")
@EnableAsync
public class PacketStoreAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PacketStoreAppApplication.class, args);
	}

}
