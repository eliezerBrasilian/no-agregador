package app.core;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${core.queue}")
    private String nomeFila;

    @Value("${core.node-id}")
    private String nodeId;

    @Bean
    public Queue queue() {
        return new Queue(nomeFila, true);
    }
}
