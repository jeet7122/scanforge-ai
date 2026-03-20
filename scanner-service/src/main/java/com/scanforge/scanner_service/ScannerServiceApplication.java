package com.scanforge.scanner_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScannerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScannerServiceApplication.class, args);
	}

}
