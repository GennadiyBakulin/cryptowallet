package com.javaacademy.cryptowallet.service;

import com.javaacademy.cryptowallet.entity.crypto.CryptoAccount;
import com.javaacademy.cryptowallet.entity.crypto.CryptoCurrency;
import com.javaacademy.cryptowallet.storage.CryptoAccountStorage;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CryptoAccountService {

  private final CryptoAccountStorage cryptoAccountStorage;
  private final UserService userService;

  public CryptoAccount getCryptoAccountByUuid(UUID uuid) {
    return cryptoAccountStorage.getCryptoAccountByUuid(uuid)
        .orElseThrow(() -> new RuntimeException("Криптосчет с таким uuid не найдено."));
  }

  public List<CryptoAccount> getAllCryptoAccountUser(String loginUser) {
    userService.getUserByLogin(loginUser);
    return cryptoAccountStorage.getAllCryptoAccountUser(loginUser);
  }

  public void create(String loginUser, CryptoCurrency cryptoCurrency) {
    userService.getUserByLogin(loginUser);
    CryptoAccount cryptoAccount = new CryptoAccount(UUID.randomUUID(), loginUser, cryptoCurrency);
    cryptoAccountStorage.save(cryptoAccount);
  }
}
