package com.javaacademy.cryptowallet.controller;

import com.javaacademy.cryptowallet.dto.cryptoaccount.ChangeAmountCryptoAccountDto;
import com.javaacademy.cryptowallet.dto.cryptoaccount.CreateCryptoAccountDto;
import com.javaacademy.cryptowallet.dto.cryptoaccount.CryptoAccountDto;
import com.javaacademy.cryptowallet.entity.User;
import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoAccount;
import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoCurrencyType;
import com.javaacademy.cryptowallet.storage.cryptoaccount.CryptoAccountStorage;
import com.javaacademy.cryptowallet.storage.user.UserStorage;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("local")
class CryptoAccountControllerTest {

  @Autowired
  private UserStorage userStorage;
  @Autowired
  private CryptoAccountStorage cryptoAccountStorage;

  private final User user = new User("user", "email@ya.ru", "password");
  private final CryptoAccount cryptoAccountBTC = new CryptoAccount(
      UUID.randomUUID(),
      user.getLogin(),
      CryptoCurrencyType.BTC);

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

  @BeforeEach
  public void putBd() {
    userStorage.getUserBd().clear();
    cryptoAccountStorage.getCryptoAccountBd().clear();
    userStorage.getUserBd().put(user.getLogin(), user);
    cryptoAccountStorage.getCryptoAccountBd().put(cryptoAccountBTC.getUuid(), cryptoAccountBTC);
  }

  @Test
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
  @DisplayName("Успешное пополнение криптосчета")
  void refillAccountSuccess() {
    BigDecimal refillOnRubles = BigDecimal.valueOf(1_000_000);
    BigDecimal cryptoAccountAmountStartOnBTC = cryptoAccountBTC.getAmount();
    ChangeAmountCryptoAccountDto changeAmountDto = new ChangeAmountCryptoAccountDto(
        cryptoAccountBTC.getUuid(),
        refillOnRubles
    );
    RestAssured.given(requestSpecification)
        .body(changeAmountDto)
        .post("/refill")
        .then()
        .statusCode(200);

    Assertions.assertEquals(cryptoAccountAmountStartOnBTC.add(BigDecimal.ONE),
        cryptoAccountBTC.getAmount());
  }

  @Test
  @DisplayName("Успешный вывод из криптосчета")
  void withdrawalFromAccountSuccess() {
    BigDecimal withdrawalOnRubles = BigDecimal.valueOf(500_000);
    cryptoAccountBTC.setAmount(BigDecimal.ONE);
    BigDecimal cryptoAccountAmountStartOnBTC = cryptoAccountBTC.getAmount();
    ChangeAmountCryptoAccountDto changeAmountDto = new ChangeAmountCryptoAccountDto(
        cryptoAccountBTC.getUuid(),
        withdrawalOnRubles
    );
    String result = RestAssured.given(requestSpecification)
        .body(changeAmountDto)
        .post("/withdrawal")
        .then()
        .spec(responseSpecification)
        .statusCode(200)
        .extract()
        .body()
        .asPrettyString();

    Assertions.assertEquals(cryptoAccountAmountStartOnBTC.subtract(BigDecimal.valueOf(0.5)),
        cryptoAccountBTC.getAmount());
    Assertions.assertEquals("Операция прошла успешно. Продано %s %s."
        .formatted(0.5, "bitcoin"), result);
  }

  @Test
  @DisplayName("Успешный показ баланса криптосчета")
  void showBalanceCryptoAccountSuccess() {
    cryptoAccountBTC.setAmount(BigDecimal.ONE);
    BigDecimal result2 = RestAssured.given(requestSpecification)
        .pathParam("id", cryptoAccountBTC.getUuid())
        .get("/balance/{id}")
        .then()
        .spec(responseSpecification)
        .statusCode(200)
        .extract()
        .body()
        .as(BigDecimal.class);
    Assertions.assertEquals(BigDecimal.valueOf(1_000_000), result2);
  }

  @Test
  @DisplayName("Успешный показ баланса всех криптосчетов")
  void showBalanceAllCryptoAccountSuccess() {
    CryptoAccount cryptoAccountETH = new CryptoAccount(UUID.randomUUID(), user.getLogin(),
        CryptoCurrencyType.ETH);
    CryptoAccount cryptoAccountSOL = new CryptoAccount(UUID.randomUUID(), user.getLogin(),
        CryptoCurrencyType.SOL);

    cryptoAccountBTC.setAmount(BigDecimal.ONE);
    cryptoAccountETH.setAmount(BigDecimal.ONE);
    cryptoAccountSOL.setAmount(BigDecimal.ONE);

    cryptoAccountStorage.getCryptoAccountBd().put(cryptoAccountETH.getUuid(), cryptoAccountETH);
    cryptoAccountStorage.getCryptoAccountBd().put(cryptoAccountSOL.getUuid(), cryptoAccountSOL);

    BigDecimal result = RestAssured.given(requestSpecification)
        .queryParam("username", user.getLogin())
        .get("/balance")
        .then()
        .spec(responseSpecification)
        .statusCode(200)
        .extract()
        .body()
        .as(BigDecimal.class);

    Assertions.assertEquals(BigDecimal.valueOf(3_000_000), result);
  }

  @Test
  @DisplayName("Успешное получение списка криптосчетов")
  void getListCryptoAccountSuccess() {
    CryptoAccount cryptoAccountETH = new CryptoAccount(UUID.randomUUID(), user.getLogin(),
        CryptoCurrencyType.ETH);
    CryptoAccount cryptoAccountSOL = new CryptoAccount(UUID.randomUUID(), user.getLogin(),
        CryptoCurrencyType.SOL);

    cryptoAccountStorage.getCryptoAccountBd().put(cryptoAccountETH.getUuid(), cryptoAccountETH);
    cryptoAccountStorage.getCryptoAccountBd().put(cryptoAccountSOL.getUuid(), cryptoAccountSOL);

    List<CryptoAccountDto> result = RestAssured.given(requestSpecification)
        .queryParam("username", user.getLogin())
        .get()
        .then()
        .spec(responseSpecification)
        .statusCode(200)
        .extract()
        .body()
        .as(new TypeRef<List<CryptoAccountDto>>() {
        });
    Assertions.assertEquals(3, result.size());
  }
}