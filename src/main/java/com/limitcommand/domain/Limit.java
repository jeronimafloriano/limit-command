package com.limitcommand.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Limit {

  private UUID accountId;
  private BigDecimal totalLimit;
  private BigDecimal reservedLimit;
  private BigDecimal availableLimit;

  public Limit(UUID accountId, BigDecimal totalLimit, BigDecimal reservedLimit,
      BigDecimal availableLimit) {
    this.accountId = accountId;
    this.totalLimit = totalLimit;
    this.reservedLimit = reservedLimit;
    this.availableLimit = availableLimit;
  }

  public void reserve(BigDecimal amount) {
      if (isAvailable(amount)) {
        this.reservedLimit = this.reservedLimit.add(amount);
        this.availableLimit = this.availableLimit.subtract(amount);
      }
  }

  public void release(BigDecimal amount) {
    this.reservedLimit = this.reservedLimit.subtract(amount);
    this.availableLimit = this.availableLimit.add(amount);
  }

  public void applyAdjustment(BigDecimal newTotalLimit) {
    this.totalLimit = this.totalLimit.add(newTotalLimit);
  }

  public boolean isAvailable(BigDecimal amount) {
    return this.availableLimit.compareTo(amount) >= 0;
  }
}
