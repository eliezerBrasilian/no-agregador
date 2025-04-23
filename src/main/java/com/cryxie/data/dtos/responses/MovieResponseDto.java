package com.cryxie.data.dtos.responses;

import java.time.LocalDateTime;

public record MovieResponseDto(
                String nome,
                String description,
                String cover,
                int totalVotos,
                LocalDateTime creationDt) {
}
