package com.javaacademy.cryptowallet.storage;

import com.javaacademy.cryptowallet.entity.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserStorage {

  private final Map<String, User> userBd = new HashMap<>();

  public Optional<User> getUserByLogin(String login) {
    return Optional.ofNullable(userBd.get(login));
  }

  public void saveUser(User user) {
    if (userBd.containsKey(user.getLogin())) {
      throw new RuntimeException("Пользователь с таким логином уже существует.");
    }
    userBd.put(user.getLogin(), user);
  }
}
