package com.driden.job_app_company_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JobAppCompanyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobAppCompanyServiceApplication.class, args);
	}

}
