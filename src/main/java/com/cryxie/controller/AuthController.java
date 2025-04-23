package com.cryxie.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryxie.data.RespostaBase;
import com.cryxie.data.dtos.requests.LoginRequestDto;
import com.cryxie.data.dtos.responses.LoginResponseDto;
import com.cryxie.services.ImplServicoAutenticacao;
import com.cryxie.utils.AppUtils;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(AppUtils.authEndpoint)
public class AuthController {

    @Autowired
    private ImplServicoAutenticacao authService;

    @PostMapping("/login")
    public ResponseEntity<RespostaBase<LoginResponseDto>> login(
            @Valid @RequestBody LoginRequestDto authenticationDto)
            throws IOException {

        return authService.login(authenticationDto);
    }

    public record PasswordBody(@NotBlank String password) {
    }

}
