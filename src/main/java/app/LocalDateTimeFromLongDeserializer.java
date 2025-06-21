package app;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateTimeFromLongDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonToken currentToken = p.getCurrentToken();

        if (currentToken == JsonToken.VALUE_NUMBER_INT) {
            // Caso seja número: timestamp em milissegundos
            long timestamp = p.getLongValue();
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        } else if (currentToken == JsonToken.VALUE_STRING) {
            // Caso seja string: ISO-8601
            String str = p.getText().trim();

            // Tenta interpretar a string com OffsetDateTime para lidar com 'Z' (UTC)
            try {
                OffsetDateTime odt = OffsetDateTime.parse(str);
                return odt.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            } catch (Exception e) {
                // Se falhar, tenta LocalDateTime simples
                return LocalDateTime.parse(str);
            }
        } else {
            // Caso inesperado, lança exceção
            throw ctxt.mappingException("Esperado timestamp numérico ou string ISO para datetime");
        }
    }
}
