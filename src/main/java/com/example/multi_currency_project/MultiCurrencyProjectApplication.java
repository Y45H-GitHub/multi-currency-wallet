package com.example.multi_currency_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MultiCurrencyProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiCurrencyProjectApplication.class, args);
	}

}
