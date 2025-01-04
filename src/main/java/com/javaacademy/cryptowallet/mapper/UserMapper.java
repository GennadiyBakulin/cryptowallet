package com.javaacademy.cryptowallet.mapper;

import com.javaacademy.cryptowallet.dto.user.CreateUserDto;
import com.javaacademy.cryptowallet.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public User convertToUser(CreateUserDto newUser) {
    return new User(newUser.getLogin(), newUser.getEmail(), newUser.getPassword());
  }
}
