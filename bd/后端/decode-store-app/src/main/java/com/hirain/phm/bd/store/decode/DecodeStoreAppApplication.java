package com.hirain.phm.bd.store.decode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.hirain.phm.bd.store")
public class DecodeStoreAppApplication {

	public static void main(String[] args) {
		System.out.println(System.getProperty("java.library.path"));
		SpringApplication.run(DecodeStoreAppApplication.class, args);
	}

}
