package com.cryxie.domain.discord;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EntregadorDiscord {

    public void enviarNotificacao(CartaoDiscordBase cartaoDiscordBase) {

        try {
            String json = cartaoDiscordBase.paraJson();
            String webhook = cartaoDiscordBase.getUrlWebhook();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(webhook))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(json))
                    .build();

            var cliente = HttpClient.newHttpClient();

            cliente.sendAsync(request, BodyHandlers.ofString())
                    .thenAccept((r) -> {
                        log.info("notificação enviada com sucesso");
                    })
                    .join();
        } catch (JsonProcessingException | URISyntaxException e) {
            log.error("erro ao enviar notificação para discord", e);
        }
    }
}
