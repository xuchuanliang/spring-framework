package com.ant.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.ant.service")
//@ImportResource("classpath:spring.xml")
//@Import({MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class Config {
}
