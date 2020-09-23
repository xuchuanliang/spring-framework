package com.ant;

import com.ant.anno.EnableConfiguration;
import com.ant.config.MainConfig;
import com.ant.entity.Child;
import com.ant.entity.Parent;
import com.ant.service.FirstService;
import com.ant.service.SecondService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.annotation.Annotation;

public class Main {
	public static void main(String[] args) {
//		testInheritedAnnotation();
		testSpring();
	}

	public static void testSpring(){
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
		FirstService firstService = annotationConfigApplicationContext.getBean(FirstService.class);
		SecondService secondService = annotationConfigApplicationContext.getBean(SecondService.class);
		firstService.query();
		secondService.query();
	}

	public static void testInheritedAnnotation(){
		Parent parent = new Parent();
		Child child = new Child();
		EnableConfiguration annotation = child.getClass().getAnnotation(EnableConfiguration.class);
		Annotation[] annotations = child.getClass().getAnnotations();
		System.err.println(annotation);
	}
}
