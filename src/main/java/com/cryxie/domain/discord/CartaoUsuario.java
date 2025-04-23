package com.cryxie.domain.discord;

public class CartaoUsuario extends CartaoDiscordBase {

    public CartaoUsuario(
            String titulo, String descricao) {
        this.mensagemIncorportadas = new MensagemIncorportada[] {
                MensagemIncorportada.builder()
                        .titulo(titulo)
                        .descricao(descricao)
                        .cor(16711680)
                        .build()
        };

        this.urlWebhook = "https://discord.com/api/webhooks/1343348960248463430/noesj0wUfTqRTFpLrQBZOGVwL50-OsUnIIYxXSbIQSmTlWmX9iyn9Segog1R8efySrVZ";
    }

}
