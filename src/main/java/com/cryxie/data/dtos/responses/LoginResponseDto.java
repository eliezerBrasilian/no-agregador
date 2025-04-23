package com.cryxie.data.dtos.responses;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponseDto(
        String token,
        String email,
        String name,
        @JsonProperty("created_at") LocalDateTime createdAt,
        @JsonProperty("user_id") String userId) {
}
