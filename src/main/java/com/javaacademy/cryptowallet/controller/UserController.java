package com.javaacademy.cryptowallet.controller;

import com.javaacademy.cryptowallet.dto.user.UserDto;
import com.javaacademy.cryptowallet.dto.user.UserResetPasswordDto;
import com.javaacademy.cryptowallet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  @ResponseStatus(code = HttpStatus.CREATED)
  public void registration(@RequestBody UserDto newUser) {
    userService.saveUser(newUser);
  }

  @PostMapping("/reset-password")
  @ResponseStatus(code = HttpStatus.OK)
  public void resetPassword(@RequestBody UserResetPasswordDto resetPassword) {
    userService.resetPassword(resetPassword);
  }
}
