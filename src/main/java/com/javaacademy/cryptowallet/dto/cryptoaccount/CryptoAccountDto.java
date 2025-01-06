package com.javaacademy.cryptowallet.dto.cryptoaccount;

import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoCurrencyType;
import lombok.Data;

@Data
public class CryptoAccountDto {

  private final String login;
  private final CryptoCurrencyType cryptoCurrencyType;
}
