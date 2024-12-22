package com.javaacademy.cryptowallet.service.convert.impl;

import com.javaacademy.cryptowallet.service.convert.ConvertDollarsToRublesService;
import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.math.BigDecimal;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("prod")
@Service
public class ConvertDollarsToRublesServiceImpl implements ConvertDollarsToRublesService {

  private final OkHttpClient client;

  @Value("${api-cbr.url}")
  private String apiUrl;

  public ConvertDollarsToRublesServiceImpl() {
    client = new OkHttpClient();
  }

  @Override
  public BigDecimal convertDollarsToRubles(BigDecimal countDollars) throws IOException {
    String pathParseValue = "$['rates']['USD']";

    Request request = new Builder()
        .url(apiUrl)
        .get()
        .build();

    Response response = client.newCall(request).execute();
    if (!response.isSuccessful() || response.body() == null) {
      throw new RuntimeException(
          "Ошибка получения ответа от сайта https://www.cbr-xml-daily.ru");
    }

    BigDecimal convertCourse = JsonPath.parse(response.body())
        .read(JsonPath.compile(pathParseValue), BigDecimal.class);
    return countDollars.divide(convertCourse);
  }

  @Override
  public BigDecimal convertRublesToDollars(BigDecimal countRubles) throws IOException {
    String pathParseValue = "$['rates']['USD']";

    Request request = new Builder()
        .url(apiUrl)
        .get()
        .build();

    Response response = client.newCall(request).execute();
    if (!response.isSuccessful() || response.body() == null) {
      throw new RuntimeException(
          "Ошибка получения ответа от сайта https://www.cbr-xml-daily.ru");
    }

    BigDecimal convertCourse = JsonPath.parse(response.body())
        .read(JsonPath.compile(pathParseValue), BigDecimal.class);
    return countRubles.multiply(convertCourse);
  }
}
