package com.ant.config;

import com.ant.freemark.MyImportBeanDefinitionRegistrar;
import com.ant.freemark.MyImportSelector;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan("com.ant")
@ImportResource("classpath:spring.xml")
@Import({MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class Config {
}
