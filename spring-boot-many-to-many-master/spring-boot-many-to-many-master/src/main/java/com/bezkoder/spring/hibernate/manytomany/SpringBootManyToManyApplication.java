package com.bezkoder.spring.hibernate.manytomany;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing


public class SpringBootManyToManyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootManyToManyApplication.class, args);
	}


}
