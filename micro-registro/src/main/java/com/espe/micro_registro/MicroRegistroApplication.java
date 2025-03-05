package com.espe.micro_registro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroRegistroApplication {
	public static void main(String[] args) {
		SpringApplication.run(MicroRegistroApplication.class, args);
	}
}
