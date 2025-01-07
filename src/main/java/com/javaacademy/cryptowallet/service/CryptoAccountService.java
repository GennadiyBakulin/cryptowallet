package com.javaacademy.cryptowallet.service;

import com.javaacademy.cryptowallet.dto.cryptoaccount.CreateCryptoAccountDto;
import com.javaacademy.cryptowallet.dto.cryptoaccount.CryptoAccountDto;
import com.javaacademy.cryptowallet.entity.User;
import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoAccount;
import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoCurrencyType;
import com.javaacademy.cryptowallet.mapper.CryptoAccountMapper;
import com.javaacademy.cryptowallet.repository.CryptoAccountRepository;
import com.javaacademy.cryptowallet.service.integration.ConvertBetweenDollarsAndRublesService;
import com.javaacademy.cryptowallet.service.integration.ConvertCryptocurrencyToUsdService;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CryptoAccountService {

  private final MathContext mathContext;
  private final CryptoAccountRepository cryptoAccountRepository;
  private final UserService userService;
  private final ConvertCryptocurrencyToUsdService convertCryptocurrencyToUsdService;
  private final ConvertBetweenDollarsAndRublesService convertBetweenDollarsAndRublesService;
  private final CryptoAccountMapper mapper;

  public UUID create(CreateCryptoAccountDto newCreateCryptoAccountDto) {
    User user = userService.getUserByLogin(newCreateCryptoAccountDto.getLogin());

    CryptoCurrencyType cryptoCurrencyType = Arrays.stream(CryptoCurrencyType.values())
        .filter(currency -> currency == newCreateCryptoAccountDto.getCryptoCurrencyType())
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Введено наименование неизвестной криптовалюты."));

    CryptoAccount newCryptoAccount = new CryptoAccount(UUID.randomUUID(), user.getLogin(),
        cryptoCurrencyType);
    cryptoAccountRepository.save(newCryptoAccount);
    return newCryptoAccount.getUuid();
  }

  public CryptoAccount findCryptoAccountByUuid(UUID uuid) {
    return cryptoAccountRepository.findCryptoAccountByUuid(uuid)
        .orElseThrow(() -> new RuntimeException("Криптосчет с таким uuid не найден."));
  }

  public List<CryptoAccountDto> getAllCryptoAccountUser(String userLogin) {
    User user = userService.getUserByLogin(userLogin);
    List<CryptoAccount> allCryptoAccountUser = cryptoAccountRepository.findAllCryptoAccountUser(
        user.getLogin());
    return allCryptoAccountUser.stream()
        .map(mapper::convertToDto)
        .toList();
  }

  public void refillAccountOnRubbles(UUID uuid, BigDecimal amountRubles) throws IOException {
    CryptoAccount cryptoAccount = findCryptoAccountByUuid(uuid);
    CryptoCurrencyType cryptoCurrencyType = cryptoAccount.getCryptoCurrencyType();
    BigDecimal currentRateInDollars = convertCryptocurrencyToUsdService
        .convertCryptocurrencyToUsd(cryptoCurrencyType);
    BigDecimal amountDollars = convertBetweenDollarsAndRublesService.convertRublesToDollars(
        amountRubles);
    BigDecimal amountCryptoCurrency = amountDollars.divide(currentRateInDollars, mathContext);
    cryptoAccount.setAmount(cryptoAccount.getAmount().add(amountCryptoCurrency, mathContext));
  }

  public String withdrawalRublesFromAccount(UUID uuid, BigDecimal amountRubles) throws IOException {
    CryptoAccount cryptoAccount = findCryptoAccountByUuid(uuid);
    CryptoCurrencyType cryptoCurrencyType = cryptoAccount.getCryptoCurrencyType();
    BigDecimal currentRateInDollars = convertCryptocurrencyToUsdService
        .convertCryptocurrencyToUsd(cryptoCurrencyType);
    BigDecimal amountDollars = convertBetweenDollarsAndRublesService.convertRublesToDollars(
        amountRubles);
    BigDecimal amountCryptoCurrency = amountDollars.divide(currentRateInDollars, mathContext);
    if (cryptoAccount.getAmount().compareTo(amountCryptoCurrency) < 0) {
      throw new RuntimeException("На счете %s недостаточно средств.".formatted(uuid));
    }
    cryptoAccount.setAmount(cryptoAccount.getAmount().subtract(amountCryptoCurrency, mathContext));
    return "Операция прошла успешно. Продано %s %s."
        .formatted(amountCryptoCurrency, cryptoCurrencyType.getFullName());
  }

  public BigDecimal getBalanceAccountInRubles(UUID uuid) throws IOException {
    CryptoAccount cryptoAccount = findCryptoAccountByUuid(uuid);
    CryptoCurrencyType cryptoCurrencyType = cryptoAccount.getCryptoCurrencyType();
    BigDecimal currentRateInDollars = convertCryptocurrencyToUsdService
        .convertCryptocurrencyToUsd(cryptoCurrencyType);
    BigDecimal amountDollars = cryptoAccount.getAmount()
        .multiply(currentRateInDollars, mathContext);
    return convertBetweenDollarsAndRublesService.convertDollarsToRubles(amountDollars);
  }

  public BigDecimal getBalanceAllAccountsInRubles(String userLogin) {
    List<CryptoAccount> listCryptoAccountUser = cryptoAccountRepository.findAllCryptoAccountUser(
        userLogin);
    return listCryptoAccountUser.stream()
        .map(cryptoAccount -> {
          try {
            return getBalanceAccountInRubles(cryptoAccount.getUuid());
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        })
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
