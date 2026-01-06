package com.limitcommand.application.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public class LimitResponseDTO {

    private UUID accountId;
    private BigDecimal totalLimit;
    private BigDecimal reservedLimit;
    private BigDecimal availableLimit;

    public LimitResponseDTO(
            UUID accountId, BigDecimal totalLimit, BigDecimal reservedLimit, BigDecimal availableLimit) {
        this.accountId = accountId;
        this.totalLimit = totalLimit;
        this.reservedLimit = reservedLimit;
        this.availableLimit = availableLimit;
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
}
