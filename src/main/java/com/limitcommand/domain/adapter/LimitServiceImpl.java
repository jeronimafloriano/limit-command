package com.limitcommand.domain.adapter;

import br.com.leverinfo.validation.ArgumentValidations;
import com.limitcommand.application.validation.Messages;
import com.limitcommand.domain.Limit;
import com.limitcommand.domain.port.LimitRepositoryPort;
import com.limitcommand.domain.port.LimitServicePort;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class LimitServiceImpl implements LimitServicePort {

    private final LimitRepositoryPort limitRepositoryPort;

    public LimitServiceImpl(LimitRepositoryPort limitRepositoryPort) {
        this.limitRepositoryPort = limitRepositoryPort;
    }

    @Override
    public Limit consult(UUID accountId) {
        ArgumentValidations.isNotNull(accountId, Messages.REQUIRED_ID);
        return this.limitRepositoryPort.findByAccountId(accountId);
    }

    @Override
    public boolean consultAvailability(UUID accountId, BigDecimal amount) {
        ArgumentValidations.isNotNull(accountId, Messages.REQUIRED_ID);
        ArgumentValidations.isNotNull(amount, Messages.REQUIRED_AMOUNT);

        return this.limitRepositoryPort.findByAccountId(accountId).isAvailable(amount);
    }

    @Override
    public void reserve(UUID accountId, BigDecimal amount) {
        ArgumentValidations.isNotNull(accountId, Messages.REQUIRED_ID);
        ArgumentValidations.isNotNull(amount, Messages.REQUIRED_AMOUNT);

        Limit limit = this.limitRepositoryPort.findByAccountId(accountId);

        limit.reserve(amount);
        this.limitRepositoryPort.save(limit);
    }

    @Override
    public void release(UUID accountId, BigDecimal amount) {
        ArgumentValidations.isNotNull(accountId, Messages.REQUIRED_ID);
        ArgumentValidations.isNotNull(amount, Messages.REQUIRED_AMOUNT);

        Limit limit = this.limitRepositoryPort.findByAccountId(accountId);

        limit.release(amount);
        this.limitRepositoryPort.save(limit);
    }

    @Override
    public void applyAdjustment(UUID accountId, BigDecimal amount) {
        ArgumentValidations.isNotNull(accountId, Messages.REQUIRED_ID);
        ArgumentValidations.isNotNull(amount, Messages.REQUIRED_AMOUNT);

        Limit limit = this.limitRepositoryPort.findByAccountId(accountId);

        limit.applyAdjustment(amount);
        this.limitRepositoryPort.save(limit);
    }
}
