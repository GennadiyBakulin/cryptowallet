package com.javaacademy.cryptowallet.entity.crypto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class CryptoAccount {

  private UUID uuid;
  private String loginUser;
  private CryptoCurrency cryptoCurrency;
  private BigDecimal amount;
}
