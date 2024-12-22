package com.javaacademy.cryptowallet.dto.cryptoaccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class ChangeAmountCryptoAccountDto {

  @JsonProperty("account_id")
  private final UUID uuid;
  @JsonProperty("rubles_amount")
  private final BigDecimal amountRubles;
}
