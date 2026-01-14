package com.limitcommand.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Limit {

    private UUID accountId;
    private BigDecimal totalLimit;
    private BigDecimal reservedLimit;
    private BigDecimal availableLimit;

    public Limit(UUID accountId, BigDecimal totalLimit, BigDecimal reservedLimit, BigDecimal availableLimit) {
        this.accountId = accountId;
        this.totalLimit = totalLimit;
        this.reservedLimit = reservedLimit;
        this.availableLimit = availableLimit;
    }

    public Limit(UUID accountId, BigDecimal totalLimit) {
        this.accountId = accountId;
        this.totalLimit = totalLimit;
        this.reservedLimit = BigDecimal.ZERO;
        this.availableLimit = totalLimit;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public BigDecimal getTotalLimit() {
        return totalLimit;
    }

    public BigDecimal getReservedLimit() {
        return reservedLimit;
    }

    public BigDecimal getAvailableLimit() {
        return availableLimit;
    }

    public void reserve(BigDecimal amount) {
        if (!isAvailable(amount)) {
            throw new IllegalArgumentException("Insufficient available limit to reserve amount: " + amount);
        }
        this.reservedLimit = this.reservedLimit.add(amount);
        this.availableLimit = this.availableLimit.subtract(amount);
    }

    public void release(BigDecimal amount) {
        this.reservedLimit = this.reservedLimit.subtract(amount);
        this.availableLimit = this.availableLimit.add(amount);
    }

    public void applyAdjustment(BigDecimal amountAdd) {
        this.totalLimit = this.totalLimit.add(amountAdd);
        this.availableLimit = this.availableLimit.add(amountAdd);
    }

    public boolean isAvailable(BigDecimal amount) {
        return this.availableLimit.compareTo(amount) >= 0;
    }
}
