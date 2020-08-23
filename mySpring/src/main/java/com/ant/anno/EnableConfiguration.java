package com.ant.anno;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Configuration
//@ComponentScan("com.ant")
@ComponentScan("com.ant.service")
//@PropertySource("classpath:my.properties")
//@Import({Body.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
//@ImportResource("classpath:Spring.xml")
public @interface EnableConfiguration {
}
