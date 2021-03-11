package com.test2;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Config.class);
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) annotationConfigApplicationContext.getBeanFactory();
		defaultListableBeanFactory.getBean(SecondService.class).test();
	}



}
