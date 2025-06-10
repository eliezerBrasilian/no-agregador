package app.core;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Component
public class CoreMessageListener {

    @Value("${core.node-id}")
    public String nodeId;

    @Value("${core.queue}")
    private String nomeFila;

    private final RabbitTemplate rabbitTemplate;

    final List<Message> messages = new CopyOnWriteArrayList<>();

    public CoreMessageListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void init() {
        System.out.println(">> Core Node UP [" + nodeId + "] ouvindo queue: " + nomeFila);
    }

    @RabbitListener(queues = "${core.queue}")
    public void handleMessage(String payload) throws JsonMappingException, JsonProcessingException {
        Message message = new ObjectMapper().readValue(payload, Message.class);

        int indexCandidatoJaExistente = -1;
        for (var i = 0; i < messages.size(); i++) {
            Message item = messages.get(i);
            if (item.object.equals(message.object)) {
                indexCandidatoJaExistente = i;
            }
        }
        if (indexCandidatoJaExistente != -1) {
            messages.get(indexCandidatoJaExistente).valor++;
        } else {
            messages.add(message);
        }

        System.out.printf(">> node: [%s] Mensagem recebida: %s%n", nodeId, payload);

        // ->

        String resposta = new ObjectMapper().writeValueAsString(messages);
        rabbitTemplate.convertAndSend("backend-response-queue", resposta);
        System.out.printf(">> node: [%s] Resposta enviada para o backend", nodeId);
    }
}
