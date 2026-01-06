package com.limitcommand.infrastructure.adapters.entity;

import com.limitcommand.domain.Limit;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "limits")
public class LimitEntity {

    @Id
    private UUID accountId;

    private BigDecimal totalLimit;
    private BigDecimal reservedLimit;
    private BigDecimal availableLimit;

    public LimitEntity() {}

    public LimitEntity(Limit limit) {
        this.accountId = limit.getAccountId();
        this.totalLimit = limit.getTotalLimit();
        this.reservedLimit = limit.getReservedLimit();
        this.availableLimit = limit.getAvailableLimit();
    }

    public void updateFromDomain(Limit limit) {
        this.accountId = limit.getAccountId();
        this.totalLimit = limit.getTotalLimit();
        this.reservedLimit = limit.getReservedLimit();
        this.availableLimit = limit.getAvailableLimit();
    }

    public Limit toLimit() {
        return new Limit(this.accountId, this.totalLimit, this.reservedLimit, this.availableLimit);
    }
}
