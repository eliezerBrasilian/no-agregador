package com.cryxie.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

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

}
