package com.flowChart;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.flowChart")
@Import(MyImportSelector.class)
public class Config {
}
