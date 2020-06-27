/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context.annotation;

import java.util.function.Supplier;

import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Standalone application context, accepting <em>component classes</em> as input &mdash;
 * in particular {@link Configuration @Configuration}-annotated classes, but also plain
 * {@link org.springframework.stereotype.Component @Component} types and JSR-330 compliant
 * classes using {@code javax.inject} annotations.
 *
 * <p>Allows for registering classes one by one using {@link #register(Class...)}
 * as well as for classpath scanning using {@link #scan(String...)}.
 *
 * <p>In case of multiple {@code @Configuration} classes, {@link Bean @Bean} methods
 * defined in later classes will override those defined in earlier classes. This can
 * be leveraged to deliberately override certain bean definitions via an extra
 * {@code @Configuration} class.
 *
 * <p>See {@link Configuration @Configuration}'s javadoc for usage examples.
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @since 3.0
 * @see #register
 * @see #scan
 * @see AnnotatedBeanDefinitionReader
 * @see ClassPathBeanDefinitionScanner
 * @see org.springframework.context.support.GenericXmlApplicationContext
 */
public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotationConfigRegistry {

	private final AnnotatedBeanDefinitionReader reader;

	private final ClassPathBeanDefinitionScanner scanner;


	/**
	 * Create a new AnnotationConfigApplicationContext that needs to be populated
	 * through {@link #register} calls and then manually {@linkplain #refresh refreshed}.
	 */
	public AnnotationConfigApplicationContext() {
		//1.创建一个读取被注解定义的BeanDefinition读取器，这个对象只能读取加了注解的类
		//2.在构造方法中，通过AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry)，
		// 向注册器（此处是this，即AnnotationConfigApplicationContext）中注册annotation相关的PostProcessors，
		// 具体注释跟进org.springframework.context.annotation.AnnotationConfigUtils.registerAnnotationConfigProcessors
		/*
			1.创建一个读取被注解定义的BeanDefinition读取器，类型是AnnotatedBeanDefinitionReader
			2.在new AnnotatedBeanDefinitionReader()内部，通过AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry)方法，
			向registry（此处是this，即AnnotationConfigApplicationContext）中注册annotation模式相关的PostProcessors，具体可以跟进AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry)方法
			即向当前对象的beanFactory（实际上是DefaultListableBeanFactory类型）的beanDefinitionMap中注册了五个BeanDefinition，分别如下；
				1).name是org.springframework.context.annotation.internalConfigurationAnnotationProcessor
				   beanDefinition类型：是RootBeanDefinition
				   class类型是org.springframework.context.annotation.ConfigurationClassPostProcessor
				   作用：该类实现BeanFactoryPostProcessor和BeanDefinitionRegistryPostProcessor两个接口
				   其中BeanFactoryPostProcessorBean的作用：在bd被加载到spring容器之后，实例化之前spring容器会调用所有的BeanFactoryPostProcessor的postProcessBeanFactory方法，在bean实例化前对bean和beanFactory进行修改
				   其中DefinitionRegistryPostProcessor的作用：在bd被加载到spring容器之后，实例化之前，spring容器会先调用所有的BeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry(registry)方法，
				   允许修改beanDefinition的注册器，然后再调用BeanFactoryPostProcessorBean的postProcessBeanFactory方法
				   实现类ConfigurationClassPostProcessor的作用：
				2).name是org.springframework.context.event.internalEventListenerFactory
				   beanDefinition类型：是RootBeanDefinition
				   class类型是org.springframework.context.event.DefaultEventListenerFactory，实现EventListenerFactory接口
				   作用：
				3).name是org.springframework.context.event.internalEventListenerProcessor
				   beanDefinition类型：是RootBeanDefinition
				   class类型是org.springframework.context.event.EventListenerMethodProcessor，实现BeanFactoryPostProcessor接口
				   作用：
				4).name是org.springframework.context.annotation.internalAutowiredAnnotationProcessor
				   beanDefinition类型：是RootBeanDefinition
				   class类型是org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor，实现BeanPostProcessor接口
				   作用：
				5).name是org.springframework.context.annotation.internalCommonAnnotationProcessor：
				   beanDefinition类型：是RootBeanDefinition
				   class类型是org.springframework.context.annotation.CommonAnnotationBeanPostProcessor，实现BeanPostProcessor接口
				   作用：

		 */
		this.reader = new AnnotatedBeanDefinitionReader(this);
		/*
		 	创建一个类路径BeanDefinition扫描器
		 	内部默认会
		 	1.注册默认过滤器（registerDefaultFilters）
		 	2.设置上下文环境（setEnvironment）
		 	3.设置资源加载器（setResourceLoader）
		 */
		this.scanner = new ClassPathBeanDefinitionScanner(this);
	}

	/**
	 * Create a new AnnotationConfigApplicationContext with the given DefaultListableBeanFactory.
	 * @param beanFactory the DefaultListableBeanFactory instance to use for this context
	 */
	public AnnotationConfigApplicationContext(DefaultListableBeanFactory beanFactory) {
		super(beanFactory);
		this.reader = new AnnotatedBeanDefinitionReader(this);
		this.scanner = new ClassPathBeanDefinitionScanner(this);
	}

	/**
	 * Create a new AnnotationConfigApplicationContext, deriving bean definitions
	 * from the given component classes and automatically refreshing the context.
	 * @param componentClasses one or more component classes &mdash; for example,
	 * {@link Configuration @Configuration} classes
	 */
	/**
	 * 这是入口方法
	 * @param componentClasses
	 */
	public AnnotationConfigApplicationContext(Class<?>... componentClasses) {
		/**
		 * 1.在父类AbstractApplicationContext的构造方法中创建PathMatchingResourcePatternResolver
		 * 2.在父类GenericApplicationContext的构造方法，创建默认的beanFactory，类型是DefaultListableBeanFactory
		 * 3.创建一个被注释的beanDefinition读取器：AnnotatedBeanDefinitionReader
		 *
		 * 4.在DefaultListableBeanFactory的beanDefinitionMap增加一个BeanFactoryPostProcessorBean和DefinitionRegistryPostProcessor的实现：ConfigurationClassPostProcessor，Beandifinition类型是RootBeanDefinition，Role是ROLE_INFRASTRUCTURE
		 * 5.在DefaultListableBeanFactory的beanDefinitionMap增加一个EventListenerFactory的实现：DefaultEventListenerFactory，Beandifinition类型是RootBeanDefinition，Role是ROLE_INFRASTRUCTURE
		 * 6.在DefaultListableBeanFactory的beanDefinitionMap增加一个BeanFactoryPostProcessor的实现：EventListenerMethodProcessor，Beandifinition类型是RootBeanDefinition，Role是ROLE_INFRASTRUCTURE
		 * 6.在DefaultListableBeanFactory的beanDefinitionMap增加一个BeanPostProcessor的实现：AutowiredAnnotationBeanPostProcessor，Beandifinition类型是RootBeanDefinition，Role是ROLE_INFRASTRUCTURE
		 * 7.在DefaultListableBeanFactory的beanDefinitionMap增加一个BeanPostProcessor的实现：CommonAnnotationBeanPostProcessor，Beandifinition类型是RootBeanDefinition，Role是ROLE_INFRASTRUCTURE
		 *
		 * 8.创建一个从Classpath扫描BeanDefinition的扫描器：CommonAnnotationBeanPostProcessor
		 *
		 */
		this();
		/**
		 * 调用AnnotatedBeanDefinitionReader的register方法，将当前配置类转成AnnotatedGenericBeanDefinition-->definitionHolder
		 * 最终使用BeanDefinitionReaderUtils.registerBeanDefinition()注册到registry中，此处的registry操作实际上是将beanDefinition注册到DefaultListableBeanFactory的beanDefinitionMap中
		 *
		 * 将一个或多个类手动注册到当前容器中，并且在注册完成后，必须按照顺序调用refresh()方法
		 *
		 * 1.将需要手动注册的组件类转成AnnotatedGenericBeanDefinition
		 * 2.解析该类上的注解，如@lazy，@Primary，@DependsOn，@Role，@Description，并将这些注解的值赋给该AnnotatedGenericBeanDefinition
		 * 3.将该AnnotatedGenericBeanDefinition put到DefaultListableBeanFactory的BeanDefinitionMap中
		 */
		register(componentClasses);
		/**
		 * 调用refresh方法
		 * 在调用该方法之前，我们的bean工厂，即DefaultListableBeanFactory中一共有六个BeanDefinition，其中5个RootBeanDefinition（spring 内部定义的BeanDefinition,Role是2），1个AnnotatedGenericBeanDefinition(我们的配置类)
		 * 真正的实例化Bean
		 */
		refresh();
	}

	/**
	 * Create a new AnnotationConfigApplicationContext, scanning for components
	 * in the given packages, registering bean definitions for those components,
	 * and automatically refreshing the context.
	 * @param basePackages the packages to scan for component classes
	 */
	public AnnotationConfigApplicationContext(String... basePackages) {
		this();
		scan(basePackages);
		refresh();
	}


	/**
	 * Propagate the given custom {@code Environment} to the underlying
	 * {@link AnnotatedBeanDefinitionReader} and {@link ClassPathBeanDefinitionScanner}.
	 */
	@Override
	public void setEnvironment(ConfigurableEnvironment environment) {
		super.setEnvironment(environment);
		this.reader.setEnvironment(environment);
		this.scanner.setEnvironment(environment);
	}

	/**
	 * Provide a custom {@link BeanNameGenerator} for use with {@link AnnotatedBeanDefinitionReader}
	 * and/or {@link ClassPathBeanDefinitionScanner}, if any.
	 * <p>Default is {@link org.springframework.context.annotation.AnnotationBeanNameGenerator}.
	 * <p>Any call to this method must occur prior to calls to {@link #register(Class...)}
	 * and/or {@link #scan(String...)}.
	 * @see AnnotatedBeanDefinitionReader#setBeanNameGenerator
	 * @see ClassPathBeanDefinitionScanner#setBeanNameGenerator
	 */
	public void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
		this.reader.setBeanNameGenerator(beanNameGenerator);
		this.scanner.setBeanNameGenerator(beanNameGenerator);
		getBeanFactory().registerSingleton(
				AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR, beanNameGenerator);
	}

	/**
	 * Set the {@link ScopeMetadataResolver} to use for registered component classes.
	 * <p>The default is an {@link AnnotationScopeMetadataResolver}.
	 * <p>Any call to this method must occur prior to calls to {@link #register(Class...)}
	 * and/or {@link #scan(String...)}.
	 */
	public void setScopeMetadataResolver(ScopeMetadataResolver scopeMetadataResolver) {
		this.reader.setScopeMetadataResolver(scopeMetadataResolver);
		this.scanner.setScopeMetadataResolver(scopeMetadataResolver);
	}


	//---------------------------------------------------------------------
	// Implementation of AnnotationConfigRegistry
	//---------------------------------------------------------------------

	/**
	 * Register one or more component classes to be processed.
	 * <p>Note that {@link #refresh()} must be called in order for the context
	 * to fully process the new classes.
	 *
	 * 注册一个或多个需要被处理的组件类
	 * 必须在注册之后调用refresh()方法
	 *
	 * 最终委托给BeanDefinition注册器：AnnotatedBeanDefinitionReader，调用其注册方法
	 *
	 * @param componentClasses one or more component classes &mdash; for example,
	 * {@link Configuration @Configuration} classes
	 * @see #scan(String...)
	 * @see #refresh()
	 */
	@Override
	public void register(Class<?>... componentClasses) {
		Assert.notEmpty(componentClasses, "At least one component class must be specified");
		this.reader.register(componentClasses);
	}

	/**
	 * Perform a scan within the specified base packages.
	 * <p>Note that {@link #refresh()} must be called in order for the context
	 * to fully process the new classes.
	 * @param basePackages the packages to scan for component classes
	 * @see #register(Class...)
	 * @see #refresh()
	 */
	@Override
	public void scan(String... basePackages) {
		Assert.notEmpty(basePackages, "At least one base package must be specified");
		this.scanner.scan(basePackages);
	}


	//---------------------------------------------------------------------
	// Convenient methods for registering individual beans
	//---------------------------------------------------------------------

	/**
	 * Register a bean from the given bean class, deriving its metadata from
	 * class-declared annotations, and optionally providing explicit constructor
	 * arguments for consideration in the autowiring process.
	 * <p>The bean name will be generated according to annotated component rules.
	 * @param beanClass the class of the bean
	 * @param constructorArguments argument values to be fed into Spring's
	 * constructor resolution algorithm, resolving either all arguments or just
	 * specific ones, with the rest to be resolved through regular autowiring
	 * (may be {@code null} or empty)
	 * @since 5.0
	 */
	public <T> void registerBean(Class<T> beanClass, Object... constructorArguments) {
		registerBean(null, beanClass, constructorArguments);
	}

	/**
	 * Register a bean from the given bean class, deriving its metadata from
	 * class-declared annotations, and optionally providing explicit constructor
	 * arguments for consideration in the autowiring process.
	 * @param beanName the name of the bean (may be {@code null})
	 * @param beanClass the class of the bean
	 * @param constructorArguments argument values to be fed into Spring's
	 * constructor resolution algorithm, resolving either all arguments or just
	 * specific ones, with the rest to be resolved through regular autowiring
	 * (may be {@code null} or empty)
	 * @since 5.0
	 */
	public <T> void registerBean(@Nullable String beanName, Class<T> beanClass, Object... constructorArguments) {
		this.reader.doRegisterBean(beanClass, null, beanName, null,
				bd -> {
					for (Object arg : constructorArguments) {
						bd.getConstructorArgumentValues().addGenericArgumentValue(arg);
					}
				});
	}

	@Override
	public <T> void registerBean(@Nullable String beanName, Class<T> beanClass, @Nullable Supplier<T> supplier,
			BeanDefinitionCustomizer... customizers) {

		this.reader.doRegisterBean(beanClass, supplier, beanName, null, customizers);
	}

}
