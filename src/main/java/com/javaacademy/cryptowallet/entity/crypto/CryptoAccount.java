package com.javaacademy.cryptowallet.entity.crypto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class CryptoAccount {

  private UUID uuid;
  private String loginUser;
  private CryptoCurrency cryptoCurrency;
  private BigDecimal amount = BigDecimal.ZERO;

  public CryptoAccount(UUID uuid, String loginUser, CryptoCurrency cryptoCurrency) {
    this.uuid = uuid;
    this.loginUser = loginUser;
    this.cryptoCurrency = cryptoCurrency;
  }
}
