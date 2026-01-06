package com.limitcommand.domain.port;

import com.limitcommand.domain.Limit;
import java.util.UUID;

public interface LimitRepositoryPort {

    /**
     * Retrieves the limit using the account identifier.
     * <p>
     * Implementations must throw {@link com.limitcommand.domain.exceptions.LimitNotFoundException}
     * when there is no limit registered for the {@code accountId}.
     */
    Limit findByAccountId(UUID accountId);

    void save(Limit limit);
}
