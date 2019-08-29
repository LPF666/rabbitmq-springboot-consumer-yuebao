package com.bfxy.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class YuebaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(YuebaoApplication.class, args);
	}
}
