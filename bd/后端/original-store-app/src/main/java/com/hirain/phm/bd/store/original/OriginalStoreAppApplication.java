package com.hirain.phm.bd.store.original;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.hirain.phm.bd.store.service.MessageStoragerFactory;
import com.hirain.phm.bd.store.service.impl.DefaultMessageStoragerFactory;

@SpringBootApplication
@ComponentScan("com.hirain.phm.bd.store")
public class OriginalStoreAppApplication {

	public static void main(String[] args) {
		System.out.println(System.getProperty("java.library.path"));
		SpringApplication.run(OriginalStoreAppApplication.class, args);
	}

	@Bean
	public MessageStoragerFactory factory() {
		return new DefaultMessageStoragerFactory();
	}
}
