package com.javaacademy.cryptowallet.service.convert.impl;

import com.javaacademy.cryptowallet.service.convert.ConvertBetweenDollarsAndRublesService;
import java.io.IOException;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("local")
@Service
public class PlugConvertBetweenDollarsAndRublesServiceImpl implements
    ConvertBetweenDollarsAndRublesService {

  @Value("${value-convert-dollars-to-rubles}")
  private BigDecimal value;

  @Override
  public BigDecimal convertDollarsToRubles(BigDecimal countDollars) {
    return countDollars.multiply(value);
  }

  @Override
  public BigDecimal convertRublesToDollars(BigDecimal countRubles) throws IOException {
    return countRubles.divide(value);
  }
}
