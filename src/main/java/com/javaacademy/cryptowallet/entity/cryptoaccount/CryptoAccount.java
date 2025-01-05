package com.javaacademy.cryptowallet.entity.cryptoaccount;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class CryptoAccount {

  private UUID uuid;
  private String userLogin;
  private CryptoCurrencyType cryptoCurrencyType;
  private BigDecimal amount = BigDecimal.ZERO;

  public CryptoAccount(UUID uuid, String userLogin, CryptoCurrencyType cryptoCurrencyType) {
    this.uuid = uuid;
    this.userLogin = userLogin;
    this.cryptoCurrencyType = cryptoCurrencyType;
  }
}

