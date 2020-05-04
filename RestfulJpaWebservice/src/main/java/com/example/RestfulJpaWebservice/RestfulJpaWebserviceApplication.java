package com.example.RestfulJpaWebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(value="com.example.RestfulJpaWebservice")
@EntityScan(value="com.example.RestfulJpaWebservice")
@ComponentScan(value="com.example.RestfulJpaWebservice")

public class RestfulJpaWebserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulJpaWebserviceApplication.class, args);
	}

}
