package com.javaacademy.cryptowallet.controller;

import com.javaacademy.cryptowallet.service.UserService;
import java.util.Map;
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
  public void registration(@RequestBody Map<String, String> newUser) {
    userService.saveUser(newUser);
  }

  @PostMapping("/reset-password")
  public void resetPassword(@RequestBody Map<String, String> updatePasswordMap) {
    userService.resetPassword(updatePasswordMap.get("login"), updatePasswordMap.get("old_password"),
        updatePasswordMap.get("new_password"));
  }
}
