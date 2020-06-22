/*
 * Copyright 2002-2010 the original author or authors.
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

package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 * Extension to the standard {@link BeanFactoryPostProcessor} SPI,
 * 是BeanFactoryPostProcessor SPI的扩展
 * allowing for the registration of further bean definitions <i>before</i> regular BeanFactoryPostProcessor detection kicks in.
 *
 *  In particular,
 *  尤其是
 * BeanDefinitionRegistryPostProcessor may register further bean definitions which in turn define BeanFactoryPostProcessor instances
 * .
 *
 * @author Juergen Hoeller
 * @since 3.0.1
 * @see org.springframework.context.annotation.ConfigurationClassPostProcessor
 */
public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {

	/**
	 * Modify the application context's internal bean definition registry after its
	 * standard initialization.
	 * 在进行标准化初始化之后，允许修改上下文内部bean Definition registry(beanDefinition注册器)
	 * All regular bean definitions will have been loaded,
	 * 所有的beanDefinition应该已经被加载完成
	 * but no beans will have been instantiated yet.
	 * 但是没有bean被实例化
	 * This allows for adding further
	 * bean definitions before the next post-processing phase kicks in.
	 *他允许在下一个阶段的post-processing开始之前添加更进一步的bean Definition，
	 * 也就是在postProcessBeanFactory之前执行
	 * @param registry the bean definition registry used by the application context
	 * @throws org.springframework.beans.BeansException in case of errors
	 */
	void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException;

}
