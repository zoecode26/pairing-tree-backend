package com.makers.pairingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PairingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PairingAppApplication.class, args);
	}

}
