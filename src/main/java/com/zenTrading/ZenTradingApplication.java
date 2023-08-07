package com.zenTrading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:beans.xml")
public class ZenTradingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZenTradingApplication.class, args);
	}

}
