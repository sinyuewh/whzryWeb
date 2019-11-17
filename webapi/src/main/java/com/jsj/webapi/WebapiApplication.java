package com.jsj.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//@EnableAutoConfiguration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = { "com.jsj.*"})
@SpringBootApplication
public class WebapiApplication  extends SpringBootServletInitializer
{
	public static void main(String[] args) {
		SpringApplication.run(WebapiApplication.class, args);
	}
}
