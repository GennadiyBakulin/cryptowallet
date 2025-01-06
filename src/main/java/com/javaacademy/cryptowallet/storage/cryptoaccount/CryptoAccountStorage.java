package com.javaacademy.cryptowallet.storage.cryptoaccount;

import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoAccount;
import java.util.Map;
import java.util.UUID;

public interface CryptoAccountStorage {

  Map<UUID, CryptoAccount> getCryptoAccountBd();
}
