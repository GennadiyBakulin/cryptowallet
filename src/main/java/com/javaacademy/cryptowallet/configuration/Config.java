package com.javaacademy.cryptowallet.configuration;

import java.math.MathContext;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

  private final int precision = 10;

  @Bean
  public OkHttpClient okHttpClient() {
    return new OkHttpClient();
  }

  @Bean
  public MathContext mathContext() {
    return new MathContext(precision);
  }
}
