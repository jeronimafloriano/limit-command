package com.limitcommand.infrastructure.events.publisher;

/**
 * Exit port for publishing limit usage events.
 */
public interface UsageLimitEventPublisherPort {

    void publishLimitUsageUpdated(LimitUsageUpdatedEvent event);
}
