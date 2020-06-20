package com.codebase.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.codebase" })
public class CodebaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodebaseApplication.class, args);
	}

}
