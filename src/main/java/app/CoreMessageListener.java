package app.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.core.OverallAggregatedResults.AggregatedStatisticItem;
import app.core.OverallAggregatedResults.TypeSpecificAggregatedResult;
import jakarta.annotation.PostConstruct;

@Component
public class CoreMessageListener {

    @Value("${core.node-id}")
    public String nodeId;

    @Value("${core.queue}")
    private String nomeFila;

    private final RabbitTemplate rabbitTemplate;

    final List<Message> messages = new CopyOnWriteArrayList<>();

    static int LotesProcessadosTotal = 0;

    public CoreMessageListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void init() {
        System.out.println(">> Core Node UP [" + nodeId + "] ouvindo queue: " + nomeFila);
    }

    @RabbitListener(queues = "${core.queue}")
    public void handleMessage(String payload) throws JsonMappingException, JsonProcessingException {
        System.out.printf(">> node: [%s] Mensagem recebida: %s%n", nodeId, payload);

        Message message = new ObjectMapper().readValue(payload, Message.class);

        var loteFinalAgregado = agrupaMensagemEmLote(message);

        String resposta = new ObjectMapper().writeValueAsString(
                loteFinalAgregado);

        rabbitTemplate.convertAndSend("backend-response-queue", resposta);
        System.out.printf(">> node: [%s] Resposta enviada para o backend", nodeId);
    }

    OverallAggregatedResults agrupaMensagemEmLote(Message message) throws JsonProcessingException {

        OverallAggregatedResults loteFinalAgregado = null;
        List<TypeSpecificAggregatedResult> dadosAgregados = new ArrayList<>();

        String[] types = { "votacao", "iot" };

        for (String type : types) {

            List<Message> mensagensRecuperadas = messages.stream().filter(it -> message.type.equals(type))
                    .toList();

            Optional<Message> itemOp = mensagensRecuperadas.stream()
                    .filter(it -> message.object.equals(message.object))
                    .findFirst();

            if (itemOp.isPresent()) {
                itemOp.get().valor++;
            } else {
                messages.add(message);
            }

            // continuar daqui

            List<AggregatedStatisticItem> objetosInternosAgregadosPorTipo = messageToListItemsAgregadosPorTipo(
                    mensagensRecuperadas);

            var itemPadronizadoAgregado = new TypeSpecificAggregatedResult(
                    type,
                    objetosInternosAgregadosPorTipo);

            dadosAgregados.add(itemPadronizadoAgregado);

            loteFinalAgregado = new OverallAggregatedResults(
                    dadosAgregados,
                    LotesProcessadosTotal,
                    messages.size());

        }

        LotesProcessadosTotal++;
        return loteFinalAgregado;

        // LotesProcessadosTotal++;
    }

    List<AggregatedStatisticItem> messageToListItemsAgregadosPorTipo(List<Message> mensagensRecuperadas) {
        List<AggregatedStatisticItem> objetosInternosAgregadosPorTipo = new ArrayList<>();

        int contagemTotalDoGrupo = contagemTotalDoGrupo(mensagensRecuperadas);

        mensagensRecuperadas.forEach(it -> {
            int valorTotalDoItem = it.valor;

            double porcentagem = (valorTotalDoItem * 100) / contagemTotalDoGrupo;

            var itemDoTipoVotacao = new AggregatedStatisticItem(
                    it.object,
                    1,
                    1,
                    valorTotalDoItem,
                    valorTotalDoItem,
                    porcentagem);

            objetosInternosAgregadosPorTipo.add(itemDoTipoVotacao);
        });

        return objetosInternosAgregadosPorTipo;
    }

    int contagemTotalDoGrupo(List<Message> mensagensRecuperadas) {
        int somatorio = 0;
        for (Message item : mensagensRecuperadas) {
            somatorio += item.valor;
        }
        return somatorio;
    }
}
