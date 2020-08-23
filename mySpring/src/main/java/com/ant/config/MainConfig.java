package com.ant.config;

import com.ant.anno.EnableConfiguration;
import com.ant.entity.Body;
import com.ant.entity.Entity;
import com.ant.entity.StaticEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

//@Configuration
//@ComponentScan("com.ant")
//@PropertySource("classpath:my.properties")
@EnableConfiguration
public class MainConfig {
//	@Autowired
//	private Environment environment;
//
//	@Bean
//	public  Entity entity() {
//		Entity entity = new Entity();
//		entity.setAge(environment.getProperty("age"));
//		entity.setName(environment.getProperty("name"));
//		return entity;
//	}
//
//	@Bean
//	public Body body() {
//		Body body = new Body();
//		body.setEntity(entity());
//		return body;
//	}
//
//	@Bean
//	public static StaticEntity staticEntity(){
//		return new StaticEntity();
//	}
}
