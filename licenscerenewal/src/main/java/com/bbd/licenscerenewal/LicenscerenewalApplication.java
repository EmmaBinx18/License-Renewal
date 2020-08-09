package com.bbd.licenscerenewal;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "License Renewal API", version = "1.0.0", description = "License Renewal API"))
public class LicenscerenewalApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicenscerenewalApplication.class, args);
	}

}
