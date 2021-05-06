package com.navercloud.openapi.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

@Configuration
public class ObjectMapperConfig {
	@Bean
	public static ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		objectMapper.setDateFormat(df);

		return objectMapper;
	}
}
