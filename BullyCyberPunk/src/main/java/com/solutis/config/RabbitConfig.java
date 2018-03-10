package com.solutis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableRabbit
public class RabbitConfig {

    private String username = "guest";

    private String password = "guest";

    private String hostname = "http://ec2-34-229-106-230.compute-1.amazonaws.com";

    private String virtualHost = "/cyberpunk";

    private String queueMensagem = "ha_queue_cyberpunk_mensagem";

    @Bean
    public ConnectionFactory connectionFactory() {
        final CachingConnectionFactory connectionFactory = new CachingConnectionFactory(hostname, 5672);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);


        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        final RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        rabbitAdmin.setAutoStartup(true);
//        rabbitAdmin.declareQueue(queueProcessamentoConflito());
        rabbitAdmin.declareQueue(queueEnviarMensagem());

        return rabbitAdmin;
    }


    @Bean
    public Queue queueEnviarMensagem() {
        final Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "ha_ex_mpc_exclusao_conflito");
        args.put("x-dead-letter-routing-key", "ha_dlq_mpc_exclusao_conflito");
        return new Queue(queueMensagem, true, false, false, args);
    }


    @Bean
    public MessageConverter jsonMessageConverter() {
        final Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        final ObjectMapper objectMapper = new ObjectMapper();
        jackson2JsonMessageConverter.setJsonObjectMapper(objectMapper);
        return jackson2JsonMessageConverter;
    }
}
