package com.javaacademy.cryptowallet.controller;

import com.javaacademy.cryptowallet.service.UserService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public void registration(Map<String, String> newUser) {
    userService.saveUser(newUser);
  }

  @PostMapping("/reset-password")
  public void resetPassword(Map<String, String> map) {
    userService.resetPassword(map.get("login"), map.get("old_password"), map.get("new_password"));
  }
}
