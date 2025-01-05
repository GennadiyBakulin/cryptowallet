package com.javaacademy.cryptowallet.service.integration.impl;

import com.javaacademy.cryptowallet.service.integration.ConvertBetweenDollarsAndRublesService;
import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("prod")
@Service
@RequiredArgsConstructor
public class ConvertBetweenDollarsAndRublesServiceImpl implements
    ConvertBetweenDollarsAndRublesService {

  private final MathContext mathContext;
  private final OkHttpClient client;

  private final String pathParse = "$.rates.USD";

  @Value("${integration.cbr.url}")
  private String apiUrl;

  @Override
  public BigDecimal convertDollarsToRubles(BigDecimal countDollars) throws IOException {
    Response response = getResponse(getRequest());
    BigDecimal convertCourse = getConvertCourse(response);
    return countDollars.divide(convertCourse, mathContext);
  }

  @Override
  public BigDecimal convertRublesToDollars(BigDecimal countRubles) throws IOException {
    Response response = getResponse(getRequest());
    BigDecimal convertCourse = getConvertCourse(response);
    return countRubles.multiply(convertCourse, mathContext);
  }

  private Request getRequest() {
    return new Builder()
        .url(apiUrl)
        .get()
        .build();
  }

  private Response getResponse(Request request) throws IOException {
    Response response = client.newCall(getRequest()).execute();
    if (!response.isSuccessful() || response.body() == null) {
      throw new RuntimeException(
          "Ошибка получения ответа от сайта %s".formatted(apiUrl));
    }
    return response;
  }

  private BigDecimal getConvertCourse(Response response) throws IOException {
    return JsonPath.parse(response.body().string())
        .read(JsonPath.compile(pathParse), BigDecimal.class);
  }
}
