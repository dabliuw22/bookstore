package com.leysoft.app.configurations;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AppConfigurations extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
		registry.addResourceHandler("/media/**")
			.addResourceLocations(Paths.get("media").toAbsolutePath().toUri().toString());
		registry.addResourceHandler("/file/**")
		.addResourceLocations(Paths.get("file").toAbsolutePath().toUri().toString());
	}
}