package com.javaacademy.cryptowallet.entity.cryptoaccount;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class CryptoAccount {

  private final UUID uuid;
  private final String userLogin;
  private final CryptoCurrencyType cryptoCurrencyType;
  private BigDecimal amount = BigDecimal.ZERO;
}
