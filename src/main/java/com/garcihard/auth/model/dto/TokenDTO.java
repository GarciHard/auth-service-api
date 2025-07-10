package com.garcihard.auth.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenDTO(
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("access_token") String accessToken) {

    public static TokenDTO of(String accessToken) {
        return new TokenDTO("Bearer", accessToken);
    }
}
