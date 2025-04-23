package com.cryxie.domain.discord;

public class CartaoAutenticacao extends CartaoDiscordBase {

    public CartaoAutenticacao(
            String titulo, String descricao) {
        this.mensagemIncorportadas = new MensagemIncorportada[] {
                MensagemIncorportada.builder()
                        .titulo(titulo)
                        .descricao(descricao)
                        .cor(16711680)
                        .build()
        };

        this.urlWebhook = "https://discord.com/api/webhooks/1339332064024985710/5LvvfgybXzJ8T7Z7osozg57B0reNAm8sZg6aSFMOnsqDNYQgilkJvxU-ZV0xnf7ro7Vs";
    }

}
