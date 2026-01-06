package com.limitcommand.domain.port;

import com.limitcommand.domain.Limit;
import java.math.BigDecimal;
import java.util.UUID;

public interface LimitServicePort {
    Limit consult(UUID accountId);

    boolean consultAvailability(UUID accountId, BigDecimal amount);

    void reserve(UUID accountId, BigDecimal amount);

    void release(UUID accountId, BigDecimal amount);

    void applyAdjustment(UUID accountId, BigDecimal amountAdd);
}
