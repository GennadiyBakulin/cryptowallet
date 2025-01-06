package com.javaacademy.cryptowallet.controller;

import com.javaacademy.cryptowallet.dto.cryptoaccount.CreateCryptoAccountDto;
import com.javaacademy.cryptowallet.entity.User;
import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoAccount;
import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoCurrencyType;
import com.javaacademy.cryptowallet.storage.cryptoaccount.CryptoAccountStorage;
import com.javaacademy.cryptowallet.storage.user.UserStorage;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.UUID;
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
class CryptoAccountControllerTest {

  @Autowired
  private UserStorage userStorage;
  @Autowired
  private CryptoAccountStorage cryptoAccountStorage;

  private final User user = new User("user", "email@ya.ru", "password");

  private final int port = 8008;
  private final RequestSpecification requestSpecification = new RequestSpecBuilder()
      .setPort(port)
      .setBasePath("/cryptowallet")
      .log(LogDetail.ALL)
      .build()
      .contentType("application/json");

  private final ResponseSpecification responseSpecification = new ResponseSpecBuilder()
      .log(LogDetail.ALL)
      .build();

  @PostConstruct
  public void createUser() {
    userStorage.getUserBd().put(user.getLogin(), user);
  }

  @Test
  @Order(1)
  @DisplayName("Успешное создание нового криптосчета")
  void createCryptoAccountSuccess() {
    CreateCryptoAccountDto cryptoAccountDto = new CreateCryptoAccountDto(
        user.getLogin(),
        CryptoCurrencyType.BTC);

    UUID uuid = RestAssured.given(requestSpecification)
        .body(cryptoAccountDto)
        .post()
        .then()
        .statusCode(201)
        .extract()
        .body()
        .as(UUID.class);

    Assertions.assertTrue(cryptoAccountStorage.getCryptoAccountBd().containsKey(uuid));
    CryptoAccount cryptoAccount = cryptoAccountStorage.getCryptoAccountBd().get(uuid);
    Assertions.assertEquals(cryptoAccountDto.getLogin(), cryptoAccount.getUserLogin());
    Assertions.assertEquals(cryptoAccountDto.getCryptoCurrencyType(),
        cryptoAccount.getCryptoCurrencyType());
    Assertions.assertEquals(BigDecimal.ZERO, cryptoAccount.getAmount());
  }

  @Test
  @Order(2)
  @DisplayName("Успешное пополнение криптосчета")
  void refillAccountSuccess() {
  }

  @Test
  @Order(3)
  @DisplayName("Успешный вывод из криптосчета")
  void withdrawalFromAccountSuccess() {
  }

  @Test
  @Order(4)
  @DisplayName("Успешный показ баланса криптосчета")
  void showBalanceCryptoAccountSuccess() {
  }

  @Test
  @Order(5)
  @DisplayName("Успешный показ баланса всех криптосчетов")
  void showBalanceAllCryptoAccountSuccess() {
  }

  @Test
  @Order(6)
  @DisplayName("Успешное получение списка криптосчетов")
  void getListCryptoAccountSuccess() {
  }
}