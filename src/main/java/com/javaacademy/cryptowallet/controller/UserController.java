package com.javaacademy.cryptowallet.controller;

import com.javaacademy.cryptowallet.dto.UserDtoRegistrationNewUser;
import com.javaacademy.cryptowallet.dto.UserDtoResetPassword;
import com.javaacademy.cryptowallet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public void registration(@RequestBody UserDtoRegistrationNewUser newUser) {
    userService.saveUser(newUser);
  }

  @PostMapping("/reset-password")
  public void resetPassword(@RequestBody UserDtoResetPassword resetPassword) {
    userService.resetPassword(resetPassword);
  }
}
