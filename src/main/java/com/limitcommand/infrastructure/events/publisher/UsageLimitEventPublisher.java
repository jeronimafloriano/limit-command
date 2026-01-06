package com.limitcommand.infrastructure.events.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class UsageLimitEventPublisher implements UsageLimitEventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    public UsageLimitEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishLimitUsageUpdated(LimitUsageUpdatedEvent event) {
        rabbitTemplate.convertAndSend("limitUsageExchange", "limit.usage.updated", event);
    }
}
