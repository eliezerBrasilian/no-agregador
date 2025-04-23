package com.cryxie.domain.discord;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

public abstract class CartaoDiscordBase {
    @JsonProperty("username")
    protected String nomeBot = "robô almanaque";
    @JsonProperty("avatar_url")
    private String urlFoto;
    @JsonProperty("embeds")
    protected MensagemIncorportada[] mensagemIncorportadas;

    @Getter
    protected String urlWebhook = "";

    @AllArgsConstructor
    @Getter
    public static class MensagemIncorportada {
        @JsonProperty("title")
        private String titulo;
        @JsonProperty("description")
        private String descricao;
        @JsonProperty("color")
        private int cor = 16711680;
        @JsonProperty("footer")
        private Rodape rodape;
        @JsonProperty("fields")
        private Campo[] campos;

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private String titulo;
            private String descricao;
            private int cor;
            private Rodape rodape;
            private Campo[] campos;

            public Builder titulo(String titulo) {
                this.titulo = titulo;
                return this;
            }

            public Builder descricao(String descricao) {
                this.descricao = descricao;
                return this;
            }

            public Builder cor(int cor) {
                this.cor = cor;
                return this;
            }

            public Builder rodape(Rodape rodape) {
                this.rodape = rodape;
                return this;
            }

            public Builder campos(Campo[] campos) {
                this.campos = campos;
                return this;
            }

            public MensagemIncorportada build() {
                return new MensagemIncorportada(titulo, descricao, cor, rodape, campos);
            }
        }

    }

    @Data
    @AllArgsConstructor
    public static class Rodape {
        @JsonProperty("text")
        private String texto;
    }

    @Data
    @AllArgsConstructor
    public static class Campo {
        @JsonProperty("name")
        private String nome;
        @JsonProperty("value")
        private String valor;
        @JsonProperty("inline")
        private boolean inline;
    }

    public String paraJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

    public static enum CorPadrao {
        VERDE(65280), // 0x00FF00
        AZUL(255), // 0x0000FF
        AMARELO(16776960), // 0xFFFF00
        LARANJA(16753920), // 0xFFA500
        VERMELHO(16711680);

        public int corNumerica;

        CorPadrao(int cor) {
            this.corNumerica = cor;
        }
    }
}
