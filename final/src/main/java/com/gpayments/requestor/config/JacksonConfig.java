package com.gpayments.requestor.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author GPayments created on 24/04/2019
 */
@Configuration
public class JacksonConfig {

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jsonSetup() {
    return customizer -> customizer.serializationInclusion(Include.NON_NULL);
  }
}

