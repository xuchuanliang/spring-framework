package com.ant.anno;

import com.ant.config.MyImportBeanDefinitionRegistrar;
import com.ant.config.MyImportSelector;
import com.ant.entity.Body;
import org.springframework.context.annotation.*;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Configuration
@ComponentScan("com.ant")
@PropertySource("classpath:my.properties")
@Import({Body.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
@ImportResource("classpath:Spring.xml")
public @interface EnableConfiguration {
}
