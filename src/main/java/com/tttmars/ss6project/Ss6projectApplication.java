package com.tttmars.ss6project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true,  securedEnabled = true,  jsr250Enabled = true)
public class Ss6projectApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ss6projectApplication.class, args);
	}

}
