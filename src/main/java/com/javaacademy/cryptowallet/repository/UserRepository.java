package com.javaacademy.cryptowallet.repository;

import com.javaacademy.cryptowallet.entity.User;
import com.javaacademy.cryptowallet.storage.user.UserStorage;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

  private final UserStorage userStorage;

  public void save(User user) {
    if (userStorage.getUserBd().containsKey(user.getLogin())) {
      throw new RuntimeException("Пользователь с таким логином уже существует.");
    }
    userStorage.getUserBd().put(user.getLogin(), user);
  }

  public Optional<User> getUserByLogin(String login) {
    return Optional.ofNullable(userStorage.getUserBd().get(login));
  }
}
