package com.example.bambi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.example.bambi", "com.example.bambi.config"})

public class BambiadminApplication {

	public static void main(String[] args) {
		SpringApplication.run(BambiadminApplication.class, args);
	}

}
