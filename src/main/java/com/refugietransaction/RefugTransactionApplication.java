package com.refugietransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RefugTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(RefugTransactionApplication.class, args);
	}

}
