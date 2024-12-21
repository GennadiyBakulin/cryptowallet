package com.javaacademy.cryptowallet.service.convert;

import com.javaacademy.cryptowallet.entity.crypto.CryptoCurrency;
import java.io.IOException;
import java.math.BigDecimal;

public interface ReceivingValueCryptocurrencyService {

  BigDecimal getValueCryptocurrencyInDollar(CryptoCurrency cryptoCurrency) throws IOException;
}
