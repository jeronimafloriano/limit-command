package com.limitcommand.infrastructure.events.publisher;

import static com.limitcommand.infrastructure.messaging.RabbitConfig.LIMIT_CREATED_ROUTING;
import static com.limitcommand.infrastructure.messaging.RabbitConfig.LIMIT_EXCHANGE;
import static com.limitcommand.infrastructure.messaging.RabbitConfig.LIMIT_USAGE_UPDATED_ROUTING;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class UsageLimitEventPublisher implements UsageLimitEventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    public UsageLimitEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishLimitEvent(LimitEvent event) {
        if (event.getOperationType() == LimitEvent.OperationType.CREATED) {
            publishLimitCreated(event);
            return;
        }
        publishLimitUsageUpdated(event);
    }

    private void publishLimitUsageUpdated(LimitEvent event) {
        rabbitTemplate.convertAndSend(LIMIT_EXCHANGE, LIMIT_USAGE_UPDATED_ROUTING, event);
    }

    private void publishLimitCreated(LimitEvent event) {
        rabbitTemplate.convertAndSend(LIMIT_EXCHANGE, LIMIT_CREATED_ROUTING, event);
    }
}
