package com.javaacademy.cryptowallet.service;

import com.javaacademy.cryptowallet.dto.cryptoaccount.CreateNewCryptoAccountDto;
import com.javaacademy.cryptowallet.entity.User;
import com.javaacademy.cryptowallet.entity.crypto.CryptoAccount;
import com.javaacademy.cryptowallet.entity.crypto.CryptoCurrency;
import com.javaacademy.cryptowallet.repository.CryptoAccountRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CryptoAccountService {

  private final CryptoAccountRepository cryptoAccountRepository;
  private final UserService userService;

  public String create(CreateNewCryptoAccountDto newCryptoAccountDto) {
    User user = userService.getUserByLogin(newCryptoAccountDto.getUserLogin());

    CryptoCurrency cryptoCurrency = Arrays.stream(CryptoCurrency.values())
        .filter(currency -> Objects.equals(currency.getFullName(),
            newCryptoAccountDto.getCryptoType()))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Введено наименование неизвестной криптовалюты."));

    CryptoAccount newCryptoAccount = new CryptoAccount(UUID.randomUUID(), user.getLogin(),
        cryptoCurrency);
    cryptoAccountRepository.save(newCryptoAccount);
    System.out.println(newCryptoAccount.getUuid());
    return newCryptoAccount.getUuid().toString();
  }

  public CryptoAccount getCryptoAccountByUuid(UUID uuid) {
    return cryptoAccountRepository.getCryptoAccountByUuid(uuid)
        .orElseThrow(() -> new RuntimeException("Криптосчет с таким uuid не найдено."));
  }

  public List<CryptoAccount> getAllCryptoAccountUser(String userLogin) {
    User user = userService.getUserByLogin(userLogin);
    return cryptoAccountRepository.getAllCryptoAccountUser(user.getLogin());
  }
}
