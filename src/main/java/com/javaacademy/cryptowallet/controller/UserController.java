package com.javaacademy.cryptowallet.controller;

import com.javaacademy.cryptowallet.dto.user.CreateUserDto;
import com.javaacademy.cryptowallet.dto.user.ResetPasswordUserDto;
import com.javaacademy.cryptowallet.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Контроллер пользователя", description = "Контроллер для работы с пользователем")
public class UserController {

  private final UserService userService;

  @Operation(
      summary = "Регистрация пользователя",
      description = "Регистрирует нового пользователя"
  )
  @ApiResponse(responseCode = "201", description = "Создан")
  @PostMapping("/signup")
  @ResponseStatus(code = HttpStatus.CREATED)
  public void signup(@RequestBody CreateUserDto newUser) {
    userService.saveUser(newUser);
  }

  @Operation(
      summary = "Смена пароля пользователя",
      description = "Меняет пароль у зарегистрированного пользователя"
  )
  @ApiResponse(responseCode = "200", description = "Успешно")
  @PostMapping("/reset-password")
  @ResponseStatus(code = HttpStatus.OK)
  public void resetPassword(@RequestBody ResetPasswordUserDto resetPassword) {
    userService.resetPassword(resetPassword);
  }
}
