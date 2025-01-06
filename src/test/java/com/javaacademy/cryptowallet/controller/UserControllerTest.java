package com.javaacademy.cryptowallet.controller;

import com.javaacademy.cryptowallet.dto.user.CreateUserDto;
import com.javaacademy.cryptowallet.dto.user.ResetPasswordUserDto;
import com.javaacademy.cryptowallet.entity.User;
import com.javaacademy.cryptowallet.storage.user.UserStorage;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("local")
@TestMethodOrder(OrderAnnotation.class)
class UserControllerTest {

  @Autowired
  private UserStorage userStorage;

  private final int port = 8008;
  private final RequestSpecification requestSpecification = new RequestSpecBuilder()
      .setPort(port)
      .setBasePath("/user")
      .log(LogDetail.ALL)
      .build()
      .contentType("application/json");

  @Test
  @Order(1)
  @DisplayName("Успешная регистрация нового пользователя")
  public void createUserSuccess() {
    CreateUserDto user = new CreateUserDto("login", "email@ya.ru", "password");
    RestAssured.given(requestSpecification)
        .body(user)
        .post("/signup")
        .then()
        .statusCode(201);

    Assertions.assertEquals(1, userStorage.getUserBd().size());
    Assertions.assertTrue(userStorage.getUserBd().containsKey(user.getLogin()));
    User result = userStorage.getUserBd().get(user.getLogin());
    Assertions.assertEquals("login", result.getLogin());
    Assertions.assertEquals("email@ya.ru", result.getEmail());
    Assertions.assertEquals("password", result.getPassword());
  }

  @Test
  @Order(2)
  @DisplayName("Успешная смена пароля у пользователя")
  public void resetPasswordSuccess() {
    User user = userStorage.getUserBd().get("login");
    String login = user.getLogin();
    String email = user.getEmail();
    String password = user.getPassword();
    ResetPasswordUserDto resetPasswordUserDto = new ResetPasswordUserDto(
        login,
        password,
        "new_password");
    RestAssured.given(requestSpecification)
        .body(resetPasswordUserDto)
        .post("/reset-password")
        .then()
        .statusCode(200);

    Assertions.assertEquals(1, userStorage.getUserBd().size());
    User result = userStorage.getUserBd().get(login);
    Assertions.assertEquals(login, result.getLogin());
    Assertions.assertEquals(email, result.getEmail());
    Assertions.assertEquals("new_password", result.getPassword());
    Assertions.assertNotEquals(password, result.getPassword());
  }
}
