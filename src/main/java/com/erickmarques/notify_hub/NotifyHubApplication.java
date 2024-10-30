package com.erickmarques.notify_hub;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EnableRabbit
public class NotifyHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotifyHubApplication.class, args);
	}

}
