package com.javaacademy.cryptowallet.storage;

import com.javaacademy.cryptowallet.entity.User;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class UserStorage {

  private final Map<String, User> userBd = new HashMap<>();

  public User getUserByLogin(String login) {
    if (userBd.containsKey(login)) {
      return userBd.get(login);
    }
    throw new RuntimeException("Пользователя с таким логином нет.");
  }

  public void saveUser(User user) {
    if (userBd.containsKey(user.getLogin())) {
      throw new RuntimeException("Пользователь с таким логином уже существует.");
    }
    userBd.put(user.getLogin(), user);
  }
}
