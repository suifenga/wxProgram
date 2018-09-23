package com.idig8;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.idig8.controller.interceptor.MiniInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	@Value("${server.file.path}")
	private String fileSpace;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//资源的路径.swagger2的资源.所在的目录，
		registry.addResourceHandler("/**")
		.addResourceLocations("classpath:/META-INF/resources/")
		.addResourceLocations("file:"+fileSpace);
		
	}


	
	@Bean
	public MiniInterceptor miniInterceptor() {
		return new MiniInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(miniInterceptor()).addPathPatterns("/user/**")
				       .addPathPatterns("/video/upload", "/video/uploadCover")
												  .addPathPatterns("/bgm/**");
		
		super.addInterceptors(registry);
	}

}
