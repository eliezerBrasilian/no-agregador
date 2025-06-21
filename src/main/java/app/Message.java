package app;

import java.time.LocalDateTime;

public record Message(
        String type,
        String objectIdentifier,
        int valor,
        LocalDateTime datetime) {
}
