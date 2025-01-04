package com.javaacademy.cryptowallet.controller;

import com.javaacademy.cryptowallet.dto.user.CreateUserDto;
import com.javaacademy.cryptowallet.dto.user.ResetPasswordUserDto;
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
  public void registration(@RequestBody CreateUserDto newUser) {
    userService.saveUser(newUser);
  }

  @PostMapping("/reset-password")
  @ResponseStatus(code = HttpStatus.OK)
  public void resetPassword(@RequestBody ResetPasswordUserDto resetPassword) {
    userService.resetPassword(resetPassword);
  }
}
