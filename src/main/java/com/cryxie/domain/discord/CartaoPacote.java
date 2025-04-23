package com.cryxie.domain.discord;

public class CartaoPacote extends CartaoDiscordBase {
    
    public CartaoPacote(
    String titulo, String descricao) {
       this.mensagemIncorportadas = new MensagemIncorportada[]{
            MensagemIncorportada.builder()
                 .titulo(titulo)
                 .descricao(descricao)
                 .cor(16711680)
                 .build()
       };

       this.urlWebhook = "https://discord.com/api/webhooks/1339319468232540182/gRs_PuF8BMoFycQAzpo5_c0YW6IURJrDPl_H3X28aH2ZxxAplQHc_EiANYrY__5pa4qu";
    }
    
}
