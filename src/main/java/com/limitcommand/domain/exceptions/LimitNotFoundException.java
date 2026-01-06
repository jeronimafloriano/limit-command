package com.limitcommand.domain.exceptions;

import java.util.UUID;

/**
 * Exception thrown when a limit associated with a given account cannot be found.
 * <p>
 * This typically indicates that no limit configuration exists for the provided
 * account identifier in the underlying data store or domain model.
 * <p>
 * Consumers should treat this as a "not found" condition. Depending on the use
 * case, callers may translate this into an appropriate response (for example,
 * an HTTP 404 status) or apply a fallback behavior when no limit is defined.
 */
public class LimitNotFoundException extends RuntimeException {

    /**
     * Creates a new exception indicating that no limit was found for the given account.
     *
     * @param accountId the identifier of the account for which a limit was expected
     */
    public LimitNotFoundException(UUID accountId) {
        super("Limit not found for accountId: " + accountId);
    }
}
