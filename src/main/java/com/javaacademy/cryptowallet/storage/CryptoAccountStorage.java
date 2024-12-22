package com.javaacademy.cryptowallet.storage;

import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoAccount;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CryptoAccountStorage {

  private final Map<UUID, CryptoAccount> cryptoAccountBd = new HashMap<>();
}
