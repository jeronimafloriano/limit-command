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

    // Exchange principal
    public static final String LIMIT_EXCHANGE = "limitExchange";

    // Filas principais
    public static final String LIMIT_CREATED_QUEUE = "limit-created";
    public static final String LIMIT_USAGE_UPDATED_QUEUE = "limit-usage-updated";

    // Routing keys
    public static final String LIMIT_CREATED_ROUTING = "limit.created";
    public static final String LIMIT_USAGE_UPDATED_ROUTING = "limit.usage.updated";

    // DLX
    public static final String LIMIT_DLX = "limit.dlx";
    public static final String LIMIT_CREATED_DLQ = "limit-created.dlq";

    // ---------- Infra básica ----------

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

    // ---------- Exchanges ----------

    @Bean
    public TopicExchange limitUsageExchange() {
        return new TopicExchange(LIMIT_EXCHANGE);
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(LIMIT_DLX);
    }

    // ---------- Filas ----------

    @Bean
    public Queue limitCreatedQueue() {
        return QueueBuilder.durable(LIMIT_CREATED_QUEUE)
                .ttl(60000) // 60 segundos
                .deadLetterExchange(LIMIT_DLX)
                .deadLetterRoutingKey("limit.created.expired")
                .build();
    }

    @Bean
    public Queue limitUsageUpdatedQueue() {
        return QueueBuilder.durable(LIMIT_USAGE_UPDATED_QUEUE).build();
    }

    @Bean
    public Queue limitCreatedDeadLetterQueue() {
        return QueueBuilder.durable(LIMIT_CREATED_DLQ).build();
    }

    // ---------- Bindings ----------

    @Bean
    public Binding limitCreatedBinding() {
        return BindingBuilder.bind(limitCreatedQueue()).to(limitUsageExchange()).with(LIMIT_CREATED_ROUTING);
    }

    @Bean
    public Binding limitUsageUpdatedBinding() {
        return BindingBuilder.bind(limitUsageUpdatedQueue())
                .to(limitUsageExchange())
                .with(LIMIT_USAGE_UPDATED_ROUTING);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(limitCreatedDeadLetterQueue())
                .to(deadLetterExchange())
                .with("#"); // qualquer routing key
    }
}
