package com.ant.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

public class MyImportSelector implements ImportSelector {
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		Set<String> annotationTypes = importingClassMetadata.getAnnotationTypes();
		return new String[]{"com.ant.entity.Body"};
	}
}
