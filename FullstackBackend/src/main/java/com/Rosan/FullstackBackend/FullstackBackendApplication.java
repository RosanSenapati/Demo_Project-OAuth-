package com.Rosan.FullstackBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class FullstackBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullstackBackendApplication.class, args);
	}

}
