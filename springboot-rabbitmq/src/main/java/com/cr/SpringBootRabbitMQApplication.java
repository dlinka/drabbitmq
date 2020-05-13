package com.cr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootRabbitMQApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRabbitMQApplication.class, args);
	}

}
