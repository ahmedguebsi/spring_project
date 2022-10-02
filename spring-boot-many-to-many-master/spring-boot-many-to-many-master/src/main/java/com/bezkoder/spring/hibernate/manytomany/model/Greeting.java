package com.bezkoder.spring.hibernate.manytomany.model;

import org.springframework.web.bind.annotation.RequestMapping;

public interface Greeting {
	
	@RequestMapping("/greeting")
    String greeting();

}
