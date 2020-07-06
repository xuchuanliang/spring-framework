package com.flowChart;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext a = new AnnotationConfigApplicationContext(Config.class);
		a.getBean(MyService.class).query();
	}
}
