package com.limitcommand.domain.adapter;

import br.com.leverinfo.validation.ArgumentValidations;
import com.limitcommand.application.validation.Messages;
import com.limitcommand.domain.Limit;
import com.limitcommand.domain.port.LimitRepositoryPort;
import com.limitcommand.domain.port.LimitServicePort;
import com.limitcommand.infrastructure.events.publisher.LimitUsageUpdatedEvent;
import com.limitcommand.infrastructure.events.publisher.LimitUsageUpdatedEvent.OperationType;
import com.limitcommand.infrastructure.events.publisher.UsageLimitEventPublisherPort;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class LimitServiceImpl implements LimitServicePort {

    private final LimitRepositoryPort limitRepositoryPort;
    private final UsageLimitEventPublisherPort eventPublisherPort;

    public LimitServiceImpl(LimitRepositoryPort limitRepositoryPort, UsageLimitEventPublisherPort eventPublisherPort) {
        this.limitRepositoryPort = limitRepositoryPort;
        this.eventPublisherPort = eventPublisherPort;
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
        this.publishEvent(accountId, amount, OperationType.RESERVE);
    }

    @Override
    public void release(UUID accountId, BigDecimal amount) {
        ArgumentValidations.isNotNull(accountId, Messages.REQUIRED_ID);
        ArgumentValidations.isNotNull(amount, Messages.REQUIRED_AMOUNT);

        Limit limit = this.limitRepositoryPort.findByAccountId(accountId);

        limit.release(amount);
        this.limitRepositoryPort.save(limit);
        this.publishEvent(accountId, amount, OperationType.RELEASE);
    }

    @Override
    public void applyAdjustment(UUID accountId, BigDecimal amountAdd) {
        ArgumentValidations.isNotNull(accountId, Messages.REQUIRED_ID);
        ArgumentValidations.isNotNull(amountAdd, Messages.REQUIRED_AMOUNT);

        Limit limit = this.limitRepositoryPort.findByAccountId(accountId);

        limit.applyAdjustment(amountAdd);
        this.limitRepositoryPort.save(limit);
        this.publishEvent(accountId, amountAdd, OperationType.ADJUSTMENT);
    }

    private void publishEvent(UUID accountId, BigDecimal amount, OperationType operationType) {
        LimitUsageUpdatedEvent event = new LimitUsageUpdatedEvent(accountId, amount, operationType);
        this.eventPublisherPort.publishLimitUsageUpdated(event);
    }
}
