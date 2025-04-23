package com.cryxie.data.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthRegisterRequestDto(
        @Email String email,
        @NotBlank @JsonProperty("password") String senha,
        @NotNull @JsonProperty("name") String nome) {
}