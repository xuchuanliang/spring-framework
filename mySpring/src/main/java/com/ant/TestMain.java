package com.ant;


import com.ant.config.Config;
import com.ant.service.IndexService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {
	public static void main(String[] args) {
		/**
		 * 记录spring中的扩展点：
		 * 1.BeanPostProcessor
		 * 2.BeanFactoryPostProcessor
		 * 3.BeanDefinitionRegistryPostProcessor,备注：实现了BeanFactoryPostProcessor接口，并且扩展了新的方法postProcessBeanDefinitionRegistry，
		 *   跟进到spring的内部类ConfigurationClassPostProcessor了解到的知识点，其中ConfigurationClassPostProcessor是解析我们注解配置类非常重要的一个类
		 * 4.通过@Import注解，注解用的类实现ImportSelector接口或ImportBeanDefinitionRegistrar接口
		 * 5.
		 *
		 *
		 *
		 *
		 */


		/**
		 * 记录向spring容器中添加bean的方式
		 * 1.通过手动注册的方式：annotationConfigApplicationContext.register(Config.class);---此种方式是通过一个类注入，由spring帮我们实例化
		 * 2.通过xml定义一个<bean><bean/>---此种方式是通过一个类注入，由spring帮我们实例化
		 * 3.通过@Component/@Service/@Controller/@Repository---此种方式是通过一个类注入，由spring帮我们实例化
		 * 4.通过@Import()中增加实现ImportBeanDefinitionRegistrar的类---此种方式spring是暴露了一个bd注册器给spring，允许自己构建bd交给spring容器，mybatis的mapperScan就是通过这种方式实现的，此种方式允许用户能够插手beanDefinition加入spring的过程
		 * 5.通过@Bean方式---此种方式是通过一个对象注入，我们可以查收bean实例化的过程
		 * 6.通过FactoryBean---此种方式是通过一个对象注入，我们可以查收bean实例化的过程
		 */
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
		annotationConfigApplicationContext.register(Config.class);
		annotationConfigApplicationContext.refresh();
		annotationConfigApplicationContext.getBean(IndexService.class).test();
	}
}
