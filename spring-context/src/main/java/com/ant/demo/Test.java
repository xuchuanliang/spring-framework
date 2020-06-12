package com.ant.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		Entity bean = classPathXmlApplicationContext.getBean(Entity.class);
		System.err.println(bean);
	}
}
