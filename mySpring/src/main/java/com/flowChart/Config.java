package com.flowChart;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.flowChart")
//@Import({MyImportSelector.class,MyImportBeanDefinitionRegistrar.class})
public class Config {

	@Bean
	public MyDao myDao() {
		return new MyDao();
	}

	@Bean
	public MyDao2 myDao2() {
		return new MyDao2(myDao());
	}

}
