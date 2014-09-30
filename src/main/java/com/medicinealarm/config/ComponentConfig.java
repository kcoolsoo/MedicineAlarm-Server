package com.medicinealarm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages="com.medicinealarm.controller", excludeFilters={ @Filter(Configuration.class)} )
public class ComponentConfig {

}
