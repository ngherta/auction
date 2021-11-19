package com.auction.config;

import com.auction.config.logging.RequestResponseLoggingFilter;
import com.auction.config.logging.TransactionLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;


@Configuration
public class FilterConfig {

  private static final String DEFAULT_FILTER_URL = "/rest/*";

  @Bean
  public FilterRegistrationBean<RequestResponseLoggingFilter> requestResponseLoggingFilterBean() {
    return createFilterRegistration(new RequestResponseLoggingFilter());
  }

  @Bean
  public FilterRegistrationBean<TransactionLoggingFilter> transactionLoggingFilterBean() {
    return createFilterRegistration(new TransactionLoggingFilter());
  }

  protected <T extends Filter> FilterRegistrationBean<T> createFilterRegistration(T filter) {
    FilterRegistrationBean<T> registration = new FilterRegistrationBean<>(filter);
    registration.addUrlPatterns(DEFAULT_FILTER_URL);

    return registration;
  }
}