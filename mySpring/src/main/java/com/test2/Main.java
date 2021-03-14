package com.test2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
		//第一大步：创建了AnnotationConfigApplicationContext实例
		//持有了DefaultListableBeanFactory引用，这是真正意义上存储bean的bean工厂
		//创建了一个读取解析被注解标记的class的BD读取器：AnnotatedBeanDefinitionReader
		//创建了用来解析@Conditional注解的解析器：ConditionEvaluator
		//创建了在注解配置情况下解析一些默认注解的BeanFactoryPostProcessor，如下：
		//1.ConfigurationClassPostProcessor，用来处理@Configuration注解、@Bean注解相关内容，实现了BeanDefinitionRegistryPostProcessor、BeanFactoryPostProcessor接口
		//2.AutowiredAnnotationBeanPostProcessor，用来处理@Autowire，@Inject，@Value，@Lookup注解相关内容，
		//3.CommonAnnotationBeanPostProcessor，用来处理@Resource、@PostConstruct、@PreDestroy等jdk相关注解内容
		//4.PersistenceAnnotationBeanPostProcessor，用来处理jpa相关内容
		//5.EventListenerMethodProcessor，用来处理@EventListener注解相关内容
		//6.DefaultEventListenerFactory，用来处理@EventListener注解相关内容
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();

		//第二大步：将标记有@Configuration/@Component注解的配置类注册到spring容器中，一般情况下此处都是注入配置类，即标记有@Configuration注解
		//此时spring会解析该配置类，并将其封装成为AnnotatedGenericBeanDefinition，并且初步解析其上的@Conditional、@Scope注解，最终将该bd注册到DefaultListableBeanFactory中
		annotationConfigApplicationContext.register(Config.class);

		annotationConfigApplicationContext.refresh();

	}


}
