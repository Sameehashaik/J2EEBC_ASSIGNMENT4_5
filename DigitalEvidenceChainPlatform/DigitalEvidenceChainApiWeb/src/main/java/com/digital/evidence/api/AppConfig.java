package com.digital.evidence.api;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@Configuration
@Import({
    com.digital.evidence.config.DigitalEvidenceChainConfig.class,
    com.digital.evidence.api.SecurityConfig.class 
})
@EnableJpaRepositories(basePackages = "com.digital.evidence.repository")
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.digital.evidence.api.service",
    "com.digital.evidence.service",
    "com.digital.evidence.api.controller",
    "com.digital.evidence.auth",
    "com.digital.evidence.config",
    "com.digital.evidence.repository" // still needed if repositories used
})
public class AppConfig implements WebMvcConfigurer {
	
	@Bean
    public MappingJackson2HttpMessageConverter jacksonConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // fix for LocalDate
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // ISO-8601

        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jacksonConverter());
    }
}
