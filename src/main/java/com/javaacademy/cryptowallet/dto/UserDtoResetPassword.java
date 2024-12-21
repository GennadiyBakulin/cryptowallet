package com.javaacademy.cryptowallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDtoResetPassword {

  private String login;
  private String oldPassword;
  private String newPassword;
}
