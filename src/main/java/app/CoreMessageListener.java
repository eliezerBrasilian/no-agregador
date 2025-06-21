package app;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.annotation.PostConstruct;

@Component
public class CoreMessageListener {

    @Value("${core.node-id}")
    public String nodeId;

    @Value("${core.queue}")
    private String nomeFila;

    private final RabbitTemplate rabbitTemplate;

    private final List<Message> buffer = new CopyOnWriteArrayList<>();

    public CoreMessageListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void init() {
        System.out.println(">> Core Node UP [" + nodeId + "] ouvindo queue: " + nomeFila);
    }

    public record PayloadCore(
            String batchId,
            String sourceNodeId,
            List<Message> dataPoints) {
    }

    @RabbitListener(queues = "${core.queue}")
    public void handleMessage(PayloadCore payload) throws JsonMappingException, JsonProcessingException {
        System.out.printf(">> node: [%s] Mensagem recebida: %s%n", nodeId, payload);

        buffer.addAll(payload.dataPoints());

        // minha logica pra enviar dados depois de 5 votos agregados
        if (buffer.size() >= 2) {
            PayloadCore loteFinalAgregado = agregar();
            rabbitTemplate.convertAndSend("backend-response-queue", loteFinalAgregado);
            buffer.clear();
            System.out.println(">> Enviado resultado agregado para o backend");

            rabbitTemplate.convertAndSend("backend-response-queue", loteFinalAgregado);

            System.out.printf(">> node: [%s] Resposta enviada para o backend", nodeId);
        }

    }

    public PayloadCore agregar() {
        Map<String, Integer> votosPorObjeto = new HashMap<>();

        for (Message m : buffer) {
            votosPorObjeto.merge(m.objectIdentifier(), m.valor(), Integer::sum);
        }

        List<Message> resultado = votosPorObjeto.entrySet().stream()
                .map(e -> new Message(
                        "voto-final", e.getKey(),
                        e.getValue(),
                        LocalDateTime.now()))
                .toList();

        return new PayloadCore(
                UUID.randomUUID().toString(),
                nodeId,
                resultado);
    }

}
