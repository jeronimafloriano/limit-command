package com.limitcommand.infrastructure.repository;

import com.limitcommand.domain.Limit;
import com.limitcommand.domain.exceptions.LimitNotFoundException;
import com.limitcommand.domain.port.LimitRepositoryPort;
import com.limitcommand.infrastructure.adapters.SpringLimitRepository;
import com.limitcommand.infrastructure.adapters.entity.LimitEntity;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class LimitRepository implements LimitRepositoryPort {

    private final SpringLimitRepository springLimitRepository;

    public LimitRepository(SpringLimitRepository springLimitRepository) {
        this.springLimitRepository = springLimitRepository;
    }

    @Override
    public Limit findByAccountId(UUID accountId) {
        return this.springLimitRepository
                .findById(accountId)
                .map(LimitEntity::toLimit)
                .orElseThrow(() -> new LimitNotFoundException(accountId));
    }

    @Override
    public void save(Limit limit) {
        LimitEntity limitEntity =
                this.springLimitRepository.findById(limit.getAccountId()).orElse(new LimitEntity(limit));

        limitEntity.updateFromDomain(limit);

        this.springLimitRepository.save(limitEntity);
    }
}
