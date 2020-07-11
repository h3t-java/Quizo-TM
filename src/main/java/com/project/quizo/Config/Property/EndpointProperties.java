package com.project.quizo.Config.Property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:endpoints.properties")
@ConfigurationProperties(prefix = "endpoints")
public class EndpointProperties {

	private String[] whiteList;

	public String[] getWhiteList() {
		return whiteList;
	}

	public void setWhiteList(String[] whiteList) {
		this.whiteList = whiteList;
	}

}