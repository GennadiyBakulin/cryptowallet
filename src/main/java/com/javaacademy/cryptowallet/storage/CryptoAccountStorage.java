package com.javaacademy.cryptowallet.storage;

import com.javaacademy.cryptowallet.entity.crypto.CryptoAccount;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CryptoAccountStorage {

  private final Map<UUID, CryptoAccount> cryptoAccountBd = new HashMap<>();

  public void save(CryptoAccount cryptoAccount) {
    if (cryptoAccountBd.containsKey(cryptoAccount.getUuid())) {
      throw new RuntimeException("Криптосчет с указанным id уже существует.");
    }
    cryptoAccountBd.put(cryptoAccount.getUuid(), cryptoAccount);
  }

  public Optional<CryptoAccount> getCryptoAccountByUuid(UUID uuid) {
    return Optional.ofNullable(cryptoAccountBd.get(uuid));
  }

  public List<CryptoAccount> getAllCryptoAccountUser(String loginUser) {
    return cryptoAccountBd.values().stream()
        .filter(cryptoAccount -> cryptoAccount.getLoginUser().equals(loginUser))
        .toList();
  }

}
