package com.cryxie.utils;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Random;

@Component
public class AppUtils {
    public static final String baseUrl = "/cryxie/api/v1";
    public static final String movieEndpoint = baseUrl + "/movie";
    public static final String authEndpoint = baseUrl + "/auth";
    public static final String userEndpoint = baseUrl + "/user";
    public static final String votoEndpoint = baseUrl + "/voto";

    public static final boolean isTestMode = false;

    public static long localdatetimeParaMilisegundos(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * Gera um número aleatório entre 0 e 1 apenas.
     *
     * @return Tem como retorno um número inteiro.
     */
    public static boolean getTrueOrFalse() {
        Random random = new Random();
        int value = random.nextInt(2);
        return value == 1;
    }

    public static int generateSixDigitCode() {
        return (int) (Math.random() * 900000) + 100000;
    }

    public static String encodeToBase64(String value) {
        var encoder = Base64.getEncoder();
        return encoder.encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

}
