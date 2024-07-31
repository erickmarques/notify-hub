package com.erickmarques.notify_hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NotifyHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotifyHubApplication.class, args);
	}

}
