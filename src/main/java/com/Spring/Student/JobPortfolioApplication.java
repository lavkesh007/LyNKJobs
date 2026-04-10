package com.Spring.Student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
@EnableAsync
@SpringBootApplication
public class JobPortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPortfolioApplication.class, args);
	}

}
