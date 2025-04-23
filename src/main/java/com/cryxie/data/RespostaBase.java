package com.cryxie.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RespostaBase<T>(
        @JsonProperty("message") String mensagem,
        @JsonProperty("data") T dado) {

    public RespostaBase(Mensagem mensagem, T dado) {
        this(mensagem.valorEmIngles(), dado);
    }

    public RespostaBase(Mensagem mensagem) {
        this(mensagem.valorEmIngles(), null);
    }

    public RespostaBase(T dado) {
        this(Mensagem.SUCESSO.valorEmIngles(), dado);
    }

    public enum Mensagem {
        SUCESSO("success"),
        ERRO("error"),
        PACOTE_CRIADO("package registered successfully"),
        PACOTE_EXCLUIDO("package deleted successfully"),
        PACOTE_REMOVIDO("package removed successfully"),
        PACOTE_DEPRECIADO("package deprecated successfully"),
        PACOTE_NAO_E_MAIS_DEPRECIADO("package has had its deprecation successfully removed"),
        PACOTE_EDITADO("package updated successfully"),
        PACOTE_NAO_EXISTE("Package with this id doesn't exists"),
        NOVA_VERSAO_ADICIONADA("new version was added"),
        USUARIO_CRIADO("user registered successfully"),
        USUARIO_EXCLUIDO("account deleted successfully"),
        USUARIO_EDITADO("user updated successfully"),
        USUARIO_COM_EMAIL_INFORMADO_NAO_EXISTE("user not found"),
        USUARIO_COM_EMAIL_INFORMADO_NAO_EXISTE_OU_SENHA_INVALIDA(
                "account with this email does not exist or the password is incorrect"),
        USUARIO_COM_ID_INFORMADO_NAO_EXISTE("user not found"),
        CONTA_ESTA_EXCLUIDA("account is deleted"),
        NAO_PODE_FAZER_ISSO_CONTA_EXCLUIDA(
                "This account does not have permission for this operation as it is excluded"),
        NAO_PODE_FAZER_ISSO_CONTA_NAO_E_PREMIUM(
                "This account does not have permission to perform this operation as it does not have premium access."),
        NAO_PODE_SUSPENDER_PACOTE(
                "This account does not have permission for suspend this package"),
        NAO_PODE_EDITAR_PACOTE(
                "This account does not have permission for edit this package"),
        PODE_EDITAR_PACOTE(
                "This account have permission for edit this package"),
        NAO_PODE_DEPRECIAR_PACOTE(
                "This account does not have permission for depreciate this package"),
        USUARIO_COM_EMAIL_INFORMADO_JA_EXISTE("email already in use"),
        SENHA_INCORRETA("invalid password"),
        CODIGO_ENVIADO_COM_SUCESSO("code sent successfully"),
        CODIGO_VALIDO("code is valid"),
        SENHA_REDEFINIDA_COM_SUCESSO("password reset successfully"),
        CODIGO_INVALIDO("invalid code"),
        SENHA_FORA_DOS_CONFORMES("the password is empty or has less than 8 characters"),
        ARQUIVO_README_ESTA_INCORRETO("The README file must be in the .md extension");

        private final String valor;

        Mensagem(String valor) {
            this.valor = valor;
        }

        public String valorEmIngles() {
            return valor;
        }
    }

    public ResponseEntity<RespostaBase<T>> ok() {
        return ResponseEntity.ok(new RespostaBase<>(mensagem, dado));
    }

    public ResponseEntity<RespostaBase<T>> created() {
        return ResponseEntity.status(HttpStatus.CREATED).body(new RespostaBase<T>(mensagem, dado));
    }

    public ResponseEntity<RespostaBase<T>> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RespostaBase<T>(mensagem, dado));
    }

    public ResponseEntity<RespostaBase<T>> authorized() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new RespostaBase<T>(mensagem, dado));
    }

    public ResponseEntity<RespostaBase<T>> notFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespostaBase<T>(mensagem, dado));
    }

    public ResponseEntity<RespostaBase<T>> badRequest() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RespostaBase<T>(mensagem, dado));
    }

    public ResponseEntity<RespostaBase<T>> expectationFailed() {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new RespostaBase<T>(mensagem, dado));
    }

}
