package com.javaacademy.cryptowallet.controller;

import com.javaacademy.cryptowallet.dto.cryptoaccount.ChangeAmountCryptoAccountDto;
import com.javaacademy.cryptowallet.dto.cryptoaccount.CryptoAccountDto;
import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoAccount;
import com.javaacademy.cryptowallet.service.CryptoAccountService;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public UUID createCryptoAccount(@RequestBody CryptoAccountDto newCryptoAccount) {
    return cryptoAccountService.create(newCryptoAccount);
  }

  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  public List<CryptoAccount> getListCryptoAccount(
      @RequestParam(value = "username") String userLogin) {
    return cryptoAccountService.findAllCryptoAccountUser(userLogin);
  }

  @PostMapping("/refill")
  @ResponseStatus(code = HttpStatus.OK)
  public void refillAccount(@RequestBody ChangeAmountCryptoAccountDto refill) throws IOException {
    cryptoAccountService.refillAccountOnRubbles(refill.getUuid(), refill.getAmountRubles());
  }

  @PostMapping("/withdrawal")
  @ResponseStatus(code = HttpStatus.OK)
  public void withdrawalRublesFromAccount(@RequestBody ChangeAmountCryptoAccountDto withdrawal)
      throws IOException {
    cryptoAccountService.withdrawalRublesFromAccount(withdrawal.getUuid(),
        withdrawal.getAmountRubles());
  }

  @GetMapping("/balance/{id}")
  @ResponseStatus(code = HttpStatus.OK)
  public BigDecimal showBalanceAccountInRubles(@PathVariable("id") UUID uuid) throws IOException {
    return cryptoAccountService.getBalanceAccountInRubles(uuid);
  }

  @GetMapping("/balance")
  @ResponseStatus(code = HttpStatus.OK)
  public BigDecimal showBalanceAccountInRubles(@RequestParam("username") String login) {
    return cryptoAccountService.getBalanceAllAccountsInRubles(login);
  }
}
