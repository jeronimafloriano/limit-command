package com.limitcommand.application.dtos;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class LimitRequestDTO {

  @NotNull
  private BigDecimal amount;

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
