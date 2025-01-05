package com.javaacademy.cryptowallet.service.integration.impl;

import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoCurrencyType;
import com.javaacademy.cryptowallet.service.integration.ConvertCryptocurrencyToUsdService;
import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.math.BigDecimal;
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
public class ConvertCryptocurrencyToUsdServiceImpl implements
    ConvertCryptocurrencyToUsdService {

  private final OkHttpClient client;

  @Value("${integration.coingecko.url}")
  private String url;
  @Value("${integration.coingecko.header.name}")
  private String headerName;
  @Value("${integration.coingecko.header.value}")
  private String headerValue;

  @Override
  public BigDecimal convertCryptocurrencyToUsd(CryptoCurrencyType cryptoCurrencyType)
      throws IOException {
    String cryptoCurrencyFullName = cryptoCurrencyType.getFullName();
    String pathSearch = "/simple/price?ids=%s&vs_currencies=usd".formatted(cryptoCurrencyFullName);
    String path = "$.%s.usd".formatted(cryptoCurrencyFullName);

    Request request = new Builder()
        .url(url + pathSearch)
        .addHeader(headerName, headerValue)
        .get()
        .build();

    Response response = client.newCall(request).execute();
    if (!response.isSuccessful() || response.body() == null) {
      throw new RuntimeException("Ошибка получения ответа от сайта %s".formatted(url));
    }

    return JsonPath.parse(response.body().string()).read(JsonPath.compile(path), BigDecimal.class);
  }
}
