package com.ant;

import com.ant.config.MainConfig;
import com.ant.service.FirstService;
import com.ant.service.SecondService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
		FirstService firstService = annotationConfigApplicationContext.getBean(FirstService.class);
		SecondService secondService = annotationConfigApplicationContext.getBean(SecondService.class);
		firstService.query();
		secondService.query();
	}
}
