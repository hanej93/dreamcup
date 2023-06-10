package com.dreamcup.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Configuration
public class LocalDateTimeSerializerConfig {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Bean
	public SimpleModule localDateTimeSerializerModule() {
		SimpleModule module = new SimpleModule();
		module.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
			@Override
			public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws
				IOException {
				jsonGenerator.writeString(localDateTime.format(formatter));
			}
		});

		return module;
	}
}
