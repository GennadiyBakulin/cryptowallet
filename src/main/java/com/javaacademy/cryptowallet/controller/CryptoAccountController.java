package com.javaacademy.cryptowallet.controller;

import com.javaacademy.cryptowallet.dto.cryptoaccount.CreateNewCryptoAccountDto;
import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoAccount;
import com.javaacademy.cryptowallet.service.CryptoAccountService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cryptowallet")
public class CryptoAccountController {

  private final CryptoAccountService cryptoAccountService;

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public String createCryptoAccount(@RequestBody CreateNewCryptoAccountDto newCryptoAccount) {
    return cryptoAccountService.create(newCryptoAccount);
  }

  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  public List<CryptoAccount> getListCryptoAccount(
      @RequestParam(value = "username", required = false) String userLogin) {
    return cryptoAccountService.getAllCryptoAccountUser(userLogin);
  }
}
