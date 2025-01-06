package com.javaacademy.cryptowallet.repository;

import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoAccount;
import com.javaacademy.cryptowallet.storage.cryptoaccount.CryptoAccountStorage;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CryptoAccountRepository {

  private final CryptoAccountStorage cryptoAccountStorage;

  public void save(CryptoAccount cryptoAccount) {
    if (cryptoAccountStorage.getCryptoAccountBd().containsKey(cryptoAccount.getUuid())) {
      throw new RuntimeException("Криптосчет с указанным id уже существует.");
    }
    cryptoAccountStorage.getCryptoAccountBd().put(cryptoAccount.getUuid(), cryptoAccount);
  }

  public Optional<CryptoAccount> findCryptoAccountByUuid(UUID uuid) {
    return Optional.ofNullable(cryptoAccountStorage.getCryptoAccountBd().get(uuid));
  }

  public List<CryptoAccount> findAllCryptoAccountUser(String loginUser) {
    return cryptoAccountStorage.getCryptoAccountBd().values().stream()
        .filter(cryptoAccount -> cryptoAccount.getUserLogin().equals(loginUser))
        .toList();
  }
}
