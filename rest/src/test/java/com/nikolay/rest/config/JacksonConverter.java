package com.nikolay.rest.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.text.SimpleDateFormat;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * The type Jackson converter.
 */
public class JacksonConverter {

  /**
   * Create jackson message converter mapping jackson 2 http message converter.
   *
   * @return the mapping jackson 2 http message converter
   */
  public static MappingJackson2HttpMessageConverter createJacksonMessageConverter() {
    MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
    ObjectMapper mapper = createObjectMapperWithJacksonConverter();
    messageConverter.setObjectMapper(mapper);
    return messageConverter;
  }

  /**
   * Create object mapper with jackson converter object mapper.
   *
   * @return the object mapper
   */
  public static ObjectMapper createObjectMapperWithJacksonConverter() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    mapper.setSerializationInclusion(Include.NON_NULL);
    return mapper;
  }

}
