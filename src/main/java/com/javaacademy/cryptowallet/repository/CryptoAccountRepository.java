package com.javaacademy.cryptowallet.repository;

import com.javaacademy.cryptowallet.entity.crypto.CryptoAccount;
import com.javaacademy.cryptowallet.storage.CryptoAccountStorage;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CryptoAccountRepository {

  private final CryptoAccountStorage cryptoAccountBd;

  public void save(CryptoAccount cryptoAccount) {
    if (cryptoAccountBd.getCryptoAccountBd().containsKey(cryptoAccount.getUuid())) {
      throw new RuntimeException("Криптосчет с указанным id уже существует.");
    }
    cryptoAccountBd.getCryptoAccountBd().put(cryptoAccount.getUuid(), cryptoAccount);
  }

  public Optional<CryptoAccount> getCryptoAccountByUuid(UUID uuid) {
    return Optional.ofNullable(cryptoAccountBd.getCryptoAccountBd().get(uuid));
  }

  public List<CryptoAccount> getAllCryptoAccountUser(String loginUser) {
    return cryptoAccountBd.getCryptoAccountBd().values().stream()
        .filter(cryptoAccount -> cryptoAccount.getUserLogin().equals(loginUser))
        .toList();
  }
}
