package com.javaacademy.cryptowallet.controller;

import com.javaacademy.cryptowallet.entity.crypto.CryptoAccount;
import com.javaacademy.cryptowallet.entity.crypto.CryptoCurrency;
import com.javaacademy.cryptowallet.service.CryptoAccountService;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cryptowallet")
public class CryptoAccountController {

  private final CryptoAccountService cryptoAccountService;

  @PostMapping
  public String createCryptoAccount(@RequestBody Map<String, String> newCryptoAccount) {
    String loginUser = newCryptoAccount.get("username");
    String cryptoType = newCryptoAccount.get("crypto_type");

    CryptoCurrency cryptoCurrency = Arrays.stream(CryptoCurrency.values())
        .filter(currency -> Objects.equals(currency.getNameCryptoCurrency(),
            cryptoType))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Введено наименование неизвестной криптовалюты."));

    CryptoAccount cryptoAccount = cryptoAccountService.create(loginUser, cryptoCurrency);
    return cryptoAccount.getUuid().toString();
  }

  @GetMapping
  public List<CryptoAccount> getListCryptoAccount(@RequestParam(required = false) String username) {
    return cryptoAccountService.getAllCryptoAccountUser(username);
  }
}
