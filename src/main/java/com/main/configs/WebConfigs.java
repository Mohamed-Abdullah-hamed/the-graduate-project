package com.main.configs;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigs implements WebMvcConfigurer {

	

	/*
	 * @Bean ViewResolver viewResolver() {
	 * 
	 * InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	 * resolver.setPrefix("classpath:templates/"); resolver.setSuffix(".jsp");
	 * return resolver; }
	 */

	/*
	 * @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
	 * if (!registry.hasMappingForPattern("/resources/**")) {
	 * registry.addResourceHandler("/resources/**").addResourceLocations(
	 * "classpath:/static/"); } }
	 */

	

	

	@Bean
	MessageSource messageSource() {
		ReloadableResourceBundleMessageSource msgSource = new ReloadableResourceBundleMessageSource();
		msgSource.setBasename("classpath:payloads/messages");
		msgSource.setDefaultEncoding("UTF-8");
		return msgSource;
	}

	@Override
	@Bean
	public LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean vBean = new LocalValidatorFactoryBean();
		vBean.setValidationMessageSource(messageSource());
		return vBean;
	}
}
