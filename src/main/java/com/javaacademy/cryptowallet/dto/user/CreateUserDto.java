package com.javaacademy.cryptowallet.dto.user;

import lombok.Data;

@Data
public class CreateUserDto {

  private final String login;
  private final String email;
  private final String password;
}
