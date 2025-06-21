package app;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import app.model.DadoAgregado;
import app.model.DadoAgregadoRepository;
import jakarta.annotation.PostConstruct;

@Component
public class CoreMessageListener {

    @Value("${core.node-id}")
    public String nodeId;

    @Value("${core.queue}")
    private String nomeFila;

    private final RabbitTemplate rabbitTemplate;
    private final DadoAgregadoRepository repository;

    private final RestTemplate restTemplate = new RestTemplate();

    public CoreMessageListener(RabbitTemplate rabbitTemplate, DadoAgregadoRepository repository) {
        this.rabbitTemplate = rabbitTemplate;
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        System.out.println(">> Core Node UP [" + nodeId + "] ouvindo queue: " + nomeFila);
    }

    public record PayloadCore(String batchId, String sourceNodeId, List<Message> dataPoints) {
    }

    public record Message(String type, String objectIdentifier, int valor,
            @JsonDeserialize(using = LocalDateTimeFromLongDeserializer.class) LocalDateTime datetime) {
    }

    public record PayloadAgregadoCore(String batchId, String sourceNodeId, List<DadoAgregado> dadosAgregados) {
    }

    @RabbitListener(queues = "${core.queue}")
    public void handleMessage(PayloadCore payload) {
        System.out.printf(">> node: [%s] Mensagem recebida: %s%n", nodeId, payload);

        try {
            String url = "https://agregador-node.onrender.com/api/aggregator/results";

            ResponseEntity<PayloadAgregadoCore> response = restTemplate.getForEntity(url, PayloadAgregadoCore.class);

            PayloadAgregadoCore dadosAgregados = response.getBody();

            if (dadosAgregados != null && dadosAgregados.dadosAgregados() != null) {
                // Para cada DadoAgregado do retorno, salva/updata no mongo
                for (DadoAgregado dado : dadosAgregados.dadosAgregados()) {
                    repository.findByType(dado.type)
                            .map(existing -> {
                                existing.lista = dado.lista;
                                return repository.save(existing);
                            })
                            .orElseGet(() -> repository.save(new DadoAgregado(dado.type, dado.lista)));
                }
                System.out.println("Banco atualizado com dados do agregador via HTTP GET.");
            } else {
                System.err.println("Resposta HTTP veio nula ou sem dados agregados.");
            }

            String urlPostNodeBackend = "http://localhost:4012/api/votar/update-from-no-agregador";

            ResponseEntity<String> postResponse = restTemplate.postForEntity(
                    urlPostNodeBackend,
                    dadosAgregados,
                    String.class);

            System.out.printf("Resposta do backend Node: %s%n", postResponse.getBody());
            // Opcional: reenviar a resposta via RabbitMQ para o backend ou outro consumidor
            // rabbitTemplate.convertAndSend("backend-response-queue", dadosAgregados);

            System.out.println(dadosAgregados);
            System.out.println("Resposta enviada para backend-response-queue.");

        } catch (Exception e) {
            System.err.println("Erro ao buscar ou salvar dados agregados: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
