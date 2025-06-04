package app.core;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class CoreMessageListener {

    @Value("${core.node-id}")
    public String nodeId;

    @Value("${core.queue}")
    private String nomeFila;

    @PostConstruct
    public void init() {
        System.out.println(">> Core Node UP [" + nodeId + "] ouvindo queue: " + nomeFila);
    }

    @RabbitListener(queues = "${core.queue}")
    public void handleMessage(Message payload) {
        System.out.printf(">> node: [%s] Mensagem recebida: %s%n", nodeId, payload);
    }
}
