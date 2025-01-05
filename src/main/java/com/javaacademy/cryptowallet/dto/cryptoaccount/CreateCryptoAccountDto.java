package com.javaacademy.cryptowallet.dto.cryptoaccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoCurrencyType;
import lombok.Data;

@Data
public class CreateCryptoAccountDto {

  @JsonProperty("username")
  private final String login;
  @JsonProperty("crypto_type")
  private final CryptoCurrencyType cryptoCurrencyType;
}
