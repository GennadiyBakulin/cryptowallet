package com.javaacademy.cryptowallet.service.convert;

import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoCurrency;
import java.io.IOException;
import java.math.BigDecimal;

public interface ConvertCryptocurrencyToUsdService {

  BigDecimal convertCryptocurrencyToUsd(CryptoCurrency cryptoCurrency) throws IOException;
}
