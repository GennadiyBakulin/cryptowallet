package com.javaacademy.cryptowallet.storage;

import com.javaacademy.cryptowallet.entity.crypto.CryptoAccount;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class CryptoAccountStorage {

  @Getter
  private final Map<UUID, CryptoAccount> cryptoAccountBd = new HashMap<>();
}
