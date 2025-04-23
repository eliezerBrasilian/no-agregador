package com.cryxie.data.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VotoRequestDto(
                @NotNull @JsonProperty("movie_id") String movieId,
                @NotBlank @JsonProperty("eleitor_id") String eleitorId) {

}
