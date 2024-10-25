package com.os.basket_service;

import com.os.spring_security.annotations.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableSecurity
@EnableCaching
public class BasketServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasketServiceApplication.class, args);
	}

}
