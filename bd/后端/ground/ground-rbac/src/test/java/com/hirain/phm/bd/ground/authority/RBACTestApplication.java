package com.hirain.phm.bd.ground.authority;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com.hirain.phm.bd.ground")
@MapperScan("com.hirain.phm.bd.ground.**.dao")
public class RBACTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RBACTestApplication.class, args);
	}
}
