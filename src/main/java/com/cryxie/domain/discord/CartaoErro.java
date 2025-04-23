package com.cryxie.domain.discord;

import static com.cryxie.domain.discord.CartaoDiscordBase.CorPadrao.VERMELHO;

public class CartaoErro extends CartaoDiscordBase {

    public CartaoErro(
            String titulo, String descricao) {
        this.nomeBot = "mensageiro do caos";
        this.mensagemIncorportadas = new MensagemIncorportada[] {
                MensagemIncorportada.builder()
                        .titulo(titulo)
                        .descricao(descricao)
                        .cor(VERMELHO.corNumerica)
                        .build()
        };

        this.urlWebhook = "https://discord.com/api/webhooks/1357515079150469303/deCWJedw22L8oWB66QI6hVWT1wA9lOeg7rxmb-EBshOKGgQjazZ4qUy3LK-dYd0r4T49";
    }

}
