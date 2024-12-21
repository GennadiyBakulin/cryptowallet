package com.javaacademy.cryptowallet.service;

import com.javaacademy.cryptowallet.dto.UserDtoRegistrationNewUser;
import com.javaacademy.cryptowallet.dto.UserDtoResetPassword;
import com.javaacademy.cryptowallet.entity.User;
import com.javaacademy.cryptowallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public void saveUser(UserDtoRegistrationNewUser newUser) {
    User user = new User(newUser.getLogin(), newUser.getEmail(), newUser.getPassword());
    userRepository.save(user);
  }

  public User getUserByLogin(String login) {
    return userRepository.getUserByLogin(login)
        .orElseThrow(() -> new RuntimeException("Пользователя с таким логином нет."));
  }

  public void resetPassword(UserDtoResetPassword resetPassword) {
    User user = getUserByLogin(resetPassword.getLogin());

    if (!user.getPassword().equals(resetPassword.getOldPassword())) {
      throw new RuntimeException("Не верный старый пароль.");
    }
    user.setPassword(resetPassword.getNewPassword());
  }
}
