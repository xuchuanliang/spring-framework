package com.ant.config;

import com.ant.Body;
import com.ant.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("com.ant")
@PropertySource("classpath:my.properties")
public class MainConfig {
	@Autowired
	private Environment environment;

	@Bean
	public Entity entity(){
		Entity entity = new Entity();
		entity.setAge(environment.getProperty("age"));
		entity.setName(environment.getProperty("name"));
		return entity;
	}

	@Bean
	public Body body(){
		Body body = new Body();
		body.setEntity(entity());
		return body;
	}
}
