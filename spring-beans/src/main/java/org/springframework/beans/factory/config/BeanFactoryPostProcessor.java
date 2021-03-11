/*
 * Copyright 2002-2016 the original author or authors.
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

package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;

/**
 * Allows for custom modification of an application context's bean definitions,
 * adapting the bean property values of the context's underlying bean factory.
 * 允许自定义修改应用上下文的BeanDefinition
 * 使用上下文内部的beanFactory修改bean的属性值
 *
 * <p>Application contexts can auto-detect BeanFactoryPostProcessor beans in
 * their bean definitions and apply them before any other beans get created.
 *
 * spring应用上下文会自动检测所有BeanDefinition中的BeanFactoryPostProcessor的实现类，
 * 并且在创建其他bean之前调用这些BeanFactoryPostProcessor
 *
 *
 * <p>Useful for custom config files targeted at system administrators that
 * override bean properties configured in the application context.
 *
 *	系统管理员自定义配置文件并且在应用程序上下文中覆盖或重写bean的属性时，实现BeanFactoryPostProcessor接口非常有用
 *
 * <p>See PropertyResourceConfigurer and its concrete implementations for out-of-the-box solutions that address such configuration needs.
 *	查看PropertyResourceConfigurer，他是BeanFactoryPostProcessor的一个实现类，为了解决配置问题的开箱即用的解决方案
 *
 * <p>A BeanFactoryPostProcessor may interact with and modify bean definitions, but never bean instances.
 *
 *  BeanFactoryPostProcessor可能影响或者修改BeanDefinition，但是不会影响bean的实例
 *
 * Doing so may cause premature bean instantiation, violating the container and causing unintended side-effects.
 * 如果影响bean实例，则可能会导致bean的提前实例化、破坏容器并产生意外的副作用
 *
 * If bean instance interaction is required, consider implementing
 * {@link BeanPostProcessor} instead.
 * 如果需要对Bean的实例进行操作，可以实现BeanPostProcessor接口
 *
 * @author Juergen Hoeller
 * @since 06.07.2003
 * @see BeanPostProcessor
 * @see PropertyResourceConfigurer
 * 总的来说：BeanFactoryPostProcessor是在BeanDefinition被加载之后，bean被实例化之前被调用，主要能够修改beanFactory和beanDefinition
 */
@FunctionalInterface
public interface BeanFactoryPostProcessor {

	/**
	 * Modify the application context's internal bean factory after its standard
	 * initialization.
	 * 在进行标准化的初始化之后，允许修改应用上下文的内部bean工厂
	 * All bean definitions will have been loaded,
	 * 所有的beanDefinition应该已经被加载
	 * but no beans will have been instantiated yet.
	 * 但是还没有bean被实例化
	 * This allows for overriding or adding properties even to eager-initializing beans.
	 *	他允许在创建bean之前做一些事情
	 * @param beanFactory the bean factory used by the application context
	 * @throws org.springframework.beans.BeansException in case of errors
	 */
	void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
