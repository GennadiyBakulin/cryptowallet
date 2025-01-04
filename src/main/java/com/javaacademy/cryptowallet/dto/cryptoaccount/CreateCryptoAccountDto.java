package com.javaacademy.cryptowallet.dto.cryptoaccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateCryptoAccountDto {

  @JsonProperty("username")
  private final String userLogin;
  @JsonProperty("crypto_type")
  private final String cryptoType;
}
