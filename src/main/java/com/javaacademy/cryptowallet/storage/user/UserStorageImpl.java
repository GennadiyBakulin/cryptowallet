package com.javaacademy.cryptowallet.storage.user;

import com.javaacademy.cryptowallet.entity.User;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class UserStorageImpl implements UserStorage {

  private final Map<String, User> userBd = new HashMap<>();

  @Override
  public Map<String, User> getUserBd() {
    return userBd;
  }
}
