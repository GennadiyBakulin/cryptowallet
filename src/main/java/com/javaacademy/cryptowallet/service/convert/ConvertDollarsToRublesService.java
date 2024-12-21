package com.javaacademy.cryptowallet.service.convert;

import java.io.IOException;
import java.math.BigDecimal;

public interface ConvertDollarsToRublesService {

  BigDecimal convertDollarsToRubles(BigDecimal countDollars) throws IOException;
  BigDecimal convertRublesToDollars(BigDecimal countRubles) throws IOException;
}
