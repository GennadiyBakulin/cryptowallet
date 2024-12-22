package com.javaacademy.cryptowallet.service.convert.impl;

import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoCurrency;
import com.javaacademy.cryptowallet.service.convert.ConvertCryptocurrencyToUsdService;
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
public class ConvertCryptocurrencyToUsdServiceImpl implements
    ConvertCryptocurrencyToUsdService {

  private final OkHttpClient client;

  @Value("${api-coingecko.url}")
  private String apiUrl;
  @Value("${api-coingecko.header.name}")
  private String headerName;
  @Value("${api-coingecko.header.value}")
  private String headerValue;

  public ConvertCryptocurrencyToUsdServiceImpl() {
    client = new OkHttpClient();
  }

  @Override
  public BigDecimal convertCryptocurrencyToUsd(CryptoCurrency cryptoCurrency)
      throws IOException {
    String cryptoCurrencyFullName = cryptoCurrency.getFullName();
    String pathSearch = "/simple/price?ids=%s&vs_currencies=usd".formatted(cryptoCurrencyFullName);
    String pathParseValue = "$['%s']['usd']".formatted(cryptoCurrencyFullName);

    Request request = new Builder()
        .url(apiUrl + pathSearch)
        .get()
        .addHeader(headerName, headerValue)
        .build();

    Response response = client.newCall(request).execute();
    if (!response.isSuccessful() || response.body() == null) {
      throw new RuntimeException(
          "Ошибка получения ответа от сайта https://api.coingecko.com/api/v3");
    }
    return JsonPath.parse(response.body()).read(JsonPath.compile(pathParseValue), BigDecimal.class);
  }
}
