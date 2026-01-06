package com.limitcommand.infrastructure.events.listener;

import com.limitcommand.domain.port.LimitServicePort;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "limit-policy-evaluated")
public class AdjustmentLimitEventListener {

    private final LimitServicePort limitService;

    public AdjustmentLimitEventListener(LimitServicePort limitService) {
        this.limitService = limitService;
    }

    @RabbitHandler
    public void process(LimitPolicyEvaluatedEvent event) {
        limitService.applyAdjustment(event.getAccountId(), event.getNewTotalLimit());
    }
}
