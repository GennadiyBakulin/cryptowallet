package com.javaacademy.cryptowallet.entity.cryptoaccount;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CryptoCurrencyType {
  BTC("bitcoin"),
  ETH("ethereum"),
  SOL("solana");

  private final String fullName;
}
