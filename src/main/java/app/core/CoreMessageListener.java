package app.core;

import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class CoreMessageListener {
    public String nodeId;

    @Value("${core.queue}")
    private String nomeFila;

    @PostConstruct
    public void init(){
        this.nodeId = UUID.randomUUID().toString().substring(0,8);
        System.out.println("🚀 Core Node UP [" + nodeId + "] ouvindo queue: " + nomeFila);
    }

     @RabbitListener(queues = "${core.queue}")
    public void handleMessage(Message payload) {
        System.out.printf("📥 [%s] Mensagem recebida: %s%n", nodeId, payload);
    }
}
