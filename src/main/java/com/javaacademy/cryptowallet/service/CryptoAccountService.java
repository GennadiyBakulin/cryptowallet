package com.javaacademy.cryptowallet.service;

import com.javaacademy.cryptowallet.dto.cryptoaccount.CreateNewCryptoAccountDto;
import com.javaacademy.cryptowallet.entity.User;
import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoAccount;
import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoCurrency;
import com.javaacademy.cryptowallet.repository.CryptoAccountRepository;
import com.javaacademy.cryptowallet.service.convert.ConvertCryptocurrencyToUsdService;
import com.javaacademy.cryptowallet.service.convert.ConvertBetweenDollarsAndRublesService;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CryptoAccountService {

  private final CryptoAccountRepository cryptoAccountRepository;
  private final UserService userService;
  private final ConvertCryptocurrencyToUsdService convertCryptocurrencyToUsdService;
  private final ConvertBetweenDollarsAndRublesService convertBetweenDollarsAndRublesService;

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

  public CryptoAccount findCryptoAccountByUuid(UUID uuid) {
    return cryptoAccountRepository.findCryptoAccountByUuid(uuid)
        .orElseThrow(() -> new RuntimeException("Криптосчет с таким uuid не найден."));
  }

  public List<CryptoAccount> findAllCryptoAccountUser(String userLogin) {
    User user = userService.getUserByLogin(userLogin);
    return cryptoAccountRepository.findAllCryptoAccountUser(user.getLogin());
  }

  public void depositAccountOnRubbles(UUID uuid, BigDecimal amountRubles) throws IOException {
    CryptoAccount cryptoAccount = findCryptoAccountByUuid(uuid);
    CryptoCurrency cryptoCurrency = cryptoAccount.getCryptoCurrency();
    BigDecimal currentRateInDollars = convertCryptocurrencyToUsdService
        .convertCryptocurrencyToUsd(cryptoCurrency);
    BigDecimal amountDollars = convertBetweenDollarsAndRublesService.convertRublesToDollars(amountRubles);
    BigDecimal amountCryptoCurrency = amountDollars.divide(currentRateInDollars);
    cryptoAccount.setAmount(cryptoAccount.getAmount().add(amountCryptoCurrency));
  }

  public void takeRublesFromAccount(UUID uuid, BigDecimal amountRubles) throws IOException {
    CryptoAccount cryptoAccount = findCryptoAccountByUuid(uuid);
    CryptoCurrency cryptoCurrency = cryptoAccount.getCryptoCurrency();
    BigDecimal currentRateInDollars = convertCryptocurrencyToUsdService
        .convertCryptocurrencyToUsd(cryptoCurrency);
    BigDecimal amountDollars = convertBetweenDollarsAndRublesService.convertRublesToDollars(amountRubles);
    BigDecimal amountCryptoCurrency = amountDollars.divide(currentRateInDollars);
    if (cryptoAccount.getAmount().compareTo(amountCryptoCurrency) < 0) {
      throw new RuntimeException("На счете %s недостаточно средств.".formatted(uuid));
    }
    cryptoAccount.setAmount(cryptoAccount.getAmount().subtract(amountCryptoCurrency));
    log.info("Операция прошла успешно.\nПродано %s %s."
        .formatted(amountCryptoCurrency, cryptoCurrency.getFullName()));
  }

  public BigDecimal showBalanceAccountInRubles(UUID uuid) throws IOException {
    CryptoAccount cryptoAccount = findCryptoAccountByUuid(uuid);
    CryptoCurrency cryptoCurrency = cryptoAccount.getCryptoCurrency();
    BigDecimal currentRateInDollars = convertCryptocurrencyToUsdService
        .convertCryptocurrencyToUsd(cryptoCurrency);
    BigDecimal amountDollars = cryptoAccount.getAmount().multiply(currentRateInDollars);
    return convertBetweenDollarsAndRublesService.convertDollarsToRubles(amountDollars);
  }

  public BigDecimal showBalanceAllAccountsInRubles(String userLogin) throws IOException {
    List<CryptoAccount> listCryptoAccountUser = findAllCryptoAccountUser(userLogin);
    return listCryptoAccountUser.stream()
        .map(CryptoAccount::getAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
