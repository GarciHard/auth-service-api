package com.garcihard.auth.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenDTO(
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("expires_at") Long expiresIn,
        @JsonProperty("access_token") String accessToken) {

    public static TokenDTO of(Long expiresIn, String accessToken) {
        return new TokenDTO("Bearer", expiresIn, accessToken);
    }
}
