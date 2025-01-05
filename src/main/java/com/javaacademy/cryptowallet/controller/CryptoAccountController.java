package com.javaacademy.cryptowallet.controller;

import com.javaacademy.cryptowallet.dto.cryptoaccount.ChangeAmountCryptoAccountDto;
import com.javaacademy.cryptowallet.dto.cryptoaccount.CreateCryptoAccountDto;
import com.javaacademy.cryptowallet.entity.cryptoaccount.CryptoAccount;
import com.javaacademy.cryptowallet.service.CryptoAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cryptowallet")
@Tag(name = "Контроллер криптосчета",
    description = "Получение запросов и отправка ответов при работе с криптокошельками пользователей")
public class CryptoAccountController {

  private final CryptoAccountService cryptoAccountService;

  @Operation(summary = "Создание криптосчета",
      description = "Создает криптосчет для зарегистрированного пользователя")
  @ApiResponses({
      @ApiResponse(responseCode = "202", description = "Создан",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = UUID.class))})})
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public UUID createCryptoAccount(@RequestBody CreateCryptoAccountDto newCryptoAccount) {
    return cryptoAccountService.create(newCryptoAccount);
  }

  @Operation(summary = "Получение всех криптосчетов пользователя",
      description = "Получение всех криптосчетов по логину пользователя")
  @ApiResponse(responseCode = "200", description = "Успешно",
      content = {@Content(mediaType = "application/json",
          array = @ArraySchema(schema = @Schema(implementation = CryptoAccount.class)))})
  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  public List<CryptoAccount> getListCryptoAccount(
      @RequestParam(value = "username") String userLogin) {
    return cryptoAccountService.findAllCryptoAccountUser(userLogin);
  }

  @Operation(summary = "Пополнение криптосчета",
      description = "Пополнение криптосчета на сумму указанную в рублевом эквиваленте")
  @ApiResponse(responseCode = "200", description = "Успешно")
  @PostMapping("/refill")
  @ResponseStatus(code = HttpStatus.OK)
  public void refillAccount(@RequestBody ChangeAmountCryptoAccountDto refill) throws IOException {
    cryptoAccountService.refillAccountOnRubbles(refill.getUuid(), refill.getAmountRubles());
  }

  @Operation(summary = "Вывод из криптосчета",
      description = "Вывод из криптосчета суммы указанной в рублевом эквиваленте")
  @ApiResponse(responseCode = "200", description = "Успешно")
  @PostMapping("/withdrawal")
  @ResponseStatus(code = HttpStatus.OK)
  public void withdrawalFromAccount(@RequestBody ChangeAmountCryptoAccountDto withdrawal)
      throws IOException {
    cryptoAccountService.withdrawalRublesFromAccount(withdrawal.getUuid(),
        withdrawal.getAmountRubles());
  }

  @Operation(summary = "Показ рублевого эквивалента криптосчета пользователя",
      description = "Показывает в рублевом эквиваленте криптосчета пользователя по id криптосчета")
  @ApiResponse(responseCode = "200", description = "Успешно", content = {
      @Content(mediaType = "plain/text", schema = @Schema(implementation = BigDecimal.class))})
  @GetMapping("/balance/{id}")
  @ResponseStatus(code = HttpStatus.OK)
  public BigDecimal showBalanceCryptoAccount(@PathVariable("id") UUID uuid) throws IOException {
    return cryptoAccountService.getBalanceAccountInRubles(uuid);
  }

  @Operation(summary = "Показ рублевого эквивалента всех криптосчетов пользователя",
      description = "Показывает в рублевом эквиваленте все криптосчета пользователя")
  @ApiResponse(responseCode = "200", description = "Успешно", content = {
      @Content(mediaType = "plain/text", schema = @Schema(implementation = BigDecimal.class))})
  @GetMapping("/balance")
  @ResponseStatus(code = HttpStatus.OK)
  public BigDecimal showBalanceAllCryptoAccount(@RequestParam("username") String login) {
    return cryptoAccountService.getBalanceAllAccountsInRubles(login);
  }
}
