package com.limitcommand.domain.exceptions;

import java.util.UUID;

public class LimitNotFoundException extends RuntimeException {
  
  public LimitNotFoundException(UUID accountId) {
    super("Limit not found for accountId: " + accountId);
  }
}

