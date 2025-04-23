package com.cryxie.models;

import java.time.LocalDateTime;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Document(collection = "movies")
public class Movie {

    public Movie() {
    }

    String id;
    String nome;
    String description;
    String cover;
    int totalVotos;
    LocalDateTime creationDt;

}
