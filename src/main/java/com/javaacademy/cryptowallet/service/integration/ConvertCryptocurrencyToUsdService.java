package com.javaacademy.cryptowallet.service.integration;

import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoCurrencyType;
import java.io.IOException;
import java.math.BigDecimal;

public interface ConvertCryptocurrencyToUsdService {

  BigDecimal convertCryptocurrencyToUsd(CryptoCurrencyType cryptoCurrencyType) throws IOException;
}
