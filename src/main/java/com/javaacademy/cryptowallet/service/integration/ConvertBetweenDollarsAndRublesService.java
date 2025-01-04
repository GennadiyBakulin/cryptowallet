package com.javaacademy.cryptowallet.service.integration;

import java.io.IOException;
import java.math.BigDecimal;

public interface ConvertBetweenDollarsAndRublesService {

  BigDecimal convertDollarsToRubles(BigDecimal countDollars) throws IOException;
  BigDecimal convertRublesToDollars(BigDecimal countRubles) throws IOException;
}
