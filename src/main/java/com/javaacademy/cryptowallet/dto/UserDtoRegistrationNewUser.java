package com.javaacademy.cryptowallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDtoRegistrationNewUser {

  private String login;
  private String email;
  private String password;
}
