package com.hirain.phm.bd.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import com.hirain.phm.bd.data.TableNameFormatter;
import com.hirain.phm.bd.data.impl.TableNameFormatterImpl;

@SpringBootApplication(scanBasePackages = "com.hirain.phm.bd")
@EnableAsync
public class PacketStoreAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PacketStoreAppApplication.class, args);
	}

	@Bean
	public TableNameFormatter tableName() {
		return new TableNameFormatterImpl();
	}
}
