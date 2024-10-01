package com.odin.core.update;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.odin.core.update.*")
public class CoreCreateApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreCreateApplication.class, args);
	}

}
