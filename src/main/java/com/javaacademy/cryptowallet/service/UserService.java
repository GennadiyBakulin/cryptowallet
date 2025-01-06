package com.javaacademy.cryptowallet.service;

import com.javaacademy.cryptowallet.dto.user.CreateUserDto;
import com.javaacademy.cryptowallet.dto.user.ResetPasswordUserDto;
import com.javaacademy.cryptowallet.entity.User;
import com.javaacademy.cryptowallet.mapper.UserMapper;
import com.javaacademy.cryptowallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper mapper;

  public void saveUser(CreateUserDto newUser) {
    User user = mapper.convertToUser(newUser);
    userRepository.save(user);
  }

  public User getUserByLogin(String login) {
    return userRepository.getUserByLogin(login)
        .orElseThrow(() -> new RuntimeException("Пользователя с таким логином нет."));
  }

  public void resetPassword(ResetPasswordUserDto resetPassword) {
    User user = getUserByLogin(resetPassword.getLogin());

    if (!user.getPassword().equals(resetPassword.getOldPassword())) {
      throw new RuntimeException("Не верный старый пароль.");
    }
    user.setPassword(resetPassword.getNewPassword());
  }
}
