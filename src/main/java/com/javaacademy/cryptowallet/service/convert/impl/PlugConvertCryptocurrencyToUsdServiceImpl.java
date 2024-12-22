package com.javaacademy.cryptowallet.service.convert.impl;

import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoCurrency;
import com.javaacademy.cryptowallet.service.convert.ConvertCryptocurrencyToUsdService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("local")
@Service
public class PlugConvertCryptocurrencyToUsdServiceImpl implements
    ConvertCryptocurrencyToUsdService {

  @Value("${value-convert-any-cryptocurrency-to-usd}")
  private BigDecimal value;

  @Override
  public BigDecimal convertCryptocurrencyToUsd(CryptoCurrency cryptoCurrency) {
    return value;
  }
}
