package com.javaacademy.cryptowallet.mapper;

import com.javaacademy.cryptowallet.dto.cryptoaccount.CryptoAccountDto;
import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoAccount;
import org.springframework.stereotype.Component;

@Component
public class CryptoAccountMapper {

  public CryptoAccountDto convertToDto(CryptoAccount cryptoAccount) {
    return new CryptoAccountDto(cryptoAccount.getUserLogin(),
        cryptoAccount.getCryptoCurrencyType());
  }
}
