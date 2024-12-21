package com.javaacademy.cryptowallet.entity.crypto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CryptoCurrency {
  BTC("bitcoin"),
  ETH("ethereum"),
  SOL("solana");

  private final String fullName;
}
