package com.limitcommand.infrastructure.events.listener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * Listening to an event containing a recommendation to adjust the limit.
 */
public class LimitPolicyEvaluatedEvent {

    private UUID accountId;
    private BigDecimal newTotalLimit;
    private String reason;
    private LocalDateTime timestamp;
    private Map<String, Object> context;

    public LimitPolicyEvaluatedEvent() {}

    public UUID getAccountId() {
        return accountId;
    }

    public BigDecimal getNewTotalLimit() {
        return newTotalLimit;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Map<String, Object> getContext() {
        return context;
    }
}
