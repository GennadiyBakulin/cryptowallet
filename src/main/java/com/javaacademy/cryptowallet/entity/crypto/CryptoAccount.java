package com.javaacademy.cryptowallet.entity.crypto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class CryptoAccount {

  private UUID uuid;
  private String userLogin;
  private CryptoCurrency cryptoCurrency;
  private BigDecimal amount = BigDecimal.ZERO;

  public CryptoAccount(UUID uuid, String userLogin, CryptoCurrency cryptoCurrency) {
    this.uuid = uuid;
    this.userLogin = userLogin;
    this.cryptoCurrency = cryptoCurrency;
  }
}

