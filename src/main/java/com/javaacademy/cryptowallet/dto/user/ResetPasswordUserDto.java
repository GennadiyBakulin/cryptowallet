package com.javaacademy.cryptowallet.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResetPasswordUserDto {

  private final String login;
  @JsonProperty("old_password")
  private final String oldPassword;
  @JsonProperty("new_password")
  private final String newPassword;
}
