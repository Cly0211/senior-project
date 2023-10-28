package com.seniorProject.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.seniorProject.project.mapper")
@EnableTransactionManagement
public class SeniorProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeniorProjectApplication.class, args);
	}

}
