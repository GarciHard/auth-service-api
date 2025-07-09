package com.garcihard.auth.controller;

import com.garcihard.auth.model.dto.LoginDTO;
import com.garcihard.auth.model.dto.NewUserDTO;
import com.garcihard.auth.model.dto.TokenDTO;
import com.garcihard.auth.service.AuthenticationService;
import com.garcihard.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(AuthUserController.BASE_URL)
@RestController
public class AuthUserController {
    static final String BASE_URL = "/api/v1/auth";
    static final String REGISTER_USR_URL = "/register";
    static final String LOGIN_USR_URL = "/login";

    private final AuthenticationService authService;
    private final UserService userService;

    @PostMapping(AuthUserController.REGISTER_USR_URL)
    public ResponseEntity<Void> registerNewUser(
            @RequestBody @Valid NewUserDTO newUser) {
        userService.createUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(AuthUserController.LOGIN_USR_URL)
    public ResponseEntity<TokenDTO> login(
            @RequestBody @Valid LoginDTO credentials) {
        TokenDTO tokenResponse = authService.authenticate(credentials);
        return ResponseEntity.status(HttpStatus.OK).body(tokenResponse);
    }

}
