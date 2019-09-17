package com.netisov.tim.commandes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"com.netisov.tim.commandes"})
@EnableCaching
public class CommandesAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommandesAppApplication.class, args);
	}

}
