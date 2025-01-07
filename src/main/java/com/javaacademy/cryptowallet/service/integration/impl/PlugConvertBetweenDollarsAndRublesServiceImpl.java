package com.javaacademy.cryptowallet.service.integration.impl;

import com.javaacademy.cryptowallet.service.integration.ConvertBetweenDollarsAndRublesService;
import java.math.BigDecimal;
import java.math.MathContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("local")
@Service
@RequiredArgsConstructor
public class PlugConvertBetweenDollarsAndRublesServiceImpl implements
    ConvertBetweenDollarsAndRublesService {

  private final MathContext mathContext;

  @Value("${value-convert-dollars-to-rubles}")
  private BigDecimal value;

  @Override
  public BigDecimal convertDollarsToRubles(BigDecimal countDollars) {
    return countDollars.multiply(value, mathContext);
  }

  @Override
  public BigDecimal convertRublesToDollars(BigDecimal countRubles) {
    return countRubles.divide(value, mathContext);
  }
}
