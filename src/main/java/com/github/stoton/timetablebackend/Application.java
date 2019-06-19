package com.github.stoton.timetablebackend;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.github.stoton")
@EnableJpaRepositories({"com.github.stoton.timetablebackend.repository", "com.github.stoton.timetablebackend.repository.optivum"})
@EnableSpringConfigured
@EnableAsync
@ComponentScan(basePackages = {"com.github.stoton"})
@Configuration
public class Application extends SpringBootServletInitializer implements ApplicationContextAware {

	private static ApplicationContext appContext;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appContext = applicationContext;
	}

	public static ApplicationContext getAppContext() {
		return appContext;
	}
}
