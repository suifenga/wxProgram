package com.idig8;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	@Value("${server.face.path}")
	private String fileSpace;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//资源的路径.swagger2的资源.所在的目录，
		registry.addResourceHandler("/**")
		.addResourceLocations("classpath:/META-INF/resources/")
		.addResourceLocations("file:"+fileSpace);
		
	}

}
