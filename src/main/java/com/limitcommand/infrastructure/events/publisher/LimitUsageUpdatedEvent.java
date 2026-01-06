package com.limitcommand.infrastructure.events.publisher;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This event is published whenever a credit limit is used (reservation/release/adjustment).
 */
public class LimitUsageUpdatedEvent {

    private UUID accountId;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private OperationType operationType;

    public enum OperationType {
        RESERVE,
        RELEASE,
        ADJUSTMENT
    }

    public LimitUsageUpdatedEvent() {}

    public LimitUsageUpdatedEvent(UUID accountId, BigDecimal amount, OperationType operationType) {
        this.accountId = accountId;
        this.amount = amount;
        this.operationType = operationType;
        this.timestamp = LocalDateTime.now();
    }

    public UUID getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }
}
