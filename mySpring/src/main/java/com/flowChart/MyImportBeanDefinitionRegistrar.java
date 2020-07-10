package com.flowChart;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
		genericBeanDefinition.setBeanClass(MyDao2.class);
		System.out.println(genericBeanDefinition.getBeanClass().getSimpleName());
		registry.registerBeanDefinition(genericBeanDefinition.getBeanClassName(),genericBeanDefinition);
		System.out.println("ImportBeanDefinitionRegistrar");
	}
}
