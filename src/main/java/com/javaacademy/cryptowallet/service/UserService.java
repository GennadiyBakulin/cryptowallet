package com.javaacademy.cryptowallet.service;

import com.javaacademy.cryptowallet.entity.User;
import com.javaacademy.cryptowallet.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserStorage userStorage;

  public User getUserByLogin(String login) {
    return userStorage.getUserByLogin(login)
        .orElseThrow(() -> new RuntimeException("Пользователя с таким логином нет."));
  }

  public void saveUser(User user) {
    userStorage.saveUser(user);
  }

  public void resetPassword(String login, String oldPassword, String newPassword) {
    User user = getUserByLogin(login);

    if (user.getPassword().equals(oldPassword)) {
      throw new RuntimeException("Не верный старый пароль.");
    }
    user.setPassword(newPassword);
  }
}
