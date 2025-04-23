package com.cryxie.controllers;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

class AuthControllerTest {

    @Test
    void login() {
        var encoder = Base64.getEncoder();
        String encodedName = encoder.encodeToString("PICAPAU".getBytes(StandardCharsets.UTF_8));
        System.out.println(encodedName);
    }

    @Test
    void register() {
    }

    @Test
    void confirmCode() {
    }

    @Test
    void sendCodeToResetPassword() {
    }

    @Test
    void resetPassword() {
    }
}