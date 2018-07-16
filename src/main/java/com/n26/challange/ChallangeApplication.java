package com.n26.challange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * main app
 * 
 * @author resulav
 *
 */
@SpringBootApplication
@EnableScheduling
public class ChallangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallangeApplication.class, args);
	}
}
