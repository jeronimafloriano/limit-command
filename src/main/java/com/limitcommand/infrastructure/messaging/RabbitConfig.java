package com.limitcommand.infrastructure.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String LIMIT_USAGE_EXCHANGE = "limitUsageExchange";
    public static final String LIMIT_CREATED_QUEUE = "limit-created";
    public static final String LIMIT_USAGE_UPDATED_QUEUE = "limit-usage-updated";
    public static final String LIMIT_USAGE_UPDATED_ROUTING = "limit.usage.updated";
    public static final String LIMIT_CREATED_ROUTING = "limit.created";

    @Bean
    public JacksonJsonMessageConverter jackson2JsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory, JacksonJsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Queue limitCreatedQueue() {
        return QueueBuilder.durable(LIMIT_CREATED_QUEUE).build();
    }

    @Bean
    public Queue limitUsageUpdatedQueue() {
        return QueueBuilder.durable(LIMIT_USAGE_UPDATED_QUEUE).build();
    }

    @Bean
    public TopicExchange limitUsageExchange() {
        return new TopicExchange(LIMIT_USAGE_EXCHANGE);
    }

    @Bean
    public Binding limitUsageUpdateBinding() {
        return BindingBuilder.bind(limitUsageUpdatedQueue())
                .to(limitUsageExchange())
                .with(LIMIT_USAGE_UPDATED_ROUTING);
    }

    @Bean
    public Binding limitUsageCreatedBinding() {
        return BindingBuilder.bind(limitCreatedQueue()).to(limitUsageExchange()).with(LIMIT_CREATED_ROUTING);
    }
}
