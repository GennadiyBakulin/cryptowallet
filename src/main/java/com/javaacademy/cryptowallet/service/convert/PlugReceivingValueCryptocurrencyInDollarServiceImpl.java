package com.javaacademy.cryptowallet.service.convert;

import com.javaacademy.cryptowallet.entity.crypto.CryptoCurrency;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("local")
@Service
public class PlugReceivingValueCryptocurrencyInDollarServiceImpl implements
    ReceivingValueCryptocurrencyService {

  @Value("${value-convert-any-cryptocurrency-to-usd}")
  private BigDecimal value;

  @Override
  public BigDecimal getValueCryptocurrencyInDollar(CryptoCurrency cryptoCurrency) {
    return value;
  }
}
