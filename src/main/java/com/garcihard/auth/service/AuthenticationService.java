package com.garcihard.auth.service;

import com.garcihard.auth.model.dto.LoginDTO;
import com.garcihard.auth.model.dto.TokenDTO;
import com.garcihard.auth.security.CustomUserDetails;
import com.garcihard.auth.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public TokenDTO authenticate(LoginDTO loginRequest) {
        String jwt = getJwt(loginRequest);
        Long expiration = jwtUtil.extractExpirationMillis(jwt);

        return TokenDTO.of(expiration, jwt);
    }

    private String getJwt(LoginDTO loginRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return jwtUtil.generateToken(userDetails);
    }
}
