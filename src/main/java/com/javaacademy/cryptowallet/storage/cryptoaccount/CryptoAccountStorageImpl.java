package com.javaacademy.cryptowallet.storage.cryptoaccount;

import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoAccount;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class CryptoAccountStorageImpl implements CryptoAccountStorage {

  private final Map<UUID, CryptoAccount> cryptoAccountBd = new HashMap<>();

  @Override
  public Map<UUID, CryptoAccount> getCryptoAccountBd() {
    return cryptoAccountBd;
  }
}
