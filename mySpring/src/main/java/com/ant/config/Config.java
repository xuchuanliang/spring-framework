package com.ant.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan("com.ant")
@ImportResource("classpath:spring.xml")
public class Config {
}
