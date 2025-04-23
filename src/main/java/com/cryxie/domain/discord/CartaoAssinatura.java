package com.cryxie.domain.discord;

public class CartaoAssinatura extends CartaoDiscordBase {

    public CartaoAssinatura(
            String titulo, String descricao) {
        this.mensagemIncorportadas = new MensagemIncorportada[]{
                MensagemIncorportada.builder()
                        .titulo(titulo)
                        .descricao(descricao)
                        .cor(16711680)
                        .build()
        };

        this.urlWebhook = "https://discord.com/api/webhooks/1341927075744972891/cwNE02zedc84CeX1STXDuWvoH68_p0eFwsuAgI871Gl0YCws_jyfxTvcdeOdJv2tOmdI";
    }

}
