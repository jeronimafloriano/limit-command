package com.limitcommand.domain.adapter;

import com.limitcommand.domain.Limit;
import com.limitcommand.domain.port.LimiteRepositoryPort;
import com.limitcommand.domain.port.LimiteServicePort;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class LimiteServiceImpl implements LimiteServicePort {

  private final LimiteRepositoryPort limiteRepositoryPort;

  public LimiteServiceImpl(LimiteRepositoryPort limiteRepositoryPort) {
    this.limiteRepositoryPort = limiteRepositoryPort;
  }

  @Override
  public Optional<Limit> consult(UUID accountId) {
    return this.limiteRepositoryPort.findByAccountId(accountId);
  }

  @Override
  public boolean consultAvailability(UUID accountId, BigDecimal amount) {
      return this.limiteRepositoryPort.findByAccountId(accountId)
          .map(limit -> limit.isAvailable(amount))
          .orElseThrow(() -> new IllegalArgumentException("Limit not found for accountId: " + accountId));

  }

  @Override
  public void reserve(UUID accountId, BigDecimal amount) {
    Optional <Limit> newLimit = this.limiteRepositoryPort.findByAccountId(accountId)
        .map(limit -> {
          limit.reserve(amount);
          return limit;
        });

    newLimit.ifPresent(limiteRepositoryPort::save);
  }

  @Override
  public void release(UUID accountId, BigDecimal amount) {
    Optional<Limit> releasedLimit = this.limiteRepositoryPort.findByAccountId(accountId)
        .map(limit -> {
          limit.release(amount);
          return limit;
        });
    releasedLimit.ifPresent(limiteRepositoryPort::save);
  }

  @Override
  public void applyAdjustment(UUID accountId, BigDecimal amount) {
    Optional<Limit> adjustedLimit = this.limiteRepositoryPort.findByAccountId(accountId)
        .map(limit -> {
          limit.applyAdjustment(amount);
          return limit;
        });
    adjustedLimit.ifPresent(limiteRepositoryPort::save);
  }
}
