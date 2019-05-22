package com.nikolay.webapp.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import brave.spring.webmvc.DelegatingTracingFilter;

@Configuration
public class WebMvcConfig {

  @Bean
  public FilterRegistrationBean<DelegatingTracingFilter> tracingFilterRegistration() {
    FilterRegistrationBean<DelegatingTracingFilter> registration = new FilterRegistrationBean<>();
    registration.setFilter(tracingFilter());
    registration.addUrlPatterns("/*");
    registration.setName("tracingFilter");
    return registration;
  }

  public DelegatingTracingFilter tracingFilter() {
    return new DelegatingTracingFilter();
  }

}
