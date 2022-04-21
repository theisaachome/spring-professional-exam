package com.isaachome.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"com.isaachome.services","com.isaachome.aspects"})
@EnableAspectJAutoProxy
public class ProjectConfig {

}
