package com.limitcommand.domain.adapter;

import br.com.leverinfo.validation.ArgumentValidations;
import com.limitcommand.application.validation.Messages;
import com.limitcommand.domain.Limit;
import com.limitcommand.domain.exceptions.LimitNotFoundException;
import com.limitcommand.domain.port.LimiteRepositoryPort;
import com.limitcommand.domain.port.LimiteServicePort;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
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
    ArgumentValidations.isNotNull(accountId, Messages.REQUIRED_ID);
    ArgumentValidations.isNotNull(amount, Messages.REQUIRED_AMOUNT);

    return this.limiteRepositoryPort.findByAccountId(accountId)
        .map(limit -> limit.isAvailable(amount))
        .orElseThrow(() -> new LimitNotFoundException(accountId));
  }

  @Override
  public void reserve(UUID accountId, BigDecimal amount) {
    ArgumentValidations.isNotNull(accountId, Messages.REQUIRED_ID);
    ArgumentValidations.isNotNull(amount, Messages.REQUIRED_AMOUNT);

    Limit limit = this.limiteRepositoryPort.findByAccountId(accountId)
        .orElseThrow(() -> new LimitNotFoundException(accountId));
    
    limit.reserve(amount);
    this.limiteRepositoryPort.save(limit);
  }

  @Override
  public void release(UUID accountId, BigDecimal amount) {
    ArgumentValidations.isNotNull(accountId, Messages.REQUIRED_ID);
    ArgumentValidations.isNotNull(amount, Messages.REQUIRED_AMOUNT);

    Limit limit = this.limiteRepositoryPort.findByAccountId(accountId)
        .orElseThrow(() -> new LimitNotFoundException(accountId));
    
    limit.release(amount);
    this.limiteRepositoryPort.save(limit);
  }

  @Override
  public void applyAdjustment(UUID accountId, BigDecimal amount) {
    ArgumentValidations.isNotNull(accountId, Messages.REQUIRED_ID);
    ArgumentValidations.isNotNull(amount, Messages.REQUIRED_AMOUNT);

    Limit limit = this.limiteRepositoryPort.findByAccountId(accountId)
        .orElseThrow(() -> new LimitNotFoundException(accountId));
    
    limit.applyAdjustment(amount);
    this.limiteRepositoryPort.save(limit);
  }
}
